package core;

import graph.GeneLink;

public interface Node extends Identifiable {
	String getTag();

	void setActive(boolean active);

	boolean isActive();

	GeneLink[] getOutgoingLinks();

	GeneLink[] getIncomingLinks();


}
