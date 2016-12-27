import java.util.Iterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class LinkedList<T> implements Iterable<T>{
	
	//Fields
	private Node head;
	private Node tail;
	private int listSize;
	
	//a constructor that initializes the fields
	public LinkedList(){
		this.head = null;
		this.tail = null;
		this.listSize = 0;
	}
	
	//empties the list
	public void clear(){
		this.listSize = 0;
		this.head = null;
		this.tail = null;
	}
	
	//returns the size of the list
	public int size(){
		return this.listSize;
	}
	
	//adds item to the head of the list
	public void add(T item){
		Node newNode = new Node(item);
		
		//Sets the new top item's pointer to the previous head
		if (head != null){
			newNode.setNext(this.head);
		}
		
		//Sets the tail to the bottom node
		if (size() == 0){
			this.tail = newNode;
		}
		
		//Sets the prev to the node above it for the previous head
		if (head != null){
			head.setPrev(newNode);
		}
		
		//Sets the new item to the new head
		this.head = newNode;
		
		//Increments the list by 1
		this.listSize ++;
		
	}
	
	public Node getNode(T item){
		
		Node curNode = head;
		
		if (tail.getThing().equals(item)){
			return tail;
		}
		
		if (head.getThing().equals(item)){
			return head;
		}
		
		while(curNode.getNext() != null){
			if (curNode.getThing().equals(item)){
				return curNode;
			}
			
			else{
				curNode = curNode.getNext();
			}
		}
		
		return tail;
	}
	
	public void remove(Node item){
		
		if (item == tail){
			Node prev = item.getPrev();
			Node next = null;
			
			prev.setNext(next);
			
			tail = prev;
			
			listSize --;
		}
		
		else if (item == head){
			Node prev = null;
			Node next = item.getNext();
		
			next.setPrev(prev);
			
			head = next;
			
			listSize --;
		}
		
		else{
			Node prev = item.getPrev();
			Node next = item.getNext();
		
			System.out.println(prev.getThing() + " " + next.getThing());
		
			next.setPrev(prev);
			prev.setNext(next);
			
			listSize --;
		}
	}
	
	//Return tail
	public T getTail(){
		return tail.getThing();
	}
	
	//Return head
	public T getHead(){
		return head.getThing();
	}
	
	//Private inner class called node
	public class Node{
		
		//Fields
		private Node next;
		private Node prev;
		private T bucket;
		
		// a constructor that initializes next to null and the container field to item
		public Node(T item){
			this.next = null;
			this.prev = null;
			this.bucket = item;
		}
		
		//returns the value of the container field
		public T getThing(){
			return this.bucket;
		}
		
		//sets prev to the given node
		public void setPrev(Node n){
			this.prev = n;
		}
		
		//returns the prev node
		public Node getPrev(){
			return this.prev;
		}
		
		//sets next to the given node
		public void setNext(Node n){
			this.next = n;
		}
		
		//returns the next field
		public Node getNext(){
			return this.next;
		}
	}
	
	 private class LLBackwardIterator implements Iterator<T>{
	 	//Fields
		private Node prevNode;
		
		//creates an LLIterator given the head and tail of a list (constructor)
		public LLBackwardIterator(Node tail){
			prevNode = tail;
		}
		
		//returns true if there are still values to traverse (if the current node reference is not null)
		public boolean hasNext(){
			if (prevNode != null){
				if (prevNode.getThing() == null){
					return false;
				}
			
				else{
					return true;
				}
			}
			return false;
		}
		
		//returns the next item in the list, which is the item contained in the current node
		//The method also needs to move the traversal along to the next node in the list
		public T next(){
			if (hasNext()){
				Node tempNode = prevNode;
				prevNode = prevNode.getPrev();
				return tempNode.getThing();
			}
			
			else{
				return null;
			}
		} 
	}
	
	//Private inner class called iterator
	private class LLIterator implements Iterator<T>{
		
		//Fields
		private Node nextNode;
		private Node prevNode;
		
		//creates an LLIterator given the head and tail of a list (constructor)
		public LLIterator(Node head, Node tail){
			nextNode = head;
			prevNode = tail;
		}
		
		//returns true if there are still values to traverse (if the current node reference is not null)
		public boolean hasNext(){
			if (nextNode != null){
				if (nextNode.getThing() == null){
					return false;
				}
			
				else{
					return true;
				}
			}
			return false;
		}
		
		//Returns true if there is a node below it
		public boolean hasPrev(){
			if (prevNode != null){
				if (prevNode.getThing() == null){
					return false;
				}
				
				else{
					return true;
				}
			}
			return false;
		}
		
		//returns the prev item in the list, which is the item contained in the current node
		//The method also needs to move the traversal along to the next node in the list
		public T prev(){
			if (hasPrev()){
				Node tempNode = prevNode;
				prevNode = prevNode.getPrev();
				return tempNode.getThing();
			}
			
			else{
				return null;
			}
		} 
		
		//returns the next item in the list, which is the item contained in the current node
		//The method also needs to move the traversal along to the next node in the list
		public T next(){
			if (hasNext()){
				Node tempNode = nextNode;
				nextNode = nextNode.getNext();
				return tempNode.getThing();
			}
			
			else{
				return null;
			}
		} 
		
		
		
	}
	
	// Return a new LLIterator pointing to the head of the list and the tail
    public Iterator<T> iterator() {
        return new LLIterator( this.head, this.tail );
    }
    
     // Return a new LLBackwardIterator pointing to the head of the list
    public Iterator<T> backward_iterator() {
        return new LLBackwardIterator( this.tail );
    }
    
    
    //returns an ArrayList of the list contents in order
    public ArrayList<T> toArrayList(){
    	ArrayList<T> returnArray = new ArrayList<T>();
    	Node tempNode = head;
    	
    	for(int i = 0; i < this.listSize; i++) {
			returnArray.add(tempNode.getThing());
			tempNode = tempNode.getNext();
		}
		
		return returnArray;
    }
    
    //returns an ArrayList of the list contents in shuffled order
	public ArrayList<T> toShuffledList(){
		ArrayList<T> firstArray = this.toArrayList();
		ArrayList<T> returnArray = new ArrayList<T>();
		
		Random r = new Random();
		
		int interations = firstArray.size();
		for(int i = 0; i < interations; i ++){
			
			returnArray.add(firstArray.remove(r.nextInt(firstArray.size())));
		}
		
		return returnArray;
	}
	
	
	//Test/Main Function
	public static void main(String []args){
		LinkedList<Integer> llist = new LinkedList<Integer>();
		
		llist.add(5);
		llist.add(10);
		llist.add(20);
		llist.add(51);
		llist.add(101);
		llist.add(201);
		
		System.out.println(llist.toArrayList());
		
		llist.remove(llist.getNode(5));
		System.out.println(llist.toArrayList());
		llist.remove(llist.getNode(51));
		System.out.println(llist.toArrayList());
		
	}
}