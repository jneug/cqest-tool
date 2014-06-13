package de.upb.ddi.cqest;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.upb.ddi.cqest.model.CqestModel;
import de.upb.ddi.cqest.model.CqestPerson;

public class CqestPVsParser {

	private File pvsFile;
	
	public CqestPVsParser() {
		
	}
	
	public CqestPVsParser( String file ) {
		this.pvsFile = new File(file);
	}
	
	public CqestModel parse( CqestModel model ) {
		if( pvsFile == null ) {
			pvsFile = model.getPVsFile();			
		}

		if( !pvsFile.canRead() ) {
			System.err.println("Can't read input file.");
			return model;
		}
		
		try(Scanner scanner = new Scanner(pvsFile)) {
			String p_number = "\\s+(-?\\d+\\.\\d+)";
			Pattern p1 = Pattern.compile("^\\s+(\\d+)\\s*$");
			Pattern p2 = Pattern.compile("^\\s+(\\d+)"+p_number+"$");
			Pattern p3 = Pattern.compile("^\\s+"+p_number+"\\s*$");
			
			int np = 0, n = 0; boolean resizeArr = false;
			CqestPerson p = null;
			while( scanner.hasNextLine() ) {
				String line = scanner.nextLine();
				
				Matcher m1 = p1.matcher(line);
				Matcher m2 = p2.matcher(line);
				Matcher m3 = p3.matcher(line);
				
				if( m1.matches() ) {
					int id = Integer.parseInt(m1.group(1));
					
					if( resizeArr ) {
						resizeArr = false;
					}
					
					if( model.persons.containsKey(id) ) {
						p = model.persons.get(id);
					} else {
						p = new CqestPerson();
						model.persons.put(id, p);
						n = 0;
						
						if( np > 0 ) {
							p.pvs = new double[np];
						} else {
							p.pvs = new double[1];
							resizeArr = true;
						}
					}
					p.id = id;
				} else if( m2.matches() ) {
					if( resizeArr ) {
						np++;
						
						double[] tmp = p.pvs;
						p.pvs = new double[np];
						System.arraycopy(tmp, 0, p.pvs, 0, np-1);
					} else {
						p.pvs[n] = Double.parseDouble(m2.group(2));
					}
					
					n++;
				} else if( m3.matches() ) {
					if( n == np ) {
						p.eap =  Double.parseDouble(m3.group(1));
						n++;
					} else {
						p.sd =  Double.parseDouble(m3.group(1));
					}
				}
			}
			
			scanner.close();
		} catch( FileNotFoundException e1 ) {
			e1.printStackTrace();
		}
		
		return model;
	}
	
}
