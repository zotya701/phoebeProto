package phoebeProto;

/**
 * 
 */
public class Edge {

//priv�t adattagok kezdete
	/**
	 * 
	 */
	final private Node target;
//priv�t adattagok v�ge
	
//publikus met�dusok kezdete
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
//publikus met�dusok v�ge
}
