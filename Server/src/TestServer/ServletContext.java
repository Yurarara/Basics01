package TestServer;

import java.util.HashMap;
import java.util.Map;

/**
 * the context among which the entities and mappings are related.
 * it links entity with mapping.
 */
public class ServletContext {
    /*
     * The context by nature consists of some HashMaps connecting servlet entity with
     * mappings.
     * Notice that in a HashMap there can be multiple keys corresponding to one value,
     * but not vice versa.
     */
    private Map<String,String> servlet;//key:servlet-name, value:servlet-class (in Servlet entities)
    private Map<String,String> mapping;//key:url-pattern, value:servlet-name (in Mapping entities)
    public ServletContext(){ //initialisation
        servlet = new HashMap<String,String>();
        mapping = new HashMap<String,String>();
    }
    public Map<String, String> getServlet() {
        return servlet;
    }

    public void setServlet(Map<String, String> servlet) {
        this.servlet = servlet;
    }

    public Map<String, String> getMapping() {
        return mapping;
    }

    public void setMapping(Map<String, String> mapping) {
        this.mapping = mapping;
    }
}
