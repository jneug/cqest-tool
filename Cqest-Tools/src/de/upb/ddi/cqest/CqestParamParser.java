package de.upb.ddi.cqest;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class CqestParamParser {

	private File in, out;
	
	public CqestParamParser( String input, String output ) {
		in = new File(input);
		out = new File(output);
	}

	public void parse() {
		if( !in.canRead() ) {
			System.err.println("Can't read input file.");
			System.exit(0);
		}
		
		StringBuilder sb = new StringBuilder();
		
		try(Scanner scanner = new Scanner(in)) {
			Pattern p = Pattern.compile("^\\s*(\\d+)\\s+(C\\S+)\\s+(-?\\d+\\.\\d+)\\s.*$");
			while( scanner.hasNextLine() ) {
				String str = scanner.nextLine();
				
				Matcher m = p.matcher(str);
				if( m.matches() ) {
					String g1 = String.format("%1$3s", m.group(1));
					String g2 = m.group(2);
					String g3 = String.format("%1$9s", m.group(3));
					
					sb.append(g1).append(g3).append("  /* "+g2+" */").append('\n');
				}
			}
			
			scanner.close();
		} catch( FileNotFoundException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try(Writer writer = new BufferedWriter( new FileWriter(out) )) {
			writer.append(sb);
			writer.close();
		} catch( IOException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("All done! Parameters extraced from");
		System.out.println(in.getAbsolutePath());
		System.out.println("and written to");
		System.out.println(out.getAbsolutePath());
	}


	@SuppressWarnings("static-access")
	public static void main( String[] args ) {
		OptionGroup input = new OptionGroup();
		input.isRequired();
		input.addOption(OptionBuilder
				.hasArg()
				.withArgName("file")
				.withDescription(
						"ConQuest show file. "
								+ "Created from ConQuest with 'show ! estimates=latent >> file'")
				.create("show"));
		input.addOption(OptionBuilder
				.hasArg()
				.withArgName("file")
				.withDescription("The file with the itanal output. "
						+ "Created from ConQuest with 'itanal >> file'")
				.create("itanal"));

		Options options = new Options();
		options.addOptionGroup(input);
		options.addOption( OptionBuilder
				.hasArg()
				.withArgName("file")
				.withDescription("The output file.")
				.isRequired()
				.create("output"));

		CommandLineParser parser = new BasicParser();
		CommandLine cli = null;
		try {
			cli = parser.parse(options, args);
			
			String in, out;
			
			if( cli.hasOption("show") ) {
				in = cli.getOptionValue("show");
			} else {
				in = cli.getOptionValue("itanal");
			}
			
			out = cli.getOptionValue("output");
				
			
			new CqestParamParser(in, out).parse();
		} catch( ParseException ex ) {
			System.err.println("Error parsing the command line arguments.");
			System.err.println(ex.getMessage());
			System.err.println();

			HelpFormatter help = new HelpFormatter();
			help.printHelp("ConquestParameterParser", options);

			System.exit(0);
		}
	}

}
