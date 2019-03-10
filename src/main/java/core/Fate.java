package core;

import graph.GeneLink;

public class Fate extends Identifier implements InNode{
	private String tag;
	private boolean active;
	private Rule rule;

	public Fate(String tag) {
		this.tag = tag;
	}

	public Fate(String tag, boolean active) {
		this.tag = tag;
		this.active = active;
	}

	public Fate(String tag, String rule) {
		this(tag);
		this.rule = Rule.ruleParser(this, rule);
	}


	public Fate(String tag, String rule, boolean active) {
		this(tag, active);
		this.rule = Rule.ruleParser(this, rule);
	}

	@Override
	public Rule getRule() {
		return rule;
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
