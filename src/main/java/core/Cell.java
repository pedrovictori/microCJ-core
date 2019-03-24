package core;
/*
Copyright 2019 Pedro Victori

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */


import geom.Point3D;
import graph.GeneGraph;
import update.Updatable;
import update.Update;
import update.UpdateFlag;

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

    public Cell() {
        geneGraph = GeneGraph.RandomlyActivatedGraph(getId()).turnNode("Oxygen_supply", true); //turn oxygen on to avoid necrosis while oxygen diffusion gets implemented todo remove this when no longer necessary
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
        if (cellState.equals(CellState.NECROTIC) || cellState.equals(CellState.DEAD)) return null;
        else{
            arrestCountdown();
            getGeneGraph().update();
            Fate computedFate = getGeneGraph().getCurrentlyActiveFate();
            if ((computedFate != null) && (computedFate.equals(Fate.PROLIFERATION) && cellState.equals(CellState.ARRESTED))) return Fate.GROWTH_ARREST;
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