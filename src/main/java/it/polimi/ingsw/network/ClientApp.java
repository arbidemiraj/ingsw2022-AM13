package it.polimi.ingsw.network;


import it.polimi.ingsw.network.client.SocketClient;
import it.polimi.ingsw.network.message.Ping;
import it.polimi.ingsw.network.message.SuccessMessage;
import it.polimi.ingsw.network.server.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Main of the client app.
 */
public class ClientApp {

    public static void main(String[] args) throws IOException {

        SocketClient client = new SocketClient("127.0.0.1" ,16847 );
        Ping pingMessage = new Ping();

        client.enablePinger(true);
        System.out.printf("Insert username: ");
        String username;
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));

        username = reader.readLine();

        SuccessMessage successMessage = new SuccessMessage(username);
        client.sendMessage(successMessage);
    }
}
