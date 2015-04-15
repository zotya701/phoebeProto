package phoebeProto;

import java.util.ArrayList;
import java.util.List;

public class NormalField implements Field{
	
//privát adattagok kezdete
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
	public void addTrap(Trap trap){
		for(int i=0;i<this.elements.size();++i){
			if(this.elements.get(i).gooType() || this.elements.get(i).oilType()){
				if(((Trap)(this.elements.get(i))).compareType(trap)){
					((Trap)(this.elements.get(i))).cleanup();
				}
			}
		}
		this.elements.add(trap);
		trap.setNormalField(this);
	}
	
	/**
	 * 
	 * @param l
	 */
	public void staying(Landable l){
		
	}
//publikus metódusok vége
}
