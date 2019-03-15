package core;

import geom.Distributor;
import geom.Point3D;
import geom.RandomRecursiveDistributor;

import java.util.*;

public class Tumor {
	private List<Cell> cellList = new ArrayList<>();

	private Set<Point3D> cellLocations;
	private int initialNumber;
	private Distributor distributor;
	/**
	 * Maximum number of cells in the tumor
	 */
	private int maxSize; //todo implement

	public Tumor(int initialNumber, double cellRadius) {
		this.initialNumber = initialNumber;
		distributor = new RandomRecursiveDistributor(); //todo choose in user settings
		cellLocations = distributor.populate(initialNumber, cellRadius);

		for (Point3D location : cellLocations) {
			cellList.add(new Cell(location, cellRadius));
		}
	}

	public Tumor(int initialNumber) {
		this(initialNumber, Cell.getDefaultRadius());
	}

	public List<Cell> getCellList() {
		return cellList;
	}

	public Set<Point3D> getCellLocations() {
		return cellLocations;
	}

	public int getInitialNumber() {
		return initialNumber;
	}

	public int getMaxSize() {
		return maxSize;
	}
}
