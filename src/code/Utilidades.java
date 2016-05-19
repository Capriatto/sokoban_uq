/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

/**
 *
 * @author BRAYHAN JARAMILLO
 */
public class Utilidades {

    
    /***
     * Metodo que trae todo lo que halla en el archivo txt
     * @param archivo
     * @return 
     */
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

    /***
     * Metodo para guardar un jugador en el archivo txt
     * @param archivo
     * @param jugadores
     * @return True or False
     */
    public boolean guardarJugador(String archivo, ArrayList<Jugador> jugadores) {
        Jugador j;
        String cd = System.getProperty("user.dir");
        String directorio = cd + "\\" + archivo;
        System.out.println("directorio" + directorio);
        String outputFile = "test/usuarios_export.txt";
        System.out.println("El otro " + outputFile);
        boolean alreadyExists = new File(directorio).exists();

        if (alreadyExists) {
            File ficheroUsuarios = new File(directorio);
            ficheroUsuarios.delete();
        }

        try {

            CsvWriter csvOutput = new CsvWriter(new FileWriter(directorio, true), ',');

            csvOutput.write("JUGADOR");
            csvOutput.write("JUGADAS");
            csvOutput.write("TABLERO");
            csvOutput.endRecord();

            for (Jugador ju : jugadores) {

                csvOutput.write(ju.getNombreJugador());
                csvOutput.write(String.valueOf(ju.getJugadas()));
                csvOutput.write(String.valueOf(ju.getTablero()));
                csvOutput.endRecord();
            }

            csvOutput.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    
    /***
     * Metodo para traer un arrayList de todos los jugadores que hay en el archivo txt
     * @return ArrayList de Jugadores
     */
    public ArrayList<Jugador> cargar(String archivo) {
        ArrayList<Jugador> jugadorCargado = new ArrayList<Jugador>();
        String cd = System.getProperty("user.dir");
        String directorio = cd + "\\" + archivo;
        try {

            CsvReader usuarios_import = new CsvReader(directorio);
            usuarios_import.readHeaders();

            while (usuarios_import.readRecord()) {
                String nombres = usuarios_import.get("JUGADOR");
                int jugadas = Integer.parseInt(usuarios_import.get("JUGADAS"));
                String tablero = usuarios_import.get("TABLERO");
                jugadorCargado.add(new Jugador(nombres, jugadas, null));
            }

            usuarios_import.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jugadorCargado;
    }

    /***
     * Metodo para validar si existe un jugador con el mismo nombre
     * @param nombre
     * @return True or False
     */
    public boolean existe(String nombre, String archivo) {
        for (int i = 0; i < cargar(archivo).size(); i++) {
            if (cargar(archivo).get(i).getNombreJugador().equals(nombre)) {
                return true;
            }
        }
        return false;
    }
}
