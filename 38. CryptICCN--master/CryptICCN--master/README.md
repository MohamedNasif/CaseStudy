# CryptICCN
Ransomware based in Java
# Warning
CryptICCN is a Java based ransomware developed by Cybersecurity and Digital Confidence Engineer students from INPT of Rabat. This project is intended for exclusively educational use. We advise you to use a virtual machine for your tests to avoid any risk of losing your personal data.
# How the malware works
![alt text](https://github.com/bassam269/CryptICCN-/blob/master/model.png)

● The attacker generates the asymmetric key pair using RSA-2048.

● He places the public key in the malware he sends to the victim.

● When running the victim's malware, a symmetric key will be generated using AES-256.

● The victim files will be encrypted using the AES-256 key with the CBC mode.

● The AES-256 symmetric key used for encryption will be encrypted using the public key contained in the malware and will be stored at the
victim in the directory "C: /cryptICCN/decrypt.key".

When the victim has to pay the required ransom, the hijacking must end. As a result, the malware must decrypt the victim's files. Our program will work as follows:

● The attacker sends him the RSA-2048 asymmetric private key

● The victim decrypts the AES-256 symmetric key using the asymmetrical key

● The victim decrypts his files using the AES-256 symmetric key

# Running the malware on a victim machine
After running the malware, a graphical interface is displayed to tell the victim what happened to their files and how to recover them.

![alt text](https://github.com/bassam269/CryptICCN-/blob/master/cryptICCN.png)
