package main;

import java.io.File;
import java.util.ArrayList;

public class FileItter {

	/*
	 * Class constructor will iterate though target paths (Not a good idea actually, may get updated in future).
	 * Function itterFiles recursively walks directory finding files.
	 */

	private ArrayList<String> paths;
	private ArrayList<File> directory_paths;
	
	public ArrayList<String> getPaths() {
		return this.paths;
	}
	
	private void itterFiles(File[] files) {
		try {
			for (File file : files) {
				if (file.isDirectory()) { // recursion
					itterFiles(file.listFiles());
				} 
				else {
					this.paths.add(file.getPath());
				}
			}

		} 
		catch (NullPointerException e) {
			System.out.println(e);
		}
	}

	public FileItter(String[] paths) {

		this.paths = new ArrayList<String>();
		this.directory_paths = new ArrayList<File>();

		for (String path : paths) {
			System.out.println(path);
			File file = new File(path);

			if (file.isDirectory()) {
				directory_paths.add(file);
			} 
			else {
				this.paths.add(path);
			}
		}
		
		this.itterFiles(this.directory_paths.toArray(new File[this.directory_paths.size()]));
		
	}
}
