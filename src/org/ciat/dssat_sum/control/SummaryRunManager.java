package org.ciat.dssat_sum.control;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SummaryRunManager {
	public static String separator = "\t";

	private static List<String> growthVariables;

	public void work() {
		DecimalFormat nf = new DecimalFormat("000000");
		growthVariables = new ArrayList<String>();
		growthVariables.add("Anthesis day (dap)");
		growthVariables.add("Physiological maturity day (dap) ");
		growthVariables.add("Yield at harvest maturity (kg [dm]/ha)");
		growthVariables.add("Number at maturity (no/m2)");
		growthVariables.add("Unit wt at maturity (g [dm]/unit)");
		growthVariables.add("Number at maturity (no/unit)");
		growthVariables.add("Tops weight at maturity (kg [dm]/ha)");
		growthVariables.add("By-product produced (stalk) at maturity (kg[dm]/ha");
		growthVariables.add("Leaf area index, maximum");
		growthVariables.add("Harvest index at maturity");
		growthVariables.add("Leaf number per stem at maturity");

		PrintWriter writer;
		try {
			File master = new File("summary" + ".csv");
			writer = new PrintWriter(master);
			String head = "Corrida No" + separator + "TR" + separator + "End Juv" + separator + "Flor ini" + separator + "75%" + separator + "Beg g f" + separator + "End g f" + separator + "Mat" + separator + "Har" + separator;

			for (String var : growthVariables) {
				var=var.replaceAll(",", "");
				var=var.replaceAll("\t", "");
				head += var + separator;
				head += "Observed " + var + separator;
			}
			System.out.println(head);
			writer.println(head);
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

					for (String var : growthVariables) {
						if (line.contains(var)) {
							cadena += line.substring(57, 64) + separator;
							cadena += line.substring(69, 77) + separator;
						}
					}
					if (line.contains("----------------------------------------------------------------------------------------------------------------------------------------------------------------")) {
						flag = fileSection.END;
						runs.add(cadena);
						System.out.println(cadena);
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
