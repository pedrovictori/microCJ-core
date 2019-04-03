package diffusion;

import graph.Input;

public interface Diffusible {
	/**
	 * Returns the name of the diffusible substance
	 * @return a String with the name of the substance
	 */
	String getName();

	/**
	 * Returns the initial concentration in every voxel.
	 * @return a Double representing the initial concentration
	 */
	Double getInitialConcentration();

	/**
	 * Returns the default modifier to the probability that the associated input node becomes active in presence of this substance. The other variable is concentration
	 * @return a Double representing the default probability modifier
	 */
	Double getDefaultProbabilityModifier();

	/**
	 * Returns the diffusion coefficient in m^2/s.
	 * @return a Double representing the diffusion coefficient in m^2/s.
	 */
	Double getDiffusionCoefficient();

	/**
	 * Returns the default consumption of this substance by each cell
	 * @return a Double representing the default consumption of this substance
	 */
	Double getDefaultConsumption();

	/**
	 * Returns the default production of this substance by each cell
	 * @return a Double representing the default production of this substance
	 */
	Double getDefaultProduction();

	/**
	 *  Returns the input node activated by this substance.
	 * @return
	 */
	Input getInputNode();
}
