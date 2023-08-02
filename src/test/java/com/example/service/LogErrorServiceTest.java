package com.example.service;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class LogErrorServiceTest {

    @Inject
    LogErrorService logErrorService;

    @Test
    public void queSePuedaGuardarUnError(){
        try{
            int a = (int) (5/0);
        }catch (ArithmeticException ex){
            //abrir archivo de error
            logErrorService.grabarError(ex, this.getClass().getName());
        }
    }
}
