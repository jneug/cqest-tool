package de.upb.ddi.cqest;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.upb.ddi.cqest.model.CqestItem;
import de.upb.ddi.cqest.model.CqestModel;

public class CqestShowParser {

	private File showFile;

	public CqestShowParser() {
		
	}
	
	public CqestShowParser( String showFile ) {
		this.showFile = new File(showFile);
	}

	public CqestModel parse( CqestModel model ) {
		if( showFile == null )
			showFile = model.getShowFile();
		
		if( !showFile.canRead() ) {
			System.err.println("Can't read input file.");
			return model;
		}
		
		try(Scanner scanner = new Scanner(showFile)) {
			String p_number = "\\s+(-?\\d+\\.\\d+)", p_mnsq = p_number+"\\s+\\("+p_number+","+p_number+"\\)"+p_number;
			Pattern p = Pattern.compile("^\\s*(\\d+)\\s+(\\S+)"+p_number+"\\*?"+p_number+p_mnsq+p_mnsq+"\\s*$");
			while( scanner.hasNextLine() ) {
				String str = scanner.nextLine();
				
				Matcher m = p.matcher(str);
				if( m.matches() ) {
					int id = Integer.parseInt(m.group(1));
					
					CqestItem i = null;
					if( model.items.containsKey(id) ) {
						i = model.items.get(id);
					} else {
						i = new CqestItem();
						i.id = id;
						i.label = m.group(2);
						model.items.put(id, i);
					}
					
					i.estimate = Double.parseDouble(m.group(3));
					i.error = Double.parseDouble(m.group(4));
					i.mnsq = Double.parseDouble(m.group(5));
					i.ci0 = Double.parseDouble(m.group(6));
					i.ci1 = Double.parseDouble(m.group(7));
					i.t = Double.parseDouble(m.group(8));
					i.wmnsq = Double.parseDouble(m.group(9));
					i.wci0 = Double.parseDouble(m.group(10));
					i.wci1 = Double.parseDouble(m.group(11));
					i.wt = Double.parseDouble(m.group(12));
				}
			}
			
			scanner.close();
		} catch( FileNotFoundException e ) {
			e.printStackTrace();
		}
		
		return model;
	}

}
