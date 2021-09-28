package com.pruebatec.controllers;

import com.pruebatec.services.ClienteService;
import com.pruebatec.utilities.ResponseService;
import com.pruebatec.utilities.TipoRespuestas;
import com.pruebatec.models.Cliente;
import com.pruebatec.utilities.TratamientoTexto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/cliente")
public class ClienteController {

    private ClienteService clienteService;
    private TipoRespuestas tipoRespuestas;

    @Autowired
    public ClienteController(ClienteService clienteService, TipoRespuestas tipoRespuestas){
        this.clienteService = clienteService;
        this.tipoRespuestas = tipoRespuestas;
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_COMERCIAL','ROLE_USER')")
    @GetMapping(value = "/find/{id}")
    public Cliente getDato(@PathVariable Long id){
        return this.clienteService.findById(id);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @RequestMapping(path = "/list", method = RequestMethod.GET)
    public Page<Cliente> buscarTodosPorFiltroYActivo(@RequestParam Map<String, String> customQuery){
        String filtro= TratamientoTexto.getFilterText(customQuery);
        Pageable pageable=TratamientoTexto.getPagination(customQuery);
        return this.clienteService.listarClientesPorFiltro(filtro,  pageable);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping(path = "/findbyid/{filtro}")
    public Cliente buscarPorIdentificacion(@PathVariable String filtro){
        return this.clienteService.buscarClientePorIdentificacion(filtro);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_COMERCIAL')")
    @PostMapping(value = "/create")
    public ResponseEntity<?> save(@Valid @RequestBody Cliente cliente, BindingResult result){
        if (result.hasErrors())
            return tipoRespuestas.getValidation(result);
        try {
            this.clienteService.save(cliente);
        }catch (DataAccessException e) {
            return tipoRespuestas.getDataAccessException("INSERT", e);
        }catch (Exception e){
            return tipoRespuestas.getInternalServerErrorException(e);
        }
        return tipoRespuestas.getSuccessRecord("El cliente ",cliente);
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_COMERCIAL')")
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Cliente cliente, BindingResult result, @PathVariable Long id){
        if (result.hasErrors())
            return tipoRespuestas.getValidation(result);
        try {
            if (this.clienteService.findById(id)==null)
                return tipoRespuestas.getNotFoundRecord(id,"Clientes");
            this.clienteService.save(cliente);
        }catch (DataAccessException e){
            return tipoRespuestas.getDataAccessException("UPDATE",e);
        }catch (Exception e){
            return tipoRespuestas.getInternalServerErrorException(e);
        }

        return tipoRespuestas.getSuccessUpdateRecord("El cliente ",cliente);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        ResponseService responseService;
        //Buscamos los datos del registro a borrar
        Cliente cliente = this.clienteService.findById(id);
        if(cliente == null)
            return tipoRespuestas.getNotFoundRecord(id,"Clientes");
        //Ejecutamos el borrado
        try {
            responseService=this.clienteService.delete(id);
            if (responseService.isError())
                return this.tipoRespuestas.getNotAcceptable(responseService.getCodigo()+":"+responseService.getMensaje(),responseService.getRecord());
        }catch (DataAccessException e){
            return tipoRespuestas.getDataAccessException("DELETE",e);
        }catch (Exception e){
            return tipoRespuestas.getInternalServerErrorException(e);
        }
        return tipoRespuestas.getSuccessDeleteRecord("El cliente "+cliente.getIdentificacion().concat(" ").concat(cliente.getNombres()),cliente);
    }



}
