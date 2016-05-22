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
     * Metodo que trae todo lo que halla en el archivo txt
     *
     * @param archivo
     * @return
     */
    public String leerArchivo(String archivo) {
        char[] data;
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

    /**
     * *
     * Metodo para validar si existe un jugador con el mismo nombre
     *
     * @param nombre
     * @return True or False
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

    public void verificarAcceso(JTextField nombre, Login login, ArrayList<Jugador> jugadores) {
        String nombreJugador = nombre.getText();
        Jugador jugador;
        if (existe(nombreJugador)) {
            JOptionPane.showMessageDialog(login, "BIENVENID@  " + nombreJugador + "!", "Jugar", JOptionPane.INFORMATION_MESSAGE);
            ElegirNivelFrame ij = new ElegirNivelFrame(nombreJugador, login);
            ij.setVisible(true);
            login.setVisible(false);
        } else {
            jugadores.add(new Jugador(nombreJugador, 0, null));
            guardarJugador(jugadores);
            JOptionPane.showMessageDialog(null, "Se ha guardado el jugador con éxito " + "\n" + "BIENVENID@  " + nombreJugador + "!", "Guardar Jugador", JOptionPane.INFORMATION_MESSAGE);
            ElegirNivelFrame ij = new ElegirNivelFrame(nombreJugador, login);
            ij.setVisible(true);
            login.setVisible(false);
        }
    }

    public String retornarNombreNivel(JComboBox cbNivel){
        String nombreNivel=null;
        if (cbNivel.getSelectedItem().equals("BASICO")){
            nombreNivel="1basico";
        }else  if (cbNivel.getSelectedItem().equals("INTERMEDIO")){
            nombreNivel="2intermedio";
        }else  if (cbNivel.getSelectedItem().equals("AVANZADO")){
            nombreNivel="3avanzado";
        }else{
            nombreNivel= cbNivel.getSelectedItem().toString();
        }
        return nombreNivel + ".txt";
        
    }

    public void imprimirJugadores(ArrayList<Jugador> jugadores) {
        for (int i = 0; i < jugadores.size(); i++) {
            System.out.println("Nombre: " + jugadores.get(i).getNombreJugador() + ", Puntaje: " + jugadores.get(i).getJugadas() + ", Tablero: " + jugadores.get(i).getTablero());
        }
    }

}
