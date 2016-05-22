/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

import interfaz.BoardFrame;
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
     * @param nombre
     * @return un numero mayor a -1 si lo encuntra y un -1 si no lo encuentra
     */
    public int buscarJugador(String nombre) {
        if (jugadores.isEmpty()) {
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
     * @param jugadorDatos
     * @param nombre
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
     * @param nombre
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
     * Este metodo retorna el puntaje del jugador
     *
     * @param nombre
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
     * @param nombre
     * @param puntajeJugador
     * @return verdadero si es posible realizar la modificación del puntaje del
     * jugador si no es posible devuelve falso
     */
    public boolean modificarPuntajeJugador(String nombre, int puntajeJugador) {
        if (verificarJugador(nombre) == -1) {
            return false;
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

}
