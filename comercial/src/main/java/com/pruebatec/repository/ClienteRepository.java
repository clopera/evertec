package com.pruebatec.repository;

import com.pruebatec.models.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    @Query(value = "select c " +
            "from Cliente c " +
            "where c.identificacion like (?1) or upper(c.nombres) like (?1)")
    Page<Cliente> listarClientesPorFiltro(String filtro, Pageable pageable);

    @Query(value = "select c " +
            "from Cliente c " +
            "where c.identificacion=?1")
    Cliente buscarClientePorIdentificacion(String identificacion);
}
