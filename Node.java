package phoebeProto;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class Node implements Comparable<Node>{

//privát adattagok kezdete
	/**
	 * 
	 */
	private Point coord;
	
	/**
	 * 
	 */
	private Node previous;
	
	/**
	 * 
	 */
	private int minDistance;
	
	/**
	 * 
	 */
	private List<Edge> adjacencies;
//privát adattagok vége
	
//publikus metódusok kezdete
	/**
	 * 
	 * @param p
	 */
	public Node(Point p){
    	this.coord			=	p;
    	this.adjacencies	=	new ArrayList<Edge>();
    	this.minDistance	=	Integer.MAX_VALUE;
    }
	
	/**
	 * 
	 * @param e
	 */
	public void addEdge(Edge e){
		this.adjacencies.add(e);
	}
	
	/**
	 * 
	 * @return
	 */
	public Point getCoord(){
		return this.coord;
	}
	
	/**
	 * 
	 * @return
	 */
	public Node getPrevious(){
		return this.previous;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Edge> getAdjacencies(){
		return this.adjacencies;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getMinDistance(){
		return this.minDistance;
	}
	
	/**
	 * 
	 * @param md
	 */
	public void setMinDistance(int md){
		this.minDistance	=	md;
	}
	
	/**
	 * 
	 * @param prev
	 */
	public void setPrevious(Node prev){
		this.previous		=	prev;
	}
	
	/**
	 * 
	 * @param other
	 * @return
	 */
	public int compareTo(Node other){
		return Integer.compare(minDistance, other.getMinDistance());
	}
	
}
