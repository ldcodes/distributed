package avg;

import java.util.ArrayList;
import java.util.Random;

public class Solution {


	public final static int nodes = 130;
	public final static double error = 0.000001;
	public final static double k = 1.1;
	
	public static double ave =0;
	public static ArrayList<Node> list = new ArrayList<Node>();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 double v = 0;
        for(int i=0 ;i<nodes ;i++) {
        	v = Math.pow(1.1, i) ;
        	list.add(new Node(v));
        	ave += v;
        }
        
        ave /= nodes;
        System.out.println("aim average is "+ave );
       
		for(int i =0 ;i <nodes ;i++)
			System.out.println(" now nodes are "+list.get(i));
		long startTime = System.currentTimeMillis(); 
		gossip();
		long endTime = System.currentTimeMillis();
		System.out.println("aim average is "+ave );
		System.out.println("run time "+(endTime - startTime) + "ms");
      
        for(int i=0 ;i<nodes ;i++) {
        	v = Math.pow(2, i) ;
        	list.get(i).setValue(v);
        	list.get(i).setInterest(1.0);
        	list.get(i).setOver(false);
        }
		for(int i =0 ;i <nodes ;i++)
			System.out.println(" now nodes are "+list.get(i));
		long startTime = System.currentTimeMillis(); 
        anti();
        long endTime = System.currentTimeMillis();
         System.out.println("run time "+(endTime - startTime) + "ms");
         System.out.println("aim average is "+ave );
	}

	private static void anti() {
		// TODO Auto-generated method stub
		Node n ;
		double average = 0 ;
		int another = -1 , time =0 ;
		Random random = new Random();
		System.out.println("\n\n anti begin ");
		while(time < 40) {
			System.out.println("");
			System.out.println("");
			for(int i =0 ;i <nodes ;i++)
				System.out.println("this is "+time+" times  node  "+ i+" "+ list.get(i));
			time ++ ;
			for(int i = 0 ;i < nodes ; i++) {
				n = list.get(i);
				another = random.nextInt(nodes);
				average = ( list.get(another).getValue() + n.getValue() )/2;
				n.setValue(average);
				list.get(another).setValue(average);
				System.out.println("node "+i+" talk to node "+another +" ave = "+average);
			}
		}// while
		test();
	}

	private static  void gossip() {
		// TODO Auto-generated method stub
		boolean over = false ;
		Node n ;
		double average = 0 ;
		int another = -1 , time =0 ;
		Random random = new Random();
		System.out.println("\n\ngossip begin ");
		while(!over) {
			System.out.println("");
			System.out.println("");
			for(int i =0 ;i <nodes ;i++)
				System.out.println("this is "+time+" times  node  "+ i+" "+ list.get(i));
			time ++ ;
			over = true ;
			for(int i = 0 ;i < nodes ; i++) {			
				n = list.get(i);
				if(!n.isOver()) {
					if(random.nextDouble() <= n.getInterest() ) {
						over = false ;
						another = random.nextInt(nodes);
						if(Math.abs(n.getValue()-list.get(another).getValue()) <= error) {
							// equal
							n.updataInsert(k);
							System.out.println("node "+i+" talk to node "+another +" and no talk ");
						}else {
							// not euqa.l
							average = ( list.get(another).getValue() + n.getValue() )/2;
							n.setValue(average);
							list.get(another).setValue(average);
							System.out.println("node "+i+" talk to node "+another +" ave = "+average);
						}
					}else {
						n.setOver(true);
						System.out.println("node "+i+" no talk ");
					}
				}// if over
			}
			
		}// while	
		test();

	}// gossip

	private static void test() {
		// TODO Auto-generated method stub
		double test = 0  , tave = 0  ,s = 0;
		for(int i = 0 ; i < nodes ; i++) {
			test += Math.abs(list.get(i).getValue()-ave);
			tave += list.get(i).getValue();
			s += Math.abs(list.get(i).getValue()-ave)*Math.abs(list.get(i).getValue()-ave);
		}
		System.out.println(" test error is "+test);
		//System.out.println(" ave is "+tave/nodes);
		//System.out.println(" ave gap is  "+(tave/nodes - ave));
		System.out.println(" ave variance   "+(s/nodes));
	}

}


class Node{
	private double interest ;
	private double value;
	private boolean over = false ;
	
	
	public Node() {
		this.interest = 1.0;
	}

	public Node( double value) {
		
		this.interest = 1.0 ;
		this.value = value;
	}
	
	public Node(double interest, double value) {
	
		this.interest = interest;
		this.value = value;
	}
	
	public boolean isOver() {
		return over;
	}

	public void setOver(boolean over) {
		this.over = over;
	}

	public double getInterest() {
		return interest;
	}
	public void setInterest(double interest) {
		this.interest = interest;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	
	public void updataInsert(double k) {
		this.interest /= k ;
	}

	@Override
	public String toString() {
		return "Node [interest=" + interest + ", value=" + value + ", over=" + over + "]";
	}

}
