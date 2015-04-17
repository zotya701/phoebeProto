package phoebeProto;

import java.awt.Point;

/**
 *
 */
public interface Jumping {
	
	/**
	 * 
	 * @param nf
	 */
	public void normalField(NormalField nf);

	/**
	 * 
	 */
	public void onGoo();

	/**
	 * 
	 */
	public void onOil();
	
	/**
	 * 
	 * @param r
	 */
	public void onRobot(Robot r);

	/**
	 * 
	 * @param c
	 */
	public void onCleaner(Cleaner c);
	
	/**
	 * 
	 */
	public void outside();
	
	/**
	 * 
	 */
	public void destroy();
	
	/**
	 * 
	 * @return
	 */
	public Point getPosition();
	
}
