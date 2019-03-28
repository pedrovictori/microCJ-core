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
import graph.MutationGroup;
import update.Updatable;
import update.Update;
import update.UpdateFlag;

import java.util.*;

public class Tumor {
	private static final int DEFAULT_INITIAL_SIZE = 100;
	private static final int DEFAULT_MAX_SIZE = 4000;
	private List<Cell> cellList = new ArrayList<>();

	private Set<Point3D> cellLocations;
	private int initialNumber;
	private Distributor distributor;
	private Point3D center;
	private List<String> mutationGroupsNames = new ArrayList<>();
	/**
	 * Maximum number of cells in the tumor
	 */
	private int maxSize;

	public Tumor(int initialNumber, int maxSize, Point3D center, double cellRadius) {
		this.initialNumber = initialNumber;
		this.maxSize = maxSize;
		this.center = center;

		//determine the location for every cell
		distributor = new RandomRecursiveDistributor(); //todo choose in user settings
		cellLocations = distributor.populate(initialNumber, cellRadius);

		for (Point3D location : cellLocations) { //create all initial cells
			cellList.add(new Cell(location, cellRadius));
		}

		//parse the mutation groups
		Map<MutationGroup, Double> mutationGroups = MutationGroup.parseMutationsFile("mutations.csv"); //todo choose file name in user settings

		//put all cells from cell list into a queue to wait to receive their mutation group
		Queue<Cell> cellsWaitingForGroup = new LinkedList<>(cellList);

		//for every mutation group, assign it to the specified percentage of cells
		for (MutationGroup mutationGroup : mutationGroups.keySet()) {
			mutationGroupsNames.add(mutationGroup.getName());
			Double percentage = mutationGroups.get(mutationGroup);
			int cellsInThisGroup = (int) Math.ceil((initialNumber * percentage)/100);
			for (int i = 0; i < cellsInThisGroup; i++) {
				Cell nextCell = cellsWaitingForGroup.poll();
				if (nextCell == null) break; //nextCell will be null if the queue was empty, so we can finish the loop
				nextCell.setMutationGroup(mutationGroup);
			}
		}
	}

	public Tumor(int initialNumber, int maxSize, double cellRadius) {
		this(initialNumber, maxSize, Point3D.ZERO, cellRadius);
	}

	public Tumor(int initialNumber, int maxSize) {
		this(initialNumber, maxSize, Cell.getDefaultRadius());
	}

	public Tumor() {
		this(DEFAULT_INITIAL_SIZE, DEFAULT_MAX_SIZE);
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

	public List<String> getMutationGroupsNames() {
		return mutationGroupsNames;
	}

	void updateAllCells() {
		cellList.parallelStream().forEach(cell -> {
			cell.update().getExecutionRule().execute(cell, this);
		});

		/*
		* Make the necessary changes to the cell list.
		* Doing it after the update step guarantees that there are not co-modification issues and the updating order doesn't matter
		 */
		while (World.INSTANCE.getRemainingUpdates() > 0) {
			Update<UpdateFlag, Updatable> update = World.INSTANCE.getUpdateFromQueue();
			switch (update.getFlag()) {
				case DEAD_CELL:
					//This is done after new cells have already been located, so the order in which cells are looped doesn't matter
					cellLocations.remove(((Cell) update.getUpdatable()).getLocation());
					cellList.remove((Cell) update.getUpdatable());
					break;
				case NECROTIC_CELL:
					//nothing to do
					break;
				case NEW_CELL:
					cellList.add((Cell) update.getUpdatable());
					break;
			}
		}
	}

	/**
	 * Checks if there is available space next to the cell, if so it creates a copy  of the cell and places it next to the original cell.
	 * @param cell the original cell to be copied
	 */
	void proliferate(Cell cell) {
		if(cellList.size() < maxSize){
			/* create a copy to iterate over in the next method in a thread-safe way
			 * todo come back to this as other ways to achieve thread safety might be more efficient */
			Set<Point3D> cellLocationsCopy = new HashSet<>(cellLocations);
			Point3D location = distributor.locateEmptySpotNextTo(cell.getLocation(), cell.getRadius() * 2, cellLocationsCopy);
			if (location != null) { //if there's free space next to this cell
				Cell newCell = Cell.copy(cell);
				newCell.setLocation(location);
				cellLocations.add(location);
				tryToAddCellUpdate(new Update<>(UpdateFlag.NEW_CELL, newCell));
			}
		}
	}

	void apoptose(Cell cell) {
		tryToAddCellUpdate(new Update<>(UpdateFlag.DEAD_CELL, cell));
	}

	private void tryToAddCellUpdate(Update<UpdateFlag, Updatable> update) {
		try {
			World.INSTANCE.addToUpdateQueues(update);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
