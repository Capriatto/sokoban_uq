/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

import interfaz.BoardFrame;
import interfaz.ElegirNivelFrame;

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
        ElegirNivelFrame elegir=new ElegirNivelFrame();
        elegir.setVisible(true);
    }
    
}
