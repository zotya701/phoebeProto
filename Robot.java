package phoebeProto;

import java.awt.Point;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * A robotot megval�s�t� oszt�ly. 
 * Megval�s�tja a Jumping �s Landable interf�szt, 
 * �gy ugrani is k�pes, �s r� is tudnak ugrani
 */
public class Robot implements Landable, Jumping{
	
//priv�t adattagok kezdete
	/**
	 * A robot �llapota: Lehet Normal, Collided, Eliminated
	 */
	private RobotState state;
	
	/**
	 * Referencia a p�ly�ra.
	 */
	private Map map;
	
	/**
	 * A robot jelenlegi poz�ci�ja.
	 */
	private Point position;
	
	/**
	 * A robot sebess�gvektora.
	 */
	private Point velocity;
	
	/**
	 *  A jelenlegi mez�, amin �pp van a robot.

	 */
	private NormalField currentField;
	
	/**
	 * A robotnak a j�t�k kezdete �ta megtett t�vols�ga.
	 */
	private  float routeTravelled;
	
	/**
	 * A m�g felhaszn�lhat� ragacsk�szlet, amit a robot ugr�skor maga m�g�tt hagyhat.

	 */
	private  int gooTraps;
	
	/**
	 * A m�g felhaszn�lhat� olajk�szlet, amit a robot ugr�skor maga m�g�tt hagyhat.

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
//priv�t adattagok v�ge
	
//statikus adattagok kezdete
	/**
	 * Legnagyobb ID-j� robot ID-je
	 */
	public static int statid=0;
//statikus adattagok v�ge
	
//publikus met�dusok kezdete
	/**
	 * Konstruktor. Be�ll�tja a p�ly�t, a kezd� poz�ci�t, �s a sebess�gvektort.
	 *  Regisztr�lja mag�t a p�lya megfelel� mez�j�re.

	 * @param map A p�lya referenci�ja
	 * @param pos Kezd�poz�ci�
	 * @param vel Kezd� sebess�gvektor
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
	 * Megh�vja a jumping onRobot() f�ggv�ny�t.
	 * @param j A jumping objektum aki r�ugrott a robotra.
	 */
	public void interact(Jumping j){
		j.onRobot(this);
	}
	
	/**
	 *  Be�ll�tja a currentField attrib�tumot, megh�vja a staying met�dus�t.
	 * @param nf A NormalField amire �rkezett a robot.
	 */
	public void normalField(NormalField nf){
		this.currentField=nf;
		this.currentField.staying(this);
	}
	
	/**
	 * Az objektum attrib�tumainak ki�rat�sa a tesztel�shez
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
//publikus met�dusok v�ge
}





