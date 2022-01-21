import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.PriorityQueue;

class Graph { 
	public int say=0,cik=0,mesafe=0;
	public static int index=0,index2=0,index3=0;
    private int v;  
    private ArrayList<Integer>[] List;
    public static ArrayList<Integer>[] liste=new ArrayList[820];
    public static ArrayList<Integer>[] liste2=new ArrayList[820];
    public static ArrayList<Integer>[] liste3=new ArrayList[820];
    public static ArrayList<Integer> yedek=new ArrayList(820);
    public Graph() {
    	
    }
	public Graph(int vertices){ 
    	this.v = vertices; 
        Listayarla(this.v);  
    } 
    private void Listayarla(int v) 
    { 
        List = new ArrayList[v];
        for(int i = 0; i < v; i++) 
        { 
            List[i] = new ArrayList<>(); 
        } 
        for(int i = 0; i < 820; i++) 
        { 
            liste[i] = new ArrayList<>(); 
            liste2[i] = new ArrayList<>();
            liste3[i] = new ArrayList<>(); 
        } 
    } 

    public void addEdge(int u, int v) 
    { 
        List[u].add(v);  
    } 
     
    public void Yollar(int s, int d)  
    { 
        boolean[] isVisit = new boolean[v]; 
        ArrayList<Integer> patika = new ArrayList<>(); 
        patika.add(s); 
        tumYollar(s, d, isVisit, patika); 
    } 
    public void hesaplar(sehirler[] sehir,ArrayList secilenler) throws IOException {
    	File f = new File("cikti.txt");
        FileWriter fw = new FileWriter(f,true);
        BufferedWriter bw = new BufferedWriter(fw);
    	
    	for(int i=0;i<index;i++) {
    		if(liste[i].get(0)==41) {
    		liste2[index2]=(ArrayList<Integer>) liste[i].clone();
    		index2++;
    		}
    		else if(liste[i].get(liste[i].size()-1)==41) {
    			for(int j=liste[i].size()-1;j>-1;j--) {
    				liste2[index2].add(liste[i].get(j));
    			}
        		index2++;
        	}
    		else {
    			for(int j=1;j<secilenler.size();j++) {
    				if(liste[i].get(liste[i].size()-1)==secilenler.get(j)) {
    					for(int k=liste[i].size()-1;k>-1;k--) {
    	    				liste2[index2].add(liste[i].get(k));
    	    			}
    	        		index2++;
    				}
    			}
    		}
        }
    	if(index2==0) {
    		for(int i=0;i<index;i++) {
    			for(int j=liste[i].size()-1;j>-1;j--) {
    				liste2[index2].add(liste[i].get(j));
    			}
    		}
    	}
    	for(int i=0;i<index;i++) {
    		for(int j=0;j<index2;j++) {
    			if(liste2[j].get(liste2[j].size()-1)==liste[i].get(0)) {
    				for(int k=0;k<liste[i].size();k++) {
    					liste2[j].add(liste[i].get(k));
    				}
    			}
    		}
    	}
   
    	int say=0;
    	for(int i=0;i<index2;i++) {
    		for(int j=0;j<secilenler.size();j++) {
    			for(int k=0;k<liste2[i].size();k++) {
    				if(secilenler.get(j)==liste2[i].get(k)) {
    					say++;
    				}
    			}
    		}
    		if(say>=secilenler.size()) {
    			for(int j=0;j<liste2[i].size();j++) {
    				liste3[index3].add(liste2[i].get(j));
    			}
    			index3++;
    			say=0;
    		}
    	}
    	for(int i=0;i<index3;i++) {
    		for(int j=0;j<liste3[i].size()-1;j++) {
    			if(liste3[i].get(j)==liste3[i].get(j+1)) {
    				liste3[i].remove(j);
    			}
    		}
    	}
    	bw.newLine();
    	for(int i=0;i<index3;i++) {
    		for(int j=0;j<liste3[i].size();j++) {
    			String sayi1 = String.valueOf(liste3[i].get(j));
    			bw.write(sayi1+" ");
    		}
    		bw.newLine();
    	}
    	bw.close();

    }
    
    
    private void sehirbul(List<Integer> patika) {
    	
    	for(int i=0;i<patika.size();i++) {
    		liste[index].add(patika.get(i));
    	}
    	index++;
    	
    	
    }
    private void tumYollar(Integer a, Integer b,  boolean[] isVisit, List<Integer> patika) { 
      
    	 isVisit[a] = true; 
         
         if (a.equals(b) && say!=30)  
         {
         	sehirbul(patika);
             isVisit[a]= false; 
             say++;
             return ;
         } 
       if(say<30) {
         for (Integer i :List[a])  
         { 
             if (!isVisit[i] && cik!=8000000) 
             {  
             	cik++;
                 patika.add(i); 
                 tumYollar(i, b, isVisit,patika); 
                 patika.remove(i); 
             } 
         } 
         isVisit[a] = false;  
      }
    } 
} 

class Dijkstra { 
	static ArrayList<Vertex> liste=new ArrayList<Vertex>();
	public void kisaYolHesapla(Vertex vertex){
 
		vertex.setMesafe(0);
		PriorityQueue<Vertex> kuyruk = new PriorityQueue<>();
		kuyruk.add(vertex);
		vertex.setVisit(true);
 
		while( !kuyruk.isEmpty() ){
			Vertex vertex1 = kuyruk.poll();
 
			for(Edge edge : vertex1.getList()){
 
				Vertex v = edge.getHedefVertex();
				if(!v.isVisit())
				{
					double yeniMesafe = vertex1.getMesafe() + edge.getWeight();
 
					if( yeniMesafe < v.getMesafe() ){
						kuyruk.remove(v);
						v.setMesafe(yeniMesafe);
						v.setIlk(vertex1);
						kuyruk.add(v);
					}
				}
			}
			vertex1.setVisit(true);
		}
	}
	public List<Vertex> enKisaYol(Vertex hedefVertex){
		List<Vertex> yol = new ArrayList<>();
		
		for(Vertex vertex=hedefVertex;vertex!=null;vertex=vertex.getIlk()){
			yol.add(vertex);
		}
 
		Collections.reverse(yol);
		return yol;
	}	
	
}
class Edge {
	 
	private double weight;
	private Vertex ilkVertex;
	private Vertex hedefVertex;
	
	public Edge(double weight, Vertex ilkVertex, Vertex hedefVertex) {
		this.weight = weight;
		this.ilkVertex = ilkVertex;
		this.hedefVertex = hedefVertex;
	}
 
	public double getWeight() {
		return weight;
	}
 
	public void setWeight(double weight) {
		this.weight = weight;
	}
 
	public Vertex getIlkVertex() {
		return ilkVertex;
	}
 
	public void setIlkVertex(Vertex ilkVertex) {
		this.ilkVertex = ilkVertex;
	}
 
	public Vertex getHedefVertex() {
		return hedefVertex;
	}
 
	public void setHedefVertex(Vertex hedefVertex) {
		this.hedefVertex = hedefVertex;
	}
} 

class Vertex implements Comparable<Vertex> {
 
	private String name;
	private List<Edge> list;
	private boolean visit;
	private Vertex ilk;
	private double mesafe = Double.MAX_VALUE;
	private int plaka;
	
	public int getPlaka() {
		return plaka;
	}

	public void setPlaka(int plaka) {
		this.plaka = plaka;
	}

	public Vertex(int plaka) {
		this.plaka = plaka;
		this.list = new ArrayList<>();
	}
 
	public void komsuEkle(Edge edge) {
		this.list.add(edge);
	}
	public String getName() {
		return name;
	}
 
	public void setName(String name) {
		this.name = name;
	}
 
	public List<Edge> getList() {
		return list;
	}
 
	public void setList(List<Edge> list) {
		this.list = list;
	}
 
	public boolean isVisit() {
		return visit;
	}
 
	public void setVisit(boolean visit) {
		this.visit = visit;
	}
 
	public Vertex getIlk() {
		return ilk;
	}
 
	public void setIlk(Vertex ilk) {
		this.ilk = ilk;
	}
 
	public double getMesafe() {
		return mesafe;
	}
 
	public void setMesafe(double mesafe) {
		this.mesafe = mesafe;
	}
 
	@Override
	public String toString() {
		return this.name;
	}
 
	@Override
	public int compareTo(Vertex otherVertex) {
		return Double.compare(this.mesafe, otherVertex.getMesafe());
	}
}

class komsusehir{
	private String isim;
	private int plaka;
	private int mesafe;
	private int x;
	private int y;
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public String getIsim() {
		return isim;
	}
	public void setIsim(String isim) {
		this.isim = isim;
	}
	public int getPlaka() {
		return plaka;
	}
	public void setPlaka(int plaka) {
		this.plaka = plaka;
	}
	public int getMesafe() {
		return mesafe;
	}
	public void setMesafe(int mesafe) {
		this.mesafe = mesafe;
	}
}
public class sehirler {
	private String isim;
	private int plaka;
	private int x,y;
	private int komsusayisi=0;
	public int getKomsusayisi() {
		return komsusayisi;
	}
	public void setKomsusayisi(int komsusayisi) {
		this.komsusayisi = komsusayisi;
	}
	komsusehir komsular[]=new komsusehir[10];
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public String getIsim() {
		return isim;
	}
	public void setIsim(String isim) {
		this.isim = isim;
	}
	public int getPlaka() {
		return plaka;
	}
	public void setPlaka(int plaka) {
		this.plaka = plaka;
	}	
}
