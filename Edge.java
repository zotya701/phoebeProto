package phoebeProto;

/**
 * Két csomópontot (Node) összeköt
 */
public class Edge {

//privát adattagok kezdete
	/**
	 * Az él végpontját jelzõ csomópont.
	 */
	final private Node target;
//privát adattagok vége
	
//publikus metódusok kezdete
	/**
	 * Konstruktor
	 * @param target 
	 */
	public Edge(Node target){
		this.target	=	target;
	}
	
	/**
	 * Target lekérdezése
	 * @returnv Visszaadja a target Node-ot.

	 */
	public Node getTarget(){
		return target;
	}
//publikus metódusok vége
}
