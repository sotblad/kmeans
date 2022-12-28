package controller;

import java.util.ArrayList;

import controller.FileProcessor.ReadFromFile;
import controller.classifications.NineClass;
import model.KMeans;

public class kmeans {
	private static int M;
	
	public static void main(String[] args) {
		ArrayList<ArrayList<Float>> points;
		M = 3;
		
//		// generatePoints
//		points = NineClass.createPoints();
		
		// loadFromFile
		points = ReadFromFile.parse("points.txt");
		
		KMeans kmeans = new KMeans(M, points, 700);
		
		kmeans.start();
	}
}
