package phoebeProto;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class Oil implements Trap{
	
//privát adattagok kezdete
	/**
	 * 
	 */
	private Point position;
	
	/**
	 * 
	 */
	private NormalField currentField;

	/**
	 * 
	 */
	private int health;
//privát adattagok vége
	
	/**
	 * 
	 */
	static public List<Oil> oilList=new ArrayList<Oil>();
	
	/**
	 * 
	 */
	static public List<Trap> trapList=new ArrayList<Trap>();
	
//publikus metódusok kezdete
	/**
	 * 
	 */
	public Oil(Point pos){
		this.position=pos;
		this.health=10;
		Oil.trapList.add(this);
		Oil.oilList.add(this);
	}

	/**
	 * 
	 */
	public void Print(){
		System.out.println("Trap oil ("+this.position.x+","+this.position.y+") health: "+this.health);
	}

	/**
	 * 
	 * @param j
	 */
	public void interact(Jumping j){
		j.onOil();
	}

	/**
	 * 
	 */
	public boolean gooType(){
		return false;
	}

	/**
	 * 
	 * @param nf
	 */
	public void setNormalField(NormalField nf){
		this.currentField=nf;
	}

	/**
	 * 
	 */
	public void cleanup(){
		this.currentField.left(this);
		Oil.trapList.remove(this);
		Oil.oilList.remove(this);
	}

	/**
	 * 
	 */
	public boolean oilType(){
		return true;
	}

	/**
	 * 
	 * @param l
	 */
	public boolean compareType(Landable l){
		return l.oilType();
	}

	/**
	 * 
	 * @param pos
	 */
	public void setPosition(Point pos){
		this.position=pos;
	}

	/**
	 * 
	 */
	public Point getPosition(){
		return this.position;
	}

	/**
	 * 
	 */
	public List<Trap> trapList(){
		return Oil.trapList;
	}
//publikus metódusok vége
}
