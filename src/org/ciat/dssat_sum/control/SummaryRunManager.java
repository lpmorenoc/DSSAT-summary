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

	private String separator = "\t";
	private List<String> growthVariables;
	private List<String> cropNSoilVariables;

	public enum fileSection {
		INIT, CROP_N_SOIL, GROWTH, END
	};

	public void work() {
		DecimalFormat nf = new DecimalFormat("000000");
		cropNSoilVariables = new ArrayList<String>();
		growthVariables = new ArrayList<String>();

		String model = "BN";

		File firstCultivarOutput = new File("0\\000000\\" + "OVERVIEW.OUT");
		Scanner reader;
		try {
			reader = new Scanner(firstCultivarOutput);
			String line="";
			fileSection flag=fileSection.INIT;
			while (flag==fileSection.INIT && reader.hasNextLine()) {
				line = reader.nextLine();
				if (line.contains("EXPERIMENT")) {
					model = line.substring(27, 29);
					flag=fileSection.END;
				}

			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		switch (model) {
		case "BN": {
			cropNSoilVariables.add("Emergence");
			cropNSoilVariables.add("End Juven");
			cropNSoilVariables.add("Flower Ind");
			cropNSoilVariables.add("First Flwr");
			cropNSoilVariables.add("First Pod");
			cropNSoilVariables.add("First Seed");
			cropNSoilVariables.add("End Pod");
			cropNSoilVariables.add("Phys. Mat");
			cropNSoilVariables.add("Maturity");
			cropNSoilVariables.add("End Leaf");
			cropNSoilVariables.add("Harv. Mat");
			cropNSoilVariables.add("Harvest");
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

		}

			break;
		case "MZ": {
			cropNSoilVariables.add("End Juveni");
			cropNSoilVariables.add("Floral Ini");
			cropNSoilVariables.add("Silkin");
			cropNSoilVariables.add("End Gr Fil");
			cropNSoilVariables.add("Maturity");
			cropNSoilVariables.add("Harvest");
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

		}
			break;
		default: {
			System.out.println("Crop not found: "+model);
			cropNSoilVariables.add("End Juven");
			cropNSoilVariables.add("Floral I");
			cropNSoilVariables.add("Maturity");
			cropNSoilVariables.add("Harvest");
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
		}
		}

		PrintWriter writer;
		try {
			File master = new File("summary" + ".csv");
			writer = new PrintWriter(master);
			String head = "Corrida No" + separator + "TR" + separator;

			for (String var : cropNSoilVariables) {
				var = var.replaceAll(",", "");
				var = var.replaceAll(separator, "");
				head += var + separator;
			}

			for (String var : growthVariables) {
				var = var.replaceAll(",", "");
				var = var.replaceAll(separator, "");
				head += var + separator;
				head += "Observed " + var + separator;
			}

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

					for (String var : cropNSoilVariables) {
						if (line.contains(var)) {
							cadena += line.substring(7, 12) + separator;
						}
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
