package it.polimi.ingsw.network;


import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.client.SocketClient;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.network.message.clientmsg.JoinGameMessage;
import it.polimi.ingsw.network.message.clientmsg.LoginMessage;
import it.polimi.ingsw.network.message.clientmsg.NewGameMessage;

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
        System.out.flush();

        switch (choice){
            case 1 -> {
                System.out.flush();
                System.out.println("Insert number of players: ");
                System.out.printf("> ");
            int maxPlayers = scanner.nextInt();

            System.out.println("Insert \n[1] for expert mode ON \n[2] for expert mode OFF ");
            System.out.printf("> ");
            int expertMode = scanner.nextInt();

            boolean expertModeBoolean = false;

            if(expertMode == 1) expertModeBoolean = true;

            NewGameMessage newGameMessage = new NewGameMessage(username, maxPlayers, expertModeBoolean);

            client.sendMessage(newGameMessage);}

            case 2 -> {
                System.out.println("Insert the id of the game you want to join: ");
                System.out.printf("> ");

                int gameId = scanner.nextInt();

                JoinGameMessage joinGameMessage = new JoinGameMessage(username, gameId);
                client.sendMessage(joinGameMessage);
            }
        }





    }
}
