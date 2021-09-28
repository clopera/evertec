package com.pruebatec.services;

import com.pruebatec.models.Cliente;
import com.pruebatec.models.Deuda;
import com.pruebatec.repository.DeudaRepository;
import com.pruebatec.utilities.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DeudaServiceImpl implements DeudaService {

    private DeudaRepository repository;
    private ClienteService clienteService;
    private ResponseService responseService;

    @Autowired
    public DeudaServiceImpl(DeudaRepository repository, ClienteService clienteService){
        this.repository = repository;
        this.clienteService = clienteService;
    }

    public Deuda save(Deuda deuda){
        return this.repository.save(deuda);
    }

    public ResponseService delete(Long id){
        Deuda deuda = this.findById(id);
        if (deuda.getMonto().compareTo(BigDecimal.ZERO)>0)
            return new ResponseService(true,"[DEU-ER-DEL]","Ups! la deuda con código:"+deuda.getCoddeuda()+"  presenta saldo",deuda);
        this.repository.deleteById(id);
        return new ResponseService(false,"[200]","Deuda eliminada satisfactoriamente",deuda);
    }

    public Deuda findById(Long id) { return  this.repository.findById(id).orElse(null); }

    public Page<Deuda> listarDeudasPorIdCliente(String identificacion, Pageable pageable) {
        Cliente cliente=this.clienteService.buscarClientePorIdentificacion(identificacion);
        if (cliente != null)
            return  this.repository.listarDeudasPorIdCliente(cliente.getIdcliente(), pageable);

        return new PageImpl<Deuda>(null, pageable,0);
    }

    @Override
    public int cantidadDeudasConSaldo(Long idcliente) {
        return this.repository.cantidadDeudasConSaldo(idcliente);
    }
}
