package org.ciat.dssat_sum.control;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.ciat.dssat_sum.model.ProgressBar;
import org.ciat.dssat_sum.model.SummaryRun;

public class OverviewWorker {


	private Map<String, String> outputVarsValues;
	private List<String> growthVariables;
	private List<String> cropNSoilVariables;
	private final String MEASURED_PREFIX = "Measured ";
	private SummaryRun run;
	

	public enum fileSection {
		INIT(), CROP_N_SOIL, GROWTH, END
	};

	public OverviewWorker(SummaryRun summaryRun) {
		this.run = summaryRun;
	}

	public void work() {
		
		cropNSoilVariables = new ArrayList<String>();
		growthVariables = new ArrayList<String>();
		outputVarsValues =new LinkedHashMap<>();
		ProgressBar bar = new ProgressBar();
		int subFolderNumber = 0;


		populateVariables();

		File master = run.getOverviewOutput();
		
		try(BufferedWriter bwriter = new BufferedWriter(new PrintWriter(master))) {

						
			/* Building the header */
			String head = "CULTIVAR" + run.LINE_SEPARATOR + "TR" + run.LINE_SEPARATOR;

			for (String var : cropNSoilVariables) {
				outputVarsValues.put(var, "");
				var = var.replaceAll(",", "");
				var = var.replaceAll(run.LINE_SEPARATOR, "");
				head += var + run.LINE_SEPARATOR;
			}

			for (String var : growthVariables) {
				outputVarsValues.put(var, "");
				outputVarsValues.put(MEASURED_PREFIX + var, "");
				var = var.replaceAll(",", "");
				var = var.replaceAll(run.LINE_SEPARATOR, "");
				head += var + run.LINE_SEPARATOR;
				head += MEASURED_PREFIX + var + run.LINE_SEPARATOR;
			}

			bwriter.write(head);
			bwriter.newLine();
			/* END building the header **/
			
			/* Search on each OVERVIEW.OUT file from 0/ folder and further */
			boolean flagFolder = true;
			for (int folder = 0; flagFolder; folder++) {
				File bigFolder = new File(folder + run.PATH_SEPARATOR);
				if (bigFolder.exists()) {
					bar = new ProgressBar();
					System.out.println("Getting overwiew on folder " + bigFolder.getName());
					bar.update(0, bigFolder.listFiles().length);
					for (File subFolder:bigFolder.listFiles()) { // for each subfolder
						File output = new File(subFolder.getAbsolutePath()+run.PATH_SEPARATOR+"OVERVIEW.OUT"); // look at the overview.out file
						if (output.exists()) {
								for (String cadena : getCultivarVariables(output)) { // for each cultivar get all the simulated and observed values
									bwriter.write(cadena); // print the values
									bwriter.newLine();
								}
								
						}else {
							App.LOG.warning(subFolder+run.PATH_SEPARATOR+output.getName()+" not found");
						}
						bar.update(subFolderNumber++, bigFolder.listFiles().length);
					}
					//bwriter.flush();

				} else {
					flagFolder = false; // Flag that there are no more folders search in 
					App.LOG.fine("Finished gathering overwiew results");
				}
			}
			
			
			//pwriter.close();
			//bwriter.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	private void populateVariables() {
		switch (run.getModel()) {
		case BEAN: {
			cropNSoilVariables.add("Emergence");
			cropNSoilVariables.add("End Juven");
			cropNSoilVariables.add("Flower Ind");
			cropNSoilVariables.add("First Flwr");
			cropNSoilVariables.add("First Pod");
			cropNSoilVariables.add("First Seed");
			cropNSoilVariables.add("End Pod");
			cropNSoilVariables.add("Phys. Mat");
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
		case MAIZE: {
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
			App.LOG.warning("Crop not configurated for overview: " + run.getModel()+", using default variables");
			cropNSoilVariables.add("End Juven");
			cropNSoilVariables.add("Floral I");
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

	}


	/* obtain all the simulated and observed values of the variables populated in both cropNSoilVariables and growthVariables */
	private List<String> getCultivarVariables(File cultivarOutput) {

		List<String> runsOutput = new ArrayList<String>();
		String cadena = "";
		String line = "";
		fileSection flag = fileSection.INIT;
		int treatment = 0;
		try(Scanner reader = new Scanner(cultivarOutput)) {
			
			while (reader.hasNextLine()) { // reading the whole file
				line = reader.nextLine();

				switch (flag) {
				case INIT: {
					if (line.contains("*RUN")) { // to detect each single run of a treatment
						treatment = Integer.parseInt(line.substring(7, 10).replaceAll(" ", ""));
						cadena = cultivarOutput.getParent() + run.LINE_SEPARATOR + treatment + run.LINE_SEPARATOR; // to print experiment run ID and the treatment
						for (String key : outputVarsValues.keySet()) {
							outputVarsValues.put(key, ""); // clear the previous values to recycle the Map
						}
					}
					if (line.contains("*SIMULATED CROP AND SOIL STATUS AT MAIN DEVELOPMENT STAGES")) { // detect section
						flag = fileSection.CROP_N_SOIL;
					}
				}
					break;
				case CROP_N_SOIL: {

					for (String var : cropNSoilVariables) {
						if (line.contains(var)) {
							outputVarsValues.put(var, line.substring(7, 12)); // get value
						}
					}
					if (line.contains("*MAIN GROWTH AND DEVELOPMENT VARIABLES")) { // detect section
						flag = fileSection.GROWTH;
					}
				}
					break;
				case GROWTH: {

					for (String var : growthVariables) {
						if (line.contains(var)) {
							outputVarsValues.put(var, line.substring(57, 64)); // get simulated value
							outputVarsValues.put(MEASURED_PREFIX + var, line.substring(69, 77)); // get observed value

						}
					}
					// to detect the end of the treatment run
					if (line.contains("----------------------------------------------------------------------------------------------------------------------------------------------------------------")) {
						flag = fileSection.END;
						for (String key : outputVarsValues.keySet()) {
							cadena += outputVarsValues.get(key) + run.LINE_SEPARATOR;
						}
						runsOutput.add(cadena);
					}
				}
				case END: {
					if (line.contains("*DSSAT Cropping System Model")) { // detect the start of a new treatment run
						flag = fileSection.INIT;
					}

				}
					break;
				default:
					break;
				}
			}
			//reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return runsOutput;
	}

	@Deprecated
	public static String obtainModel() {
		String model = "MZCER046 - Maize";
		File firstCultivarOutput = new File(((new File("0")).listFiles()[0].getAbsolutePath() + "\\OVERVIEW.OUT"));
		Scanner reader;
		try {
			if (firstCultivarOutput.exists()) {
				reader = new Scanner(firstCultivarOutput);
				String line = "";
				fileSection flag = fileSection.INIT;
				while (flag == fileSection.INIT && reader.hasNextLine()) {
					line = reader.nextLine();
					if (line.contains("MODEL          :")) {
						model = line.substring(18, 38);
						flag = fileSection.END;
					}

				}
				reader.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		App.LOG.info("Detected model: " + model);
		return model;
	}
}
