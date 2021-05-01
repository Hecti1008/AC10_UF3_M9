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

    public static void main(String[] args) throws Exception {
        Scanner teclat = new Scanner(System.in);

        int numPort = 60000;
        ServerSocket servidor = new ServerSocket(numPort);

        //Variable per comptar els clients               
        int numClients;

        System.out.println("Numero dels clients que vols? ");
        numClients = teclat.nextInt();

        Socket[] clientConnectat = new Socket[numClients];
        servidorFils[] arrayRunnable = new servidorFils[numClients];
        Thread[] arrayThread = new Thread[numClients];

        //El Servidor agafa els clients que li posem per teclat
        for (int i = 0; i < arrayRunnable.length; i++) {

            boolean noFunciona = true;

            Socket sortidaClient = null;
            try {
                sortidaClient = servidor.accept();
            } catch (SocketException e) {
                noFunciona = false;
            }

            boolean stop = false;

            for (int j = 0; j < clientConnectat.length; j++) {

                if (clientConnectat[i] == null && stop == false) {
                    clientConnectat[i] = sortidaClient;
                    stop = true;
                }

            }

            if (noFunciona) {
                System.out.println("Esperant una conexio... ");
                arrayRunnable[i] = new servidorFils(servidor, clientConnectat, sortidaClient);
                arrayThread[i] = new Thread((Runnable) arrayRunnable[i]);
                arrayThread[i].start();
            }
        }
    }
}
