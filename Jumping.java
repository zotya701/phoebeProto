package phoebeProto;

import java.awt.Point;

/**
 * El��rja, hogy milyen met�dusokat kell megval�s�tani egy ugr�sra k�pes oszt�lynak.
 */
public interface Jumping {
	
	/**
	 * Ez t�rt�nik, ha normalField-re ugrik, f�ggetlen�l att�l,
	 * hogy ott van-e robot vagy csapda, vagy nincs.
	 * @param nf A NormalField amire ugrik.
	 */
	public void normalField(NormalField nf);

	/**
	 * Ez a f�ggv�ny h�v�dik meg ha ragacsra ugrik.
	 */
	public void onGoo();

	/**
	 * Ez a f�ggv�ny h�v�dik meg ha olajra ugrik.
	 */
	public void onOil();
	
	/**
	 * Ez a f�ggv�ny h�v�dik meg robottal val� �tk�z�s eset�n.
	 * @param r Evvel a robottal �tk�zik.
	 */
	public void onRobot(Robot r);

	/**
	 * Ez t�rt�nik, ha kisrobottal �tk�zik.
	 * @param c A kisrobot referenci�ja.
	 */
	public void onCleaner(Cleaner c);
	
	/**
	 * Ez t�rt�nik, ha OutsideField-re ugrik.
	 */
	public void outside();
	
	/**
	 * Az objektum megsemmis�t�se
	 */
	public void destroy();
	
	/**
	 * Poz�ci� lek�rdez�se.
	 * @return Az objektum poz�ci�j�val t�r vissza
	 */
	public Point getPosition();
	
}
