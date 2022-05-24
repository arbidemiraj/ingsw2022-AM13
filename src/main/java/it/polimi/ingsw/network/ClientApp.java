package it.polimi.ingsw.network;


import it.polimi.ingsw.network.client.ClientController;
import it.polimi.ingsw.view.cli.CLI;

import java.io.IOException;

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
