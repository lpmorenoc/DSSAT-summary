package org.ciat.dssat_sum.model;

public enum ModelCode {
	BEAN, MAIZE;

	public static ModelCode getModelCode(String name) {
		switch (name) {
		case "BN":
			return ModelCode.BEAN;
		case "MZ":
			return ModelCode.MAIZE;
		}
		return null;
	}

}
