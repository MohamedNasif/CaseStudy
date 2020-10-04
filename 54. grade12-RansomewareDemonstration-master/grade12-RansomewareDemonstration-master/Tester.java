package FEU;

import java.io.IOException;

public class Tester {

	public static void main(String[] args) throws IOException, NullPointerException, InterruptedException {
		// TODO Auto-generated method stub

		AccountClass userAccount = new AccountClass();
		
		if (userAccount.isEncrypted == false){
		userAccount.loadScreenBeforeInfected();
		
		}else{
			userAccount.loadScreenAfterInfected();
		}
		
	OperatingSystem OS = new OperatingSystem();
	
		OS.createLoginInformation();
		
	
		System.out.println("\n\n\n");
		
		while (OS.userCredientialsExist == false){
			System.out.print( " ");
		}
		
	
	
		userAccount = new AccountClass();
	
		/*
		if (userAccount.toString().equals("This Account is: SAFE" )){
			
			SecureAccount goodAccount = new SecureAccount();
			
		}else{
			
			Non_SecureAccount badAccount = new Non_SecureAccount();
		
		}
	
		*/
		
		
	
	
	}
}
