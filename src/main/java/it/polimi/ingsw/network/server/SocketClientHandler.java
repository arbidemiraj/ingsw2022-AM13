package it.polimi.ingsw.network.server;

import it.polimi.ingsw.model.exceptions.DuplicateUsernameException;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.network.message.clientmsg.CreateOrJoinAnswer;
import it.polimi.ingsw.network.message.servermsg.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;


public class SocketClientHandler implements ClientHandler, Runnable {
    private final Socket client;
    private final SocketServer socketServer;

    private boolean connected;

    private int gameId;

    private final Object inputLock;
    private final Object outputLock;

    private ObjectOutputStream output;
    private ObjectInputStream input;

    private final ScheduledExecutorService ping;

    public SocketClientHandler(SocketServer socketServer, Socket client) {
        this.socketServer = socketServer;
        this.client = client;
        this.connected = true;
        this.inputLock = new Object();
        this.outputLock = new Object();
        this.ping = Executors.newSingleThreadScheduledExecutor();

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

                    Message message = (Message) input.readObject();
                    SuccessMessage successMessage = new SuccessMessage(SuccessType.CONNECTED);

                    if(message != null && message.getMessageType() == MessageType.PING){
                        sendMessage(new Ping());
                    }

                    if (message != null && message.getMessageType() != MessageType.PING) {
                        if (message.getMessageType() == MessageType.LOGIN_REQUEST) {
                            try {
                                socketServer.addClient(message.getUsername(), this);
                                sendMessage(new ChooseMessage());
                            } catch (DuplicateUsernameException e) {
                                sendMessage(new ErrorMessage(e.getError(), ErrorType.DUPLICATE_USERNAME));
                            }
                            Server.LOGGER.info(() -> "New user -> username: " + message.getUsername());
                        }
                        else if (message.getMessageType() == MessageType.CREATE_JOIN_ANSWER){
                            CreateOrJoinAnswer createOrJoinAnswer = (CreateOrJoinAnswer) message;
                            if(createOrJoinAnswer.getChoice() == 2){
                                sendMessage(new LobbyMessage(socketServer.getLobby().toString()));
                            }

                            if(createOrJoinAnswer.getChoice() == 1){
                                sendMessage(new AskGameSettings());
                            }
                        }
                        else if (message.getMessageType() == MessageType.DISCONNECTED){
                            disconnect();
                            client.close();
                        }
                        else {
                            Server.LOGGER.info(() -> "Received: " + message);
                            socketServer.MessageReceived(message);
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

            socketServer.disconnect(this);
        }
    }

    @Override
    public void sendMessage(Message message) {
        try {
            synchronized (outputLock) {
                output.writeObject(message);
                output.reset();
                if(message != null && message.getMessageType() != MessageType.PING) {
                    Server.LOGGER.info(() -> "Sent: " + message);
                }
            }
        } catch (IOException e) {
            Server.LOGGER.severe(e.getMessage());
            disconnect();
        }
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }
}