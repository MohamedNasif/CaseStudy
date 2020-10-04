package FEU;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class AccountClass  extends OperatingSystem{

	/**
	 * Name:Andrew Balmakund
	 * 
	 * Date: 01/15/2018
	 * 
	 * Purpose: This class is used to to essentially validate the user credtionals, mostly the password to see if it is secure enough to prevent hackers from accessing the accuont easily
	 * This class also checks to see if the saveData file has been tempered with, if there is any slight change to the saveData txt file and backUPData txt file, then the computer has seem to be infected with a malware.
	 * It also has a loading screen, that will be used to help decipher the difference between a safe and protected computer vs. a infected computer (a computer with a virus)
	 * In addition to the loading screens, there is a recursive implentation of reversing a string using a 2D String array
	 */
	
	
	
	
	private String userName;
	private String userPassword;
	private boolean  secure;

	private String userAccountHolder;
	

	
	
	public AccountClass() throws IOException, InterruptedException{
		
		
		numBasedOnLevelOfSecurity = -1;
		
		
		String[] userCreditionals = new String[2];
		
		
		isEncrypted = checkEncryption();	//checking to see if the backUp is matched with the saveData
	
		
		
		
		BufferedReader findPassword = new BufferedReader(new FileReader("determineIfLoginProceeded.txt"));
		userCreditionals = loadArrayFromFile(userCreditionals, findPassword);
		
		this.userName = userCreditionals[0];
		this.userPassword = userCreditionals[1];
	
	
		
		numBasedOnLevelOfSecurity = (validiateUserPassword(this.userPassword));
		
		
			
	
	}
	
	
	//The following is setters and gettors
	public int getLevelOfSecurity(){
		return numBasedOnLevelOfSecurity;
	}
	
	public void setLevelOfSecurity(int numBasedOnLevelOfSecurity){
		this.numBasedOnLevelOfSecurity = numBasedOnLevelOfSecurity;
	}

	
	
	
	
	public int validiateUserPassword(String password) throws UnsupportedEncodingException{
		
		/*
		 * Description - This method is used to take a String data and determine if the password is valid based on the following sequence, if the password is greater 
		 * than 6 characters, and if it contains at least one special character. This method will return a number based on if the String data has met the criteria
		 * 
		 * 1 is returned that if the password is valid
		 * 0 is returned that if the password is not valid thus allowing the computer to be vulnerable
		 * -1 is returned that if anything, password happens to be null
		 */
		
		password = this.userPassword;
		numBasedOnLevelOfSecurity = -1;
		
	
	
		boolean checkForSpecialChar = false;
		
		try{
			byte[] bytes = password.getBytes("US-ASCII");
			
			for (int index = 0; index < bytes.length; index++) {
				if (bytes[index] > 32 && bytes[index] < 65 ) {
					//System.out.println("hello");
					checkForSpecialChar = true;
					//System.out.println(password + "*");
					break;
					// System.out.printf(" %c \n ", index , index);
				}
			}
			
			if (password.length() < 6 ||checkForSpecialChar == false) {
				
				
				numBasedOnLevelOfSecurity = 0;
			
				
				System.out.println("Your password appears to be less than 6 characters long, usually secured websites will advise you to have a minimum of 8 characters (some allow 6 and above)");
			} else if (password.length() >= 6 && checkForSpecialChar ==true){
			numBasedOnLevelOfSecurity = 1;
			}
			
			if (password.equals(null)){
				numBasedOnLevelOfSecurity = -1;
			}
		
		} catch (NullPointerException e){
			
		}
		
	
		
		return numBasedOnLevelOfSecurity;
	
	}
	public static boolean checkEncryption() throws IOException, NullPointerException{
      	/*
      	 * Description - This method is to check whether or not the saveData.txt file has been infected with malware or not. To determine this, the saveData.txt file will
      	 * be compared to the backUp.txt file. If they are not similar, this method will return a boolean value of true. If it is similar then it will return false.
      	 */
      	BufferedReader saveData = new BufferedReader(new FileReader("saveData1.txt"));
      	BufferedReader backUp = new BufferedReader(new FileReader("backUp1.txt"));
      	
      	String[] saveDataArray = new String[50];
      	String[] backUpArray = new String[50];
      	
      	saveDataArray = loadArrayFromFile(saveDataArray, saveData);
      	backUpArray = loadArrayFromFile(backUpArray, backUp);
      	
      	boolean checkData = false;
      	int checkCount = 0;
      	
      	for (int index = 0; index < saveDataArray.length; index++){
      		//The following saveDataArray[index] == null is used to disregard nullPointerException that occurs when the saveData.txt or backUp.txt files are empty (which tells java they are null). 
      		if (saveDataArray[index] == null){
      			
      		}
      		else if (!saveDataArray[index].equals(backUpArray[index])){
                   	
                   	checkCount++;
             	}
      	}
      	
      	if (checkCount == 50){
             	
             	checkData = true;
      	}
      	else{
             	checkData = false;
      	}
      	return checkData;
      	
	}//Method - checkEncryption
	public void loadScreenBeforeInfected() throws InterruptedException{
		/*
		 * Description - this method is use as a design effect to look over and create a special loading screen, This method is speicfically designed for the purpose
		 * of a computer that has not been infected. This effect will just be demonstrating by showcasing pascals triangle as part of its loading screen
		 */
		
		JFrame frame = new JFrame("Loading screen...");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel();
		panel.setLayout(null);
		frame.setSize(600,400);
		
		
		frame.add(panel);
		frame.setVisible(true);	
		
		
		JLabel welcomeUserMessage  = new JLabel("Loading Windows 10" );
		welcomeUserMessage.setBounds(10,10,600,40);
		panel.add(welcomeUserMessage);
		
		
		
		JTextArea loadingScreen = new JTextArea();
		loadingScreen.setBounds(10,70,580,580);
		panel.add(loadingScreen);
		frame.add(panel);
		frame.setVisible(true);	
		
		
		int pascalRowNum = 10;

		
		String[][] answerList = pascalsTriangle(pascalRowNum);
		
		for (int row = 1; row < answerList.length; row++) {
			
			 for (int index = answerList.length - row; index > 0; index--) {
	
				 loadingScreen.append("   ");
			    }
			
			for (int col = 0; col < answerList[row].length; col++) {
				 Thread.sleep(20);
				loadingScreen.append(answerList[row][col] + " ");
			}
		
			loadingScreen.append("\n");
			
		}
	
		
		
		frame.add(panel);
		frame.setVisible(true);	
		
		Thread.sleep(1000);
		frame.setVisible(false);	
	}
	
	public void loadScreenAfterInfected() throws InterruptedException{
		
		/*
		 * Description - The following method is used as a design to perform or act as a loading screen for when the program starts up. This specific loading screen
		 * is used to demonstrate what a infected computer would look like. THis method will create a jframe that would produce a series of random numbers between
		 * 1 or 0 to showcase an odd loading screen.
		 */
		
		JFrame frame = new JFrame("Loading screen...");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel();
		panel.setLayout(null);
		frame.setSize(600,400);
		
		
		
		//The following will just take a simple String and reverse it recurssively and then add it to a JLabel
		String welcomeMessage = "Loading % Windows % 10 $";
		welcomeMessage = setUp2DArrayForReversingRecursively(welcomeMessage);

		
		
		JLabel welcomeUserMessage  = new JLabel(welcomeMessage );
		welcomeUserMessage.setBounds(10,10,600,40);
		panel.add(welcomeUserMessage);
		
		
		JTextArea loadingScreen = new JTextArea();
		loadingScreen.setBounds(10,	70,600,330);
		panel.add(loadingScreen);
		frame.add(panel);
		frame.setVisible(true);	
		
		
		

		
		Random random = new Random();
		
		
		for (int row = 0; row < 80; row++) {
			
			for (int col = 0; col < 80; col++){
			Thread.sleep(1);	
				int num = random.nextInt(2) + 0;
				loadingScreen.append(""+ num);
			}
			
			loadingScreen.append("\n");
			
		}
	
		
		
		frame.add(panel);
		frame.setVisible(true);	
		
		Thread.sleep(1000);
		frame.setVisible(false);
	}
	
	public static String setUp2DArrayForReversingRecursively(String message){
		/*
		 * Description - This method is used to setup 2D array that will be used to help reverse the string recurssively through a 2D array of type String, it will take a following String
		 * and then return the reverse String
		 * 
		 * 
		 * This 2D array is setup on this basis...
		 * 
		 * data[0] (orignial String) = is each chacter is a index in this first row
		 * data[1] = is the counter value that starts at the end of the index, this index will decrease in value until it reaches zero
		 * data[2] (new String) = each chacter will be in the reverse order of the original string
		 * data[3] = is the counter value that starts from zero and works it way up to the length of the string minus one
		 */
		
		
		String[][] data = new String[4][];
		
		data[0] = new String[message.length()];
		data[1] = new String[1];
		
		data[2] = new String[message.length()];
		data[3] = new String[1];
		
		data[3][0] = "" + 0;
		data[1][0] = ""+(message.length()-1);
		
		for (int index = 0; index < message.length(); index++){
			data[0][index] = ""+message.charAt(index);
		}
		
		data = reverseString2DArrayRecursive(data);

		String newString = "";
		
		for (int index = 0; index < message.length(); index++){
			//System.out.print(data[2][index]);
			newString += data[2][index];
		}
		
		return newString;
	}
	
	public static String[][] reverseString2DArrayRecursive(String[][] data){
		/*
		 * Description - THis method is meant to take a 2D String array and reverse the elements in the first row and update that reverse row in the third row in the 2D
		 * array. This method does this recursively. 
		 * 
		 * For example the call to this method...
		 * 
		 * data[0] = 12345
		 * data[1] = 4 (Represents the last index of data[0])
		 * 
		 * data[2] = 51234 
		 * data[3] = 0
		 * 
		 */
		
		
		
		//Meant to count back from 5,4,3,2,1 ...
		int reverseCounter = Integer.parseInt(data[1][0]);
		
		//Meant to count upwards from 1,2,3,4,5 ...
		int indexCounter = Integer.parseInt(data[3][0]);
		
		if (reverseCounter ==0){
			data[2][indexCounter] = data[0][reverseCounter];
			return data;
			
		}else {
			reverseCounter = Integer.parseInt(data[1][0]);
			
			data[2][indexCounter] = data[0][reverseCounter];
			
			
			//The following counters are used to help direct the method in reversing the index of the string, 
			//Reverse counter, counts downards for the original string. Index counter, counts  upwards for the new String (this is a way of positioning the characters in reverse order)
			
			reverseCounter -=1;
			data[1][0] = ""+reverseCounter;
			
			indexCounter+=1;
			data[3][0] =indexCounter+"";
			
			return reverseString2DArrayRecursive(data);
		}
	}
	
	public String toString(){
		/*
		 * Description - This method is a toString to showcase or give this accountClass (a object) a specific meaning of it. With that being said, it represents
		 */
		
		if (this.getLevelOfSecurity() == 1){
			return ("This Account is: SAFE"  );
		}else if (this.getLevelOfSecurity()== 0){
			return ("This Account is: UNSecure"  );
		}else{
			return ("This Account is: N/A"  );
		}
		
	}
}
