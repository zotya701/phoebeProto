package phoebeProto;

import java.awt.Point;

/**
 * 
 */
public class Cleaner implements Landable, Jumping{
	
//priv�t adattagok kezdete
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
//priv�t adattagok v�ge
	
//publikus met�dusok kezdete

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
		this.currentField.addTrap(new Oil(this.position));	//olajfoltot lak re a mez�re amin van
		this.state=RobotState.Eliminated;
		this.currentField.left(this);						//leszedi mag�t a mez�r�l amin van
		GameManager.cleaners.remove(this);					//t�rli mag�t a list�b�l
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
//publikus met�dusok v�ge
}
