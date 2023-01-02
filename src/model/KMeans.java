package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.swing.SwingUtilities;

import visualizer.ScatterPlot;

public class KMeans {
	
	private int M;
	private ArrayList<ArrayList<Float>> points;
	private int maxIterations;
	private ArrayList<ArrayList<Float>> centroids;
	private ArrayList<Integer> clusters;
	private float best = Float.POSITIVE_INFINITY;
	private ArrayList<ArrayList<Float>> bestCentroids;
	
	public KMeans(int M, ArrayList<ArrayList<Float>> points, int maxIterations) {
        this.M = M;
        this.points = points;
        this.maxIterations = maxIterations;
    }
	
	public void initCentroids() {
		centroids = new ArrayList<>();
		Random random = new Random();
		int max = points.size();
		
		for(int i = 0;i<M;i++) {
			centroids.add(points.get(random.nextInt(max)));
		}
	}
	
	public float start() {
		initCentroids();
		
		float prevWCSS = 0;

		while(maxIterations != 0) {
			
			clusters = new ArrayList<>();
			for(int i = 0;i<points.size();i++) {
				float[] tmpDistance = new float[M];
				for(int j = 0;j<M;j++) {
					float dist = euclideanDistance(points.get(i), centroids.get(j));
					tmpDistance[j] = dist;
				}
				clusters.add(minIndex(tmpDistance));
			}
			
			float WCSS = WCSS();
			System.out.println("WCSS= " + WCSS);
			
			if(WCSS < best) {
				best = WCSS;
				bestCentroids = centroids;
			}
			
			if(WCSS == prevWCSS) break;
			prevWCSS = WCSS;
			
			for(int i = 0;i<M;i++) {
				calcNewCentroid(i);
			}
			maxIterations--;
		}
		
		this.centroids = bestCentroids;
		return best;
	}
	
	public float SE(int clusterNumber) {
		ArrayList<Integer> clusterPoints = getCluster(clusterNumber);
		ArrayList<Float> clusterCentroid = centroids.get(clusterNumber);
		
		float res = 0;
		
		for(int i = 0;i<clusterPoints.size();i++) {
			ArrayList<Float> currentPoint = points.get(clusterPoints.get(i));
			res += euclideanDistance(currentPoint, clusterCentroid);
		}
		
		return res;
	}
	
	public float WCSS() {
		float res = 0;
		
		for(int i = 0;i<M;i++) {
			res += SE(i);
		}
		
		return res;
	}
	
	public void calcNewCentroid(int clusterNumber) {
		ArrayList<Integer> clusterPoints = getCluster(clusterNumber);
		ArrayList<Float> res = new ArrayList<>();
		
		float sumX = 0;
		float sumY = 0;
		
		for(int i = 0;i<clusterPoints.size();i++) {
			ArrayList<Float> currentPoint = points.get(clusterPoints.get(i));
			sumX += currentPoint.get(0);
			sumY += currentPoint.get(1);
		}
		
		res.add(sumX/clusterPoints.size());
		res.add(sumY/clusterPoints.size());
		
		this.centroids.set(clusterNumber, res);
	}
	
	public ArrayList<Integer> getCluster(int cluster) {
		ArrayList<Integer> clusterPoints = new ArrayList<>();
		
		for(int i = 0;i<clusters.size();i++) {
			if(clusters.get(i) == cluster) clusterPoints.add(i);
		}
		
		return clusterPoints;
	}
	
	public float euclideanDistance(ArrayList<Float> point1, ArrayList<Float> point2) {
		return (float) Math.sqrt(Math.pow(point2.get(0)-point1.get(0), 2) + Math.pow(point2.get(1)-point1.get(1), 2));
	}
	
	public void visualize() {
		SwingUtilities.invokeLater(() -> {
	    	ScatterPlot example = new ScatterPlot("M=" + M + " k-means ScatterPlot", createDataset(), bestCentroids);
	      example.setSize(800, 400);
	      example.setLocationRelativeTo(null);
	      example.setVisible(true);
	    });
	}
	
	public HashMap<String, ArrayList<ArrayList<Float>>> createDataset() {
		HashMap<String, ArrayList<ArrayList<Float>>> data = new HashMap<>();
		
		for(int i = 0;i<M;i++) {
			ArrayList<Integer> cluster = getCluster(i);
			ArrayList<ArrayList<Float>> tmpPoints = new ArrayList<>();
			for(int j = 0;j<cluster.size();j++) {
				tmpPoints.add(points.get(cluster.get(j)));
			}
			data.put("C"+i, tmpPoints);
		}
		
		return data;
	}
	
	public int minIndex(float[] numbers) {
		float min = (float) Double.POSITIVE_INFINITY;
		int minIndex = -1;
	
		for(int i = 0;i<numbers.length;i++) {
			if(numbers[i] < min) {
				min = numbers[i];
				minIndex = i;
			}
		}

		return minIndex;
	}

	public int getM() {
		return M;
	}

	public void setM(int m) {
		M = m;
	}

	public ArrayList<ArrayList<Float>> getPoints() {
		return points;
	}

	public void setPoints(ArrayList<ArrayList<Float>> points) {
		this.points = points;
	}

	public int getMaxIterations() {
		return maxIterations;
	}

	public void setMaxIterations(int maxIterations) {
		this.maxIterations = maxIterations;
	}

	public ArrayList<ArrayList<Float>> getCentroids() {
		return centroids;
	}

	public void setCentroids(ArrayList<ArrayList<Float>> centroids) {
		this.centroids = centroids;
	}
}
