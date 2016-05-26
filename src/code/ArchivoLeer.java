/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *
 * @author sokobanUQ
 */
public class ArchivoLeer {

    private char[][] matriz;
/**
 * El metodo nos permite retornar una matriz de acuerdo a un archivo el cual
 * contendrá una serie de caracteres que son los que van a permitir cargar una imagen
 * de acuerdo a cada uno
 * @param nivel
 * @param ruta
 * @return 
 */
    public char[][] leerArchivo(String nivel, String ruta) {
        matriz = new char[20][20];
        String levelDirectory = System.getProperty("user.dir") + java.io.File.separator + "src/" + ruta + java.io.File.separator;
        String filename = levelDirectory + nivel + ".txt";

        BufferedReader in;
        try {
            in = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException e) {
            System.out.println("No pudimos encontrar el archivo especificado como  \"" + filename + "\".");
            return null;
        }

        try {
            for (int i = 0; i < 20; i++) {

                for (int j = 0; j < 20; j++) {
                    char letra = (char) in.read();
                    matriz[j][i] = letra;
                }
                in.readLine();
            }

        } catch (IOException e) {
            System.out.println("Archivo mal formateado, saliendo....");
            return null;
        }
        return matriz;
    }
/**
 * Metodo que retorna los archivos que estén en una determinada ruta ya sean niveles 
 * o partidasGuardads
 * @param ruta
 * @return la lista de archivos
 */
    public String[] leerFicheros(String ruta) {
        String levelDirectory = System.getProperty("user.dir") + java.io.File.separator + "src/" + ruta + java.io.File.separator;
        File fichero = new File(levelDirectory);
        String[] f = null;
        if (fichero.exists()) {

            File[] ficheros = fichero.listFiles();
            f = new String[ficheros.length];
            for (int x = 0; x < ficheros.length; x++) {
                f[x] = ficheros[x].getName().replace(".txt", "").toUpperCase();

            }
        }
        return f;
    }
/**
 * Metodo que permite eliminar un archivo de acuerdo a un nombre y una ruta 
 * @param nivel
 * @param ruta 
 */
    public void eliminarFichero(String nivel, String ruta) {
        System.out.println("Este el archivo: " + nivel);
        System.out.println("Este la ruta: " + ruta);
        String levelDirectory = System.getProperty("user.dir") + java.io.File.separator + "src/" + ruta + java.io.File.separator;
        String filename = levelDirectory + nivel + ".txt";
        System.out.println("Este es la ruta completa: " + filename);
        File fichero = new File(filename);
        if (fichero.delete()) {
            JOptionPane.showMessageDialog(null, "Se ha eliminado el nivel satisfactoriamente", "Eliminar Nivel", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("El fichero ha sido borrado satisfactoriamente");
        } else {
            System.out.println("El fichero no puede ser borrado");
        }
    }
}
