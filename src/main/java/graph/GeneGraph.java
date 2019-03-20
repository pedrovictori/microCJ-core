package graph;
/**
 * @author Pedro Victori
 */
/*
Copyright 2019 Pedro Victori

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import core.*;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.io.*;
import org.jgrapht.traverse.DepthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import java.io.File;
import java.util.*;

public class GeneGraph {
	private int containingCellId;
	private Graph<Node, GeneLink> graph;
	private List<Node> nodes;
	private Map<String, Boolean> currentValues = new HashMap<>();
	Map<Node, Boolean> nextUpdate = new HashMap<>();
	private Fate currentlyActiveFate;

	public GeneGraph(int containingCellId) {
		this.containingCellId = containingCellId;

		graph = new DefaultDirectedGraph<>(GeneLink.class);
		GraphImporter<Node, GeneLink> importer = createImporter();
		File graphFile = new File(GeneGraph.class.getClassLoader().getResource("mod.graphml").getPath());
		//System.out.println(graphFile.exists());
		try {
			importer.importGraph(graph, graphFile);
		} catch (ImportException e) {
			e.printStackTrace();
		}

		for (Node node : getGraph().vertexSet()) {

		}
	}

	public int getContainingCellId() {
		return containingCellId;
	}

	public Graph<Node, GeneLink> getGraph() {
		return graph;
	}

	public Set<GeneLink> getEdges() {
		return getGraph().edgeSet();
	}

	public Set<Node> getNodes() {
		return getGraph().vertexSet();
	}

	private Set<Node> getGenes() {
		Set<Node> set = new HashSet<>();
		for (Node node : getNodes()) {
			if (node instanceof Gene) {
				set.add(node);
			}
		}
		return set;
	}

	public Set<Node> getInputs() {
		Set<Node> set = new HashSet<>();
		for (Node node : getNodes()) {
			if (node instanceof Input) {
				set.add(node);
			}
		}
		return set;
	}

	protected Set<Node> getFates() {
		Set<Node> set = new HashSet<>();
		for (Node node : getNodes()) {
			if (node instanceof FateNode) {
				set.add(node);
			}
		}
		return set;
	}

	/**
	 * When called, update the whole gene network.
	 */
	public void update(){
		/*update all the nodes with the map generated in the last update step
		 *Then, update the map with all the current values of every node, to use them as reference for the next computing of the activation rules.
		 */
		for (Node node : getNodes()) {
			if (!nextUpdate.isEmpty() && node instanceof InNode) {
				node.setActive(nextUpdate.get(node));
			}
			currentValues.put(node.getTag(), node.isActive());

			//store the first activated Fate TODO what happens if several Fates get activated in the same step?
			if (node.isActive() && node instanceof FateNode) {
				setCurrentlyActiveFate(((FateNode)node).getFate());
			}
		}

		//generate a map with all the projected values after the update. First compute them and then use the map to update all of them, so the query order doesn't matter
		for (Node node : getNodes()) {
			if (node instanceof InNode){//if it is not an input
				InNode in = (InNode) node;
				nextUpdate.put(in, in.getRule().checkStatus(currentValues));
			}
		}
	}

	private void setCurrentlyActiveFate(Fate currentlyActiveFate) {
		this.currentlyActiveFate = currentlyActiveFate;
	}

	/**
	 * Returns the Fate activated in the last update step, null if no Fates were activated
	 * @return an active Fate or null if no active Fates are present
	 */
	public Fate getCurrentlyActiveFate() {
		return currentlyActiveFate;
	}

	/**
	 * Return the node with the specified tag
	 * @param tag
	 * @return A Node with that tag if found, null otherwise
	 */
	protected Node findNodeWithTag(String tag) {
		for (Node node : getGraph().vertexSet()) {
			if (node.getTag().equals(tag)) {
				return node;
			}
		}

		return null;
	}

	protected GraphIterator<Node, GeneLink> iteratorFromInputsDown() {
		return new DepthFirstIterator<>(getGraph(), getInputs());
	}

	/**
	 * Factory method for creating a new instance of GeneGraph which inputs and nodes are inactive but some of the genes are active at random
	 *
	 * @param containingCellId
	 * @return
	 */
	public static GeneGraph RandomlyActivatedGraph(int containingCellId) {
		return new GeneGraph(containingCellId).turnNodesOff().activateGenesAtRandom();
	}

	private GeneGraph activateGenesAtRandom() {
		Random r = new Random();
		for (Node node : getGenes()) {
			node.setActive(r.nextBoolean());
		}

		return this;
	}

	private GeneGraph turnNodesOff() {
		for (Node node : getNodes()) {
			node.setActive(false);
		}
		return this;
	}

	/**
	 * Turn an specific node on or off
	 * @throws NullPointerException if no node found with that tag
	 * @param tag
	 * @param on true for turning it on, false for turning it off
	 * @return the GeneGraph with the applied change
	 */
	public GeneGraph turnNode(String tag, boolean on) throws NullPointerException{
		Node node = findNodeWithTag(tag);
		if(node == null) throw new NullPointerException("No Node with that tag");
		else {
			node.setActive(on);
			return this;
		}
	}

	private static GraphImporter<Node, GeneLink> createImporter() {
		VertexProvider<Node> vertexProvider = (id, attributes) -> {
			String shape = attributes.get("MY-SHAPE").getValue();
			Node v;
			if (attributes.containsKey("MY-RULE")) { //it's a gene or a cellular fate
				String rule = attributes.get("MY-RULE").getValue();

				if (shape.equals("rect")) { //it's a fate
					v = new FateNode(id, rule);
				} else {
					v = new Gene(id, rule);
				}
			} else { //it's an input
				v = new Input(id);
			}

			return v;
		};

		EdgeProvider<Node, GeneLink> edgeProvider = (from, to, label, attributes) -> {
			GeneLink link = new GeneLink(to, from, attributes.get("SIGN-OF-LINK").getValue().equals("positive"));
			return link;
		};

		GraphMLImporter<Node, GeneLink> importer =
				new GraphMLImporter<>(vertexProvider, edgeProvider);
		importer.setSchemaValidation(false); //todo check later as possible bug source
		return importer;
	}

}