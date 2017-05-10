package org.ciat.dssat_sum.control;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;
import org.ciat.dssat_sum.model.VariableLocation;
import org.ciat.dssat_sum.model.Measurement;
import org.ciat.dssat_sum.model.ModelCode;
import org.ciat.dssat_sum.model.SummaryRun;
import org.ciat.dssat_sum.model.Treatment;
import org.ciat.dssat_sum.model.Variable;

public class SeriesWorker {

	private static final String MEASURED_PREFIX = "M-";
	private static final String SIMULATED_PREFIX = "S-";
	private Set<VariableLocation> variables;

	private SummaryRun run;

	public SeriesWorker(SummaryRun summaryRun) {
		this.run = summaryRun;
	}

	public void work() {

		variables = getVariables(run.getModel());
		Set<Treatment> samplings = getSampleMeasurements();
		Set<Treatment> simulations = new LinkedHashSet<>();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");

		File master = run.getSummaryOutput();
		try(BufferedWriter bwriter = new BufferedWriter(new PrintWriter(master))) {
			
			

			/* Building the header */
			String head = "CULTIVAR" + run.LINE_SEPARATOR + "TR" + run.LINE_SEPARATOR;

			for (VariableLocation var : variables) {
				head += MEASURED_PREFIX + var.getVariable().getName() + run.LINE_SEPARATOR;
				head += SIMULATED_PREFIX + var.getVariable().getName() + run.LINE_SEPARATOR;
			}

			bwriter.write(head);
			bwriter.newLine();
			/* END building the header **/

			/* Search on each PlanGro.OUT file from 0/ folder and further */
			boolean flagFolder = true;
			for (int folder = 0; flagFolder; folder++) {
				File bigFolder = new File(folder + run.PATH_SEPARATOR);
				if (bigFolder.exists()) {
					for (File subFolder : bigFolder.listFiles()) { // for each subfolder
						// look at the overview.out file
						File output = new File(subFolder.getAbsolutePath() + run.PATH_SEPARATOR + "OVERVIEW.OUT");
						if (output.exists()) {
							simulations = printWithSimulations(output);

							for (Treatment sampleTreatment : samplings) {
								for (Treatment simulationTreatment : simulations) {

									if (sampleTreatment == simulationTreatment) {
									
										for (Measurement msample : sampleTreatment.getSamplings()) {
											for (Measurement msimule : simulationTreatment.getSamplings()) {

												if (msample.getDate() == msimule.getDate()) {
													bwriter.write(sdf.format(msample.getDate())+run.LINE_SEPARATOR);
													
													for(Variable v: msimule.getValues().keySet()){
														bwriter.write(sdf.format(msample.getValues().get(v).doubleValue())+run.LINE_SEPARATOR);
														bwriter.write(sdf.format(msimule.getValues().get(v).doubleValue())+run.LINE_SEPARATOR);
														bwriter.write("");
													}
													bwriter.newLine();
													
												}
											}
										}

									}
								}
								//bwriter.flush();
							}
							

						} else {
							App.LOG.warning(subFolder + run.PATH_SEPARATOR + output.getName() + " not found");
						}
					}

				} else {
					flagFolder = false; // Flag that there are no more folders search in
					App.LOG.fine("Finished gathering data");
				}
			}

			//pwriter.close();
			//bwriter.close();
		} catch (FileNotFoundException e) {
			App.LOG.severe("File not found " + master.getAbsolutePath());
		} catch (IOException e) {
			App.LOG.severe("Error writing in " + master.getAbsolutePath());
		}

	}

	private Set<Treatment> printWithSimulations(File plantGro) {
		Set<Treatment> treatments = new LinkedHashSet<>();
		Scanner reader;
		String line = "";
		String[] numbers;
		Treatment t = new Treatment(-1);
		Measurement m = new Measurement(new Date());
		int doy = 0;
		int year = 0;
		Calendar calendar = Calendar.getInstance();

		try {
			if (plantGro.exists()) {
				reader = new Scanner(plantGro);
				while (reader.hasNextLine()) {
					line = reader.nextLine();
					line = line.trim();

					/* Leave the line with only one space of separation */
					while (line.contains("  ")) {
						line = line.replaceAll("  ", " ");
					}

					numbers = line.split(" "); // divide in spaces

					if (line.contains("TREATMENT")) { // detecting the treatment
						if (t != new Treatment(Integer.parseInt(numbers[1]))) {
							t = new Treatment(Integer.parseInt(numbers[1]));
						}
					}

					// In values section
					if (numbers.length > 0 && isNumeric(numbers[0])) {

						year = Integer.parseInt(numbers[0]); // obtain the year
						doy = Integer.parseInt(numbers[1]); // obtain the DOY

						calendar.set(Calendar.DAY_OF_YEAR, doy);
						calendar.set(Calendar.YEAR, year);

						m = new Measurement(calendar.getTime());
						for (VariableLocation vl : variables) {
							m.getValues().put(vl.getVariable(), Double.parseDouble(numbers[vl.getIndexPlantGro()]));
						}
						t.getSamplings().add(m);
						treatments.add(t);
					}

				}

				reader.close();
			} else {
				App.LOG.warning("File not found " + plantGro.getAbsolutePath());
			}

		} catch (FileNotFoundException e) {
			App.LOG.severe("File not found as " + plantGro.getAbsolutePath());
		}

		return treatments;

	}

	private Set<Treatment> getSampleMeasurements() {
		Set<Treatment> treatments = new LinkedHashSet<>();
		File fileT = new File(run.getFileT());
		Scanner reader;
		String line = "";
		String[] numbers;
		Treatment t = new Treatment(-1);
		Measurement m = new Measurement(new Date());
		int doy = 0;
		int year = 0;
		Calendar calendar = Calendar.getInstance();

		try {
			if (fileT.exists()) {
				reader = new Scanner(fileT);
				while (reader.hasNextLine()) {
					line = reader.nextLine();
					line = line.trim();

					/* Leave the line with only one space of separation */
					while (line.contains("  ")) {
						line = line.replaceAll("  ", " ");
					}

					numbers = line.split(" ");
					if (numbers.length > 0 && isNumeric(numbers[0])) {

						if (t != new Treatment(Integer.parseInt(numbers[0]))) {
							t = new Treatment(Integer.parseInt(numbers[0]));
						}
						// obtain two digits of the year
						year = Integer.parseInt(numbers[1].charAt(0) + "" + numbers[1].charAt(1));

						if (year > 50) {
							year += 1900; // 50 and higher will be 1950
						} else {
							year += 2000; // 49 and lower will be 2049
						}
						// obtain three digits of the DOY
						doy = Integer.parseInt(numbers[1].charAt(2) + "" + numbers[1].charAt(3) + "" + numbers[1].charAt(4));

						calendar.set(Calendar.DAY_OF_YEAR, doy);
						calendar.set(Calendar.YEAR, year);
						m = new Measurement(calendar.getTime());
						for (VariableLocation vl : variables) {
							m.getValues().put(vl.getVariable(), Double.parseDouble(numbers[vl.getIndexFileT()]));
						}
						t.getSamplings().add(m);
						treatments.add(t);

					}

				}

				reader.close();
			} else {
				App.LOG.severe("File T not found as " + fileT.getAbsolutePath());
			}

		} catch (FileNotFoundException e) {
			App.LOG.severe("File T not found as " + fileT.getAbsolutePath());
		}
		return treatments;
	}

	private Set<VariableLocation> getVariables(ModelCode modelCode) {
		Set<VariableLocation> vars = new LinkedHashSet<VariableLocation>();

		switch (run.getModel()) {
		case BEAN: {
			// TODO

		}

			break;
		case MAIZE: {
			vars.add(new VariableLocation(new Variable("LAID"), 2, 7));
			vars.add(new VariableLocation(new Variable("CWAD"), 3, 13));
			vars.add(new VariableLocation(new Variable("LWAD"), 4, 8));
			vars.add(new VariableLocation(new Variable("GWAD"), 5, 10));
			vars.add(new VariableLocation(new Variable("HAID"), 6, 16));
		}
			break;
		default: {
			App.LOG.warning("Crop not configurated for plantgro: " + run.getModel());
		}

		}

		return vars;
	}

	public boolean isNumeric(String s) {
		return s.matches("[-+]?\\d*\\.?\\d+");
	}

}
