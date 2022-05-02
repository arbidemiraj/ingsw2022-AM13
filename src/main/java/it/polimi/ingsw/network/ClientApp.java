package it.polimi.ingsw.network;


import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.client.SocketClient;
import it.polimi.ingsw.network.message.LoginMessage;
import it.polimi.ingsw.network.message.NewGameMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Main of the client app.
 */
public class ClientApp {

    public static void main(String[] args) throws IOException {

        SocketClient client = new SocketClient("127.0.0.1" ,12345 );

        client.enablePinger(true);
        Client.LOGGER.info("Insert username: ");

        String username;
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));

        username = reader.readLine();

        LoginMessage login = new LoginMessage(username);

        client.sendMessage(login);

        int choice = Integer.parseInt(reader.readLine());

        if(choice == 1){
            Client.LOGGER.info("Insert number of players: ");
            int maxPlayers = Integer.parseInt(reader.readLine());

            Client.LOGGER.info("Insert [1] for expert mode ON [2] else: ");
            int expertMode = Integer.parseInt(reader.readLine());

            boolean expertModeBoolean = false;

            if(expertMode == 1) expertModeBoolean = true;

            NewGameMessage newGameMessage = new NewGameMessage(username, maxPlayers, expertModeBoolean);

            client.sendMessage(newGameMessage);
        }



    }
}
