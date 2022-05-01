package it.polimi.ingsw.network.client;


import it.polimi.ingsw.network.message.ErrorMessage;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Ping;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

    /**
     * This class represents a socket client implementation.
     */
    public class SocketClient extends Client {

        private final Socket socket;

        private final ObjectOutputStream outputStm;
        private final ObjectInputStream inputStm;
        private final ExecutorService readExecutionQueue;
        private final ScheduledExecutorService pinger;

        private static final int SOCKET_TIMEOUT = 10000;

        public SocketClient(String address, int port) throws IOException {
            this.socket = new Socket();
            this.socket.connect(new InetSocketAddress(address, port), SOCKET_TIMEOUT);
            this.outputStm = new ObjectOutputStream(socket.getOutputStream());
            this.inputStm = new ObjectInputStream(socket.getInputStream());
            this.readExecutionQueue = Executors.newSingleThreadExecutor();
            this.pinger = Executors.newSingleThreadScheduledExecutor();
        }

        /**
         * Asynchronously reads a message from the server via socket and notifies the ClientController.
         */
        @Override
        public void readMessage() {
            readExecutionQueue.execute(() -> {

                while (!readExecutionQueue.isShutdown()) {
                    Message message;
                    try {
                        message = (Message) inputStm.readObject();
                        Client.LOGGER.info("Received: " + message);
                    } catch (IOException | ClassNotFoundException e) {
                        message = new ErrorMessage(null, "Connection lost with the server.");
                        disconnect();
                        readExecutionQueue.shutdownNow();
                    }

                }
            });
        }


        @Override
        public void sendMessage(Message message) {
            try {
                outputStm.writeObject(message);
                outputStm.reset();
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
                    enablePinger(false);
                    socket.close();
                }
            } catch (IOException e) {

            }
        }

        /**
         * Enable a heartbeat (ping messages) between client and server sockets to keep the connection alive.

         */
        public void enablePinger(boolean enabled) {
            if (enabled) {
                pinger.scheduleAtFixedRate(() -> sendMessage(new Ping()), 0, 1000, TimeUnit.MILLISECONDS);
            } else {
                pinger.shutdownNow();
            }
        }
    }

