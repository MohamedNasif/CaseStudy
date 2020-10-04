package ma.inpt.iccn.app;

import java.security.PrivateKey;

import ma.inpt.iccn.crypto.Cryptage;

public class AppDecrypter {
	public static void main(String[] args) {
		PrivateKey key;
		try {
			key = Cryptage.getClePrivee("C:/Test/cles/private.key");
			Cryptage.decrypterCle(key,"C:/cryptICCN/decrypt.key", "C:/cryptICCN/decrypt.key");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
