package phoebeProto;

/**
 * K�t csom�pontot (Node) �sszek�t
 */
public class Edge {

//priv�t adattagok kezdete
	/**
	 * Az �l v�gpontj�t jelz� csom�pont.
	 */
	final private Node target;
//priv�t adattagok v�ge
	
//publikus met�dusok kezdete
	/**
	 * Konstruktor
	 * @param target 
	 */
	public Edge(Node target){
		this.target	=	target;
	}
	
	/**
	 * Target lek�rdez�se
	 * @returnv Visszaadja a target Node-ot.

	 */
	public Node getTarget(){
		return target;
	}
//publikus met�dusok v�ge
}
