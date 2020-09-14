package TestServer;

import Util.IOCloseUtil;

import java.io.*;

public class Response {
    private static final String CRLF="\r\n";
    private static final String BLANK=" ";
    private StringBuilder headInfo; //response head
    private StringBuilder content; //response content
    private int length; //length of content
    private BufferedWriter bw;

    public Response(){
        headInfo = new StringBuilder();
        content = new StringBuilder();
    }
    public Response(OutputStream os){
        this();
        try {
            bw = new BufferedWriter(new OutputStreamWriter(os,"utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            headInfo=null;
        }
    }

    /*
    The following lines are for creating respond content.
     */
    public Response print(String info){ //add 'info' to content without newline
        content.append(info);
        try {
            length+=info.getBytes("utf-8").length;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return this;
    }
    public Response println(String info){ //add 'info' to content with newline
        content.append(info).append(CRLF);
        try {
            length+=(info+CRLF).getBytes("utf-8").length;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return this;
    }

    /*
    The following lines are for creating respond head.
    Although content may vary, the respond head is constant in most cases.
     */
    private void createHeadInfo(int code){
        headInfo.append("HTTP/1.1").append(BLANK).append(code).append(BLANK);
        /*
        the above line should have continued with the response status.
        however, as the status depends on 'code', determination is needed.
         */
        switch (code){
            case 200:
                headInfo.append("OK");
                break;
            case 500:
                headInfo.append("SERVER ERROR");
                break;
            default:
                headInfo.append("NOT FOUND");
                break;
        }
        headInfo.append(CRLF);
        headInfo.append("Content-Type:text/html;charset=utf-8").append(CRLF);
        headInfo.append("Content-Length:"+length).append(CRLF);
        headInfo.append(CRLF);
    }
    public void toClient(int code){
        if(headInfo==null){ //if no head info, return error
            code=500;
        }
        this.createHeadInfo(code);
        try {
            bw.write(headInfo.toString());
            bw.write(content.toString());
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        close();
    }
    public void close(){
        IOCloseUtil.closeAll(bw);
    }



}
