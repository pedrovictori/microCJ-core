package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tumor {
	private List<Cell> cellList = new ArrayList<>();

	/**
	 * Key: hashCode of the Point3D center
	 * Value: isEmpty?
	 */
	private Map<Integer, Boolean> occupiedSpaces = new HashMap<>();

	/**
	 * Maximum number of cells in the tumor
	 */
	private int maxSize;

	public List<Cell> getCellList() {
		return cellList;
	}

	public Map<Integer, Boolean> getOccupiedSpaces() {
		return occupiedSpaces;
	}

	public int getMaxSize() {
		return maxSize;
	}
}
