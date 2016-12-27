import java.util.Random;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

import java.util.*;
import java.awt.*;
import java.io.*;
import javax.imageio.*;
import java.awt.image.BufferedImage;

public class Landscape{
	
	//Fields
	private Random gen;
    private int rows;
    private int cols;
	LinkedList<Agent> foreground;
	LinkedList<Agent> background;
    
    //Constructor
	public Landscape( int rows, int cols){
		
		//Set the rows and cols values
		this.rows = rows;
		this.cols = cols;
		
		//Initialize the foreground and the background linkedlists
		foreground = new LinkedList<Agent>();
		background = new LinkedList<Agent>();
	}
	
	//Return the number of rows in the landscape
	public int getRows(){
		return this.rows;
	}
	
	//Return the number of columns in the landscape
	public int getCols(){
		return this.cols;
	} 
	
	//Return a string providing useful information about the Landscape. This may be as simple as its dimensions.
	public String toString(){
		return "";	
	}
	
	
	//Draw all 
	public void draw( Graphics g, int gridScale ){
		//Loop through the background agents first
		for (Agent backAgent: background){
			backAgent.draw(g, gridScale);
		}
		
		//Loop through the foreground agents second
		for (Agent foreAgent: foreground){
			foreAgent.draw(g, gridScale);
		}
		
	}
	
	//Test/Main Method
	public static void main(String []args){
		Landscape ls = new Landscape(10,10);
	
		
		
	}
}