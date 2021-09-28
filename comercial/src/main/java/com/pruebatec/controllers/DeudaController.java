package com.pruebatec.controllers;

import com.pruebatec.services.DeudaService;
import com.pruebatec.utilities.ResponseService;
import com.pruebatec.utilities.TipoRespuestas;
import com.pruebatec.models.Deuda;
import com.pruebatec.utilities.TratamientoTexto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/deuda")
public class DeudaController {

    private DeudaService deudaService;
    private TipoRespuestas tipoRespuestas;

    @Autowired
    public DeudaController(DeudaService deudaService, TipoRespuestas tipoRespuestas){
        this.deudaService = deudaService;
        this.tipoRespuestas = tipoRespuestas;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER','ROLE_COMERCIAL')")
    @RequestMapping(path = "/findbyidclient", method = RequestMethod.GET)
    public ResponseEntity<?> buscarDeudasPorIdCliente(@RequestParam Map<String, String> customQuery){
        String filtro= TratamientoTexto.getFilterText(customQuery);
        Pageable pageable=TratamientoTexto.getPagination(customQuery);
        return this.tipoRespuestas.getOkRecord("La Deuda por filtro: "+filtro+" ", this.deudaService.listarDeudasPorIdCliente(filtro, pageable));
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER','ROLE_COMERCIAL')")
    @GetMapping("/find/{id}")
    public Deuda getDato(@PathVariable Long id){
        return this.deudaService.findById(id);
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_COMERCIAL')")
    @PostMapping("/create")
    public ResponseEntity<?> save(@Valid @RequestBody Deuda deuda, BindingResult result){
        if (result.hasErrors())
            return tipoRespuestas.getValidation(result);
        try {
            this.deudaService.save(deuda);
        }catch (DataAccessException e) {
            return tipoRespuestas.getDataAccessException("INSERT", e);
        }catch (Exception e){
            return tipoRespuestas.getInternalServerErrorException(e);
        }
        return tipoRespuestas.getSuccessRecord("La deuda ",deuda);
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_COMERCIAL')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Deuda deuda, BindingResult result, @PathVariable Long id){
        if (result.hasErrors())
            return tipoRespuestas.getValidation(result);
        try {
            if (this.deudaService.findById(id)==null)
                return tipoRespuestas.getNotFoundRecord(id,"Deuda");
            this.deudaService.save(deuda);
        }catch (DataAccessException e){
            return tipoRespuestas.getDataAccessException("UPDATE",e);
        }catch (Exception e){
            return tipoRespuestas.getInternalServerErrorException(e);
        }
        return tipoRespuestas.getSuccessUpdateRecord("La deuda ",deuda);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        ResponseService responseService;
        //Buscamos los datos del registro a borrar
        Deuda deuda = this.deudaService.findById(id);
        if(deuda == null)
            return tipoRespuestas.getNotFoundRecord(id,"Deuda");
        //Ejecutamos el borrado
        try {
            responseService=this.deudaService.delete(id);
            if (responseService.isError())
                return this.tipoRespuestas.getNotAcceptable(responseService.getCodigo()+":"+responseService.getMensaje(),responseService.getRecord());
        }catch (DataAccessException e){
            return tipoRespuestas.getDataAccessException("DELETE",e);
        }catch (Exception e){
            return tipoRespuestas.getInternalServerErrorException(e);
        }

        return tipoRespuestas.getSuccessDeleteRecord("La deuda cn código: "+deuda.getCoddeuda(),deuda);
    }
}
