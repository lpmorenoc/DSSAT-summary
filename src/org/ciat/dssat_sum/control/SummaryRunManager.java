package org.ciat.dssat_sum.control;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import jdk.nashorn.internal.ir.Terminal;

public class SummaryRunManager {
	public static String separator = "\t";

	public void work() {
		DecimalFormat nf = new DecimalFormat("000000");

		PrintWriter writer;
		try {
			File master = new File("summary" + ".csv");
			writer = new PrintWriter(master);
			writer.println("Corrida No" + separator + "TR" + separator + "End Juv" + separator + "Flor ini" + separator + "75%" + separator + "Beg g f" + separator + "End g f" + separator + "Mat" + separator + "Har" + separator + "# Leaf a m");
			boolean flagFile = true;
			boolean flagFolder = true;
			for (int folder = 0; flagFolder; folder++) {
				if ((new File(folder + "\\" + nf.format(folder))).exists()) {
					for (int cultivar = 0; flagFile; cultivar++) {
						File cultivarOutput = new File(folder + "\\" + nf.format(cultivar) + "\\" + "OVERVIEW.OUT");
						if (cultivarOutput.exists()) {

							for (String cadena : getCultivarVariables(cultivarOutput)) {
								writer.println(cadena);
							}
							writer.flush();

						} else {
							flagFile = false;
						}
					}
				} else {
					flagFolder = false;
				}
			}

			writer.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	public enum fileSection {
		INIT, CROP_N_SOIL, GROWTH, END
	};

	private List<String> getCultivarVariables(File cultivarOutput) {
		Scanner reader;
		List<String> runs = new ArrayList<String>();
		String cadena = "";
		String line = "";
		fileSection flag = fileSection.INIT;
		int treatment = 0;

		try {
			reader = new Scanner(cultivarOutput);
			while (reader.hasNextLine()) {
				line = reader.nextLine();

				switch (flag) {
				case INIT: {
					if (line.contains("*RUN")) {
						treatment = Integer.parseInt(line.substring(7, 10).replaceAll(" ", ""));
						cadena = cultivarOutput.getParent() + separator + treatment + separator;
					}
					if (line.contains("*SIMULATED CROP AND SOIL STATUS AT MAIN DEVELOPMENT STAGES")) {
						flag = fileSection.CROP_N_SOIL;
					}
				}
					break;
				case CROP_N_SOIL: {
					if (line.contains("End Juveni") || line.contains("Floral Ini") || line.contains("Silkin") || line.contains("Beg Gr Fil") || line.contains("End Gr Fil") || line.contains("Maturity") || line.contains("Harvest")) {
						cadena += line.substring(7, 12) + separator;
					}
					if (line.contains("*MAIN GROWTH AND DEVELOPMENT VARIABLES")) {
						flag = fileSection.GROWTH;
					}
				}
					break;
				case GROWTH: {
					if (line.contains("Leaf number per stem at maturity")) {
						cadena += line.substring(57, 64);
					}
					if (line.contains("----------------------------------------------------------------------------------------------------------------------------------------------------------------")) {
						flag = fileSection.END;
						runs.add(cadena);
					}
				}
				case END: {
					if (line.contains("*DSSAT Cropping System Model")) {
						flag = fileSection.INIT;
					}

				}
					break;
				default:
					break;
				}
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return runs;
	}

}
