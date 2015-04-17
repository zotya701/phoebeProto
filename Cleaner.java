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
		if(this.state!=RobotState.Eliminated){										//csak akkor lép, ha "él"
			Point oldPos	=	new Point(this.position);
			this.currentField.left(this);											//elhagyja a mezõt amin áll
			if(this.state==RobotState.Normal){										//csak akkor, ha normál állapotban van
				this.position	=	this.map.getRouteToTrap(this.position, this);	//lekéri a map-tõl az új pozícióját
			}
			map.getField(this.position).arrived(this);								//megérkezik az új mezõre
			if(this.state==RobotState.Unturnable){									//ha az új mezõn ütközött, unturnable lesz az állapota így ez is lefut
				this.state=RobotState.Normal;										//és ilyenkor visszamegy oda ahonnan jött
				this.currentField.left(this);										//ez a játékban úgy jelenik meg, hogy
				this.position	=	oldPos;											//ha a takarítórobot elõtt van valamilyen más robot,
				map.getField(this.position).arrived(this);							//akkor nem mozdul.
			}
			if(this.target!=null){													//csak akkor ha van egyáltalán target
				if(target.getPosition().equals(oldPos)){							//és annak pozíciója megegyezik a takarítórobot pozíciójával
					this.cleaningStage	=	this.cleaningStage+1;					//akkor takarítja a foltot
				}
			}
			if(this.cleaningStage>=2){												//2 kör alatt feltakarítja a foltot
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
		this.currentField.addTrap(new Oil(this.position));	//olajfoltot lak re a mezõre amin van
		this.state=RobotState.Eliminated;
		this.currentField.left(this);						//leszedi magát a mezõrõl amin van
		GameManager.cleaners.remove(this);					//törli magát a listából
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
		//Cleaner pos:(<posx>,<posy>) stage:<cleaningStage>
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
