package phoebeProto;

import java.awt.Point;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * A robotot megvalósító osztály. 
 * Megvalósítja a Jumping és Landable interfészt, 
 * így ugrani is képes, és rá is tudnak ugrani
 */
public class Robot implements Landable, Jumping{
	
//privát adattagok kezdete
	/**
	 * A robot állapota: Lehet Normal, Collided, Eliminated
	 */
	private RobotState state;
	
	/**
	 * Referencia a pályára.
	 */
	private Map map;
	
	/**
	 * A robot jelenlegi pozíciója.
	 */
	private Point position;
	
	/**
	 * A robot sebességvektora.
	 */
	private Point velocity;
	
	/**
	 *  A jelenlegi mezõ, amin épp van a robot.

	 */
	private NormalField currentField;
	
	/**
	 * A robotnak a játék kezdete óta megtett távolsága.
	 */
	private  float routeTravelled;
	
	/**
	 * A még felhasználható ragacskészlet, amit a robot ugráskor maga mögött hagyhat.

	 */
	private  int gooTraps;
	
	/**
	 * A még felhasználható olajkészlet, amit a robot ugráskor maga mögött hagyhat.

	 */
	private  int oilTraps;
	
	
	/**
	 * 
	 */
	//private boolean onOil;
	
	
	/**
	 * 
	 */
	private int id;
//privát adattagok vége
	
//statikus adattagok kezdete
	/**
	 * Legnagyobb ID-jû robot ID-je
	 */
	public static int statid=0;
//statikus adattagok vége
	
//publikus metódusok kezdete
	/**
	 * Konstruktor. Beállítja a pályát, a kezdõ pozíciót, és a sebességvektort.
	 *  Regisztrálja magát a pálya megfelelõ mezõjére.

	 * @param map A pálya referenciája
	 * @param pos Kezdõpozíció
	 * @param vel Kezdõ sebességvektor
	 */
	public Robot(Map map, Point pos, Point vel){
		this.state			=	RobotState.Normal;
		this.map			=	map;
		this.position		=	pos;
		this.velocity		=	vel;
		this.routeTravelled	=	0;
		this.gooTraps		=	3;
		this.oilTraps		=	3;
		//this.onOil			=	false;
		this.id				=	Robot.statid;
		Robot.statid		=	Robot.statid+1;
		this.map.getField(this.position).arrived(this);
	}
	
	/**
	 * Meghívja a jumping onRobot() függvényét.
	 * @param j A jumping objektum aki ráugrott a robotra.
	 */
	public void interact(Jumping j){
		j.onRobot(this);
	}
	
	/**
	 *  Beállítja a currentField attribútumot, meghívja a staying metódusát.
	 * @param nf A NormalField amire érkezett a robot.
	 */
	public void normalField(NormalField nf){
		this.currentField=nf;
		this.currentField.staying(this);
	}
	
	/**
	 * Az objektum attribútumainak kiíratása a teszteléshez
	 */
	public void Print(){
		String state="ilyen nem lehetne";
		if(this.state==RobotState.Eliminated)
			state="eliminated";
		else if(this.state==RobotState.Normal)
			state="normal";
		else if(this.state==RobotState.Unturnable)
			state="unturnable";
		DecimalFormat format		=	new DecimalFormat("0.######");
		DecimalFormatSymbols dfs	=	format.getDecimalFormatSymbols();
	    dfs.setDecimalSeparator('.');
		if(this.routeTravelled==0)
			format	=	new DecimalFormat("0.#");
		format.setDecimalFormatSymbols(dfs);
		System.out.println("Robot id:"+this.id+" pos:("+this.position.x+","+this.position.y+") vel:("+this.velocity.x+","+this.velocity.y+") route: "+format.format(this.routeTravelled)+" goo:"+this.gooTraps+" oil:"+this.oilTraps+" state:"+state);					
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
	 */
	public void jump(Point modifierVelocity){
		if(this.state!=RobotState.Eliminated){
			Point old=new Point(this.position);
			this.currentField.left(this);
			if(this.state!=RobotState.Unturnable){
				this.velocity.translate(modifierVelocity.x, modifierVelocity.y);
			}
			else {
				this.state=RobotState.Normal;
				//this.onOil=false;
			}
			this.position=this.map.getNewPos(this.position, this.velocity);
			this.map.getField(this.position).arrived(this);
			this.routeTravelled=this.map.calculateDistance(old, this.position);
		}
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
		//this.onOil=true;
		this.state=RobotState.Unturnable;
	}
	
	/**
	 * 
	 * @param goo
	 */
	public void placeGoo(){
		if(this.gooTraps>0){
			Goo goo=new Goo(this.position);
			if(this.currentField.addTrap(goo))
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
			if(this.currentField.addTrap(oil))
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
	 * @return
	 */
	public boolean isAlive(){
		if(this.state!=RobotState.Eliminated)
			return true;
		else return false;
	}
	
	/**
	 * 
	 * @return
	 */
	public float getRouteTravelled(){
		return this.routeTravelled;
	}
	
	/**
	 * 
	 * @param c
	 */
	public void onCleaner(Cleaner c){
		c.destroy();
		this.state=RobotState.Unturnable;
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
		this.currentField.left(this);
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





