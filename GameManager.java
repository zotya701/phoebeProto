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
	
//Privát adattagok kezdete
	/**
	 * 
	 */
	private List<Cleaner> cleaners;

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

	/**
	 * 
	 */
	private List<Oil> oilList;

	/**
	 * 
	 */
	private List<Trap> trapList;
//Privát adattagok vége

//publikus metódusok kezdete
	/**
	 * 
	 */
	public GameManager(){
		this.currentPlayer		=	0;
		this.round				=	0;
		this.robotsEliminated	=	new boolean[4];
		this.oilList			=	new ArrayList<Oil>();
		this.trapList			=	new ArrayList<Trap>();
		this.cleaners			=	new ArrayList<Cleaner>();
		this.robots				=	new ArrayList<Robot>(4);
		Goo.trapList			=	this.trapList;
		Oil.trapList			=	this.trapList;
		Oil.oilList				=	this.oilList;
		this.map				=	new Map("test.map", this.trapList);
	}

	/**
	 * 
	 * @param filename
	 */
	public void LoadMap(String filename){
		try {
			if(new File(filename+".map").exists()){											//akkor töltse be az adatokat ha van honnan
				this.map	=	new Map(filename+".map", trapList);					//létrehoz egy pályát a filename fájlból
				File robotsFile=new File(filename+".robots");
				if(robotsFile.exists()){													//ha létezik a robotok adatait tartalmazó fájl
					Robot.statid=0;
					BufferedReader BR	=	new BufferedReader(new FileReader(robotsFile));
				    while(BR.ready()){														//amíg van adat a robotok adatait tartalmazó fájlban
				    	String[] p=BR.readLine().split("\\s+");					//feldarabolja a beolvasott stringet space-enként
				    	if(p.length>=4){											//legalább 4 kell
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
			try {
				command=br.readLine();
	//loadMap
				if(command.contains("loadMap")){													//ha tartalmazza a parancs a loadMap stringet
					String[] splittedCommand=command.split("\\s+");									//feldarabolja a stringet space-enként
					if(splittedCommand.length>=2){													//ha legaláb 2 rész string maradt
						if(new File(splittedCommand[1]).exists()){									//akkor töltse be az adatokat ha van honnan
							this.map	=	new Map(splittedCommand[1], trapList);					//létrehoz egy pályát a loadMap után beírt fájlból
						}
					}
				}
	//loadRobots
				else if(command.contains("loadRobots")){
					String[] splittedCommand=command.split("\\s+");									//feldarabolja a stringet space-enként
					if(splittedCommand.length>=2){													//ha legaláb 2 rész string maradt
						File robotsFile=new File(splittedCommand[1]);
						if(robotsFile.exists()){													//ha létezik a robotok adatait tartalmazó fájl
							Robot.statid=0;
							BufferedReader BR = new BufferedReader(new FileReader(robotsFile));
						    while(BR.ready()){														//amíg van adat a robotok adatait tartalmazó fájlban
						    	String[] p=BR.readLine().split("\\s+");					//feldarabolja a beolvasott stringet space-enként
						    	if(p.length>=4){											//legalább 4 kell, mivel egy robotot 4 adattal lehet inicializálni
						    		this.robots.add(new Robot(this.map, new Point(Integer.parseInt(p[0]), Integer.parseInt(p[1])), new Point(Integer.parseInt(p[2]), Integer.parseInt(p[3]))));
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
					
				}
	//placeTrap
				else if(command.contains("placeTrap")){
					
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
					for(int i=this.trapList.size()-1;i>=0;--i)
						this.trapList.get(i).Print();
				}
	//listRobots
				else if(command.equals("listRobots")){
					for(Robot robot : robots)
						robot.Print();
				}
	//listCleaners
				else if(command.equals("listCleaners")){
					//for(Cleaner cleaner : cleaners)
						//cleaner.Print();
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
//publikus metódusok vége
}
