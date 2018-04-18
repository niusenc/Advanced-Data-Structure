import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;


public class jobscheduler {
	static void predecessor(int JobId, rbt RBT) {
		int temp;
		if(RBT.contains(JobId) == true) {
			int tempExe = RBT.get(JobId).exeTime;
			int tempTotal = RBT.get(JobId).totalTime;
			RBT.delete(JobId);
			temp = RBT.floor(JobId);
			RBT.put(JobId, tempExe, tempTotal);
			if(temp == 0) {
				System.out.println("(" + temp +"," + 0 + "," + 0 + ")");
			}

			else {
				System.out.println("(" + temp +"," + RBT.get(temp).exeTime + "," + RBT.get(temp).totalTime + ")");
			}
			return;
		}

		else {
				temp = RBT.floor(JobId);
				if(temp == 0) {
				System.out.println("(" + temp +"," + 0 + "," + 0 + ")");
			}

			else {
				System.out.println("(" + temp +"," + RBT.get(temp).exeTime + "," + RBT.get(temp).totalTime + ")");
			}
			return;
		}

	}


	static void successor(int JobId, rbt RBT) {
		int temp;
		if(RBT.contains(JobId) == true) {
			int tempExe = RBT.get(JobId).exeTime;
			int tempTotal = RBT.get(JobId).totalTime;
				RBT.delete(JobId);
				temp = RBT.ceiling(JobId);
				RBT.put(JobId, tempExe, tempTotal);
				if(temp == 0) {
					System.out.println("(" + temp +"," + 0 + "," + 0 + ")");
				}

				else {
					System.out.println("(" + temp +"," + RBT.get(temp).exeTime + "," + RBT.get(temp).totalTime + ")");
				}
				return;
			}
			

		else {
				temp = RBT.ceiling(JobId);
				if(temp == 0) {
				System.out.println("(" + temp +"," + 0 + "," + 0 + ")");
			}

			else {
				System.out.println("(" + temp +"," + RBT.get(temp).exeTime + "," + RBT.get(temp).totalTime + ")");
			}
			return;
		}

	}

	static int getSuccId(int JobId, rbt RBT) {
		int temp;
		if(RBT.contains(JobId) == true) {
			int tempExe = RBT.get(JobId).exeTime;
			int tempTotal = RBT.get(JobId).totalTime;
			RBT.delete(JobId);
			temp = RBT.ceiling(JobId);
			RBT.put(JobId, tempExe, tempTotal);
			return temp;
		}

		else {
				temp = RBT.ceiling(JobId);
				return temp;
		}

	}


	static int getPreviousId(int JobId, rbt RBT) {
		int temp;
		if(RBT.contains(JobId) == true) {
			int tempExe = RBT.get(JobId).exeTime;
			int tempTotal = RBT.get(JobId).totalTime;
			RBT.delete(JobId);
			temp = RBT.floor(JobId);
			RBT.put(JobId, tempExe, tempTotal);
			return temp;
		}

		else {
				temp = RBT.floor(JobId);
				return temp;
		}
	}


	


	static void Range(int lowId, int highId, rbt RBT) {
		/*both low and high in*/
		if(RBT.contains(lowId) == true && RBT.contains(highId) == true) {
			int temp = lowId;
			System.out.println("(" + lowId +"," + RBT.get(lowId).exeTime + "," + RBT.get(lowId).totalTime + ")");
			while(true) {
				if(getSuccId(temp,RBT) < highId && getSuccId(temp,RBT) != 0) {
					temp = getSuccId(temp,RBT);
					System.out.print("(" + temp +"," + RBT.get(temp).exeTime + "," + RBT.get(temp).totalTime + ")" + ",");
				}
				else if(getSuccId(temp,RBT) == highId && getSuccId(temp,RBT) != 0) {
					temp = getSuccId(temp,RBT);
					System.out.println("(" + temp +"," + RBT.get(temp).exeTime + "," + RBT.get(temp).totalTime + ")");
				}

				else return;
			}
		}

		/*low in high out*/
		else if(RBT.contains(lowId) == true && RBT.contains(highId) == false) {
			int temp = lowId;
			System.out.println("(" + lowId +"," + RBT.get(lowId).exeTime + "," + RBT.get(lowId).totalTime + ")");
			while(true) {
				if(getSuccId(temp,RBT) < getPreviousId(highId,RBT) && getSuccId(temp,RBT) != 0) {
					temp = getSuccId(temp,RBT);
					System.out.print("(" + temp +"," + RBT.get(temp).exeTime + "," + RBT.get(temp).totalTime + ")" + ",");
				}

				else if(getSuccId(temp,RBT) == getPreviousId(highId,RBT) && getSuccId(temp,RBT) != 0) {
					temp = getSuccId(temp,RBT);
					System.out.println("(" + temp +"," + RBT.get(temp).exeTime + "," + RBT.get(temp).totalTime + ")");
				}

				else return;
			}
		}

		/*low out high in*/
		else if(RBT.contains(lowId) == false && RBT.contains(highId) == true) {
			int temp = lowId;
			//System.out.println("(" + lowId +"," + RBT.get(lowId).exeTime + "," + RBT.get(lowId).totalTime + ")");
			while(true) {
				if(getSuccId(temp,RBT) < highId && getSuccId(temp,RBT) != 0) {
					temp = getSuccId(temp,RBT);
					System.out.print("(" + temp +"," + RBT.get(temp).exeTime + "," + RBT.get(temp).totalTime + ")" + ",");
				}
				else if(getSuccId(temp,RBT) == highId) {
					temp = getSuccId(temp,RBT);
					System.out.println("(" + temp +"," + RBT.get(temp).exeTime + "," + RBT.get(temp).totalTime + ")");
				}

				else return;
			}
		}

		/*low out high out*/
		if(RBT.contains(lowId) == false && RBT.contains(highId) == false) {
			int temp = lowId;
			int flag = 0;
			//System.out.println("(" + lowId +"," + RBT.get(lowId).exeTime + "," + RBT.get(lowId).totalTime + ")");
			while(true) {
				if(getSuccId(temp,RBT) < getPreviousId(highId,RBT) && getSuccId(temp,RBT) != 0) {
					temp = getSuccId(temp,RBT);
					System.out.print("(" + temp +"," + RBT.get(temp).exeTime + "," + RBT.get(temp).totalTime + ")" + ",");
					flag = 1;
				}

				else if(getSuccId(temp,RBT) == getPreviousId(highId,RBT) && getSuccId(temp,RBT) != 0) {
					temp = getSuccId(temp,RBT);
					System.out.println("(" + temp +"," + RBT.get(temp).exeTime + "," + RBT.get(temp).totalTime + ")");
					flag = 1;
				}

				else {
					if(flag == 0) {
						System.out.println("(" + 0 + "," + 0 + "," + 0 + ")");
					}
					return;
				}
			}
		}
		// if()
		// if()
		// if()

	}

	static int parseJobdId(String str) {
		int parenStart = str.indexOf("(");
		int comma = str.indexOf(",");
		return Integer.parseInt(str.substring(parenStart + 1, comma));
	}

	static int parsetotalTime(String str) {
		int comma = str.indexOf(",");
		int parenEnd = str.indexOf(")");
		return Integer.parseInt(str.substring(comma + 1, parenEnd));
	}

	static void INSERT(String str,minheap heap, rbt RBT) {
		int JobId = parseJobdId(str);
		int totalTime = parsetotalTime(str);
		RBT.put(JobId,0,totalTime);
		heap.insert(JobId,0,totalTime);
	}


	static void PrintSingleJob(String str, rbt RBT) {
		int parenStart = str.indexOf("(");
		int parenEnd = str.indexOf(")");
		int JobId = Integer.parseInt(str.substring(parenStart + 1, parenEnd));
		if(RBT.contains(JobId) == false) {
			System.out.println("(" + 0 +"," + 0 + "," + 0 + ")");
		}

		else {
			System.out.println("(" + JobId +"," + RBT.get(JobId).exeTime + "," + RBT.get(JobId).totalTime + ")");
		}
	}

	static void PrintMultiJob(String str, rbt RBT) {
		int JobID1;
		int JobID2;
		int index = str.indexOf("(");
		int end = str.indexOf(",");
		JobID1 = Integer.parseInt(str.substring(index+1,end));//find first JobId from the command
	    index = str.indexOf(",");
		end = str.indexOf(")");
		JobID2 = Integer.parseInt(str.substring(index+1,end));
		Range(JobID1,JobID2,RBT);
	}

	static void PrintNextJob(String str, rbt RBT) {
		int parenStart = str.indexOf("(");
		int parenEnd = str.indexOf(")");
		int JobId = Integer.parseInt(str.substring(parenStart + 1, parenEnd));
		// if(RBT.contains(JobId) == false) {
		// 	RBT.put(JobId,0,0);
		// }
		successor(JobId,RBT);
		//RBT.delete(JobId);
	}

	static void PrintPreviousJob(String str, rbt RBT) {
		int parenStart = str.indexOf("(");
		int parenEnd = str.indexOf(")");
		int JobId = Integer.parseInt(str.substring(parenStart + 1, parenEnd));
		// if(RBT.contains(JobId) == false) {
		// 	RBT.put(JobId,0,0);
		// }
		predecessor(JobId,RBT);
		//RBT.delete(JobId);
	}


	
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		int counter = 0;
		int index = 0;
		int end = 0;
		int jobID,execute_time,total_time;
		int remainTime = 0;
		int execute_time2;
		String subStr = null;
		String command = null;
		boolean profree = true;
		minheap heap = new minheap();
		rbt RedBlackTree = new rbt();
		ArrayList<Integer> heapRoot = new ArrayList<Integer>();

		// printToFile output = new printToFile();

		try {    			  
				  File file = new File("output_file.txt");		 			 	
				  PrintStream print=new PrintStream("output_file.txt");			 
		          System.setOut(print);  
		    } 

		 catch (FileNotFoundException e) {  
		            e.printStackTrace();  
		    
		    }  



		try {
			InputStream is = new FileInputStream(args[0]);
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String str = null;
			str = reader.readLine();
			index = str.indexOf(" ");
			end = str.indexOf("(");
			subStr = str.substring(0,index - 1);

			while (true) {
				if (counter == Integer.parseInt(subStr))	{
					command = str.substring(index+1,end);					
					switch(command) {
						case "Insert": INSERT(str,heap,RedBlackTree);	
								break;
						case "PrintJob": 	           
							           if(!str.contains(","))
							        	   PrintSingleJob(str,RedBlackTree);   
							            if(str.contains(","))
							        	   PrintMultiJob(str,RedBlackTree);
								break;
						case "NextJob": 
							              PrintNextJob(str,RedBlackTree);
								break;
						case "PreviousJob": 
							              PrintPreviousJob(str,RedBlackTree);
								break;
						default : break;
					}
					str = reader.readLine();
					if(str == null) {
						reader.close();
						break;
						}
					index = str.indexOf(" ");
					end = str.indexOf("(");
					
					subStr = str.substring(0,index - 1);
				}
				counter++;
				 if(profree == true) { 
					 if (heap.size() == 0) {
						 continue;
					 }
					heapRoot = heap.pop();
					execute_time2 = heapRoot.get(1);
					remainTime = heapRoot.get(2) - execute_time2;
					heapRoot.set(1,heapRoot.get(1)+1);
					// if(rbt.findNode(rootOfHeap.get(0), rbt.getRoot()) == null) {
					// 	rbt.insert(rootOfHeap.get(0),rootOfHeap.get(1),rootOfHeap.get(2));// try
					// }
					//rbt.findNode(rootOfHeap.get(0), rbt.getRoot()).exeTime = rootOfHeap.get(1);
					RedBlackTree.get(heapRoot.get(0)).exeTime = heapRoot.get(1);

					/*********modified area**************/
					 if(heapRoot.get(1) - heapRoot.get(2) == 0) {
					 		RedBlackTree.delete(heapRoot.get(0));
					 		continue;
					 }
					 /*************modifiedd area*********/

					profree = false;
				 }
				 else {
					 heapRoot.set(1,heapRoot.get(1)+1);		
					 // if(rbt.findNode(rootOfHeap.get(0), rbt.getRoot()) == null) {
						// 	rbt.insert(rootOfHeap.get(0),rootOfHeap.get(1),rootOfHeap.get(2));// try
						// }
					//rbt.findNode(rootOfHeap.get(0), rbt.getRoot()).exeTime = rootOfHeap.get(1);

					 RedBlackTree.get(heapRoot.get(0)).exeTime = heapRoot.get(1);


					 if(remainTime<=5) {
						 if(heapRoot.get(2)-heapRoot.get(1)==0) {
							 RedBlackTree.delete(heapRoot.get(0));
							 profree = true;	 
						 }
					 }
					 if(remainTime>5) {
						 if(remainTime-(heapRoot.get(2)-heapRoot.get(1))==5) {
							 heap.insert(heapRoot.get(0), heapRoot.get(1), heapRoot.get(2));
							 profree = true;
						 }
					 }
				 }
		  	}
		}

		catch (Exception e) {
			e.printStackTrace();
		}	

		long endTime = System.currentTimeMillis();
		System.out.println("程序运行时间："+(endTime-startTime)+"ms");
		
	}
}