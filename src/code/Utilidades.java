/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author BRAYHAN JARAMILLO
 */
public class Utilidades {

    public void guardarArchivo(String texto, String archivo) {
        String cd = System.getProperty("user.dir");
        String cd1 = cd + "\\" + archivo;
        File f = new File(cd1);
        try {
            FileWriter escribe = new FileWriter(f);
            for (int x = 0; x < texto.length(); x++) {
                escribe.write((int) texto.charAt(x));
            }
            escribe.close();
        } catch (Exception w) {
            System.out.print(w);
        }
    }

    public String leerArchivo(String archivo) {
        char[] data = new char[0];
        String cd = System.getProperty("user.dir");
        String cd1 = cd + "\\" + archivo;
        File f = new File(cd1);
        try {
            FileReader fin = new FileReader(f);
            int filesize = (int) f.length();
            data = new char[filesize];
            fin.read(data, 0, filesize);
        } catch (FileNotFoundException exc) {
            String errorString = "No se Encontro Archivo: ";
            data = errorString.toCharArray();
        } catch (IOException exc) {
            String errorString = "Tipo de Error: ";
            data = errorString.toCharArray();
        }
        return new String(data);
    }
}
