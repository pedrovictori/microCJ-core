package graph;

import core.*;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.io.*;

import java.io.File;

public class GeneGraph {
    private int containingCellId;
    private Graph<Node, GeneLink> graph;

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

    public Node findNodeWithTag(String tag) {
        for (Node node : getGraph().vertexSet()) {
            if(node.getTag().equals(tag)){
                return node;
            }
        }

        return null;
    }

    private static GraphImporter<Node, GeneLink> createImporter(){
        VertexProvider<Node> vertexProvider = (id, attributes) -> {
            String shape = attributes.get("MY-SHAPE").getValue();
            Node v;
            if(attributes.containsKey("MY-RULE")) { //it's a gene or a cellular fate
                String rule = attributes.get("MY-RULE").getValue();

                if(shape.equals("rect")){ //it's a fate
                    v = new Fate(id, rule);
                }
                else{
                    v = new Gene(id, rule);
                }
            }
            else{ //it's an input
                v = new Input(id);
            }

            return v;
        };

        EdgeProvider<Node, GeneLink> edgeProvider = (from, to, label, attributes) -> {
            GeneLink link = new GeneLink(from, to, attributes.get("SIGN-OF-LINK").getValue().equals("positive"));
            return link;
        };

        GraphMLImporter<Node, GeneLink> importer =
                new GraphMLImporter<>(vertexProvider, edgeProvider);
        importer.setSchemaValidation(false); //todo check later as possible bug source
        return importer;
    }

}