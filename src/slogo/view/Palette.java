package slogo.view;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;
import slogo.model.SlogoException;

/**
 * A combobox showing available colors to use and their indices.
 * 
 * @author Aditya Srinivasan, Arjun Desai
 *
 */
public class Palette extends ComboBox<HColorAlias>{
    
    private static final String palettePropertiesExtension = "resources/general/Palette";
	private static final String palettePropertiesFileExtension = "./src/resources/general/Palette.properties";
	private static final String PALETTE_PROP_LOCATION = "/resources/general/Palette.properties";

    private Properties paletteProperties;
    private ResourceBundle paletteBundle;
    private File palettePropertiesFile;

    /**
     * Initializes the palette.
     */
    public Palette (){
    	
        paletteProperties = new OrderedProperties();
        loadPaletteProperties();
        
        palettePropertiesFile = new File (palettePropertiesFileExtension);
        paletteBundle = ResourceBundle.getBundle(palettePropertiesExtension);
        
        initializeComboBox();

    }
    
    /**
     * Loads the palette properties file.
     */
    private void loadPaletteProperties(){
        try {
            paletteProperties.load(this.getClass().getResourceAsStream(PALETTE_PROP_LOCATION));
        } catch (Exception e) {
            throw new SlogoException();
        }
    }
    
    /**
     * Initializes the combobox with the olors in numerical order.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	private void initializeComboBox(){
		Comparator sortStringByIntegerValue = new Comparator(){
            @Override
            public int compare (Object o1, Object o2) {
                return Integer.parseInt(o1.toString())-Integer.parseInt(o2.toString());
            }
        };
        
        List<String> colors = new ArrayList(paletteProperties.keySet());
        Collections.sort(colors, sortStringByIntegerValue);
        
        for(String index : colors){
            String color = paletteBundle.getString(index);
            HColorAlias colorBox = new HColorAlias(Color.valueOf(color), index);
            this.getItems().add(colorBox);
        }

    }
    
    /**
     * Loads a color given the index of the color.
     * @param index
     */
    void loadColor(String index){
        for (HColorAlias item : this.getItems()){
            if (index.equals(item.toString())){
                this.setValue(item);
                break;
            }
        }
    }

}
