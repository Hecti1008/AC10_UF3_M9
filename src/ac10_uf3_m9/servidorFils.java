/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ac10_uf3_m9;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

/**
 *
 * @author Alumne
 */
public class servidorFils implements Runnable {
    ServerSocket Servidor;
    String cadena = "";
    Socket[] clientConnectat;
    static int clients;
    Socket client;

    public servidorFils(ServerSocket servidor, Socket[] clientConnectat, Socket sortidaClient) {
        this.Servidor = servidor;
        this.clientConnectat = clientConnectat;
        this.clients ++;
        this.client = sortidaClient;
    }
    
        public void run(){
            boolean stop = false;
            while(!stop) {
            
                try {
                                PrintWriter fsortida = null;
                                BufferedReader fentrada = null;

                                System.out.println("Client " + this.clients + " connectat... ");
                                
                                try {
                                //FLUX DE SORTIDA AL CLIENT
                                fsortida = new PrintWriter(this.client.getOutputStream(), true);


                                //FLUX D'ENTRADA DEL CLIENT
                                 fentrada = new BufferedReader(new InputStreamReader(this.client.getInputStream()));

                                //Missatge quan el client conecta amb el servidor
                                fsortida.println("Connexió amb client: " + clients);
                                if ((cadena = fentrada.readLine()) != null) { 
                                    System.out.println("Nom del client " + this.client + ": " + cadena);
                                }
                                
                                }catch (SocketException e) {
                                    stop = true;
                                }
                                
                                while (!stop) {
                                    try {
                                        cadena = fentrada.readLine();                                        
                                    }catch (SocketException e) {
                                        stop = true;
                                    }
                                    if (cadena == null || cadena.equals("")) {
                                        stop = true;
                                    }
                                     if(!stop) {
                                         fsortida.println(cadena);
                                     }
                                     
                                     if (cadena != null) {
                                            for (int i = 0; i < clientConnectat.length; i++) {
                                                if(clientConnectat[i] != null)  {
                                                   fsortida = new PrintWriter(this.clientConnectat[i].getOutputStream(), true);
                                                   fsortida.println(cadena);
                                                    
                                                }
                                            }
                                     }
                                }
                                         System.out.println("Rebent misatge: " + cadena);

                                //TANCAR STREAMS I SOCKETS
                                try {
                                fentrada.close();
                                fsortida.close();                               
                            }catch (NullPointerException e){
                                
                            }
                                this.Servidor.close();
                                this.client.close();
                                     
                               }catch (IOException s) {
                                s.printStackTrace();
                            }
        }
            
        }
}