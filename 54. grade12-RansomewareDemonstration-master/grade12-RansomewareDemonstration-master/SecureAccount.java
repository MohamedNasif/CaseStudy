package FEU;

import java.io.IOException;

public class SecureAccount extends AccountClass {

	/**
	 * Name: Andrew Balmakund
	 * 
	 * Date: 01/15/2018
	 * 
	 * Purpose: This class is used to represent that class is a safe account, meaning that it can't be infected easily. However tho, even if an account is secured there is a slight chance that the account can still be infected if not careful about doing certain thing on the webd
	 * Basically this class just ensures that the account bas been infected
	 * 
	 */
	
	public SecureAccount() throws IOException, InterruptedException {
		
		// TODO Auto-generated constructor stub
		desktopView();
		System.out.println("GOOD ACCOUNT");
		
		isAccountInfected = false;
		//numBasedOnLevelOfSecurity = 1;
		
	}
	
	
	
	

}
