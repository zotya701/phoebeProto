package phoebeProto;

import java.awt.Point;

/**
 * 
 */
public class Goo implements Trap{
	
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
	
//publikus metódusok kezdete
	/**
	 * 
	 */
	public Goo(Point pos){
		this.position=pos;
		this.health=4;
		GameManager.trapList.add(this);
	}

	/**
	 * 
	 */
	public void Print(){
		System.out.println("Trap goo ("+this.position.x+","+this.position.y+") health: "+this.health);
	}

	/**
	 * 
	 * @param j
	 */
	public void interact(Jumping j){
		j.onGoo();
		this.health	=	this.health-1;
		if(this.health==0)
			this.cleanup();
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
		GameManager.trapList.remove(this);
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
//publikus metódusok vége
}
