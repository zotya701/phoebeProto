package phoebeProto;

/**
 * Az árkot megvalósító osztály, a dolga, hogy kiejtse a játékost, ha ráugrik a robot.

 */
public class OutsideField implements Field{
	
//publikus metódusok kezdete
	/**
	 *  Az objektum attribútumainak kiíratása a teszteléshez.
	 */
	public void Print() {
		System.out.print("#");
	}
	
	/**
	 * Meghívja a ráérkezõ Jumping objektum outside() metódusát.
	 * @param j A rá ugró objektum
	 */
	public void arrived(Jumping j) {
		j.outside();
	}
//publikus metódusok vége
}
