/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

import java.io.FileNotFoundException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import interfaz.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author sokobanUQ
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
        for (int i = 0; i < cargarJugadores().size(); i++) {
            if (cargarJugadores().get(i).getNombreJugador().equals(nombre)) {
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

        String nombreJugador = null;
        int puntaje = 0;
        String tablero = null;
        Charset utf = StandardCharsets.UTF_8;
        Path lector = Paths.get("jugadores.txt");
        try (BufferedWriter escribir = Files.newBufferedWriter(lector, utf)) {

            for (Jugador ju : jugadores) {
                System.out.println("Entro por aca");
                nombreJugador = ju.getNombreJugador();
                puntaje = ju.getJugadas();
                tablero = String.valueOf(ju.getTablero());
                escribir.write(nombreJugador + "," + puntaje + "," + tablero + "\n");
            }
            escribir.close();
        } catch (Exception e) {
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
    public ArrayList<Jugador> cargarJugadores() {
        ArrayList<Jugador> jugadores = new ArrayList<>();
        String temp;
        Charset utf = StandardCharsets.UTF_8;
        Jugador jugador;
        Path lector = Paths.get("jugadores.txt");
        BufferedReader r;

        try {

            r = Files.newBufferedReader(lector, utf);
            while ((temp = r.readLine()) != null) {
                String tempSplit[] = temp.split(",");
                //System.out.println(tempSplit[0] + " " + tempSplit[1]);
                jugador = new Jugador(tempSplit[0], Integer.parseInt(tempSplit[1]), tempSplit[2]);
                jugadores.add(jugador);

            }
            r.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Utilidades.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.out.println("Archivo vacio");
            //Logger.getLogger(Utilidades.class.getName()).log(Level.SEVERE, null, ex);
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
            Frame frame = new Frame(nombreJugador, login);
            frame.setVisible(true);
            login.setVisible(false);
        } else if (!existe(nombreJugador) && nombreJugador.length() > 0) {
            jugadores.add(new Jugador(nombreJugador, 0, null));
            guardarJugador(jugadores);
            JOptionPane.showMessageDialog(null, "Se ha guardado el jugador con éxito " + "\n" + "BIENVENID@  " + nombreJugador + "!", "Guardar Jugador", JOptionPane.INFORMATION_MESSAGE);
            Frame ij = new Frame(nombreJugador, login);
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
     * @param nombreJu
     */
    public void cargarNiveles(JComboBox cbNivel, String nombreJu) {
        String nomJugador = nombreJu.toLowerCase();

        String ruta = System.getProperty("user.dir") + java.io.File.separator + "src/niveles" + java.io.File.separator;
        File[] archivos = new File(ruta).listFiles();
        for (File file : archivos) {
            if (file.isFile()) {
                String nombreArchivo = file.getName().replace(".txt", "");
                if (nombreArchivo.startsWith(nomJugador.toLowerCase())) {
                    cbNivel.addItem(nombreArchivo.replace(nomJugador.toLowerCase(), "").toUpperCase());
                }
                if (nombreArchivo.startsWith("1") || nombreArchivo.startsWith("2") || nombreArchivo.startsWith("3")) {
                    cbNivel.addItem(nombreArchivo.replaceAll("[0-9]", "").toUpperCase());
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
     * Metodo para obtener la posicion donde se encuentra el jugador
     *
     * @param jugadores
     * @param nombre
     * @return puntaje del jugador
     */
    public int retornarPosicion(ArrayList<Jugador> jugadores, String nombre) {
        for (int i = 0; i < jugadores.size(); i++) {
            if (jugadores.get(i).getNombreJugador().equals(nombre)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * *
     * Metodo para modificar el puntaje del jugador
     *
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
     * Metodo para validar al cargar la partida del jugador
     *
     * @param jugadores
     * @param nombre
     * @return
     */
    public boolean validarContinuarJuego(ArrayList<Jugador> jugadores, String nombre) {
        String tablero = jugadores.get(retornarPosicion(jugadores, nombre)).getTablero();
        System.out.println("El tablero es: " + tablero);
        if (!tablero.equals("null")) {
            return true;
        }
        return false;
    }

    /**
     * *
     * Permite llenar la tabla con los datos que esten en el array
     *
     * @param j
     */
    public void llenarTabla(JTable jtJugadores, ArrayList<Jugador> jugadores) {

        DefaultTableModel modelo = (DefaultTableModel) jtJugadores.getModel();
        Object[] fila = new Object[2];

        for (int i = 0; i < jugadores.size(); i++) {
            fila[0] = jugadores.get(i).getNombreJugador();
            fila[1] = jugadores.get(i).getJugadas();
            modelo.addRow(fila);
        }

        jtJugadores.setModel(modelo);

    }

    /**
     * *
     * Permite mostrar en la tabla los mejores jugadores, los cuales han tenido
     * menor numero de jugadas
     *
     * @param jtJugadores
     */
    public void mejoresJugadores(JTable jtJugadores, ArrayList<Jugador> jugadores) {
        DefaultTableModel modelo = (DefaultTableModel) jtJugadores.getModel();
        Object[] fila = new Object[2];

        Collections.sort(jugadores);
        for (int i = 0; i < jugadores.size(); i++) {
            if (jugadores.get(i).getJugadas() == 0) {

            } else {
                fila[0] = jugadores.get(i).getNombreJugador();
                fila[1] = jugadores.get(i).getJugadas();
                modelo.addRow(fila);
            }
        }

        jtJugadores.setModel(modelo);
    }

    /**
     * *
     * Permite mostrar en la tabla los mejores jugadores, los cuales han tenido
     * menor numero de jugadas
     *
     * @param jtJugadores
     */
    public void mejoresJugadoresPorNivel(JTable jtJugadores, ArrayList<Jugador> jugadores, String nombre) {
        DefaultTableModel modelo = (DefaultTableModel) jtJugadores.getModel();
        Object[] fila = new Object[2];
        
        String nivel = nombre.concat(".txt").toLowerCase();
        System.out.println("Esto es el nivel normal: " + nivel);
        Collections.sort(jugadores);
        
        for (int i = 0; i < jugadores.size(); i++) {
            if (jugadores.get(i).getTablero().endsWith(nivel)) {
                if (jugadores.get(i).getJugadas() == 0) {

                } else {
                    fila[0] = jugadores.get(i).getNombreJugador();
                    fila[1] = jugadores.get(i).getJugadas();
                    modelo.addRow(fila);
                }
            }
        }

        jtJugadores.setModel(modelo);
    }

    /**
     * *
     * Permite dar el nombre a las columnas de la tabla
     */
    public void llenarColumnas(JTable jtJugadores) {
        DefaultTableModel modelo = (DefaultTableModel) jtJugadores.getModel();
        modelo.addColumn("NOMBRE JUGADOR");
        modelo.addColumn("PUNTAJE");
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
