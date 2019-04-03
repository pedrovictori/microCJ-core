package diffusion;

import graph.Input;
import graph.Node;

public class Factor extends DiffusibleBase{
	private String outputNode;

	public Factor(String name, String inputNode, String outputNode, Double initialConcentration, Double defaultProbabilityModifier, Double diffusionCoefficient, Double defaultConsumption, Double defaultProduction) {
		super(name, inputNode, initialConcentration, defaultProbabilityModifier, diffusionCoefficient, defaultConsumption, defaultProduction);
		this.outputNode = outputNode;
	}

	public Factor(Diffusible diffusible) {
		super(diffusible);
	}

	public String getOutputNode() {
		if (outputNode == null) {
			throw new IllegalStateException();
		}
		return outputNode;
	}

	public void setOutputNode(String outputNode) {
		this.outputNode = outputNode;
	}
}
