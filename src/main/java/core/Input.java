package core;

import graph.GeneLink;

public class Input extends Identifier implements Node{
	private String tag;
	private boolean active;

	public Input(String tag) {
		this.tag = tag;
	}

	public Input(String tag, boolean active) {
		this.tag = tag;
		this.active = active;
	}
	@Override
	public String getTag() {
		return tag;
	}

	@Override
	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public boolean isActive() {
		return active;
	}

	@Override
	public GeneLink[] getOutgoingLinks() {
		return new GeneLink[0];
	}

	@Override
	public GeneLink[] getIncomingLinks() {
		return new GeneLink[0];
	}

	@Override
	public String toString() {
		return tag;
	}
}
