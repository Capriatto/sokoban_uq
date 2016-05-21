/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import interfaz.ElegirNivelFrame;
import interfaz.Login;
import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author BRAYHAN JARAMILLO
 */
public class Utilidades {

    /**
     * *
     * Metodo para validar si existe un jugador con el mismo nombre
     *
     * @param nombre
     * @return true or false
     */
    public boolean existe(String nombre) {
        System.out.println("Nombre: " + nombre);
        for (int i = 0; i < cargarJugadoresBueno().size(); i++) {
            if (cargarJugadoresBueno().get(i).getNombreJugador().equals(nombre)) {
                return true;
            }
        }
        return false;
    }

    /**
     * *
     * Metodo para guardar un jugador
     *
     * @param jugadores
     * @return true or false
     */
    public boolean guardarJugador(ArrayList<Jugador> jugadores) {
        try {
            String nombreJugador = null;
            String puntaje = null;
            String tablero = null;
            String cd = System.getProperty("user.dir");
            String directorio = cd + "\\" + "jugadores.txt";
            //Crear un objeto File se encarga de crear o abrir acceso a un archivo que se especifica en su constructor
            File archivo = new File("jugadores.txt");

            try ( //Crear objeto FileWriter que sera el que nos ayude a escribir sobre archivo
                    FileWriter escribir = new FileWriter(archivo, true)) {
                for (Jugador ju : jugadores) {
                    nombreJugador = ju.getNombreJugador();
                    puntaje = String.valueOf(ju.getJugadas());
                    tablero = String.valueOf(ju.getTablero());
                }
                //Escribimos en el archivo con el metodo write
                escribir.write(nombreJugador + "," + puntaje + "," + tablero + "\n");
            }
        } //Si existe un problema al escribir cae aqui //Si existe un problema al escribir cae aqui
        catch (Exception e) {
            System.out.println("Error al escribir");
            return false;
        }
        return true;
    }

    /**
     * *
     * Metodo que retorna un ArrayList de los jugadores del archivo txt
     *
     * @return ArrayList<Jugador>
     */
    public ArrayList<Jugador> cargarJugadoresBueno() {
        ArrayList<Jugador> jugadores = new ArrayList<>();
        String temp;
        Charset utf = StandardCharsets.UTF_8;
        Jugador jugador;
        String cd = System.getProperty("user.dir");
        String directorio = cd + "\\" + "jugadores.txt";
        Path lector = Paths.get("jugadores.txt");
        BufferedReader r;

        try {

            r = Files.newBufferedReader(lector, utf);
            while ((temp = r.readLine()) != null) {
                String tempSplit[] = temp.split(",");
                //System.out.println(tempSplit[0] + " " + tempSplit[1]);
                jugador = new Jugador(tempSplit[0], Integer.parseInt(tempSplit[1]), null);
                jugadores.add(jugador);

            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Utilidades.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Utilidades.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jugadores;
    }

    /**
     * *
     * Metodo para verificar el acceso del jugador, si esta o no creado en el
     * archivo
     *
     * @param nombre
     * @param login
     * @param jugadores
     */
    public void verificarAcceso(JTextField nombre, Login login, ArrayList<Jugador> jugadores) {
        String nombreJugador = nombre.getText();
        Jugador jugador;
        if (existe(nombreJugador) && nombreJugador.length() > 0) {
            JOptionPane.showMessageDialog(login, "BIENVENID@  " + nombreJugador + "!", "Jugar", JOptionPane.INFORMATION_MESSAGE);
            ElegirNivelFrame ij = new ElegirNivelFrame(nombreJugador, login);
            ij.setVisible(true);
            login.setVisible(false);
        } else if (!existe(nombreJugador) && nombreJugador.length() > 0) {
            jugadores.add(new Jugador(nombreJugador, 0, null));
            guardarJugador(jugadores);
            JOptionPane.showMessageDialog(null, "Se ha guardado el jugador con éxito " + "\n" + "BIENVENID@  " + nombreJugador + "!", "Guardar Jugador", JOptionPane.INFORMATION_MESSAGE);
            ElegirNivelFrame ij = new ElegirNivelFrame(nombreJugador, login);
            ij.setVisible(true);
            login.setVisible(false);
        }
        if (nombreJugador.length() == 0) {
            JOptionPane.showMessageDialog(null, "Se debe de llenar el campo USERNAME", "Guardar Jugador", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * *
     * Metodo para cargar los niveles en orden BASICO INTERMEDIO AVANZADO y
     * despues de estos se cargan los demas que esten creados por los jugadores
     *
     * @param cbNivel
     */
    public void cargarNiveles(JComboBox cbNivel) {
        String ruta = System.getProperty("user.dir") + java.io.File.separator + "src/niveles" + java.io.File.separator;
        File[] archivos = new File(ruta).listFiles();
        for (File file : archivos) {
            if (file.isFile()) {
                String nombreArchivo = file.getName().replace(".txt", "");
                if (nombreArchivo.equals("1basico")) {
                    cbNivel.addItem(nombreArchivo.replace("1", "").toUpperCase());
                } else if (nombreArchivo.equals("2intermedio")) {
                    cbNivel.addItem(nombreArchivo.replace("2", "").toUpperCase());
                } else if (nombreArchivo.equals("3avanzado")) {
                    cbNivel.addItem(nombreArchivo.replace("3", "").toUpperCase());
                } else if (!nombreArchivo.equals("1BASICO") && !nombreArchivo.equals("2INTERMEDIO") && !nombreArchivo.equals("3AVANZADO")) {
                    cbNivel.addItem(nombreArchivo.toUpperCase());
                }
            }
        }
    }

    /**
     * *
     * Metodo para retornar el nombre del nivel que se escogio en el combobox
     *
     * @param cbNivel
     * @return nombre del nivel
     */
    public String retornarNombreNivel(JComboBox cbNivel) {
        String nombreNivel = null;
        if (cbNivel.getSelectedItem().equals("BASICO")) {
            nombreNivel = "1basico";
        } else if (cbNivel.getSelectedItem().equals("INTERMEDIO")) {
            nombreNivel = "2intermedio";
        } else if (cbNivel.getSelectedItem().equals("AVANZADO")) {
            nombreNivel = "3avanzado";
        } else {
            nombreNivel = cbNivel.getSelectedItem().toString();
        }
        return nombreNivel;

    }

    /**
     * *
     * Metodo para modificar el puntaje
     *
     * @param jugadores
     * @param nombre
     * @return puntaje del jugador
     */
    public int retornarPuntaje(ArrayList<Jugador> jugadores, String nombre) {
        for (int i = 0; i < jugadores.size(); i++) {
            if (jugadores.get(i).getNombreJugador().equals(nombre)) {
                return jugadores.get(i).getJugadas();
            }
        }
        return -1;
    }

    /***
     * Metodo para modificar el puntaje del jugador
     * @param jugadores
     * @param nombre
     * @param puntaje
     * @return true or false
     */
    public boolean modificarPuntaje(ArrayList<Jugador> jugadores, String nombre, int puntaje) {
        for (int i = 0; i < jugadores.size(); i++) {
            if (jugadores.get(i).getNombreJugador().equals(nombre)) {
                jugadores.get(i).setJugadas(puntaje);
                return true;
            }
        }
        return false;
    }

    /**
     * *
     * Metodo para imprimir los jugadores contenidos en el archivo
     *
     * @param jugadores
     */
    public void imprimirJugadores(ArrayList<Jugador> jugadores) {
        for (int i = 0; i < jugadores.size(); i++) {
            System.out.println("Nombre: " + jugadores.get(i).getNombreJugador() + ", Puntaje: " + jugadores.get(i).getJugadas() + ", Tablero: " + jugadores.get(i).getTablero());
        }
    }

}
