package core;
/**
 * @author Pedro Victori
 */
/*
Copyright 2019 Pedro Victori

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */


import geom.Distributor;
import geom.Point3D;
import geom.RandomRecursiveDistributor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

	public void updateAllCells() {
		for (Cell cell : cellList) {
			Fate fate = cell.update();
			if (fate != null) {
				fate.getExecutionRule().execute(cell, this);
			}
		}
	}

	/**
	 * Checks if there is available space next to the cell, if so it creates a copy  of the cell and places it next to the original cell.
	 * @param cell the original cell to be copied
	 */
	void proliferate(Cell cell) {
		if(cellList.size() < maxSize){
			Point3D location = distributor.locateEmptySpotNextTo(cell.getLocation(), cell.getRadius() * 2, cellLocations);
			if (location != null) {
				Cell newCell = Cell.copy(cell);
				newCell.setLocation(location);
				cellList.add(newCell);
				cellLocations.add(location);

			}
		}
	}

	void apoptose(Cell cell) {
		cellList.remove(cell);
	}
}
