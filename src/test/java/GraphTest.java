/**
 * @author Pedro Victori
 */
/*
Copyright 2019 Pedro Victori

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import graph.Gene;
import graph.GeneGraph;
import graph.GeneLink;
import graph.Node;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.io.ComponentNameProvider;
import org.jgrapht.io.DOTExporter;
import org.jgrapht.io.ExportException;
import org.jgrapht.io.GraphExporter;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.DepthFirstIterator;

import java.io.StringWriter;
import java.io.Writer;

public class GraphTest {
	public static void main(String[] args) {
		GeneGraph geneGraph = new GeneGraph(GeneGraph.getDefaultFile());
		iterateBFromInputs(geneGraph);
	}

	private static void iterateDFromInputs(GeneGraph geneGraph) {
		int i = 0;

		DepthFirstIterator<Node, GeneLink> iterator = new DepthFirstIterator<>(geneGraph.getGraph(), geneGraph.getInputs());
		while (iterator.hasNext()) {
			Node node = iterator.next();
			System.out.println(i + " " + node.getTag());
			i++;
		}
	}

	private static void iterateRandom(GeneGraph geneGraph) {
		int i = 0;
		for (Node node : geneGraph.getGraph().vertexSet()) {
			System.out.println(i + " " + node.getTag());
			i++;
		}
	}

	private static void iterateBFromInputs(GeneGraph geneGraph) {
		Node root = new Gene("root"); //todo create type for this
		geneGraph.getGraph().addVertex(root);
		for (Node input : geneGraph.getInputs()) {
			geneGraph.getGraph().addEdge(root, input, new GeneLink(root, input));
		}
		int i = 0;
		BreadthFirstIterator<Node, GeneLink> iterator = new BreadthFirstIterator<>(geneGraph.getGraph(), root);
		while (iterator.hasNext()) {
			Node node = iterator.next();
			System.out.println(i + " " + node.getTag() + " " + iterator.getDepth(node));
			i++;
		}
	}

	private static void printAsDot(GeneGraph geneGraph) {
		//export and print in DOT format
		GraphExporter<Node, GeneLink> exporter = createDotExporter();
		Writer writer = new StringWriter();

		try {
			exporter.exportGraph(geneGraph.getGraph(), writer);
		} catch (ExportException e) {
			e.printStackTrace();
		}

		System.out.println(writer.toString());
	}

	private static GraphExporter<Node, GeneLink> createDotExporter()
	{
		/*
		 * Create vertex id provider.
		 *
		 * The exporter needs to generate for each vertex a unique identifier.
		 */
		ComponentNameProvider<Node> vertexIdProvider = v -> v.getTag();

		/*
		 * Create vertex label provider.
		 *
		 * The exporter may need to generate for each vertex a (not necessarily unique) label. If
		 * null the exporter does not output any labels.
		 */
		ComponentNameProvider<Node> vertexLabelProvider = v -> null;

		/*
		 * Create edge id provider.
		 *
		 * The exporter needs to generate for each edge a unique identifier.
		 */
		ComponentNameProvider<GeneLink> edgeIdProvider = e -> e.getId().toString();

		/*
		 * Create edge label provider.
		 *
		 * The exporter may need to generate for each edge a (not necessarily unique) label. If null
		 * the exporter does not output any labels.
		 */
		ComponentNameProvider<DefaultWeightedEdge> edgeLabelProvider = null;

		/*
		 * Create the exporter
		 */
		DOTExporter<Node,
				GeneLink> exporter = new DOTExporter<>(vertexIdProvider, vertexLabelProvider, null);

		return exporter;
	}

}
