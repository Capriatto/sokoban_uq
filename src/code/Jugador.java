/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

import java.io.Serializable;

/**
 *
 * @author sokobanUQ
 */
public class Jugador implements Serializable, Comparable<Jugador>{

    private String nombreJugador;
    private int jugadas;
    private String tablero;

    /**
     * Metodo constructor de la clase jugador
     * @param nombreJugador
     * @param jugadas
     * @param tablero 
     */
    public Jugador(String nombreJugador, int jugadas, String tablero) {
        this.nombreJugador = nombreJugador;
        this.jugadas = jugadas;
        this.tablero = tablero;
    }

    /**
     * *
     * Metodo para obtener el nombre del jugador
     *
     * @return
     */
    public String getNombreJugador() {
        return nombreJugador;
    }

    /**
     * *
     * Metodo para modificar el nombre del jugador
     *
     * @param nombreJugador
     */
    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }

    /**
     * *
     * Metodo para obtener las jugadas del jugador
     *
     * @return
     */
    public int getJugadas() {
        return jugadas;
    }

    /**
     * *
     * Metodo para modificar las jugadas del jugador
     *
     * @param jugadas
     */
    public void setJugadas(int jugadas) {
        this.jugadas = jugadas;
    }

    /**
     * *
     * Metodo para obtener el tablero
     *
     * @return
     */
    public String getTablero() {
        return tablero;
    }

    /**
     * *
     * Metodo para modificar el tablero
     *
     * @param tablero
     */
    public void setTablero(String tablero) {
        this.tablero = tablero;
    }

    public int compareTo(Jugador o) {
        if (jugadas < o.jugadas) {
            return -1;
        }
        if (jugadas > o.jugadas) {
            return 1;
        }
        return 0;
    }
}
