package hashing;

import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Hashtable;

public class HashTable {
	Hashtable<Integer,Value> hash;
	
	static String string = "abcdefghijklmnopqrstuvwxyz";
	static SecureRandom rnd = new SecureRandom();
	
	//get a random string
	public String getString(int length){
		StringBuilder sb = new StringBuilder(length);
		   for( int i = 0; i < length; i++ ) 
		      sb.append( string.charAt( rnd.nextInt(string.length()) ) );
		   return sb.toString();
	}
	
	//create a hash table
	public void createTable(int size){
		hash = new Hashtable<Integer,Value>(size);
	}
	
	//get the key a value
	public int hushFunction(Value v, int size){
		int key=0;
		String value = v.getValue();
		for (int i=0;i<value.length();i++){
 			key += value.charAt(i)*Math.pow(32,value.length()-i-1);
		}
		key = key%size;
		return key;
	}
	
	//display a table
	public void showTable(double size){
		System.out.println("Table size: " + (int)size);
		System.out.println("Load factor: "+ (double)(hash.size()) / size);
		for(int i =0  ; i<size; i++ ){
			if(hash.get(i)!=null){
				System.out.print("("+i+","+hash.get(i).getValue()+") ");
			}
		}
		System.out.println(" ");
		System.out.println(" ");
		System.out.println(" ");
	}
	
	//use linear proding to fill the hash table
	public void linearProd(int size, int keynumber){
		createTable(size);
		while(keynumber>0){
			keynumber--;
			int count = 1;
			Value v = new Value();
			v.setValue( getString(5) );
			int firstkey = hushFunction(v, size);
			int key = firstkey;
			while(hash.get(key)!=null){
				key = (firstkey+count)%size;
				count++;
			}
			hash.put(key, v);
		}
	}
	
	//use quadratic to fill the hash table
	public void quadratic(int size, int keynumber){
		createTable(size);
		while(keynumber>0){
			keynumber--;
			int count=1;
			Value v = new Value();
			v.setValue( getString(5) );
			int firstkey = hushFunction(v, size);
			int key = firstkey;
			while(hash.get(key)!=null){
				key= (int) ((firstkey + Math.pow(count,2))%size);
				count++;
			}
			hash.put(key, v);
		}
	}
	
	//use double hashing
	public void doubleHash(int size, int keynumber){
		createTable(size);
		while(keynumber>0){
			keynumber--;
			int count=1;
			Value v = new Value();
			v.setValue( getString(5) );
			int firstkey = hushFunction(v, size);
			int secondkey = doubleHashFunction(v, size);
			int key = firstkey;
			while(hash.get(key)!=null){
				key= (firstkey + count*secondkey)%size;
				count++;
			}
			hash.put(key, v);
		}
	}
	
	//another hash function
	public int doubleHashFunction(Value v, int size){
		int key=0;
		int R = 13;
		key = R- v.getKey()%R;
		return key;
	}
	
	//test linear
	public void useLinearProd(int size, int keynumber){
		System.out.println("Using Linear Proding:");
		linearProd(size, keynumber);
		showTable(size);
	}
	
	//teat quadratic
	public void useQuadratic(int size, int keynumber){
		System.out.println("Using Quadratic Proding:");
		quadratic(size, keynumber);
		showTable(size);
	}
	
	//test double
	public void useDoubleHash(int size, int keynumber){
		System.out.println("Using Double Hashing:");
		doubleHash(size, keynumber);
		showTable(size);
	}
	
	//test all
	public void testAll(int size, double loadfactor){
		int keynumber = (int) (size*loadfactor);
		useLinearProd(size, keynumber);
		useQuadratic(size, keynumber);
		useDoubleHash(size, keynumber);
	}
	
	//analyze load factor for size 200
	public void testLoadFactor() throws IOException{
		FileWriter fw1 = new FileWriter("linear_size_200.csv");
    	FileWriter fw2 = new FileWriter("quadratic_size_200.csv");
    	FileWriter fw3 = new FileWriter("double_size_200.csv");
		int size = 200;
		
		//load factor from 0.01 to 0.80
		for(double i = 1; i<=80; i++){
			double loadfactor = i/100;
			int keynumber = (int) (size*loadfactor);
			
			//run 10 times and get the average for linear
			double sum1 = 0;
			for(int j = 0 ; j<10; j++){
				long s = System.nanoTime();
				linearProd(size, keynumber);
	        	long e = System.nanoTime();
	        	double linear =(double) (e-s)/100000;
	        	sum1 = sum1+linear;
			}
			double linear = sum1/10;
			fw1.write(loadfactor+","+linear+"\n");
        	System.out.println(loadfactor+" load factor test for linear "+linear);
        	
        	//run 10 times and get the average for quadratic
			double sum2 = 0;
			for(int j = 0 ; j<10; j++){
				long s = System.nanoTime();
				quadratic(size, keynumber);
	        	long e = System.nanoTime();
	        	double quadratic =(double) (e-s)/100000;
	        	sum2 = sum2+quadratic;
			}
			double quadratic = sum2/10;
			fw2.write(loadfactor+","+quadratic+"\n");
        	System.out.println(loadfactor+" load factor test for quadratic "+quadratic);
        	
        	//run 10 times and get the average for double
			double sum3 = 0;
			for(int j = 0 ; j<10; j++){
				long s = System.nanoTime();
				doubleHash(size, keynumber);
	        	long e = System.nanoTime();
	        	double doubleHash =(double) (e-s)/100000;
	        	sum3 = sum3+doubleHash;
			}
			double doubleHash = sum3/10;
			fw3.write(loadfactor+","+doubleHash+"\n");
        	System.out.println(loadfactor+" load factor test for doubleHash "+doubleHash);
		}
		
		fw1.close();
    	fw2.close();
    	fw3.close();
    	System.out.println("Time Test end");
	}
	
	//analyze table size for load factor 0.6
	public void testSize() throws IOException{
		FileWriter fw1 = new FileWriter("linear_loadfactor_0.6.csv");
    	FileWriter fw2 = new FileWriter("quadratic_loadfactor_0.6.csv");
    	FileWriter fw3 = new FileWriter("double_loadfactor_0.6.csv");
		double loadfactor = 0.6;
		
		//size from 10 to 1000
		for(double i = 1; i<=100; i++){
			int size = (int) (i*10);
			int keynumber = (int) (size*loadfactor);
			
			//run 10 times and get the average for linear
			double sum1 = 0;
			for(int j = 0 ; j<10; j++){
				long s = System.nanoTime();
				linearProd(size, keynumber);
	        	long e = System.nanoTime();
	        	double linear =(double) (e-s)/100000;
	        	sum1 = sum1+linear;
			}
			double linear = sum1/10;
			fw1.write(size+","+linear+"\n");
        	System.out.println(size+" size test for linear "+linear);
        	
        	//run 10 times and get the average for quadratic
			double sum2 = 0;
			for(int j = 0 ; j<10; j++){
				long s = System.nanoTime();
				quadratic(size, keynumber);
	        	long e = System.nanoTime();
	        	double quadratic =(double) (e-s)/100000;
	        	sum2 = sum2+quadratic;
			}
			double quadratic = sum2/10;
			fw2.write(size+","+quadratic+"\n");
        	System.out.println(size+" size test for quadratic "+quadratic);
        	
        	//run 10 times and get the average for double
			double sum3 = 0;
			for(int j = 0 ; j<10; j++){
				long s = System.nanoTime();
				doubleHash(size, keynumber);
	        	long e = System.nanoTime();
	        	double doubleHash =(double) (e-s)/100000;
	        	sum3 = sum3+doubleHash;
			}
			double doubleHash = sum3/10;
			fw3.write(size+","+doubleHash+"\n");
        	System.out.println(size+" size test for doubleHash "+doubleHash);
		}
		
		fw1.close();
    	fw2.close();
    	fw3.close();
    	System.out.println("Time Test end");
	}
	
	public void testResizing() throws IOException{
		FileWriter fw = new FileWriter("dynamic_resizing.csv");
		int size = 200;
		double loadfactor = 1;
		int keynumber = (int) (loadfactor*size);
		createTable(size);
		int count2 = 1;
		while(keynumber>0){
			long s = System.nanoTime();
			keynumber--;
			int count = 1;
			Value v = new Value();
			v.setValue( getString(5) );
			int firstkey = hushFunction(v, size);
			int key = firstkey;
			while(hash.get(key)!=null){
				key = (firstkey+count)%size;
				count++;
			}
			hash.put(key, v);
			long e = System.nanoTime();
			double time =(double) (e-s)/100000;
			fw.write(count2+","+time+"\n");
        	System.out.println("Adding "+count2+" to hash table "+time);
        	count2 ++;
		}
		fw.close();
    	System.out.println("Time Test end");
	}
	
	public void resizing() throws IOException{
		FileWriter fw = new FileWriter("resizing.csv");
		int size = 200;
		int keynumber = 400;
		Hashtable<Integer,Value> table = new Hashtable<Integer,Value>();
		int count2 = 1;
		while(keynumber>0){
			double loadfactor = (double)table.size()/(double)size;
	
			if(loadfactor<0.95){
				long s = System.nanoTime();
				keynumber--;
				int count = 1;
				Value v = new Value();
				v.setValue( getString(5) );
				int firstkey = hushFunction(v, size);
				int key = firstkey;
				while(table.get(key)!=null){
					key = (firstkey+count)%size;
					count++;
				}
				table.put(key, v);
				long e = System.nanoTime();
				double time =(double) (e-s)/100000;
				fw.write(count2+","+time+"\n");
	        	System.out.println("Adding "+count2+" to hash table "+time);
	        	count2 ++;
			}
			else{
				size =  (int) (size*1.20);
			}
			
		}
		fw.close();
    	System.out.println("Time Test end");
	}
	
	public void resizingByNew() throws IOException{
		FileWriter fw = new FileWriter("newresizing.csv");
		int size = 200;
		int keynumber = 300;
		Hashtable<Integer,Value> table1 = new Hashtable<Integer,Value>();
		Hashtable<Integer,Value> table2 = new Hashtable<Integer,Value>();
		int count2 = 1;
		while(keynumber>0){
			double loadfactor = (double)table1.size()/(double)size;
	
			if(loadfactor<0.95){
				long s = System.nanoTime();
				keynumber--;
				int count = 1;
				Value v = new Value();
				v.setValue( getString(5) );
				int firstkey = hushFunction(v, size);
				int key = firstkey;
				while(table1.get(key)!=null){
					key = (firstkey+count)%size;
					count++;
				}
				table1.put(key, v);
				long e = System.nanoTime();
				double time =(double) (e-s)/100000;
				fw.write(count2+","+time+"\n");
	        	System.out.println("Adding "+count2+" to hash table "+time);
	        	count2 ++;
			}
			else{
				long s = System.nanoTime();
				keynumber--;
				int count = 1;
				Value v = new Value();
				v.setValue( getString(5) );
				int firstkey = hushFunction(v, size);
				int key = firstkey;
				while(table2.get(key)!=null){
					key = (firstkey+count)%size;
					count++;
				}
				table2.put(key, v);
				long e = System.nanoTime();
				double time =(double) (e-s)/100000;
				fw.write(count2+","+time+"\n");
	        	System.out.println("Adding "+count2+" to hash table "+time);
	        	count2 ++;
			}
			
		}
		fw.close();
    	System.out.println("Time Test end");
		
	}
	
	public static void main(String[] args) throws IOException {
		HashTable ht = new HashTable();
		//test the three method
//		ht.testAll(200, 0.25);
		
//		ht.testLoadFactor();
		
//		ht.testSize();
		
//		ht.testResizing();
		
//		ht.resizing();
		
//		ht.resizingByNew();
		
	}
}
