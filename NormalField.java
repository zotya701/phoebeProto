package phoebeProto;

import java.util.ArrayList;
import java.util.List;

/**
 * Ezek azok a Field-ek, amikre ugorva a robot játékban marad. 
 * Felelõs azért, hogy tudja milyen objektumok vannak a mezõn rajta.
 */
public class NormalField implements Field{
	
//privát adattagok kezdete
	/**
	 *  Landable interfészt megvalósító objektumokat tartalmaz, 
	 *  amikkel a robotok a mezõre ugráskor interakcióba lépnek.
	 */
	private List<Landable> elements;
//privát adattagok vége
	
//publikus metódusok kezdete
	/**
	 * Konstruktor, létrehozza az elements tömböt.
	 */
	public NormalField(){
		this.elements	=	new ArrayList<Landable>();
	}
	
	/**
	 *  Az objektum attribútumainak kiíratása a teszteléshez.
	 */
	public void Print() {
		System.out.print("0");
	}
	
	/**
	 * Meghívja az összes rajta lévõ objektumnak az interact függvényét, 
	 * majd a kapott objektum normalField függvényét, saját referenciáját átadva.
	 * @param j A mezõre érkezõ objektum
	 */
	public void arrived(Jumping j) {
		Landable[] elements=this.elements.toArray(new Landable[this.elements.size()]);	//így nem fog gondot okozni, ha az elements elemein való interaktálás esetén törlõdne az elem
		for(int i=0;i<elements.length;++i)
			elements[i].interact(j);
		j.normalField(this);
	}
	
	/**
	 * Törli a kapott Jumping interfészt megvalósító objektumot az elementsbõl.
	 * @param l A Landable amit törölnie kell
	 */
	public void left(Landable l){
		this.elements.remove(l);
	}
	
	/**
	 * Csapdát helyez el a mezõn (ragacsfoltot vagy olajfoltot), 
	 * vagyis hozzáadja a trap objektumot az elements végére
	 * @param trap A csapda amit bele kell raknia az elements-be
	 */
	public boolean addTrap(Trap trap){
		boolean alreadyHaveTrap=false;													//feltételezzük, hogy nincs csapda az adott mezõn
		Landable[] elements=this.elements.toArray(new Landable[this.elements.size()]);	//ugyan azért van így, amiért az arrivednél is
		for(int i=0;i<elements.length;++i){
			if(elements[i].gooType() || elements[i].oilType()){							//ha az adott elem mégis csapda
				alreadyHaveTrap=true;													//beállítjuk, hogy valamilyen csapda már van a mezõn
				if(((Trap)(elements[i])).compareType(trap)){							//ha ez a csapda ugyan olyan típusú, mint a paraméterben átadott trap
					((Trap)elements[i]).cleanup();										//akkor a mezõn lévõt eltüntetjük,
					this.elements.add(trap);											//és helyére rajuk az újat, szóval tualjdonképpen frissítjük az adott csapdát,
																						//tehát pl ha az olaj 1 kör múlva már felszáradna de mi lerakunk rá egy olajfoltot,
																						//újabb 10 kör kell mire felszárad, hasonlóan ragacsfoltnál is
					trap.setNormalField(this);											//az új csapda mezõjének beállítása
					return true;														//visszatérünk igazzal, mert a csapda lerakása sikeres volt
				}
			}
		}
		if(alreadyHaveTrap==false){			//ha még semmilyen csapda nincs a mezõn, lerakja rá azt
			this.elements.add(trap);
			trap.setNormalField(this);
			return true;					//itt is sikeres volt a csapda lerakása
		}
		else{								//csak akkor, ha a mezõn már volt egy másik típusú csapda, mint amit mi szerettünk volna lerakni
			trap.cleanup();					//ilyenkor nem rakunk le csapdát, viszont amit paraméterül kaptunk, a listákba már bekerült, így onnan törölni kell
			return false;					//hamissal tér vissza, mert nem lett lerakva csapda
		}
	}
	
	/**
	 * Landable objektum hozzáadása az elements elejére.
	 * @param l Ezt adjuk hozzá
	 */
	public void staying(Landable l){
		this.elements.add(0, l);
	}
//publikus metódusok vége
}
