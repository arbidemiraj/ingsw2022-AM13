package it.polimi.ingsw.network;


import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.network.client.CLI;
import it.polimi.ingsw.network.client.SocketClient;
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

            CLI view = new CLI();
            ClientController clientController = new ClientController(view);
            view.addObserver(clientController);
            view.init();
    }
}
