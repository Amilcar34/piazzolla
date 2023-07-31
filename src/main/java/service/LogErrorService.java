package service;

import jakarta.enterprise.context.ApplicationScoped;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ApplicationScoped
public class LogErrorService {

    public void grabarError(Exception exception, String className) {
        BufferedWriter bw = null;
        FileWriter fw = null;

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            String data = LocalDateTime.now().format(formatter) + "\n"
                    + "Excepcion name: " + exception.getClass().getName() + " message: " +exception.getMessage() + " Class error: " + className + "\n";
            File file = new File("errors.txt");
            // Si el archivo no existe, se crea!
            if (!file.exists()) {
                file.createNewFile();
            }
            // flag true, indica adjuntar información al archivo.
            fw = new FileWriter(file.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);
            bw.write(data);
            System.out.println("información agregada!");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                //Cierra instancias de FileWriter y BufferedWriter
                if (bw != null)
                    bw.close();
                if (fw != null)
                    fw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
