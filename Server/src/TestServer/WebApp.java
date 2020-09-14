package TestServer;

import Servlet.Servlet;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * This class initialises the whole programme, and create different servlet objects according to
 * different requests.
 * Servlet class is used to locate specified class within the package.
 * The class will then be activated via reflects.
 */
public class WebApp {
    private static ServletContext context;
    static{
        //create map objects
        context = new ServletContext();
        Map servlet = context.getServlet();
        Map mapping = context.getMapping();
        //parse xml document and extract entities
        WebDom4J wdj = new WebDom4J();
        wdj.parse(wdj.getDocument());
        List<ServletEntity> entityList = wdj.getEntityList();
        List<MappingEntity> mappingEntityList = wdj.getMappingEntityList();
        //convey information within lists to maps
        for(ServletEntity se:entityList){
            servlet.put(se.getName(),se.getClazz());
        }
        for(MappingEntity me:mappingEntityList){
            List<String> url = me.getUrlPattern();
            for(String s:url){
                mapping.put(s,me.getName());
            }
        }
    }
    public static Servlet getServlet(String url){
        if(url==null||url.trim().equals("")){ //check falsified url
            return null;
        }
        Servlet s = null;

        try {
            //search for servlet name with url
            String servletName = context.getMapping().get(url);
            //search for servlet class with servlet name
            String servletClass = context.getServlet().get(servletName);
            Class<?> clazz= Class.forName(servletClass);
            Servlet servlet = (Servlet)clazz.getDeclaredConstructor().newInstance();
            return servlet;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;

    }
}
