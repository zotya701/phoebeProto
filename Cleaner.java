package phoebeProto;

import java.awt.Point;

/**
 * A takar�t� kisrobotot megval�s�t� oszt�ly
 */
public class Cleaner implements Landable, Jumping{
	
//priv�t adattagok kezdete
	/**
	 * A robot �llapota. Az unturnable �llapot jelzi, hogy a takar�t� �tk�z�tt.
	 */
	private RobotState state;
	
	/**
	 * Referencia a j�t�k p�ly�j�ra.
	 */
	private Map map;

	/**
	 *A takar�t�robot poz�ci�ja a p�ly�n
	 */
	private Point position;

	/**
	 * Referencia a mez�re ahol �pp tart�zkodik a kisrobot
	 */
	private NormalField currentField;

	/**
	 *  A csapda feltakar�t�sa t�bb k�rig tart,
	 *  ez az attrib�tum jelzi, hogy hol tart a kisrobot a takar�t�sban.
	 */
	private int cleaningStage;

	/**
	 * Az a csapda, amit �pp takar�t.
	 */
	private Trap target;
//priv�t adattagok v�ge
	
//publikus met�dusok kezdete

	/**
	 * Konstruktor, be�ll�tja a point, a map �s a trapList attrib�tumot,
	 * valamint berakja mag�t a cleanerList-be. (ha ez nem l�tezik, akkor l�tre is hozza)
	 * @param map Referencia a p�ly�ra
	 * @param pos A kisrobot kezd�poz�ci�ja
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
	 * Megh�vja a jumping onCleaner() f�ggv�ny�t.
	 * @param j Az objektum amelyik r�ugrott a kisrobotra
	 */
	public void interact(Jumping j){
		j.onCleaner(this);
	}
	
	/**
	 * Be�ll�tja a currentField attrib�tumot.
	 * @param nf  A NormalField amire �rkezett a kisrobot.
	 */
	public void normalField(NormalField nf){
		this.currentField=nf;
		this.currentField.staying(this);
	}

	/**
	 * A kisrobot mozg�s�t kezel� met�dus:
	 * Ha az �llapota Normal, akkor a map-t�l lek�ri a legk�zelebbi csapda ir�ny�t, �s elindul fel�.
	 * Ha a c�lmez�n �ll, akkor a mez�n l�v� els� csapd�t takar�tja.
	 * Ha az �llapota UnTurnable, akkor az el�z�t�l egy m�sik ir�nyba l�p.
	 * Ha az �llapota Eliminated, akkor nem csin�l semmit.

	 */
	public void move(){
		if(this.state!=RobotState.Eliminated){										//csak akkor l�p, ha "�l"
			Point oldPos	=	new Point(this.position);
			this.currentField.left(this);											//elhagyja a mez�t amin �ll
			if(this.state==RobotState.Normal){										//csak akkor, ha norm�l �llapotban van
				this.position	=	this.map.getRouteToTrap(this.position, this);	//lek�ri a map-t�l az �j poz�ci�j�t
			}
			map.getField(this.position).arrived(this);								//meg�rkezik az �j mez�re
			if(this.state==RobotState.Unturnable){									//ha az �j mez�n �tk�z�tt, unturnable lesz az �llapota �gy ez is lefut
				this.state=RobotState.Normal;										//�s ilyenkor visszamegy oda ahonnan j�tt
				this.currentField.left(this);										//ez a j�t�kban �gy jelenik meg, hogy
				this.position	=	oldPos;											//ha a takar�t�robot el�tt van valamilyen m�s robot,
				map.getField(this.position).arrived(this);							//akkor nem mozdul.
			}
			if(this.target!=null){													//csak akkor ha van egy�ltal�n target
				if(target.getPosition().equals(oldPos)){							//�s annak poz�ci�ja megegyezik a takar�t�robot poz�ci�j�val
					this.cleaningStage	=	this.cleaningStage+1;					//akkor takar�tja a foltot
				}
			}
			if(this.cleaningStage>=2){												//2 k�r alatt feltakar�tja a foltot
				this.target.cleanup();
				this.target	=	null;
				this.cleaningStage=0;
			}
		}
	}
	

	/**
	 * Mivel a kisrobot takar�tja a csapd�kat, ez�rt ezeknek k�l�n hat�sa nincs r�.
	 */
	public void onGoo(){
		
	}

	/**
	 * Mivel a kisrobot takar�tja a csapd�kat, ez�rt ezeknek k�l�n hat�sa nincs r�.
	 */
	public void onOil(){
		
	}

	/**
	 *  Megv�ltoztatja a kisrobot ir�ny�t, �s az �llapot�t UnTurnable-be �ll�tja.
	 * @param r A robot akinek �tk�z�tt a kisrobot
	 */
	public void onRobot(Robot r){
		this.state=RobotState.Unturnable;
	}

	/**
	 *  Megv�ltoztatja a kisrobot ir�ny�t, �s az �llapot�t UnTurnable-be �ll�tja.
	 * @param c A kisrobot akinek �tk�z�tt a kisrobot
	 */
	public void onCleaner(Cleaner c){
		this.state=RobotState.Unturnable;
	}

	/**
	 * Megh�vja a kisrobot destroy() met�dus�t. 
	 * Akkor h�v�dik meg amikor a kisrobot �rokba ker�l.
	 */
	public void outside(){
		this.state=RobotState.Eliminated;
		
	}

	/**
	 * A jelenlegi mez�j�re olajfoltot rak, 
	 * az �llapot�t Eliminated-be rakja, megh�vja a jelenlegi mez�j�n a left() met�dust, 
	 * �s t�rli mag�t a takar�t�k list�j�r�l.
	 */
	public void destroy(){
		this.currentField.addTrap(new Oil(this.position));	//olajfoltot lak re a mez�re amin van
		this.state=RobotState.Eliminated;
		this.currentField.left(this);						//leszedi mag�t a mez�r�l amin van
		GameManager.cleaners.remove(this);					//t�rli mag�t a list�b�l
	}

	/**
	 * Hamis �rt�ket ad vissza.
	 */
	public boolean oilType(){
		return false;
	}

	/**
	 * Hamis �rt�ket ad vissza.
	 */
	public boolean gooType(){
		return false;
	}

	/**
	 * Az objektum attrib�tumainak ki�rat�sa a tesztel�shez.
	 */
	public void Print(){
		//Cleaner pos:(<posx>,<posy>) stage:<cleaningStage>
		System.out.println("Cleaner pos:("+this.position.x+","+this.position.y+") stage:"+this.cleaningStage);
	}

	/**
	 * Be�ll�tja a k�vetkez� eltakar�tand� csapd�t.
	 * @param trap
	 */
	public void setTarget(Trap trap){
		this.target=trap;
	}
	
	/**
	 * Visszat�r a takar�torobot poz�ci�j�val
	 * @return A takar�t�robot poz�ci�ja
	 */
	public Point getPosition(){
		return this.position;
	}
//publikus met�dusok v�ge
}
