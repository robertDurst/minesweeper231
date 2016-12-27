/*

Robert Durst

Project 8

November 27, 2016

*/

import java.util.*;

public class MinHeap<T>{
	//Fields
	ArrayList<T> heap;
	Compare comp;
	int lastItemIndex;
	
	//Constructor
	public MinHeap(Compare comparable){
		heap = new ArrayList<T>();
		comp = comparable;
		lastItemIndex = -1;
	}
	
	//Get size
	public int getSize(){
		return heap.size();
	}
	
	//Add Method
	public void add(T item){
		//Add Method: step one add to the end of the array
		heap.add(item);
		lastItemIndex ++;
		
		//Add Method: step two reheap the elements of the array
		if (lastItemIndex == 0){
			return;
		}
		
		else{
			
			int curIndex = lastItemIndex;
			int prevIndex = ((curIndex/2) + curIndex%2) - 1;
			
			while((curIndex > 0)){
				T childVal = heap.get(curIndex);
				T parentVal = heap.get(prevIndex);
				
				
				
				try{ if (comp.compare((Comparable)childVal,(Comparable)parentVal) == 1){
					
					heap.set(curIndex, parentVal);
					heap.set(prevIndex, childVal);
					
					curIndex = prevIndex;
					prevIndex = ((curIndex/2) + curIndex%2) - 1;
					
				}
				
				else{
					break;
				} }
				
				catch (ClassCastException e){
					
				KVTestComparator altCompare = new KVTestComparator();
				
				if (altCompare.compare(( KeyValuePair<String,Integer>)childVal,( KeyValuePair<String,Integer>)parentVal) == 1){
					
					heap.set(curIndex, parentVal);
					heap.set(prevIndex, childVal);
					
					curIndex = prevIndex;
					prevIndex = ((curIndex/2) + curIndex%2) - 1;
					
				}
				
				else{
					break;
				} 
					
				}
			}
		}
	}
	
	
	//Get the heap array list
	public ArrayList<T> getHeap(){
		return heap;
	} 
	
	
	//Remove Method
	public T remove(){
		
		if (lastItemIndex == -1){
			return null;
		}
		
		else if (lastItemIndex == 0){
			T returnItem = heap.remove(lastItemIndex);
			lastItemIndex --;
			return returnItem;
		}
		
		else{
			int curIndex = 0;
			
			//Keep a reference of the removed item
			T itemRemoved = heap.get(curIndex);

			//Switch the item to remove and the last item in the heap
			heap.set(curIndex, heap.get(lastItemIndex));
		
			//Remoce the item at the end of the heap
			heap.remove(lastItemIndex);
			
			lastItemIndex --;
		
			//Now reheap
			int nextIndex1 = (2*curIndex) + 1;
			int nextIndex2 = (curIndex + 1) * 2; 
		
			while((nextIndex1 <= lastItemIndex) && (nextIndex2 <= lastItemIndex)){
			
				try{
					if (comp.compare((Comparable)heap.get(nextIndex1), (Comparable)heap.get(nextIndex2)) >= 0){
						if(comp.compare((Comparable)heap.get(nextIndex1), (Comparable)heap.get(curIndex)) == 1){
							T temp = heap.get(curIndex);
							heap.set(curIndex, heap.get(nextIndex1));
							heap.set(nextIndex1, temp);
				
							curIndex = nextIndex1;
							nextIndex1 = (2*curIndex) + 1;
							nextIndex2 = (curIndex + 1) * 2;
						}
						else{
							break;
						}
					}
			
					else{
						if(comp.compare((Comparable)heap.get(nextIndex2), (Comparable)heap.get(curIndex)) == 1){
					
							T temp = heap.get(curIndex);
							heap.set(curIndex, heap.get(nextIndex2));
							heap.set(nextIndex2, temp);
				
							curIndex = nextIndex2;
							nextIndex1 = (2*curIndex) + 1;
							nextIndex2 = (curIndex + 1) * 2; 
						}
						else{
							break;
						}
					}
				}
				
				catch (ClassCastException e){
					KVTestComparator altCompare = new KVTestComparator();
					
					if (altCompare.compare((KeyValuePair<String,Integer>)heap.get(nextIndex1), (KeyValuePair<String,Integer>)heap.get(nextIndex2)) >= 0){
						if(altCompare.compare((KeyValuePair<String,Integer>)heap.get(nextIndex1), (KeyValuePair<String,Integer>)heap.get(curIndex)) == 1){
							T temp = heap.get(curIndex);
							heap.set(curIndex, heap.get(nextIndex1));
							heap.set(nextIndex1, temp);
				
							curIndex = nextIndex1;
							nextIndex1 = (2*curIndex) + 1;
							nextIndex2 = (curIndex + 1) * 2;
						}
						else{
							break;
						}
					}
			
					else{
						if(altCompare.compare((KeyValuePair<String,Integer>)heap.get(nextIndex2), (KeyValuePair<String,Integer>)heap.get(curIndex)) == 1){
					
							T temp = heap.get(curIndex);
							heap.set(curIndex, heap.get(nextIndex2));
							heap.set(nextIndex2, temp);
				
							curIndex = nextIndex2;
							nextIndex1 = (2*curIndex) + 1;
							nextIndex2 = (curIndex + 1) * 2; 
						}
						else{
							break;
						}
					}
				}
				
				
			
			}
		
			return itemRemoved;
		}
	}
	
	
	//Remove certain element
	public void removeEl(T item){
		if (heap.contains(item)){
			heap.remove(item);
			lastItemIndex --;
		}
		
		
	}
	
	//Main Method
	public static void main(String [] args){
		Compare<Integer> c = new Compare<Integer>();
		MinHeap<Integer> mh = new MinHeap<Integer>(c);
		
		
		mh.add(10);
		mh.add(8);
		mh.add(11);
		mh.add(2);
		mh.add(14);
		mh.add(7);
		mh.add(1);
		
		System.out.println(mh.getHeap());
		System.out.println();
		
		mh.removeEl(1);
		
		System.out.println(mh.getHeap());
		System.out.println();
		
		mh.add(1);
		
		System.out.println(mh.getHeap());
		System.out.println();
	}
}

/*
The compare class. Here is how it works:

For items o1 = 10, o2 = 11

o1 is less than o2
command: c.compare(10,11)
10 < 11
returns -1

o1 is greater than o2
command: c.compare(11,10)
11 > 10
returns 1

o1 is equal to o2
command: c.compare(10,10)
10 = 1
returns 0


*/
class Compare <T extends Comparable<T>> {
	public int compare(T o1, T o2){
		
		if ( o1.compareTo(o2) == 0){
			return 0;
		}
		
		else if ( o1.compareTo(o2) < 0){
			return 1;
		}
		
		else{
			return -1;
		}
	}
}

class KVTestComparator implements Comparator<KeyValuePair<String,Integer>> {
    public int compare( KeyValuePair<String,Integer> i1, KeyValuePair<String,Integer> i2 ) {
        // returns negative number if i2 comes after i1 lexicographically
        float diff = i1.getValue() - i2.getValue();
        if (diff == 0.0)
            return 0;
        if (diff < 0.0)
            return 1;
        else
            return -1;
    }
}