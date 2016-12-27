/*

Robert Durst

Project 8

November 27, 2016

*/

import java.util.*;

public class KeyValuePair<Key,Value> implements Comparator<KeyValuePair<Integer,Float>>{
	//Fields
	Key K;
	Value V;
	
	//Constructor
	public KeyValuePair( Key k, Value v ){
		this.K = k;
		this.V = v;
	} 
	
	//Return key
	public Key getKey(){
		return this.K;
	}
	
	//return the value
	public Value getValue(){
		return this.V;
	}
	
	//set the value
	public void setValue( Value v ){
		this.V = v;
	}
	
	//set Key
	public void setKey( Key k ){
		this.K = k;
	}
	
	//return a String containing both the key and value
	public String toString(){
		return (K + " " + V);
	}
	
	public int compare( KeyValuePair<Integer,Float> i1, KeyValuePair<Integer,Float> i2 ) {
	        // returns negative number if i2 comes after i1 lexicographically
	        float diff = i1.getValue() - i2.getValue();
	        if (diff == 0.0)
	            return 0;
	        if (diff < 0.0)
	            return 1;
	        else
	            return -1;
	    }
	
	//Main Method
	public static void main(String []args){
		KeyValuePair KVP = new KeyValuePair(1,1);
		
		System.out.println(KVP);
	}
	
	
}