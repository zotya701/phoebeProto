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
 * 
 */
public class GameManager {
	
//priv�t adattagok kezdete

	/**
	 * 
	 */
	private List<Robot> robots;

	/**
	 * 
	 */
	private Map map;

	/**
	 * 
	 */
	private int currentPlayer;

	/**
	 * 
	 */
	private int round;
//priv�t adattagok v�ge

//statikus adattagok kezdete
	/**
	 * 
	 */
	public static List<Oil> oilList			=	new ArrayList<Oil>();

	/**
	 * 
	 */
	public static List<Trap> trapList		=	new ArrayList<Trap>();
	
	/**
	 * 
	 */
	public static List<Cleaner> cleaners	=	new ArrayList<Cleaner>();
//statikus adattagok v�ge
	
//publikus met�dusok kezdete
	/**
	 * 
	 */
	public GameManager(){
		this.currentPlayer		=	0;
		this.round				=	0;
		this.robots				=	new ArrayList<Robot>(4);
		this.map				=	new Map("test.map");
	}

	/**
	 * 
	 * @param filename
	 */
	public void LoadMap(String filename){
		try {
			if(new File(filename+".map").exists()){											//akkor t�ltse be az adatokat ha van honnan
				this.map		=	new Map(filename+".map");								//l�trehoz egy p�ly�t a filename nev� f�jlb�l
				File robotsFile	=	new File(filename+".robots");
				if(robotsFile.exists()){													//ha l�tezik a robotok adatait tartalmaz� f�jl
					Robot.statid=0;															//�j bet�lt�sn�l megint null�r�l induljon
					this.robots.clear();													//ez sem �rt, ha ki�r�tj�k m�s robotok bet�lt�se el�tt
					this.currentPlayer=0;
					this.round=0;
					GameManager.cleaners.clear();											//szint�n
					BufferedReader BR	=	new BufferedReader(new FileReader(robotsFile));
				    while(BR.ready()){														//am�g van adat a robotok adatait tartalmaz� f�jlban
				    	String[] p=BR.readLine().split("\\s+");								//feldarabolja a beolvasott stringet space-enk�nt
				    	if(p.length>=4){													//robotok konstruktor�ban mapon k�v�l m�g kell 2 pont, ez�rt a legal�bb 4
				    		this.robots.add(new Robot(this.map, new Point(Integer.parseInt(p[0]), Integer.parseInt(p[1])), new Point(Integer.parseInt(p[2]), Integer.parseInt(p[3]))));
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
	 * 
	 */
	public void start(){
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while(true){												//v�gtelen ciklusban olvassa a felhaszn�l� inputjait, ha exitet ad, kil�p az eg�sz programb�l
			String command="";
			String[] splittedCommand;								//n�h�ny parancs t�bb param�terb�l �ll, ilyenkor feladaraboljuk �s ebbe a v�ltoz�ba tessz�k �ket
			try {
				command=br.readLine();
	//loadMap
				if(command.contains("loadMap")){					//ha tartalmazza a parancs a loadMap stringet
					splittedCommand=command.split("\\s+");			//feldarabolja a stringet space-enk�nt
					if(splittedCommand.length>=2){					//ha legal�b 2 r�sz string maradt
						String filename	=	splittedCommand[1];		//a param�ter�l kapott f�jln�v
						if(new File(filename).exists()){			//akkor t�ltse be az adatokat ha van honnan
							GameManager.cleaners.clear();			//�r�ti a takar�t�robotok list�j�t
							this.map	=	new Map(filename);		//bet�lt egy p�ly�t a loadMap param�ter��l kapott f�jlb�l
						}
					}
				}
	//loadRobots
				else if(command.contains("loadRobots")){
					splittedCommand=command.split("\\s+");
					if(splittedCommand.length>=2){
						File robotsFile=new File(splittedCommand[1]);
						if(robotsFile.exists()){													//ha l�tezik a robotok adatait tartalmaz� f�jl
							Robot.statid=0;															//nem �rt null�zni
							this.robots.clear();													//ezt sem �rt �r�teni
							BufferedReader BR = new BufferedReader(new FileReader(robotsFile));
						    while(BR.ready()){														//am�g van adat a robotok adatait tartalmaz� f�jlban
						    	String[] p=BR.readLine().split("\\s+");								//feldarabolja a beolvasott stringet space-enk�nt
						    	if(p.length>=4){													//legal�bb 4 kell
						    		int x1	=	Integer.parseInt(p[0]);
						    		int y1	=	Integer.parseInt(p[1]);
						    		int x2	=	Integer.parseInt(p[2]);
						    		int y2	=	Integer.parseInt(p[3]);
						    		this.robots.add(new Robot(this.map, new Point(x1, y1), new Point(x2, y2)));
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
						int i=Integer.parseInt(splittedCommand[1]);			//h�ny k�r teljen el
						for(int a=0;a<i;++a){
							Cleaner[] cleanersArr=GameManager.cleaners.toArray(new Cleaner[GameManager.cleaners.size()]);
							for(int j=0;j<cleanersArr.length;++j){			//l�pteti a takar�t�robotokat
								cleanersArr[j].move();
							}
							Oil[] oils=GameManager.oilList.toArray(new Oil[GameManager.oilList.size()]);
							for(int j=0;j<oils.length;++j){					//mindegyik olajfolt "�let�t" cs�kkenti egyel
								oils[j].roundElapsed();
							}
							int robotsAlive=0;
							for(Robot r : this.robots){						//megsz�molja h�ny robot "�l" m�g
								if(r.isAlive()){
									robotsAlive=robotsAlive+1;
								}
							}
							round=round+1;
							if(round>=20 || robotsAlive<=1){				//ha csak 1 robot �l, vagy v�ge a j�t�knak (eltelt 20 k�r)
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
						if(id>=0 && id<this.robots.size())
							this.robots.get(id).jump(new Point(x,y));		//id.-edik robotot ugratja, az (x,y) m�dos�t� sebess�g vektorral
					}
				}
	//placeTrap
				else if(command.contains("placeTrap")){
					splittedCommand=command.split("\\s+");
					if(splittedCommand.length>=3){
						int id		=	Integer.parseInt(splittedCommand[1]);
						int type	=	Integer.parseInt(splittedCommand[2]);
						if(id>=0 && id<this.robots.size()){					//id.edik robot lerak egy csapd�t, a type-nak megfelel�en 1-ragacs, 2-olaj
							if(type==1){
								this.robots.get(id).placeGoo();
							}
							else if(type==2){
								this.robots.get(id).placeOil();
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
						GameManager.cleaners.add(new Cleaner(this.map, new Point(x, y)));		//hozz�ad egy takar�t�robotot az (x,y) koordin�t�j� helyre
					}
				}
	//exit
				else if(command.equals("exit")){
					br.close();
					System.exit(0);		//kil�p :D
				}
	//showMap
				else if(command.equals("showMap")){
					this.map.Print();	//konzolon megjelen�ti a mapot, de csak a mez�k t�pus�t 0-norm�l mez�, #-�rok
				}
	//listTraps
				else if(command.equals("listTraps")){
					for(Trap trap : GameManager.trapList)	//konzolra kilist�zza a csapd�kat
						trap.Print();
				}
	//listRobots
				else if(command.equals("listRobots")){
					for(Robot robot : this.robots)			//konzolra kilist�zza a robotokat
						robot.Print();
				}
	//listCleaners
				else if(command.equals("listCleaners")){
					for(Cleaner cleaner : cleaners)			//konzolra kilist�zza a takar�t�robotokat
						cleaner.Print();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 */
	public void step(){
		int x,y;
		x=y=0;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("K�rem a(z) "+currentPlayer+". robot m�dos�t� sebess�gvektor�nak koordin�t�it");
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
		this.robots.get(currentPlayer).jump(new Point(x,y));
		currentPlayer	=	currentPlayer+1;
		if(currentPlayer==this.robots.size()){
			Cleaner[] cleanersArr=GameManager.cleaners.toArray(new Cleaner[GameManager.cleaners.size()]);
			for(int j=0;j<cleanersArr.length;++j){
				cleanersArr[j].move();
			}
			Oil[] oils=GameManager.oilList.toArray(new Oil[GameManager.oilList.size()]);
			for(int j=0;j<oils.length;++j){
				oils[j].roundElapsed();
			}
			int robotsAlive=0;
			for(Robot r : this.robots){
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
	 * 
	 */
	public void end(){
		this.showResults();
	}

	/**
	 * 
	 */
	public void showResults(){
		float max=0;
		int maxIndex=0;
		for(int i=0;i<this.robots.size();++i){
			if(this.robots.get(i).isAlive() && max<this.robots.get(i).getRouteTravelled()){		//az "�l�" robotok k�z�l megkeresi melyik robot tette meg a legt�bb t�vot
				max=this.robots.get(i).getRouteTravelled();
				maxIndex=i;
			}
		}
		System.out.println("Robot id:"+maxIndex+" nyert!");										//merthogy az a robot nyerte meg a j�t�kot
	}
	
	public static void main(String[] args){
		GameManager gm=new GameManager();
		gm.start();
	}
//publikus met�dusok v�ge
}
