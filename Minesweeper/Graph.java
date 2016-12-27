import java.util.*;

public class Graph{
	//Fields
	ArrayList<Vertex> vertexList;
	
	//Constructor
	public Graph(){
		vertexList = new ArrayList<Vertex>();
	}
	
	//Return how many vertices are in the graph
	public  int vertexCount(){
		return vertexList.size();
	}
	
	//Adding an edge method
	public void addEdge(Vertex v1, Direction dir, Vertex v2){
		if (vertexList.contains(v1) == false){
			vertexList.add(v1);
		}
		
		if (vertexList.contains(v2) == false){
			vertexList.add(v2);
		}
		
		v1.connect(v2,dir); //I automatically connect v2 in the Vertex class, taking into consideration the opoosite direction
	}
	
	/*
	

	
	
	
	
	
	while q is not empty:
		let v be the vertex in q with lowest cost
		remove v from q
		mark v as visited
		for each vertex w that neighbors v:
			if w is not marked and v.cost + 1 < w.cost:
				w.cost = v.cost + 1
				add w to q
				
	Output: the cost of each vertex v in G is the shortest distance from v0 to v.
		
		*/
	
	//Dijkstra's algorithm
	public int shortestPath(Vertex v0, Vertex v1){
		
		//Initialize all vertices in G to be unmarked and have infinite cost
		for (Vertex vertex: vertexList){
			vertex.setCost(Integer.MAX_VALUE);
			vertex.setMarked(false);
		}
		
		//Create a priority queue, q, that orders vertices by lowest cost
		PQHeap<Vertex> q = new PQHeap<Vertex>();
		
		//Set the cost of v0 to 0 and add it to q
		
		
		v0.setCost(0);
		
		q.add(v0);
		

	
		while(q.size() != 0){
			Vertex v = q.remove();
			v.setMarked(true);
			
			for (Vertex w: v.getNeighbors()){
				
				//System.out.println(v.name + " to " + w.name);
				
				if ((w.isMarked() == false) && ((v.getCost() + 1) < w.getCost())){
					w.setCost(v.getCost() + 1);
					//w.setMarked(true);
					if (q.size() != 0){
						q.removeEl(w);
					}
					q.add(w);
				}
				
			
			}
			
		}
		
		int returnValue = 0;
		for(Vertex vert: vertexList){
			if(vert == v1){
				returnValue = vert.getCost();
			}
		}
		
		return returnValue;
	}
	
	
	//Main Method
	public static void main( String[] args ) {
		
	}
}