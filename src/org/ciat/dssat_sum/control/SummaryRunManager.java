package org.ciat.dssat_sum.control;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Scanner;

public class SummaryRunManager {
	public static String separator="\t";

	public void work() {
		
		PrintWriter writer;
		try {
			File master = new File("summary" + ".csv");
			writer = new PrintWriter(master);
			writer.println("Corrida No" + separator + "End Juv" + separator + "Flor ini" + separator + "75%" + separator+ "Beg g f" + separator + "End g f" + separator + "Mat" + separator + "Har" + separator + "# Leaf a m");

			for (int cultivar = 0; cultivar < 54366; cultivar++) {
				writer.println(getCultivarVariables(cultivar));
			}
			writer.flush();
			writer.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	private String getCultivarVariables(int cultivar) {
		DecimalFormat nf = new DecimalFormat("000000");
		Scanner reader;
		String cadena = nf.format(cultivar)+separator;
		String line = "";
		boolean flag = false;
		File cultivarOutput = new File("0" + "\\" + nf.format(cultivar) + "\\" + "OVERVIEW.OUT");
		if (cultivarOutput.exists()) {

			try {
				reader = new Scanner(cultivarOutput);
				while (reader.hasNextLine()) {
					line = reader.nextLine();
					if (line.contains("SIMULATED CROP AND SOIL STATUS AT MAIN DEVELOPMENT STAGES")) {
						flag = true;
					}
					if (line.contains("MAIN GROWTH AND DEVELOPMENT VARIABLES")) {
						flag = false;
					}
					if (flag) {
						if (line.contains("End Juveni") || line.contains("Floral Ini") || line.contains("Silkin") || line.contains("Beg Gr Fil") || line.contains("End Gr Fil") || line.contains("Maturity") || line.contains("Harvest")) {
							cadena += line.substring(7, 12) + separator;
						}
					}
					if (line.contains("Leaf number per stem at maturity")) {
						cadena += line.substring(57, 64);
					}

				}
				reader.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		return cadena;
	}

}
