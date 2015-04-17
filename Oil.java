package phoebeProto;

import java.awt.Point;

/**
 * Az olaj csapd�t megval�s�t� oszt�ly
 */
public class Oil implements Trap{
	
//priv�t adattagok kezdete
	/**
	 * A ragacs poz�ci�ja a p�ly�n.
	 */
	private Point position;
	
	/**
	 *  Referencia a mez�re, amin az olaj van.
	 */
	private NormalField currentField;

	/**
	 * Ez jelzi az olaj h�tral�v� �lettartam�t. Minden k�rben eggyel cs�kken.
	 * Ha elfogy, az olajfolt elt�nik.
	 */
	private int health;
//priv�t adattagok v�ge
	
//publikus met�dusok kezdete
	/**
	 * Konstruktor. felrakja mag�t a trapList �s oilList list�kra.
	 * @param pos A poz�ci� ahova ker�l
	 */
	public Oil(Point pos){
		this.position=pos;
		this.health=10;
		GameManager.trapList.add(this);
		GameManager.oilList.add(this);
	}

	/**
	 * Az objektum attrib�tumainak ki�rat�sa a tesztel�shez.
	 */
	public void Print(){
		System.out.println("Trap oil ("+this.position.x+","+this.position.y+") health: "+this.health);
	}
	
	/**
	 * Eggyel cs�kkenti az olaj �let�t. Ha az olaj �lete el�ri a 0-t, megh�vja a cleanup met�dust.
	 */
	public void roundElapsed(){
		this.health=this.health-1;
		if(this.health==0){
			this.cleanup();
		}
	}

	/**
	 * Megh�vja a r�l�p� Jumping objektum onOil met�dus�t.
	 * @param j A r�l�p�, akivel interakt�lni kell.
	 */
	public void interact(Jumping j){
		j.onOil();
	}

	/**
	 * Hamis �rt�ket ad vissza (mert nem ragacs)
	 */
	public boolean gooType(){
		return false;
	}

	/**
	 * Be�ll�tja a currentField attrib�tumot.
	 * @param nf A NormalField amire az olajat le akarjuk rakni.
	 */
	public void setNormalField(NormalField nf){
		this.currentField=nf;
	}

	/**
	 *  Feltakar�tja az olajat a mez�r�l, �s t�rli mag�t a list�kr�l.
	 */
	public void cleanup(){
		if(this.currentField!=null)
			this.currentField.left(this);
		GameManager.trapList.remove(this);
		GameManager.oilList.remove(this);
	}

	/**
	 * Igaz �rt�ket ad vissza
	 */
	public boolean oilType(){
		return true;
	}

	/**
	 *  Megh�vja a param�terben kapott objektum oilType met�dus�t, �s az �rt�k�t visszaadja.
	 * @param l A Landable param�ter 
	 */
	public boolean compareType(Landable l){
		return l.oilType();
	}

	/**
	 * Be�ll�tja a position attrib�tumot.
	 * @param pos A be�ll�tand� poz�ci�
	 */
	public void setPosition(Point pos){
		this.position=pos;
	}

	/**
	 * @return 	 Visszaadja az olaj poz�ci�j�t.
	 */
	public Point getPosition(){
		return this.position;
	}
//publikus met�dusok v�ge
}
