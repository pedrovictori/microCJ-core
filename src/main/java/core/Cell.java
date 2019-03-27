package core;
/*
Copyright 2019 Pedro Victori

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */


import geom.Point3D;
import graph.GeneGraph;
import graph.MutationGroup;
import graph.Node;
import update.Updatable;
import update.Update;
import update.UpdateFlag;

import java.util.Optional;

enum CellState{
    NORMAL, //functioning as default
    ARRESTED, //will be unresponsive for an interval of time
    NECROTIC, //is dead but still occupying space
    DEAD; //is dead and will be removed from the simulation as soon as possible
}
/**
 * Base class for Cell objects.
 * @author Pedro Victori
 */
public class Cell extends Identifier implements Updatable {
    private boolean alive = true;
    private int age = 0;
    private GeneGraph geneGraph;
    private Point3D location;
    private CellState cellState = CellState.NORMAL;
    private int arrestCountdown;
    private static final int DEFAULT_ARREST_COUNTDOWN = 10;

    /**
     * the cell radius
     */
    private double radius;
    private final static double DEFAULT_RADIUS = 5;
    private MutationGroup mutationGroup;

    public Cell() {
        geneGraph = GeneGraph.RandomlyActivatedGraph(getId()).turnNode("Oxygen_supply", true); //turn oxygen on to avoid necrosis while oxygen diffusion gets implemented todo remove this when no longer necessary
        geneGraph.update(); //update to map the nodes current state in this first run (see GeneGraph.update method body)
        radius = DEFAULT_RADIUS;
    }

    public Cell(Point3D location) {
        this();
        this.location = location;
    }

    public Cell(double radius) {
        this();
        this.radius = radius;
    }

    public Cell(Point3D location, double radius) {
        this(location);
        this.radius = radius;
    }

    /**
     * Return the cell's mutation group inside an Optional. It is possible for the cell to not have a mutation group, in which case it behaves as a wild type cell.
     * @return an Optional MutationGroup
     */
    Optional<MutationGroup> getMutationGroup() {
        return Optional.of(mutationGroup);
    }

    /**
     * Sets the cell's mutation group and applies its mutations to the cell's gene graph.
     * @param mutationGroup the new mutation group for the cell.
     */
    void setMutationGroup(MutationGroup mutationGroup) {
        for (String tag : mutationGroup.getMutations().keySet()) {
            applyMutation(tag, mutationGroup.getMutations().get(tag));
        }
        this.mutationGroup = mutationGroup;
    }

    /**
     * Applies a mutation to the gene graph. Mutations are permanent changes in the activation status of a node.
     * @param node the node to which the mutation will be applied
     * @param value the type of mutation: true means activation, false means deactivation and null means no effect (wild type).
     */
    void applyMutation(String tag, Boolean value) {
        getGeneGraph().applyMutation(tag, value);
    }


    public static double getDefaultRadius() {
        return DEFAULT_RADIUS;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public Point3D getLocation() {
        return location;
    }

    public void setLocation(Point3D location) {
        this.location = location;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public int getAge() {
        return age;
    }

    public GeneGraph getGeneGraph() {
        return geneGraph;
    }

    private void resetArrestCountdown() {
        arrestCountdown = DEFAULT_ARREST_COUNTDOWN;
    }

    private void arrestCountdown() {
        arrestCountdown--;
        if (arrestCountdown == 0) {
            cellState = CellState.NORMAL;
        }
    }

    private boolean isArrested() {
       return cellState.equals(CellState.ARRESTED);
    }

    void arrest() {
        resetArrestCountdown();
        cellState = CellState.ARRESTED;
    }

    void necrotize() {
        geneGraph = null;
        cellState = CellState.NECROTIC;
        tryToAddUpdate(UpdateFlag.NECROTIC_CELL);
    }

    Fate update() {
        if (cellState.equals(CellState.NECROTIC) || cellState.equals(CellState.DEAD)) return Fate.NO_FATE_REACHED; //no fate to execute if cell is dead
        else{
            arrestCountdown(); //do this before returning a fate in case the cell goes back to normal in this update step
            Fate computedFate = getGeneGraph().update();
            if ((computedFate.equals(Fate.PROLIFERATION) && cellState.equals(CellState.ARRESTED))) return Fate.GROWTH_ARREST;
            else return computedFate;
        }
    }


    private void tryToAddUpdate(UpdateFlag flag) {
        try {
            World.INSTANCE.addToUpdateQueues(new Update<>(flag, this));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static Cell copy(Cell cell) {
        return new Cell(cell.getRadius());
    }
}