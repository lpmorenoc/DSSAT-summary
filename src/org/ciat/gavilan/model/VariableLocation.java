package org.ciat.gavilan.model;

public class VariableLocation {

	
	private Variable variable;
	private int indexFileT;
	private int indexPlantGro;
	private int indexFileA;
	
	public VariableLocation(Variable variable, int indexFileT, int indexPlantGro) {
		super();
		this.variable = variable;
		this.indexFileT = indexFileT;
		this.indexPlantGro = indexPlantGro;
	}
	
	public VariableLocation(Variable variable, int indexFileA) {
		super();
		this.variable = variable;
		this.indexFileA = indexFileA;
	}

	public Variable getVariable() {
		return variable;
	}

	public void setVariable(Variable variable) {
		this.variable = variable;
	}

	public int getIndexFileT() {
		return indexFileT;
	}

	public void setIndexFileT(int indexFileT) {
		this.indexFileT = indexFileT;
	}

	public int getIndexPlantGro() {
		return indexPlantGro;
	}

	public void setIndexPlantGro(int indexPlantGro) {
		this.indexPlantGro = indexPlantGro;
	}

	public int getIndexFileA() {
		return indexFileA;
	}

	public void setIndexFileA(int indexFileA) {
		this.indexFileA = indexFileA;
	}
	




}