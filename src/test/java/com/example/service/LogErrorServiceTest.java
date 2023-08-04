package com.example.service;


import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
public class LogErrorServiceTest {

    @Inject
    LogErrorService logErrorService;

    @Test
    public void queSePuedaGuardarUnError(){

        Exception exception = new NullPointerException("Null pointer exception");
        String className = "TestClass";

        boolean result = logErrorService.grabarError(exception, className);

        assertTrue(result);
    }
}
