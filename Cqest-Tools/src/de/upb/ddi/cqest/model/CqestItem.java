package de.upb.ddi.cqest.model;

public class CqestItem {
	
	public int id;
	
	public String label;

	public double estimate, error, mnsq, ci0, ci1, t, wmnsq, wci0, wci1, wt, discrimination;
	
	public int cases;
	
	public CqestItem() {
	}

}
