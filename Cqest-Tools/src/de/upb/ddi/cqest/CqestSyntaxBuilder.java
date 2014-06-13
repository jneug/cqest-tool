package de.upb.ddi.cqest;

import java.io.File;

public class CqestSyntaxBuilder {

	private String title = "", abbrv = "";

	private File file_root, file_show, file_data;

	boolean generate_comments = false;

	private boolean export_parameters = true, export_itanals = true,
			export_pvs = true;

	private File file_out_parameters, file_out_itanals, file_out_pvs;

	private boolean import_parameters = false;

	private File file_in_parameters;

	private String model = "item";

	private String additions = "";

	public CqestSyntaxBuilder() {

	}

	public CqestSyntaxBuilder withTitle( String title ) {
		this.title = title;
		return this;
	}

	public CqestSyntaxBuilder withAbbrv( String abbrv ) {
		this.abbrv = abbrv;
		return this;
	}

	public CqestSyntaxBuilder withRoot( String root ) {
		this.file_root = new File(root);
		return this;
	}

	public CqestSyntaxBuilder withData( String data ) {
		this.file_data = new File(data);
		return this;
	}

	public CqestSyntaxBuilder outputTo( String out ) {
		this.file_show = new File(out);
		return this;
	}

	public CqestSyntaxBuilder generateComments() {
		this.generate_comments = true;
		return this;
	}

	public CqestSyntaxBuilder generateComments( boolean bln ) {
		this.generate_comments = bln;
		return this;
	}

	public boolean verify() {
		return true;
	}

	public String toString() {
		StringBuilder str = new StringBuilder();


		str.append("/* ConQuest syntax file generated with CqestSyntaxBuilder */");
		str.append('\n').append('\n');

		str.append("title ").append(title).append(";");
		if( generate_comments )
			str.append('\n').append("/* Any title for this syntax file */");
		str.append('\n').append('\n');

		str.append("data")
				.append(file_root.toPath().relativize(file_data.toPath())
						.toString()).append(";");
		if( generate_comments )
			str.append('\n').append("/* path to the data file to use */");
		str.append('\n').append('\n');

		return str.toString();
	}

	public void build() {

	}

	public static void main( String[] args ) {
		CqestSyntaxBuilder csb = new CqestSyntaxBuilder();
		csb
				.withTitle("Test file")
				.withData(
						"/Volumes/Hyrrokkin/Dropbox/UPB/MoKoM/D_Auswertung/IRT-FINAL_2014-01-28/4DIM SPEED/Komplett-Datensatz-MoKoM_finissiert_SPEED.dat")
				.withRoot(
						"/Volumes/Hyrrokkin/Dropbox/UPB/MoKoM/D_Auswertung/IRT-FINAL_2014-01-28/4DIM SPEED")
				.generateComments();

		System.out.println(csb.toString());
	}

}
