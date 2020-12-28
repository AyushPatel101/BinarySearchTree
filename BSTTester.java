
/* CS 314 STUDENTS: FILL IN THIS HEADER.

 *
 * Student information for assignment:
 *
 *  On my honor, Ayush Patel, this programming assignment is my own work
 *  and I have not provided this code to any other student.
 *
 *  UTEID: ap55837
 *  email address: patayush01@utexas.edu
 *  TA name: Tony
 *  Number of slip days I am using: 1
 */

import java.util.ArrayList;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;
//----------------------------For BST, adding random values----------------------------
//	N(number of elems added)		Average add time 	Average height	Average Size 
//						1000				  .0038s			  20.8			1000
//						2000				  .0028s			  25.2			2000
//  					4000				  .0041s			  28.6			4000
//  					8000				  .0044s			  29.0			8000
//  				   16000				  .0048s			  32.2		 15999.9
//  				   32000        		  .0148s			  35.2		 31999.7
//  			       64000				  .0169s			  37.6		 63999.1
//  			      128000				  .0659s			  39.5		127997.6
// 					  256000				  .1236s			  42.9		255992.2
//  				  512000				  .3070s			  46.1		  511968
//  				 1024000				  .9046s			  49.3	   1023882.5

//-----------For BST, Min heights for each N (assuming all values are unique)-----------
//	N(number of elems added)			  Min height
//						1000				 	   9
//						2000				  	  10
//						4000				  	  11
//						8000				  	  12
//					   16000				      13
//					   32000        		      14
//				       64000				      15
//					  128000				      16
//				      256000				  	  17
//					  512000					  18
//					 1024000				      19
//----------------------------For TreeSet, adding random values----------------------------
//  N(number of elems added)		Average add time 	
// 						1000				  .00131s			  
//						2000				  .00076s			  
//						4000				  .00126s			  
//						8000				  .00360s			  
//				   	   16000				  .00647s			 
//				   	   32000        		  .01473s			 
//			       	   64000				  .01934s		
//			      	  128000				  .06401s			 
//					  256000				  .19427s			
//				 	  512000				  .40144s			  
//					 1024000				  1.0985s		

// Comparing the average time to add random values for BST and TreeSet, TreeSet had lower times up until N = 32000, 
// where both had approximately the same run time (.0148s). From there after, BST had lower run times
//----------------------------For BST, adding sorted values----------------------------
//	N(number of elems added)		Average add time 	Average height	Average Size 
//						1000				  .0026s			   999			1000
//						2000				  .0048s			  1999			2000
//						4000				  .0194s			  3999			4000
//						8000				  .0801s			  7999			8000
//				  	   16000				  .3492s			 15999		   16000
//				  	   32000        		  1.701s			 31999		   32000
//			     	   64000				  6.439s			 63999		   64000
//--------------------------------------Predictions--------------------------------------
//			      	  128000				  ~25.6s			
//					  256000				 ~102.4s
//				 	  512000				 ~409.6s	  
//					 1024000				~1638.4s

//----------------------------For TreeSet, adding sorted values----------------------------
//	N(number of elems added)		Average add time 	
//						1000				  .00016s			  
//						2000				  .00043s			  
//						4000				  .00076s			  
//						8000				  .00153s			  
//			   	  	   16000				  .00241s			 
//			   	  	   32000        		  .00645s			 
//		       	   	   64000				  .00993s		
//		      	  	  128000				  .02243s		 
//					  256000				  .04596s			
//			 	 	  512000				  .10688s
//					 1024000				  .26409s
//
//Comparing the average add times for sorted values between BST and TreeSet, the TreeSet has faster times for every N tested. 
//I think the cause of this difference is the fact that the BST will be completely right sided, meaning it has to traverse the 
//whole tree to find the spot to insert new element. On the other hand, the TreeSet could potentially go backwards, starting 
//from the largest value, which would mean it would find the spot where new value should be inserted immediately
//since adding elements in order). In terms of Big O, the BST add Big O in this case would be O(N), while the TreeSet add would be O(1). 

/**
 * Some test cases for CS314 Binary Search Tree assignment.
 *
 */
public class BSTTester {

	/**
	 * The main method runs the tests.
	 * 
	 * @param args Not used
	 */
	public static void main(String[] args) {
		BinarySearchTree<Integer> t = new BinarySearchTree<>();
		int testNum=0;
		
		//add tests
		t.add(0);
		t.add(-2);
		System.out.println("Test " + ++testNum + " : add method.");
		showTestResults(t.isPresent(0) == true, testNum);
		t.add(-2);
		t.add(0);
		System.out.println("Test " + ++testNum + " : add method.");
		showTestResults(t.isPresent(-2) == true, testNum);
		//remove tests
		t.remove(0);
		System.out.println("Test " + ++testNum + " : remove method.");
		showTestResults(t.isPresent(0) == false, testNum);
		t.remove(-2);
		System.out.println("Test " + ++testNum + " : remove method.");
		showTestResults(t.isPresent(-2) == false,testNum);
		//isPresent tests
		t.add(-1);
		System.out.println("Test " + ++testNum + " : isPresent method.");
		showTestResults(t.isPresent(1) == false, testNum);
		System.out.println("Test " + ++testNum + " : isPresent method.");
		showTestResults(t.isPresent(-1) == true, testNum);
		//size tests
		System.out.println("Test " + ++testNum + " : size method.");
		showTestResults(t.size()== 1, testNum);
		t.add(-1);
		t.add(0);
		System.out.println("Test " + ++testNum + " : size method.");
		showTestResults(t.size()== 2, testNum);
		//height tests
		System.out.println("Test " + ++testNum + " : height method.");
		showTestResults(t.height()== 1, testNum);
		t.add(2);
		t.add(3);
		System.out.println("Test " + ++testNum + " : height method.");
		showTestResults(t.height()== 3, testNum);
		//getAll tests
		List<Integer> expected= new ArrayList<>();
		expected.add(-3);
		expected.add(-2);
		expected.add(-1);
		expected.add(0);
		expected.add(2);
		expected.add(3);
		t.add(-2);
		t.add(-3); 
		System.out.println("Test " + ++testNum + " : getAll method.");
		showTestResults(t.getAll().equals(expected), testNum);
		t.remove(4);
		t.remove(-1);
		expected.remove(2);
		System.out.println("Test " + ++testNum + " : getAll method.");
		showTestResults(t.getAll().equals(expected), testNum);
	
		//max tests
		System.out.println("Test " + ++testNum + " : max method.");
		showTestResults(t.max()==3, testNum);
		t.remove(-3);
		t.remove(-2);
		t.remove(-1);
		System.out.println("Test " + ++testNum + " : max method.");
		showTestResults(t.max()==3, testNum);
		
		//min tests
		System.out.println("Test " + ++testNum + " : min method.");
		showTestResults(t.min()==0, testNum);
		t.add(-10);
		t.add(-9);
		System.out.println("Test " + ++testNum + " : min method.");
		showTestResults(t.min()==-10, testNum);
		//iterativeAdd tests
		t.add(100);
		System.out.println("Test " + ++testNum + " : iterativeAdd method.");
		showTestResults(t.isPresent(100), testNum);
		t.add(-200);
		System.out.println("Test " + ++testNum + " : iterativeAdd method.");
		showTestResults(!t.isPresent(200), testNum);
		//get tests
		System.out.println("Test " + ++testNum + " : get method.");
		showTestResults(t.get(0)== -200, testNum);
		System.out.println("Test " + ++testNum + " : get method.");
		showTestResults(t.get(t.size()-1)== 100, testNum);
		//getAllLessThan tests
		System.out.println("Test " + ++testNum + " : getAllLessThan method.");
		showTestResults(t.getAllLessThan(-200).equals(new ArrayList<Integer>()), testNum);
		System.out.println("Test " + ++testNum + " : getAllLessThan method.");
		expected= new ArrayList<>();
		expected.add(-200);
		expected.add(-10);
		expected.add(-9);
		showTestResults(t.getAllLessThan(0).equals(expected), testNum);
		//getAllGreaterThan tests
		System.out.println("Test " + ++testNum + " : getAllGreaterThan method.");
		showTestResults(t.getAllGreaterThan(100).equals(new ArrayList<Integer>()), testNum);
		System.out.println("Test " + ++testNum + " : getAllGreaterThan method.");
		expected= new ArrayList<>();
		expected.add(2);
		expected.add(3);
		expected.add(100);
		showTestResults(t.getAllGreaterThan(0).equals(expected), testNum);
		//numNodesAtDepth test
		System.out.println("Test " + ++testNum + " : numNodesAtDepth method.");
		showTestResults(t.numNodesAtDepth(2)==3, testNum);
		System.out.println("Test " + ++testNum + " : numNodesAtDepth method.");
		showTestResults(t.numNodesAtDepth(0)==1, testNum);
		
		

		// BST experiment code
		int n = 1000;
		System.out.println("For BST (random)");
		while (n <= 1024000) {
			int run = 0;
			double time = 0;
			double height = 0;
			double size = 0;
			while (run < 10) {
				Random ro = new Random();
				BinarySearchTree<Integer> b = new BinarySearchTree<>();
				Stopwatch watch = new Stopwatch();
				watch.start();
				for (int i = 0; i < n; i++) {
					b.add(ro.nextInt());
				}
				watch.stop();
				time += watch.time();
				height += b.height();
				size += b.size();
				run++;
			}
			System.out.println("For n = " + n);
			System.out.println(
					"The average time over 10 experiment runs: " + time / 10);
			System.out.println("the average height over 10 experiment runs: "
					+ height / 10);
			System.out.println(
					"the average size over 10 experiment runs: " + size / 10);
			n *= 2;
		}
		// TreeSet experiment code
		int nTree = 1000;
		System.out.println("For TreeSet (random)");
		while (nTree <= 1024000) {
			int run = 0;
			double time = 0;
			while (run < 10) {
				Random ro = new Random();
				TreeSet<Integer> b = new TreeSet<>();
				Stopwatch watch = new Stopwatch();
				watch.start();
				for (int i = 0; i < nTree; i++) {
					b.add(ro.nextInt());
				}
				watch.stop();
				time += watch.time();
				run++;
			}
			System.out.println("For n = " + nTree);
			System.out.println(
					"The average time over 10 experiment runs: " + time / 10);
			nTree *= 2;
		}
		ArrayList<Integer> sortedList = new ArrayList<>(1000);
		for (int i = 0; i < 1000; i++) {
			sortedList.add(i);
		}
		// BST sorted experiment code
		int nBST = 1000;
		System.out.println("For BST (sorted)");
		while (nBST <= 128000) {
			int run = 0;
			double time = 0;
			double height = 0;
			double size = 0;
			while (run < 10) {
				BinarySearchTree<Integer> b = new BinarySearchTree<>();
				Stopwatch watch = new Stopwatch();
				watch.start();
				for (int i = 0; i < nBST; i++) {
					b.iterativeAdd(i);
				}
				watch.stop();
				time += watch.time();
				
				run++;
			}
			System.out.println("For n = " + nBST);
			System.out.println(
					"The average time over 10 experiment runs: " + time / 10);
			nBST *= 2;
		}
		// TreeSet sorted experiment code
		int nTree2 = 1000;
		System.out.println("For TreeSet (sorted)");
		while (nTree2 <= 1024000) {
			int run = 0;
			double time = 0;
			while (run < 10) {
				TreeSet<Integer> b = new TreeSet<>();
				Stopwatch watch = new Stopwatch();
				watch.start();
				for (int i = 0; i < nTree2; i++) {
					b.add(i);
				}
				watch.stop();
				time += watch.time();
				run++;
			}
			System.out.println("For n = " + nTree2);
			System.out.println(
					"The average time over 10 experiment runs: " + time / 10);
			nTree2 *= 2;
		}

	}

	private static void showTestResults(boolean passed, int testNum) {
		if (passed) {
			System.out.println("Test " + testNum + " passed.");
		} else {
			System.out.println("TEST " + testNum + " FAILED.");
		}
	}
}
