package phoebeProto;

/**
 * A NormalField-en elõfordulható objektumok osztályainak ezt az interfészt kell megvalósítania
 */
public interface Landable extends Printable{
	
	/**
	 *  Jelzi az õt tartalmazó NormalField-re ugró 
	 *  Jumping interface-t implementáló objektumnak,
	 *  hogy mire ugrott azzal, hogy a Landable meghívja
	 *  a saját osztályának megfelelõ metódusát a Jumping-ban.
	 * @param j A Jumping objektum ami ráugrott-
	 */
	public void interact(Jumping j);
	
	/**
	 * Osztályok összehasonlításához szükséges függvény. 
	 * Alapvetõen hamis értéket ad vissza. 
	 * Csak akkor igaz ha az osztály a ragacsot valósítja meg
	 */
	public boolean gooType();
	
	/**
	 * Osztályok összehasonlításához szükséges függvény. 
	 * Alapvetõen hamis értéket ad vissza. 
	 * Csak akkor igaz ha az osztály az olajat valósítja meg
	 */
	public boolean oilType();
	
}
