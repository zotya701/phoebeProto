package phoebeProto;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class NormalField implements Field{
	
//privát adattagok kezdete
	/**
	 * 
	 */
	private List<Landable> elements;
//privát adattagok vége
	
//publikus metódusok kezdete
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
		for(int i=0;i<this.elements.size();++i)
			this.elements.get(i).interact(j);
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
		for(int i=0;i<this.elements.size();++i){
			if(this.elements.get(i).gooType() || this.elements.get(i).oilType()){
				alreadyHaveTrap=true;
				if(((Trap)(this.elements.get(i))).compareType(trap)){//ha ugyanolyan csapda már van a mezõn, akkor azt leszedi, és rárakja az újat (frissíti)
					((Trap)(this.elements.get(i))).cleanup();
					this.elements.add(0, trap);
					trap.setNormalField(this);
					return true;
				}
			}
		}
		if(alreadyHaveTrap==false){//ha még semmilyen csapda nincs a mezõn, lerakja rá azt
			this.elements.add(0, trap);
			trap.setNormalField(this);
			return true;
		}
		else{//ha egy másik típusú csapda van már a mezõn, akkor nem rakja le
			trap.cleanup();//viszont listából ki kell szedni
			return false;
		}
	}
	
	/**
	 * 
	 * @param l
	 */
	public void staying(Landable l){
		this.elements.add(l);
	}
//publikus metódusok vége
}
