package phoebeProto;

import java.awt.Point;

/**
 * A ragacs csapd�t megval�s�t� oszt�ly
 */
public class Goo implements Trap{
	
//priv�t adattagok kezdete
	/**
	 * A ragacs poz�ci�ja a p�ly�n
	 */
	private Point position;
	
	/**
	 *  Referencia a mez�re, amin a ragacs van
	 */
	private NormalField currentField;

	/**
	 * A ragacs �lettartama, ha elfogy, a ragacs elt�nik
	 */
	private int health;
//priv�t adattagok v�ge
	
//publikus met�dusok kezdete
	/**
	 * Konstuktor. Be�ll�tja a kezd��rt�keket, �s a trapListre felrakja mag�t.
	 * @param pos Az a poz�ci� ahova le akarjuk rakni a ragacsot.
	 */
	public Goo(Point pos){
		this.position=pos;
		this.health=4;
		GameManager.trapList.add(this);
	}

	/**
	 * Az objektum attrib�tumainak ki�rat�sa a tesztel�shez.
	 */
	public void Print(){
		System.out.println("Trap goo ("+this.position.x+","+this.position.y+") health: "+this.health);
	}

	/**
	 * Megh�vja a r� ugr� Jumping onGoo() met�dus�t
	 * @param j A Jumping objektum aki r�ugrott
	 */
	public void interact(Jumping j){
		j.onGoo();
		this.health	=	this.health-1;
		if(this.health==0)
			this.cleanup();
	}

	/**
	 * Igaz �rt�ket ad vissza.
	 */
	public boolean gooType(){
		return true;
	}

	/**
	 * Be�ll�tja a currentField attrib�tumot
	 * @param nf A NormalField amire a ragacsot le akarjuk rakni.
	 */
	public void setNormalField(NormalField nf){
		this.currentField=nf;
	}

	/**
	 * Feltakar�tja a ragacsot a mez�r�l, �s leszedi a trapList list�r�l.
	 */
	public void cleanup(){
		if(this.currentField!=null)
			this.currentField.left(this);
		GameManager.trapList.remove(this);
	}

	/**
	 * Hamis �rt�ket ad vissza. (mert nem olaj)
	 */
	public boolean oilType(){
		return false;
	}

	/**
	 *  Megh�vja a param�terben kapott objektum gooType met�dus�t, 
	 *  �s az �rt�k�t visszaadja. (igaz ha a mindk�t objektum goo)
	 * @param l A Landable param�ter. 
	 */
	public boolean compareType(Landable l){
		return l.gooType();
	}

	/**
	 * Be�ll�tja a position attrib�tumot
	 * @param pos A be�ll�tand� poz�ci�
	 */
	public void setPosition(Point pos){
		this.position=pos;
	}

	/**
	 * @return  Visszaadja a ragacs poz�ci�j�t.
	 */
	public Point getPosition(){
		return this.position;
	}
//publikus met�dusok v�ge
}
