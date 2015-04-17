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
		this.cleaningStage	=	0;
		this.target			=	null;
		this.map.getField(this.position).arrived(this);
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
		this.currentField.staying(this);
	}

	/**
	 * 
	 */
	public void move(){
		if(this.state!=RobotState.Eliminated){
			Point oldPos	=	new Point(this.position);
			this.currentField.left(this);
			if(this.state==RobotState.Normal){
				this.position	=	this.map.getRouteToTrap(this.position, this);
			}
			map.getField(this.position).arrived(this);
			if(this.state==RobotState.Unturnable){//ha a takarítórobot elõtt robot vagy takarítórobot van, nem mozdul.
				this.state=RobotState.Normal;
				this.currentField.left(this);
				this.position	=	oldPos;
				map.getField(this.position).arrived(this);
			}
			if(this.target!=null){
				if(target.getPosition().equals(oldPos)){
					this.cleaningStage	=	this.cleaningStage+1;
				}
			}
			if(this.cleaningStage>=2){
				this.target.cleanup();
				this.target	=	null;
				this.cleaningStage=0;
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
