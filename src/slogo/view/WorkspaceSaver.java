package slogo.view;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import generic.Pair;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import slogo.xml.XMLReader;
import slogo.xml.XMLWriter;


// TODO: Ask User to override or not
public class WorkspaceSaver extends CustomDialog {
    private static final String DEFAULT_XML_PATH = "./src/resources/preferences/PreferencesXML.xml";
    private XMLReader xmlReader;
    private Map<String, String> options;
    private XMLWriter xmlWriter;
    
    private int tabIndex;
    private TextField tabName;
    
    private View view;
    
    public WorkspaceSaver (Object context) {
        super(context);
        this.view = (View) view;
        options = new TreeMap<String, String>();

        File xml = null;
        xml = new File(DEFAULT_XML_PATH);
        xmlReader = new XMLReader(xml);
        xmlWriter = new XMLWriter(xml);
        
        initializeTextField();
        initializeOptions();
        makeStage();
    }

    private void initializeOptions () {
        NodeList tabs = xmlReader.getElementsOfName("Tab");
        tabIndex = tabs.getLength();
        System.out.println(tabs);
        for (int i = 0; i < tabs.getLength(); i++) {
            Element tab = (Element) tabs.item(i);
            String name = tab.getAttribute("name");
            String index = tab.getAttribute("index");
            options.put(name, index);
        }
    }
    
    private void initializeTextField(){
        tabName= new TextField();
    }
    
    @Override
    protected Pair<Scene, String> makeStage () {
        HBox buttons = new HBox();
        buttons.setPadding(new Insets(25));
        Button save = buttonFactory("Save",e->{
            saveWorkspace();
            this.close();
        });
        save.setDefaultButton(true);
        Button cancel = buttonFactory("Cancel",e->{
            this.close();
        })  ;
        cancel.setCancelButton(true);
        
        buttons.getChildren().addAll(save,cancel);
        BorderPane root = new BorderPane();
        root.setBottom(buttons);
        root.setCenter(tabName);
        
        Scene myScene = new Scene(root);
        this.setScene(myScene);
        return new Pair<Scene,String>(myScene,"Workspace Saver");
    }

    @Override
    protected void setContext (String string) {
        // TODO Auto-generated method stub
        
    }
    
    private Button buttonFactory(String name, EventHandler<ActionEvent> event){
        Button button = new Button(name);
        button.setOnAction(event);
        return button;
    }
    
    void saveWorkspace(){
        String index = Integer.toString(tabIndex);
        String name = tabName.getText();
        Map<String,String> workspaceCharacteristics = createWorkspaceCharacteristics();
        xmlWriter.addElementWithChildren(index,name,workspaceCharacteristics);
    }
    
    private Map<String,String> createWorkspaceCharacteristics(){
        Map<String,String> workspaceCharacteristics = new HashMap<String,String>();
        
        System.out.println("Language-workspacesaver: "+view.getLanguage());
        workspaceCharacteristics.put("Language", view.getLanguage().getValue());
        workspaceCharacteristics.put("BackgroundColorIndex", Integer.toString(view.getGraphicsWindow().getBackgroundColorIndex()));
        workspaceCharacteristics.put("PenColorIndex", "0");
        workspaceCharacteristics.put("PenWidth", "3");
        workspaceCharacteristics.put("Shape", "12");
        workspaceCharacteristics.put("NumTurtles", Integer.toString(view.getGraphicsWindow().getNumTurtles()));
        //workspaceCharacteristics.put("File","FILENAME");
        return workspaceCharacteristics;
        
    }

}
