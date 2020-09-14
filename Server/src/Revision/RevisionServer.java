package Revision;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.cert.CRL;

public class RevisionServer {
    public static void main(String[] args) {
        String CRLF = "\r\n"; //new line
        String BLANK = " "; //space
        InputStream is = null;
        ServerSocket server = null;
        Socket monitor = null;
        try {
            server = new ServerSocket(2434);
            monitor = server.accept();
            is = monitor.getInputStream();
            byte[] buf = new byte[20480];
            int length = is.read(buf);
            System.out.println(new String(buf,0,length));
            //response
            StringBuilder sbHead = new StringBuilder();
            StringBuilder sbContent = new StringBuilder(); //
            sbContent.append("<html><head><title> Response Result: </title></head>");
            sbContent.append("<body>Login Successful</body></html>");
            //1. assemble response head
            sbHead.append("HTTP/1.1"+"").append(BLANK).append(200).append(BLANK).append("END");
            sbHead.append(CRLF);
            sbHead.append("Content-Type: text/html;charset=utf-8");
            sbHead.append(CRLF);
            sbHead.append("Content-Length: ").append(sbContent.toString().length()).append(CRLF);
            sbHead.append(CRLF); //a blank line separating response head and body. necessary.
            sbHead.append(sbContent);
            //2.
            //3. build IO stream to send response
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(monitor.getOutputStream()));
            bw.write(sbHead.toString());
            bw.flush();
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            IOClose.closeAll(is,server,monitor);
        }
    }


}
