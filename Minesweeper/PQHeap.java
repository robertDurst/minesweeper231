/*

Robert Durst

Project 8

November 27, 2016

*/

import java.util.*;

public class PQHeap<T> {
	
	//Fields
	MinHeap<T> heap;
	
	//Constructor
	public PQHeap(){
		heap = new MinHeap<T>(new Compare<Integer>());
	}
	
	//Return heap size
	public int size(){
		return heap.getSize();
	}
	
	//Add the value to the heap and increment the size. Be sure to use the Comparator to reshape the heap. You may want to add private methods to handle the reshaping of the heap
	public void add(T obj){
		heap.add(obj);
	}
	
	
	//Remove and return the highest priority element from the heap. Be sure to use the Comparator to reshape the heap. You may want to add private methods to handle the reshaping of the heap.
	public T remove(){
		return heap.remove();
	}
	
	//Remove a specific element
	public void removeEl(T obj){
		heap.removeEl(obj);
	}
	
	//Main Method
	public static void main(String [] args){
        PQHeap<KeyValuePair<Integer,Float>> ppq = new PQHeap<KeyValuePair<Integer,Float>>();
         ppq.add( new KeyValuePair<Integer,Float>( new Integer(1), new Float(2.0) ) );
         ppq.add( new KeyValuePair<Integer,Float>( new Integer(3), new Float(1.0) ) );
         ppq.add( new KeyValuePair<Integer,Float>( new Integer(4), new Float(0.0) ) );
         KeyValuePair<Integer,Float> pair = ppq.remove();
         System.out.println( "Removing a pair, which should be (4, 0.0) and is " + pair  );
         pair = ppq.remove();
        System.out.println( "Removing a pair, which should be (3, 1.0) and is " + pair  );
         pair = ppq.remove();
         System.out.println( "Removing a pair, which should be (1, 2.0) and is " + pair  );
         pair = ppq.remove();
         System.out.println( "Removing a pair, which should be null and is " + pair  );
	}
}