package phoebeProto;

import java.awt.Point;

/**
 * Egybefogja a j�t�kban a Field-ekre lerakhat� csapd�kat. 

 */
public interface Trap extends Landable{
	
	/**
	 *  Be�ll�tja a currentField attrib�tumot.
	 * @param nf A NormalField amire a csapd�t le akarjuk rakni.
	 */
	public void setNormalField(NormalField nf);
	
	/**
	 *  Feltakar�tja a csapd�t a mez�r�l.
	 */
	public void cleanup();
	
	/**
	 * Megh�vja a param�terben kapott objektum oilType vagy gooType met�dus�t, 
	 * �s az �rt�k�t visszaadja. 
	 * @param l A Landable param�ter. 
	 * @return Megyezik-e vagy nem a Landable t�pusa a megfelel� csapda t�pus�val
	 */
	public boolean compareType(Landable l);
	
	/**
	 * Be�ll�tja a position attrib�tumot
	 * @param pos A be�ll�tand� poz�ci�
	 */
	public void setPosition(Point p);
	
	/**
	 * 
	 * @return Visszaadja a csapda poz�ci�j�t.
	 */
	public Point getPosition();

}
