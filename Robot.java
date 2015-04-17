package phoebeProto;

import java.awt.Point;

/**
 * A robotot megval�s�t� oszt�ly. 
 * Megval�s�tja a Jumping �s Landable interf�szt, 
 * �gy ugrani is k�pes, �s r� is tudnak ugrani
 */
public class Robot implements Landable, Jumping{
	
//priv�t adattagok kezdete
	/**
	 * A robot �llapota: Lehet Normal, Unturnable, Eliminated
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
	 *  
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
		//this.onOil		=	false;
		this.id				=	Robot.statid;
		Robot.statid		=	Robot.statid+1;
		this.map.getField(this.position).arrived(this);
	}
	
	/**
	 * Megh�vja a jumping onRobot() f�ggv�ny�t.
	 * 
	 * @param j A jumping objektum aki r�ugrott a robotra.
	 */
	public void interact(Jumping j){
		j.onRobot(this);
	}
	
	/**
	 *  Be�ll�tja a currentField attrib�tumot, megh�vja a staying met�dus�t.
	 *  
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
		//Robot id:<id> pos:(<posx>,<posy>) vel:(<velx>,<vely>) route: <route> goo:<goo> oil:<oil> state:<state>
		System.out.println("Robot id:"+this.id+" pos:("+this.position.x+","+this.position.y+") vel:("+this.velocity.x+","+this.velocity.y+") route: "+this.routeTravelled+" goo:"+this.gooTraps+" oil:"+this.oilTraps+" state:"+state);					
	}
	
	/**
	 * Seg�dmet�dus, hamis �rt�ket ad vissza.
	 */
	public boolean gooType(){
		return false;
	}
	
	/**
	 * Ugratja a robotot a poz�ci�ja �s sebess�gvektora alapj�n.
	 * 
	 * @param modifierVelocity A sebess�gvektor v�ltoz�sa
	 */
	public void jump(Point modifierVelocity){
		if(this.state!=RobotState.Eliminated){															//csak akkor ugrohat a robot ha "�l" m�g
			Point old=new Point(this.position);															//el kell t�rolni az ugr�s el�tti poz�ci�j�t hogy ki lehessen sz�molni a megtett utat
			this.currentField.left(this);																//el hagyja a jelenlegi mez�t
			if(this.state!=RobotState.Unturnable){														//csak akkor m�dos�tja a sebess�gvektort, ha m�d�s�that�
				this.velocity.translate(modifierVelocity.x, modifierVelocity.y);
			}
			else {
				this.state=RobotState.Normal;
				//this.onOil=false;
			}
			this.position=this.map.getNewPos(this.position, this.velocity);								//�j poz�ci� lek�rdez�se a robot poz�ci�ja �s sebess�gvektora alapj�n
			this.map.getField(this.position).arrived(this);												//a robot meg�rkezik az �j mez�re, �s interakt�l annak elemeivel
			this.routeTravelled=this.routeTravelled+this.map.calculateDistance(old, this.position);		//megtett �t kisz�mol�sa
		}
	}
	
	/**
	 * Felezi a robot sebess�g�t. Ha p�ratlan, lefel� kerek�t.
	 */
	public void onGoo(){
		this.velocity.x=this.velocity.x/2;
		this.velocity.y=this.velocity.y/2;
	}
	
	/**
	 * Seg�dmet�dus, hamis �rt�ket ad vissza.
	 */
	public boolean oilType(){
		return false;
	}
	
	/**
	 * Az onOil attrib�tumot igazz� teszi, 
	 * �s a robot �llapot�t Unturnable-be �ll�tja,
	 * �gy a k�vetkez� k�rben nem m�dos�that� a sebess�gvektor.
	 */
	public void onOil(){
		//this.onOil=true;
		this.state=RobotState.Unturnable;
	}
	
	/**
	 * Lerak egy ragacsot a mez�re amin �ll.
	 */
	public void placeGoo(){
		if(this.gooTraps>0){							//csak akkor rak le ragacsfoltot, ha van mit lerakni
			Goo goo=new Goo(this.position);				
			if(this.currentField.addTrap(goo))			//ha siker�lt a lerak�s
				this.gooTraps	=	this.gooTraps-1;	//cs�kkenti a lerakhat� ragacsfoltok sz�m�t
		}
	}
	
	/**
	 * �sszehasonl�tja a k�t egym�ssal �tk�z�tt robot sebess�g�t,
	 *  majd a kisebb sebess�g�t kiejti a j�t�kb�l,
	 *  a nagyobb sebess�ge pedig kettej�k �tlagsebess�ge lesz.
	 *  
	 * @param r A m�sik robot, evvel �tk�zik
	 */
	public void onRobot(Robot r){
		if(r.getSpeed()>this.getSpeed()){	//ha az �tk�z�tt robot sebess�ge nagyobb mint az �tk�z��
			//r.halveSpeed();
			r.velocity.x=(r.velocity.x+this.velocity.x)/2;
			r.velocity.y=(r.velocity.y+this.velocity.y)/2;
			this.destroy();
		}
		else{
			this.velocity.x=(r.velocity.x+this.velocity.x)/2;
			this.velocity.y=(r.velocity.y+this.velocity.y)/2;
			r.destroy();
		}
	}
	
	/**
	 * Lerak egy olajat a mez�re amin �ll.
	 */
	public void placeOil(){
		if(this.gooTraps>0){							//csak akkor rak le olajfoltot, ha van mit lerakni
			Oil oil=new Oil(this.position);
			if(this.currentField.addTrap(oil))			//ha siker�lt a lerak�s
				this.oilTraps	=	this.oilTraps-1;	//cs�kkenti a lerakhat� ragacsfoltok sz�m�t
		}
	}
	
	/**
	 *  Visszaadja a robot aktu�lis sebess�g�t.
	 *  
	 * @return A robot sebess�ge
	 */
	public float getSpeed(){
		//szok�sos gy�kalatt(x^2+y^2)
		return (float)(Math.pow(this.velocity.x*this.velocity.x+this.velocity.y*this.velocity.y, 0.5));
	}
	
	/**
	 * Lek�rdezi, hogy �l-e m�g a robot
	 * 
	 * @return igaz ha �l m�g a robot(Normal, vagy Unturnable), hamis ha Eliminated
	 */
	public boolean isAlive(){
		if(this.state!=RobotState.Eliminated)
			return true;
		else return false;
	}
	
	/**
	 * A robot eddig megtett t�vols�g�nak lek�rdez�se
	 * 
	 * @return A t�vols�g
	 */
	public float getRouteTravelled(){
		return this.routeTravelled;
	}
	
	/**
	 * A robot kisrobotra ugr�sa, amit elpuszt�t
	 * 
	 * @param c A Cleaner amire r�ugrott
	 */
	public void onCleaner(Cleaner c){
		c.destroy();	//ksirobot megsemmis�l�sekor azonnal olajfolt keletkezik a mez�n, viszont
						//ekkor az arrived met�dusban nem fog interakt�lni az �jonnan l�trehozott olajfolttal,
						//mert az m�g nem szerepel a t�mbben
		this.onOil();	//ez�rt itt �ll�tjuk be, hogy m�dos�thatatlan legyen a sebess�ge egy k�rig
	}
	
	/**
	 * A robot �llapot�t Eliminated �llapotba �ll�tja. �rokba ugr�s eset�n
	 */
	public void outside(){
		this.state=RobotState.Eliminated;
	}
	
	/**
	 * A robot �llapot�t Eliminated �llapotba �ll�tja, �s elt�vol�tja az eddigi mez�j�r�l.
	 * Akkor h�v�dik meg amikor egy gyorsabb robottal �tk�zik.
	 */
	public void destroy(){
		this.state=RobotState.Eliminated;
		this.currentField.left(this);
	}
//publikus met�dusok v�ge
}





