package visualizer;
import java.awt.Color;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.util.ShapeUtilities;

public class ScatterPlot extends JFrame{
	private static final long serialVersionUID = 1L;

	  public ScatterPlot(String title, HashMap<String, ArrayList<ArrayList<Float>>> data, ArrayList<ArrayList<Float>> centroids) {
	    super(title);
	    
	    XYDataset dataset = createDataset(data, centroids);

	    JFreeChart chart = ChartFactory.createScatterPlot(
	    	new TreeSet<String>(data.keySet()).toString(),
	        "X-Axis", "Y-Axis", dataset, PlotOrientation.VERTICAL, false, false,
            false);

	    
	    Shape cross = ShapeUtilities.createRegularCross(3, 1);
	    Shape downTriangle = ShapeUtilities.createDownTriangle(3);
	    XYPlot plot = (XYPlot)chart.getPlot();
	    plot.setBackgroundPaint(new Color(255,228,196));
	    XYItemRenderer renderer = plot.getRenderer();
	    for(int i = 0;i<data.keySet().size();i++) {
	    	renderer.setSeriesShape(i, cross);
	    }
	    
	    renderer.setSeriesShape(data.keySet().size()+1, downTriangle);
	    
	    ChartPanel panel = new ChartPanel(chart);
	    setContentPane(panel);
	  }
	  
	  private XYDataset createDataset(HashMap<String, ArrayList<ArrayList<Float>>> data, ArrayList<ArrayList<Float>> centroids) {
		    XYSeriesCollection dataset = new XYSeriesCollection();
		    
		    for(String key: new TreeSet<String>(data.keySet())) {
		    	XYSeries tmpSeries = new XYSeries(key);
		    	for(int i = 0;i < data.get(key).size();i++) {
		    		tmpSeries.add(data.get(key).get(i).get(0), data.get(key).get(i).get(1));
		    	}
		    	dataset.addSeries(tmpSeries);
		    }
		    
		    XYSeries centroidsSeries = new XYSeries("Centroids");
		    for(int i = 0;i<centroids.size();i++) {
		    	centroidsSeries.add(centroids.get(i).get(0), centroids.get(i).get(1));
		    }
		    dataset.addSeries(centroidsSeries);
		    
		    return dataset;
	  }
}
