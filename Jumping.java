package phoebeProto;

import java.awt.Point;

/**
 * Elõírja, hogy milyen metódusokat kell megvalósítani egy ugrásra képes osztálynak.
 */
public interface Jumping {
	
	/**
	 * Ez történik, ha normalField-re ugrik, függetlenül attól,
	 * hogy ott van-e robot vagy csapda, vagy nincs.
	 * @param nf A NormalField amire ugrik.
	 */
	public void normalField(NormalField nf);

	/**
	 * Ez a függvény hívódik meg ha ragacsra ugrik.
	 */
	public void onGoo();

	/**
	 * Ez a függvény hívódik meg ha olajra ugrik.
	 */
	public void onOil();
	
	/**
	 * Ez a függvény hívódik meg robottal való ütközés esetén.
	 * @param r Evvel a robottal ütközik.
	 */
	public void onRobot(Robot r);

	/**
	 * Ez történik, ha kisrobottal ütközik.
	 * @param c A kisrobot referenciája.
	 */
	public void onCleaner(Cleaner c);
	
	/**
	 * Ez történik, ha OutsideField-re ugrik.
	 */
	public void outside();
	
	/**
	 * Az objektum megsemmisítése
	 */
	public void destroy();
	
	/**
	 * Pozíció lekérdezése.
	 * @return Az objektum pozíciójával tér vissza
	 */
	public Point getPosition();
	
}
