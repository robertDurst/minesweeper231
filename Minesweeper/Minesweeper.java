import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.Point;

import javax.imageio.ImageIO;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.event.MouseInputAdapter;

import java.util.*;
import java.awt.*;
import java.io.*;
import javax.imageio.*;
import java.awt.image.BufferedImage;
import javax.swing.JTextField; 
import javax.swing.Timer;


public class Minesweeper extends JFrame{
	//Fields
	Landscape landscape;
	LandscapeDisplay display;
	Graph graph;
	Random rand;
	
	Bomb bomb1;
	Bomb bomb2;
	Bomb bomb3;
	Bomb bomb4;
	
	Point curPoint;
	/** height of the main drawing window **/
	int height;
	/** width of the main drawing window **/
	int width;
	
	JLabel timerLabel;
	
	int timerCount;
	
	Timer timer;
	
	// controls whether the simulation is playing or exiting
	private enum PlayState { PLAY, GAME_ENDED, STOP, RESTART }
	private PlayState state= PlayState.PLAY;
	
	public Minesweeper(int row, int col, int scale){
	
		
		//Timer
		timerCount = 0;
		timerLabel = new JLabel(Integer.toString(timerCount));
		
		Timer timer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
           		 	if (state == PlayState.PLAY){
						timerCount ++;
						timerLabel.setText(Integer.toString(timerCount));
						display.repaint();
           		 	}
					
                }
            });
            timer.start();	
			
		//Initialize the graph
		graph = new Graph();	
		
		//Initialize the landscape
		landscape = new Landscape(row,col);
		
		//Initialize the landscapeDisplay
		display = new LandscapeDisplay(landscape,scale);	
		
		//Initialize Random
		rand = new Random();
		
		//GUI compnonents
		Control control = new Control();
		display.addKeyListener(control);
		display.setFocusable(true);
		display.requestFocus();
		MouseControl mc = new MouseControl();
		display.addMouseListener( mc );
		display.addMouseMotionListener( mc );
		
		display.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
		JButton quit = new JButton("Quit");
		JButton restart = new JButton("Restart");
		JPanel panel = new JPanel( new FlowLayout(FlowLayout.RIGHT));
		panel.add(timerLabel);
		panel.add(restart);
		panel.add( quit );
		display.add( panel, BorderLayout.SOUTH);
		display.pack();
		quit.addActionListener( control );
		restart.addActionListener( control );
			
		width = row*scale;
		height = col*scale;	
			
		//Generate the vertices and connect them horizontally
		for (int i = 0; i < row; i ++){
			
			Vertex vl = new Vertex(i,0);
			vl.minesweeper = true;
			landscape.background.add(vl);
			
			for (int l = 1; l < col; l ++){
				Vertex vr = new Vertex(i,l);
				vr.minesweeper = true;
				landscape.background.add(vr);
				
				graph.addEdge(vl, Direction.EAST, vr);
				
				vl = vr;
			}	
		}
		
		ArrayList<Agent> verts = landscape.background.toArrayList();
		
		
		
		//Connect the vertices vertically
		for (int i = 0; i < row-1; i ++){
			for(int l = 0; l < col; l ++){
				graph.addEdge((Vertex)verts.get(l + (col*i)), Direction.NORTH, (Vertex)verts.get(l + (col*(i+1))));
			}
		}
		
		//Put the bombs
		Vertex bomb1Loc = (Vertex)verts.get(rand.nextInt(verts.size()));
		bomb1 = new Bomb(bomb1Loc);
		bomb1.visible = false;
		verts.remove(bomb1Loc);
		landscape.foreground.add(bomb1);
		
		Vertex bomb2Loc = (Vertex)verts.get(rand.nextInt(verts.size()));
		bomb2 = new Bomb(bomb2Loc);
		bomb2.visible = false;
		verts.remove(bomb2Loc);
		landscape.foreground.add(bomb2);
		
		Vertex bomb3Loc = (Vertex)verts.get(rand.nextInt(verts.size()));
		bomb3 = new Bomb(bomb3Loc);
		bomb3.visible = false;
		verts.remove(bomb3Loc);
		landscape.foreground.add(bomb3);
		
		Vertex bomb4Loc = (Vertex)verts.get(rand.nextInt(verts.size()));
		bomb4 = new Bomb(bomb4Loc);
		bomb4.visible = false;
		verts.remove(bomb4Loc);
		landscape.foreground.add(bomb4);
		
		verts = landscape.background.toArrayList();
		
		for (Agent agent: verts){
			Vertex vert = (Vertex)agent;
			
			int numSum = 0;
			
			numSum += howClose(vert,bomb1.curLoc);
			numSum += howClose(vert,bomb2.curLoc);
			numSum += howClose(vert,bomb3.curLoc);
			numSum += howClose(vert,bomb4.curLoc);
			
			if ((vert == bomb1.curLoc) || (vert == bomb2.curLoc) || (vert == bomb3.curLoc) || (vert == bomb4.curLoc)){
				numSum = 0;
			}
			
			vert.number = numSum;
			
		}
		
		
		
		for (Agent agent: verts){
			Vertex vert = (Vertex)agent;
			
			//vert.number += findDiagnols(vert);
			
		}
		
		ArrayList<Integer> vertIntsToAdd = new ArrayList<Integer>();
		for (Agent agent: verts){
			
			Vertex vert = (Vertex)agent;
			
			vertIntsToAdd.add(findDiagnols(vert));
			
		}
		
		
		//System.out.println(vertIntsToAdd);
		
		for (int i = 0; i < vertIntsToAdd.size(); i ++){
			Vertex tempVert = (Vertex)verts.get(i);
			tempVert.number += vertIntsToAdd.get(i);
		}
		
		this.curPoint = new Point( this.width/2, this.height/2 );
		
		display.repaint();
	}
	
	//Get diagnols
	public int findDiagnols(Vertex vert){
		
		int returnValue = 0;
		
		boolean north = false;
		boolean south = false;
		boolean east = false;
		boolean west = false;
		
		if (vert.getNeighbor(Direction.NORTH) != null){
				Vertex temp = vert.getNeighbor(Direction.NORTH);
				Vertex tempL = temp.getNeighbor(Direction.EAST);
				
				if (tempL != null){
					if ((tempL == bomb1.curLoc) || (tempL == bomb2.curLoc) || (tempL == bomb3.curLoc) || (tempL == bomb4.curLoc)){
						returnValue ++;
					}
				}
				
				Vertex tempR = temp.getNeighbor(Direction.WEST);
				if ((tempR == bomb1.curLoc) || (tempR == bomb2.curLoc) || (tempR == bomb3.curLoc) || (tempR == bomb4.curLoc)){
					returnValue ++;
				}
		}
		
		if (vert.getNeighbor(Direction.SOUTH) != null){
			Vertex temp = vert.getNeighbor(Direction.SOUTH);
			Vertex tempL = temp.getNeighbor(Direction.EAST);
			
			if (tempL != null){
				if ((tempL == bomb1.curLoc) || (tempL == bomb2.curLoc) || (tempL == bomb3.curLoc) || (tempL == bomb4.curLoc)){
					returnValue ++;
				}
			}
			
			Vertex tempR = temp.getNeighbor(Direction.WEST);
			if ((tempR == bomb1.curLoc) || (tempR == bomb2.curLoc) || (tempR == bomb3.curLoc) || (tempR == bomb4.curLoc)){
				returnValue ++;
			}
		}
		
		
		
		
		
		if ((vert == bomb1.curLoc) || (vert == bomb2.curLoc) || (vert == bomb3.curLoc) || (vert == bomb4.curLoc)){
			returnValue = 0;
		}
		
		return returnValue;
	}
	
	//Get the distance to the bombs
	public int howClose(Vertex vert, Vertex bomb){
		
		int dist = graph.shortestPath(vert,bomb);
		
		if (dist == 1){
			return 1;
		}
		
		return 0;
	}
	
	//Get the Vertex that was clicked
	public int getVertex(double x, double y){
		int vertexIndex = (((int)x/80) + ((int)(y-25)/80)*landscape.getCols());
		
		return(vertexIndex);
	}
	
	//Clicked metho
	public void clicked(int index){
		ArrayList<Agent> verts = landscape.background.toArrayList();
		
		Vertex vert = (Vertex)verts.get((landscape.getCols() * landscape.getRows()) - index - 1);
		vert.visible = true;
		
		if ((vert == bomb1.curLoc)){
			display.backgroundRed();
			bomb1.visible = true;
			//timer.stop();
			state= PlayState.GAME_ENDED;
		}
		
		else if ((vert == bomb2.curLoc)){
			display.backgroundRed();
			bomb2.visible = true;
			//timer.stop();
			state= PlayState.GAME_ENDED;
		}
		
		else if ((vert == bomb3.curLoc)){
			display.backgroundRed();
			bomb3.visible = true;
			//timer.stop();
			state= PlayState.GAME_ENDED;
		}
		
		else if ((vert == bomb4.curLoc)){
			display.backgroundRed();
			bomb4.visible = true;
			//timer.stop();
			state= PlayState.GAME_ENDED;
		}
		
		int counter = 0;
		for (Agent agent: verts){
			Vertex pert = (Vertex)agent;
			if (pert.visible){
				
			}
			else{
				counter ++;
			}
		}
		
		if (counter == 4){
			display.backgroundGreen();
			bomb1.visible = true;
			bomb2.visible = true;
			bomb3.visible = true;
			bomb4.visible = true;
			//timer.stop();
			state= PlayState.GAME_ENDED;
		}
		
		display.repaint();
	}
	
	private class MouseControl extends MouseInputAdapter {

	        public void mouseClicked(MouseEvent e) {
				if (state == PlayState.PLAY){
					clicked(getVertex(e.getPoint().x, e.getPoint().y));
				}
	        }
		} // end class MouseControul
		
	private class Control extends KeyAdapter implements ActionListener {

	        // public void keyTyped(KeyEvent e) {
  // 	            System.out.println( "Key Pressed: " + e.getKeyChar() );
  // 	            if( ("" + e.getKeyChar()).equalsIgnoreCase("q") ) {
  // 	                state = PlayState.STOP;
  // 	            }
  // 	        }

		  public void actionPerformed(ActionEvent event) {
		      if( event.getActionCommand().equalsIgnoreCase("Quit") ) {
		          state = PlayState.STOP;
		      }
	
		      else if( event.getActionCommand().equalsIgnoreCase("Restart") ) {
		          state = PlayState.RESTART;
				display.requestFocus();
		      }
	
		  }
	} // end class Control
	
	//End Method
	public void end(){
		display.dispose();
	}
	
	//Restart Method
	public void restart(){
		
		
		
		ArrayList<Agent> verts = landscape.background.toArrayList();
		
		for (Agent vert: verts){
			Vertex temp = (Vertex) vert;
			temp.visible = false;
		}
		
		bomb1.visible = false;
		bomb2.visible = false;
		bomb3.visible = false;
		bomb4.visible = false;
		
		//Put the bombs
		Vertex bomb1Loc = (Vertex)verts.get(rand.nextInt(verts.size()));
		bomb1 = new Bomb(bomb1Loc);
		bomb1.visible = false;
		verts.remove(bomb1Loc);
		landscape.foreground.add(bomb1);
		
		Vertex bomb2Loc = (Vertex)verts.get(rand.nextInt(verts.size()));
		bomb2 = new Bomb(bomb2Loc);
		bomb2.visible = false;
		verts.remove(bomb2Loc);
		landscape.foreground.add(bomb2);
		
		Vertex bomb3Loc = (Vertex)verts.get(rand.nextInt(verts.size()));
		bomb3 = new Bomb(bomb3Loc);
		bomb3.visible = false;
		verts.remove(bomb3Loc);
		landscape.foreground.add(bomb3);
		
		Vertex bomb4Loc = (Vertex)verts.get(rand.nextInt(verts.size()));
		bomb4 = new Bomb(bomb4Loc);
		bomb4.visible = false;
		verts.remove(bomb4Loc);
		landscape.foreground.add(bomb4);
		
		verts = landscape.background.toArrayList();
		
		for (Agent agent: verts){
			Vertex vert = (Vertex)agent;
			
			int numSum = 0;
			
			numSum += howClose(vert,bomb1.curLoc);
			numSum += howClose(vert,bomb2.curLoc);
			numSum += howClose(vert,bomb3.curLoc);
			numSum += howClose(vert,bomb4.curLoc);
			
			if ((vert == bomb1.curLoc) || (vert == bomb2.curLoc) || (vert == bomb3.curLoc) || (vert == bomb4.curLoc)){
				numSum = 0;
			}
			
			vert.number = numSum;
			
		}
		
		
		
		for (Agent agent: verts){
			Vertex vert = (Vertex)agent;
			
			//vert.number += findDiagnols(vert);
			
		}
		
		ArrayList<Integer> vertIntsToAdd = new ArrayList<Integer>();
		for (Agent agent: verts){
			
			Vertex vert = (Vertex)agent;
			
			vertIntsToAdd.add(findDiagnols(vert));
			
		}
		
		
		//System.out.println(vertIntsToAdd);
		
		for (int i = 0; i < vertIntsToAdd.size(); i ++){
			Vertex tempVert = (Vertex)verts.get(i);
			tempVert.number += vertIntsToAdd.get(i);
		}
		
		display.backgroundWhite();
		
		//timer.start();
		timerCount = 0;
		
		state = PlayState.PLAY;
		
		display.repaint();	
	}
	
	//Main Method
    public static void main(String[] args) throws InterruptedException {
        Minesweeper mine = new Minesweeper(6,6,80);
		
		while(mine.state != PlayState.STOP){
			
			Thread.sleep(33);
			
			while(mine.state == PlayState.PLAY) {
				mine.repaint();
				Thread.sleep(33);
			}
			
			if(mine.state == PlayState.GAME_ENDED){
				mine.timerCount = mine.timerCount;
				//Thread.sleep(33);
			}
			
		    while(mine.state == PlayState.RESTART){
				mine.restart();
				Thread.sleep(33);
			}
		}
		
		mine.end();
		
		
		
    }
}