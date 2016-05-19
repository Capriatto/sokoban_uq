/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author BRAYHAN JARAMILLO
 */
public class MetodosJugador {

    private ArrayList<Jugador> jugadores;
    private File file;

    /**
     * *
     * Metodo para poder guardar el jugador en un archivo .txt
     *
     * @return
     */
    public ArrayList<Jugador> leer() {
        ArrayList<Jugador> jugadores = new ArrayList<>();
        Jugador j;

        if (file.exists()) {
            try {
                FileInputStream fileInput = new FileInputStream(file);
                while (true) {
                    ObjectInputStream fis = new ObjectInputStream(fileInput);
                    j = (Jugador) fis.readObject();
                    jugadores.add(j);
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(MetodosJugador.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(MetodosJugador.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(MetodosJugador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return jugadores;
    }

    public boolean existe(String nombre) {
        for (int i = 0; i < leer().size(); i++) {
            if (leer().get(i).getNombreJugador().equals(nombre)) {
                return true;
            }
        }

        return false;
    }

}
