package Util;

import java.io.Closeable;
import java.io.IOException;

/**
 * A utility class specifically for closing streams and sockets.
 */
public class IOCloseUtil {
    public static void closeAll(Closeable... c){
        for(Closeable close:c){
            if(close!=null){
                try {
                    close.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
