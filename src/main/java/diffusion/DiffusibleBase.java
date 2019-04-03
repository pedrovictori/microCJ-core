package diffusion;

import graph.Input;
import graph.MutationGroup;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Base class for diffusible substances, with basic getters and a static method to import from csv
 */
public class DiffusibleBase implements Diffusible {
	private String name;
	private Double initialConcentration;
	private Double defaultProbabilityModifier;
	private Double diffusionCoefficient;
	private Double defaultConsumption;
	private Double defaultProduction;
	private String inputNode;

	public DiffusibleBase(String name, String inputNode, Double initialConcentration, Double defaultProbabilityModifier, Double diffusionCoefficient, Double defaultConsumption, Double defaultProduction) {
		this.name = name;
		this.initialConcentration = initialConcentration;
		this.defaultProbabilityModifier = defaultProbabilityModifier;
		this.diffusionCoefficient = diffusionCoefficient;
		this.defaultConsumption = defaultConsumption;
		this.defaultProduction = defaultProduction;
		this.inputNode = inputNode;
	}

	public DiffusibleBase(Diffusible diffusible) {
		this(diffusible.getName(), diffusible.getInputNode(), diffusible.getInitialConcentration(), diffusible.getDefaultProbabilityModifier(),
				diffusible.getDiffusionCoefficient(), diffusible.getDefaultConsumption(), diffusible.getDefaultProduction());
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Double getInitialConcentration() {
		return initialConcentration;
	}

	@Override
	public Double getDefaultProbabilityModifier() {
		return defaultProbabilityModifier;
	}

	@Override
	public Double getDiffusionCoefficient() {
		return diffusionCoefficient;
	}

	@Override
	public Double getDefaultConsumption() {
		return defaultConsumption;
	}

	@Override
	public Double getDefaultProduction() {
		return defaultProduction;
	}

	@Override
	public String getInputNode() {
		return inputNode;
	}

	public static List<Diffusible> parseDiffusibleFile(String diffusibleFile) {
			Reader in = null;
			try {
				in = new FileReader(DiffusibleBase.class.getClassLoader().getResource(diffusibleFile).getPath());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			CSVParser records = null;
			try {
				records = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(in);
			} catch (IOException e) {
				e.printStackTrace();
			}

			Map<Integer, String> headersByIndexes = new HashMap<>(); //the parsed header map has the name of the headers as keys and their indexes as values, we need it the other way around.
			for (String s : records.getHeaderMap().keySet()) {
				headersByIndexes.put(records.getHeaderMap().get(s), s);
			}

			List<Diffusible> list = new ArrayList<>();
			for (CSVRecord record : records) { //every record (row) is a diffusible substance
				Diffusible diffusible = new DiffusibleBase(record.get("name"), record.get("input-node"), Double.valueOf(record.get("initial-concentration")), Double.valueOf(record.get("probability-modifier")),
						Double.valueOf(record.get("diffusion-coefficient")), Double.valueOf(record.get("consumption")), Double.valueOf(record.get("production")));

				String type = record.get("type");
				if(type.equals("nutrient")) diffusible = new Nutrient(diffusible);
				else if (type.equals("factor")) {
					Factor factor = new Factor(diffusible);
					factor.setOutputNode(record.get("output-node"));
					diffusible = factor;
				}
				else if (type.equals("drug")) diffusible = new Drug(diffusible);
				else throw new IllegalArgumentException("Incorrect diffusible file");

				list.add(diffusible);
				System.out.println(diffusible.toString());
			}
			return list;
	}

	@Override
	public String toString() {
		return "DiffusibleBase{" +
				"name='" + name + '\'' +
				", initialConcentration=" + initialConcentration +
				", defaultProbabilityModifier=" + defaultProbabilityModifier +
				", diffusionCoefficient=" + diffusionCoefficient +
				", defaultConsumption=" + defaultConsumption +
				", defaultProduction=" + defaultProduction +
				", inputNode='" + inputNode + '\'' +
				'}';
	}
}
