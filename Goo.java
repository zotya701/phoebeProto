package phoebeProto;

import java.awt.Point;

/**
 * A ragacs csapdát megvalósító osztály
 */
public class Goo implements Trap{
	
//privát adattagok kezdete
	/**
	 * A ragacs pozíciója a pályán
	 */
	private Point position;
	
	/**
	 *  Referencia a mezõre, amin a ragacs van
	 */
	private NormalField currentField;

	/**
	 * A ragacs élettartama, ha elfogy, a ragacs eltûnik
	 */
	private int health;
//privát adattagok vége
	
//publikus metódusok kezdete
	/**
	 * Konstuktor. Beállítja a kezdõértékeket, és a trapListre felrakja magát.
	 * @param pos Az a pozíció ahova le akarjuk rakni a ragacsot.
	 */
	public Goo(Point pos){
		this.position=pos;
		this.health=4;
		GameManager.trapList.add(this);
	}

	/**
	 * Az objektum attribútumainak kiíratása a teszteléshez.
	 */
	public void Print(){
		System.out.println("Trap goo ("+this.position.x+","+this.position.y+") health: "+this.health);
	}

	/**
	 * Meghívja a rá ugró Jumping onGoo() metódusát
	 * @param j A Jumping objektum aki ráugrott
	 */
	public void interact(Jumping j){
		j.onGoo();
		this.health	=	this.health-1;
		if(this.health==0)
			this.cleanup();
	}

	/**
	 * Igaz értéket ad vissza.
	 */
	public boolean gooType(){
		return true;
	}

	/**
	 * Beállítja a currentField attribútumot
	 * @param nf A NormalField amire a ragacsot le akarjuk rakni.
	 */
	public void setNormalField(NormalField nf){
		this.currentField=nf;
	}

	/**
	 * Feltakarítja a ragacsot a mezõrõl, és leszedi a trapList listáról.
	 */
	public void cleanup(){
		if(this.currentField!=null)
			this.currentField.left(this);
		GameManager.trapList.remove(this);
	}

	/**
	 * Hamis értéket ad vissza. (mert nem olaj)
	 */
	public boolean oilType(){
		return false;
	}

	/**
	 *  Meghívja a paraméterben kapott objektum gooType metódusát, 
	 *  és az értékét visszaadja. (igaz ha a mindkét objektum goo)
	 * @param l A Landable paraméter. 
	 */
	public boolean compareType(Landable l){
		return l.gooType();
	}

	/**
	 * Beállítja a position attribútumot
	 * @param pos A beállítandó pozíció
	 */
	public void setPosition(Point pos){
		this.position=pos;
	}

	/**
	 * @return  Visszaadja a ragacs pozícióját.
	 */
	public Point getPosition(){
		return this.position;
	}
//publikus metódusok vége
}
