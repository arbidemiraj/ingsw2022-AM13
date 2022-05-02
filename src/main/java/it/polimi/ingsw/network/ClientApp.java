package it.polimi.ingsw.network;


import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.client.SocketClient;
import it.polimi.ingsw.network.message.LoginMessage;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;
import it.polimi.ingsw.network.message.NewGameMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Main of the client app.
 */
public class ClientApp {

    public static void main(String[] args) throws IOException {

        SocketClient client = new SocketClient("127.0.0.1" ,12345 );

        client.enablePinger(true);

        Scanner scanner = new Scanner(System.in);

        System.out.println("Insert username: ");
        System.out.printf("> ");
        String username;

        username = scanner.nextLine();

        LoginMessage login = new LoginMessage(username);

        client.sendMessage(login);

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));

        int choice = Integer.parseInt(reader.readLine());

        switch (choice){
            case 1 -> {
                System.out.println("Insert number of players");
            int maxPlayers = scanner.nextInt();

            System.out.println("Insert \n[1] for expert mode ON \n[2] for expert mode OFF: ");
            int expertMode = scanner.nextInt();

            boolean expertModeBoolean = false;

            if(expertMode == 1) expertModeBoolean = true;

            NewGameMessage newGameMessage = new NewGameMessage(username, maxPlayers, expertModeBoolean);

            client.sendMessage(newGameMessage);}


        }

    }
}
