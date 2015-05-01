package mapping;


import java.util.ArrayDeque;
import java.util.Iterator;

import lejos.robotics.navigation.Pose;

public class CycleDetector {

	//Data Structure
	private ArrayDeque<GridCell>  poseHistory;
	private int size;
	
	public CycleDetector(int size){
		poseHistory = new ArrayDeque<GridCell>();
		this.size = size;
	}
	
	public void update(GridCell cell){
		if(poseHistory.size() >= size){
			poseHistory.removeFirst();			
		}
		poseHistory.addFirst(cell);	
	}
	
	public void list(){
		int i = 0;
		Iterator it=poseHistory.iterator();
		while(it.hasNext()){
	            Pose pose = (Pose) it.next();
	            i++;
	    		System.out.println(i + " [" + pose.getX() + "," + pose.getY() + "," + pose.getHeading() + "]");
	    		
		}
	}
}
