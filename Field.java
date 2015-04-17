package phoebeProto;

/**
 * A Field biztos�tja a kommunik�ci�t a rajta l�v� objektumok �s az �ppen r�ugr� objektum k�z�tt.
 */
public interface Field extends Printable{
	
	/**
	 * A megval�s�t� oszt�lyok ezzel a f�ggv�nnyel kezelik le azt az esetet, 
	 * mikor r�ugrik egy Jumping interface-t megval�s�t� objektum a mez�re.
	 * @param j Az objektum aki r�ugrott
	 */
	public void arrived(Jumping j);
	
}
