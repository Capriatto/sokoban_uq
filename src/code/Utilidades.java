/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

import static code.Sokoban.jugadores;
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

    public void guardarArchivo(ArrayList<Jugador> j, String archivo) {
        String cd = System.getProperty("user.dir");
        String cd1 = cd + "\\" + archivo;
        File f = new File(cd1);
        try {
            FileWriter escribe = new FileWriter(f);
            for (int x = 0; x < j.size(); x++) {
                escribe.write(j.get(x).getNombreJugador().concat(",").concat(String.valueOf(j.get(x).getJugadas())).concat(",").concat(String.valueOf(j.get(x).getTablero())));
                escribe.write("\n");
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

    public boolean guardarNuevo(String archivo, ArrayList<Jugador> jugadores) {
        Jugador j;

        String outputFile = "test/usuarios_export.txt";
        boolean alreadyExists = new File(outputFile).exists();

        if (alreadyExists) {
            File ficheroUsuarios = new File(outputFile);
            ficheroUsuarios.delete();
        }

        try {

            CsvWriter csvOutput = new CsvWriter(new FileWriter(outputFile, true), ',');

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

    public ArrayList<Jugador> cargar() {
        try {

            ArrayList<Jugador> jugadores = new ArrayList<Jugador>();

            CsvReader usuarios_import = new CsvReader("test/usuarios_export.txt");
            usuarios_import.readHeaders();

            while (usuarios_import.readRecord()) {
                String nombres = usuarios_import.get("JUGADOR");
                int jugadas = Integer.parseInt(usuarios_import.get("JUGADAS"));
                String tablero = usuarios_import.get("TABLERO");
                jugadores.add(new Jugador(nombres, jugadas, null));
            }

            usuarios_import.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();

        }
        return jugadores;
    }

    public boolean existe(String nombre) {
        for (int i = 0; i < cargar().size(); i++) {
            if (cargar().get(i).getNombreJugador().equals(nombre)) {
                return true;
            }
        }
        return false;
    }
}
