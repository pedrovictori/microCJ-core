package graph;

import core.Identifier;
import core.Node;

public class GeneLink extends Identifier {
	private Node target;
	private Node source;

	public GeneLink(Node target, Node source){
		this.target = target;
		this.source = source;
	}

	public Node getTarget() {
		return target;
	}

	public Node getSource() {
		return source;
	}

	@Override
	public String toString() {
		return ""; //todo remove this later, just for visualization testing
	}
}
