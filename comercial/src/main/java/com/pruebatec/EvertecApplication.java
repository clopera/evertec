package com.pruebatec;

import com.pruebatec.models.Cliente;
import com.pruebatec.models.Deuda;
import com.pruebatec.services.ClienteService;
import com.pruebatec.services.DeudaService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.Calendar;

@SpringBootApplication
public class EvertecApplication {

    public static void main(String[] args) {
        SpringApplication.run(EvertecApplication.class, args);
    }

    @Bean
    CommandLineRunner run(ClienteService clienteService, DeudaService deudaService){
        return args -> {
            Calendar cal = Calendar.getInstance();

            //Clientes
            Cliente cliente1=clienteService.save(new Cliente(null, "145657876-4", "juan perez saez","juan.perez@gmail.com"));
            Cliente cliente2=clienteService.save(new Cliente(null, "123232323-5", "cristian perez aguila","adcdae@gmail.com"));

            //Deuda por cliente
            cal.set (2021,3,12);
            deudaService.save(new Deuda(null,cliente1,"zbc1233",new BigDecimal("19000"),cal.getTime()));
            deudaService.save(new Deuda(null,cliente2,"zbc1233",new BigDecimal("23456"),cal.getTime()));

        };
    }

}
