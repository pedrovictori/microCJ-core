package graph;

public class Gene extends Identifier{
    private String tag;
    private boolean active;

    public Gene(String tag) {
        this.tag = tag;
    }

    public Gene(String tag, boolean active) {
        this.tag = tag;
        this.active = active;
    }

    public String getTag() {
        return tag;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public String toString() {
        return tag;
    }
}
