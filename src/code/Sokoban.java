/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

import interfaz.BoardFrame;
import java.util.ArrayList;

/**
 *
 * @author FabianGM
 */
public class Sokoban {

    public static Utilidades utilidades = new Utilidades();
    public static ArrayList<Jugador> jugadores = new ArrayList<Jugador>();
    public static char[][] tablero = {{'M', 'M', 'M', 'M', 'M'},
    {'C', 'B', 'B', 'C', 'B'},
    {'C', 'C', 'C', 'C', 'C'},
    {'M', 'M', 'M', 'M', 'M'}
    };
    public static Jugador j = new Jugador("Brayhan", 10, tablero);
    public static Jugador j2 = new Jugador("Katherine", 15, tablero);


}
