package com.pruebatec.utilities;

import org.springframework.stereotype.Component;

public class ResponseService {
    boolean error;
    String codigo;
    String mensaje;
    Object record;

    public ResponseService(boolean error, String codigo, String mensaje, Object record) {
        this.error = error;
        this.codigo = codigo;
        this.mensaje = mensaje;
        this.record = record;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Object getRecord() {
        return record;
    }

    public void setRecord(Object record) {
        this.record = record;
    }
}
