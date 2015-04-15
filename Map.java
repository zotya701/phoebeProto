package phoebeProto;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class Map implements Printable{
	
//Privát adattagok kezdete
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
	private List<Trap> trapList;
	
	/**
	 * 
	 */
	private OutsideField outside;
//Privát adattagok vége

//publikus metódusok kezdete
	/**
	 * 
	 * @param filename
	 * @param trapList
	 */
	public Map(String filename, List<Trap> trapList){
		this.trapList	=	trapList;
		this.outside	=	new OutsideField();
		this.fields		=	new ArrayList<List<Field>>();
		this.nodes		=	new ArrayList<List<Node>>();
		
		try {
			File map=new File(filename+".robots");
			if(map.exists()){
				BufferedReader br	=	new BufferedReader(new FileReader(map));
				if(br.ready()){
					String[] size=br.readLine().split("\\s+");
			    	if(size.length>=2){
			    		this.size	=	new Point(Integer.parseInt(size[0]), Integer.parseInt(size[1]));
			    		for(int i=0; i<this.size.y;++i){
			    			this.fields.add(i, new ArrayList<Field>());
			    			this.nodes.add(i, new ArrayList<Node>());
			    		}
			    	}
				}
				for(int y=0;y<this.size.y && br.ready();++y){
			    	String line=br.readLine();
			    	for(int x=0;x<this.size.x && x<line.length();++x){
			    		if(line.charAt(x)=='0'){
			    			this.fields.get(y).add(x, new NormalField());
			    		}
			    		else if(line.charAt(x)=='#'){
			    			this.fields.get(y).add(x, this.outside);
			    		}
			    		else if(line.charAt(x)=='1'){
			    			NormalField nf=new NormalField();
			    			nf.addTrap(new Goo());
			    			this.fields.get(y).add(x, nf);
			    		}
			    		else if(line.charAt(x)=='2'){
			    			NormalField nf=new NormalField();
			    			nf.addTrap(new Oil());
			    			this.fields.get(y).add(x, nf);
			    		}
			    	}
				}
			    br.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 */
	public void Print(){
		
	}
	
	/**
	 * 
	 * @param coord
	 * @return
	 */
	public Field getField(Point coord){
		return fields.get(coord.y).get(coord.x);
	}
	
	
	public Point getNewPos(Point currentPos, Point vel){
		currentPos.translate(vel.x, vel.y);
		return currentPos;
	}
	
	/**
	 * 
	 * @param s
	 * @param d
	 * @return
	 */
	public float calculateDistance(Point s, Point d){
		return (float)(Math.pow((d.x-s.x)*(d.x-s.x)+(d.y-s.y)*(d.y-s.y), 0.5));
	}
	
	/**
	 * 
	 * @param source
	 * @return
	 */
	public Point getRouteToTrap(Point source){
		return source;
	}
	
	/**
	 * 
	 */
	public void createGraph(){
		
	}
//publikus metódusok vége
}
