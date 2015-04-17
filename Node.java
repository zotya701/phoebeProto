package phoebeProto;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * A pálya járható elemeit összekötõ gráf csomópontjait megvalósító osztály.
 */
public class Node implements Comparable<Node>{

//privát adattagok kezdete
	/**
	 * A csomópont koordinátája.
	 */
	private Point coord;
	
	/**
	 * Az elõzõ csomópont, egy úton belül.
	 */
	private Node previous;
	
	/**
	 *  A dijkstra algoritmushoz szükséges érték.
	 */
	private int minDistance;
	
	/**
	 * A szomszédos csúcsokba vezetõ élek.
	 */
	private List<Edge> adjacencies;
//privát adattagok vége
	
//publikus metódusok kezdete
	/**
	 * Konstruktor, létrehoz egy p koordinátájú Node-t
	 * @param p A Node koordinátája
	 */
	public Node(Point p){
    	this.coord			=	p;
    	this.adjacencies	=	new ArrayList<Edge>();
    	this.minDistance	=	Integer.MAX_VALUE;
    }
	
	/**
	 *  Új szomszédos él felvétele
	 * @param e Az él amit felveszünk.
	 */
	public void addEdge(Edge e){
		this.adjacencies.add(e);
	}
	
	/**
	 * A Node koordinátájának lekérdezése.
	 * @return A Node koordinátájával tér vissza.

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
	 *  Az útvonal elõzõ csomópontjának lekérdezése
	 * @return  Az útvonal elõzõ csomópontjával tér vissza.

	 */
	public List<Edge> getAdjacencies(){
		return this.adjacencies;
	}
	
	/**
	 * @return minDistance visszaadása
	 */
	public int getMinDistance(){
		return this.minDistance;
	}
	
	/**
	 * minDistance beállítása
	 * @param md A beállítandó érték
	 */
	public void setMinDistance(int md){
		this.minDistance	=	md;
	}
	
	/**
	 * Az útvonal elõzõ csomópontjának beállítása
	 * @param prev A beállítandó csomópont
	 */
	public void setPrevious(Node prev){
		this.previous		=	prev;
	}
	
	/**
	 * Összehasonlítás egy másik csomóponttal a minDistance alapján.
	 * @param other A másik csomópont.
	 * @return Az összehasonlítás eredménye
	 */
	public int compareTo(Node other){
		return Integer.compare(minDistance, other.getMinDistance());
	}
//publikus metódusok vége
}
