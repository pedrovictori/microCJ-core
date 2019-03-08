import com.mxgraph.layout.*;
import com.mxgraph.swing.*;
import graph.Gene;
import graph.GeneGraph;
import graph.GeneLink;
import org.jgrapht.*;
import org.jgrapht.ext.*;
import org.jgrapht.graph.*;

import javax.swing.*;
import java.awt.*;

/**
 * A demo applet that shows how to use JGraphX to visualize JGraphT graphs. Applet based on
 * JGraphAdapterDemo.
 *
 */
public class VisualizationTest extends JApplet
{
    private static final long serialVersionUID = 2202072534703043194L;

    private static final Dimension DEFAULT_SIZE = new Dimension(530, 320);

    /**
     * An alternative starting point for this demo, to also allow running this applet as an
     * application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args)
    {
        VisualizationTest applet = new VisualizationTest();
        applet.init();

        JFrame frame = new JFrame();
        frame.getContentPane().add(applet);
        frame.setTitle("JGraphT Adapter to JGraphX Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void init()
    {
        // create a JGraphT graph
        ListenableGraph<Gene, GeneLink> g =
                new DefaultListenableGraph<>(new GeneGraph(1).getGraph());

        // create a visualization using JGraph, via an adapter
        JGraphXAdapter<Gene, GeneLink> jgxAdapter = new JGraphXAdapter<>(g);

        setPreferredSize(DEFAULT_SIZE);
        mxGraphComponent component = new mxGraphComponent(jgxAdapter);
        component.setConnectable(false);
        component.getGraph().setAllowDanglingEdges(false);
        getContentPane().add(component);
        resize(DEFAULT_SIZE);

        // positioning via jgraphx layouts
        int radius = 70;
        mxCircleLayout layout = new mxCircleLayout(jgxAdapter, radius);

        // center the circle
        layout.setX0((DEFAULT_SIZE.width / 2.0) - radius);
        layout.setY0((DEFAULT_SIZE.height / 2.0) - radius);
        layout.setMoveCircle(true);
        layout.execute(jgxAdapter.getDefaultParent());
        // that's all there is to it!...
    }
}
