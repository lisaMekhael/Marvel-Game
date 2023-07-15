package engine;

import java.util.ArrayList;

public class PriorityQueue implements Cloneable {

	public Comparable[] elements;
	private int nItems;
	private int maxSize;
	private ArrayList<Comparable> avaliable;

	public PriorityQueue(int size) {
		maxSize = size;
		elements = new Comparable[maxSize];
		nItems = 0;
	}

	public void insert(Comparable item) {

		int i;
		for (i = nItems - 1; i >= 0 && item.compareTo(elements[i]) > 0; i--)
			elements[i + 1] = elements[i];

		elements[i + 1] = item;
		nItems++;
	}

	public Comparable remove() {
		nItems--;
		return elements[nItems];
	}
	
	public boolean isEmpty() {
		return (nItems == 0);
	}

	public boolean isFull() {
		return (nItems == maxSize);
	}

	public Comparable peekMin() {
		return elements[nItems - 1];
	}

	public int size() {
		return nItems;
	}
	
	@Override
	public 	Object clone() throws CloneNotSupportedException {
		return  super.clone();
	}
	

	public static void main (String[]args) {
		PriorityQueue pq  = new PriorityQueue(6);
		pq.insert(90);
		pq.insert(93);
		pq.insert(9);
		pq.insert(58);
		pq.insert(14);
		pq.insert(1);
		
	System.out.println(pq.peekMin());	
	pq.remove();
	System.out.println(pq.peekMin());
		
		for(int i=0; i<pq.size() ;i++ ) {
			System.out.println(pq.elements[i]);
		}
		
		
	}
	

}
