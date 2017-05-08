package org.ciat.dssat_sum.control;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;

import org.ciat.dssat_sum.control.OverviewWorker.fileSection;
import org.ciat.dssat_sum.model.SerieVariable;
import org.ciat.dssat_sum.model.SummaryRun;

public class SeriesWorker {

	private static final String MEASURED_PREFIX = "M-";
	private static final String SIMULATED_PREFIX = "S-";
	private Set<SerieVariable> variables;

	private SummaryRun run;

	public SeriesWorker(SummaryRun summaryRun) {
		this.run = summaryRun;
	}

	public void work() {

		variables = getVariables(run.getModel());


		PrintWriter pwriter;
		BufferedWriter bwriter;

		File master = run.getSummaryOutput();
		try {
			pwriter = new PrintWriter(master);
			bwriter = new BufferedWriter(pwriter);

			/* Building the header */
			String head = "CULTIVAR" + run.LINE_SEPARATOR + "TR" + run.LINE_SEPARATOR;

			for (SerieVariable var : variables) {
				head += MEASURED_PREFIX + var.getName() + run.LINE_SEPARATOR;
				head += SIMULATED_PREFIX + var.getName() + run.LINE_SEPARATOR;
			}

			bwriter.write(head);
			bwriter.newLine();
			/* END building the header **/
			
			

			pwriter.close();
			bwriter.close();
		} catch (FileNotFoundException e) {
			App.LOG.severe("File not found " + master.getAbsolutePath());
		} catch (IOException e) {
			App.LOG.severe("Error writing in " + master.getAbsolutePath());
		}

	}

	private void populateMeasurements() {
		File fileT = new File(run.getFileT());
		Scanner reader;
		String line="";
		try {
			if (fileT.exists()) {
				reader = new Scanner(fileT);

				fileSection flag = fileSection.INIT;
				while (flag == fileSection.INIT && reader.hasNextLine()) {
					line = reader.nextLine();
					//TODO read T file

					}

				reader.close();
				}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	private Set<SerieVariable> getVariables(String model) {
		Set<SerieVariable> vars = new LinkedHashSet<SerieVariable>();
		vars.add(new SerieVariable("TR", 0));
		vars.add(new SerieVariable("DATE", 1));
		
		switch (run.getModel()) {
		case "CRGRO046 - Dry bean ": {
			// TODO

		}

			break;
		case "MZCER046 - Maize    ": {
			vars.add(new SerieVariable("LAID", 2));
			vars.add(new SerieVariable("CWAD", 3));
			vars.add(new SerieVariable("LWAD", 4));
			vars.add(new SerieVariable("GWAD", 5));
			vars.add(new SerieVariable("HAID", 6));
		}
			break;
		default: {
			App.LOG.warning("Crop not configurated for plantgro: " + run.getModel());
		}

		}

		return vars;
	}

}
