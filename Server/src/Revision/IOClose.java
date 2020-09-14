package Revision;

import java.io.Closeable;
import java.io.IOException;

public class IOClose {
    public static void closeAll(Closeable... c){
        for(Closeable clo:c){
            if(clo!=null){
                try {
                    clo.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
