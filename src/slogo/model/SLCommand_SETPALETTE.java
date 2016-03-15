package slogo.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.Properties;
import slogo.view.OrderedProperties;

public class SLCommand_SETPALETTE extends ReturnValue{
	private static final int LIMIT = 4;
	private static final String palettePropertiesExtension = "./src/resources/general/Palette.properties";
    
    private Properties paletteProperties;
    private File palettePropertiesFile;
	
	@Override
	public void createFunction(IParser p, IMemory m, Map<String, Double> scope) {
		// TODO Auto-generated method stub
		paletteProperties = new OrderedProperties();
        loadPaletteProperties();
        
        palettePropertiesFile = new File (palettePropertiesExtension);
    
        super.parseExpressions(p, m, scope);
    }

    @Override
    public double getValue (IMemory m, Map<String, Double> scope) {
        int index = (int) super.getExpression(0,m,scope);
        int R = (int) super.getExpression(1,m,scope);
        int G = (int) super.getExpression(2,m,scope);
        int B = (int) super.getExpression(3,m,scope);

        String hex = String.format("%02x%02x%02x", R, G, B);
        System.out.println(hex);
        createNewPaletteColor(index, hex);
        return index;
    }

    private void loadPaletteProperties () {
        try {
            paletteProperties.load(this.getClass()
                    .getResourceAsStream("/resources/general/Palette.properties"));
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createNewPaletteColor (int index, String value) {
        try {
            paletteProperties.setProperty(Integer.toString(index), value);
            OutputStream outputStream = new FileOutputStream(palettePropertiesFile);
            try {
                paletteProperties.store(outputStream, "Added new palette color");
            }
            catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    protected int argsNeeded () {
        return LIMIT;
    }

}
