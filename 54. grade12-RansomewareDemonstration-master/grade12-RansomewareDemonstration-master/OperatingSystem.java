package FEU;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.*;
public class OperatingSystem {
	
	
	/**
	 * Name:Andrew Balmakund
	 * 
	 * Date: 01/15/2018
	 * 
	 * Purpose: THis class is designed to act as and simualte a real feel to a desktop experince on a windows device. To be more exact, there is To demonstrate the effects of what dangerous malware can do to one's computer. The solution to this will be adding a kill switch software that gives the user the options to format (wipe all the files of the system) or restore from a previous backup. In addition to the back up, as the user shuts down, their will be a backup sequence happening to ensure that in a crisis event that something happens to their computer, they can safely approach the kill switch button and get their computer back to the day it was before. 
	In addition to the effects, their will be a task manager used to provide the user with information in their analysis of troubleshooting and fixing their computer
	Password combination generator (usually this in for some circumstance where hackers can easily get into an individual's account and attempt to install this malware). This idea then leads me into talking to the user about changing their password such that it meets different criteria (such as having a specific amount of characters, having at least one special character and so on). 

	 */
	
	private static JFrame frame;
	
	private static  String userName;
	private static String userPassword;
	
	private static String[] backUpTimes;
	private static int backUpCount;
	
	public static boolean userCredientialsExist;
	
	protected static int  numBasedOnLevelOfSecurity;
	
	private static String[] saveData;
	
	public static Scanner input = new Scanner(System.in);
	
	public static String[][] duplicateList;
	public static int counterForDuplicateList;
	
	public static boolean isAccountInfected = false;
	
	protected static boolean trackGoogleChromeAction;
	
	private static BufferedReader passwordFile;
	private static String[] passwordArray = new String[10];
	
	protected static boolean isEncrypted;
	
	
  	public OperatingSystem() throws IOException, InterruptedException{
		 frame = new JFrame ("Operating System");
		 
		
		 
		//LOADING THE FOLLOWING INTO A string array of passwordArray - this is used to help solve several probelms occured throughoutt he program
		  this.passwordFile = new BufferedReader(new FileReader("PasswordFiles.txt"));
		 this.passwordArray = loadArray(passwordArray, passwordFile);
		
		 
	}
  	
  	
	
	//The following are getter and settor methods
	public String getUserName(){
		return userName;
	}
	
	public void setUserName(String userName){
		this.userName = userName;
	}
	
	
	public String getUserPassword(){
		return userPassword;
	}
	
	public void setUserPassword(String userPassword){
		this.userPassword = userPassword;
	}
	
	public int getSecureNumber(){
		return numBasedOnLevelOfSecurity;
	}
	
	public void setSecureNumber(int secureAccount){
		this.numBasedOnLevelOfSecurity = secureAccount;
	}
	
	
	
	public void findUserNameAndPassword() throws IOException{
		/*
		 * Description - the following method is used to look speicfically for the username and password that correspond to 2 diffent text file, this method check to see that if the user enters 
		 * a specific username, this method is to ensure that the coresponding password must math the users input
		 */
		BufferedReader usernameFile;
		
		usernameFile = new BufferedReader(new FileReader("UserNameFile.txt"));
		
		
		String[] userNameArray = new String[20];
		
		userNameArray = loadArray(userNameArray, usernameFile);
		
		//Finding if the username and password the user enters is within the 2 file different files
		for (int index = 0; index <userNameArray.length; index++ ){
			if (this.userName.equals(userNameArray[index]) && this.userPassword.equals(passwordArray[index])){
				userCredientialsExist = true;
				this.userName = userNameArray[index];
				this.userPassword = passwordArray[index];
				break;
			}
		}
		
	
		
	}//Method - findUserNameAndPassword
	

	
	public static String[][] pascalsTriangle(int rowNum){
		
		/*
		 * Description - the following method is to create pascal triangle based on a given row num. This method will create a 2D array that will form pascal triangle
		 * This alrogitihim worked through this method takes the current position and substracrts the row from above and one index before hand (if the current position is not at the start of the row or at the end of it)
		 * If its either at the start or thte end, that positon will automically be 1
		 */
		rowNum += 2;
		int[][] pascal = new int[rowNum][];
		
		String[][] answerKey = new String[rowNum][];
		
	
		for (int row = 0; row < rowNum; row++){
			pascal[row] = new int[row];
	
			answerKey[row] = new String[row];
			for (int col = 0; col < pascal[row].length; col++){
				if (col == 0 || (col == pascal[row].length-1)){
					pascal[row][col] = 1;
					
				}
				
				else{
					//Taking the previous number from the row above and adding it also to the row and the index before
				pascal[row][col] = pascal[row-1][col-1] +pascal[row-1][col] ;
			
				}
				
			System.out.print(pascal[row][col] + "\t");
	
			
			answerKey[row][col] +=  pascal[row][col] + " ";
			answerKey[row][col] = answerKey[row][col].replaceFirst("null", "");
			}
			System.out.println();
		}
		
		return answerKey;
	}//Method - pascalsTriangle
	
	
	public void createLoginInformation(){
		/*
		 * Description - This method will create a GUI login interface, giving a series of different JTextField, button and a picture, this method will also be used
		 * to detmerine if the user entry is also valid, such that if it is, the user will then proceed to the desktop. If not, the user will be prompted that such
		 * data that they entered is currently not regirstred within the computer system files. The method will also check to see if both JTextfield have been filled
		 * in, this used to prevent any NullPointerException errors
		 */
		
		final JFrame window = new JFrame("LOGIN");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel();
		panel.setLayout(null);
		
		int defaultXPositon = 100;
		
		JLabel displayUserName = new JLabel("Username: ");
		displayUserName.setBounds(defaultXPositon,170,400,30);
		panel.add(displayUserName);
		
		ImageIcon loginPic = new ImageIcon("loginPic.png");
		JLabel loginPicture = new JLabel (loginPic);
		loginPicture.setBounds(250,10,160,160);
		panel.add(loginPicture);
		
		
		final JTextField userEntersUserName = new JTextField();			//This is used to get the users' entry for the username field
		userEntersUserName.setBounds(defaultXPositon,200,400, 30);
		
		panel.add(userEntersUserName);
		
		
		JLabel displayUserPassword = new JLabel("Password: ");
		displayUserPassword.setBounds(defaultXPositon,230,400,30);
		panel.add(displayUserPassword);
		
		
		final JTextField userEntersUserPassword = new JTextField();				//This is used to get the user' entry for the password field
		userEntersUserPassword.setBounds(defaultXPositon,260,400, 30);
		
		panel.add(userEntersUserPassword);
			
		JButton login = new JButton ("Login");						//have a button to confirm/check the valid entries by the user
		login.setBounds(defaultXPositon +150, 320,70,40);
		panel.add(login);
		
		//The following actionion listeners is used to formally check
		login.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ev) {
				
				
				if (!userEntersUserName.getText().equals("") &&!userEntersUserPassword.getText().equals("") ) {
					userName = (userEntersUserName.getText());
					userPassword = (userEntersUserPassword.getText());
				}
				else if (userEntersUserName.getText().equals("") ||userEntersUserPassword.getText().equals("")){
					JOptionPane.showMessageDialog(null, "One or both of the fields are empty",  "Error message",   JOptionPane.ERROR_MESSAGE);
				}
				
				try {
					Thread.sleep(150);
					userEntersUserPassword.selectAll();
					userEntersUserPassword.replaceSelection("");
					
					userEntersUserName.selectAll();
					userEntersUserName.replaceSelection("");
					
				
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				try {
					findUserNameAndPassword();
					
					if (userCredientialsExist == true){
						//Enter a 
						
						String[] userCreditionals = new String[2];
						userCreditionals[0] = userName;
						userCreditionals[1] = userPassword;
						
						printToDeterminePassword(userCreditionals);
						
						
						desktopView();
						window.setVisible(false);
					} else{
						JOptionPane.showMessageDialog(null, "The credientials you entered\ndo not match the system's registry",  "Incorrect Login",   JOptionPane.ERROR_MESSAGE);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
				
				System.out.println("Username:" + userName);
				System.out.println("Password:" + userPassword);
			}});
		
		
		//The following actionlistener is used to implement a shortcut rather just clicking the button (if a user presses the "ENTER" key, it will check the following data)
		userEntersUserPassword.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ev) {
				
				
				if (!userEntersUserName.getText().equals("") &&!userEntersUserPassword.getText().equals("") ) {
					userName = (userEntersUserName.getText());
					userPassword = (userEntersUserPassword.getText());
				}
				else if (userEntersUserName.getText().equals("") ||userEntersUserPassword.getText().equals("")){
					JOptionPane.showMessageDialog(null, "One or both of the fields are empty",  "Error message",   JOptionPane.ERROR_MESSAGE);
				}
				
				try {
					Thread.sleep(150);
					userEntersUserPassword.selectAll();
					userEntersUserPassword.replaceSelection("");
					
					userEntersUserName.selectAll();
					userEntersUserName.replaceSelection("");
					
				
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				try {
					findUserNameAndPassword();
					
					if (userCredientialsExist == true){
						//Enter a 
						
						String[] userCreditionals = new String[2];
						userCreditionals[0] = userName;
						userCreditionals[1] = userPassword;
						
						printToDeterminePassword(userCreditionals);
						
						
						desktopView();
						window.setVisible(false);
					} else{
						JOptionPane.showMessageDialog(null, "The credientials you entered\ndo not match the system's registry",  "Incorrect Login",   JOptionPane.ERROR_MESSAGE);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
				
				System.out.println("Username:" + userName);
				System.out.println("Password:" + userPassword);
			}});
		
		
		panel.setBackground(new Color(86, 170, 249));
		window.add(panel);
		window.setSize(700,450);
		window.setVisible(true);
		
		
	}//Method - createLoginInformation
	
	
	
	public static  String[] loadArray(String[] oneDArray, BufferedReader file) throws IOException{
		/*
		 * Description - The followoing will take a current exisitng string array and bufferedreader file as its paramters. The method will read each line in this txt file
		 * then assign each line of the text file to a index in the String array and return that array
		 */
      	
      	for (int index = 0; index < oneDArray.length; index++ ){
             	oneDArray[index] = file.readLine();
             	}
      	return oneDArray;
	}//Method - loadArray
	
	
	public static void printToDeterminePassword(String[] fileLines){
      	/*
      	 * Description - This method is take an String array write it in the saveData.txt file. Each element of the string array will represent each line in the file
      	 */
      	try{
             	PrintWriter write = new PrintWriter("determineIfLoginProceeded.txt");
             	
             	for (int index = 0; index <fileLines.length; index++ ){
                   	write.print(fileLines[index]+"\n");
                   	
             	}
             	write.close();
      	} catch(FileNotFoundException e){
             	e.printStackTrace();
      	}
      	
	}//Method - saveData
 	public static void saveData(String[] fileLines){
      	/*
      	 * Description - This method is take an String array write it in the saveData.txt file. Each element of the string array will represent each line in the file
      	 */
      	try{
             	PrintWriter write = new PrintWriter("saveData1.txt");
             	
             	for (int index = 0; index <fileLines.length; index++ ){
                   	write.print(fileLines[index]+"\n");
                   	
             	}
             	write.close();
      	} catch(FileNotFoundException e){
             	e.printStackTrace();
      	}
      	
	}//Method - saveData
 	


	

	
	public static void formatOption() throws IOException, NullPointerException, InterruptedException{
      	/*
      	 * Description -This method is to gather the saveData.txt, and replace each line with a blank line. The point of this is to
      	 * simulate that the hard drive will be be completely wiped out. 
      	 */
		//NOTE the following will empty both existing save data and back up txt files
	
		
		Thread.sleep(2000);
		JOptionPane.showMessageDialog(null, "You have sucessfully formatted your HDD",  "Sucessful Action",  JOptionPane.INFORMATION_MESSAGE);
		
      	PrintWriter formatSave = new PrintWriter("saveData1.txt");
      	BufferedReader saveData = new BufferedReader(new FileReader("saveData1.txt"));
      	
    	PrintWriter formatBackUp = new PrintWriter("backUp1.txt");
      	BufferedReader backUpData = new BufferedReader(new FileReader("backUp1.txt"));
      
      	String[] saveFileArray = new String[50];
      	saveFileArray = loadArrayFromFile(saveFileArray,saveData );
      	
      	String[] backUpFileArray = new String[50];
      	backUpFileArray = loadArrayFromFile(backUpFileArray,saveData );
      	
      	
      	
      	try{
      		 for (int index = 0; index < saveFileArray.length; index++){
      			saveFileArray[index] = saveFileArray[index].replace(saveFileArray[index], "\n");
      			backUpFileArray[index] = backUpFileArray[index].replace(backUpFileArray[index], "\n");//if error happens take out this line
             	}
      	}catch(NullPointerException e){
      	
      			
      			
             
             	}
      	formatSave.close();
      	formatBackUp.close();
      	
      	
      	
    
      	
	}//Method - formatOption
	
	public static void restoreFromBackUp() throws IOException, NullPointerException, InterruptedException{
      	/*
      	 * Description - This method is used to read the file of the back up data(backUp.txt) and load all of the lines into an array. Then that array will be written
      	 * to the saveData.txt file in which it will then have successfully backed up the data. 
      	 */
      	
      	BufferedReader backUp = new BufferedReader(new FileReader("backUp1.txt"));
      	String[] fileList = new String[50];
      	fileList = loadArrayFromFile(fileList, backUp);
      	
	
      	backUp.close();
      	//showBackUpLog();
	
      	Thread.sleep(2000);
		JOptionPane.showMessageDialog(null, "You have sucessfully restored your data",  "Sucessful Action",  JOptionPane.INFORMATION_MESSAGE);
      	
      	
      
      	saveData(fileList);
      	
      	
      	if (isEncrypted == false){
      		Thread.sleep(500);
      		System.out.println("\nYour computer has sucessfully restored from a back up");
      		
      		//waitingDotsForRestarting();
      	}
      	frame.setVisible(false);
      	
      	
	}//Method - restoreFromBackUp
	
	
 	
 	public static void taskManger (int isInfected) throws InterruptedException, IOException{
 		/*
		 * Description - This method will act as a task manger application found on computers that operate on windows. This method will produce  random values which are
		 * random based on if the computer has been infected (these values represent the different computer hardware on the computer such as CPU, Disk and so on)
		 */
 		JFrame window = new JFrame("Task Manger");
 		JPanel panel = new JPanel();
 		panel.setLayout(null);
 		window.setSize(400,400);
 		
 		final JTextArea displayInformation = new JTextArea();		//Area used to display the different sets of information
 		displayInformation.setBounds(10,10,350,200);
 		panel.add(displayInformation);
 		
 		JButton refreshButton = new JButton("Refresh");				//Button used to change the change randonodly based on the case of the computer
 		refreshButton.setBounds(10, 250, 120, 20);
 		panel.add(refreshButton);
 		
 		String[] taskMangerData;
 		
 		//The reason for allocating the same if block the same way as the within the actionlistener block is to have some of the statisitcs of the computer already in place when the user opens the program up
 		if ( !isEncrypted){
	 		
			try {
				taskMangerData = taskMangerBeforeInfected();
	 			for (int index = 0; index < taskMangerData.length; index++){
	 				displayInformation.append(taskMangerData[index] +"\n");
	 			}
			} catch (InterruptedException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
 			
 		}else{
 			try {
				taskMangerData = taskMangerAfterInfected();
	 			for (int index = 0; index < taskMangerData.length; index++){
	 				displayInformation.append(taskMangerData[index] +"\n");
	 			}
			} catch (InterruptedException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
 		}
		
 		
 		refreshButton.addActionListener(new ActionListener()  {
 			
			public void actionPerformed(ActionEvent e)  {
				displayInformation.selectAll();
				displayInformation.replaceSelection("");
				String[] taskMangerData;
				if (! isEncrypted){
		 		
					try {
						taskMangerData = taskMangerBeforeInfected();
			 			for (int index = 0; index < taskMangerData.length; index++){
			 				displayInformation.append(taskMangerData[index] +"\n");
			 			}
					} catch (InterruptedException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		 			
		 		}else{
		 			try {
						taskMangerData = taskMangerAfterInfected();
			 			for (int index = 0; index < taskMangerData.length; index++){
			 				displayInformation.append(taskMangerData[index] +"\n");
			 			}
					} catch (InterruptedException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		 		}
				
				
				
	            // printToTextArea(user, classicalComputer );
	         	//System.out.println(x);
	    	
		}});
 		
 		
 		
 		window.add(panel);
 		window.setVisible(true);
 	}//Method - taskManger
	
	
	
	public static String[] taskMangerBeforeInfected() throws InterruptedException, IOException{
      	/*
      	 * Description - This method is to simulate what the performance of an idle desktop would be. This method would be using the pre-define method Random() which
      	 * generates numbers that represent a computer idling (meaning no hard core task are to run). 
      	 */
		
		/**
		 * taskMangerData[0] = CPU
		 * taskMangerData[1] = Memory
		 * taskMangerData[2] = DISK
		 */
		
		
		String[] taskMangerData = new String[3];
       	Random randomNum = new Random();
      
      	int CPU = randomNum.nextInt(35) + 5;			//min number is 5, max number is 40
      	System.out.println("CPU: " + CPU + "%");	
       	taskMangerData[0] = ("CPU: " + CPU + "%");
      
       	
       	int memory = randomNum.nextInt(35) + 10;		//min number is 10, max number is 45
      	System.out.println("Memory: " + memory + "%");	
       	taskMangerData[1] =("Memory: " + memory + "%");
      	
       	
       	int disk0 = randomNum.nextInt(45) + 0;			//min number is 0, max number is 45
      	System.out.println("Disk 0 (C:): " + disk0 + "%");
       	taskMangerData[2] = ("Disk 0 (C:): " + disk0 + "%");
      	
      	
      	return taskMangerData;
     
	}//Method - taskMangerBeforeInfected
	
	public static String[] taskMangerAfterInfected() throws InterruptedException, IOException{
      	/*
      	 * Description -This method is to simulate what the performance of an desktop is being attacked by malware (the performance starts to take a toll on the computer 
      	 * in which the CPU, memory and disk will be at high percentages). This method would be using the pre-define method Random() which generates numbers that represents
      	 *  a computer under heavy loads.
      	 */
		
		String[] taskMangerData = new String[3];
		/**
		 * taskMangerData[0] = CPU
		 * taskMangerData[1] = Memory
		 * taskMangerData[2] = DISK
		 */
		
    
      	Random randomNum = new Random();
      
      	
      	int CPU = randomNum.nextInt(15) + 65;		//min number is 65, max number is 80
      	System.out.println("CPU: " + CPU + "%");
      	taskMangerData[0] = ("CPU: " + CPU + "%");
      	
      	int memory = randomNum.nextInt(30) + 65;	//min number is 65, max number is 95
      	System.out.println("Memory: " + memory + "%");
       	taskMangerData[1] =("Memory: " + memory + "%");
     
      	
      	
      	
      	int disk0 = randomNum.nextInt(20) + 75;		//min number is 75, max number is 95
            	
      	System.out.println("Disk 0 (C:): " + disk0 + "%");
       	taskMangerData[2] = ("Disk 0 (C:): " + disk0 + "%");
      	
       	return taskMangerData;
	}//Method - taskMangerAfterInfected
	
	
	public static void desktopView() throws IOException{
		/*
		 * Description - This method is a overall large method to hold and showcase all the applicaitons used in this program. It acts just like how a regular desktop
		 * would work, where you have applcations on your screen, you click the application and they open up. The same process works in this method, except that if 
		 * the computer finds out that the files have been tempered, the computer will not  allow the user to click on these files as this will showcase how malwares
		 * can have this effect on the computer
		 */
		
		userCredientialsExist = true;
		
		//The following JPanel is used to set a background image in the JFrame 
		JPanel panel = (new JPanel() {
	        BufferedImage image = ImageIO.read(new URL("http://i1-news.softpedia-static.com/images/news2/microsoft-reveals-the-official-windows-10-wallpaper-485311-4.jpg"));
	        public void paintComponent(Graphics g) {
	            super.paintComponent(g);
	            g.drawImage(image, 0, 0, 1200, 600, this);
	     
	        }
	    
	    }
	);
		
	
		//THE FOLLOWING GUI COMPONENTS ARE ADDING BUTTONS, Text, Pictures to the screen, note that each button has a function that calls on a speicfic method depending on what the button is
		//However, if the comouter is infected, the user will not be able to proceed to the following application
		
		frame.setSize(1200,600);
		panel.setLayout(null);
		
		ImageIcon windowsBackground = new ImageIcon ("Windows_Final_2560p_v10_2560.jpg");
		JLabel backgroundImage = new JLabel(windowsBackground);
		backgroundImage.setBounds(0, 0, 1200, 600);
		panel.add(backgroundImage);
		frame.add(panel);
		
		JButton shutDownButton = new JButton();
		shutDownButton.setBounds(1100, 450, 60, 60);
		panel.add(shutDownButton);
		
		
		JLabel shutDownText = new JLabel("Shut Down");
		 shutDownText.setBounds(1100,505, 400, 30);
		 shutDownText.setForeground(Color.WHITE);
		 panel.add(shutDownText);
		
		 try {
			  
			 shutDownButton.setIcon(new ImageIcon("shutDownButtonLogo.PNG"));
		  } catch (Exception ex) {
		    System.out.println(ex);
		  }
		
		 
		 
		 shutDownButton.addActionListener(new ActionListener()  {
				
				public void actionPerformed(ActionEvent e)  {
					
					try {
						shutDownSequence();
					} catch ( NullPointerException e1) {
						// TODO Auto-generated catch block
						
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
					
		            // printToTextArea(user, classicalComputer );
		         	//System.out.println(x);
		    	
			}});
		 
		 
		 
		
		 JButton microsoftWord = new JButton();
		 JLabel wordText = new JLabel("Microsoft Word");
		 wordText.setBounds(5,100, 400, 30);
		 wordText.setForeground(Color.WHITE);
		  try {
		  
			  microsoftWord.setIcon(new ImageIcon("microsoftWord2016.PNG"));
		  } catch (Exception ex) {
		    System.out.println(ex);
		  }
		
		  microsoftWord.setBounds(10, 10, 75, 75);
		panel.add(microsoftWord);
		panel.add(wordText);
		
		
		microsoftWord.addActionListener(new ActionListener()  {
		
			public void actionPerformed(ActionEvent e)  {
				
				if (isEncrypted == false){
					
				
				try {
					micrsoftWordProgram();
				} catch ( NullPointerException e1) {
					// TODO Auto-generated catch block
					
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				} else{
					if (numBasedOnLevelOfSecurity == 0){
						JOptionPane.showMessageDialog(null, "1.) Your password is not secure enough\n2.) You have been RESTRICTED access from this application by suspicious Malware\n\nTo fix this issue please change your password (using Password Generator) and then use the Kill Switch button",  "Error Message - 2 Problems occured",   JOptionPane.ERROR_MESSAGE);
					
					}else if (numBasedOnLevelOfSecurity == 1){
						
							JOptionPane.showMessageDialog(null, "1.) You have been RESTRICTED access from this application by suspicious Malware\n\nTo fix this issue please use the Kill Switch button",  "Error Message - 1 Problem occured",   JOptionPane.ERROR_MESSAGE);
						
					}	}
					
					//JOptionPane.showMessageDialog(null, "1.) Your password is not secure enough\2.)nYou have been RESTRICTED access from this application by suspicious Malware\n\nToFix this issue please change your password (using passwordCombinations) and then use the Kill Switch button",  "Error Message - 2 Problems occured",   JOptionPane.ERROR_MESSAGE);
				
				
				
	            // printToTextArea(user, classicalComputer );
	         	//System.out.println(x);
	    	
		}});
		
		 JButton taskManger = new JButton();
		 JLabel taskMangerText = new JLabel("Task Manger");
		 taskMangerText.setBounds(10,225, 400, 30);
		 taskMangerText.setForeground(Color.WHITE);
		  try {
		  
			  taskManger.setIcon(new ImageIcon("taskManger.PNG"));
		  } catch (Exception ex) {
		    System.out.println(ex);
		  }
		
		  taskManger.setBounds(10, 140, 75, 75);
		panel.add(taskManger);
		panel.add(taskMangerText);
		
		taskManger.addActionListener(new ActionListener()  {
		
			public void actionPerformed(ActionEvent e)  {
				
				try {
					taskManger(numBasedOnLevelOfSecurity);
				} catch ( NullPointerException e1) {
					// TODO Auto-generated catch block
					
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				
	            // printToTextArea(user, classicalComputer );
	         	//System.out.println(x);
	    	
		}});
		
		 JButton googleChrome = new JButton();
		 JLabel googleChromeText = new JLabel("Google Chrome");
		 googleChromeText.setBounds(10,400, 400, 30);
		 googleChromeText.setForeground(Color.WHITE);
		  try {
		  
			  googleChrome.setIcon(new ImageIcon("googleChrome.PNG"));
		  } catch (Exception ex) {
		    System.out.println(ex);
		  }
		  
		
		  googleChrome.setBounds(10, 300, 75, 75);
		panel.add(googleChrome);
		panel.add(googleChromeText);
		
		googleChrome.addActionListener(new ActionListener()  {
		
			public void actionPerformed(ActionEvent e)  {
				if( isEncrypted == false){
				try {
					googleChrome();
					trackGoogleChromeAction = true;
					
					
				} catch ( NullPointerException e1) {
					// TODO Auto-generated catch block
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				}else{
					if (numBasedOnLevelOfSecurity == 0){
						JOptionPane.showMessageDialog(null, "1.) Your password is not secure enough\n2.) You have been RESTRICTED access from this application by suspicious Malware\n\nTo fix this issue please change your password (using Password Generator) and then use the Kill Switch button",  "Error Message - 2 Problems occured",   JOptionPane.ERROR_MESSAGE);
					
					}else if (numBasedOnLevelOfSecurity == 1){
						
							JOptionPane.showMessageDialog(null, "1.) You have been RESTRICTED access from this application by suspicious Malware\n\nTo fix this issue please use the Kill Switch button",  "Error Message - 1 Problem occured",   JOptionPane.ERROR_MESSAGE);
						
					}	}
				
	            // printToTextArea(user, classicalComputer );
	         	//System.out.println(x);
	    	
		}});
		
		
		JButton passwordGenerator = new JButton();
		JLabel passwordText = new JLabel("Password Generator");
		passwordText.setBounds(110,100, 400, 30);
		passwordText.setForeground(Color.WHITE);
		  try {
		  
			  passwordGenerator.setIcon(new ImageIcon("combinationPic.PNG"));
		  } catch (Exception ex) {
		    System.out.println(ex);
		  }
		  
		
		  passwordGenerator.setBounds(120, 10, 75, 75);
		panel.add(passwordGenerator);
		panel.add(passwordText);
		
		passwordGenerator.addActionListener(new ActionListener()  {
		
			public void actionPerformed(ActionEvent e)  {
				
				try {
					passwordGenerator();
				} catch ( NullPointerException e1) {
					// TODO Auto-generated catch block
					
				}
				
				
				
	            // printToTextArea(user, classicalComputer );
	         	//System.out.println(x);
	    	
		}});
		
		JButton killSwitchButton = new JButton();
		JLabel killSwitchButtonText = new JLabel("Kill Switch");
		killSwitchButtonText.setBounds(130,225, 400, 30);
		killSwitchButtonText.setForeground(Color.WHITE);
		  try {
		  
			  killSwitchButton.setIcon(new ImageIcon("killSwitchLogo.PNG"));
		  } catch (Exception ex) {
		    System.out.println(ex);
		  }
		  
		
		  killSwitchButton.setBounds(120, 140, 75, 75);
		panel.add(killSwitchButton);
		panel.add(killSwitchButtonText);
		
		killSwitchButton.addActionListener(new ActionListener()  {
		
			public void actionPerformed(ActionEvent e)  {
				
				try {
					killSwitchMenu();   
				} catch ( NullPointerException e1) {
					// TODO Auto-generated catch block
					
				}
				
				
				
	            // printToTextArea(user, classicalComputer );
	         	//System.out.println(x);
	    	
		}});
		
		
		
		JButton calculator = new JButton();
		JLabel calculatorText = new JLabel("Basic Calculator");
		calculatorText.setBounds(110,400, 400, 30);
		calculatorText.setForeground(Color.WHITE);
		  try {
		  
			  calculator.setIcon(new ImageIcon("calculatorImage.PNG"));
		  } catch (Exception ex) {
		    System.out.println(ex);
		  }
		  
		
		  calculator.setBounds(120, 300, 75, 75);
		panel.add(calculator);
		panel.add(calculatorText);
		
		calculator.addActionListener(new ActionListener()  {
		
			public void actionPerformed(ActionEvent e)  {
				if( isEncrypted == false){
				try {
					calculator();
				} catch ( NullPointerException e1) {
					// TODO Auto-generated catch block
					
				}
				
				}else{
					if (numBasedOnLevelOfSecurity == 0){
						JOptionPane.showMessageDialog(null, "1.) Your password is not secure enough\n2.) You have been RESTRICTED access from this application by suspicious Malware\n\nTo fix this issue please change your password (using Password Generator) and then use the Kill Switch button",  "Error Message - 2 Problems occured",   JOptionPane.ERROR_MESSAGE);
					
					}else if (numBasedOnLevelOfSecurity == 1){
						
							JOptionPane.showMessageDialog(null, "1.) You have been RESTRICTED access from this application by suspicious Malware\n\nTo fix this issue please use the Kill Switch button",  "Error Message - 1 Problem occured",   JOptionPane.ERROR_MESSAGE);
						
					}	}
				
	            // printToTextArea(user, classicalComputer );
	         	//System.out.println(x);
	    	
		}});
		
		
		frame.add(panel);
		frame.setVisible(true);
		
		if (numBasedOnLevelOfSecurity == 0){
			JOptionPane.showMessageDialog(null, "Your password does not seem to be advanced, based on your password... \nyour computer and aswell as your self is vunerable from hackers online",  "Caution Message", JOptionPane.WARNING_MESSAGE);
		}
		
		
		
	}//Method - desktopView
	
	public static void killSwitchMenu(){
		
		/*
		 * Description - This method is used to solve the problem of malware attacking the computer. This is a optimaized solution to get rid of the malware for good.
		 * This will allow the user to select 2 options (Format or restore from backUp). 
		 */
		
		final JFrame frame = new JFrame("Kill Switch Menu");
		JPanel panel = new JPanel();
		panel.setLayout(null);
		frame.setSize(600, 150);
		
		
		//The following are just JBUttons added to the JFrame that gives the user some options
		JButton formatButton = new JButton("Format HDD");			
		formatButton.setBounds(10, 40, 250, 40);
		panel.add(formatButton);
		
		
		JButton restoreButton = new JButton("Restore From Previous Backup");
		restoreButton.setBounds(280, 40, 250, 40);
		panel.add(restoreButton);
		
		formatButton.addActionListener(new ActionListener()  {
			
			public void actionPerformed(ActionEvent e)  {
				
				try {
					formatOption();
					frame.setVisible(false);
				} catch ( NullPointerException e1) {
					// TODO Auto-generated catch block
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				
	            // printToTextArea(user, classicalComputer );
	         	//System.out.println(x);
	    	
		}});
		
		restoreButton.addActionListener(new ActionListener()  {
			
			public void actionPerformed(ActionEvent e)  {
				
				try {
					restoreFromBackUp();
					frame.setVisible(false);
				} catch ( NullPointerException e1) {
					// TODO Auto-generated catch block
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				
	            // printToTextArea(user, classicalComputer );
	         	//System.out.println(x);
	    	
		}});
		
		
		
		frame.add(panel);
		frame.setVisible(true);
	}//Method - killSwitchMenu
	
	public static void googleChrome() throws IOException{
		
		/*
		 * Description -  the following method uses a simple image of google which is just a picture of a google homepage. THe point of this method is to showcase that
		 * malware mainly comes from the World Wide Web and everyone is vunerable to it if not careful about it
		 */
		JFrame window = new JFrame();
		window.setSize(1200, 700);
		JPanel panel = (new JPanel() {
	        BufferedImage image = ImageIO.read(new URL("https://upload.wikimedia.org/wikipedia/commons/thumb/9/96/Google_web_search.png/640px-Google_web_search.png"));
	        public void paintComponent(Graphics g) {
	            super.paintComponent(g);
	            g.drawImage(image, 0, 0, 1200, 700, this);
	     
	        }
	    
	    }
	);
		
		
		panel.setLayout(null);
	
		
		
		window.add(panel);
		window.setVisible(true);
		trackGoogleChromeAction = true;
		
	}//Method - googleChrome
	
	public static void calculator(){
		/*
		 * Description - The following method acts as basic calculator in which its 4 simple buttons of action are multiplying, dividing, addiing and subtractiing. 
		 * Note, that the calcualtor performs the multiplying and dividing operation through recursion. This method also has another feature in which it gets the values
		 * from the certain answers (only addiing, subtracting and mutliplying) and sorts the answer from least to greatest.
		 */
		
		JFrame frame  = new JFrame("Calculator");
		frame.setSize(500, 700);
		JPanel panel = new JPanel();
		panel.setLayout(null);
		
	
		//The following 4 buttons are creating the basic operations and setting them to be displayed in the JFrame
		JButton division = new JButton("Division");
		division.setBounds(200,400, 150, 30);
		panel.add(division);
	
		
		JButton multiplication = new JButton("Multiply");
		multiplication.setBounds(10,400, 150, 30);
		panel.add(multiplication);
		
		JButton addition = new JButton("Add");
		addition.setBounds(10,450, 150, 30);
		panel.add(addition);
		
		JButton subtraction = new JButton("Subtraction");
		subtraction.setBounds(200,450, 150, 30);
		panel.add(subtraction);
		
		
		JButton sort = new JButton("Sort Answers");
		sort.setBounds(150,600, 200, 30);
		panel.add(sort);
		
		
		
		
		 final JTextField number1;//new TextArea
		 number1 = new JTextField ();//Set size, adds String "Hi"
         
		 number1.setBounds(80,50,40,25);
         panel.add(number1);
                 
   
         
         final JTextField number2;//new TextArea
         number2 = new JTextField ();//Set size, adds String "Hi"
         
         number2.setBounds(200,50,40,25);
         panel.add(number2);
        
         
                     
         JOptionPane.showMessageDialog(null, "At the moment, the calculator is designed to only take in positive numbers",  "Caution Message", JOptionPane.WARNING_MESSAGE);
         
         
         final JTextArea displayAnswers = new JTextArea();
         displayAnswers.setAlignmentY(0);
         
         displayAnswers.setBounds(10,150,400,200);
         panel.add(displayAnswers);
     
         
         final JTextArea duplicateAnswer = new JTextArea();
         duplicateAnswer.setAlignmentY(0);
         
         duplicateAnswer.setBounds(50,150,400,200);	
        
      
         JLabel operationSymbol = new JLabel();
         operationSymbol.setBounds(150,40,40,40);
         
         panel.add(operationSymbol);
         
         
         
         
         
         JLabel firstNumberTitle = new JLabel("Enter Number 1");
         firstNumberTitle.setBounds(80,20,90,40);
         
         panel.add(firstNumberTitle);
         
         
         
         
         
         JLabel secondNumberTitle = new JLabel("Enter Number 2");
         secondNumberTitle.setBounds(200,20,90,40);
         
         panel.add(secondNumberTitle);
         
         
         
         JButton clear = new JButton("Clear");
         clear.setBounds(10,600,100,30);
         panel.add(clear);
       
         
         //The following will just empty all the text field in the jframe
         clear.addActionListener(new ActionListener(){
        	 
        	 public void actionPerformed(ActionEvent ev) {
        		 
        		displayAnswers.selectAll();
        		displayAnswers.replaceSelection("");
        		
        		number1.selectAll();
        		number1.replaceSelection("");
        		
        		number2.selectAll();
        		number2.replaceSelection("");
        		
        		operationSymbol.setText("");
        		
                 
                 
         }});
         
         
         //The following action listeners are based on the 4 operations 
         division.addActionListener(new ActionListener(){
        	 
        	 public void actionPerformed(ActionEvent ev) {
        		 
        		 operationSymbol.setText("");
        		  operationSymbol.setText("÷");
        		 int numberOneValue = -1;
                    
                    if (!number1.getText().equals("")){
                    	numberOneValue = Integer.parseInt(number1.getText());
                    }
        		 
        		 int numberTwoValue = -1;
                 if (!number2.getText().equals("")){
                	 numberTwoValue = Integer.parseInt(number2.getText());
                 }
                 
            
        		 if (numberOneValue >= 0 && numberTwoValue >= 0   ){
        			 double[]  answerList = recurssiveDisvion(numberTwoValue, numberOneValue);
                	 displayAnswers.append("Remainder:\t"+answerList[0]  + "\nQuotient:\t" + answerList[2]   +"\n");
                 }
                 
                 
         }});
         
         
         
         multiplication.addActionListener(new ActionListener(){
        	 
        	 public void actionPerformed(ActionEvent ev) {
        		 
        	
        		 operationSymbol.setText("");
       		  operationSymbol.setText("x");
        		 int numberOneValue = -1;
                    
                    if (!number1.getText().equals("")){
                    	numberOneValue = Integer.parseInt(number1.getText());
                    }
        		 
        		 int numberTwoValue = -1;
                 if (!number2.getText().equals("")){
                	 numberTwoValue = Integer.parseInt(number2.getText());
                 }
                 
            
        		 if (numberOneValue >= 0 && numberTwoValue >= 0   ){
        			 double product = recurssiveMultiplcation(numberOneValue, numberTwoValue);
                	 displayAnswers.append("Product: "+ product  +"\n");
                	 duplicateAnswer.append(product + "\n");
                 }
                 
                 
         }});
         
     addition.addActionListener(new ActionListener(){
        	 
        	 public void actionPerformed(ActionEvent ev) {
        		 
        		 operationSymbol.setText("");
       		  operationSymbol.setText("+");
        		 int numberOneValue = -1;
                    
                    if (!number1.getText().equals("")){
                    	numberOneValue = Integer.parseInt(number1.getText());
                    }
        		 
        		 int numberTwoValue = -1;
                 if (!number2.getText().equals("")){
                	 numberTwoValue = Integer.parseInt(number2.getText());
                 }
                 
            
        		 if (numberOneValue >= 0 && numberTwoValue >= 0   ){
        			double answer = (numberOneValue+ numberTwoValue);
                	 displayAnswers.append("Addition: " +  answer  +"\n");
                	 duplicateAnswer.append(answer + "\n");
                 }
                 
                 
         }});
     
     subtraction.addActionListener(new ActionListener(){
    	 
    	 public void actionPerformed(ActionEvent ev) {
    		 
    		 operationSymbol.setText("");
   		  operationSymbol.setText("-");
    		   
    		 int numberOneValue = -1;
                
                if (!number1.getText().equals("")){
                	numberOneValue = Integer.parseInt(number1.getText());
                }
    		 
    		 int numberTwoValue = -1;
             if (!number2.getText().equals("")){
            	 numberTwoValue = Integer.parseInt(number2.getText());
             }
             
        
    		 if (numberOneValue >= 0 && numberTwoValue >= 0   ){
    			double answer = (numberOneValue- numberTwoValue);
            	 displayAnswers.append("Subtraction: " +  answer +"\n");
            	 duplicateAnswer.append(answer + "\n");
             }
             
             
     }});
  
     
     //The following actionlistener will only sort the answers that are displayed in the textbox, and sort them from least to greatest
     sort.addActionListener(new ActionListener(){
    	 
    	 public void actionPerformed(ActionEvent ev) {
    		 
    		 displayAnswers.selectAll();
    		 displayAnswers.replaceSelection("");
    		 System.out.println( duplicateAnswer.getLineCount());
    		  int countAmountOfLines = duplicateAnswer.getLineCount();
    		     String[] answersFromTextBox  = new String[countAmountOfLines-1];
    		     answersFromTextBox =  duplicateAnswer.getText().split("\\n");
    		     double[] answersInDoubleFromTextBox = convertToDoubleValuesFromString(answersFromTextBox);
    		     
    		     answersInDoubleFromTextBox = bubbleSort(answersInDoubleFromTextBox);
    		     
    		     for (int index = 0; index < answersInDoubleFromTextBox.length; index++){
    		    	 displayAnswers.append("" + answersInDoubleFromTextBox[index] + "\n");
    		     }
    		
             
             
     }});
   
     
         
		
         frame.add(panel);
         frame.setVisible(true);
	}//Method - calculator
	
	 public static double[] bubbleSort(double[] oneDArray) {  
			/*
			 * Description - This method takes in a array that is type double, and it uses the bubble sorting algorithim to sort this oneDArray from least to greatest
			*/
		 
		 
		 double length = oneDArray.length;  
		 double temp = 0;  
	         for(int index1=0; index1 < length; index1++){  
	                 for(int index2=1; index2 < (length-index1); index2++){  
	                          if(oneDArray[index2-1] > oneDArray[index2]){  
	                                 //swaps the elements before with the current element  
	                                 temp = oneDArray[index2-1];  
	                                 oneDArray[index2-1] = oneDArray[index2];  
	                                 oneDArray[index2] = temp;  
	                         }  
	                          
	                 }  
	         }  
	         return oneDArray;
	 }//Method - bubbleSort
	 
	 public static double[] convertToDoubleValuesFromString(String[] array){
			/*
			 * Description -This method is used to take a string array and convert each index in this string array to a double array
			*/
		 
		double[] doubleArrayList = null;
		try {
			doubleArrayList = new double[array.length];
			for (int index = 0; index < array.length; index++) {
				doubleArrayList[index] = Double.parseDouble("" + array[index]);
			}

		} catch (NullPointerException e) {

		}
		return doubleArrayList;
	 }//Method - convertToDoubleValuesFromString
	 
		public static double[] recurssiveDisvion(double divsor, double dividend) {
			/*
			 * Description - This method is used find the remainder and quotient given the divsor and dividend, recurssively. It then returns a oneD array
			 * that containts the main values of the quotentient and remainder
			*/
		 
			double[] divison = new double[3];
			// [0] - remainder
			// [1] - Dividend
			// [2] - quotient
			if (divsor > dividend) {
				divison[0] = dividend;
				divison[1] = divsor;
				return divison;
			} else {
				divison = recurssiveDisvion(divsor, dividend - divsor);
				divison[1] = dividend;
				divison[2] += 1;
				return divison;
			}
		}//Method - recurssiveDisvion
		
		public static double recurssiveMultiplcation(double number1, double number2) {
			/*
			 * Description -This method is used to find the product of 2 numbers recursively. It then returns a double value, which in the end will be the product value
			*/
		 
			if (number1 == 0 ||number2 == 0 ){
				return 0;
			}else{
				return number1 + recurssiveMultiplcation(number1, number2 - 1);
			}
		}//Method - recurssiveMultiplcation
	
	public static void passwordGenerator(){
		/*
		 * Description - This method is used to showcase the different possible of combinations generated based on the user's password. The point of it this is
		 * to get the user to recogonize that their password is there key to everything, once this password is exposed. There identiy is jepozaried. This method will
		 * get the user to enter a password, and generate a sequence of possible combinations of this password. As well, this method will also have a textfield that
		 *  the user can select a specific password by entering a number that corresponds to the combiatnions beside it. If the user wants to proceed with this idea, 
		 *  they have to click a button beside the text field, in which it will prompt them 2 options.
		*/
		
		
		JFrame window  = new JFrame("Password Generator");
		JPanel panel = new JPanel();
		window.setSize(700, 700);
		panel.setLayout(null);
		
		final JTextField userPossiblePasswordCombination = new JTextField();
		userPossiblePasswordCombination.setBounds(10, 10, 350, 40);
		panel.add(userPossiblePasswordCombination);
		
		JButton generate = new JButton ("Generate");
		generate.setBounds(370, 10,90,40);
		panel.add(generate);
		
		final JTextArea comb = new JTextArea();
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 60, 600, 400);
		
		window.getContentPane().add(scrollPane);
		scrollPane.setViewportView(comb);
		
		
		
		final JTextField userChosenComb = new JTextField();
		userChosenComb.setBounds(10, 520, 100, 40);
		panel.add(userChosenComb);
		
		JLabel titleForUserChosenComb = new JLabel("Enter a number that correseponds to one of the lists above as your chosen new password");
		titleForUserChosenComb.setBounds(10, 480, 600, 40);
		panel.add(titleForUserChosenComb);
		
		JButton findUserNum = new JButton("Find");
		findUserNum.setBounds(200, 520, 70, 40);
		panel.add(findUserNum);
		
		JOptionPane.showMessageDialog(null, "*Note, This software program is in its BETA stages at the moment, so only 4 chacters will function (well most of the alorigithim works)",  "NOTE",   JOptionPane.WARNING_MESSAGE);
		
		
		//The following will set the determine the user's combionateion based on  user's input
		generate.addActionListener(new ActionListener()  {
			
			public void actionPerformed(ActionEvent e)  {
				//String[][] listOfComb = null;
				try {
					comb.selectAll();
					comb.replaceSelection("");
					if (!userPossiblePasswordCombination.getText().equals("") && userPossiblePasswordCombination.getText().length() == 4){
						duplicateList = generatePassword(userPossiblePasswordCombination.getText());
						printTwoDArray(duplicateList, comb);
					}else{
						JOptionPane.showMessageDialog(null, "This Program at the time only works for 4 characters",  "Incorrect Data",   JOptionPane.ERROR_MESSAGE);
					}
					
				
				} catch (NullPointerException e1) {
					// TODO Auto-generated catch block
					
				}
				
				
				
	            // printToTextArea(user, classicalComputer );
	         	//System.out.println(x);
	    	
		}});
		
		
		findUserNum.addActionListener(new ActionListener()  {
			
			public void actionPerformed(ActionEvent e)  {
			
				try {
					if ( !userChosenComb.getText().equals("")  ){
						//System.out.println("HELLO");
						int userChosenNum = Integer.parseInt(userChosenComb.getText());
						int calculatePossibleCombinations =  userPossiblePasswordCombination.getText().length() *  (userPossiblePasswordCombination.getText().length()-1);
						
						//System.out.println(userChosenNum+"\t\t"+calculatePossibleCombinations);
						if (userChosenNum >= (0) && userChosenNum <= calculatePossibleCombinations){
							
							frameForWouldYouLikeToCreatePassword(userChosenNum,userPossiblePasswordCombination.getText() );
						}else{
							String displayErrorMeassage ="Your number ("+userChosenNum +") does not exist in this table";
							JOptionPane.showMessageDialog(null, displayErrorMeassage,  "Incorrect Data",   JOptionPane.ERROR_MESSAGE);
						}
					}else{
						JOptionPane.showMessageDialog(null, "This Program at the time only works for 4 characters",  "Incorrect Data",   JOptionPane.ERROR_MESSAGE);
					}
					
				
				} catch (NullPointerException e1) {
					// TODO Auto-generated catch block
					
				}
				
				
				
	            // printToTextArea(user, classicalComputer );
	         	//System.out.println(x);
	    	
		}});
		
		window.add(panel);
		window.setVisible(true);
	}//Method - passwordGenerator
	
	public static void createNewPasswordScreen(int num, String userCombination){
		/*
		 * Description - This method is used to help the user create a better password such that it is safe and protected. This method will acts as create a new
		 * password screen in which the user would have to enter the current password, the new password (which will already be in place from the previous selection as
		 * one of the password combinations) and reEnter the new password again. The method will check to see if the user has entered the correct information in each
		 * text field  to  perform such action. Once the user has enter the valid information, then the method will update or rather replace the old password with the exisitng one
		*/
		
		
		
		final JFrame frame = new JFrame("Create a new Password");
		JPanel panel= new JPanel();
		frame.setSize(500, 600);
		panel.setLayout(null);
		
		
		//The following is setting several GUI components and setting them to a JPanel
		final JTextField currentPassword = new JTextField();
		currentPassword.setBounds(10, 50, 400, 40);
		panel.add(currentPassword);
		
		JLabel currentPasswordLabel = new JLabel("Enter Current Password:");
		currentPasswordLabel.setBounds(10, 10, 400, 40);
		panel.add(currentPasswordLabel);
		
		
		final JTextField newPassword = new JTextField();
		newPassword.setBounds(10, 140, 400, 40);
		panel.add(newPassword);
		
		
		JLabel newPasswordLabel = new JLabel("Enter New Password:");
		newPasswordLabel.setBounds(10, 110, 400, 40);
		panel.add(newPasswordLabel);
		
		
		duplicateList = generatePassword(userCombination);
		newPassword.setText(findRowInTwoDArray(num));
		
		
		final JTextField newPasswordAgain = new JTextField();
		newPasswordAgain.setBounds(10, 240, 400, 40);
		panel.add(newPasswordAgain);
		
	
		
		JLabel newPasswordAgainLabel = new JLabel("Enter New Password Again:");
		newPasswordAgainLabel.setBounds(10, 210, 400, 40);
		panel.add(newPasswordAgainLabel);
		
		
		JButton createPasswordButton = new JButton("Create Password");
		createPasswordButton.setBounds(10,350,200,40);
		panel.add(createPasswordButton);
		
		
		//JOptionPane.showMessageDialog(null, "*Note, since the combination generator program is at the BETA stage, the password you chose will not meet the criteria because, it is less than 6 characters long\nMeaning you will have to enter a new password that has... \n1.) Is more than 6 characters long \n2.)Contains at least one special character",  "NOTE",   JOptionPane.WARNING_MESSAGE);
		
		
		//The following will create or rather update a password for the user that meets speicfic criteria 
		createPasswordButton.addActionListener(new ActionListener()  {
			
			public void actionPerformed(ActionEvent e)  {
			
				try {
					if ( currentPassword.getText().equals(userPassword) && newPassword.getText().equals(newPasswordAgain.getText()) && (newPassword.getText().length() >= 6) ){
						Thread.sleep(1500);
						overWritePasswordFile(currentPassword.getText(), newPasswordAgain.getText());
						JOptionPane.showMessageDialog(null, "You have sucessfully updated your password",  "Sucessful Action",  JOptionPane.INFORMATION_MESSAGE);
						frame.setVisible(false);
					}else{
						JOptionPane.showMessageDialog(null, "Incorrect Data (New password must have atleast 6 characters or more",  "Incorrect Data",   JOptionPane.ERROR_MESSAGE);
					}
					
				
				} catch (NullPointerException e1) {
					// TODO Auto-generated catch block
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				
	            // printToTextArea(user, classicalComputer );
	         	//System.out.println(x);
	    	
		}});
		
		frame.add(panel);
		frame.setVisible(true);
		JOptionPane.showMessageDialog(null, "Note, to have a safe password (it must have at least 6 characters and must contain a special character)",  "NOTE",   JOptionPane.WARNING_MESSAGE);
	}//Method - createNewPasswordScreen
	
	public static void frameForWouldYouLikeToCreatePassword(final int num, final String userCombination){
		/*
		 * Description - This method acts as a yes or no block in which there are 2 buttons in position that asks the user if they would like to proceed with creating a new password, if they are they will be brought to a new screen to create a new password, if not they will be returned back to the password combination generator screen
		 * 
		*/
		
		
		final JFrame frame = new JFrame();
		JPanel panel= new JPanel();
		frame.setSize(1000, 400);
		panel.setLayout(null);
		
		JButton yesProceedButton = new JButton("Yes, I would like to make a new password");
		yesProceedButton.setBounds(20,100,350,60);
		panel.add(yesProceedButton);
		
		JButton noButton = new JButton("No, I do not want to make a new password");
		noButton.setBounds(500,100,350,60);
		panel.add(noButton);
	
		
		yesProceedButton.addActionListener(new ActionListener()  {
			
			public void actionPerformed(ActionEvent e)  {
			
				try {
					createNewPasswordScreen(num,userCombination);
					frame.setVisible(false);
				
				} catch (NullPointerException e1) {
					// TODO Auto-generated catch block
					
				}
				
				
				
	           
	    	
		}});
		
		
		noButton.addActionListener(new ActionListener()  {
			
			public void actionPerformed(ActionEvent e)  {
			
				try {
					frame.setVisible(false);
					
				
				} catch (NullPointerException e1) {
					// TODO Auto-generated catch block
					
				}
				
				
				
	            // printToTextArea(user, classicalComputer );
	         	//System.out.println(x);
	    	
		}});
		
		frame.add(panel);
		frame.setVisible(true);
	}//Method - frameForWouldYouLikeToCreatePassword
	
	
 	public static void micrsoftWordProgram() throws InterruptedException, IOException{
      	/*
      	 * Description - This method is to simulate a program similar to Microsoft Word in which the purpose would be to act as text box for the user to enter what ever
      	 * they like. There is a button that will be used to save the current textbox and take each line of that textbox and write that to a txt file
      	 *
        	 */
      
      	
      	//The backUpData() method is not supposed to be here, but this is just in case, the backUp.txt file has been empty. This automatically places something in the backUpData.txt file
      
    
      
      	JFrame window = new JFrame("Microsoft Word 2016");
      	window.setSize(700,700);
      	JPanel panel = new JPanel();
      	panel.setLayout(null);
      	panel.setBackground(new Color(160, 207, 245));
      	
      	final JTextArea textBox = new JTextArea();
      	//textBox.setBounds(10,10,500,400);
      	
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 400, 400);
		
		window.getContentPane().add(scrollPane);
		scrollPane.setViewportView(textBox);
		
		JButton save = new JButton("Save");
		save.setBounds(10,430,80,40);
		panel.add(save);
		
		int countAmountOfLines = textBox.getLineCount();
		System.out.println(countAmountOfLines);
		
		JButton load = new JButton("Load");
		load.setBounds(120,430,80,40);
		panel.add(load);
		
		
		
		//The following will save data in the JTextArea and save it to a txt file
		save.addActionListener(new ActionListener()  {
			
			public void actionPerformed(ActionEvent e)  {
				String[] dataFromTextBox; 
				try {
					int countAmountOfLines = textBox.getLineCount();
					System.out.println(countAmountOfLines);
					dataFromTextBox = new String[countAmountOfLines-1];
					
					
					dataFromTextBox =  textBox.getText().split("\\n");
					saveData(dataFromTextBox);
					backUpData(dataFromTextBox);
					
					
						JOptionPane.showMessageDialog(null, "\t\tFile has been saved\nIf you wish to exit the program click the red x button at the top right otherwise you can keep writing",  "Action Performed Message", JOptionPane.INFORMATION_MESSAGE);
					
					
				} catch ( NullPointerException e1) {
					// TODO Auto-generated catch block
					
				}
				
				
				
	            // printToTextArea(user, classicalComputer );
	         	//System.out.println(x);
	    	
		}});
      	
		
		//The following will load the data from a txt file and store it in a JTextArea
		load.addActionListener(new ActionListener()  {
			
			public void actionPerformed(ActionEvent e)  {
				textBox.selectAll();
				textBox.replaceSelection("");
				
				String[] dataFromFile; 
				try {
					dataFromFile = new String[50];
					BufferedReader saveDataFile = new BufferedReader(new FileReader("saveData1.txt"));
					dataFromFile = loadArrayFromFile(dataFromFile,saveDataFile );
					
					if (!dataFromFile[0].equals("")){
						for (int index = 0; index < dataFromFile.length-1; index++){
						if (dataFromFile[index].equals("") && dataFromFile[index+1].equals("") ){
							
						}else{
							textBox.append(dataFromFile[index] + "\n");
						}
					
						
					}
						JOptionPane.showMessageDialog(null, "File Loaded Sucessfully",  "Action Performed Message", JOptionPane.INFORMATION_MESSAGE);
					}
					else {
						JOptionPane.showMessageDialog(null, "File Appears to be empty\nTry entering information into the text box and press save",  "Action Performed Message", JOptionPane.ERROR_MESSAGE);
					
					}
				} catch ( NullPointerException e1) {
					// TODO Auto-generated catch block
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				
	            // printToTextArea(user, classicalComputer );
	         	//System.out.println(x);
	    	
		}});
      	
      	
      	window.add(panel);
      	window.setVisible(true);
	}//Method - micrsoftWordProgram
	
   	public static void shutDownSequence() throws InterruptedException, IOException{
      	/*
      	 * Description - This method acts as the exit part of the program. If the user has decided ("yes") they would like to shut down their system, a back up process
      	 * will happen while it is shutting down. With that being said, the saveData will write to the backUp file. 
      	 */
   		final JFrame window = new JFrame("Shut Down Sequence...");
		JPanel panel= new JPanel();
		window.setSize(1000, 400);
		panel.setLayout(null);
		
		JButton yesProceedButton = new JButton("Yes, Shut Down");
		yesProceedButton.setBounds(20,100,350,60);
		panel.add(yesProceedButton);
		
		JButton noButton = new JButton("No, Don't Shut Down");
		noButton.setBounds(500,100,350,60);
		panel.add(noButton);
	
		
		yesProceedButton.addActionListener(new ActionListener()  {
			
			public void actionPerformed(ActionEvent e)  {
			
				try {
					 window.setVisible(false);
						frame.setVisible(false);
						
					 BufferedReader file = new BufferedReader(new FileReader("saveData1.txt"));
						 String[] backUpArray = new String[50];
						 backUpArray = loadArray(backUpArray, file);
						 
						
						if (trackGoogleChromeAction == true){
							encrypt(file);
						}
						
						//The following is used to check that if the saveData file has been encrypted, it will have a signature at the bottom of the file "HACKED", 
						//If its nots encrypted then method will proceed to backup the data in the backuptxt file (taking the savedata file and placing the data into the back up file)
						 if (!backUpArray[backUpArray.length-1].contains("HACKED") ){
							 backUpData(backUpArray);
						 }
					
					
						
						
						 file.close();
				} catch (NullPointerException e1) {
					// TODO Auto-generated catch block
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				
	            // printToTextArea(user, classicalComputer );
	         	//System.out.println(x);
	    	
		}});
		
		
		noButton.addActionListener(new ActionListener()  {
			
			public void actionPerformed(ActionEvent e)  {
			
				try {
					window.setVisible(false);
					
				
				} catch (NullPointerException e1) {
					// TODO Auto-generated catch block
					
				}
				
				
				
	            // printToTextArea(user, classicalComputer );
	         	//System.out.println(x);
	    	
		}});
		
		window.add(panel);
		window.setVisible(true);
      	
	}//Method - shutDownSequence
	
	
	public static void printList(String[] array){
      	//Description - printing the list of a given array in a specific orientation of (1. array[0] "\n"2. array[1]).
      	for (int index = 0; index < array.length; index++){
             	System.out.println(index+1 + ". " + array[index]);
      	}
	}//Method - printList
	
	
	public static String[] loadArrayFromFile(String[] array, BufferedReader file) throws IOException{
      	//Description - retrieving the read file that will be used to store each line into an array and return that array to be loaded into another array.
      	for (int index = 0; index < array.length; index++){
             	array[index] = file.readLine();
      	}
       //	file.close();
      	return array;
	}//Method - loadArray
	
	
	public static void overWritePasswordFile(String oldPassword, String newPassword) throws IOException{
		/*
		 * Description - This method is take the old password and be replaced with the new password in the exact position in the txt file. This method will will
		 * search through each password in the passwordFile.txt and if it finds that old password, it will then be replaced by a new password in that exact position
		 */
		
		PrintWriter write = new PrintWriter("PasswordFiles.txt");
		try{
  
         				
		
         	
			
         	for (int index = 0; index <passwordArray.length; index++ ){
               if (oldPassword.equals(passwordArray[index])){
            	   passwordArray[index] = newPassword;
            	   write.print( passwordArray[index]+"\n");
               }else{
            	   write.print(passwordArray[index] +"\n");
               }
               	
         	}
         write.close();
         passwordFile.close();
  	} catch(FileNotFoundException e){
         	e.printStackTrace();
  	}
	}//Method - overWritePasswordFile
	
   	public static void backUpData(String[] data){
      	/*
      	 * Description - This method will be used to back Up the saveData by storing the saveData file to a back Up file
      	 */
      	try{
             	PrintWriter write = new PrintWriter("backUp1.txt");
             	for (int index = 0; index <data.length; index++ ){
                   	write.print(data[index]+"\n");
                   	
             	}
             	write.close();
      	} catch(FileNotFoundException e){
             	e.printStackTrace();
      	}
      	
	}//Method - backUpData
   	
   	public static String[][] generatePassword(String userItem) {
   		
   		/*
		 * Description - This method is used to perform and get the number of possible combiantions given a string value. The way this method works is by looking at each character of the string seperately, a new combination would be produced by moving each character along the string before returning back into its original position.
		 *  For example, 1234
		
			-All the possible combinations will be stored in a 2D String array
			Looking at the first chacter “1”, the possible combinations that are produced are:
			
			2134
			2314
			2341
			-------			(THE '1' is BEING MOVED OVER, while keeping the other indices the same (the order in which they appear don't change))
			Now looking at the second character “2”
			1324
			1342
			2134
			-------
		*/
		
   		
		int amountOfCombinations = userItem.length() * (userItem.length() - 1);
		
		String[][] arrayList = new String[amountOfCombinations + 1][userItem.length() + 1];
		
		
		duplicateList = new String[amountOfCombinations ][userItem.length() + 1];
		counterForDuplicateList =0;
		
		//Having the first row to be the original password
		for (int index = 0; index < userItem.length(); index++) {
			arrayList[0][index] = "" + userItem.charAt(index);
			System.out.print(arrayList[0][index]);
		}
		
		
		System.out.println("\n----");
		
		
		//Used to keep track of the different combinations occur
		int trackStartComb = 0;
		int trackEndComb = userItem.length() - 1;
				
		//Counter is used to specificy which index the alrogithim is currently looking
		int counter = 1;
		
		
		for (int row = 1; row <= amountOfCombinations; row++) {
			if (trackStartComb == trackEndComb) {
				counter++;
				trackEndComb += userItem.length() - 1;
				
			}
			
			arrayList[row] = loadTempArray(userItem);
			for (int index = row - 1; index < 10000; index++) {
				int count = 0;
				
				//The if counter blocks are used to swap position based on the index value, the "count" vairable is used to keep track of how many times the method swaps positon
				
					//This block just moves the first index all the way down to the end of the string
						/*
							 * 2134
							   2314
							   2341
			    		 */
					if (counter == 1) {	
					arrayList[row] = arrayList[row - 1];
					String temp = arrayList[row][index + 1];
					arrayList[row][index + 1] = arrayList[row][index];
					arrayList[row][index] = temp;
					count++;
					if (count == 1) {
						break;
					}
					
					
					
					//NOTE the following else if block, throughout is only designed for 4 characters, within each if block, it specify different cases the alrogithim will face
				} else if (counter > 1) {
					if ((trackEndComb - trackStartComb) == userItem.length() - 1) {
						arrayList[row] = arrayList[0];
						index = counter - 1;
					
					} else {
						arrayList[row] = arrayList[row - 1];
						index = counter;
					}
					if (counter >= 3&& (trackEndComb - trackStartComb) == userItem.length() - 1) {
						index = counter - 1;
						arrayList[row] = arrayList[0];
					} else if (counter >= 3
							&& (trackEndComb - trackStartComb) != counter - 1) {
						index = 0;
						arrayList[row] = arrayList[row - 1];
					} else if (counter >= 3) {
						index = counter - 1;
						arrayList[row] = arrayList[row - 1];
					
					}
					
					
					//The following if else if and else block will perform the swap actions
					if (trackEndComb - trackStartComb == counter - 1&& counter < userItem.length()) {
						arrayList[row][0] = arrayList[row - 1][userItem.length() - 1];
						String temp = arrayList[row - 1][index - 1];
						arrayList[row][1] = "" + userItem.charAt(0);
						arrayList[row][index + 1] = arrayList[row][index];
						arrayList[row][index] = temp;
						arrayList[row][userItem.length() - 1] = ""+ userItem.charAt(userItem.length() - 1);
		
						count++;
						if (count == 1) {
							break;
						}
					
					} else if (counter == userItem.length()	&& trackEndComb - trackStartComb == counter - 1) {
						int x = row - counter;
					
						arrayList[row] = duplicateList[row - counter + 1];
						String temp = arrayList[row][0];
						arrayList[row][0] = arrayList[row][userItem.length() - 1];
						arrayList[row][userItem.length() - 1] = temp;
				
						count++;
						if (count == 1) {
							break;
						}
					} else {
				
						String temp = arrayList[row][index + 1];
						arrayList[row][index + 1] = arrayList[row][index];
						arrayList[row][index] = temp;
				
						count++;
						if (count == 1) {
							break;
						}
					}
				}
			}
			printOneDArray(arrayList[row]);
		
			trackStartComb++;
			arrayList[0] = loadTempArray(userItem);
		}
		return duplicateList;
	}//Method - generatePassword
   	
   	
	public static String[] loadTempArray(String name) {
		/*
		 * Description - this method will take a current string and take each character of that string and apply that to each index of a string array
		 * This string array will be the length of the string
		 * BASICALLY - breaking a string and converting each character as it its own in a string array
		 */
		String[] temp = new String[name.length()];
		for (int index = 0; index < name.length(); index++) {
			temp[index] = "" + name.charAt(index);
		}
		return temp;
	}//Method - loadTempArray
	
	
	
	public static String[][] printOneDArray(String[] oneD) {
		/*
		 * Description - This method is used speicfically to print the duplicateList array  given a exist String array
		 */
		
		String[] temp = oneD;
		for (int index = 0; index < oneD.length; index++) {
			//System.out.print(oneD[index] + " ");
			duplicateList[counterForDuplicateList][index] = oneD[index];
		}
		counterForDuplicateList++;
		return duplicateList;
	}//Method - printOneDArray
	
	public static String findRowInTwoDArray(int num){
		/*
		 * Description - The following method will be used to find a speicfic row of the 2D array and return each index of that speicfic row as a string
		 */
		
		String stringFromRow = "";
		for (int row = 0; row < duplicateList.length; row++){
			
			for (int col = 0; col < duplicateList[row].length-1; col++){
				if ((num-1) == row){
					stringFromRow += "" + duplicateList[row][col];
				}
			}
			
		}
		return stringFromRow;
	}//Method - findRowInTwoDArray
	
	public static void printTwoDArray(String[][] twoD, JTextArea field) {
		/*
		 * Description - This method will print a 2D array given a exisitng 2D array and printing it into a JTextArea 
		 */
		
	try{
		for (int row = 0; row < twoD.length; row++) {
			field.append((row+1) + ".)\t");
			for (int col = 0; col < twoD[row].length; col++){
				
				
			//	System.out.print(duplicateList[row][col] + " ");
				field.append(duplicateList[row][col] + " ");
			}
			field.append("\n");
			//System.out.println();
			
		}
	} catch (NullPointerException e){
		
	}
		
	}//Method - printTwoDArray
	public static void encrypt(BufferedReader savedFile) throws IOException, NullPointerException{
      	/*
      	 * Description - This method is to simulate the encrypt stage of the Ransomware. With that being said, this method will take each line of the saveData.txt file
      	 * and add an "encryption" to it. There will also be random characters generated using the Random() method for each line in the process of encrypting the file
      	 */
      	Random character = new Random();
      	savedFile = new BufferedReader(new FileReader("saveData1.txt"));
	
      	String[] saveData = new String[50];
      	saveData = loadArrayFromFile(saveData, savedFile );
      	savedFile.close();
      	
      try{
      	for (int index = 0; index < saveData.length; index++){
      		saveData[index] = saveData[index].replaceAll(" ", "");		//removing any spaces within the element
             	
      		//The following selection statements, will be used to almost randomly generate a encryption sequence for each element within the string. 
      		if (saveData[index].length() > 3 && index < saveData.length-2){
             		if (index % 2 == 0){
             		
             			saveData[index] = saveData[index].substring(0, 3)+ "\\123\\s3?32"+saveData[index].charAt(character.nextInt(saveData[index].length())) +saveData[index].charAt(character.nextInt(saveData[index].length())) +saveData[index].charAt(character.nextInt(saveData[index].length())) +saveData[index].charAt(character.nextInt(saveData[index].length()))  + saveData[index].substring( 3) + "\\12123@z@4";
             			}
             			else{
             				saveData[index] = saveData[index].substring(0, 3)+ "azA@32"+saveData[index].charAt(character.nextInt(saveData[index].length())) + saveData[index].charAt(character.nextInt(saveData[index].length())) +saveData[index].charAt(character.nextInt(saveData[index].length())) +saveData[index].charAt(character.nextInt(saveData[index].length()))  + saveData[index].substring( 3) + "331231239";
             			}
             		}
      	
             else{
             		if (index % 2 == 0){
             			saveData[index] = saveData[index].substring(0, 1) + "#21<43.@1\\%"+saveData[index].charAt(character.nextInt(saveData[index].length()))+saveData[index].charAt(character.nextInt(saveData[index].length())) +saveData[index].charAt(character.nextInt(saveData[index].length())) +saveData[index].charAt(character.nextInt(saveData[index].length()))  + saveData[index].substring( 1) + "\\zZZ95@4";
             		}
             		else{
             			saveData[index] = saveData[index].substring(0, 1)+ "Z!32"+saveData[index].charAt(character.nextInt(saveData[index].length())) + saveData[index].charAt(character.nextInt(saveData[index].length())) +saveData[index].charAt(character.nextInt(saveData[index].length())) +saveData[index].charAt(character.nextInt(saveData[index].length()))  + saveData[index].substring( 1) + "</";
             		}
             	}
      		}
      
      	//This line of code is just a signatured to help easily process and check the if the saveData txt file has been encrypted.
      	saveData[saveData.length-1] = "HACKED";
     
      }catch(NullPointerException e){
    	  
      }
      	//The catch is used to prevent a Null Pointer exception from happening
      	
      	saveData(saveData);
	}//Method - encrypt
	
}//Class

