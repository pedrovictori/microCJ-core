package graph;

import core.Identifier;
import core.Node;

public class GeneLink extends Identifier {
	private Node target;
	private Node source;
	private boolean positive;

	public GeneLink(Node target, Node source){
		this.target = target;
		this.source = source;
	}

	public GeneLink(Node target, Node source, boolean positive) {
		this(target, source);
		this.positive = positive;
	}

	public Node getTarget() {
		return target;
	}

	public Node getSource() {
		return source;
	}

	public boolean isPositive() {
		return positive;
	}

	@Override
	public String toString() {
		return ""; //todo remove this later, just for visualization testing
	}
}
