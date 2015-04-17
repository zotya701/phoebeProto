package phoebeProto;

/**
 * A NormalField-en el�fordulhat� objektumok oszt�lyainak ezt az interf�szt kell megval�s�tania
 */
public interface Landable extends Printable{
	
	/**
	 *  Jelzi az �t tartalmaz� NormalField-re ugr� 
	 *  Jumping interface-t implement�l� objektumnak,
	 *  hogy mire ugrott azzal, hogy a Landable megh�vja
	 *  a saj�t oszt�ly�nak megfelel� met�dus�t a Jumping-ban.
	 * @param j A Jumping objektum ami r�ugrott-
	 */
	public void interact(Jumping j);
	
	/**
	 * Oszt�lyok �sszehasonl�t�s�hoz sz�ks�ges f�ggv�ny. 
	 * Alapvet�en hamis �rt�ket ad vissza. 
	 * Csak akkor igaz ha az oszt�ly a ragacsot val�s�tja meg
	 */
	public boolean gooType();
	
	/**
	 * Oszt�lyok �sszehasonl�t�s�hoz sz�ks�ges f�ggv�ny. 
	 * Alapvet�en hamis �rt�ket ad vissza. 
	 * Csak akkor igaz ha az oszt�ly az olajat val�s�tja meg
	 */
	public boolean oilType();
	
}
