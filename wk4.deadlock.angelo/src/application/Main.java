package application;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/*
 * Three input files, “input1.txt” (N=4, M=3), “input2.txt” (N=4, M=3), “input3.txt” (N=4, M=3). Please
 * choose the input data in such a way that the first input has no solution, the second input has exactly one solution and the third input has at least two solutions.
 */

public class Main {

	// START MESSAGE WHEN THE APPLICATION STARTS.
	private static final String STARTMESSAGE = "Hello, welcome to a deadlock free zone.  Please choose from files: input1.txt, input2.txt, input3.txt\nPlease choose: ";
	private Scanner scanner = new Scanner(System.in);
	// STRING THAT HOLDS THE INPUT FROM THE USER
	private String inputData = "";
	private ArrayList<String> sequenceList = new ArrayList<>();;
	private boolean[] finishList;
	private int numberOfProcess = 0;
	private int numberOfResources = 0;
	private int[][] max;
	private int[][] allocation;
	private HashMap<String, Integer[]> needHash = new HashMap<>();

	public Main() {

	}

	/**
	 * gets the main scanner
	 * 
	 * @return Scanner
	 */
	public Scanner getScanner() {
		return scanner;
	}

	/**
	 * LAUCNHES THE PRGRAMS SIMPLE INTERFACE
	 */
	public String launchProgramStart() {

		System.out.print(STARTMESSAGE);
		String inputData = scanner.nextLine();

		System.out.println();
		System.out.printf("You have chosen: %s\n", inputData);
		this.inputData = "src/application/" + inputData;
		return inputData;

	}

	/**
	 * CHECKS USER INPUT MATCHES WITH THE CORRECT INPUT.TXT 1,2, OR 3
	 * 
	 * @param input
	 * @return boolean
	 */
	public boolean acceptableInputFiles(String input) {

		switch (input) {
		case "input1.txt":
			return true;
		case "input2.txt":
			return true;
		case "input3.txt":
			return true;
		default:
			break;
		}
		return false;

	}

	/**
	 * DISPLAYS SIMPLE INCORRECT INPUT MESSAGE
	 */
	public void incorrectInput() {

		System.out.println();
		System.out.printf("The text file entered %s is not correct.  Please try again.\n", inputData);
		System.out.println();
	}

	/**
	 * READS IN THE INPUT FILE AND CONVERTS ALL THE DATA IN TO ARRAYS AND LISTS FOR
	 * USE
	 * 
	 * @param input
	 * @throws Exception
	 */
	public void readInput(String input) throws Exception {

		// PATH AND READING OF THE INPUT FILE
		Path path = Paths.get(input);
		List<String> file = Files.readAllLines(path);

		// GETS THE NUMBER OF PROCESSES FROM LINE 1
		String temp = file.get(0);
		temp = temp.substring(3, 4);
		numberOfProcess = Integer.parseInt(temp);

		// GETS THE NUMBER OF RESOURCES FROM LINE 1
		temp = file.get(0).substring(8, 9);
		numberOfResources = Integer.parseInt(temp);

		// sets the 2d array for MAX
		setMax(numberOfProcess, numberOfResources, file);

		// Sets allocation
		setAllocation(numberOfProcess, numberOfResources, file);

	}

	/**
	 * Gets the information for the MAX 2D ARRAY
	 * 
	 * @param numberOfProcess
	 * @param numberOfResources
	 * @param file
	 */
	public void setMax(int numberOfProcess, int numberOfResources, List<String> file) {
		// CREATES THE MAX 2D ARRAY
		max = new int[numberOfProcess][numberOfResources];

		// FINDS THE LINES FOR THE VALUES FOR MAX
		int count = 0;
		for (String s : file) {

			if (s.equalsIgnoreCase("MAX:")) {
				count++;
				break;
			}
			count++;
		}

		String[] t = null;
		ArrayList<Integer> convertNumbers = new ArrayList<>();
		// GETS THE LINES FOUND FOR MAX 2D ARRAY AND STORES THEM
		for (int i = count; i < (count + numberOfProcess); i++) {
			t = file.get(i).split(",");
				
			for (int j = 0; j < t.length; j++) {

				String num = t[j];

				try {

					int tempNumber = Integer.parseInt(num);

					convertNumbers.add(tempNumber);

				} catch (Exception e) {

				}

			}

		}

		// PUT THE MAX CONVERTED NUMBERS IN TO THE 2D ARRAY
		int index = 0;
		for (int i = 0; i < numberOfProcess; i++) {

			for (int j = 0; j < numberOfResources; j++) {

				max[i][j] = convertNumbers.get(index);
				index++;
			}

		}

	}

	public void setAllocation(int numberOfProcess, int numberOfResources, List<String> list) {

		allocation = new int[numberOfProcess][numberOfResources];

		int count = 0;
		for (String s : list) {

			if (s.equalsIgnoreCase("ALLOCATION:")) {
				count++;
				break;
			}
			count++;
		}

		ArrayList<Integer> tempAllocationList = new ArrayList<>();
		String[] tmp = null;
		for (int i = count; i < (count + numberOfProcess); i++) {

			tmp = list.get(i).split(",");

			for (String s : tmp) {

				try {

					int number = Integer.parseInt(s);
					tempAllocationList.add(number);

				} catch (Exception e) {

					System.out.println("Not a valid number: " + s);

				}

			}

		}

		try {
			int index = 0;
			for (int i = 0; i < numberOfProcess; i++) {

				for (int j = 0; j < numberOfResources; j++) {

					allocation[i][j] = tempAllocationList.get(index);
					index++;
				}

			}
		} catch (IndexOutOfBoundsException iobe) {
			System.out.println("out of bounds");
		}
		
	
	}

	public static void main(String[] args) {

		Main m = new Main();
		
	
		while (true) {

			while (!m.acceptableInputFiles(m.launchProgramStart())) {

				m.incorrectInput();

			}

			try {
				m.readInput(m.inputData);
				break;
			} catch (Exception e) {

				System.out.println("File Not Found.");

			}

		}//END WHILE
		
		
		//INSTANTIATE AND SET FINISHLIST TO ALL FALSE
		m.finishList = new boolean[m.numberOfProcess];
		
		for(int i = 0; i < m.finishList.length; i++) {
			m.finishList[i] = false;
		}
		
		//SET NEED HASH MAX - ALLOCATOIN
		
		//SET WORK TOTAL RESOURCES - ALLOCATED RESOURCES
		
		//COMPARE NEED HASH TO CURRENT WORK, IF ALL RESOURCES ARE LESS THAN WORK THEN 
		//SET FINISHLIST TO TRUE FOR THAT PROCESS AND ADD IT TO THE SEQUENCE LIST
		//IF NEED IS GREATER THAN WORK MOVE TO THE NEXT PROCESS.  LOOP THROUGH UNTIL ALL
		//PROCESSES HAVE BEEN CHECKED.  IF NO PROCESS NEED IS LESS THAN WORK THEN THERE IS NO SOLUTION.
		

	}

}
