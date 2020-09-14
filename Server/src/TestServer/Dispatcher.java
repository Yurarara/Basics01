package TestServer;

import Servlet.Servlet;
import Util.IOCloseUtil;

import java.io.IOException;
import java.net.Socket;

/**
 * A dispatcher is a request and its corresponding response (or vice versa).
 * Multi-thread is used to handle large number of simultaneous request and response.
 */
public class Dispatcher implements Runnable{
    private Request req;
    private Response res;
    private Socket monitor;
    private int code = 200; //default 200, to be changed.

    public Dispatcher(Socket monitor) {
        this.monitor = monitor;
        try {
            req = new Request(monitor.getInputStream());
            res = new Response(monitor.getOutputStream());
        } catch (IOException e) {
            code = 500;
            return;
        }
    }

    @Override
    public void run() {
        Servlet servlet = WebApp.getServlet(req.getUrl());
        if(servlet == null){
            code = 404;
        }else{
            try {
                servlet.service(req,res);
            } catch (Exception e) {
                code = 500;
            }
        }
        res.toClient(code);
        IOCloseUtil.closeAll(monitor);
    }
}
