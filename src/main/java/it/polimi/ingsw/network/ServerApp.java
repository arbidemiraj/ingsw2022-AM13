package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.network.server.SocketServer;

/**
 * Main of the server app.
 */
public class ServerApp {

    public static void main(String[] args) {
        int serverPort = 16847; // default value

        Game game = new Game(2, false);
        Controller controller = new Controller(game);
        Server server = new Server(controller);

        SocketServer socketServer = new SocketServer(server, serverPort);
        Thread thread = new Thread(socketServer, "socketserver_");
        thread.start();
    }
}