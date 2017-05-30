package org.ciat.gavilan.model;

public enum CropCode {
	BEAN, MAIZE;

	public static CropCode getModelCode(String name) {
		switch (name) {
		case "BN":
			return CropCode.BEAN;
		case "MZ":
			return CropCode.MAIZE;
		}
		return null;
	}

}
