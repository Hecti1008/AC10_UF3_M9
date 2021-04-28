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
import java.util.Scanner;

/**
 *
 * @author Alumne
 */
public class server {
    public static void main (String[] args) throws Exception {
                Scanner teclat = new Scanner(System.in);
        
		int numPort = 60000;
		ServerSocket servidor = new ServerSocket(numPort);
                
                //Variable per comptar els clients
                int clients = 0;
                int numClients;
                Thread thread;
                servidorFils fil;
                
                System.out.println("Numero dels clients que vols? ");
                numClients = teclat.nextInt();
                
                    //El Servidor agafa els clients que li posem per teclat
                    while ( clients < numClients) {
                        try {
                            System.out.println("Esperant connexió... ");
                            Socket clientConnectat = servidor.accept();
                            clients++;
                            
                            fil = new servidorFils(servidor, clientConnectat);
                            thread = new Thread(fil);
                            thread.start();
                        }catch (SocketException e){
                                System.out.println("Error");
                        }catch (IOException s) {
                                s.printStackTrace();
                            }
                    }   
            servidor.close();
            teclat.close();
	}
}
