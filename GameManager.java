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

	/**
	 * 
	 */
	private boolean[] robotsEliminated;
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
		this.robotsEliminated	=	new boolean[4];
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
				this.map	=	new Map(filename+".map");							//l�trehoz egy p�ly�t a filename f�jlb�l
				File robotsFile=new File(filename+".robots");
				if(robotsFile.exists()){													//ha l�tezik a robotok adatait tartalmaz� f�jl
					Robot.statid=0;															//�j bet�lt�sn�l megint null�r�l induljon
					BufferedReader BR	=	new BufferedReader(new FileReader(robotsFile));
				    while(BR.ready()){														//am�g van adat a robotok adatait tartalmaz� f�jlban
				    	String[] p=BR.readLine().split("\\s+");								//feldarabolja a beolvasott stringet space-enk�nt
				    	if(p.length>=4){													//legal�bb 4 kell
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
		while(true){
			String command="";
			String[] splittedCommand;
			try {
				command=br.readLine();
	//loadMap
				if(command.contains("loadMap")){													//ha tartalmazza a parancs a loadMap stringet
					splittedCommand=command.split("\\s+");									//feldarabolja a stringet space-enk�nt
					if(splittedCommand.length>=2){													//ha legal�b 2 r�sz string maradt
						String filename	=	splittedCommand[1];
						if(new File(filename).exists()){									//akkor t�ltse be az adatokat ha van honnan
							this.map	=	new Map(filename);					//l�trehoz egy p�ly�t a loadMap ut�n be�rt f�jlb�l
						}
					}
				}
	//loadRobots
				else if(command.contains("loadRobots")){
					splittedCommand=command.split("\\s+");									//feldarabolja a stringet space-enk�nt
					if(splittedCommand.length>=2){													//ha legal�b 2 r�sz string maradt
						File robotsFile=new File(splittedCommand[1]);
						if(robotsFile.exists()){													//ha l�tezik a robotok adatait tartalmaz� f�jl
							Robot.statid=0;
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
					
				}
	//jump
				else if(command.contains("jump")){
					splittedCommand=command.split("\\s+");
					if(splittedCommand.length>=4){
						int id		=	Integer.parseInt(splittedCommand[1]);
						int x		=	Integer.parseInt(splittedCommand[2]);
						int y		=	Integer.parseInt(splittedCommand[3]);
						if(id>=0 && id<this.robots.size())
							this.robots.get(id).jump(new Point(x,y));
					}
				}
	//placeTrap
				else if(command.contains("placeTrap")){
					splittedCommand=command.split("\\s+");
					if(splittedCommand.length>=3){
						int id		=	Integer.parseInt(splittedCommand[1]);
						int type	=	Integer.parseInt(splittedCommand[2]);
						if(id>=0 && id<this.robots.size()){
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
					
				}
	//exit
				else if(command.equals("exit")){
					br.close();
					System.exit(0);
				}
	//showMap
				else if(command.equals("showMap")){
					this.map.Print();
				}
	//listTraps
				else if(command.equals("listTraps")){
					for(Trap trap : GameManager.trapList)
						trap.Print();
				}
	//listRobots
				else if(command.equals("listRobots")){
					for(Robot robot : this.robots)
						robot.Print();
				}
	//listCleaners
				else if(command.equals("listCleaners")){
					for(Cleaner cleaner : cleaners)
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
		
	}

	/**
	 * 
	 */
	public void end(){
		
	}

	/**
	 * 
	 */
	public void showResults(){
		
	}
	
	public static void main(String[] args){
		GameManager gm=new GameManager();
		gm.start();
	}
//publikus met�dusok v�ge
}
