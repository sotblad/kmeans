package controller.classifications;

import java.util.ArrayList;
import java.util.Collections;

import controller.FileProcessor.WriteToFile;
import controller.points.Points;

public class NineClass {
	public NineClass() {}
	
	public static ArrayList<ArrayList<Float>> createPoints() {
		Points points = new Points();
		ArrayList<ArrayList<Float>> pointsList = new ArrayList<>();
		
		double[] list = new double[] {
				150, 0.8, 1.2, 0.8, 1.2,
				150, 0, 0.5, 0, 0.5,
				150, 0, 0.5, 1.5, 2,
				150, 1.5, 2, 0, 0.5,
				150, 1.5, 2, 1.5, 2,
				75, 0.8, 1.2, 0, 0.4,
				75, 0.8, 1.2, 1.6, 2,
				75, 0.3, 0.7, 0.8, 1.2,
				75, 1.3, 1.7, 0.8, 1.2,
				150, 0, 2, 0, 2
		};
		
		int cnt = 0;
		for(int i = 0;i<list.length/5;i++) {
			pointsList.addAll(points.generatePoints((int)list[cnt], list[cnt+1], list[cnt+2], list[cnt+3], list[cnt+4]));
			cnt += 5;
		
		}
		Collections.shuffle(pointsList);
		
		WriteToFile.write(pointsList);
		
		return pointsList;
	}
}
