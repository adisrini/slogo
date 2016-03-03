package slogo.view;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class MenuPanel extends MenuBar {
	
	private static final String DEFAULT_LOCATION = "/resources/bundles/";
	private static final String EXTENSION = "Menu.properties";
	
	Properties menuProperties;
	
	private String language;
	private static final String DEFAULT_LANGUAGE = "English";
	
	private EventHandler<ActionEvent> event;
	
	public MenuPanel(EventHandler<ActionEvent> menuItemEvent) {
		this.event = menuItemEvent;
		menuProperties = new OrderedProperties();
		setLanguage(DEFAULT_LANGUAGE);

		makeMenus(menuMap(menuItemEvent));
	}
	
	public void setLanguage(String language) {
		try {
			menuProperties.load(this.getClass().getResourceAsStream(DEFAULT_LOCATION + language + EXTENSION));
		} catch(Exception e) {
			e.printStackTrace();
		}
		this.getMenus().clear();
		makeMenus(menuMap(this.event));
	}
	
	private Map<String, List<MenuItem>> menuMap(EventHandler<ActionEvent> menuItemEvent) {
		Map<String, List<MenuItem>> menus = new LinkedHashMap<String, List<MenuItem>>();
		for(Enumeration<?> enumer = menuProperties.propertyNames(); enumer.hasMoreElements();) {
			String key = (String) enumer.nextElement();
			if(key.endsWith("Menu")) {
				menus.put(menuProperties.getProperty(key), new ArrayList<MenuItem>());
			} else if(key.endsWith("Item")) {
				String menuBelonging = findMenu(key.replace("Item", ""));
				List<MenuItem> items = menus.get(menuBelonging);
				MenuItem item = new MenuItem(menuProperties.getProperty(key));
				item.setId(key);
				item.setOnAction(menuItemEvent);
				items.add(item);
				menus.put(menuBelonging, items);
			}
		}
		return menus;
	}
	
	private void makeMenus(Map<String, List<MenuItem>> menuMap) {
		for(String menuName : menuMap.keySet()) {
			Menu menu = new Menu(menuName);
			menu.getItems().addAll(menuMap.get(menuName));
			this.getMenus().add(menu);
		}
	}
	
	private String findMenu(String str) {    
		int start = 0;
	    for(int i=str.length()-1; i>=0; i--) {
	        if(Character.isUpperCase(str.charAt(i))) {
	            start = i;
	            break;
	        }
	    }
	    String menu = str.substring(start);
	    for(Enumeration<?> enumer = menuProperties.propertyNames(); enumer.hasMoreElements();) {
	    	String key = (String) enumer.nextElement();
	    	if(key.startsWith(menu)) {
	    		return menuProperties.getProperty(key);
	    	}
	    }
	    return null;
	}
	
}
