/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author FabianGM
 */
public class ArchivoLeer {
    private char [][]matriz;
     public char[][] leerArchivo(String nivel) {
        matriz=new char[20][20];
        String levelDirectory = System.getProperty("user.dir") + java.io.File.separator + "src/niveles" + java.io.File.separator;
        String filename = levelDirectory  + nivel+".txt";

        BufferedReader in;
        try {
            in = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException e) {
            System.out.println("No pudimos encontrar el archivo especificado como  \"" + filename + "\".");
            return null;
        }

        try {
            for (int i = 0; i <20; i++) {
                
                for (int j = 0; j <20; j++) {
                    char letra=(char) in.read();
                    matriz[j][i]=letra;
                }
                in.readLine();
            }

        } catch (IOException e) {
            System.out.println("File improperly formatted, quitting");
            return null;
        }
        return matriz;
    }

    public String[] leerFicheros() {
        String levelDirectory = System.getProperty("user.dir") + java.io.File.separator + "src/niveles" + java.io.File.separator;
        File fichero = new File(levelDirectory);
        String[] f = null;
        if (fichero.exists()) {
            
            File[] ficheros = fichero.listFiles();
            f=new String[ficheros.length];
            for (int x = 0; x < ficheros.length; x++) {
                f[x]=ficheros[x].getName().replace(".txt", "").toUpperCase();
                
            }
        }
        return f;
    }

}
