import java.util.List;

public class Cell extends Identifier{
    private boolean alive = true;
    private int age = 0;
    private GeneGraph geneGraph;

    public Cell() {
        geneGraph = new GeneGraph(getId());
    }

    public boolean isAlive() {
        return alive;
    }

    public int getAge() {
        return age;
    }

    public GeneGraph getGeneGraph() {
        return geneGraph;
    }
}
