package TestServer;

/**
 * servlet-name and its corresponding entity class.
 * it describes the relationship between name and Servlet class.
 * i.e. the following lines.
 *     <servlet>
 *         <servlet-name>login</servlet-name>
 *         <servlet-class>Servlet.loginServlet</servlet-class><
 *     </servlet>
 */
public class ServletEntity {
    private String name; //servlet name
    private String clazz;

    public ServletEntity() {
    }

    public ServletEntity(String name, String clazz) {
        this.name = name;
        this.clazz = clazz;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }
}
