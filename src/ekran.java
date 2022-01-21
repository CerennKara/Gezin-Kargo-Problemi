import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;


public class ekran extends JFrame {
	public static ArrayList <Integer> secilenler=new ArrayList(100);
	static ArrayList<Integer> Gidilenler=new ArrayList();
	static int mesafe=0,sayac=0;
	Image img;
	public static int komsusay=0,nodesay=0;
	public static void varmi(sehirler sehir,int plaka) {
	     for(int i=0;i<komsusay+1;i++) {
	     if(sehir.komsular[i].getPlaka()==plaka){
				return;
			}
		}
	     sehir.komsular[komsusay].setPlaka(plaka);
	      komsusay++;
	}
	public void ciz(sehirler sehir[]) throws IOException {
		File file1=new File("cikti.txt");
		FileReader fr1=new FileReader(file1);
		String line1;
        BufferedReader br1 = new BufferedReader(fr1);
        
		
		
		int index=40,sayi2=0,kayma=20;
		Graphics g=img.getGraphics();
		((Graphics2D) g).setStroke(new BasicStroke(3));
		g.setColor(Color.black);
		while((line=br1.readLine())!=null) {
		    if(sayac==1) {
	               g.setColor(Color.blue);
			}
			if(sayac==2) {
				g.setColor(Color.green);
			}
			if(sayac==3) {
				g.setColor(Color.red);
			}
			if(sayac==4) {
				g.setColor(Color.ORANGE);
			}
			if(sayac==5)
				break;
			sayac++;
			for(int i=2;i<line.length()-1;i++) {
		    	  if(Character.isDigit(line.charAt(i))){
		    		 if(line.charAt(i-1)==' ' && line.charAt(i)=='0' && (line.charAt(i+1)==' ')) {
		    			sayi2=0;
		    		 }
		    		 else {
		    		int sayi1=Integer.parseInt(String.valueOf(line.charAt(i))); 
		    		 sayi2=(int) sayi1+(sayi2*10);
		    		 if(line.charAt(i+1)==' ' || i+1==line.length()-1) {
		    			 g.drawLine(sehir[index].getX(),sehir[index].getY()-kayma,sehir[sayi2-1].getX(),sehir[sayi2-1].getY()-kayma);
		    			 index=sayi2-1;
		    			 sayi2=0;
		    		 }
	 	          }
		    	  }
		    	  if(line.charAt(i)=='=') {
		    		  break;
		    	  }
		      }
			kayma-=5;
			
        }
		
		
	}
	public static void hepsinihesapla(ArrayList secilenler,sehirler sehir[]) throws IOException {
		
		Graph[] g = new Graph[81]; 
		for(int i=0;i<81;i++) {
			g[i]=new Graph(82);
		}
		for(int k=0;k<81;k++) {
			for(int i=0;i<81;i++) {
				for(int j=0;j<sehir[i].getKomsusayisi();j++) {
				g[k].addEdge(sehir[i].getPlaka(),sehir[i].komsular[j].getPlaka());
				}
			}
		}
		 for(int i=0;i<secilenler.size()-1;i++) {
				 g[i].Yollar(sehir[(int) secilenler.get(i)].getPlaka(),sehir[(int) secilenler.get(i+1)].getPlaka());
				
		 }
          Graph f=new Graph();
	      f.hesaplar(sehir,secilenler);
		
	}
	public static void hesapla(ArrayList secilenler,sehirler sehir[],Dijkstra kisaYol[],Vertex vertex[]) throws IOException {
		int mesafe=0;
		File f = new File("cikti.txt");
        FileWriter fw = new FileWriter(f,false);
        BufferedWriter bw = new BufferedWriter(fw);
	
        for(int i=0;i<secilenler.size()-1;i++) {
			for(int j=0;j<81;j++) {
				int plaka=sehir[j].getPlaka();
				vertex[j]=new Vertex(plaka);
			}
			for(int j=0;j<81;j++) {
				for(int k=0;k<sehir[j].getKomsusayisi();k++) {
					vertex[j].komsuEkle(new Edge(sehir[j].komsular[k].getMesafe(),vertex[j],vertex[sehir[j].komsular[k].getPlaka()-1]));
				}	
			}
			kisaYol[i]=new Dijkstra();
			kisaYol[i].kisaYolHesapla(vertex[(int) secilenler.get(i)]);
			mesafe+=vertex[(int)secilenler.get(i+1)].getMesafe();
			
			for(int j=0;j<kisaYol[i].enKisaYol(vertex[(int)secilenler.get(i+1)]).size();j++) {
			Gidilenler.add(kisaYol[i].enKisaYol(vertex[(int)secilenler.get(i+1)]).get(j).getPlaka());
			}
		 
		}
		for(int i=0;i<Gidilenler.size()-1;i++) {
			if(Gidilenler.get(i).equals(Gidilenler.get(i+1))) {
				Gidilenler.remove(i);
				i=0;
			}
		}
		for(int i=0;i<Gidilenler.size();i++) {
			bw.write(Gidilenler.get(i)+" ");
		}
		String mesafe1 = String.valueOf(mesafe);
		bw.write("="+mesafe1);
		bw.close();
	}
	public static void siraduzelt(ArrayList secilenler,sehirler sehir[]) {
		ArrayList <Integer> kopya=new ArrayList();
		ArrayList <Integer> duzenlenmis=new ArrayList();
		kopya=(ArrayList<Integer>) secilenler.clone();
		kopya.add(0,41);
		int adet=secilenler.size();
		secilenler.clear();
		secilenler.add(0,40);
		int index=0,temp=0,sil=0,girdi=0;
		for(int i=0;i<adet;i++) {
			if(i==0) {
				double mesafe=0;
				for(int j=1;j<adet+1;j++) {
					if(mesafe<
					 Math.sqrt(Math.pow((sehir[kopya.get(i)].getX()-sehir[kopya.get(j)].getX()),2.0)+Math.pow((sehir[kopya.get(i)].getY()-sehir[kopya.get(j)].getY()),2.0))){
					 mesafe=Math.sqrt(Math.pow((sehir[kopya.get(i)].getX()-sehir[kopya.get(j)].getX()),2.0)+Math.pow((sehir[kopya.get(i)].getY()-sehir[kopya.get(j)].getY()),2.0));
					 temp=kopya.get(j); sil=j;
				 }
			}
			index++;
			secilenler.add(index,temp);
			kopya.remove(sil);
			kopya.remove(0);
			}
			else {
				double mesafe=Math.sqrt(Math.pow((sehir[(int) secilenler.get(secilenler.size()-1)].getX()-sehir[kopya.get(0)].getX()),2.0)+Math.pow((sehir[(int) secilenler.get(secilenler.size()-1)].getY()-sehir[kopya.get(0)].getY()),2.0));
				for(int j=1;j<kopya.size();j++) {
					if(mesafe>Math.sqrt(Math.pow((sehir[(int) secilenler.get(secilenler.size()-1)].getX()-sehir[kopya.get(j)].getX()),2.0)+Math.pow((sehir[(int) secilenler.get(secilenler.size()-1)].getY()-sehir[kopya.get(j)].getY()),2.0))) {
						mesafe=Math.sqrt(Math.pow((sehir[(int) secilenler.get(secilenler.size()-1)].getX()-sehir[kopya.get(j)].getX()),2.0)+Math.pow((sehir[(int) secilenler.get(index-1)].getY()-sehir[kopya.get(j)].getY()),2.0));
						sil=j; temp=kopya.get(j); girdi++;
					}
				}
				if(girdi==0) {
					sil=0;
					temp=kopya.get(0);
				}
				if(0==kopya.size()) {
					index++;
					secilenler.add(index,temp);
					girdi=0;
				}
				else {
					index++;
					secilenler.add(index,temp);
					kopya.remove(sil);
					girdi=0;
				}
			}
		}
		secilenler.add(index+1,40);
	}
	private JPanel contentPane;
	private static JPanel Pane;
	private JTextField txtehirIsmi;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ekran frame = new ekran();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	static String isim,line;
	static int say=0,y=10,say1=0,secilen=0,say2=0;
	public void ekranciz() throws IOException {
		img=ImageIO.read(new File("images/Harita.png"));
		Pane = new JPanel();
		Pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(Pane);
		Pane.setLayout(null);
		Pane.setBounds(0,0,1350,731);
		JLabel label=new JLabel();
		label.setIcon(new ImageIcon(img));
		label.setBounds(0,-29,1350,731);
		Pane.add(label);
	}
	public ekran() throws IOException{
		File file1=new File("plakalar.txt");
		FileReader fr1=new FileReader(file1);
		String line1;
        BufferedReader br1 = new BufferedReader(fr1);
        sehirler sehir[]=new sehirler[82];
        int sehirsay=0;
        for(int i=0;i<81;i++) {
        sehir[i]=new sehirler(); }
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100,1350,731);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JCheckBox box[]=new JCheckBox[82];
		for(int i=0;i<81;i++) {
			while((line1=br1.readLine())!=null) {
	        	isim=line1.substring(13,line1.length());
	        	if(say==i) 
	        		break;
	        }
			box[i]=new JCheckBox(isim);
			say++;
		}
		File file2=new File("plakalar.txt");
		FileReader fr2=new FileReader(file2);
		String line2;
        BufferedReader br2 = new BufferedReader(fr2);
		for(int i=0;i<81;i++) {
	  	      for(int j=0;j<10;j++) {
	  	    	  sehir[i].komsular[j]=new komsusehir();}
	  	      }
	        while((line2=br2.readLine())!=null) {
	        	String isim=line2.substring(13,line2.length());
	        	int plaka=Integer.valueOf(line2.substring(0,2));
	        	int x=Integer.valueOf(line2.substring(3,7));
	        	int y=Integer.valueOf(line2.substring(8,12));
	        	sehir[sehirsay].setIsim(isim);
	        	sehir[sehirsay].setPlaka(plaka);
	        	sehir[sehirsay].setX(x);
	        	sehir[sehirsay].setY(y);
	            sehirsay++;
	        }
		JPanel panel=new JPanel();
		for(int i=0;i<81;i++) {
			box[i].setBounds(20,y,170,37);
			panel.add(box[i]);
			y+=25;
		}
		 File file=new File("komsuuzaklik.txt");
			FileReader fr=new FileReader(file);
	        BufferedReader br = new BufferedReader(fr);
	        
	        int sifirsay=0,sayi2=0,komsusay2=0;
	       while ((line = br.readLine()) != null) {
	 		   	int plaka1=Integer.valueOf(line.substring(0,2));
	 		   	if(sehir[say2].getPlaka()==plaka1) {
	 		   	  line = line.replaceAll("[^-?0-9]+"," "); 
	 		   	 for(int i=2;i<line.length();i++) {
					 if(line.charAt(i)==' ') {
						 sifirsay++;
					 }
					 else if(line.charAt(i)!='0') {
						 if(sifirsay<plaka1) {
		 					varmi(sehir[say2],sifirsay); 
						 }else if(plaka1<=sifirsay) {
							 varmi(sehir[say2],sifirsay+1); 
		 			     }
					 }
				  }
	 		   	 for(int i=2;i<line.length()-1;i++) {
			    	  if(Character.isDigit(line.charAt(i))){
			    		 if(line.charAt(i-1)==' ' && line.charAt(i)=='0' && (line.charAt(i+1)==' ')) {
			    			sayi2=0;
			    		 }
			    		 else {
			    		int sayi1=Integer.parseInt(String.valueOf(line.charAt(i))); 
			    		 sayi2=(int) sayi1+(sayi2*10);
			    		 if(line.charAt(i+1)==' ' || i+1==line.length()-1) {
			    			sehir[say2].komsular[komsusay2].setMesafe(sayi2);
			    			 sayi2=0;
			    			 komsusay2++;
			    		 }
		 	          }
			    	  }
			      }
	 		   	}
	 		   sehir[say2].setKomsusayisi(komsusay);
			   komsusay=0;
	 		   	say2++;
	 		   	sifirsay=0;	
	 		   	komsusay2=0;
	 		}
	       br.close();
	       for(int i=0;i<81;i++) {
	      	 for(int j=0;j<sehir[i].getKomsusayisi();j++) {
	      		 for(int k=0;k<81;k++) {
	      			 if(sehir[i].komsular[j].getPlaka()==sehir[k].getPlaka()) {
	      				 sehir[i].komsular[j].setIsim(sehir[k].getIsim());
	      				 sehir[i].komsular[j].setX(sehir[k].getX());
	      				 sehir[i].komsular[j].setY(sehir[k].getY());
	      			 }
	      		 }
	      	 }
	       }
		JScrollPane scrollPane = new JScrollPane(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(822,190,317,308);
		contentPane.add(scrollPane);
		
		txtehirIsmi = new JTextField();
		txtehirIsmi.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if(say1==0) {
				txtehirIsmi.setText("");
				say1++; }
				if(e.getKeyChar() == KeyEvent.VK_ENTER){
				      line=txtehirIsmi.getText();
				      for(int i=0;i<81;i++) {
				    	if(!box[i].getText().equals(line)) {
				    		box[i].setVisible(false);
				    	  }
				    	else if(box[i].getText().equals(line)) {
				    		box[i].setVisible(true);
				    	  }
				      }
				}
				if(e.getKeyChar()==KeyEvent.VK_BACK_SPACE) {
					line=txtehirIsmi.getText();
					if(line.equals("")) {
						 for(int i=0;i<81;i++) {
						    		box[i].setVisible(true);
						      }
					}
				}
			}
		});
		txtehirIsmi.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				txtehirIsmi.setText("");
			}
		});
		txtehirIsmi.setHorizontalAlignment(SwingConstants.CENTER);
		txtehirIsmi.setText("Sehir ismi");
		scrollPane.setColumnHeaderView(txtehirIsmi);
		txtehirIsmi.setColumns(10);
		JLabel lblNewLabel = new JLabel("10'dan fazla sehir secemessiniz.");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 19));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setBounds(822, 552, 331, 57);
		lblNewLabel.setVisible(false);
		contentPane.add(lblNewLabel);
		Vertex[] vertex=new Vertex[82];
        Dijkstra[] kisaYol= new Dijkstra[82];
        File f = new File("cikti.txt");
        if (!f.exists()) {
            f.createNewFile();
        }
        FileWriter fw = new FileWriter(f, false);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.close();
		JButton btnBasla = new JButton("Basla");
		btnBasla.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				for(int i=0;i<81;i++) {
					if(box[i].isSelected()) {
						secilenler.add(i);
						secilen++;
					}
				}
				if(secilen>10) {
					lblNewLabel.setVisible(true);
					secilenler.removeAll(secilenler);
				}
				else {
					lblNewLabel.setVisible(false);
					try {
						ekranciz();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					siraduzelt(secilenler,sehir);
					try {
						hesapla(secilenler,sehir,kisaYol,vertex);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						hepsinihesapla(secilenler,sehir);
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					try {
						ciz(sehir);
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
				secilen=0;
			}
		});
		btnBasla.setBounds(911, 511, 143, 41);
		contentPane.add(btnBasla);
		
		JLabel lblNewLabel_1 = new JLabel("<HTML>-Kargo firmamizin sistemine hosgeldiniz.<br/>-Sistemimiz en kisa yoldan ulasim yapmayi hedeflemektedir.<br/>"
				+ "-Kargomuzun merkezi ve baslangic noktasi Kocaeli'dir.<br/>-Kargolarin teslim edilecek sehirleri secmek icin yanda bulunan arama yerini kullanmalisiniz."
				+ "<br/>-En fazla 10 adet sehir secimi yapabilirsiniz.<br/>-Secim yaptiktan sonra 'Basla' butonuna tiklamalisiniz.<br/>-En kisa 5 yol haritada sirasiyla gosterilecektir."
				+ "<br/>Diger yol alternatiflerine ise programin bulundugu klasorde olusacak olan 'cikti.txt'den bakabilirsiniz.<br/>Iyi gunler dileriz...</HTML> ");
		lblNewLabel_1.setForeground(new Color(46, 139, 87));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 16));
		lblNewLabel_1.setBounds(31, 199, 587, 299);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("170201016-180201069 Kargo Sirketi");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 23));
		lblNewLabel_2.setBounds(335, 26, 602, 83);
		contentPane.add(lblNewLabel_2);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
	}

}
