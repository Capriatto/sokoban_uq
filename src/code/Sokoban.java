/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

import interfaz.BoardFrame;

/**
 *
 * @author FabianGM
 */
public class Sokoban {
    public static Utilidades utilidades= new Utilidades();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        BoardFrame board=new BoardFrame();
        board.setVisible(true);
        System.out.println (utilidades.leerArchivo("archivo.txt"));
    }
    
}
