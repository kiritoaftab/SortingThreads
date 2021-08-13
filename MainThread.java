package threads;
import java.util.Random;
import java.util.Arrays;
import java.util.Scanner;
class QuickSort implements Runnable {
	public static Long startTimeQ,stopTimeQ;
int i,j,temp,pivot,p,r,n;
int[] a=new int[1000];
QuickSort(int[] arr,int p,int r) {
this.a = arr;
this.p=p;
this.r=r;
n=arr.length;
}
public void run() {
System.out.println("\n Thread Name:QuickSort started " );
startTimeQ=System.nanoTime();
quickSort(p,r);
stopTimeQ=System.nanoTime();
System.out.println("\nSorted Array using QuickSort");
for(int i=0;i<n;i++) {
	System.out.print(a[i]+" ");
}
System.out.print("\n");
}
public void quickSort(int p,int r) {
	if (p < r) {
		i = p;
		j = r + 1;
		pivot = a[p]; // mark first element as pivot
		while (true) {
		i++;
		while (a[i] < pivot && i < r)
		i++;
		j--;
		while (a[j] > pivot)
		j--;
		if (i < j) {
		temp = a[i];
		a[i] = a[j];

		a[j] = temp;
		} else
		break; // partition is over

		}
		a[p] = a[j];
		a[j] = pivot;
		quickSort(p, j - 1);
		quickSort(j + 1, r);
	}
		
}


}


class MergeSort implements Runnable {
	public static Long startTimeM,stopTimeM;
int low,high,n;
int[] a=new int[20];
MergeSort(int[] arr,int low, int high) {
this.a=arr;
this.low=low;
this.high=high;
n=arr.length;
}
public void run() {
System.out.println("\nThread Name:Merge Sort started " );
startTimeM=System.nanoTime();
mergeSort(a,low,high);
stopTimeM=System.nanoTime();
System.out.println("Sorted Array using MergeSort");
for(int i=0;i<n;i++) {
	System.out.print(a[i]+" ");
}
System.out.print("\n");
}
public void mergeSort(int[]a,int low,int high) {
	int mid;
	if(low<high)
	{
	mid=(low+high)/2;
	mergeSort(a,low,mid);//recursively sort left part
	mergeSort(a,mid+1,high);//recursively sort right part
	merge(a,low,mid,high); // merge two sorted parts
	}
}
 void merge(int a[],int low, int mid, int high)
{
int i,j,h,k;
int b[]=new int[1000];
h=low; //h points to first element in first half a [low:mid]

i=low;
j=mid+1;//j points to first element in second half a[mid+1:high]
while((h<=mid)&&(j<=high))
{
if(a[h]<a[j])
{
b[i]=a[h];
h=h+1;

}
else
{
b[i]=a[j];
j=j+1;
}
i=i+1;
}
if(h>mid)
{
for(k=j;k<=high;k++)
{
b[i]=a[k];
i=i+1;
}
}
else
{
for(k=h;k<=mid;k++)
{
b[i]=a[k];
i=i+1;
}
}
for(k=low; k<=high;k++)
a[k]=b[k];
}//end of merge
}


class BubbleSort implements Runnable {
public static Long startTimeB,stopTimeB;
int[] a=new int[1000];
int n;
BubbleSort(int[] arr) {
this.a = arr;
n=arr.length;
}
public void run() {
System.out.println("\nThread Name:Bubble Sort " );
startTimeB=System.nanoTime();
bubbleSort(a);
stopTimeB=System.nanoTime();
System.out.println("Sorted Array using BubbleSort");
for(int i=0;i<n;i++) {
	System.out.print(a[i]+" ");
}
System.out.print("\n");
}
 void bubbleSort(int[] arr) {  
      
    int temp = 0;  
     for(int i=0; i < n; i++){  
             for(int j=1; j < (n-i); j++){  
                      if(arr[j-1] > arr[j]){  
                             //swap elements  
                             temp = arr[j-1];  
                             arr[j-1] = arr[j];  
                             arr[j] = temp;  
                     }  
                      
             }  
     }  

}  
}
class RandomThread implements Runnable {
public void run() {
int max=1000;
int n;
int[] arr = new int[max];
Random r = new Random();
Scanner s=new Scanner(System.in);
System.out.println("Enter the size of array");
n=s.nextInt();
//making Array of n random Integers
for(int i=0;i<n;i++) {
	arr[i]=r.nextInt(1000);
}
arr=Arrays.copyOf(arr, n);//to remove any zeros generated using Random 
Arrays.sort(arr);
try {

System.out.println("Main Thread running, Generated Array is");
for(int i=0;i<n;i++) {
	System.out.print(arr[i]+" ");
}
System.out.print("\n");
Thread t2 = new Thread (new QuickSort(arr,0,n-1));
t2.start();//Creating Thread QuickSort
t2.sleep(1000);
Thread t3 = new Thread(new BubbleSort(arr));
t3.start();
t3.sleep(1000);
Thread t4=new Thread(new MergeSort(arr,0,n-1));
t4.start();
t4.sleep(1000);
Thread.sleep(1000); //Thread sleeps for 1 second
System.out.println("\n--------------------------------------");

Long elapsedQ=QuickSort.stopTimeQ-QuickSort.startTimeQ;
System.out.println("Time Complexity for n="+n+" QuickSort in ms is "+(double)elapsedQ/1000000);
Long elapsedM=MergeSort.stopTimeM-MergeSort.startTimeM;
System.out.println("Time Complexity for n="+n+" MergeSort in ms is "+(double)elapsedM/1000000);
Long elapsedB=BubbleSort.stopTimeB-BubbleSort.startTimeB;
System.out.println("Time Complexity for n="+n+"BubbleSort in ms is "+(double)elapsedB/1000000);
} catch (Exception ex) {
System.out.println("Interrupted Exception");
}
}
}


public class MainThread {
public static void main(String[] args) {
RandomThread thread_obj = new RandomThread();
Thread t1 = new Thread(thread_obj);
t1.start();
}
}