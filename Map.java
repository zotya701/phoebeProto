package phoebeProto;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

/**
 * A j�t�k p�ly�j�nak t�rol�sa, robotok �j poz�ci�j�nak kisz�mol�sa 
 * a robot jelenlegi poz�ci�j�nak �s sebess�gvektor�nak f�ggv�ny�ben, 
 * robotok ugr�skor megtett t�vols�g�nak kisz�m�t�sa.
 */
public class Map implements Printable{
	
//priv�t adattagok kezdete
	/**
	 * Referenci�t t�rol a p�lya mez�ire.
	 */
	private List<List<Field>> fields;

	/**
	 * Csom�pontok az �tvonalkeres�shez.
	 */
	private List<List<Node>> nodes;

	/**
	 * A p�lya m�rete.
	 */
	private Point size;
	
	/**
	 *  Referencia a p�ly�n k�v�l es� mez�ket reprezent�l� objektumra.
	 */
	private OutsideField outside;
//priv�t adattagok v�ge

//publikus met�dusok kezdete
	/**
	 * Konstruktor. Bet�lti a p�ly�t a filename nev� f�jlb�l, 
	 * �s elhelyezi a mez�k�n a csapd�kat, ha vannak rajtuk.
	 * @param filename A f�jl neve amib�l bet�ltj�k a p�ly�t
	 */
	public Map(String filename){
		this.outside	=	new OutsideField();
		this.fields		=	new ArrayList<List<Field>>();
		this.nodes		=	new ArrayList<List<Node>>();
		GameManager.trapList.clear();
		GameManager.oilList.clear();
		
		try {
			File map=new File(filename);																	//a f�jl neve amib�l bet�ltj�k a p�ly�t
			if(map.exists()){																				//csak akkor kell csin�lni b�rmit, ha l�tezik a f�jl 
				BufferedReader br	=	new BufferedReader(new FileReader(map));
				if(br.ready()){																				//ha van valami amit be lehetne olvasni
					String[] size=br.readLine().split("\\s+");												//a f�jl form�tum miatt els� sor a m�ret
			    	if(size.length>=2){																		//legal�bb 2 sz�mnak kellene lennie a f�jl els� sor�ban
			    		this.size	=	new Point(Integer.parseInt(size[0]), Integer.parseInt(size[1]));	//p�lya m�ret�nek be�ll�t�sa size[0]-sz�less�g, size[1]-magass�g
			    		for(int i=0; i<this.size.y;++i){
			    			this.fields.add(i, new ArrayList<Field>());
			    			this.nodes.add(i, new ArrayList<Node>());
			    		}
			    	}
				}
				List<String> lines=new ArrayList<String>();						//ebben a p�ly�t tartalmaz� f�jl sorai lesznek, hogy meglehessen csin�lni a gr�fot
				for(int y=0;y<this.size.y && br.ready();++y){					//addig olvassa a f�jlt am�g el nem �rte a magass�g-adik sort, vagy a f�jlnak id� el�tt v�ge (b�r ekkor biztos lesznek gondok)
			    	String line	=	br.readLine();								//sor beolvas�sa
			    	lines.add(line);											//sor hozz�ad�sok a sorokhoz
			    	for(int x=0;x<this.size.x && x<line.length();++x){			//aktu�lis soron v�gighaladva l�trehozza a megfelel� dolgokat
			    		if(line.charAt(x)=='0'){								//ha 0, akkor ott egy �res mez� van
			    			this.fields.get(y).add(x, new NormalField());		//be is �ll�tja a megfelel� poz�ci�ra az �res mez�t
			    		}
			    		else if(line.charAt(x)=='#'){							//ha #, akkor �tt �rok van
			    			this.fields.get(y).add(x, this.outside);			//be is �ll�tja a megfelel� poz�ci�ra az �rkot
			    		}
			    		else if(line.charAt(x)=='1'){							//ha 1, akkor ott egy norm�l mez� van, amin van egy ragacsfolt
			    			NormalField nf=new NormalField();					//�res mez� l�trehoz�sa
			    			nf.addTrap(new Goo(new Point(x,y)));				//a mez�n elhejyezi a csapd�t
			    			this.fields.get(y).add(x, nf);						//be �ll�tja a megfelel� poz�ci�ra a mez�t
			    		}
			    		else if(line.charAt(x)=='2'){							//ha 2, akkor ott egy norm�l mez� van, amin van egy olajfolt
			    			NormalField nf=new NormalField();					//�res mez� l�trehoz�sa
			    			nf.addTrap(new Oil(new Point(x,y)));				//a mez�n elhejyezi a csapd�t
			    			this.fields.get(y).add(x, nf);						//be �ll�tja a megfelel� poz�ci�ra a mez�t
			    		}
			    		this.nodes.get(y).add(x, new Node(new Point(x, y)));	//gr�fban l�rehozza a megfelel� poz�ci�hoz tartoz� cs�csot a megfelel� poz�ci�n a megfelel� poz�ci�val
			    	}
				}
				this.createGraph(lines);										//gr�f cs�csai m�r megvannak, ez be�ll�tja az �leket is.
			    br.close();														//f�jl lez�r�sa
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Az objektum attrib�tumainak ki�rat�sa a tesztel�shez.
	 */
	public void Print(){
		System.out.println(this.size.x+" "+this.size.y);
		for(int i=0;i<this.size.y;++i){
			for(int j=0;j<this.size.x;++j){
				this.fields.get(i).get(j).Print();
			}
			System.out.println();
		}
	}
	
	/**
	 * Visszat�r a coord helyen tal�lhat� mez�vel.
	 * @param coord A mez� koordin�t�i amit le akarunk k�rdezni
	 * @return A megfelel� koordin�t�n l�v� mez�vel t�r vissza
	 */
	public Field getField(Point coord){
		if(coord.x<0 || coord.y<0 || coord.x>=this.size.x || coord.y>=this.size.y){		//kiv�telt dobna, ha p�ly�n k�v�li mez�t k�rn�nk le
			return this.outside;														//azonban p�ly�n k�v�l mindenhol �rok van, �gy ez megoldva
		}
		else return fields.get(coord.y).get(coord.x);
	}
	
	/**
	 * Kisz�molja az �j poz�ci�t currentPos �s vel szerint, majd visszat�r vele.
	 * @param currentPos A kezd�poz�ci�
	 * @param vel A sebess�gvektor
	 * @return A kezd�poz�ci� �s a sebess�gvektor alapj�n kisz�m�tott koordin�ta
	 */
	public Point getNewPos(Point currentPos, Point vel){
		currentPos.translate(vel.x, vel.y);		//�j p�z�ci�t megkapjuk akkor, ha a r�git eltoljuk a sebess�g vektorral
		return currentPos;
	}
	
	/**
	 * Visszat�r a p�lya m�ret�vel
	 * @return A p�lya m�rete
	 */
	public Point getSize(){
		return this.size;
	}
	
	/**
	 * Kisz�molja a t�vols�got a p�lya k�t pontja k�z�tt, majd visszat�r vele.
	 * @param s Az egyik pont
	 * @param d A m�sik pont
	 * @return A k�t pont k�z�tti t�vols�g
	 */
	public float calculateDistance(Point s, Point d){
		//szok�sos gy�kalatt(x^2+y^2) miut�n d vektorb�l kivontuk s vektort, azaz s->d vektor hossza (source, destination)
		float dist=(float)(Math.pow((d.x-s.x)*(d.x-s.x)+(d.y-s.y)*(d.y-s.y), 0.5));
		return dist;
	}
	
	/**
	 * Kisz�molja egy adott kisrobott�l a legr�videbb �tvonalat a legk�zelebbi csapd�hoz dijkstra algoritmus�t haszn�lva.
	 * @param c A kisrobot
	 * @return Az �tvonal els� pontja
	 */
	public Point getRouteToTrap(Cleaner c){
		this.computePaths(this.nodes.get(c.getPosition().y).get(c.getPosition().x));					//a gr�fban az adott takar�t�robot hely�hez k�pest kisz�molja a legr�vedd utakat az �sszes t�bbi koordin�t�ra
		int min			=	Integer.MAX_VALUE;															//legk�zelebbi csapda keres�s�hez
		Point minPoint	=	new Point(c.getPosition());													//legk�zelebbi csapda koordin�t�j�hoz
		for(Trap trap : GameManager.trapList){															//megkeress�k melyik csapd�hoz lehet a legr�videbb �ton elmenni
			if(min>this.nodes.get(trap.getPosition().y).get(trap.getPosition().x).getMinDistance()){	//ha tal�ltunk egy csapd�t ahova r�videbb �ton jutunk el mint az el�z� csapd�hoz,
				min=this.nodes.get(trap.getPosition().y).get(trap.getPosition().x).getMinDistance();	//min �rt�ke az sz�ks�ges �t hossza lesz
				minPoint=trap.getPosition();															//minPoint �rt�ke pedig a legk�zelebbi csapda poz�ci�ja
				c.setTarget(trap);																		//be�ll�tja a takar�t�robot targetj�t, azaz hogy melyik csapd�t szedje majd le, miut�n 2 k�rig takar�totta
			}
		}
		List<Node> shortestPath	=	this.getShortestPathTo(this.nodes.get(minPoint.y).get(minPoint.x));	//itt m�r megtal�ltuk a legk�zelebbi csapd�t, most visszak�rj�k a hozz� tartoz� legr�videbb utat
		if(shortestPath.size()>1)																		//ha legal�bb 1 ugr�s kell m�g a csapd�ig
			minPoint=shortestPath.get(1).getCoord();													//minPoint az �t k�vetkez� koordin�t�ja lesz
		else minPoint=shortestPath.get(0).getCoord();													//a 0. elem mindig az ahol a takar�t� robot �ll. ha erre �ll�tja be minPointot, az azt jelenti, hogy egy csapd�n �ll
		Point dir	=	new Point(minPoint.x-c.getPosition().x, minPoint.y-c.getPosition().y);
		return dir;
	}
	
	/**
	 * Az �tvonalkeres�shez a gr�f l�trhoz�sa.
	 */
	public void createGraph(List<String> lines){
		for(int y=0;y<this.size.y;++y){
			for(int x=0;x<this.size.x;++x){
				if(lines.get(y).charAt(x)!='_'){	//a norm�l mez�knek megfelel� cs�csokat nem k�ti �ssze az �rkokkal
					if(x>0)
						if(lines.get(y).charAt(x-1)!='#')
							this.nodes.get(y).get(x).addEdge(new Edge(this.nodes.get(y).get(x-1)));//balra
					if(y>0)
						if(lines.get(y-1).charAt(x)!='#')
							this.nodes.get(y).get(x).addEdge(new Edge(this.nodes.get(y-1).get(x)));//fel
					if(x<this.size.x-1)
						if(lines.get(y).charAt(x+1)!='#')
							this.nodes.get(y).get(x).addEdge(new Edge(this.nodes.get(y).get(x+1)));//jobbra
					if(y<this.size.y-1)
						if(lines.get(y+1).charAt(x)!='#')
							this.nodes.get(y).get(x).addEdge(new Edge(this.nodes.get(y+1).get(x)));//le
				}
			}
		}
	}
	
	/**
	 * Az �tvonal kisz�m�t�sa
	 * @param source A kiindul�si pont
	 */
	public void computePaths(Node source){
		for(int y=0;y<this.size.y;++y){			//alaphelyzetbe �ll�tja a gr�fot
			for(int x=0;x<this.size.x;++x){
				this.nodes.get(y).get(x).setPrevious(null);
				this.nodes.get(y).get(x).setMinDistance(Integer.MAX_VALUE);
			}
		}
		
        source.setMinDistance(0);										//a forr�snak legr�videbb �tnak �rtelemszer�en 0-t �ll�t be
        PriorityQueue<Node> NodeQueue = new PriorityQueue<Node>();		//a m�g nem megl�togatott cs�csok
      	NodeQueue.add(source);											//hisz m�g nem l�togattuk meg a forr�st

		while (!NodeQueue.isEmpty()) {									//am�g van meg nem l�togatott cs�cs
		    Node u = NodeQueue.poll();									//u a lista els� eleme lesz, �s egyben t�rl�dik is ez az elem a list�b�l
            for (Edge e : u.getAdjacencies()){							//u minden szomsz�dj�ra
            	boolean robotOnNode=false;
            	Node v = e.getTarget();
            	//for(int i=0;i<GameManager.cleaners.size();++i){			// e miatt a takar�t�robot el ker�li a t�bbi takar�t�robotot �s nem fog �tk�zni vel�k
            	//	if(GameManager.cleaners.get(i).getPosition().equals(v.getCoord()))
            	//		robotOnNode=true;
            	//}
            	//for(int i=0;i<GameManager.robots.size();++i){			// e miatt a rendes robotokat ker�li el	�s nem fog �tk�zni vel�k
            	//	if(GameManager.robots.get(i).getPosition().equals(v.getCoord()))
            	//		robotOnNode=true;
            	//}
            	if(robotOnNode==false){
                    int distanceThroughU = u.getMinDistance() + 1;
    				if (distanceThroughU < v.getMinDistance()) {			//ha tal�lt egy r�videbb utat s b�l u egyik szomsz�dj�ra
    				    NodeQueue.remove(v);								//a szomsz�dot kiveszi a list�b�l
    				    v.setMinDistance(distanceThroughU);					//be�ll�tja az �j t�vols�got
    				    v.setPrevious(u);									//�s hogy ide eljussunk melyik az el�z� cs�cs
    				    NodeQueue.add(v);									//majd visszarakja  a list�ba
    				}
            	}
            }
		}
    }
	
	/**
	 * A source �s a target k�z�tti legr�videbb �tvonal lek�rdez�se.
	 * @param target Az utols� Node 
	 * (minden Node tartalmazza a legr�videbb �ton bel�l az el�z� Node-t, �gy megy�nk v�gig a l�ncolt list�n)
	 * @return A legr�videbb �tvonal csom�pontjaib�l �ll� list�val t�r vissza.
	 */
    public List<Node> getShortestPathTo(Node target){
        List<Node> path = new ArrayList<Node>();
        for (Node node = target; node != null; node = node.getPrevious())	//fel�p�ti visszafel� az utat
            path.add(node);
        Collections.reverse(path);											//itt megford�tja
        return path;
    }
//publikus met�dusok v�ge
}
