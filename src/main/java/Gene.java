public class Gene extends Identifier{
    private int containingCellId;
    private boolean active;

    public Gene(int containingCellId) {
        this.containingCellId = containingCellId;
    }

    public Gene(int containingCellId, boolean active) {
        this.containingCellId = containingCellId;
        this.active = active;
    }

    public int getContainingCellId() {
        return containingCellId;
    }

    public boolean isActive() {
        return active;
    }
}
