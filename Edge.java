package phoebeProto;

/**
 * 
 */
public class Edge {

//privát adattagok kezdete
	/**
	 * 
	 */
	final private Node target;
//privát adattagok vége
	
//publikus metódusok kezdete
	/**
	 * 
	 * @param target
	 */
	public Edge(Node target){
		this.target	=	target;
	}
	
	/**
	 * 
	 * @return
	 */
	public Node getTarget(){
		return target;
	}
//publikus metódusok vége
}
