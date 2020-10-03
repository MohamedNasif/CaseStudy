package aes;
import java.io.File;
import java.util.ArrayList;

public class SearchDirectory {
	//암호화가 되어있는 파일
	ArrayList <File> encryptedFileList = new ArrayList<>();
	//암호화가 되어있지 않은 파일
	ArrayList <File> decryptedFileList = new ArrayList<>();
	
	File directory;
	
	//encrypt후 파일의 확장자의 마지막 값을 .encrypted로 전부 변경시킴.
	void setEncrypted() {
		boolean success;
		File changeExtension;
		//directory = new File(file.getPath());
		for(File file : decryptedFileList) {
			//changeExtension = new File(file.getPath() + ".encrypted");
			changeExtension = new File(System.getProperty("user.home") + "\\Desktop\\" + file.getName() + ".encrypted");
			success = file.renameTo(changeExtension);
			if(success) {
				System.out.printf("file encrypted : %s\n", file.getName());
			}
		}
		decryptedFileList.clear();		
	}
	
	//복호화를 한 후 .encrypted확장자를 제거함.
	void setDecrypted() {
		boolean success; 
		File changeExtension;
		for(File file : encryptedFileList) {
			if(file.getName().length() < 10)
				continue;			
			if(file.getName().substring(file.getName().length() - 10, file.getName().length()).equals(".encrypted")) {
				changeExtension = new File(file.getPath().substring(0, file.getPath().length() - 10));
				success = file.renameTo(changeExtension);
				if(success) {
					System.out.printf("file decrypted : %s\n", file.getName());
				}
			}
		}
		encryptedFileList.clear();
	}
	
	//String으로 받은 디렉토리 경로 내의 모든 폴더와 파일을 추적
	//암호화가 되어있는 파일은 encryptedFileList에
	//복호화가 되어있는 파일은 decryptedFileList에 파일을 전부 저장.
	void getFileList(String source) {
		File[] fileList;
		String fileDirectory;
		directory = new File(source);
		fileList = directory.listFiles();
		try {
			for(File file : fileList) {
				System.out.println(file);
				if(file.isFile()) {
					if(file.getName().length() < 10|| !file.getName().substring(file.getName().length() - 10, file.getName().length()).equals(".encrypted")) {
						decryptedFileList.add(file);
					} else {
						encryptedFileList.add(file);
					}
				} else if(file.isDirectory()){
					fileDirectory = file.getPath();
					getFileList(fileDirectory);					
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
