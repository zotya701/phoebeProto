package phoebeProto;

import java.awt.Point;
import java.util.List;

/**
 *
 */
public interface Trap extends Landable{
	
	/**
	 * 
	 * @param j
	 */
	public void interact(Jumping j);
	
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
	
	/**
	 * 
	 * @return
	 */
	public List<Trap> trapList();
	
}
