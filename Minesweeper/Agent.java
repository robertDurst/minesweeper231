import java.awt.Graphics;

public class Agent{
	
	//Fields
	int col;
	int row;
	int Category;
	
	//Constructor that sets the position
	public Agent(int r, int c){
		this.col = c;
		this.row = r;
	}
	
	//Return the column
	public int getCol(){
		return col;
	}
	
	//Return the row
	public int getRow(){
		return row;
	}
	
	//Sets the column
	public void setRow( int newRow ){
		this.row = newRow;
	}
	
	//Sets the row
	public void setCol( int newCol ){
		this.col = newCol;
	}
	
	//Returns a string containing the x and y positions e.g. "(3, 4)"
	public String toString(){
		return ("(" + this.col + ", " + this.row + ")");
	}
	
	//Does nothing
	public void draw(Graphics g, int scale){
		//g.drawOval((int)this.xPos, (int)this.yPos, 5, 5);
    	//g.fillOval((int)this.xPos, (int)this.yPos, 5, 5);
	}
	
	//Does Nothing
	//public void updateState( Landscape scape ){
		
	//}
	
	//Test/Main Method
	public static void main(String []args){
		Agent a = new Agent(10,10);
		System.out.println(a);
	}
	
}