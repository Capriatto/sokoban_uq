/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

import java.io.Serializable;

/**
 *
 * @author BRAYHAN JARAMILLO
 */
public class Jugador implements Serializable {

    private String nombreJugador;
    private int puntaje;
    private int jugadas;
    private char[][] tablero;

    public Jugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
        puntaje = jugadas = 0;
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
     * Metodo para obtener el puntaje del jugador
     *
     * @return
     */
    public int getPuntaje() {
        return puntaje;
    }

    /**
     * *
     * Metodo para modificar el puntaje del jugador
     *
     * @param puntaje
     */
    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
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
    public char[][] getTablero() {
        return tablero;
    }

    /**
     * *
     * Metodo para modificar el tablero
     *
     * @param tablero
     */
    public void setTablero(char[][] tablero) {
        this.tablero = tablero;
    }
}
