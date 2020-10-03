package aes;
import java.io.File;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import javax.crypto.spec.SecretKeySpec;

public class Main {
	//searchDirectory를 접근하기 위해 하나의 객체를 생성한다.
	SearchDirectory dir = new SearchDirectory();		

	//알고리즘 방식을 설정.
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";
	
	
    void myMain(){
//		generateKeyString 함수에서 random키를 생성한 것을 받아온다.
    	String key = generateKeyString();
    	
//    	파일을 전부 암호화 set.
    	//파일의 리스트를 받음.
    	//암호화가 안되어 있는 파일은 decryptedFileList
    	//암호화가 되어있는 파일은 encryptedFileList에 추가.
    	dir.getFileList("/Users/hyunjinkim/sicProject/");
    	
    	//모든 파일에 대해 AES방식의 암호화를 진행
		encryptAll(key);
		//암호화된 파일이라는 것을 알려주기 위해서 .encrypted라는 확장자를 붙임.
		dir.setEncrypted();
		
//		파일을 전부 복호화 set.
		dir.getFileList(System.getProperty("user.home") + "\\Desktop\\");
		//모든 파일을 복호화를 진행.
		decryptAll(key);
		//.encrypted를 제거하고 원래 확장자로 돌아옴.
		dir.setDecrypted();
	}
    
	//랜덤한 16자리의 키를 생성하여 String으로 반환.
	String generateKeyString() {
		//실제 random Key를 반환해주는 클래스의 객체를 생성.
		RandomKeyGenerator rkg = new RandomKeyGenerator();
		//객체의 .getRandomKey(n)으로 불러서 랜덤키를 저장함.
		String randomKeyString = rkg.getRandomKey(16);
		
		//PrintWriter을 이용해서 UTF-8형식으로 text파일에다 생성된 key값을 프로젝트 폴더 내에 저장.
		try {
			PrintWriter keyWriter;
			keyWriter = new PrintWriter("key.txt", "UTF-8");
			keyWriter.println(randomKeyString);
			keyWriter.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return randomKeyString;
	}
	
	//모든 파일을 받은 key값을 이용하여 복호화를 진행.
	void decryptAll(String key) {
		//암호화된 모든 파일의 개수를 찾아내여 size에 저장.
		int size = dir.encryptedFileList.size();
		//모든 파일에 대해 복호화를 진행.
		for(int i = 0; i < size; i++) {
			decrypt(key, dir.encryptedFileList.get(i), dir.encryptedFileList.get(i));			
		}
	}
	
	//모든 파일을 받은 key값을 이용하여 암호화를 진행.
	void encryptAll(String key) {
		//암호화가 안되어있는 모든 파일의 개수를 찾아내어 size에 저장.
		int size = dir.decryptedFileList.size();
		//모든 파일에 대해 암호화를 진행.
		for(int i = 0; i < size; i++) {
			encrypt(key, dir.decryptedFileList.get(i), dir.decryptedFileList.get(i));
		}
	}

	//실제 암호화를 하는 함수. 
    private static void doCrypto(int cipherMode, String key, File inputFile, File outputFile) 
    		throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, 
    		IOException, IllegalBlockSizeException, BadPaddingException {
    	//AES방식의 키 값을 받아옴.
    	Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
    	//AES방식의 Ciper을 생성하여 받아옴.
    	Cipher cipher = Cipher.getInstance(TRANSFORMATION);
    	//Cipher을 받은 모드와 key를 이용하여 초기화.
		cipher.init(cipherMode, secretKey);
		
		//inputFile에 대한 stream을 생성.
		FileInputStream inputStream = new FileInputStream(inputFile);
		//inputFile의 길이를 int형으로 받아서 byte값을 생성.
		byte[] inputBytes = new byte[(int) inputFile.length()];
		//inputStream에서 inputByte를 읽어들임.
		inputStream.read(inputBytes);
		//outputBytes에 초기화된 방식에 따라 암호화 또는 해독작업을 하여 나온 결과값을 넣는다.
		byte[] outputBytes = cipher.doFinal(inputBytes);
		
		//outputFile에 대한 outputStream을 생성.
		FileOutputStream outputStream = new FileOutputStream(outputFile);
		//outputBytes를 이용하여 outputStream에 쓴다.
		outputStream.write(outputBytes);
		
		//stream을 종료함. 
		inputStream.close();
		outputStream.close();
    }
	
    //encrypt모드를 입력된 key와 inputFile, outputFile를 받아서 doCrypto를 이용해 암호화를 진행.
    public static void encrypt(String key, File inputFile, File outputFile){
        try {
			doCrypto(Cipher.ENCRYPT_MODE, key, inputFile, outputFile);
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
				| BadPaddingException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    //decrypt모드를 입력된 key와 inputFile, outputFile를 받아서 doCrypto를 이용해 암호화를 진행.
    public static void decrypt(String key, File inputFile, File outputFile){
        try {
			doCrypto(Cipher.DECRYPT_MODE, key, inputFile, outputFile);
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
				| BadPaddingException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Main main = new Main();
		main.myMain();
	}

}
