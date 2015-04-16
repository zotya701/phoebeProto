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
	private List<Trap> trapList;
	
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
	public Map(String filename, List<Trap> trapList){
		this.trapList	=	trapList;
		this.outside	=	new OutsideField();
		this.fields		=	new ArrayList<List<Field>>();
		this.nodes		=	new ArrayList<List<Node>>();
		Oil.trapList.clear();
		Oil.oilList.clear();
		
		try {
			File map=new File(filename);
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
				List<String> lines=new ArrayList<String>();
				for(int y=0;y<this.size.y && br.ready();++y){
			    	String line	=	br.readLine();
			    	lines.add(line);
			    	for(int x=0;x<this.size.x && x<line.length();++x){
			    		if(line.charAt(x)=='0'){
			    			this.fields.get(y).add(x, new NormalField());
			    		}
			    		else if(line.charAt(x)=='#'){
			    			this.fields.get(y).add(x, this.outside);
			    		}
			    		else if(line.charAt(x)=='1'){
			    			NormalField nf=new NormalField();
			    			nf.addTrap(new Goo(new Point(x,y)));
			    			this.fields.get(y).add(x, nf);
			    		}
			    		else if(line.charAt(x)=='2'){
			    			NormalField nf=new NormalField();
			    			nf.addTrap(new Oil(new Point(x,y)));
			    			this.fields.get(y).add(x, nf);
			    		}
			    		this.nodes.get(y).add(x, new Node(new Point(x, y)));
			    	}
				}
				this.createGraph(lines);
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
		return fields.get(coord.y).get(coord.x);
	}
	
	/**
	 * 
	 * @param currentPos
	 * @param vel
	 * @return
	 */
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
		float dist=(float)(Math.pow((d.x-s.x)*(d.x-s.x)+(d.y-s.y)*(d.y-s.y), 0.5));
		return dist;
	}
	
	/**
	 * 
	 * @param source
	 * @return
	 */
	public Point getRouteToTrap(Point source){
		this.computePaths(this.nodes.get(source.y).get(source.x));
		int min			=	Integer.MAX_VALUE;
		Point minPoint	=	new Point(source);
		for(Trap trap : this.trapList){//megkeressük melyik csapdához lehet a legrövidebb úton elmenni
			if(min>this.nodes.get(trap.getPosition().y).get(trap.getPosition().x).getMinDistance()){
				min=this.nodes.get(trap.getPosition().y).get(trap.getPosition().x).getMinDistance();
				minPoint=trap.getPosition();
			}
		}
		List<Node> shortestPath	=	this.getShortestPathTo(this.nodes.get(minPoint.y).get(minPoint.x));
		if(shortestPath.size()>1)
			minPoint=shortestPath.get(1).getCoord();
		else minPoint=shortestPath.get(0).getCoord();
		return minPoint;
	}
	
	/**
	 * 
	 */
	public void createGraph(List<String> lines){
		for(int y=0;y<this.size.y;++y){
			for(int x=0;x<this.size.x;++x){
				if(lines.get(y).charAt(x)!='_'){
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
		for(int y=0;y<this.size.y;++y){
			for(int x=0;x<this.size.x;++x){
				this.nodes.get(y).get(x).setPrevious(null);
				this.nodes.get(y).get(x).setMinDistance(Integer.MAX_VALUE);
			}
		}
		
        source.setMinDistance(0);
        PriorityQueue<Node> NodeQueue = new PriorityQueue<Node>();
      	NodeQueue.add(source);
      	
		while (!NodeQueue.isEmpty()) {
		    Node u = NodeQueue.poll();
            for (Edge e : u.getAdjacencies()){
                Node v = e.getTarget();
                //double weight = e.getWeight();
                int distanceThroughU = u.getMinDistance() + 1;
				if (distanceThroughU < v.getMinDistance()) {
				    NodeQueue.remove(v);
				    v.setMinDistance(distanceThroughU);
				    v.setPrevious(u);
				    NodeQueue.add(v);
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
        for (Node node = target; node != null; node = node.getPrevious())
            path.add(node);
        Collections.reverse(path);
        return path;
    }
//publikus metódusok vége
}
