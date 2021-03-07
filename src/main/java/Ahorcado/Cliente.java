/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ahorcado;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.InetAddress;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
/**
 *
 * @author David Madrigal Buendía
 * @author David Arturo Oxaca Pérez
 */
public class Cliente {
    public static void main(String[] args) {
        String dir= "localhost";
        int puerto= 1234;
        
        try {
            Socket cl= new Socket(dir, puerto);
            System.out.println("Conexión establecida con: " + dir + ":" + puerto);
            BufferedReader teclado= new BufferedReader(new InputStreamReader(System.in));
            BufferedReader conexion_lectura= new BufferedReader(new InputStreamReader(cl.getInputStream())); 
            
            
            System.out.println("Ingresa la dificultad:");
            System.out.println("1 - Fácil");
            System.out.println("2 - Medio");
            System.out.println("3 - Dífícil");
            System.out.println("Escribe el numero de la opcion:");
            String opcion= (String) teclado.readLine();
            
            PrintWriter conexion_escritura= new PrintWriter(new OutputStreamWriter(cl.getOutputStream()));
            conexion_escritura.println(opcion);
            conexion_escritura.flush();
            
            String palabra= (String) conexion_lectura.readLine();
            
            long tiempo_inicio= System.currentTimeMillis();
            System.out.println("Palabra a encontrar: ");
            String palabra_aux = "";
            palabra_aux = colocarPalabra(palabra);
            System.out.println(palabra_aux + "\n");
            
            boolean palabra_encontrada= false;
            String [] ahorcado = {"______\n|     |\n|     \n|    \n|    \n|\n|\n|\n--------", 
            "______\n|     |\n|     O\n|      \n|     \n|\n|\n|\n--------",
            "______\n|     |\n|     O\n|    |  \n|     \n|\n|\n|\n--------",
            "______\n|     |\n|     O\n|    /|  \n|     \n|\n|\n|\n--------",
            "______\n|     |\n|     O\n|    /|\\  \n|     \n|\n|\n|\n--------",
            "______\n|     |\n|     O\n|    /|\\  \n|    / \n|\n|\n|\n--------", 
            "______\n|     |\n|     O\n|    /|\\  \n|    / \\\n|\n|\n|\n--------"};
            
            int intentos= 0;
            do {
                System.out.println(ahorcado[intentos]);
                System.out.println("Ingresa un carácter:");
                char caracter= teclado.readLine().charAt(0);
                if(palabra.contains(caracter + "")) {
                    System.out.println("La palabra contiene: " + caracter);
                    palabra_aux = colocarPalabra(palabra, palabra_aux, caracter);
                    System.out.println("Palabra a encontrar: " + palabra_aux + "\n");
                }else {
                    System.out.println("La palabra no contiene: " + caracter + ", te quedan: " + (5 - intentos) + " intentos");
                    //palabra_aux = colocarPalabra(palabra_aux, caracter);
                    System.out.println("Palabra a encontrar: " + palabra_aux + "\n");
                    intentos++;
                }
                
                if(!palabra_aux.contains("-")) palabra_encontrada = true;
                
                if(intentos > 5) {
                    System.out.println("Perdiste, se te acabaron los intentos.\n");
                    System.out.println(ahorcado[intentos]);
                    break;
                }
            }while(!palabra_encontrada);
            long tiempo_final= System.currentTimeMillis();
            
            tiempo_inicio= tiempo_inicio/1000;
            tiempo_final= tiempo_final/1000;
            long tiempo_total= tiempo_final - tiempo_inicio; //segundos
            
            if(palabra_encontrada) {
                System.out.println("Ganaste.");
                
                if(tiempo_total > 500)
                    System.out.println("Puntaje: " + 0);
                else
                    System.out.println("Puntaje: " + (500 - tiempo_total));
            }else{
                System.out.println("Puntaje: " + 0);
            }
            
            
            
            String puntuacion= "Tiempo (segundos): " + tiempo_total;
            
            conexion_escritura.println(puntuacion);
            conexion_escritura.flush();
            
            conexion_escritura.close();
            conexion_lectura.close();
            cl.close();
            
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public static String colocarPalabra(String palabra) {
        int tamanio= palabra.length();
        String aux = "";
        for(int i= 0; i < tamanio; i++) {
            aux += "-";
        }
        System.out.println("");
        return aux;
    }
    
    public static String colocarPalabra(String palabra, String palabra_aux, char caracter) {
        int tamanio = palabra_aux.length();
        String aux = "";
        for(int i= 0; i < tamanio; i++) {
            if(palabra.charAt(i) == caracter) {
                aux += caracter + "";
            }else{
                aux += palabra_aux.charAt(i) + "";
            }
        }
        return aux;
    }
}

/*
______
|     |
|     
|    
|    
|
|
|
--------

______
|     |
|     O
|    
|    
|
|
|
--------
______
|     |
|     O
|     |  
|    
|
|
|
--------
______
|     |
|     O
|    /|  
|    
|
|
|
--------
______
|     |
|     O
|    /|\  
|    
|
|
|
--------
______
|     |
|     O
|    /|\  
|    /
|
|
|
--------

______\n|     |\n|     O\n|    /|\  \n|    / \\n|\n|\n|\n--------

*/