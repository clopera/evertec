package com.pruebatec.services;

import com.pruebatec.models.Deuda;
import com.pruebatec.utilities.ResponseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DeudaService {
    Deuda save(Deuda deuda);
    ResponseService delete(Long id);
    Deuda findById(Long id);
    Page<Deuda> listarDeudasPorIdCliente(String identificacion, Pageable pageable);
    int cantidadDeudasConSaldo(Long idcliente);
}
