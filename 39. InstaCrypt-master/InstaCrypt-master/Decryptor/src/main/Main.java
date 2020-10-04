package main;

public class Main {

	public static void main(String[] args) { // args[0] = AES_key; args[1] = directory_to_decrypt
		if (args.length <= 1) {
			System.out.println("Usage: Path_to_AES_key_file  directory_to_decrypt (Both in String i.e. in double quotes)");
			System.out.println("Example Usage: \"C:\\Users\\user\\Desktop\\AES.key\" \"C:\\Users\\user\\Desktop\"");
		} 
		else {
			String[] target_paths = new String[] {args[1]};
			Decrypter d = new Decrypter(args[0]);
			FileItter file = new FileItter(target_paths);
			for (String f : file.getPaths()) {
				d.ProcessFile(f);
			}
		}
	}
}
