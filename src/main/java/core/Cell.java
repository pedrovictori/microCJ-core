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
    private double size;
    private final static double DEFAULT_SIZE = 10;

    public Cell() {
        geneGraph = new GeneGraph(getId());
        size = DEFAULT_SIZE;
    }

    public Cell(Point3D location) {
        this();
        this.location = location;
    }

    public Cell(Point3D location, double size) {
        this(location);
        this.size = size;
    }



    public static double getDefaultSize() {
        return DEFAULT_SIZE;
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

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public int getAge() {
        return age;
    }

    public GeneGraph getGeneGraph() {
        return geneGraph;
    }
}