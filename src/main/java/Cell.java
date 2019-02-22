import java.util.List;

public class Cell extends Identifier{
    private boolean alive = true;
    private int age = 0;
    private List<Gene> genes;

    public Cell() {
    }

    public boolean isAlive() {
        return alive;
    }

    public int getAge() {
        return age;
    }

    public List<Gene> getGenes() {
        return genes;
    }
}
