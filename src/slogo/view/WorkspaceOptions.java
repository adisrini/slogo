package slogo.view;

import java.io.File;
import java.util.Map;
import java.util.TreeMap;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import javafx.scene.control.ComboBox;
import slogo.xml.XMLReader;

public class WorkspaceOptions extends ComboBox<String>{
    private static final String DEFAULT_XML_PATH = "./src/resources/preferences/PreferencesXML.xml";
    private XMLReader xmlReader;
    private Map<String,String> options;
    
    public WorkspaceOptions(){
        options = new TreeMap<String,String>();
        
        File xml = null;
        xml = new File(DEFAULT_XML_PATH);
        xmlReader = new XMLReader(xml);
        
        initializeOptions();
    }
    
    private void initializeOptions(){
       NodeList tabs = xmlReader.getElementsOfName("Tab");
       System.out.println(tabs);
       for (int i=0;i<tabs.getLength();i++){
           Element tab = (Element) tabs.item(i);
           String name = tab.getAttribute("name");
           String index = tab.getAttribute("index");
           options.put(name,index);
       }
       
       this.getItems().addAll(options.keySet());
    }
    
    String getIndex(){
        return options.get(this.getValue());
    }
}
