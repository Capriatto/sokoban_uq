/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author FabianGM
 */
public class CrearArchivoTxt {
    private File archivo;
    private String nombreArchivo;
    private String levelDirectory;
    private String filename;
    private FileWriter escribir;
    private PrintWriter pw;
    private char matriz[][];
    private char array[];
    
    /***
     * Metodo para crear el archivo el cual contendra las letras para llenar el tablero
     * despues de ser cargado
     * @param nombreArchivo
     * @param ruta
     * @param matriz 
     */
    public CrearArchivoTxt(String nombreArchivo,String ruta, char matriz[][]) {
        this.nombreArchivo = nombreArchivo;
        this.matriz=matriz;
        this.array=new char[20]; 
        levelDirectory = System.getProperty("user.dir") + java.io.File.separator + "src/" + ruta + java.io.File.separator;
        filename = levelDirectory + nombreArchivo + ".txt";
        try {
            llenarArchivo(archivo);
            if (ruta.equals("niveles")){
                JOptionPane.showMessageDialog(null, "Se ha creado el nivel con éxito\n Eliga la opción JUGAR en el menú principal \n para poder escoger este nivel.", "CREAR NIVEL", JOptionPane.INFORMATION_MESSAGE);
            }else if (ruta.equals("partidasGuardadas")){
                JOptionPane.showMessageDialog(null, "Se ha guardado la partida con éxito.", "PARTIDA GUARDADA", JOptionPane.INFORMATION_MESSAGE);
            }
            } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "No se pudo crear el nivel\n Regrese al menú principal", "Error", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(CrearArchivoTxt.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /***
     * Metodo para llenar el archivo
     * @param archivo
     * @throws IOException 
     */
    public void llenarArchivo(File archivo) throws IOException{
        archivo=new File(filename);
        escribir=new FileWriter(archivo);
        pw=new PrintWriter(escribir);
        System.out.println("Creo archivo");
            for(int i=0;i<20;i++){
                for(int j=0;j<20;j++){
                 array[j]=matriz[j][i];
                }
                pw.println(array);
            }
             escribir.close();
             pw.close();
    }  
}
