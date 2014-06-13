package de.upb.ddi.cqest;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.upb.ddi.cqest.model.CqestItem;
import de.upb.ddi.cqest.model.CqestModel;

public class CqestLabelsParser {

	private File labelsFile;

	public CqestLabelsParser() {

	}

	public CqestLabelsParser( String file ) {
		this.labelsFile = new File(file);
	}

	public CqestModel parse( CqestModel model ) {
		if( labelsFile == null ) {
			labelsFile = model.getLabelsFile();
		}

		if( !labelsFile.canRead() ) {
			System.err.println("Can't read input file.");
			return model;
		}

		try( Scanner scanner = new Scanner(labelsFile) ) {
			Pattern p = Pattern.compile("^(\\d+)\\s+(\\S+)$");
			while( scanner.hasNextLine() ) {
				String line = scanner.nextLine();

				Matcher m = p.matcher(line);
				if( m.matches() ) {
					int id = Integer.parseInt(m.group(1));

					CqestItem i = null;
					if( model.items.containsKey(id) ) {
						i = model.items.get(id);
					} else {
						i = new CqestItem();
						model.items.put(id, i);
					}
					i.id = id;
					i.label = m.group(2);
				}
			}

			scanner.close();
		} catch( FileNotFoundException e1 ) {
			e1.printStackTrace();
		}

		return model;
	}

}
