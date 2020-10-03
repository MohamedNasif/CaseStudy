package rsa;

import java.security.InvalidKeyException;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;

public class RSAClient {
	
	public static void main(String[] args) {
		String publicKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBANFxyP0FzmSAAXIWuHjArAbmNcz9bM8+tWdJEyj61HZVkSnB4NhPqumf4HhYojzzLcPw4RmKDroh1oXeyecnkl8CAwEAAQ==";

		String plainText = "Welcome to RSA";
		String encryptedText = null;

		//String 값을 byte로 가져옴.
		byte[] bufferPublicKey = Base64.decodeBase64(publicKey.getBytes());

		PublicKey publicKey1 = null;
		
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			//byte로 가져온 것을 키스펙을 통해 PublicKey에 입력됨.
			X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(bufferPublicKey);
			publicKey1 = keyFactory.generatePublic(publicKeySpec);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e){
			e.printStackTrace();
		}
		
		try {
			Cipher cipher = Cipher.getInstance("RSA");
			//공개키 이용 암호화
			cipher.init(Cipher.ENCRYPT_MODE, publicKey1);
			byte[] bufferCipher = cipher.doFinal(plainText.getBytes());
			String cipherBase64 = Base64.encodeBase64String(bufferCipher);
			encryptedText = cipherBase64;
		} catch (NoSuchAlgorithmException e){
			e.printStackTrace();
		} catch (NoSuchPaddingException | InvalidKeyException
				| IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}
		
		System.out.println("plainText : " + plainText);
		System.out.println("after encrypt : " + encryptedText);				
	}
}
