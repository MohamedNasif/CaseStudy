package rsa;

import java.security.InvalidKeyException;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;

public class RSAmanager {
	public static void main(String[] args) {
		String publicKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBANFxyP0FzmSAAXIWuHjArAbmNcz9bM8+tWdJEyj61HZVkSnB4NhPqumf4HhYojzzLcPw4RmKDroh1oXeyecnkl8CAwEAAQ==";
		String privateKey = "MIIBVgIBADANBgkqhkiG9w0BAQEFAASCAUAwggE8AgEAAkEA0XHI/QXOZIABcha4eMCsBuY1zP1szz61Z0kTKPrUdlWRKcHg2E+q6Z/geFiiPPMtw/DhGYoOuiHWhd7J5yeSXwIDAQABAkBWDCZj9Xe3CHQPmFAPpDDI4Nmnr1J9zqiel4IcqviQ6JX+Q3ItV+vBFouBiMqQCjwPHnQBj5T8010ZA235BqEZAiEA91Q8YIpELmHtZqKcLMsz5I6bIFbWR3fUEmWBQX2JMe0CIQDYyYmQ/kNh5fvtoXpb5fHxbZkKBMuoLu7PwEErw2o7+wIhAJ/PE/wlGw0FRYotw8/FtNAXvPBWa7peGO+SGsSgQse5AiEAmtCCT2tlD3rASNlymkJCX9mW2xLFpduua+quH+Sz7xUCIQDpgH8NWK9p85FuJPidXilBjBwhn3EJfOWGTHDTNf9t2w==";
		
		String plainText = "Welcome to RSA";
		String plainAfterEncryption = null;
		//String 값을 byte로 가져옴
		byte[] bufferPublicKey = Base64.decodeBase64(publicKey.getBytes());
		byte[] bufferPrivateKey = Base64.decodeBase64(privateKey.getBytes());
		
		PublicKey publicKey1 = null;
		PrivateKey privateKey1 = null;
		
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			//byte로 가져온 것을 키스펙을 통해 PublicKey에 입력됨.
			X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(bufferPublicKey);
			publicKey1 = keyFactory.generatePublic(publicKeySpec);
			
			PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(bufferPrivateKey);
			privateKey1 = keyFactory.generatePrivate(privateKeySpec);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e){
			e.printStackTrace();
		}
		
		String encryptedText = null;
		
		try {
			Cipher cipher = Cipher.getInstance("RSA");
			//공개키 이용 암호화
			cipher.init(Cipher.ENCRYPT_MODE, publicKey1);
			byte[] bufferCipher = cipher.doFinal(plainText.getBytes());
			String cipherBase64 = Base64.encodeBase64String(bufferCipher);
			encryptedText = cipherBase64;
			
			//개인키 이용 복호화
			byte[] bufferCipher1 = Base64.decodeBase64(cipherBase64.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, privateKey1);
			byte[] bufferPlain1 = cipher.doFinal(bufferCipher);
			plainAfterEncryption = new String(bufferPlain1);			
		} catch (NoSuchAlgorithmException e){
			e.printStackTrace();
		} catch (NoSuchPaddingException | InvalidKeyException
				| IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}
		
		System.out.println("plainText : " + plainText);
		System.out.println("after encrypt : " + encryptedText);		
		System.out.println("after decrypt : " + plainAfterEncryption);
		
	}
}
