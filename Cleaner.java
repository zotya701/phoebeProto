package phoebeProto;

import java.awt.Point;

/**
 * A takarító kisrobotot megvalósító osztály
 */
public class Cleaner implements Landable, Jumping{
	
//privát adattagok kezdete
	/**
	 * A robot állapota. Az unturnable állapot jelzi, hogy a takarító ütközött.
	 */
	private RobotState state;
	
	/**
	 * Referencia a játék pályájára.
	 */
	private Map map;

	/**
	 *A takarítórobot pozíciója a pályán
	 */
	private Point position;

	/**
	 * Referencia a mezõre ahol épp tartózkodik a kisrobot
	 */
	private NormalField currentField;

	/**
	 *  A csapda feltakarítása több körig tart,
	 *  ez az attribútum jelzi, hogy hol tart a kisrobot a takarításban.
	 */
	private int cleaningStage;

	/**
	 * Az a csapda, amit épp takarít.
	 */
	private Trap target;
//privát adattagok vége
	
//publikus metódusok kezdete

	/**
	 * Konstruktor, beállítja a point, a map és a trapList attribútumot,
	 * valamint berakja magát a cleanerList-be. (ha ez nem létezik, akkor létre is hozza)
	 * @param map Referencia a pályára
	 * @param pos A kisrobot kezdõpozíciója
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
	 * Meghívja a jumping onCleaner() függvényét.
	 * @param j Az objektum amelyik ráugrott a kisrobotra
	 */
	public void interact(Jumping j){
		j.onCleaner(this);
	}
	
	/**
	 * Beállítja a currentField attribútumot.
	 * @param nf  A NormalField amire érkezett a kisrobot.
	 */
	public void normalField(NormalField nf){
		this.currentField=nf;
		this.currentField.staying(this);
	}

	/**
	 * A kisrobot mozgását kezelõ metódus:
	 * Ha az állapota Normal, akkor a map-tõl lekéri a legközelebbi csapda irányát, és elindul felé.
	 * Ha a célmezõn áll, akkor a mezõn lévõ elsõ csapdát takarítja.
	 * Ha az állapota UnTurnable, akkor az elõzõtõl egy másik irányba lép.
	 * Ha az állapota Eliminated, akkor nem csinál semmit.

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
	 * Mivel a kisrobot takarítja a csapdákat, ezért ezeknek külön hatása nincs rá.
	 */
	public void onGoo(){
		
	}

	/**
	 * Mivel a kisrobot takarítja a csapdákat, ezért ezeknek külön hatása nincs rá.
	 */
	public void onOil(){
		
	}

	/**
	 *  Megváltoztatja a kisrobot irányát, és az állapotát UnTurnable-be állítja.
	 * @param r A robot akinek ütközött a kisrobot
	 */
	public void onRobot(Robot r){
		this.state=RobotState.Unturnable;
	}

	/**
	 *  Megváltoztatja a kisrobot irányát, és az állapotát UnTurnable-be állítja.
	 * @param c A kisrobot akinek ütközött a kisrobot
	 */
	public void onCleaner(Cleaner c){
		this.state=RobotState.Unturnable;
	}

	/**
	 * Meghívja a kisrobot destroy() metódusát. 
	 * Akkor hívódik meg amikor a kisrobot árokba kerül.
	 */
	public void outside(){
		this.state=RobotState.Eliminated;
		
	}

	/**
	 * A jelenlegi mezõjére olajfoltot rak, 
	 * az állapotát Eliminated-be rakja, meghívja a jelenlegi mezõjén a left() metódust, 
	 * és törli magát a takarítók listájáról.
	 */
	public void destroy(){
		this.currentField.addTrap(new Oil(this.position));	//olajfoltot lak re a mezõre amin van
		this.state=RobotState.Eliminated;
		this.currentField.left(this);						//leszedi magát a mezõrõl amin van
		GameManager.cleaners.remove(this);					//törli magát a listából
	}

	/**
	 * Hamis értéket ad vissza.
	 */
	public boolean oilType(){
		return false;
	}

	/**
	 * Hamis értéket ad vissza.
	 */
	public boolean gooType(){
		return false;
	}

	/**
	 * Az objektum attribútumainak kiíratása a teszteléshez.
	 */
	public void Print(){
		//Cleaner pos:(<posx>,<posy>) stage:<cleaningStage>
		System.out.println("Cleaner pos:("+this.position.x+","+this.position.y+") stage:"+this.cleaningStage);
	}

	/**
	 * Beállítja a következõ eltakarítandó csapdát.
	 * @param trap
	 */
	public void setTarget(Trap trap){
		this.target=trap;
	}
	
	/**
	 * Visszatér a takarítorobot pozíciójával
	 * @return A takarítórobot pozíciója
	 */
	public Point getPosition(){
		return this.position;
	}
//publikus metódusok vége
}
