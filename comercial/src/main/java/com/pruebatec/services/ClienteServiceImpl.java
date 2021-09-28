package com.pruebatec.services;

import com.pruebatec.models.Cliente;
import com.pruebatec.repository.ClienteRepository;
import com.pruebatec.utilities.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteServiceImpl implements ClienteService {

    private ClienteRepository repository;
    private DeudaService deudaService;

    @Autowired
    public ClienteServiceImpl(ClienteRepository repository,@Lazy DeudaService deudaService){
        this.repository = repository;
        this.deudaService = deudaService;
    }

    public Cliente save(Cliente cliente){
        return this.repository.save(cliente);
    }

    public ResponseService delete(Long id){
        Cliente cliente=this.findById(id);
        int cantDeudas=this.deudaService.cantidadDeudasConSaldo(cliente.getIdcliente());
        if (cantDeudas>0)
            return new ResponseService(true,"[CLI-ER-DEL]","Ups! el cliente con identificación "+cliente.getIdentificacion()+"  presenta deudas",cliente);
        this.repository.deleteById(id);
        return new ResponseService(false,"[200]","Cliente eliminada satisfactoriamente",cliente);
    }

    public Cliente findById(Long id) { return  this.repository.findById(id).orElse(null); }

    @Transactional(readOnly = true)
    public Page<Cliente> listarClientesPorFiltro(String filtro, Pageable pageable){
        return this.repository.listarClientesPorFiltro(filtro, pageable);
    }

    @Transactional(readOnly = true)
    public Cliente buscarClientePorIdentificacion(String identificacion){
        return this.repository.buscarClientePorIdentificacion(identificacion);
    }
}
