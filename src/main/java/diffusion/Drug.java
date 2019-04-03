package diffusion;

import graph.Input;

public class Drug extends DiffusibleBase{
	public Drug(String name, String inputNode, Double initialConcentration, Double defaultProbabilityModifier, Double diffusionCoefficient, Double defaultConsumption, Double defaultProduction) {
		super(name, inputNode, initialConcentration, defaultProbabilityModifier, diffusionCoefficient, defaultConsumption, defaultProduction);
	}

	public Drug(Diffusible diffusible) {
		super(diffusible);
	}
}
