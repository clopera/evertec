package com.pruebatec.repository;

import com.pruebatec.models.Deuda;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DeudaRepository extends JpaRepository<Deuda,Long> {
    @Query(value = "select d " +
            "from Deuda d " +
            "where d.cliente.idcliente=?1 ")
    Page<Deuda> listarDeudasPorIdCliente(Long idcliente, Pageable pageable);

    @Query(value = "select count(d) " +
            "from Deuda d " +
            "where d.cliente.idcliente=?1 and d.monto>0")
    int cantidadDeudasConSaldo(Long idcliente);
}
