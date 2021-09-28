package com.pruebatec.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import static com.pruebatec.models.Constantes.MSG_DB_NOT_NULL;
import static com.pruebatec.models.Constantes.MSG_DB_SIZE;

@Entity
public class Deuda implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long iddeuda;

    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @NotNull(message = MSG_DB_NOT_NULL)
    @Size(min = 1, max = 15,message = MSG_DB_SIZE)
    private String coddeuda;

    private BigDecimal monto;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date fechavence;

    public Deuda() {
    }

    public Deuda(Long iddeuda, Cliente cliente, String coddeuda, BigDecimal monto, Date fechavence) {
        this.iddeuda = iddeuda;
        this.cliente = cliente;
        this.coddeuda = coddeuda;
        this.monto = monto;
        this.fechavence = fechavence;
    }

    public Long getIddeuda() {
        return iddeuda;
    }

    public void setIddeuda(Long iddeuda) {
        this.iddeuda = iddeuda;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getCoddeuda() {
        return coddeuda;
    }

    public void setCoddeuda(String coddeuda) {
        this.coddeuda = coddeuda;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public Date getFechavence() {
        return fechavence;
    }

    public void setFechavence(Date fechavence) {
        this.fechavence = fechavence;
    }
}
