import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.io.*;

import java.io.File;
import java.io.StringWriter;
import java.io.Writer;

public class GeneGraph {
    private int containingCellId;
    private Graph<Gene, GeneLink> graph;

    public GeneGraph(int containingCellId) {
        this.containingCellId = containingCellId;

        graph = new DefaultDirectedGraph<>(GeneLink.class);
        GraphImporter<Gene, GeneLink> importer = createImporter();
        File graphFile = new File(GeneGraph.class.getClassLoader().getResource("mod.graphml").getPath());
        //System.out.println(graphFile.exists());
        try {
            importer.importGraph(graph, graphFile);
        } catch (ImportException e) {
            e.printStackTrace();
        }
    }

    public int getContainingCellId() {
        return containingCellId;
    }

    public Graph<Gene, GeneLink> getGraph() {
        return graph;
    }

    private static GraphImporter<Gene, GeneLink> createImporter(){
        VertexProvider<Gene> vertexProvider = (id, attributes) -> {
            Gene v = new Gene(id);

            //todo: deal with attributes

            return v;
        };

        EdgeProvider<Gene, GeneLink> edgeProvider = (from, to, label, attributes) -> {
            GeneLink link = new GeneLink(from, to);
            return link;
        };

        GraphMLImporter<Gene, GeneLink> importer =
                new GraphMLImporter<>(vertexProvider, edgeProvider);
        importer.setSchemaValidation(false); //todo check later as possible bug source
        return importer;
    }

}