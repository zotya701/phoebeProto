package phoebeProto;

import java.awt.Point;
import java.util.List;

/**
 * 
 */
public class Map implements Printable{
	
//Privát adattagok kezdete
	/**
	 * 
	 */
	private List<List<Field>> fields;

	/**
	 * 
	 */
	private List<List<Node>> nodes;

	/**
	 * 
	 */
	private Point size;

	/**
	 * 
	 */
	private List<Trap> trapList;
	
	/**
	 * 
	 */
	private OutsideField outside;
//Privát adattagok vége

//publikus metódusok kezdete
	/**
	 * 
	 * @param filename
	 * @param trapList
	 */
	public Map(String filename, List<Trap> trapList){
		
	}
	
	/**
	 * 
	 */
	public void Print(){
		
	}
	
	/**
	 * 
	 * @param coord
	 * @return
	 */
	public Field getField(Point coord){
		return fields.get(coord.y).get(coord.x);
	}
	
	
	public Point getNewPos(Point currentPos, Point vel){
		currentPos.translate(vel.x, vel.y);
		return currentPos;
	}
	
	/**
	 * 
	 * @param s
	 * @param d
	 * @return
	 */
	public float calculateDistance(Point s, Point d){
		return (float)(Math.pow((d.x-s.x)*(d.x-s.x)+(d.y-s.y)*(d.y-s.y), 0.5));
	}
	
	/**
	 * 
	 * @param source
	 * @return
	 */
	public Point getRouteToTrap(Point source){
		return source;
	}
	
	/**
	 * 
	 */
	public void createGraph(){
		
	}
//publikus metódusok vége
}
