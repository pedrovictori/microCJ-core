package diffusion;

import graph.Input;
import graph.Node;

public class Factor implements Diffusible {
	private String name;
	private Double initialConcentration;
	private Double defaultProbabilityModifier;
	private Double diffusionCoefficient;
	private Double defaultConsumption;
	private Double defaultProduction;
	private Input inputNode;
	private Node outputNode;

	//todo builder pattern for constructor
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
	public Input getInputNode() {
		return inputNode;
	}

	public Node getOutputNode() {
		return outputNode;
	}
}
