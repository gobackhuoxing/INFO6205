package sorting;

import java.io.FileWriter;
import java.io.IOException;

public class Sorting {
	
	public int[] A;
	public static int[] merge;
	
	//method to show the current list
	public void showList(int[] arr){
		for(int i = 0; i<arr.length; i++){
			System.out.print(arr[i]+" ");
		}
		System.out.println(" ");
		if(testList(arr)) System.out.println("List is sorted");
		else System.out.println("List is unsorted");
	}
	
	//test list
	public boolean testList(int[] arr){
		boolean isSorted = true;
		for(int i = 0; i<arr.length-1; i++){
			if(arr[i]>arr[i+1]){
				isSorted = false;
				break;
			}
		}
		return isSorted;
	}
	
	//test if list is half sorted
	public boolean testList1(int[] arr){
		boolean halfSorted = false;
		for (int i = 0; i< arr.length/2-1; i++){
			if(arr[i+1]- arr[i]==1 && arr[i+2] - arr[i+1]==1){
				halfSorted = true;
				break;
			}
		}
		
		
		return halfSorted;
	}
	
	//create a random list
	public void createRList(int length , int inc){
		A = new int[length*inc];
		for(int i = 0; i<length*inc; i++){
			A[i]=i;
		}
		knuthShuffleAll(A);
	}
	
	//create a partially sorted list
	public void createPList(int length , int inc){
		A = new int[length*inc];
		for(int i = 0; i<length*inc; i++){
			A[i]=i;
		}
		knuthShufflePart(A);
	}
		
	//create a fully sorted list
	public void createFList(int length , int inc){
		A = new int[length*inc];
			for(int i = 0; i<length*inc; i++){
				A[i]=i;
			}
	}
	
	// shuffle all
	public void knuthShuffleAll(int[] arr){
		for(int i = 0; i <arr.length; i++){
			int ran = (int)(Math.random()*i);
			exch(arr, i, ran);
		}
	}
	
	// shuffle part
		public void knuthShufflePart(int[] arr){
			int k = (int) (arr.length/2);
			for(int i = k; i <arr.length; i++){
				int ran = (int)((Math.random()*(i-50))+50);
				exch(arr, i, ran);
			}
		}
	
	//exch two element
	public void exch(int[] arr, int i , int j ){
		int temp = arr[j];
		arr[j] = arr[i];
		arr[i] = temp;
	}
	
///////////////////////////Quick Sort/////////////////////////////////
	public void quickSort(int[] arr){
		quicksort(arr, 0, arr.length-1);
	}
	
	public int partition(int[] arr, int l, int r){
		int i = l;
		int j = r+1;
		while(true){
			while(arr[l]>arr[++i]){
				if(i==r) break;
			}
			while(arr[l]<arr[--j]){
				if(j==l) break;
			}
			
			if(i>=j) break;
			exch(arr, i, j);
		}
		exch(arr,l,j);
		return j;
	}
	
	public void quicksort(int[] arr, int l, int r){
		if (l>=r) return;
		int j = partition(arr, l ,r);
		quicksort(arr,l, j-1);
		quicksort(arr, j+1, r);
	}
//////////////////////////////////////////////////////////////////////
	
///////////////////////////Merge Sort/////////////////////////////////
	public void mergeSort(int[] arr){
		merge = new int[arr.length];
		mergeSort(arr, 0 ,arr.length-1);
	}

	public void mergeSort(int[] arr, int l, int r){
		if(r<=l) return;
		int m = l+(r-l)/2;
		mergeSort(arr, l, m);
		mergeSort(arr, m+1, r);
		merge(arr, l , m , r );
	}
	
	public void merge(int[] arr, int l, int m, int r){
		int i = l;
		int j = m +1;
		for(int k = l; k<=r; k++){
			merge[k] = arr[k];
		}
		for(int k = l; k<=r; k++){
			if (i >m) arr[k] = merge[j++];
			else if(j>r) arr[k] = merge[i++];
			else if(merge[j]<merge[i]) arr[k] = merge[j++];
			else arr[k] = merge[i++];
		}
	}
//////////////////////////////////////////////////////////////////////
	
///////////////////////////Insertion Sort/////////////////////////////
	public void insertionSort(int[] arr){
		int n = arr.length;
		for (int i = 0; i < n; i++){
			for (int j = i; j > 0; j--){
				if (arr[j]< arr[j-1]) exch(arr, j, j-1);
				else break;
			}
		}
	}
//////////////////////////////////////////////////////////////////////
	
///////////////////////////Selection Sort/////////////////////////////
	public void selectionSort(int[] arr){
		int n = arr.length;
		for (int i = 0; i < n; i++){
			int min = i;
			for (int j = i+1; j < n; j++){
				if (arr[j]<arr[min]) min = j;
			}
			exch(arr, i, min);
		}
	}
//////////////////////////////////////////////////////////////////////
	
///////////////////////////Heapsort Sort//////////////////////////////
	public void heapSort(int[] arr){
        int n = arr.length;
        for (int i = n / 2 - 1; i >= 0; i--){
            heapify(arr, n, i);
        }
        for (int i=n-1; i>=0; i--){
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            heapify(arr, i, 0);
        }
    }
 
    public void heapify(int arr[], int n, int i) {
        int largest = i;  
        int l = 2*i + 1;  
        int r = 2*i + 2;  
        
        if (l < n && arr[l] > arr[largest]) largest = l;

        if (r < n && arr[r] > arr[largest]) largest = r;
 
        if (largest != i){
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;
            heapify(arr, n, largest);
        }
    }
//////////////////////////////////////////////////////////////////////

    public void testforQ1(int length){
    	createRList(length,100);
    	System.out.println("The initial list:");
    	showList(A);
    	System.out.println(" ");
    	
    	int[] a1 = new int[A.length];
    	int[] a2 = new int[A.length];
    	int[] a3 = new int[A.length];
    	int[] a4 = new int[A.length];
    	int[] a5 = new int[A.length];
    	for(int i =0;i<A.length;i++){
    		a1[i] = A[i];
    	}
    	for(int i =0;i<A.length;i++){
    		a2[i] = A[i];
    	}
    	for(int i =0;i<A.length;i++){
    		a3[i] = A[i];
    	}
    	for(int i =0;i<A.length;i++){
    		a4[i] = A[i];
    	}
    	for(int i =0;i<A.length;i++){
    		a5[i] = A[i];
    	}
    	
    	System.out.println("Quick Sort:");
    	quickSort(a1);
    	showList(a1);
    	System.out.println(" ");
    	
    	System.out.println("Insertion Sort:");
    	insertionSort(a2);
    	showList(a2);
    	System.out.println(" ");
    	
    	System.out.println("Selection Sort:");
    	selectionSort(a3);
    	showList(a3);
    	System.out.println(" ");
    	
    	System.out.println("Merge Sort:");
    	mergeSort(a4);
    	showList(a4);
    	System.out.println(" ");
    	
    	System.out.println("Heap Sort:");
    	heapSort(a5);
    	showList(a5);
    }
	
    public void testplist(int length){
    	createPList(length,100);
    	System.out.println("The initial list:");
    	showList(A);
    	System.out.println(" ");
    	
    	int[] a1 = new int[A.length];
    	int[] a2 = new int[A.length];
    	int[] a3 = new int[A.length];
    	int[] a4 = new int[A.length];
    	int[] a5 = new int[A.length];
    	for(int i =0;i<A.length;i++){
    		a1[i] = A[i];
    	}
    	for(int i =0;i<A.length;i++){
    		a2[i] = A[i];
    	}
    	for(int i =0;i<A.length;i++){
    		a3[i] = A[i];
    	}
    	for(int i =0;i<A.length;i++){
    		a4[i] = A[i];
    	}
    	for(int i =0;i<A.length;i++){
    		a5[i] = A[i];
    	}
    	
    	System.out.println("Quick Sort:");
    	quickSort(a1);
    	showList(a1);
    	System.out.println(" ");
    	
    	System.out.println("Insertion Sort:");
    	insertionSort(a2);
    	showList(a2);
    	System.out.println(" ");
    	
    	System.out.println("Selection Sort:");
    	selectionSort(a3);
    	showList(a3);
    	System.out.println(" ");
    	
    	System.out.println("Merge Sort:");
    	mergeSort(a4);
    	showList(a4);
    	System.out.println(" ");
    	
    	System.out.println("Heap Sort:");
    	heapSort(a5);
    	showList(a5);
    }
    
    public void testflist(int length){
    	createFList(length,100);
    	System.out.println("The initial list:");
    	showList(A);
    	System.out.println(" ");
    	
    	int[] a1 = new int[A.length];
    	int[] a2 = new int[A.length];
    	int[] a3 = new int[A.length];
    	int[] a4 = new int[A.length];
    	int[] a5 = new int[A.length];
    	for(int i =0;i<A.length;i++){
    		a1[i] = A[i];
    	}
    	for(int i =0;i<A.length;i++){
    		a2[i] = A[i];
    	}
    	for(int i =0;i<A.length;i++){
    		a3[i] = A[i];
    	}
    	for(int i =0;i<A.length;i++){
    		a4[i] = A[i];
    	}
    	for(int i =0;i<A.length;i++){
    		a5[i] = A[i];
    	}
    	
    	System.out.println("Quick Sort:");
    	quickSort(a1);
    	showList(a1);
    	System.out.println(" ");
    	
    	System.out.println("Insertion Sort:");
    	insertionSort(a2);
    	showList(a2);
    	System.out.println(" ");
    	
    	System.out.println("Selection Sort:");
    	selectionSort(a3);
    	showList(a3);
    	System.out.println(" ");
    	
    	System.out.println("Merge Sort:");
    	mergeSort(a4);
    	showList(a4);
    	System.out.println(" ");
    	
    	System.out.println("Heap Sort:");
    	heapSort(a5);
    	showList(a5);
    }
    
    public void testforQ2(int length){
    	System.out.println("Random list:");
    	testforQ1(length);
    	System.out.println(" ");
    	
    	System.out.println("Partially sorted list:");
    	testplist(length);
    	System.out.println(" ");
    	
    	System.out.println("Fully sorted list:");
    	testflist(length);
    }
    
    public void test1forQ3() throws IOException{
    	FileWriter fw1 = new FileWriter("merge R.csv");
    	FileWriter fw2 = new FileWriter("quick R.csv");
    	FileWriter fw3 = new FileWriter("heap R.csv");
    	FileWriter fw4 = new FileWriter("selection R.csv");
    	FileWriter fw5 = new FileWriter("insertion R.csv");
    	
    	for(int j = 1 ; j<=100; j++){
    		
    		
        	
        	double sum1= 0;
        	for(int i =0; i<10; i++){
        		createRList(j,10000);
	        	long s1 = System.nanoTime();
	        	mergeSort(A);
	        	long e1 = System.nanoTime();
	        	double merge =(double) (e1-s1)/100000;
	        	sum1= sum1+merge;
        	}
        	double merge = sum1/10;
        	fw1.write(j+","+merge+"\n");
        	System.out.println(j+" Time Test for meger "+merge);
        	
        	double sum2= 0;
        	for(int i =0; i<10; i++){
        		createRList(j,10000);
	        	long s2 = System.nanoTime();
	        	quickSort(A);
	        	long e2 = System.nanoTime();
	        	double quick =(double) (e2-s2)/100000;
	        	sum2=sum2+quick;
        	}
        	double quick = sum2/10;
        	fw2.write(j+","+quick+"\n");
        	System.out.println(j+" Time Test for quick "+quick);
        	
        	double sum3= 0;
        	for(int i =0; i<10; i++){
        		createRList(j,10000);
	        	long s3 = System.nanoTime();
	        	heapSort(A);
	        	long e3 = System.nanoTime();
	        	double heap =(double) (e3-s3)/100000;
	        	sum3 = sum3+heap;
        	}
        	double heap = sum3/10;
        	fw3.write(j+","+heap+"\n");
        	System.out.println(j+" Time Test for heap "+heap);
        	
        	// insertion and selection
        	if(j<=20){
        		createRList(j,10000);
	        	long s4 = System.nanoTime();
	        	selectionSort(A);
	        	long e4 = System.nanoTime();
	        	double sel =(double) (e4-s4)/100000;
	        	fw4.write(j+","+sel+"\n");
	        	System.out.println(j+" Time Test for sel "+sel);
	        	
	        	createRList(j,10000);
	        	long s5 = System.nanoTime();
	        	insertionSort(A);
	        	long e5 = System.nanoTime();
	        	double ins =(double) (e5-s5)/100000;
	        	fw5.write(j+","+ins+"\n");
	        	System.out.println(j+ " Time Test for ins "+ins);
        	}
    	}
    	fw1.close();
    	fw2.close();
    	fw3.close();
    	fw4.close();
    	fw5.close();
    	System.out.println("Time Test end");
    }
    
    public void test2forQ3() throws IOException{
    	FileWriter fw1 = new FileWriter("merge P.csv");
    	FileWriter fw2 = new FileWriter("quick P.csv");
    	FileWriter fw3 = new FileWriter("heap P.csv");
    	FileWriter fw4 = new FileWriter("selection P.csv");
    	FileWriter fw5 = new FileWriter("insertion P.csv");
    	
    	for(int j = 1 ; j<=100; j++){
    		
    		
        	
        	double sum1= 0;
        	for(int i =0; i<10; i++){
        		createPList(j,10000);
	        	long s1 = System.nanoTime();
	        	mergeSort(A);
	        	long e1 = System.nanoTime();
	        	double merge =(double) (e1-s1)/100000;
	        	sum1= sum1+merge;
        	}
        	double merge = sum1/10;
        	fw1.write(j+","+merge+"\n");
        	System.out.println(j+" Time Test for meger "+merge);
        	
        	double sum2= 0;
        	for(int i =0; i<10; i++){
        		createPList(j,10000);
	        	long s2 = System.nanoTime();
	        	quickSort(A);
	        	long e2 = System.nanoTime();
	        	double quick =(double) (e2-s2)/100000;
	        	sum2=sum2+quick;
        	}
        	double quick = sum2/10;
        	fw2.write(j+","+quick+"\n");
        	System.out.println(j+" Time Test for quick "+quick);
        	
        	double sum3= 0;
        	for(int i =0; i<10; i++){
        		createPList(j,10000);
	        	long s3 = System.nanoTime();
	        	heapSort(A);
	        	long e3 = System.nanoTime();
	        	double heap =(double) (e3-s3)/100000;
	        	sum3 = sum3+heap;
        	}
        	double heap = sum3/10;
        	fw3.write(j+","+heap+"\n");
        	System.out.println(j+" Time Test for heap "+heap);
        	
        	// insertion and selection
        	if(j<=20){
        		createPList(j,10000);
	        	long s4 = System.nanoTime();
	        	selectionSort(A);
	        	long e4 = System.nanoTime();
	        	double sel =(double) (e4-s4)/100000;
	        	fw4.write(j+","+sel+"\n");
	        	System.out.println(j+" Time Test for sel "+sel);
	        	
	        	createPList(j,10000);
	        	long s5 = System.nanoTime();
	        	insertionSort(A);
	        	long e5 = System.nanoTime();
	        	double ins =(double) (e5-s5)/100000;
	        	fw5.write(j+","+ins+"\n");
	        	System.out.println(j+ " Time Test for ins "+ins);
        	}
    	}
    	fw1.close();
    	fw2.close();
    	fw3.close();
    	fw4.close();
    	fw5.close();
    	System.out.println("Time Test end");
    }
    
    public void test3forQ3() throws IOException{
    	FileWriter fw1 = new FileWriter("merge F.csv");
    	FileWriter fw2 = new FileWriter("quick F.csv");
    	FileWriter fw3 = new FileWriter("heap F.csv");
    	FileWriter fw4 = new FileWriter("selection F.csv");
    	FileWriter fw5 = new FileWriter("insertion F.csv");
    	
    	for(int j = 1 ; j<=100; j++){
    		
    		
        	
        	double sum1= 0;
        	for(int i =0; i<10; i++){
        		createFList(j,10000);
	        	long s1 = System.nanoTime();
	        	mergeSort(A);
	        	long e1 = System.nanoTime();
	        	double merge =(double) (e1-s1)/100000;
	        	sum1= sum1+merge;
        	}
        	double merge = sum1/10;
        	fw1.write(j+","+merge+"\n");
        	System.out.println(j+" Time Test for meger "+merge);
        	
        	/*double sum2= 0;
        	for(int i =0; i<10; i++){
        		createFList(j,10000);
	        	long s2 = System.nanoTime();
	        	quickSort(A);
	        	long e2 = System.nanoTime();
	        	double quick =(double) (e2-s2)/100000;
	        	sum2=sum2+quick;
        	}
        	double quick = sum2/10;
        	fw2.write(j+","+quick+"\n");
        	System.out.println(j+" Time Test for quick "+quick);*/
        	
        	double sum3= 0;
        	for(int i =0; i<10; i++){
        		createFList(j,10000);
	        	long s3 = System.nanoTime();
	        	heapSort(A);
	        	long e3 = System.nanoTime();
	        	double heap =(double) (e3-s3)/100000;
	        	sum3 = sum3+heap;
        	}
        	double heap = sum3/10;
        	fw3.write(j+","+heap+"\n");
        	System.out.println(j+" Time Test for heap "+heap);
        	
        	// insertion and selection
        	if(j<=20){
        		createFList(j,10000);
	        	long s4 = System.nanoTime();
	        	selectionSort(A);
	        	long e4 = System.nanoTime();
	        	double sel =(double) (e4-s4)/100000;
	        	fw4.write(j+","+sel+"\n");
	        	System.out.println(j+" Time Test for sel "+sel);
	        	
	        	createFList(j,10000);
	        	long s5 = System.nanoTime();
	        	insertionSort(A);
	        	long e5 = System.nanoTime();
	        	double ins =(double) (e5-s5)/100000;
	        	fw5.write(j+","+ins+"\n");
	        	System.out.println(j+ " Time Test for ins "+ins);
        	}
    	}
    	fw1.close();
    	fw2.close();
    	fw3.close();
    	fw4.close();
    	fw5.close();
    	System.out.println("Time Test end");
    }
    
    public void testforQ3() throws IOException{
    	test1forQ3();
    	test2forQ3();
    	test3forQ3();
    }
    
    // combine method for fully random
    public void sortforRandom(int[] arr){
    	System.out.println("Using quick sort");
    	quickSort(arr);
    }
    
    //combine method for part randon
    public void sortforPart(int[] arr){
    	if(arr.length<=20000){ 
    		System.out.println("Using heap sort");
    		heapSort(arr);
    	}
    	else{
    		System.out.println("Using merge sort");
    		mergeSort(arr);
    	}
    }
    
    //combine method for fully sorted
    public void sortforFull(int[] arr){
    	System.out.println("Using insertion sort");
    	insertionSort(arr);
    }
    
    //combine method for all
    public void sortforall(int[] arr){
    	if(testList(arr)) sortforFull(arr);
    	else if (testList1(arr)) sortforPart(arr);
    	else sortforRandom(arr);
    }
    
    public void test1forB(){
    	//case1
    	createRList(2,100);
    	System.out.println("Create a random list n = 2");
    	showList(A);
    	sortforall(A);
    	showList(A);
    	System.out.println("");
    	
    	//case2
    	createPList(2,100);
    	System.out.println("Create a part random list n = 2");
    	showList(A);
    	sortforall(A);
    	showList(A);
    	System.out.println("");
    	
    	//case3
//    	createPList(3,10000);
//    	System.out.println("Create a part random list");
//    	showList(A);
//    	sortforall(A);
//    	showList(A);
//    	System.out.println("");
    	
    	//case4
    	createFList(2,100);
    	System.out.println("Create a fully sorted list n = 2");
    	showList(A);
    	sortforall(A);
    	showList(A);
    	System.out.println("");
    }
    
	public static void main(String[] args) throws IOException {
		Sorting sort =new Sorting();
		
//		sort.testforQ1(1);
	
//		sort.testforQ2(1);
		
		sort.testforQ3();

//		sort.test1forB();
	}

}
