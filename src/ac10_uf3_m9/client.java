/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ac10_uf3_m9;

import java.io.PrintWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.net.Socket;
import java.net.SocketException;

/**
 *
 * @author Alumne
 */
public class client implements Runnable {

    /**
     * @param args the command line arguments
     */
    private static Socket client;
    private static boolean follow = true;

    public client(Socket client) {
        this.client = client;
    }

    public static void main(String[] args) throws Exception {

        String host = "localhost";
        int port = 60000;//Port remot
        client = new Socket(host, port);

        //FLUX fDE SORTIDA AL SERVIDOR
        PrintWriter fsortida = new PrintWriter(client.getOutputStream(), true);

        //FLUX D'ENTRADA AL SERVIDOR
        BufferedReader fentrada = new BufferedReader(new InputStreamReader(client.getInputStream()));

        //FLUX PER A ENTRADA ESTÀNDARD
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        String name = "";
        System.out.println("Introdueix el teu nom: ");
        //Lectura teclat
        name = in.readLine();

        while (name == null || name.equals("")) {

            System.out.println("Error al introduir el nom, torna a intentar: ");
            name = in.readLine();

        }

        Runnable run = new client(client);
        Thread clientEnter = new Thread(run);

        fsortida.println(name);
        clientEnter.start();

        String cadena, eco = "";
        System.out.println("Missatge a enviar: ");
        cadena = in.readLine();

        while (cadena != null && (!cadena.equals("_exit"))) {

            fsortida.println(name + ": " + cadena);
            eco = fentrada.readLine();
            System.out.println(" =>Eco: " + eco);
            cadena = in.readLine();

            if (cadena == null || cadena.equals("")) {
                fsortida.print(cadena);
            }
        }

        notFollow();
        fsortida.close();
        fentrada.close();
        System.out.println("Finalització de l'enviament...");
        in.close();
        client.close();
    }

    public static void notFollow() {
        follow = false;
    }

    @Override
    public void run() {
        while (follow == true) {
            try {

                BufferedReader fentrada = new BufferedReader(new InputStreamReader(client.getInputStream()));
                String enter = fentrada.readLine();

                if (enter == null || enter.equals("_exit") || enter.equals("null")) {
                    notFollow();
                } else {
                    System.out.println(enter);
                }
            } catch (SocketException e) {

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
