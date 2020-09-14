package Servlet;

import TestServer.Request;
import TestServer.Response;

/**
 * Super class of all Servlet requests.
 */
public abstract class Servlet {
    public void service(Request req, Response rep) throws Exception{
        this.forGet(req, rep);
        this.forPost(req, rep);
    }
    public abstract void forGet(Request req, Response rep) throws Exception;
    public abstract void forPost(Request req, Response rep) throws Exception;

}
