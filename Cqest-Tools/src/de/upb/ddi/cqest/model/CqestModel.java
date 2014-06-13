package de.upb.ddi.cqest.model;

import java.io.File;
import java.util.SortedMap;
import java.util.TreeMap;

public class CqestModel {

	public String root = null;
	
	public String prefix = "";
	
	public String dataFile = "data.dat", labelsFile = "labels.txt", showFile = "show.txt", 
			itanalFile = "itanal.txt", pvsFile = "pvs.txt", paramFiel = "parameters.txt";
	
	public SortedMap<Integer, CqestItem>  items;
	
	public SortedMap<Integer, CqestPerson>  persons;
	
	public CqestModel() {
		this(null);
	}
	public CqestModel( String root ) {
		this.root = root;
		this.items = new TreeMap<Integer, CqestItem>();
		this.persons = new TreeMap<Integer, CqestPerson>();
	}

	public File getLabelsFile() {
		return new File(getPathTo(labelsFile));
	}

	public File getShowFile() {
		return new File(getPathTo(showFile));
	}

	public File getItanalFile() {
		return new File(getPathTo(itanalFile));
	}
	public File getPVsFile() {
		return new File(getPathTo(pvsFile));
	}
	
	public String getPathTo( String path ) {
		if( root != null ) {
			return root+File.separator+prefix+path;
		} else {
			return path;
		}
	}
	
}
