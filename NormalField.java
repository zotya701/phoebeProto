package phoebeProto;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class NormalField implements Field{
	
//priv�t adattagok kezdete
	/**
	 * 
	 */
	private List<Landable> elements;
//priv�t adattagok v�ge
	
//publikus met�dusok kezdete
	/**
	 * 
	 */
	public NormalField(){
		this.elements	=	new ArrayList<Landable>();
	}
	
	/**
	 * 
	 */
	public void Print() {
		System.out.print("0");
	}
	
	/**
	 * 
	 * @param j
	 */
	public void arrived(Jumping j) {
		Landable[] elements=this.elements.toArray(new Landable[this.elements.size()]);
		for(int i=0;i<elements.length;++i)
			elements[i].interact(j);
		j.normalField(this);
	}
	
	/**
	 * 
	 * @param l
	 */
	public void left(Landable l){
		this.elements.remove(l);
	}
	
	/**
	 * 
	 * @param trap
	 */
	public boolean addTrap(Trap trap){
		boolean alreadyHaveTrap=false;
		Landable[] elements=this.elements.toArray(new Landable[this.elements.size()]);
		for(int i=0;i<elements.length;++i){
			if(elements[i].gooType() || elements[i].oilType()){
				alreadyHaveTrap=true;
				if(((Trap)(elements[i])).compareType(trap)){
					((Trap)elements[i]).cleanup();
					this.elements.add(trap);
					trap.setNormalField(this);
					return true;
				}
			}
		}
		if(alreadyHaveTrap==false){//ha m�g semmilyen csapda nincs a mez�n, lerakja r� azt
			this.elements.add(trap);
			trap.setNormalField(this);
			return true;
		}
		else{//ha egy m�sik t�pus� csapda van m�r a mez�n, akkor nem rakja le
			trap.cleanup();//viszont list�b�l ki kell szedni
			return false;
		}
	}
	
	/**
	 * 
	 * @param l
	 */
	public void staying(Landable l){
		this.elements.add(0, l);
	}
//publikus met�dusok v�ge
}
