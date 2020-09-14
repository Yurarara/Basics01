package TestServer;
/**
 * Objects under this class aims to extract information readable for JVM from the request information
 * sent by web browser via http protocol.
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

public class Request {
    private static final String CRLF="\r\n";
    private static final String BLANK=" ";
    private InputStream is;
    //request String, including request method, url, parameters, protocol version and content.
    private String requestInfo;
    private String method; //request method
    private String url;
    /*
    key: name of input; value: input content
    The reason for using a List as value is that the name appx corresponds to multiple values.
     */
    private Map<String, List<String>> paraMapValues;

    //constructor, for initialising above parameters
    public Request(){
        paraMapValues = new HashMap<String,List<String>>();
        method = "";
        url = "";
        requestInfo = "";
    }
    public Request(InputStream is){
        this(); //non-para constructor for initialisation
        this.is = is;
        //receive request info
        try {
            byte[] buf = new byte[20480];
            int length = this.is.read(buf);
            requestInfo = new String(buf,0,length);
        } catch (IOException e) {
            return;
        }
        //parse request info and set parameter values accordingly
        this.parseRequestInfo();
    }

    /*
    The following lines are for separating request info into parameters:
    -method
    -url
    -parameters: for GET, the parameter comes with url; for POST, the parameter lies within the content
    */
    private void parseRequestInfo(){
        String  paraString= null; //a String for storing parameter info
        //the first line of request info, containing method, url, protocol version and probably parameters.
        /*
        Debugging!
         */
        System.out.println("The Request Info is: "+getRequestInfo());
        /*
        Debugging!
         */
        String line1 = requestInfo.substring(0,requestInfo.indexOf(CRLF)).trim(); //from 0 to first newline
        method = line1.substring(0,line1.indexOf("/")).trim();
        String urlString = line1.substring(line1.indexOf("/"),line1.indexOf("HTTP/")).trim();
        if("GET".equalsIgnoreCase(method)){ //parameter in first line
            if(urlString.contains("?")){ //determine existence of parameter
                String[] urlArray = urlString.split("\\?");
                this.url = urlArray[0];
                paraString = urlArray[1];
            }else{
                this.url=urlString;
            }
        }else{ //parameter in content (last line of request info)
            this.url=urlString;
            paraString = requestInfo.substring(requestInfo.lastIndexOf(CRLF)).trim();
        }
        if(paraString.equalsIgnoreCase("")){
            return;
        }

        this.parseParameter(paraString);
    }
    private void parseParameter(String para){
        String[] paraArray = para.split("&");
        for(String s:paraArray){
            String[] paraPair = s.split("=");
            if(paraPair.length==1){ //no value, only name
                paraPair = Arrays.copyOf(paraPair,2);
                paraPair[1] = null; //assign null to value
            }
            //store name and corresponding value into Map
            String key = paraPair[0].trim();
            String value = paraPair[1]==null?null:decode(paraPair[1].trim(),"UTF-8");
            if(!paraMapValues.containsKey(key)) { //check if key already exists
                paraMapValues.put(key, new ArrayList<String>()); //if not, create the key
            }
            List<String> values = paraMapValues.get(key); //locate corresponding list with key
            values.add(value);
        }
    }

    /*
    The following lines are for extracting String from Map for later use.
    As a key can correspond to one or multiple values, it's reasonable to write two methods.
     */
    public String[] getValues(String key){
        List<String> values = paraMapValues.get(key);
        if(values==null){
            return null;
        }else{
            return values.toArray(new String[0]);
        }
    }
    public String getValue(String key){
        String[] values = this.getValues(key);
        if(values == null){
            return null;
        }else{
            return values[0];
        }
    }

    /*
    The following lines aims to decode the encoded parts within the parameters.
    For instance, Chinese characters are encoded by the http protocol.
     */
    private String decode(String str, String code){
        try {
            return URLDecoder.decode(str, code);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getUrl() {
        return url;
    }
    public String getRequestInfo(){
        return requestInfo;
    }

}
