package phoebeProto;

import java.awt.Point;

/**
 * Az olaj csapdát megvalósító osztály
 */
public class Oil implements Trap{
	
//privát adattagok kezdete
	/**
	 * A ragacs pozíciója a pályán.
	 */
	private Point position;
	
	/**
	 *  Referencia a mezõre, amin az olaj van.
	 */
	private NormalField currentField;

	/**
	 * Ez jelzi az olaj hátralévõ élettartamát. Minden körben eggyel csökken.
	 * Ha elfogy, az olajfolt eltûnik.
	 */
	private int health;
//privát adattagok vége
	
//publikus metódusok kezdete
	/**
	 * Konstruktor. felrakja magát a trapList és oilList listákra.
	 * @param pos A pozíció ahova kerül
	 */
	public Oil(Point pos){
		this.position=pos;
		this.health=10;
		GameManager.trapList.add(this);
		GameManager.oilList.add(this);
	}

	/**
	 * Az objektum attribútumainak kiíratása a teszteléshez.
	 */
	public void Print(){
		System.out.println("Trap oil ("+this.position.x+","+this.position.y+") health: "+this.health);
	}
	
	/**
	 * Eggyel csökkenti az olaj életét. Ha az olaj élete eléri a 0-t, meghívja a cleanup metódust.
	 */
	public void roundElapsed(){
		this.health=this.health-1;
		if(this.health==0){
			this.cleanup();
		}
	}

	/**
	 * Meghívja a rálépõ Jumping objektum onOil metódusát.
	 * @param j A rálépõ, akivel interaktálni kell.
	 */
	public void interact(Jumping j){
		j.onOil();
	}

	/**
	 * Hamis értéket ad vissza (mert nem ragacs)
	 */
	public boolean gooType(){
		return false;
	}

	/**
	 * Beállítja a currentField attribútumot.
	 * @param nf A NormalField amire az olajat le akarjuk rakni.
	 */
	public void setNormalField(NormalField nf){
		this.currentField=nf;
	}

	/**
	 *  Feltakarítja az olajat a mezõrõl, és törli magát a listákról.
	 */
	public void cleanup(){
		if(this.currentField!=null)
			this.currentField.left(this);
		GameManager.trapList.remove(this);
		GameManager.oilList.remove(this);
	}

	/**
	 * Igaz értéket ad vissza
	 */
	public boolean oilType(){
		return true;
	}

	/**
	 *  Meghívja a paraméterben kapott objektum oilType metódusát, és az értékét visszaadja.
	 * @param l A Landable paraméter 
	 */
	public boolean compareType(Landable l){
		return l.oilType();
	}

	/**
	 * Beállítja a position attribútumot.
	 * @param pos A beállítandó pozíció
	 */
	public void setPosition(Point pos){
		this.position=pos;
	}

	/**
	 * @return 	 Visszaadja az olaj pozícióját.
	 */
	public Point getPosition(){
		return this.position;
	}
//publikus metódusok vége
}
