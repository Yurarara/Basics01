package TestServer;

import Servlet.Servlet;
import Util.IOCloseUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This is the class for starting up and shutting down service.
 */
public class Server {
    private ServerSocket server;
    private Boolean isShutDown = false;

    public static void main(String[] args) {
        Server s= new Server();
        s.start(2434);
    }
    public void start(int port){
        try {
            server = new ServerSocket(port);
            receive();

        } catch (IOException e) {
            e.printStackTrace();
            isShutDown = true;
        }

    }

    private void receive() { //a method for receiving requests
        while(!isShutDown){
            try {
                Socket monitor = server.accept();
                Dispatcher dispatcher = new Dispatcher(monitor);
                new Thread(dispatcher).start();
            } catch (IOException e) {
                this.shut();
            }
        }
    }
    public void shut(){
        isShutDown = true;
        IOCloseUtil.closeAll(server);
    }
}
