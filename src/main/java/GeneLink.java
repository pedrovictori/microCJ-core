public class GeneLink extends Identifier{
	private Gene target;
	private Gene source;

	public GeneLink(Gene target, Gene source){
		this.target = target;
		this.source = source;
	}

	public Gene getTarget() {
		return target;
	}

	public Gene getSource() {
		return source;
	}
}
