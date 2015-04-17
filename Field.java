package phoebeProto;

/**
 * A Field biztosítja a kommunikációt a rajta lévõ objektumok és az éppen ráugró objektum között.
 */
public interface Field extends Printable{
	
	/**
	 * A megvalósító osztályok ezzel a függvénnyel kezelik le azt az esetet, 
	 * mikor ráugrik egy Jumping interface-t megvalósító objektum a mezõre.
	 * @param j Az objektum aki ráugrott
	 */
	public void arrived(Jumping j);
	
}
