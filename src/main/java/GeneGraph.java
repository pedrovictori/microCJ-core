import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.io.*;

import java.io.File;
import java.io.StringWriter;
import java.io.Writer;

public class GeneGraph {
    //main method just for testing
    public static void main(String[] args) {
        DefaultDirectedGraph<Gene, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
        SimpleGraphMLImporter importer = new SimpleGraphMLImporter();
        File graphFile = new File(GeneGraph.class.getClassLoader().getResource("regulatoryGraphDefault.graphml").getPath());
        //System.out.println(graphFile.exists());
        try {
            importer.importGraph(graph, graphFile);
        } catch (ImportException e) {
            e.printStackTrace();
        }

        //export to test
        GraphExporter<Gene, DefaultEdge> exporter =
                new DOTExporter<>(null, null, null);
        Writer writer = new StringWriter();
        try {
            exporter.exportGraph(graph, writer);
        } catch (ExportException e) {
            e.printStackTrace();
        }
        System.out.println(writer.toString());

    }
}