package phoebeProto;

import java.awt.Point;

/**
 * 
 */
public class Robot implements Landable, Jumping{
	
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
	private Point velocity;
	
	/**
	 * 
	 */
	private NormalField currentField;
	
	/**
	 * 
	 */
	private  float routeTravelled;
	
	/**
	 * 
	 */
	private  int gooTraps;
	
	/**
	 * 
	 */
	private  int oilTraps;
	
	/**
	 * 
	 */
	private boolean onOil;
	
	/**
	 * 
	 */
	private int id;
//privát adattagok vége
	
	/**
	 * 
	 */
	public static int statid=0;
	
//publikus metódusok kezdete
	/**
	 * 
	 * @param map
	 * @param pos
	 * @param vel
	 */
	public Robot(Map map, Point pos, Point vel){
		this.state			=	RobotState.Normal;
		this.map			=	map;
		this.position		=	pos;
		this.velocity		=	vel;
		this.routeTravelled	=	0;
		this.gooTraps		=	3;
		this.oilTraps		=	3;
		this.onOil			=	false;
		this.id				=	Robot.statid;
		Robot.statid		=	Robot.statid+1;
		this.map.getField(this.position).arrived(this);
	}
	
	/**
	 * 
	 * @param j
	 */
	public void interact(Jumping j){
		j.onRobot(this);
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
	public void Print(){
		String state="ilyen nem lehetne";
		if(this.state==RobotState.Eliminated)
			state="eliminated";
		else if(this.state==RobotState.Normal)
			state="normal";
		else if(this.state==RobotState.Unturnable)
			state="unturnable";
		System.out.println("Robot id:"+this.id+" pos:("+this.position.x+","+this.position.y+") vel:("+this.velocity.x+","+this.velocity.y+") route: "+this.routeTravelled+" goo:"+this.gooTraps+" oil:"+this.oilTraps+" state:"+state);					
	}
	
	/**
	 * 
	 */
	public boolean gooType(){
		return false;
	}
	
	/**
	 * 
	 * @param modifierVelocity
	 * @return
	 */
	public RobotState jump(Point modifierVelocity){
		
		return this.state;
	}
	
	/**
	 * 
	 */
	public void onGoo(){
		this.halveSpeed();
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
	public void onOil(){
		this.onOil=true;
		this.state=RobotState.Unturnable;
	}
	
	/**
	 * 
	 * @param goo
	 */
	public void placeGoo(){
		if(this.gooTraps>0){
			Goo goo=new Goo(this.position);
			this.currentField.addTrap(goo);
			this.gooTraps	=	this.gooTraps-1;
		}
	}
	
	/**
	 * 
	 * @param r
	 */
	public void onRobot(Robot r){
		if(r.getSpeed()>this.getSpeed()){
			this.destroy();		
			r.halveSpeed();
		}
		else{
			r.destroy();
			this.halveSpeed();
		}
	}
	
	/**
	 * 
	 * @param oil
	 */
	public void placeOil(){
		if(this.gooTraps>0){
			Oil oil=new Oil(this.position);
			this.currentField.addTrap(oil);
			this.oilTraps	=	this.oilTraps-1;
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public float getSpeed(){
		return (float)(Math.pow(this.velocity.x*this.velocity.x+this.velocity.y*this.velocity.y, 0.5));
	}
	
	/**
	 * 
	 * @param clr
	 */
	public void onCleaner(Cleaner clr){
		//clr.destroy();
	}
	
	/**
	 * 
	 */
	public void outside(){
		this.state=RobotState.Eliminated;
	}
	
	/**
	 * 
	 */
	public void destroy(){
		this.state=RobotState.Eliminated;
	}
	
	/**
	 * 
	 */
	public void halveSpeed(){
		this.velocity.x=this.velocity.x/2;
		this.velocity.y=this.velocity.y/2;
	}
//publikus metódusok vége
}





