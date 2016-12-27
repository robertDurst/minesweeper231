import java.util.*;
import java.awt.Graphics;
import java.awt.Color;

public class Vertex extends Agent implements Comparable<Vertex>{
	//Fields
	HashMap<Direction,Vertex> map;
	int cost;
	boolean marked;
	boolean visible;
	boolean minesweeper;
	int number;
	
	//Constructor
	public Vertex(int r, int c){
		super(r,c);
		
		this.visible = false;
		map = new HashMap<Direction,Vertex>();
		this.cost = 0;
		marked = false;
		minesweeper = false;
		number = 0;
	}
	
	//Clear Function
	public void clear(){
		map.clear();
	}
	
	//Return the cost
	public int getCost(){
		return cost;
	}
	
	//Return the marked state
	public boolean isMarked(){
		return marked;
	}
	
	//Connect the vertex to another
	public void connect(Vertex other, Direction dir){
		if (map.containsKey(dir) == false){
			map.put(dir, other);
			other.connect(this,opposite(dir));
		}
	}
	
	//Get the neighbor in a given direction
	public Vertex getNeighbor(Direction dir){
		return map.get(dir);
	}
	
	//Get all the neighbors
	public Collection<Vertex> getNeighbors(){
		return map.values();
	}
	
	//Return the opposite direction
	public static Direction opposite(Direction dir){
		Direction returnDirection = Direction.NORTH;
		
		switch(dir){
			case NORTH: returnDirection = Direction.SOUTH;
						break;
			case EAST: returnDirection = Direction.WEST;
						break;
			case WEST: returnDirection = Direction.EAST;
						break;
			default: returnDirection = Direction.NORTH;
		}
		
		return returnDirection;
	}
	
	//To String method
	public String toString(){
		return (getNeighbors().size() + ", " + cost + ", " + marked);
	}
	
	//Comparable method
	public int compareTo(Vertex v){
		
		float diff = this.getCost() - v.getCost();
		        if (diff == 0.0)
		            return 0;
		        if (diff > 0.0)
		            return 1;
		        else
		            return -1;
	}
	
	//Increment Cost
	public void incrementCost(int value){
		cost += value;
	}
	
	//Set cost
	public void setCost(int value){
		this.cost = value;
	}
	
	//Set marked
	public void setMarked(boolean value){
		marked = value;
	}
	
	//Draw Method
    public void draw(Graphics g, int gridScale) {
	       
		if (minesweeper == false){
		   if (!this.visible)
	           return;
	       int xpos = this.getCol()*gridScale;
	       int ypos = this.getRow()*gridScale;
	       int border = 2;
	       int half = gridScale / 2;
	       int eighth = gridScale / 8;
	       int sixteenth = gridScale / 16;
    
    
	       g.drawRect(xpos + border, ypos + border, gridScale - 2*border, gridScale - 2 * border);
    
	       // draw doorways as boxes
	       g.setColor(Color.black);
	       if (this.map.containsKey(Direction.NORTH)){
	           g.fillRect(xpos + half - sixteenth, ypos, eighth, eighth + sixteenth);
		   }
	       if (this.map.containsKey(Direction.SOUTH)){
	           g.fillRect(xpos + half - sixteenth, ypos + gridScale - (eighth + sixteenth), eighth, eighth + sixteenth);
		   }
	       if (this.map.containsKey(Direction.WEST)){
	           g.fillRect(xpos, ypos + half - sixteenth, eighth + sixteenth, eighth);
		   }
	       if (this.map.containsKey(Direction.EAST)){
	           g.fillRect(xpos + gridScale - (eighth + sixteenth), ypos + half - sixteenth, eighth + sixteenth, eighth);
	       }
	   }
	   
	   else{
	       int xpos = this.getCol()*gridScale;
	       int ypos = this.getRow()*gridScale;
	       int border = 2;
	       int half = gridScale / 2;
	       int eighth = gridScale / 8;
	       int sixteenth = gridScale / 16;
    
    	   
	      
		   
		   if (!this.visible){
		   		g.setColor(Color.green);
				g.fillRect(xpos + border, ypos + border, gridScale - 2*border, gridScale - 2 * border);
		   }
		   
		   else{
			    g.setColor(Color.black);
				g.drawRect(xpos + border, ypos + border, gridScale - 2*border, gridScale - 2 * border);
				//g.fillRect(xpos + border, ypos + border, gridScale - 2*border, gridScale - 2 * border);
		   		g.drawString(Integer.toString(number), xpos + border + gridScale/2, ypos + gridScale/2);
		   }
		   
		   
			
	       
		   
	   }
   }
	//Main Method
	public static void main( String[] args ) {
		
	}
}

enum Direction {
    NORTH,
    EAST,
    WEST,
    SOUTH;
}