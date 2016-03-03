package slogo.view;

import java.io.File;
import java.io.FilenameFilter;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;

public class ResourceFilter {
	
	private Collection<String> list;

	public ResourceFilter(File file, FilenameFilter filter) {
		
		this.list = new ArrayList<String>();
		
		makeFilteredList(file, filter);
		
	}
	
	private void makeFilteredList(File file, FilenameFilter filter) {
		
		String[] items = file.list(filter);
		
		for(String item : items) {
			this.list.add(item);
		}
		
	}
	
	Collection<String> getFilteredList() {
		return this.list;
	}
	
}
