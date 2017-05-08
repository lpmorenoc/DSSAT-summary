package org.ciat.dssat_sum.model;

public class SerieVariable {

	
	private String name;
	private int index;
	
	public SerieVariable(String name, int index) {
		super();
		this.name = name;
		this.index = index;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	public int getIndex() {
		return index;
	}


	public void setIndex(int index) {
		this.index = index;
	}



}