package phoebeProto;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * A p�lya j�rhat� elemeit �sszek�t� gr�f csom�pontjait megval�s�t� oszt�ly.
 */
public class Node implements Comparable<Node>{

//priv�t adattagok kezdete
	/**
	 * A csom�pont koordin�t�ja.
	 */
	private Point coord;
	
	/**
	 * Az el�z� csom�pont, egy �ton bel�l.
	 */
	private Node previous;
	
	/**
	 *  A dijkstra algoritmushoz sz�ks�ges �rt�k.
	 */
	private int minDistance;
	
	/**
	 * A szomsz�dos cs�csokba vezet� �lek.
	 */
	private List<Edge> adjacencies;
//priv�t adattagok v�ge
	
//publikus met�dusok kezdete
	/**
	 * Konstruktor, l�trehoz egy p koordin�t�j� Node-t
	 * @param p A Node koordin�t�ja
	 */
	public Node(Point p){
    	this.coord			=	p;
    	this.adjacencies	=	new ArrayList<Edge>();
    	this.minDistance	=	Integer.MAX_VALUE;
    }
	
	/**
	 *  �j szomsz�dos �l felv�tele
	 * @param e Az �l amit felvesz�nk.
	 */
	public void addEdge(Edge e){
		this.adjacencies.add(e);
	}
	
	/**
	 * A Node koordin�t�j�nak lek�rdez�se.
	 * @return A Node koordin�t�j�val t�r vissza.

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
	 *  Az �tvonal el�z� csom�pontj�nak lek�rdez�se
	 * @return  Az �tvonal el�z� csom�pontj�val t�r vissza.

	 */
	public List<Edge> getAdjacencies(){
		return this.adjacencies;
	}
	
	/**
	 * @return minDistance visszaad�sa
	 */
	public int getMinDistance(){
		return this.minDistance;
	}
	
	/**
	 * minDistance be�ll�t�sa
	 * @param md A be�ll�tand� �rt�k
	 */
	public void setMinDistance(int md){
		this.minDistance	=	md;
	}
	
	/**
	 * Az �tvonal el�z� csom�pontj�nak be�ll�t�sa
	 * @param prev A be�ll�tand� csom�pont
	 */
	public void setPrevious(Node prev){
		this.previous		=	prev;
	}
	
	/**
	 * �sszehasonl�t�s egy m�sik csom�ponttal a minDistance alapj�n.
	 * @param other A m�sik csom�pont.
	 * @return Az �sszehasonl�t�s eredm�nye
	 */
	public int compareTo(Node other){
		return Integer.compare(minDistance, other.getMinDistance());
	}
//publikus met�dusok v�ge
}
