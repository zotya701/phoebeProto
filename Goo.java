package phoebeProto;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class Goo implements Trap{
	
//priv�t adattagok kezdete
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
//priv�t adattagok v�ge
	
	/**
	 * 
	 */
	static public List<Trap> trapList=new ArrayList<Trap>();
	
//publikus met�dusok kezdete
	/**
	 * 
	 */
	public Goo(Point pos){
		this.position=pos;
		this.health=4;
		Goo.trapList.add(this);
	}

	/**
	 * 
	 */
	public void Print(){
		System.out.println("Trap Goo ("+this.position.x+","+this.position.y+") health: "+this.health);
	}

	/**
	 * 
	 * @param j
	 */
	public void interact(Jumping j){
		j.onGoo();
	}

	/**
	 * 
	 */
	public boolean gooType(){
		return true;
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
		Goo.trapList.remove(this);
	}

	/**
	 * 
	 */
	public boolean oilType(){
		return false;
	}

	/**
	 * 
	 * @param l
	 */
	public boolean compareType(Landable l){
		return l.gooType();
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
		return Goo.trapList;
	}
//publikus met�dusok v�ge
}
