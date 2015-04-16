package phoebeProto;

import java.awt.Point;

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
	
//publikus metódusok kezdete
	/**
	 * 
	 */
	public Oil(Point pos){
		this.position=pos;
		this.health=10;
		GameManager.trapList.add(this);
		GameManager.oilList.add(this);
	}

	/**
	 * 
	 */
	public void Print(){
		System.out.println("Trap oil ("+this.position.x+","+this.position.y+") health: "+this.health);
	}
	
	/**
	 * 
	 */
	public void roundElapse(){
		this.health=this.health-1;
		if(this.health==0)
			this.cleanup();
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
		GameManager.trapList.remove(this);
		GameManager.oilList.remove(this);
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
//publikus metódusok vége
}
