package phoebeProto;

/**
 * 
 */
public class OutsideField implements Field{
	
//publikus met�dusok kezdete
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
//publikus met�dusok v�ge
}
