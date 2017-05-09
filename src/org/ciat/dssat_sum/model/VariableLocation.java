package org.ciat.dssat_sum.model;

public class VariableLocation {

	
	private Variable variable;
	private int indexFileT;
	private int indexPlantGro;
	
	public VariableLocation(Variable variable, int indexFileT, int indexPlantGro) {
		super();
		this.variable = variable;
		this.indexFileT = indexFileT;
		this.indexPlantGro = indexPlantGro;
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
	




}