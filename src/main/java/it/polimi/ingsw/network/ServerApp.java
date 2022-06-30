package it.polimi.ingsw.network;

import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.network.server.SocketServer;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Main of the server app.
 */
public class ServerApp {
    private static final Scanner input = new Scanner(System.in);
    private static final PrintStream output =  System.out;
    
    public static void main(String[] args) {
        int defaultPort = 12345;
        int serverPort = 0;
        boolean isValid = false;
        
        do{
            output.print("Enter the server port [ Default = " + defaultPort + "] : ");
            String port = input.nextLine();

            if(port.equals("")){
                serverPort = defaultPort;
                isValid = true;
            }
            else if(Integer.parseInt(port) >= 1 && Integer.parseInt(port) <= 65535){
                serverPort = Integer.parseInt(port);
                
                isValid = true;
            }
            else{
                output.println("Invalid port");
                isValid = false;
            }

        }while(!isValid);

        Server server = new Server();

        SocketServer socketServer = new SocketServer(server, serverPort);
        socketServer.run();
    }
}