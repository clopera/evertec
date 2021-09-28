package com.pruebatec.utilities;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class TipoRespuestas {

    public ResponseEntity<?> getValidation(BindingResult result) {
        List<String> errors=result.getFieldErrors().stream().map(err->"El campo '"+err.getField()+"' "+err.getDefaultMessage()).collect(Collectors.toList());
        Map<String,Object> response=new HashMap<>();

        response.put("error","Campos requeridos");
        response.put("message",errors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> getDataAccessException(String tipo_operacion_db, DataAccessException e){
        Map<String,Object> response=new HashMap<>();
        response.put("error","Error al realizar la operacion  en la base de datos");
        response.put("message",e.getMessage()+": "+e.getMostSpecificCause().getMessage());

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<?> getInternalServerErrorException(Exception e){
        Map<String,Object> response=new HashMap<>();
        String detalles="";
        try {
            int tam=e.getCause().getCause().getMessage().length();
            int pos=e.getCause().getCause().getMessage().indexOf("List of constraint violations:[");

            if (pos>0) {
                detalles = e.getCause().getCause().getMessage().substring(pos, tam);
                detalles = detalles.replaceAll("[\r\n\t]", "");
            }else{
                detalles = e.getCause().getCause().getMessage();
            }

            response.put("error","Error en la operación");
            response.put("message","Detalles de la Causa: "+detalles);

        }catch (Exception ex){
            response.put("error","Error en la operación");
            response.put("message",e);

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<?> getSuccessRecord(String quien, Object record){
        Map<String,Object> response=new HashMap<>();
        response.put("message",quien+" ha sido creado(a) con éxito");
        response.put("record",record);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    public ResponseEntity<?> getNotFoundRecord(Long id, String tabla){
        Map<String,Object> response=new HashMap<>();
        response.put("error","Error al consultar registro");
        response.put("message","El registro con el ID: "+id+" no existe en la tabla : " + tabla);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> getSuccessUpdateRecord(String quien, Object record){
        Map<String,Object> response=new HashMap<>();
        response.put("message",quien+" ha sido actualizado con éxito");
        response.put("record",record);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    public ResponseEntity<?> getSuccessDeleteRecord(String quien, Object record){
        Map<String,Object> response=new HashMap<>();
        response.put("message",quien+" ha sido eliminado(a) con éxito");
        response.put("record",record);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<?> getOkRecord(String quien, Object record){
        Map<String,Object> response=new HashMap<>();
        response.put("message",quien+" se ha encontrado con éxito");
        response.put("record",record);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<?> getNotAcceptable(String quien, Object record){
        Map<String,Object> response=new HashMap<>();
        response.put("message",quien);
        response.put("record",record);

        return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
    }
}
