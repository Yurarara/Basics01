package TestServer;

import Servlet.Servlet;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * for parsing .xml file.
 * it helps JVM to locate corresponding resources described within the .xml file.
 */
public class WebDom4J {
    /*
    a list to store all servlet entities.
    every entity describes the relationship between a servlet-name and a servlet-class.
     */
    private List<ServletEntity> entityList;
    /*
    a list to store all servlet entities.
    every entity describes the relationship between a servlet-name and multiple url-patterns.
     */
    private List<MappingEntity> mappingList;

    public WebDom4J() {
        this.entityList = new ArrayList<ServletEntity>();
        this.mappingList = new ArrayList<MappingEntity>();
    }

    public WebDom4J(List<ServletEntity> entityList, List<MappingEntity> mappingEntityList) {
        this.entityList = entityList;
        this.mappingList = mappingEntityList;
    }

    public Document getDocument(){
        SAXReader reader = new SAXReader();
        try {
            return reader.read(new File("src/WebInfo/Web.xml"));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void parse(Document doc){
        Element root = doc.getRootElement(); //web-app
        for(Iterator<Element> ite = root.elementIterator("servlet");ite.hasNext();){
            Element subElement = ite.next(); //servlet
            //create an entity for storing information from .xml file
            ServletEntity se = new ServletEntity();
            for(Iterator<Element> subIte = subElement.elementIterator();subIte.hasNext();){
                Element ele = subIte.next(); //could be either servlet-name or servlet-class
                if("servlet-name".equals(ele.getName())){
                    se.setName(ele.getText());
                }else if("servlet-class".equals(ele.getName())){
                    se.setClazz(ele.getText());
                }
            }
            entityList.add(se);
        }
        /*
        for(ServletEntity se:entityList){
        System.out.println(se.getName()+"\t"+se.getClazz());
        }
         */
        for(Iterator<Element> ite = root.elementIterator("servlet-mapping");ite.hasNext();){
            Element subElement = ite.next(); //servlet-mapping
            //create an entity for storing information from .xml file
            MappingEntity me = new MappingEntity();
            for(Iterator<Element> subIte = subElement.elementIterator();subIte.hasNext();){
                Element ele = subIte.next(); //could be either servlet-name or url-pattern
                if("servlet-name".equals(ele.getName())){
                    me.setName(ele.getText());
                }else if("url-pattern".equals(ele.getName())){
                    me.getUrlPattern().add(ele.getText());
                }
            }
            mappingList.add(me);
        }
    }

    public List<ServletEntity> getEntityList() {
        return entityList;
    }

    public void setEntityList(List<ServletEntity> entityList) {
        this.entityList = entityList;
    }

    public List<MappingEntity> getMappingEntityList() {
        return mappingList;
    }

    public void setMappingEntityList(List<MappingEntity> mappingEntityList) {
        this.mappingList = mappingEntityList;
    }
}
