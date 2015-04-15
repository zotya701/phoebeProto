package phoebeProto;

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
	
//Priv�t adattagok kezdete
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
//Priv�t adattagok v�ge

//publikus met�dusok kezdete
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
			if(new File(filename+".map").exists()){											//akkor t�ltse be az adatokat ha van honnan
				this.map			=	new Map(filename+".map", trapList);					//l�trehoz egy p�ly�t a filename f�jlb�l
				File robotsFile=new File(filename+".robots");
				if(robotsFile.exists()){													//ha l�tezik a robotok adatait tartalmaz� f�jl
					BufferedReader BR = new BufferedReader(new FileReader(robotsFile));
				    while(BR.ready()){														//am�g van adat a robotok adatait tartalmaz� f�jlban
				    	String[] robotParams=BR.readLine().split("\\s+");					//feldarabolja a beolvasott stringet space-enk�nt
				    	if(robotParams.length>=4){											//legal�bb 4 kell, mivel egy robotot 4 adattal lehet inicializ�lni
				    		//this.robots.add(new Robot(robotParams[0], robotParams[1], robotParams[2], robotParams[3]));
				    	}
				    }
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
					String[] splittedCommand=command.split("\\s+");									//feldarabolja a stringet space-enk�nt
					if(splittedCommand.length>=2){													//ha legal�b 2 r�sz string maradt
						if(new File(splittedCommand[1]).exists()){									//akkor t�ltse be az adatokat ha van honnan
							this.map	=	new Map(splittedCommand[1], trapList);					//l�trehoz egy p�ly�t a loadMap ut�n be�rt f�jlb�l
						}
					}
				}
	//loadRobots
				else if(command.contains("loadRobots")){
					String[] splittedCommand=command.split("\\s+");									//feldarabolja a stringet space-enk�nt
					if(splittedCommand.length>=2){													//ha legal�b 2 r�sz string maradt
						File robotsFile=new File(splittedCommand[1]);
						if(robotsFile.exists()){													//ha l�tezik a robotok adatait tartalmaz� f�jl
							BufferedReader BR = new BufferedReader(new FileReader(robotsFile));
						    while(BR.ready()){														//am�g van adat a robotok adatait tartalmaz� f�jlban
						    	String[] robotParams=BR.readLine().split("\\s+");					//feldarabolja a beolvasott stringet space-enk�nt
						    	if(robotParams.length>=4){											//legal�bb 4 kell, mivel egy robotot 4 adattal lehet inicializ�lni
						    		//this.robots.add(new Robot(robotParams[0], robotParams[1], robotParams[2], robotParams[3]));
						    	}
						    }
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
					for(Trap trap : trapList)
						trap.Print();
				}
	//listRobots
				else if(command.equals("listRobots")){
					//for(Robot robot : robots)
						//robot.Print();
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
//publikus met�dusok v�ge
}
