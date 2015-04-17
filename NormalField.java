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
		Landable[] elements=this.elements.toArray(new Landable[this.elements.size()]);	//�gy nem fog gondot okozni, ha az elements elemein val� interakt�l�s eset�n t�rl�dne az elem
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
		boolean alreadyHaveTrap=false;													//felt�telezz�k, hogy nincs csapda az adott mez�n
		Landable[] elements=this.elements.toArray(new Landable[this.elements.size()]);	//ugyan az�rt van �gy, ami�rt az arrivedn�l is
		for(int i=0;i<elements.length;++i){
			if(elements[i].gooType() || elements[i].oilType()){							//ha az adott elem m�gis csapda
				alreadyHaveTrap=true;													//be�ll�tjuk, hogy valamilyen csapda m�r van a mez�n
				if(((Trap)(elements[i])).compareType(trap)){							//ha ez a csapda ugyan olyan t�pus�, mint a param�terben �tadott trap
					((Trap)elements[i]).cleanup();										//akkor a mez�n l�v�t elt�ntetj�k,
					this.elements.add(trap);											//�s hely�re rajuk az �jat, sz�val tualjdonk�ppen friss�tj�k az adott csapd�t,
																						//teh�t pl ha az olaj 1 k�r m�lva m�r felsz�radna de mi lerakunk r� egy olajfoltot,
																						//�jabb 10 k�r kell mire felsz�rad, hasonl�an ragacsfoltn�l is
					trap.setNormalField(this);											//az �j csapda mez�j�nek be�ll�t�sa
					return true;														//visszat�r�nk igazzal, mert a csapda lerak�sa sikeres volt
				}
			}
		}
		if(alreadyHaveTrap==false){			//ha m�g semmilyen csapda nincs a mez�n, lerakja r� azt
			this.elements.add(trap);
			trap.setNormalField(this);
			return true;					//itt is sikeres volt a csapda lerak�sa
		}
		else{								//csak akkor, ha a mez�n m�r volt egy m�sik t�pus� csapda, mint amit mi szerett�nk volna lerakni
			trap.cleanup();					//ilyenkor nem rakunk le csapd�t, viszont amit param�ter�l kaptunk, a list�kba m�r beker�lt, �gy onnan t�r�lni kell
			return false;					//hamissal t�r vissza, mert nem lett lerakva csapda
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
