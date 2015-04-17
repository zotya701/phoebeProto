package phoebeProto;

/**
 * Az �rkot megval�s�t� oszt�ly, a dolga, hogy kiejtse a j�t�kost, ha r�ugrik a robot.

 */
public class OutsideField implements Field{
	
//publikus met�dusok kezdete
	/**
	 *  Az objektum attrib�tumainak ki�rat�sa a tesztel�shez.
	 */
	public void Print() {
		System.out.print("#");
	}
	
	/**
	 * Megh�vja a r��rkez� Jumping objektum outside() met�dus�t.
	 * @param j A r� ugr� objektum
	 */
	public void arrived(Jumping j) {
		j.outside();
	}
//publikus met�dusok v�ge
}
