package phoebeProto;

/**
 * 
 */
public class OutsideField implements Field{
	
//publikus metódusok kezdete
	/**
	 * 
	 */
	public void Print() {
		System.out.print("#");
	}
	
	/**
	 * 
	 * @param j
	 */
	public void arrived(Jumping j) {
		j.outside();
	}
//publikus metódusok vége
}
