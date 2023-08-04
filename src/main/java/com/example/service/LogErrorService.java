package com.example.service;

import jakarta.enterprise.context.ApplicationScoped;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ApplicationScoped
public class LogErrorService {

    public Boolean grabarError(Exception exception, String className)  {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            String data = LocalDateTime.now().format(formatter) + "\n"
                    + "Excepcion name: " + exception.getClass().getName() + " message: " + exception.getMessage() + " Class error: " + className + "\n";

            Path filePath = Paths.get("errors.txt");
            // Si el archivo no existe, se crea
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
            }
            // Escribir en el archivo usando Files.write
            Files.write(filePath, data.getBytes(), java.nio.file.StandardOpenOption.APPEND);

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
