package slogo.view;

import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;

public class TurtlePreferenceTab extends CustomTab{
    private ResourceBundle commandsBundle =  ResourceBundle.getBundle("resources/general/IdsToCommands");
    private Map<Integer,TurtleView> turtles;
    
    private ComboBox<Integer> turtleIdsComboBox;
    private Palette penColor;
    private NumberTextField penWidth;
    private ComboBox<String> penType;
    private SwitchButton pen;
    private TurtlePicker shapePicker;
    private ImageView shapeField;
    
    private GraphicsWindow graphicsWindow;
    
    private StringBuilder internalCommand;
    private InternalEditor internalEditor;
    
    
    private int id;
    
    public TurtlePreferenceTab(GraphicsWindow graphicsWindow,Map<Integer,TurtleView> turtles){
        this.setText("Turtle");;
        this.setClosable(false);
        
        this.graphicsWindow=graphicsWindow;
        this.turtles= turtles;
        id = turtles.keySet().iterator().next();
        
        initializeTurtleComboBox();
        
        pen = new SwitchButton();
        initializePenColor();
        initializePenWidth();
        initializePenType();
        initializeShape();
        
        internalCommand = new StringBuilder();
        internalEditor = InternalEditor.getInstance();
        this.addContent(generateTitle("Turtle Id:"),turtleIdsComboBox,
                        generateTitle("Pen :"), pen,
                        generateTitle("Pen Color:"), penColor, 
                        generateTitle("Pen Size:"), penWidth,
                        generateTitle("Pen Type:"), penType,
                        generateTitle("Turtle Image"),shapeField);
                        
    }
    
    private void initializeTurtleComboBox(){
        turtleIdsComboBox= new ComboBox<Integer>();
        turtleIdsComboBox.getItems().addAll(turtles.keySet());
        turtleIdsComboBox.setOnAction(e -> updateTurtleFields());
    }
    
    private void updateTurtleFields(){
        int id = turtleIdsComboBox.getValue();
        TurtleView turtle = graphicsWindow.getTurtle(id);
        
        pen.setOn(turtle.isPenDown());
        penWidth.setText(turtle.getPenWidth());
        penColor.loadColor(turtle.getPenColorIndex());
        
        shapePicker.loadImage(Integer.toString(turtle.getShapeIndex()));
        shapeField.setImage(shapePicker.loadImage(Integer.toString(turtle.getShapeIndex())).getImage());
    }
    
    private void initializePenColor(){
        penColor = new Palette();
    }
    
    private void initializePenWidth(){
        penWidth = new NumberTextField();
    }
    
    private void initializePenType(){
        penType = new ComboBox<String>();
    }
    
    private void initializeShape(){
        shapePicker = new TurtlePicker(1);
        shapeField = new ImageView();

        shapeField.setFitWidth(200);
        shapeField.setFitHeight(200);

        shapeField.setOnMouseClicked(e -> {
            shapePicker.showAndWait();
            shapeField.setImage(shapePicker.getImage().getImage());;
        });
    }
    
    void buildInternalCommand(){
        internalCommand.append(commandsBundle.getString("Tell")+" "+turtleIdsComboBox.getValue()+" ]\n");
        internalCommand.append(commandsBundle.getString("SetPenColor")+" "+penColor.getValue()+"\n");
        internalCommand.append(commandsBundle.getString("SetPenSize")+" "+penWidth.getText()+"\n");
        internalCommand.append(commandsBundle.getString("SetShape")+" "+shapePicker.getValue()+"\n");
        if (pen.switchOnProperty()){
            internalCommand.append("PenDown\n");
        } else {
            internalCommand.append("PenUp\n");
        }
        
        System.out.println("Execute internal-turtlepreferencetab: \n"+internalCommand);
        internalEditor.setEditorText(internalCommand);
    }
}
