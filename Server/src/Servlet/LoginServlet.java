package Servlet;

import TestServer.Request;
import TestServer.Response;

public class LoginServlet extends Servlet{
    @Override
    public void forGet(Request req, Response rep) throws Exception {
        String name = req.getValue("uname");
        String pw = req.getValue("pw");
        if(this.login(name,pw)){
            rep.println(name+" Successfully Logged In!");
        }else{
            rep.println("Wrong Username or Password!");
        }
    }

    @Override
    public void forPost(Request req, Response rep) throws Exception {

    }
    private boolean login(String name, String pw){
        if("araumi".equals(name)&&"114514".equals(pw)){
            return true;
        }else{
            return false;
        }
    }
}
