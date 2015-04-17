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
		this.position	=	pos;
		this.health		=	10;
		GameManager.trapList.add(this);
		GameManager.oilList.add(this);
	}

	/**
	 * 
	 */
	public void Print(){
		//Trap <type> (<x>,<y>) health: <health>
		System.out.println("Trap oil ("+this.position.x+","+this.position.y+") health: "+this.health);
	}
	
	/**
	 * 
	 */
	public void roundElapsed(){
		this.health=this.health-1;		//minden körben 1-el csökken az élete
		if(this.health==0){				//ha elérte a 0-át
			this.cleanup();				//akkor "felszárad"
		}
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
		if(this.currentField!=null)
			this.currentField.left(this);	//törli magát a mezõrõl amin van
		GameManager.trapList.remove(this);	//a csapdák listájából is törli magát
		GameManager.oilList.remove(this);	//az olajfoltok listájából is törli magát
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
