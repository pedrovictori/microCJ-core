import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.io.*;

import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class GraphTest {
	public static void main(String[] args) {
		GeneGraph geneGraph = new GeneGraph(1);

		//export and print in DOT format
		GraphExporter<Gene, GeneLink> exporter = createDotExporter();
		Writer writer = new StringWriter();

		try {
			exporter.exportGraph(geneGraph.getGraph(), writer);
		} catch (ExportException e) {
			e.printStackTrace();
		}

		System.out.println(writer.toString());
	}

	private static GraphExporter<Gene, GeneLink> createDotExporter()
	{
		/*
		 * Create vertex id provider.
		 *
		 * The exporter needs to generate for each vertex a unique identifier.
		 */
		ComponentNameProvider<Gene> vertexIdProvider = v -> v.getTag();

		/*
		 * Create vertex label provider.
		 *
		 * The exporter may need to generate for each vertex a (not necessarily unique) label. If
		 * null the exporter does not output any labels.
		 */
		ComponentNameProvider<Gene> vertexLabelProvider = v -> null;

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
		DOTExporter<Gene,
				GeneLink> exporter = new DOTExporter<>(vertexIdProvider, vertexLabelProvider, null);

		return exporter;
	}

}
