package it.polimi.ingsw.network;


import it.polimi.ingsw.network.client.SocketClient;
import it.polimi.ingsw.network.message.Ping;

import java.io.IOException;

/**
 * Main of the client app.
 */
public class ClientApp {

    public static void main(String[] args) throws IOException {

        SocketClient client = new SocketClient("server",16847 );
        Ping ping = new Ping();
        client.sendMessage(ping);

        boolean cliParam = false; // default value

        for (String arg : args) {
            if (arg.equals("--cli") || arg.equals("-c")) {
                cliParam = true;
                break;
            }
        }



    }
}
