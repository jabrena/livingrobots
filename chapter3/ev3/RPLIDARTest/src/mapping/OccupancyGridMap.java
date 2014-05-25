package mapping;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.Queue;

import lejos.robotics.navigation.Pose;


public class OccupancyGridMap {
	
	
	private int xMax;
	private int yMax;
	private float cellSize; 
	
	private int[][] M; //Map
	private int[][] C; //Counter
	private int[][] P; //Pose Cell
	private String path;
	private String file;
	private Pose current;
	private CycleDetector cd;
	
	public OccupancyGridMap(final int dimension, final float cellSize){
		xMax = dimension;
		yMax = xMax;
		M = new int[xMax][yMax];
		C = new int[xMax][yMax];
		P = new int[xMax][yMax];
		initialize();
		this.cellSize = cellSize;
		cd = new CycleDetector(20);
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

			xCell = (int) Math.floor(pose.getX()/this.cellSize);
			yCell = (int) Math.floor(pose.getY()/this.cellSize);

			P[xCell][yCell] = 1;
			
			xMeasuredCell = (int) Math.floor(xMeasured/this.cellSize);
			yMeasuredCell = (int) Math.floor(yMeasured/this.cellSize);

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
	
	private void store(final String content){
		PrintStream out = null;
		try {
			out = new PrintStream(new FileOutputStream(path + file));
		    out.print(content);
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		finally {
		    if (out != null) out.close();
		}
	}
	
	private String getJSArrayMap(String mapName, int[][] data, boolean reverse){
		StringBuffer sb = new StringBuffer();
		
		sb.append("var " + mapName + " = Array(");
		sb.append("\n");
		
		int counter = 0;
		for(int y = 0; y< data.length; y++){
			sb.append("[");
			for(int x = 0; x< M.length; x++){
				
				if(reverse){
					sb.append(data[y][x]);
				}else{
					sb.append(data[x][y]);					
				}
				
				if(x < data.length-1){
					sb.append(",");
				}
			}
			sb.append("]");
			
			if(counter < data.length-1){
				sb.append(",");
			}
			
			sb.append("\n");
			counter ++;
			
		}
		
		sb.append(");");
		
		return sb.toString();
	}
	
	public void writeJSData(){

		StringBuffer sb = new StringBuffer();
		String mapM = this.getJSArrayMap("mapM", M, false);
		sb.append(mapM);
		
		//MAP2
		sb.append("\n");
		sb.append("\n");
		
		String mapC = this.getJSArrayMap("mapC", C,false);
		sb.append(mapC);		

		//MAP3
		sb.append("\n");
		sb.append("\n");
		
		String mapP= this.getJSArrayMap("mapP", P, false);
		sb.append(mapP);	

		//MAP3
		sb.append("\n");
		sb.append("\n");
		
		String mapR = this.getJSArrayMap("mapR", R, true);
		sb.append(mapR);
		
		//MAP3
		sb.append("\n");
		sb.append("\n");
		sb.append("var lastPose = Array([" + current.getX() + "," + current.getY() + "," + current.getHeading() + "]);");
		
		this.store(sb.toString());
	}

	public void setFilePath(final String path) {
		this.path = path;
	}

	public void setFileName(String file) {
		this.file = file;
	}

	private int[][] R; //Pose Cell
	
	public void setRealMap(int[][] mapR) {
		// TODO Auto-generated method stub
		this.R = mapR;
	}

}
