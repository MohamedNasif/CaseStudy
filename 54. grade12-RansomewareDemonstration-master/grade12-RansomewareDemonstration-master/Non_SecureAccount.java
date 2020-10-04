package FEU;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class Non_SecureAccount extends AccountClass{
	/**
	 * Name: Andrew Balmakund
	 * 
	 * Date: 01/15/2018
	 * 
	 * Purpose: This class is used to represent that class is a bad/vunerable account, meaning that it can be infected easily. Basically this class just ensures that the account bas been infected
	 * 
	 */
	

	public Non_SecureAccount() throws IOException , InterruptedException{
	
		// TODO Auto-generated constructor stub
		//OperatingSystem.desktopView();
		isAccountInfected = true;
		desktopView();
		//numBasedOnLevelOfSecurity = 0;
		System.out.println("Bad Account");
		
		
	}

}
