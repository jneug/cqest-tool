package de.upb.ddi.cqest;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.upb.ddi.cqest.model.CqestItem;
import de.upb.ddi.cqest.model.CqestModel;

public class CqestItanalParser {

	private File itanalFile;

	public CqestItanalParser() {
		
	}
	
	public CqestItanalParser( String showFile ) {
		this.itanalFile = new File(showFile);
	}

	public CqestModel parse( CqestModel model ) {
		if( itanalFile == null )
			itanalFile = model.getItanalFile();
		
		if( !itanalFile.canRead() ) {
			System.err.println("Can't read input file.");
			return model;
		}
		
		try(Scanner scanner = new Scanner(itanalFile)) {
			String p_number = "\\s+(-?\\d+\\.\\d+)";
			Pattern p_id = Pattern.compile("^item:(\\d+) \\(\\S+\\)\\s*$");
			Pattern p = Pattern.compile("^Cases for this item\\s+(\\d+)\\s+Discrimination"+p_number+"\\s*$");
			
			CqestItem i = null;
			while( scanner.hasNextLine() ) {
				String str = scanner.nextLine();
				
				Matcher m1 = p_id.matcher(str);
				Matcher m2 = p.matcher(str);
				if( m1.matches() ) {
					int id = Integer.parseInt(m1.group(1));
					
					if( model.items.containsKey(id) ) {
						i = model.items.get(id);
					} else {
						i = new CqestItem();
						i.id = id;
						i.label = m1.group(2);
						model.items.put(id, i);
					}
					
				} else if( m2.matches() && i != null ) {
					i.cases = Integer.parseInt(m2.group(1));
					i.discrimination = Double.parseDouble(m2.group(2));
					i = null;
				}
			}

			scanner.close();
		} catch( FileNotFoundException e ) {
			e.printStackTrace();
		}
		
		return model;
	}

}
