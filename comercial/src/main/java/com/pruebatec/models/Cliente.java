package com.pruebatec.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.*;
import static com.pruebatec.models.Constantes.*;

@Entity
public class Cliente implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long idcliente;

//    @JsonIgnoreProperties(value = {"hibernateLazyInitializer","handler",}, allowSetters = true)
//    @OneToMany(fetch = FetchType.LAZY,  cascade = CascadeType.ALL , orphanRemoval = true)
//    @NotNull(message = MSG_DB_NOT_NULL)
//    @JoinColumn(name = "cliente_id", nullable = false)
//    private Set<Deuda> deudas=new HashSet<>();

    @NotNull(message = MSG_DB_NOT_NULL)
    @Size(min = 1, max = 15,message = MSG_DB_SIZE)
    private String identificacion;

    @NotNull(message = MSG_DB_NOT_NULL)
    @Size(min = 1, max = 60,message = MSG_DB_SIZE)
    private String nombres;

    @Size(min = 0, max = 60,message = MSG_DB_SIZE)
    @Email(message="Porfavor proveer un correo válido")
    @Pattern(regexp=".+@.+\\..+", message="Porfavor proveer un correo válido")
    private String correo;

    public Cliente() {
    }

    public Cliente(Long idcliente, String identificacion, String nombres, String correo) {
        this.idcliente = idcliente;
        this.identificacion = identificacion;
        this.nombres = nombres;
        this.correo = correo;
    }

    public Long getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(Long idcliente) {
        this.idcliente = idcliente;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

//    public Set<Deuda> getDeudas() {
//        return deudas;
//    }
//
//    public void setDeudas(Set<Deuda> deudas) {
//        this.deudas = deudas;
//    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
