package slogo.view;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

/**
 * This is the menu panel view component located at the top of the
 * view.
 * 
 * @author Aditya Srinivasan, Arjun Desai
 *
 */
public class MenuPanel extends MenuBar {
	
	private static final String DEFAULT_LOCATION = "/resources/bundles/";
	private static final String EXTENSION = "Menu.properties";
	
	Properties menuProperties;
	
	private static final String DEFAULT_LANGUAGE = "English";
	
	private EventHandler<ActionEvent> event;
	
	/**
	 * Initializes the menupanel with an event listener.
	 * @param menuItemEvent
	 */
	public MenuPanel(EventHandler<ActionEvent> menuItemEvent) {
		this.event = menuItemEvent;
		menuProperties = new OrderedProperties();
		setLanguage(DEFAULT_LANGUAGE);

		makeMenus(menuMap(menuItemEvent));
	}
	
	/**
	 * Sets the language of the menu panel.
	 * 
	 * @param language
	 */
	void setLanguage(String language) {
		try {
			menuProperties.load(this.getClass().getResourceAsStream(DEFAULT_LOCATION + language + EXTENSION));
		} catch(Exception e) {
			e.printStackTrace();
		}
		this.getMenus().clear();
		makeMenus(menuMap(this.event));
	}
	
	/**
	 * Returns a map of menu names to menuitems.
	 * @param menuItemEvent
	 * @return
	 */
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
				System.out.println(item);
				items.add(item);
				menus.put(menuBelonging, items);
			}
		}
		return menus;
	}
	
	/**
	 * Makes the menus given a menu map.
	 * @param menuMap
	 */
	private void makeMenus(Map<String, List<MenuItem>> menuMap) {
		for(String menuName : menuMap.keySet()) {
			Menu menu = new Menu(menuName);
			menu.getItems().addAll(menuMap.get(menuName));
			this.getMenus().add(menu);
		}
	}
	
	/**
	 * Finds the menu that a menu item string corresponds to.
	 * @param str
	 * @return
	 */
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
