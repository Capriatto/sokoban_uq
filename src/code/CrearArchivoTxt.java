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
    
    public CrearArchivoTxt(String nombreArchivo,char matriz[][]) {
        this.nombreArchivo = nombreArchivo;
        this.matriz=matriz;
        this.array=new char[20]; 
        levelDirectory = System.getProperty("user.dir") + java.io.File.separator + "src/niveles" + java.io.File.separator;
        filename = levelDirectory + nombreArchivo + ".txt";
        try {
            llenarArchivo(archivo);
        } catch (IOException ex) {
            Logger.getLogger(CrearArchivoTxt.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
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

    }
  
    
}
