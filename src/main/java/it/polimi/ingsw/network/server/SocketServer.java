package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.NewGameMessage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class SocketServer implements Runnable {
    private final Server server;
    private final int port;
    ServerSocket serverSocket;

    public SocketServer(Server server, int port) {
        this.server = server;
        this.port = port;
    }

    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            Server.LOGGER.info(() -> "Socket server started on port " + port + ".");
        } catch (IOException e) {
            Server.LOGGER.severe("Server could not start!");
            return;
        }

        while (!Thread.currentThread().isInterrupted()) {
            try {
                Socket client = serverSocket.accept();

                client.setSoTimeout(5000);

                SocketClientHandler clientHandler = new SocketClientHandler(this, client);
                Thread thread = new Thread(clientHandler, "ss_handler" + client.getInetAddress());
                thread.start();
            } catch (IOException e) {
                Server.LOGGER.severe("Connection dropped");
            }
        }
    }

    public void addPlayer(String username, ClientHandler clientHandler) {
        server.addPlayer(username, clientHandler);
    }


    public void MessageReceived(Message message) {
        server.messageReceived(message);
    }


    public void Disconnect(ClientHandler clientHandler) {
        server.disconnect(clientHandler);
    }

    public void createNewGame(NewGameMessage newGameMessage){
        server.createNewGame(newGameMessage);
    }
}