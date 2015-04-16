package phoebeProto;

import java.awt.Point;

/**
 * A robotot megvalósító osztály. 
 * Megvalósítja a Jumping és Landable interfészt, 
 * így ugrani is képes, és rá is tudnak ugrani
 */
public class Robot implements Landable, Jumping{
	
//privát adattagok kezdete
	/**
	 * A robot állapota: Lehet Normal, Unturnable, Eliminated
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
		System.out.println("Robot id:"+this.id+" pos:("+this.position.x+","+this.position.y+") vel:("+this.velocity.x+","+this.velocity.y+") route: "+this.routeTravelled+" goo:"+this.gooTraps+" oil:"+this.oilTraps+" state:"+state);					
	}
	
	/**
	 * Segédmetódus, hamis értéket ad vissza.
	 */
	public boolean gooType(){
		return false;
	}
	
	/**
	 * Ugratja a robotot a pozíciója és sebességvektora alapján, majd visszatér az állapotával.
	 * @param modifierVelocity A sebességvektor változása
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
	 * Felezi a robot sebességét. Ha páratlan, lefelé kerekít.
	 */
	public void onGoo(){
		this.halveSpeed();
	}
	
	/**
	 * Segédmetódus, hamis értéket ad vissza.
	 */
	public boolean oilType(){
		return false;
	}
	
	/**
	 * Az onOil attribútumot igazzá teszi, 
	 * és a robot állapotát Unturnable-be állítja,
	 * így a következõ körben nem módosítható a sebességvektor.
	 */
	public void onOil(){
		//this.onOil=true;
		this.state=RobotState.Unturnable;
	}
	
	/**
	 * Lerak egy ragacsot a mezõre amin áll.
	 */
	public void placeGoo(){
		if(this.gooTraps>0){
			Goo goo=new Goo(this.position);
			if(this.currentField.addTrap(goo))
				this.gooTraps	=	this.gooTraps-1;
		}
	}
	
	/**
	 * Összehasonlítja a két egymással ütközött robot sebességét,
	 *  majd a kisebb sebességût kiejti a játékból,
	 *  a nagyobb sebessége pedig kettejük átlagsebessége lesz.

	 * @param r A másik robot, evvel ütközik
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
	 * Lerak egy olajat a mezõre amin áll.
	 */
	public void placeOil(){
		if(this.gooTraps>0){
			Oil oil=new Oil(this.position);
			if(this.currentField.addTrap(oil))
				this.oilTraps	=	this.oilTraps-1;
		}
	}
	
	/**
	 *  Visszaadja a robot aktuális sebességét.
	 * @return A robot sebessége
	 */
	public float getSpeed(){
		return (float)(Math.pow(this.velocity.x*this.velocity.x+this.velocity.y*this.velocity.y, 0.5));
	}
	
	/**
	 * Lekérdezi, hogy él-e még a robot
	 * @return igaz ha él még a robot(Normal, vagy Unturnable), hamis ha Eliminated
	 */
	public boolean isAlive(){
		if(this.state!=RobotState.Eliminated)
			return true;
		else return false;
	}
	
	/**
	 * A robot eddig megtett távolságának lekérdezése
	 * @return A távolság
	 */
	public float getRouteTravelled(){
		return this.routeTravelled;
	}
	
	/**
	 * A robot kisrobotra ugrása, amit elpusztít
	 * @param c A Cleaner amire ráugrott
	 */
	public void onCleaner(Cleaner c){
		c.destroy();
		this.state=RobotState.Unturnable;
	}
	
	/**
	 * A robot állapotát Eliminated állapotba állítja. Árokba ugrás esetén
	 */
	public void outside(){
		this.state=RobotState.Eliminated;
	}
	
	/**
	 * A robot állapotát Eliminated állapotba állítja, és eltávolítja az eddigi mezõjérõl.
	 * Akkor hívódik meg amikor egy gyorsabb robottal ütközik.
	 */
	public void destroy(){
		this.state=RobotState.Eliminated;
		this.currentField.left(this);
	}
	
	/**
	 * Sebességvektor felezése
	 */
	public void halveSpeed(){
		this.velocity.x=this.velocity.x/2;
		this.velocity.y=this.velocity.y/2;
	}
//publikus metódusok vége
}





