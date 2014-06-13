package de.upb.ddi.cqest;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import de.upb.ddi.cqest.model.CqestItem;
import de.upb.ddi.cqest.model.CqestModel;

public class CqestCsvWriter {

	public static final int CSV = 1;
	public static final int CSV2 = 2;
	

	private File outputFile;
	
	private int format = CSV;
	
	private CqestItemFilter filter = CqestItemFilter.universalFilter;

	public CqestCsvWriter( String output ) {
		this.outputFile = new File(output);
	}
	
	public CqestCsvWriter( String output, CqestItemFilter filter ) {
		this.outputFile = new File(output);
		this.filter = filter;
	}
	
	public CqestCsvWriter setFormat( int f ) {
		if( f == CSV2 ) {
			format = CSV2;
		} else {
			format = CSV;
		}
		return this;
	}
	
	public CqestCsvWriter setFilter( CqestItemFilter filter ) {
		this.filter = filter;
		return this;
	}

	public void write( CqestModel model ) throws IOException {
		try( Writer writer = new BufferedWriter(new FileWriter(outputFile)) ) {
			writer.append("ID;Label;Estimate;Error;MNSQ;ci0;ci1;t;wMNSQ;wci0;wci1;wt;Cases;Discrimination\n");
			for( CqestItem item: model.items.values() ) {
				if( filter.isAccepted(item) ) {
					if( format == CSV2 ) {
						writer.append(formatCsv2(item));
					} else {
						writer.append(formatCsv(item));
					}
				}
			}
			writer.close();
		} catch( IOException e ) {
			throw e;
		}
	}

	public String formatCsv( CqestItem item ) {
		String l = (item.label.indexOf(" ") > -1 ) ? "\"" + item.label + "\"" : item.label;
		return item.id + "," + l + ","
				+ item.estimate + "," + item.error + "," + item.mnsq
				+ "," + item.ci0 + "," + item.ci1 + "," + item.t + "," + item.wmnsq
				+ "," + item.wci0 + "," + item.wci1 + "," + item.wt + "," + item.cases
				+ "," + item.discrimination
				+ "\n";
	}

	public String formatCsv2( CqestItem item ) {
		String l = (item.label.indexOf(" ") > -1 ) ? "\"" + item.label + "\"" : item.label;
		String str = item.estimate + ";" + item.error + ";" + item.mnsq
				+ ";" + item.ci0 + ";" + item.ci1 + ";" + item.t + ";" + item.wmnsq
				+ ";" + item.wci0 + ";" + item.wci1 + ";" + item.wt + ";" + item.cases
				+ ";" + item.discrimination;
		return item.id + ";" + l + ";" + str.replace('.', ',') + "\n";
	}
}
