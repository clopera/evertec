package com.pruebatec.services;

import com.pruebatec.models.Cliente;
import com.pruebatec.utilities.ResponseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClienteService {
    Cliente save(Cliente cliente);
    ResponseService delete(Long id);
    Cliente findById(Long id);
    Cliente buscarClientePorIdentificacion(String identificacion);
    Page<Cliente> listarClientesPorFiltro(String filtro, Pageable pageable);
}
