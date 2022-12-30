package visualizer;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class LinePlot extends JFrame {
	private static final long serialVersionUID = 1L;

	public LinePlot(String title , int[] x, float[] y) {
	      super(title);
	      
	      JFreeChart chart = ChartFactory.createLineChart(
	         title,
	         "Groups count", "Error",
	         createDataset(x, y),
	         PlotOrientation.VERTICAL,
	         true,true,false);
	         
	      ChartPanel panel = new ChartPanel(chart);
	      panel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
	      setContentPane(panel);
	   }

	   private DefaultCategoryDataset createDataset(int[] x, float[] y) {
	      DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
	      for(int i = 0;i<x.length;i++) {
	    	  dataset.addValue((Number)y[i] , "WCSS", x[i]);
	      }

	      return dataset;
	   }
}
