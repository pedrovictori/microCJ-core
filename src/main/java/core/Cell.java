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

import geom.Point3D;
import graph.GeneGraph;

public class Cell extends Identifier {
    private boolean alive = true;
    private int age = 0;
    private GeneGraph geneGraph;
    private Point3D location;

    /**
     * the cell radius
     */
    private double radius;
    private final static double DEFAULT_RADIUS = 5;

    public Cell() {
        geneGraph = GeneGraph.RandomlyActivatedGraph(getId());
        radius = DEFAULT_RADIUS;
    }

    public Cell(Point3D location) {
        this();
        this.location = location;
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

    protected void arrest() {
        //TODO implement
    }

    protected void necrotize() {
        //TODO implement
    }

    Fate update() {
        getGeneGraph().update();
        return getGeneGraph().getCurrentlyActiveFate();
    }

    public Cell copy() {
        return null;
        //todo implement
    }
}