package diffusion;

import graph.Input;

public class Nutrient extends DiffusibleBase {

	public Nutrient(String name, Input inputNode, Double initialConcentration, Double defaultProbabilityModifier, Double diffusionCoefficient, Double defaultConsumption, Double defaultProduction) {
		super(name, inputNode, initialConcentration, defaultProbabilityModifier, diffusionCoefficient, defaultConsumption, defaultProduction);
	}

	public Nutrient(Diffusible diffusible) {
		super(diffusible);
	}
}
