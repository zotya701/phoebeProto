package phoebeProto;

import java.awt.Point;

/**
 * 
 */
public class Oil implements Trap{
	
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
	
//publikus met�dusok kezdete
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
		this.health=this.health-1;		//minden k�rben 1-el cs�kken az �lete
		if(this.health==0){				//ha el�rte a 0-�t
			this.cleanup();				//akkor "felsz�rad"
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
			this.currentField.left(this);	//t�rli mag�t a mez�r�l amin van
		GameManager.trapList.remove(this);	//a csapd�k list�j�b�l is t�rli mag�t
		GameManager.oilList.remove(this);	//az olajfoltok list�j�b�l is t�rli mag�t
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
//publikus met�dusok v�ge
}
