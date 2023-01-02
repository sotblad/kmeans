import java.util.ArrayList;
import javax.swing.SwingUtilities;

import controller.FileProcessor.ReadFromFile;
import controller.classifications.NineClass;
import model.KMeans;
import visualizer.LinePlot;

public class kmeans {
	
	public static void main(String[] args) {
		ArrayList<ArrayList<Float>> points;
		
//		// generatePoints
		points = NineClass.createPoints();
		
		// loadFromFile
//		points = ReadFromFile.parse("points.txt");
		
		KMeans kmeans;
		
		int[] Ms = new int[]{3,6,9,12};
		float[] WCSS = new float[Ms.length];
		
		for(int i = 0;i<Ms.length;i++) {
			System.out.println("\nRunning KMeans for M=" + Ms[i]);
			kmeans = new KMeans(Ms[i], points, 15);
			
			WCSS[i] = kmeans.start();
			kmeans.visualize();
		}
		
		visualizeError(Ms, WCSS);
	}
	
	public static void visualizeError(int[] Ms, float[] WCSS) {
		SwingUtilities.invokeLater(() -> {
			LinePlot example = new LinePlot("Error-M LinePlot", Ms, WCSS);
	      example.setSize(800, 400);
	      example.setLocationRelativeTo(null);
	      example.setVisible(true);
	    });
	}
}
