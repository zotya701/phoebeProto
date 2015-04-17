package phoebeProto;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * A programot irányító osztály, felelõsségei:
 * Pálya/robotok inicializálása, játék indítása,
 * játékosok/robotok léptetése, játék vége, 
 * kommunikáció a felhasználói felülettel.
 */
public class GameManager {
	
//privát adattagok kezdete
	/**
	 * Referencia a pályára
	 */
	private Map map;

	/**
	 *  Számon tartja épp melyik játékos van a soron.
	 */
	private int currentPlayer;

	/**
	 * Számon tartja épp melyik körben vannak a játékosok.
	 */
	private int round;
//privát adattagok vége

//statikus adattagok kezdete
	/**
	 * Lista a pályán lévõ olajokról
	 */
	public static List<Oil> oilList			=	new ArrayList<Oil>();

	/**
	 * Lista a pályán lévõ csapdákról
	 */
	public static List<Trap> trapList		=	new ArrayList<Trap>();
	
	/**
	 * Lista a pályán lévõ robotokról
	 */
	public static List<Robot> robots		=	new ArrayList<Robot>(4);
	
	/**
	 * Lista a pályán lévõ kisrobotokról
	 */
	public static List<Cleaner> cleaners	=	new ArrayList<Cleaner>();
//statikus adattagok vége
	
//publikus metódusok kezdete
	/**
	 * Konstruktor. Inicializálja az attribútumokat.
	 */
	public GameManager(){
		this.currentPlayer		=	0;
		this.round				=	0;
		this.map				=	new Map("test.map");
	}

	/**
	 * Betölti a pályát egy fájlból, és létrehozza a robotokat.
	 * @param filename A fájl neve amiben a pálya van.
	 */
	public void LoadMap(String filename){
		try {
			if(new File(filename+".map").exists()){											//akkor töltse be az adatokat ha van honnan
				this.map		=	new Map(filename+".map");								//létrehoz egy pályát a filename nevû fájlból
				File robotsFile	=	new File(filename+".robots");
				if(robotsFile.exists()){													//ha létezik a robotok adatait tartalmazó fájl
					Robot.statid=0;															//új betöltésnél megint nulláról induljon
					GameManager.robots.clear();												//ez sem árt, ha kiürítjük más robotok betöltése elõtt
					this.currentPlayer=0;
					this.round=0;
					GameManager.cleaners.clear();											//szintén
					BufferedReader BR	=	new BufferedReader(new FileReader(robotsFile));
				    while(BR.ready()){														//amíg van adat a robotok adatait tartalmazó fájlban
				    	String[] p=BR.readLine().split("\\s+");								//feldarabolja a beolvasott stringet space-enként
				    	if(p.length>=4){													//robotok konstruktorában mapon kívül még kell 2 pont, ezért a legalább 4
				    		GameManager.robots.add(new Robot(this.map, new Point(Integer.parseInt(p[0]), Integer.parseInt(p[1])), new Point(Integer.parseInt(p[2]), Integer.parseInt(p[3]))));
				    	}
				    }
				    BR.close();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Elindítja a játékot.
	 */
	public void start(){
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while(true){												//végtelen ciklusban olvassa a felhasználó inputjait, ha exitet ad, kilép az egész programból
			String command="";
			String[] splittedCommand;								//néhány parancs több paraméterbõl áll, ilyenkor feladaraboljuk és ebbe a változóba tesszük õket
			try {
				command=br.readLine();
	//loadMap
				if(command.contains("loadMap")){					//ha tartalmazza a parancs a loadMap stringet
					splittedCommand=command.split("\\s+");			//feldarabolja a stringet space-enként
					if(splittedCommand.length>=2){					//ha legaláb 2 rész string maradt
						String filename	=	splittedCommand[1];		//a paraméterül kapott fájlnév
						if(new File(filename).exists()){			//akkor töltse be az adatokat ha van honnan
							GameManager.cleaners.clear();			//üríti a takarítórobotok listáját
							this.map	=	new Map(filename);		//betölt egy pályát a loadMap paraméteréül kapott fájlból
						}
					}
				}
	//loadRobots
				else if(command.contains("loadRobots")){
					splittedCommand=command.split("\\s+");
					if(splittedCommand.length>=2){
						File robotsFile=new File(splittedCommand[1]);
						if(robotsFile.exists()){													//ha létezik a robotok adatait tartalmazó fájl
							Robot.statid=0;															//nem árt nullázni
							GameManager.robots.clear();												//ezt sem árt üríteni
							BufferedReader BR = new BufferedReader(new FileReader(robotsFile));
						    while(BR.ready()){														//amíg van adat a robotok adatait tartalmazó fájlban
						    	String[] p=BR.readLine().split("\\s+");								//feldarabolja a beolvasott stringet space-enként
						    	if(p.length>=4){													//legalább 4 kell
						    		int x1	=	Integer.parseInt(p[0]);
						    		int y1	=	Integer.parseInt(p[1]);
						    		int x2	=	Integer.parseInt(p[2]);
						    		int y2	=	Integer.parseInt(p[3]);
						    		GameManager.robots.add(new Robot(this.map, new Point(x1, y1), new Point(x2, y2)));
						    	}
						    }
						    BR.close();
						}
					}
				}
	//step
				else if(command.contains("step")){
					splittedCommand=command.split("\\s+");
					if(splittedCommand.length>=2){
						int i=Integer.parseInt(splittedCommand[1]);			//hány kör teljen el
						for(int a=0;a<i;++a){
							Cleaner[] cleanersArr=GameManager.cleaners.toArray(new Cleaner[GameManager.cleaners.size()]);
							for(int j=0;j<cleanersArr.length;++j){			//lépteti a takarítórobotokat
								cleanersArr[j].move();
							}
							Oil[] oils=GameManager.oilList.toArray(new Oil[GameManager.oilList.size()]);
							for(int j=0;j<oils.length;++j){					//mindegyik olajfolt "életét" csökkenti egyel
								oils[j].roundElapsed();
							}
							int robotsAlive=0;
							for(Robot r : GameManager.robots){				//megszámolja hány robot "él" még
								if(r.isAlive()){
									robotsAlive=robotsAlive+1;
								}
							}
							round=round+1;
							if((round>=20 || robotsAlive<=1) && GameManager.robots.size()>=2 ){		//ha csak 1 robot él, vagy vége a játéknak (eltelt 20 kör)
								this.end();
							}
						}
					}
				}
	//jump
				else if(command.contains("jump")){
					splittedCommand=command.split("\\s+");
					if(splittedCommand.length>=4){
						int id		=	Integer.parseInt(splittedCommand[1]);
						int x		=	Integer.parseInt(splittedCommand[2]);
						int y		=	Integer.parseInt(splittedCommand[3]);
						if(id>=0 && id<GameManager.robots.size())
							GameManager.robots.get(id).jump(new Point(x,y));		//id.-edik robotot ugratja, az (x,y) módosító sebesség vektorral
					}
				}
	//placeTrap
				else if(command.contains("placeTrap")){
					splittedCommand=command.split("\\s+");
					if(splittedCommand.length>=3){
						int id		=	Integer.parseInt(splittedCommand[1]);
						int type	=	Integer.parseInt(splittedCommand[2]);
						if(id>=0 && id<GameManager.robots.size()){					//id.edik robot lerak egy csapdát, a type-nak megfelelõen 1-ragacs, 2-olaj
							if(type==1){
								GameManager.robots.get(id).placeGoo();
							}
							else if(type==2){
								GameManager.robots.get(id).placeOil();
							}
						}
					}
				}
	//addCleaner
				else if(command.contains("addCleaner")){
					splittedCommand=command.split("\\s+");
					if(splittedCommand.length>=3){
						int x	=	Integer.parseInt(splittedCommand[1]);
						int y	=	Integer.parseInt(splittedCommand[2]);
						GameManager.cleaners.add(new Cleaner(this.map, new Point(x, y)));		//hozzáad egy takarítórobotot az (x,y) koordinátájú helyre
					}
				}
	//exit
				else if(command.equals("exit")){
					br.close();
					System.exit(0);		//kilép :D
				}
	//showMap
				else if(command.equals("showMap")){
					this.map.Print();	//konzolon megjeleníti a mapot, de csak a mezõk típusát 0-normál mezõ, #-árok
				}
	//listTraps
				else if(command.equals("listTraps")){
					for(Trap trap : GameManager.trapList)	//konzolra kilistázza a csapdákat
						trap.Print();
				}
	//listRobots
				else if(command.equals("listRobots")){
					for(Robot robot : GameManager.robots)	//konzolra kilistázza a robotokat
						robot.Print();
				}
	//listCleaners
				else if(command.equals("listCleaners")){
					for(Cleaner cleaner : cleaners)			//konzolra kilistázza a takarítórobotokat
						cleaner.Print();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Minden hívásra a következõ játékos robotja ugrik.
	 */
	public void step(){
		int x,y;
		x=y=0;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Kérem a(z) "+currentPlayer+". robot módosító sebességvektorának koordinátáit");
		try {
			System.out.println("X = ");
			x=Integer.parseInt(br.readLine());
			System.out.println("Y = ");
			y=Integer.parseInt(br.readLine());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		GameManager.robots.get(currentPlayer).jump(new Point(x,y));
		currentPlayer	=	currentPlayer+1;
		if(currentPlayer==GameManager.robots.size()){
			Cleaner[] cleanersArr=GameManager.cleaners.toArray(new Cleaner[GameManager.cleaners.size()]);
			for(int j=0;j<cleanersArr.length;++j){
				cleanersArr[j].move();
			}
			Oil[] oils=GameManager.oilList.toArray(new Oil[GameManager.oilList.size()]);
			for(int j=0;j<oils.length;++j){
				oils[j].roundElapsed();
			}
			int robotsAlive=0;
			for(Robot r : GameManager.robots){
				if(r.isAlive()){
					robotsAlive=robotsAlive+1;
				}
			}
			if(round>=20 || robotsAlive<=1){
				this.end();
			}
			
			currentPlayer	=	0;
			round			=	round+1;
		}
	}

	/**
	 * Véget vet a futó játéknak.
	 */
	public void end(){
		this.showResults();
	}

	/**
	 * Megjeleníti a robotok megtett távolságait.
	 */
	public void showResults(){
		float max=0;
		int maxIndex=0;
		for(int i=0;i<GameManager.robots.size();++i){
			if(GameManager.robots.get(i).isAlive() && max<GameManager.robots.get(i).getRouteTravelled()){	//az "élõ" robotok közül megkeresi melyik robot tette meg a legtöbb távot
				max=GameManager.robots.get(i).getRouteTravelled();
				maxIndex=i;
			}
		}
		System.out.println("Robot id:"+maxIndex+" nyert!");													//merthogy az a robot nyerte meg a játékot
	}
	
	public static void main(String[] args){
		GameManager gm=new GameManager();
		gm.start();
	}
//publikus metódusok vége
}
