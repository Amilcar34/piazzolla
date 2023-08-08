package com.example.service;


import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
public class LogErrorServiceTest {

    @Inject
    LogErrorService logErrorService;

    @Test
    public void queSePuedaGuardarUnError() throws IOException {

        Exception exception = new NullPointerException("Null pointer exception");
        String className = "TestClass";

        boolean result = logErrorService.grabarError(exception, className);

        assertTrue(result);
    }

    @Test
    public void queNoSePuedaGuardarUnError() throws IOException {

        Exception exception = new NullPointerException("Null pointer exception");
        String className = "TestClass";

        LogErrorService mock = Mockito.mock(LogErrorService.class);
        Mockito.when(mock.grabarError(exception, className)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () ->{
            mock.grabarError(exception, className);
        });
    }
}
