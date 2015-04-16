package phoebeProto;

import java.awt.Point;

/**
 *
 */
public interface Trap extends Landable{
	
	/**
	 * 
	 * @param nf
	 */
	public void setNormalField(NormalField nf);
	
	/**
	 * 
	 */
	public void cleanup();
	
	/**
	 * 
	 * @param l
	 * @return
	 */
	public boolean compareType(Landable l);
	
	/**
	 * 
	 * @param p
	 */
	public void setPosition(Point p);
	
	/**
	 * 
	 * @return
	 */
	public Point getPosition();
	
}
