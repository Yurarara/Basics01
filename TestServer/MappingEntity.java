package TestServer;

import java.util.ArrayList;
import java.util.List;

/**
 * the mapping relationships described in the .xml file.
 * it describes the relationship between name and multiple paths
 * i.e. the following lines.
 *     <servlet-mapping>
 *         <servlet-name>login</servlet-name>
 *         <url-pattern>/log</url-pattern>
 *         <url-pattern>/login</url-pattern>
 *     </servlet-mapping>
 */
public class MappingEntity {
    private String name; //servlet name
    private List<String> urlPattern; //url patterns to be mapped

    public MappingEntity() {
        urlPattern = new ArrayList<String>();
    }

    public MappingEntity(String name, List<String> urlPattern) {
        this.name = name;
        this.urlPattern = urlPattern;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getUrlPattern() {
        return urlPattern;
    }

    public void setUrlPattern(List<String> urlPattern) {
        this.urlPattern = urlPattern;
    }
}
