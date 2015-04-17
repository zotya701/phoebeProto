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
 * 
 */
public class Map implements Printable{
	
//privát adattagok kezdete
	/**
	 * 
	 */
	private List<List<Field>> fields;

	/**
	 * 
	 */
	private List<List<Node>> nodes;

	/**
	 * 
	 */
	private Point size;
	
	/**
	 * 
	 */
	private OutsideField outside;
//privát adattagok vége

//publikus metódusok kezdete
	/**
	 * 
	 * @param filename
	 * @param trapList
	 */
	public Map(String filename){
		this.outside	=	new OutsideField();
		this.fields		=	new ArrayList<List<Field>>();
		this.nodes		=	new ArrayList<List<Node>>();
		GameManager.trapList.clear();
		GameManager.oilList.clear();
		
		try {
			File map=new File(filename);																	//a fájl neve amibõl betöltjük a pályát
			if(map.exists()){																				//csak akkor kell csinálni bármit, ha létezik a fájl 
				BufferedReader br	=	new BufferedReader(new FileReader(map));
				if(br.ready()){																				//ha van valami amit be lehetne olvasni
					String[] size=br.readLine().split("\\s+");												//a fájl formátum miatt elsõ sor a méret
			    	if(size.length>=2){																		//legalább 2 számnak kellene lennie a fájl elsõ sorában
			    		this.size	=	new Point(Integer.parseInt(size[0]), Integer.parseInt(size[1]));	//pálya méretének beállítása size[0]-szélesség, size[1]-magasság
			    		for(int i=0; i<this.size.y;++i){
			    			this.fields.add(i, new ArrayList<Field>());
			    			this.nodes.add(i, new ArrayList<Node>());
			    		}
			    	}
				}
				List<String> lines=new ArrayList<String>();						//ebben a pályát tartalmazó fájl sorai lesznek, hogy meglehessen csinálni a gráfot
				for(int y=0;y<this.size.y && br.ready();++y){					//addig olvassa a fájlt amíg el nem érte a magasság-adik sort, vagy a fájlnak idõ elõtt vége (bár ekkor biztos lesznek gondok)
			    	String line	=	br.readLine();								//sor beolvasása
			    	lines.add(line);											//sor hozzáadások a sorokhoz
			    	for(int x=0;x<this.size.x && x<line.length();++x){			//aktuális soron végighaladva létrehozza a megfelelõ dolgokat
			    		if(line.charAt(x)=='0'){								//ha 0, akkor ott egy üres mezõ van
			    			this.fields.get(y).add(x, new NormalField());		//be is állítja a megfelelõ pozícióra az üres mezõt
			    		}
			    		else if(line.charAt(x)=='#'){							//ha #, akkor ütt árok van
			    			this.fields.get(y).add(x, this.outside);			//be is állítja a megfelelõ pozícióra az árkot
			    		}
			    		else if(line.charAt(x)=='1'){							//ha 1, akkor ott egy normál mezõ van, amin van egy ragacsfolt
			    			NormalField nf=new NormalField();					//üres mezõ létrehozása
			    			nf.addTrap(new Goo(new Point(x,y)));				//a mezõn elhejyezi a csapdát
			    			this.fields.get(y).add(x, nf);						//be állítja a megfelelõ pozícióra a mezõt
			    		}
			    		else if(line.charAt(x)=='2'){							//ha 2, akkor ott egy normál mezõ van, amin van egy olajfolt
			    			NormalField nf=new NormalField();					//üres mezõ létrehozása
			    			nf.addTrap(new Oil(new Point(x,y)));				//a mezõn elhejyezi a csapdát
			    			this.fields.get(y).add(x, nf);						//be állítja a megfelelõ pozícióra a mezõt
			    		}
			    		this.nodes.get(y).add(x, new Node(new Point(x, y)));	//gráfban lérehozza a megfelelõ pozícióhoz tartozó csúcsot a megfelelõ pozíción a megfelelõ pozícióval
			    	}
				}
				this.createGraph(lines);										//gráf csúcsai már megvannak, ez beállítja az éleket is.
			    br.close();														//fájl lezárása
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
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
	 * 
	 * @param coord
	 * @return
	 */
	public Field getField(Point coord){
		if(coord.x<0 || coord.y<0 || coord.x>=this.size.x || coord.y>=this.size.y){		//kivételt dobna, ha pályán kívüli mezõt kérnénk le
			return this.outside;														//azonban pályán kívül mindenhol árok van, így ez megoldva
		}
		else return fields.get(coord.y).get(coord.x);
	}
	
	/**
	 * 
	 * @param currentPos
	 * @param vel
	 * @return
	 */
	public Point getNewPos(Point currentPos, Point vel){
		currentPos.translate(vel.x, vel.y);		//új pózíciót megkapjuk akkor, ha a régit eltoljuk a sebesség vektorral
		return currentPos;
	}
	
	/**
	 * 
	 * @param s
	 * @param d
	 * @return
	 */
	public float calculateDistance(Point s, Point d){
		//szokásos gyökalatt(x^2+y^2) miután d vektorból kivontuk s vektort, azaz s->d vektor hossza (source, destination)
		float dist=(float)(Math.pow((d.x-s.x)*(d.x-s.x)+(d.y-s.y)*(d.y-s.y), 0.5));
		return dist;
	}
	
	/**
	 * 
	 * @param source
	 * @return
	 */
	public Point getRouteToTrap(Point source, Cleaner c){
		this.computePaths(this.nodes.get(source.y).get(source.x));										//a gráfban az adott takarítórobot helyéhez képest kiszámolja a legrövedd utakat az összes többi koordinátára
		int min			=	Integer.MAX_VALUE;															//legközelebbi csapda kereséséhez
		Point minPoint	=	new Point(source);															//legközelebbi csapda koordinátájához
		for(Trap trap : GameManager.trapList){															//megkeressük melyik csapdához lehet a legrövidebb úton elmenni
			if(min>this.nodes.get(trap.getPosition().y).get(trap.getPosition().x).getMinDistance()){	//ha találtunk egy csapdát ahova rövidebb úton jutunk el mint az elõzõ csapdához,
				min=this.nodes.get(trap.getPosition().y).get(trap.getPosition().x).getMinDistance();	//min értéke az szükséges út hossza lesz
				minPoint=trap.getPosition();															//minPoint értéke pedig a legközelebbi csapda pozíciója
				c.setTarget(trap);																		//beállítja a takarítórobot targetjét, azaz hogy melyik csapdát szedje majd le, miután 2 körig takarította
			}
		}
		List<Node> shortestPath	=	this.getShortestPathTo(this.nodes.get(minPoint.y).get(minPoint.x));	//itt már megtaláltuk a legközelebbi csapdát, most visszakérjük a hozzá tartozó legrövidebb utat
		if(shortestPath.size()>1)							//ha legalább 1 ugrás kell még a csapdáig
			minPoint=shortestPath.get(1).getCoord();		//minPoint az út következõ koordinátája lesz
		else minPoint=shortestPath.get(0).getCoord();		//a 0. elem mindig az ahol a takarító robot áll. ha erre állítja be minPointot, az azt jelenti, hogy egy csapdán áll
		return minPoint;
	}
	
	/**
	 * 
	 */
	public void createGraph(List<String> lines){
		for(int y=0;y<this.size.y;++y){
			for(int x=0;x<this.size.x;++x){
				if(lines.get(y).charAt(x)!='_'){	//a normál mezõknek megfelelõ csúcsokat nem köti össze az árkokkal
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
	 * 
	 * @param source
	 */
	public void computePaths(Node source){
		for(int y=0;y<this.size.y;++y){			//alaphelyzetbe állítja a gráfot
			for(int x=0;x<this.size.x;++x){
				this.nodes.get(y).get(x).setPrevious(null);
				this.nodes.get(y).get(x).setMinDistance(Integer.MAX_VALUE);
			}
		}
		
        source.setMinDistance(0);										//a forrásnak legrövidebb útnak értelemszerûen 0-t állít be
        PriorityQueue<Node> NodeQueue = new PriorityQueue<Node>();		//a még nem meglátogatott csúcsok
      	NodeQueue.add(source);											//hisz még nem látogattuk meg a forrást

		while (!NodeQueue.isEmpty()) {									//amíg van meg nem látogatott csúcs
		    Node u = NodeQueue.poll();									//u a lista elsõ eleme lesz, és egyben törlõdik is ez az elem a listábõl
            for (Edge e : u.getAdjacencies()){							//u minden szomszédjára						
                Node v = e.getTarget();
                int distanceThroughU = u.getMinDistance() + 1;
				if (distanceThroughU < v.getMinDistance()) {			//ha talált egy rövidebb utat s bõl u egyik szomszédjára
				    NodeQueue.remove(v);								//a szomszédot kiveszi a listából
				    v.setMinDistance(distanceThroughU);					//beállítja az új távolságot
				    v.setPrevious(u);									//és hogy ide eljussunk melyik az elõzõ csúcs
				    NodeQueue.add(v);									//majd visszarakja  a listába
				}
            }
		}
    }
	
	/**
	 * 
	 * @param target
	 * @return
	 */
    public List<Node> getShortestPathTo(Node target){
        List<Node> path = new ArrayList<Node>();
        for (Node node = target; node != null; node = node.getPrevious())	//felépíti visszafelé az utat
            path.add(node);
        Collections.reverse(path);											//itt megfordítja
        return path;
    }
//publikus metódusok vége
}
