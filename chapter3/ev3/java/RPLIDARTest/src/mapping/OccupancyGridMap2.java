package mapping;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.Queue;

import lejos.robotics.geometry.Point;
import lejos.robotics.navigation.Pose;


public class OccupancyGridMap2 {
	
	
	private int xMax;
	private int yMax;
	private int g; 
	
	private int[][] M; //Map
	private int[][] C; //Counter
	private int[][] P; //Pose Cell
	private String path;
	private String file;
	private Pose current;
	
	private final int halfBeamWidth = 25;
	private final int BUFFER = 0;
	
	public OccupancyGridMap2(final int maxx, final int maxy, final int granularity){
		xMax = maxx;
		yMax = maxy;
		g = granularity;
		
		M = new int[xMax / g][xMax / g];
		C = new int[xMax / g][xMax / g];
		P = new int[xMax / g][xMax / g];
		initialize();
	}
	
	private void initialize(){
		for(int y = 0; y< M.length; y++){
			for(int x = 0; x < M.length; x++){
				M[x][y] = 0;
				C[x][y] = 0;
				P[x][y] = 0;
			}
		}
	}

	public int[][] getMap(){
		return M;
	}
	
	private void addValueAtCell(int x, int y, int v) {
		if(x < 0 || x >= M.length || y < 0 || y >= M[0].length) {
			return;
		} else {
			M[x][y] += v;
		}
	}
	
	private void addValueAtCoord(float x, float y, int v) {
		addValueAtCell((int) (x/g), (int) (y/g), v);
	}
	
	public void updateValue(Pose robotPose, int angle, int distance) {
		
		//System.out.println("Distance" + distance);
		//System.out.println("Angle" + robotPose.getHeading() + angle);
		//System.out.println("Angle" + angle);
		
		Point rayEnd = robotPose.pointAt(distance, robotPose.getHeading() + angle);
		//Point rayEnd = robotPose.pointAt(distance, angle);
		
		int x0 = (int) (robotPose.getX());
		int y0 = (int) (robotPose.getY());
		int x1 = (int) (rayEnd.getX());
		int y1 = (int) (rayEnd.getY());
		
		//System.out.println("X0 " + x0);
		//System.out.println("Y0 " + y0);
		//System.out.println("X1 " + x1);
		//System.out.println("Y1 " + y1);
		
		
		// Mark cells around beam end as filled
		for(int cx = -BUFFER; cx <= BUFFER; cx++) {
			for(int cy = -BUFFER; cy <= BUFFER; cy++) {
				addValueAtCell(cx + x1/g, cy + y1/g, -1);
			}
		}
		
		// Mark beam as clear
		
		if(x0 > x1) {
			int t = x0;
			x0 = x1;
			x1 = t;
		}
		if(y0 > y1) {
			int t = y0;
			y0 = y1;
			y1 = t;
		}

		float m = (y1 - y0)/(x1 - x0 + 0.0001f);
		
		// We approximate a wide beam with ends parallel to the axes
		// Mark all cells up to beam end as clear
		for(float x = x0, y = y0; x <= x1; x+=g) {
			y += m*g;
			
			if(m > 1) {
				addValueAtCoord(x-halfBeamWidth, y, 1);
				addValueAtCoord(x+halfBeamWidth, y, 1);
			} else {
				addValueAtCoord(x, y-halfBeamWidth, 1);
				addValueAtCoord(x, y+halfBeamWidth, 1);
			}
		}
	}
	
	/**
	 * 
	 * yxxxxxx
	 * yO###xx
	 * yxxxxxx
	 * yxxxxxx
	 * 
	 * @param pose
	 * @param measure
	 */
	public void update(Pose pose, float measure){
			this.current = pose;
			
			float xMeasured = 0;
			float yMeasured = 0;
			int xCell = 0;
			int yCell = 0;
			int xMeasuredCell = 0;
			int yMeasuredCell = 0;
			//TODO Constructor parameter
			int THRESHOLD_DETECTION  = 90;
			
			float theta = pose.getHeading();
			
			//TODO Use a new structure to send angle + measure
			if(theta == 0){
				xMeasured = pose.getX() + measure;
				yMeasured = pose.getY();				
			}else if(theta == 90){
				xMeasured = pose.getX();
				yMeasured = pose.getY() + measure;				
			}else if(theta == 180){
				xMeasured = pose.getX() - measure;
				yMeasured = pose.getY();				
			}

			xCell = (int) Math.floor(pose.getX()/this.g);
			yCell = (int) Math.floor(pose.getY()/this.g);

			P[xCell][yCell] = 1;
			
			xMeasuredCell = (int) Math.floor(xMeasured/this.g);
			yMeasuredCell = (int) Math.floor(yMeasured/this.g);

			//Adjust negative values
			if(xMeasuredCell < 0){
				xMeasuredCell = 0;
			}
			
			int cellCounter = 0;
			
			if(theta == 0){
				cellCounter = xMeasuredCell - xCell;
	
				if(cellCounter > 0){
					//Forward
					for(int i=0; i<cellCounter;i++){
						C[xCell+i][yCell]++;
					}
				}else{
					
				}
			}else if(theta == 90){
				cellCounter = yMeasuredCell - yCell;
				
				if(cellCounter > 0){
					//Forward
					for(int i=0; i<cellCounter;i++){
						C[xCell][yCell+i]++;
					}
				}else{
					
				}
			}else if(theta == 180){
				cellCounter = Math.abs(xMeasuredCell - xCell);
				//System.out.println(yMeasuredCell);
				//System.out.println(xMeasuredCell);
				//System.out.println(xCell);
				
				//System.out.println("COUNTER " + cellCounter);
				
				for(int i=cellCounter; i>0;i--){
					C[xCell-i][yCell]++;
				}
				
			}else if(theta == 270){
				cellCounter = Math.abs(xMeasuredCell - xCell);
				//System.out.println(yMeasuredCell);
				//System.out.println(xMeasuredCell);
				//System.out.println(xCell);
				
				//System.out.println("COUNTER " + cellCounter);
				
				for(int i=cellCounter; i>0;i--){
					C[xCell][yCell-i]++;
				}
				
			}
			
			if(measure > 0){
				//No detection
				if(measure >= THRESHOLD_DETECTION){
					M[xMeasuredCell][yMeasuredCell]--;
				}else{
					M[xMeasuredCell][yMeasuredCell]++;
				}
			}
			
			//System.out.println("" + xCell + "," + xCell);
	}

}
