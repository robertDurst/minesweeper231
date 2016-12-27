import java.util.*;
import java.awt.*;
import java.io.*;
import javax.imageio.*;
import java.awt.image.BufferedImage;

public class Bomb extends Agent{
	//Fields
	boolean visible;
	Vertex curLoc;
	
	//Constructor
	public Bomb(Vertex v){
		super(v.getRow(),v.getCol());
		
		this.visible = false;
		this.curLoc = v;
	}
	
	//Set the visibility
	public void setVisibile(boolean vis){
		this.visible = vis;
	}
	
	//Set the current location (Vertex) of the Wumpus
	public void setCurLoc(Vertex v){
		this.curLoc = v;
	}
	
	//Draw method
    public void draw(Graphics g, int gridScale) {
		
		if (visible){
			String path = "bomb.png";
	        try{
				File file = new File(path);
		        Image image = ImageIO.read(file);
				g.drawImage(image, curLoc.getCol()*gridScale+gridScale/4, curLoc.getRow()*gridScale+gridScale/4, gridScale/2, gridScale/2, null);
			}

			catch(IOException e){

			}
				
		}
        
	}

}