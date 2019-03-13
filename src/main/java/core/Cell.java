package core;

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
        geneGraph = new GeneGraph(getId());
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
}