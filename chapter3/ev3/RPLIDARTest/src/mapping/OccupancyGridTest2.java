package mapping;

import lejos.robotics.navigation.Pose;

public class OccupancyGridTest2 {

	//20
	static int[][] mapR = new int[][] {
			{1,1,1,1,1,1,1,1,0,0,0,1,1,1,1,1,1,1,1,1},
			{1,0,0,0,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,1},
			{1,0,0,0,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,1},
			{1,0,0,0,0,0,0,1,1,1,1,1,0,0,0,0,0,0,0,1},
			{1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{0,0,1,0,0,0,0,1,1,1,1,1,0,0,0,0,0,0,0,1},
			{0,0,1,0,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,1},
			{0,0,1,0,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,1},
			{0,0,1,0,0,0,0,1,1,1,1,1,0,0,0,0,0,0,0,1},
			{0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{0,0,1,0,0,0,0,0,0,1,1,1,1,1,1,1,0,0,0,1},
			{1,1,1,0,0,0,0,0,1,1,0,0,0,0,0,1,0,0,0,1},
			{1,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,0,0,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
	};
	private static int cellSize = 20;
	
	private static final int ITERATIONS = 70;
	
	public static void main(String[] args) {

		int mapDimension = 20;

		OccupancyGridMap cgm = new OccupancyGridMap(mapDimension,cellSize);
		cgm.setFilePath("/home/lejos/www/ogm/js/");
		cgm.setFileName("map.js");

		float distance = 0;
		
		//BeetleRobotEV3 robot = BeetleRobotEV3.getInstance();
		int theta = 0;
		int travelDistance = 20;
		Pose pose = new Pose();
		pose.setLocation(20, 20);
		pose.setHeading(theta);
		distance = getFrontalDistance(pose);
		System.out.print("Iteration: " + "-" + " ");
		System.out.println("pose = " + pose.toString() + " distance:" + distance);
		cgm.update(pose, distance);
		
		for(int i = 0; i<ITERATIONS;i++){
			
			if(distance > 20){
				if(theta == 0){
					pose.setLocation(pose.getX() + travelDistance, pose.getY());
				}else if(theta == 90){
					pose.setLocation(pose.getX(), pose.getY()+ travelDistance);
				}else if(theta == 180){
					pose.setLocation(pose.getX()-travelDistance, pose.getY());					
				}else if(theta == 270){
					pose.setLocation(pose.getX(), pose.getY()-travelDistance);					
				}
			}else{
				theta += 90;
				//Reset
				if(theta == 360){
					theta = 0;
				}
				pose.setLocation(pose.getX(), pose.getY());
				pose.setHeading(theta);
			}
			distance = getFrontalDistance(pose);
			System.out.print("Iteration: " + i + " ");
			System.out.println("pose = " + pose.toString() + " distance:" + distance);
			cgm.update(pose, distance);
		}
		
		cgm.setRealMap(mapR);
		cgm.writeJSData();
		
		System.out.println("END");

	}

	private static final int MAX_DISTANCE = 95;
	
	private static float getFrontalDistance(Pose pose) {
		float distance = 0;
		
		float xMeasured = 0;
		float yMeasured = 0;
		int xCell = 0;
		int yCell = 0;
		int xMeasuredCell = 0;
		int yMeasuredCell = 0;
		
		xCell = (int) Math.floor(pose.getX()/cellSize);
		yCell = (int) Math.floor(pose.getY()/cellSize);
		
		float theta = pose.getHeading();
		int cellCounter = 0;
		
		if(theta == 0){
			xMeasured = pose.getX() + MAX_DISTANCE;
			yMeasured = pose.getY();

			xMeasuredCell = (int) Math.floor(xMeasured/cellSize);
			yMeasuredCell = (int) Math.floor(yMeasured/cellSize);
			
			cellCounter = xMeasuredCell - xCell;
			
		}else if(theta == 90){
			xMeasured = pose.getX();
			yMeasured = pose.getY() + MAX_DISTANCE;	
			
			xMeasuredCell = (int) Math.floor(xMeasured/cellSize);
			yMeasuredCell = (int) Math.floor(yMeasured/cellSize);
			
			cellCounter = yMeasuredCell - yCell;

		}else if(theta == 180){

			xMeasured = pose.getX() - MAX_DISTANCE;
			yMeasured = pose.getY();	
			
			xMeasuredCell = (int) Math.floor(xMeasured/cellSize);
			yMeasuredCell = (int) Math.floor(yMeasured/cellSize);
			
			cellCounter = Math.abs(xMeasuredCell - xCell);
			
			//System.out.println(xCell);
			//System.out.println(xMeasuredCell);
			//System.out.println(cellCounter);
		}else if(theta == 270){
			xMeasured = pose.getX();
			yMeasured = pose.getY()- MAX_DISTANCE;	
			
			xMeasuredCell = (int) Math.floor(xMeasured/cellSize);
			yMeasuredCell = (int) Math.floor(yMeasured/cellSize);
			
			cellCounter = Math.abs(yMeasuredCell - yCell);
			
			//System.out.println(yCell);
			//System.out.println(yMeasuredCell);
			//System.out.println(cellCounter);
		}

		
		distance = detectDistanceToObstacle(pose,cellCounter);
		
		return distance;
	}
	
	private static int detectDistanceToObstacle(Pose pose, int cellCounter){
		int xCell = 0;
		int yCell = 0;
		xCell = (int) Math.floor(pose.getX()/cellSize);
		yCell = (int) Math.floor(pose.getY()/cellSize);
		
		int distance = 0;
		
		float theta = pose.getHeading();
		
		if(theta == 0){
			
			for(int x= 0; x<cellCounter; x++){
			
				//System.out.println("map[" + yCell + "][" + (xCell + x) + "] " + mapR[yCell][xCell+x]);
				if(mapR[yCell][xCell + x] == 1){
					distance = x*cellSize;
					break;
				}else{
					distance = MAX_DISTANCE;
				}
			}
		}else if(theta== 90){
			for(int x= 0; x<cellCounter; x++){
				//System.out.println("map[" + (yCell + x) + "]["+ xCell + "] " + mapR[yCell+x][xCell]);
				if(mapR[yCell + x][xCell] == 1){
					distance = x*cellSize;
					break;
				}else{
					distance = MAX_DISTANCE;
				}						
			}
		
		}else if(theta== 180){
			
			for(int x= 0; x<cellCounter; x++){
				
				//System.out.println("map[" + yCell + "][" + (xCell - x) + "] " + mapR[yCell][xCell-x]);
				
				if(mapR[yCell][xCell - x] == 1){
					distance = x*cellSize;
					break;
				}else{
					distance = MAX_DISTANCE;
				}
				
			}
		}else if(theta== 270){
			for(int x= 0; x<cellCounter; x++){
				
				//System.out.println("map[" + (yCell -x) + "][" + xCell + "] " + mapR[yCell-x][xCell]);

				if(mapR[yCell-x][xCell] == 1){
					distance = x*cellSize;
					break;
				}else{
					distance = MAX_DISTANCE;
				}
				
			}
		}
		
		return distance;
	}

}
