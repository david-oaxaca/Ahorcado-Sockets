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
import java.net.ServerSocket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;

/**
 *
 * @author tdwda
 */
public class Servidor {
    
    public static void main(String[] args) {
    
        int puerto= 1234;
        
        String [] facil={"casa", "mama", "oso", "coco", "perro"};
        String [] intermedio={"xochimilco", "ferrocarril", "escuela", "burrito", "servidor"};
        String [] dificil={"erase una vez", "ya hay practica", "caminando por la calle", "se hizo el intento", "se acaba el tiempo"};

        
        try{
            System.out.println("Esperando a un jugador...");
            //Inicializamos socket
            ServerSocket server= new ServerSocket(puerto);
            Socket socket = server.accept();
            
            
            //Leemos mensaje del cliente
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            int dificultad = Integer.parseInt(br.readLine());
            
            System.out.println("Conexión establecida con: " + socket.getInetAddress() + ":" + puerto);
            
            System.out.println("El nivel de dificultad seleccionado fue: " + dificultad);
            
            //Escogemos una palabra de acuerdo con el nivel de dificultad
            
            String palabra = "";
            switch(dificultad){
                case 1:
                    palabra = facil[(int)(Math.random() * (5 - 0 + 1) + 0)];
                    break;
                case 2:
                    palabra = intermedio[(int)(Math.random() * (5 - 0 + 1) + 0)];
                    break;
                case 3:
                    palabra = dificil[(int)(Math.random() * (5 - 0 + 1) + 0)];
                    break;    
                default:
                    System.err.println("Se selecciono una dificultad no valida, asi que se enviara el modo facil");
                    palabra = facil[(int)(Math.random() * (5 - 0 + 1) + 0)];
                    break;
            }
            
            //Envia la palabra al cliente
            PrintWriter pw= new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            pw.println(palabra);
            pw.flush();
            
            //Imprime tiempo del jugador
            String puntaje= (String) br.readLine();
            System.out.println(puntaje);
            
            //Cerramos Streams
            pw.close();
            br.close();
            socket.close();
            
        }catch(Exception e) {
            e.printStackTrace();
        }
        
    }
}
