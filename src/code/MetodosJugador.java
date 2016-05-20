/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

import static code.Sokoban.utilidades;
import interfaz.BoardFrame;
import interfaz.Login;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author BRAYHAN JARAMILLO
 */
public class MetodosJugador {

    private ArrayList<Jugador> jugadores;

    /**
     * *
     * Metodo constructor, se inicializa el ArrayList de jugadores
     */
    public MetodosJugador() {
        this.jugadores = new ArrayList<Jugador>();
    }

    /**
     * *
     * Este metodo permite buscar un jugador
     *
     * @param id
     * @return un numero mayor a -1 si lo encuntra y un -1 si no lo encuentra
     */
    public int buscarJugador(String nombre) {
        if (jugadores.size() == 0) {
            return -1;
        }
        for (int i = 0; i < jugadores.size(); i++) {
            if (jugadores.get(i).getNombreJugador().equals(nombre)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * *
     * Permite guardar un jugador siempre y cuando no este repetido el id
     *
     * @param j
     * @return True (si lo guardo) o False (si no lo guardo)
     */
    public boolean guardarJugador(Jugador j) {
        if (buscarJugador(j.getNombreJugador()) != -1) {
            return false;
        }
        return jugadores.add(j);
    }

    /**
     * *
     * Metodo que guarda el jugador
     */
    public void guardarElJugador(Jugador jugadorDatos, JTextField nombre) {

        String nombreJugador = nombre.getText();

        if ((nombreJugador.length() == 0)) {
            JOptionPane.showMessageDialog(null, "Debe llenar el campo USERNAME", "Registrar Jugador", JOptionPane.ERROR_MESSAGE);
        } else {
            jugadorDatos = new Jugador(nombreJugador, 0, null);
            boolean guardoJugador = guardarJugador(jugadorDatos);
            if (guardoJugador == true) {
                JOptionPane.showMessageDialog(null, "Se ha guardado el jugador con éxito", "Guardar Jugador", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "No se ha podido guardar el jugador." + "\n" + "El ID ya existe!", "Guardar Jugador", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * *
     * Permite obtener el tamaño del arreglo de jugadores que tiene en el
     * momento
     *
     * @return tamañño del ArrayList de jugadores
     */
    public int tamanioJugadores() {
        return jugadores.size();
    }

    /**
     * *
     * Muestra en la tabla cada uno de los jugadores que hay guardados en el
     * ArrayList
     *
     * @param jtJugadores
     */
    public void mostrarJugadores(JTable jtJugadores) {
        DefaultTableModel modelo = (DefaultTableModel) jtJugadores.getModel();
        Object[] fila = new Object[4];

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
     * menor numero de jugadas y menor numero de tiempo
     *
     * @param jtJugadores
     */
    public void mejoresJugadores(JTable jtJugadores) {
        DefaultTableModel modelo = (DefaultTableModel) jtJugadores.getModel();
        Object[] fila = new Object[3];
        int puntaje = 1000;
        int tiempo = 0;
        for (int i = 0; i < jugadores.size(); i++) {

            if (jugadores.get(i).getJugadas() != 0) {

                if (jugadores.get(i).getJugadas() < puntaje) {
                    puntaje = jugadores.get(i).getJugadas();
                }
                fila[0] = jugadores.get(i).getNombreJugador();
                fila[1] = jugadores.get(i).getJugadas();
                fila[2] = jugadores.get(i).getTablero();
                modelo.addRow(fila);
            }
        }
        jtJugadores.setModel(modelo);
    }

    /**
     * *
     * Verifica si el el ID del jugador ingresado por el usuario esta guardado
     * en el array de jugadores
     *
     * @param id
     * @return int
     */
    public int verificarJugador(String nombre) {
        for (int i = 0; i < jugadores.size(); i++) {
            if (jugadores.get(i).getNombreJugador().equals(nombre)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * *
     * Permite verificar el acceso al ju
     *
     * @param txtId
     * @param principal
     * @param login
     */
    public void verificarLogin(JTextField nombre, Login login) {
        String id;
        String nombreJugador;
        int verificar;
        Jugador jugadorDatos = null;

        if (nombre.getText().length() == 0) {
            JOptionPane.showMessageDialog(login, "Debe llenar el campo USERNAME", "Jugar", JOptionPane.ERROR_MESSAGE);
        } else if (nombre.getText().length() > 0) {

            if (utilidades.existe(nombre.getText(), "jugadores.txt") == false) {
                nombreJugador = nombre.getText().toUpperCase();
                guardarElJugador(jugadorDatos, nombre);
                utilidades.guardarJugador("jugadores.txt", jugadores);
                JOptionPane.showMessageDialog(login, "BIENVENID@  " + nombreJugador + "!", "Jugar", JOptionPane.INFORMATION_MESSAGE);
                BoardFrame ij = new BoardFrame(nombre.getText(), login);
                ij.setVisible(true);
                login.setVisible(false);

            } else {
                JOptionPane.showMessageDialog(login, "Ya se ha registrado otro usuario con este nombre", "Error al guardar", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * *
     * Este metodo retorna el puntaje del jugador
     *
     * @param id
     * @return String
     */
    public int retornarPuntajeJugador(String nombre) {

        int informacionJugador = 0;
        if (verificarJugador(nombre) == -1) {
            informacionJugador = 0;
        }
        if (verificarJugador(nombre) != -1) {
            informacionJugador = jugadores.get(verificarJugador(nombre)).getJugadas();
        }

        return informacionJugador;
    }

    /**
     * *
     * Este metodo permite modificar el puntaje del jugador
     *
     * @param id
     * @param puntajeJugador
     * @return verdadero si es posible realizar la modificación del puntaje del
     * jugador si no es posible devuelve falso
     */
    public boolean modificarPuntajeJugador(String nombre, int puntajeJugador) {

        boolean modificar;
        if (verificarJugador(nombre) == -1) {
            modificar = false;
        }

        if (verificarJugador(nombre) != -1) {
            jugadores.get(verificarJugador(nombre)).setJugadas(puntajeJugador);
            return true;
        }
        return false;
    }

    /**
     * *
     * Metodo que modifica el putaje del jugador
     *
     * @param id
     * @param tiempo
     * @param jugadas
     * @param jugar
     */
    public void guardarInformacionGanador(String nombre, int jugadas, BoardFrame jugar) {

        if (verificarJugador(nombre) != -1) {
            boolean puntajeJugador = modificarPuntajeJugador(nombre, jugadas);
            if ((puntajeJugador == true)) {
                JOptionPane.showMessageDialog(jugar, "FELICIDADES " + nombre.toUpperCase() + "\n" + "SU PUNTAJE ES: " + jugadas, "JUGADOR", JOptionPane.OK_OPTION);
            }
        }
    }
//    public void mostrarInformacion() {
//        for (int i = 0; i < jugadores.size(); i++) {
//            System.out.println("El jugador " + jugadores.get(i).getId() + " " + jugadores.get(i).getNombre());
//        }
//    }
}
