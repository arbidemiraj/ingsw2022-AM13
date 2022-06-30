package it.polimi.ingsw.network.client;


import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.network.message.servermsg.ErrorMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static it.polimi.ingsw.network.message.ErrorType.CONNECTION_LOST;
import static it.polimi.ingsw.network.message.ErrorType.DISCONNECTION;

/**
* This class represents a socket client implementation.
*/
public class SocketClient extends Client {
    private final Socket socket;
    private final ObjectOutputStream output;
    private final ObjectInputStream input;
    private final ExecutorService readExecutionQueue;
    private final ExecutorService pingExecutionQueue;
    private final ScheduledExecutorService ping;
    private static final int SOCKET_TIMEOUT = 3000;

    public SocketClient(String address, int port) throws IOException {
        this.socket = new Socket();
        this.socket.connect(new InetSocketAddress(address, port));
        this.output = new ObjectOutputStream(socket.getOutputStream());
        this.input = new ObjectInputStream(socket.getInputStream());
        this.readExecutionQueue = Executors.newSingleThreadExecutor();
        this.pingExecutionQueue = Executors.newSingleThreadExecutor();
        this.ping = Executors.newSingleThreadScheduledExecutor();
        socket.setSoTimeout(5000);
    }
        /** This method reads the message sent from the server **/
        @Override
        public void readMessage() {
            readExecutionQueue.execute(() -> {

                while (!readExecutionQueue.isShutdown()) {
                    Message message;

                    try {
                        message = (Message) input.readObject();
                    } catch (IOException | ClassNotFoundException e) {
                        message = new ErrorMessage("Connection lost with the server.", CONNECTION_LOST);
                        disconnect();
                        readExecutionQueue.shutdownNow();
                    }

                    notifyObserver(message);
                }
            });
        }
    /** This method sends message to the server **/
    @Override
    public void sendMessage(Message message) {
        try {
            output.writeObject(message);
            output.reset();
        } catch (IOException e) {
            disconnect();
        }
    }

    /**
     * Disconnect the socket from the server.
     */
    @Override
    public void disconnect() {
        try {
            if (!socket.isClosed()) {
                readExecutionQueue.shutdownNow();
                enablePing(false);
                socket.close();
            }
        } catch (IOException e) {
            ErrorMessage message = new ErrorMessage("you've got disconnected", DISCONNECTION);
            sendMessage(message);
        }
    }

    /**
     * Enable a heartbeat (ping messages) between client and server sockets to keep the connection alive.
     */
    public void enablePing(boolean enabled) {
            if (enabled) {
                ping.scheduleAtFixedRate(() -> ping(), 0, 1000, TimeUnit.MILLISECONDS);
            } else {
                ping.shutdownNow();
            }
    }

    public void ping(){
        sendMessage(new Ping());
    }
}

