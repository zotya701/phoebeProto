package phoebeProto;

import java.awt.Point;

/**
 * 
 */
public class Cleaner implements Landable, Jumping{
	
//privát adattagok kezdete
	/**
	 * 
	 */
	private RobotState state;
	
	/**
	 * 
	 */
	private Map map;

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
	private int cleaningStage;

	/**
	 * 
	 */
	private Trap target;
//privát adattagok vége
	
//publikus metódusok kezdete

	/**
	 * 
	 * @param map
	 * @param pos
	 */
	public Cleaner(Map map, Point pos){
		this.state			=	RobotState.Normal;
		this.map			=	map;
		this.position		=	pos;
		this.currentField	=	null;
		this.cleaningStage	=	0;
		this.target			=	null;
	}
	
	/**
	 * 
	 * @param j
	 */
	public void interact(Jumping j){
		j.onCleaner(this);
	}
	
	/**
	 * 
	 * @param nf
	 */
	public void normalField(NormalField nf){
		this.currentField=nf;
	}

	/**
	 * 
	 */
	public void move(){
		if(this.state!=RobotState.Eliminated){
			this.currentField.left(this);
			if(this.state==RobotState.Normal)
				this.position	=	this.map.getRouteToTrap(this.position, this);
			map.getField(this.position).arrived(this);
			
			if(target.getPosition().equals(this.position)){
				this.cleaningStage=this.cleaningStage+1;
			}
			else this.cleaningStage=0;
			
			if(this.cleaningStage>=2){
				this.target.cleanup();
			}
		}
	}
	

	/**
	 * 
	 */
	public void onGoo(){
		
	}

	/**
	 * 
	 */
	public void onOil(){
		
	}

	/**
	 * 
	 * @param r
	 */
	public void onRobot(Robot r){
		this.state=RobotState.Unturnable;
	}

	/**
	 * 
	 * @param c
	 */
	public void onCleaner(Cleaner c){
		this.state=RobotState.Unturnable;
	}

	/**
	 * 
	 */
	public void outside(){
		this.destroy();
	}

	/**
	 * 
	 */
	public void destroy(){
		this.currentField.addTrap(new Oil(this.position));
		this.state=RobotState.Eliminated;
		this.currentField.left(this);
		GameManager.cleaners.remove(this);
	}

	/**
	 * 
	 */
	public boolean oilType(){
		return false;
	}

	/**
	 * 
	 */
	public boolean gooType(){
		return false;
	}

	/**
	 * 
	 */
	public void Print(){
		System.out.println("Cleaner pos:("+this.position.x+","+this.position.y+") stage:"+this.cleaningStage);
	}

	/**
	 * 
	 * @param trap
	 */
	public void setTarget(Trap trap){
		this.target=trap;
	}
//publikus metódusok vége
}
