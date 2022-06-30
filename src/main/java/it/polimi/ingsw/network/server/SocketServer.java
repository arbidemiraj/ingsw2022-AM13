package it.polimi.ingsw.network.server;

import it.polimi.ingsw.model.exceptions.DuplicateUsernameException;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.clientmsg.NewGameMessage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Handles the server socket
 */
public class SocketServer {
    private final Server server;
    private final int port;
    ServerSocket serverSocket;

    public SocketServer(Server server, int port) {
        this.server = server;
        this.port = port;
    }

    /**
     * Thread running and accepting connection on this socket
     */
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

    /**
     * Adds a client
     * @param username to add to the server
     * @param clientHandler that has to handle it
     * @throws DuplicateUsernameException
     */
    public void addClient(String username, ClientHandler clientHandler) throws DuplicateUsernameException {
        server.addClient(username, clientHandler);
    }


    /**
     * Handles the received message and sends it to the server
     * @param message the received message
     */
    public void MessageReceived(Message message) {
        server.messageReceived(message);
    }


    /**
     * Handles the disconnection of an user
     * @param clientHandler the clientHandler that disconnected
     */
    public void disconnect(ClientHandler clientHandler) {
        server.disconnect(clientHandler);
    }

    /**
     *
     * @param newGameMessage sent to the server
     */
    public void createNewGame(NewGameMessage newGameMessage){
        server.createNewGame(newGameMessage);
    }

    public Message getLobby(){
        return server.getLobby();
    }
}