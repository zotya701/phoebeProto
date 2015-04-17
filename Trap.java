package phoebeProto;

import java.awt.Point;

/**
 * Egybefogja a játékban a Field-ekre lerakható csapdákat. 

 */
public interface Trap extends Landable{
	
	/**
	 *  Beállítja a currentField attribútumot.
	 * @param nf A NormalField amire a csapdát le akarjuk rakni.
	 */
	public void setNormalField(NormalField nf);
	
	/**
	 *  Feltakarítja a csapdát a mezõrõl.
	 */
	public void cleanup();
	
	/**
	 * Meghívja a paraméterben kapott objektum oilType vagy gooType metódusát, 
	 * és az értékét visszaadja. 
	 * @param l A Landable paraméter. 
	 * @return Megyezik-e vagy nem a Landable típusa a megfelelõ csapda típusával
	 */
	public boolean compareType(Landable l);
	
	/**
	 * Beállítja a position attribútumot
	 * @param pos A beállítandó pozíció
	 */
	public void setPosition(Point p);
	
	/**
	 * 
	 * @return Visszaadja a csapda pozícióját.
	 */
	public Point getPosition();

}
