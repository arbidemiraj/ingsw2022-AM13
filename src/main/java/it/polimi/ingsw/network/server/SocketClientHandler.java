package it.polimi.ingsw.network.server;

import it.polimi.ingsw.model.exceptions.DuplicateUsernameException;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.network.message.clientmsg.NewGameMessage;
import it.polimi.ingsw.network.message.servermsg.ChooseMessage;
import it.polimi.ingsw.network.message.servermsg.ErrorMessage;
import it.polimi.ingsw.network.message.servermsg.SuccessMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class SocketClientHandler implements ClientHandler, Runnable {
    private final Socket client;
    private final SocketServer socketServer;

    private boolean connected;

    private final Object inputLock;
    private final Object outputLock;

    private ObjectOutputStream output;
    private ObjectInputStream input;


    public SocketClientHandler(SocketServer socketServer, Socket client) {
        this.socketServer = socketServer;
        this.client = client;
        this.connected = true;

        this.inputLock = new Object();
        this.outputLock = new Object();

        try {
            this.output = new ObjectOutputStream(client.getOutputStream());
            this.input = new ObjectInputStream(client.getInputStream());
        } catch (IOException e) {
            Server.LOGGER.severe(e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            handleClientConnection();
        } catch (IOException e) {
            Server.LOGGER.severe("Client " + client.getInetAddress() + " connection dropped.");
            disconnect();
        }
    }

    private void handleClientConnection() throws IOException {
        Server.LOGGER.info("Client connected from " + client.getInetAddress());

        try {
            while (!Thread.currentThread().isInterrupted()) {
                synchronized (inputLock) {
                    Message message = (Message) input.readObject();
                    SuccessMessage successMessage = new SuccessMessage();

                    if (message != null && message.getMessageType() != MessageType.PING) {
                        if (message.getMessageType() == MessageType.LOGIN_REQUEST) {
                            try {
                                socketServer.addPlayer(message.getUsername(), this);
                                sendMessage(successMessage);
                            } catch (DuplicateUsernameException e) {
                                ErrorMessage errorMessage = new ErrorMessage(message.getUsername(), e.getError());
                                sendMessage(errorMessage);
                            }


                            ChooseMessage chooseMessage = new ChooseMessage(message.getUsername());
                            Server.LOGGER.info(() -> "New user -> username: " + message.getUsername());
                            sendMessage(chooseMessage);
                        }
                        else if(message.getMessageType() == MessageType.NEW_GAME){
                            Server.LOGGER.info(() -> "Received: " + message);
                            socketServer.createNewGame((NewGameMessage) message);
                            sendMessage(successMessage);
                        }
                        else {
                            Server.LOGGER.info(() -> "Received: " + message);
                            socketServer.MessageReceived(message);
                        }
                    }
                }
            }
        } catch (ClassCastException | ClassNotFoundException e) {
            Server.LOGGER.severe("Invalid stream from client");
        }
        client.close();
    }


    @Override
    public boolean isConnected() {
        return connected;
    }


    @Override
    public void disconnect() {
        if (connected) {
            try {
                if (!client.isClosed()) {
                    client.close();
                }
            } catch (IOException e) {
                Server.LOGGER.severe(e.getMessage());
            }
            connected = false;
            Thread.currentThread().interrupt();

            socketServer.Disconnect(this);
        }
    }


    @Override
    public void sendMessage(Message message) {
        try {
            synchronized (outputLock) {
                output.writeObject(message);
                output.reset();
                Server.LOGGER.info(() -> "Sent: " + message);
            }
        } catch (IOException e) {
            Server.LOGGER.severe(e.getMessage());
            disconnect();
        }
    }
}