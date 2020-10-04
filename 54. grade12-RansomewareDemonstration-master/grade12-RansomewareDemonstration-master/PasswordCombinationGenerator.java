package FEU;
import java.io.UnsupportedEncodingException;
public class PasswordCombinationGenerator {
	static String[][] duplicateList;
	static int counterForDuplicateList;
	public static void main(String[] args) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		 String[][] x = generatePassword("1234");
		 
		 String s = "Andrew4";
			byte[] bytes = s.getBytes("US-ASCII");
			for (int index = 0; index < bytes.length; index++) {
				if (bytes[index] > 33 && bytes[index] < 65) {
					System.out.println("hello");
					break;
					// System.out.printf(" %c \n ", index , index);
				}
			}
		// searchForIndex("2341");
		 
		 
		 
		String[] tempArray = { "123", "213", "231", "132", "213", "312", "132" };
	}
	public static String[][] generatePassword(String userItem) {
		int amountOfCombinations = userItem.length() * (userItem.length() - 1);
		String[][] arrayList = new String[amountOfCombinations + 1][userItem.length() + 1];
		duplicateList = new String[amountOfCombinations + 1][userItem.length() + 1];
		for (int index = 0; index < userItem.length(); index++) {
			arrayList[0][index] = "" + userItem.charAt(index);
			System.out.print(arrayList[0][index]);
		}
		System.out.println("\n----");
		int trackStartComb = 0;
		int trackEndComb = userItem.length() - 1;
		int counter = 1;
		for (int row = 1; row <= amountOfCombinations; row++) {
			if (trackStartComb == trackEndComb) {
				counter++;
				trackEndComb += userItem.length() - 1;
				// System.out.println("\ndone");
			}
			arrayList[row] = loadTempArray(userItem);
			for (int index = row - 1; index < 10000; index++) {
				int count = 0;
				if (counter == 1) {
					arrayList[row] = arrayList[row - 1];
					String temp = arrayList[row][index + 1];
					arrayList[row][index + 1] = arrayList[row][index];
					arrayList[row][index] = temp;
					// System.out.print(arrayList[row][index] );
					// System.out.println("\tIndex: " + index);
					count++;
					if (count == 1) {
						break;
					}
				} else if (counter > 1) {
					if ((trackEndComb - trackStartComb) == userItem.length() - 1) {
						arrayList[row] = arrayList[0];
						index = counter - 1;
						System.out.print("*");
					} else {
						arrayList[row] = arrayList[row - 1];
						index = counter;
					}
					if (counter >= 3 && (trackEndComb - trackStartComb) == userItem.length() - 1) {
						index = counter - 1;
						arrayList[row] = arrayList[0];
					} else if (counter >= 3	&& (trackEndComb - trackStartComb) != counter - 1) {
						index = 0;
						arrayList[row] = arrayList[row - 1];
					} else if (counter >= 3) {
						index = counter - 1;
						arrayList[row] = arrayList[row - 1];
						System.out.println("Z");
					}
					if (trackEndComb - trackStartComb == counter - 1	&& counter < userItem.length()) {
						arrayList[row][0] = arrayList[row - 1][userItem.length() - 1];
						String temp = arrayList[row - 1][index - 1];
						arrayList[row][1] = "" + userItem.charAt(0);
						arrayList[row][index + 1] = arrayList[row][index];
						arrayList[row][index] = temp;
						arrayList[row][userItem.length() - 1] = ""
								+ userItem.charAt(userItem.length() - 1);
						// arrayList[row][userItem.length()-1] = temp;
						System.out.print("\tIndex: " + index + "\t" + "Row: "
								+ row + "\t");
						count++;
						if (count == 1) {
							break;
						}
						// ADD ANOTHER ELSE IF FOR THE LAST CHARACTER
						// COMBIATIONATION SEQUENCE
					} else if (counter == userItem.length()	&& trackEndComb - trackStartComb == counter - 1) {
						int x = row - counter;
						System.out.println(x + "\t" + row);
						arrayList[row] = duplicateList[row - counter + 1];
						String temp = arrayList[row][0];
						arrayList[row][0] = arrayList[row][userItem.length() - 1];
						arrayList[row][userItem.length() - 1] = temp;
						// arrayList[row][userItem.length()-1] = temp;
						System.out.print("\tIndex: " + index + "\t" + "Row: "
								+ row + "\t");
						count++;
						if (count == 1) {
							break;
						}
					} else {
						System.out.print(arrayList[row][index + 1] + "="
								+ arrayList[row][index]);
						String temp = arrayList[row][index + 1];
						arrayList[row][index + 1] = arrayList[row][index];
						arrayList[row][index] = temp;
						// System.out.print(arrayList[row][index] );
						System.out.print("\tIndex: " + index + "\t" + "Row: "
								+ row + "\t");
						count++;
						if (count == 1) {
							break;
						}
					}
				}
			}
			printOneDArray(arrayList[row]);
			// System.out.println("\n" +
			// compareEachRowForDebugging(arrayList[row],userItem) + "\n");
			System.out.println("\tCounter:" + counter + "\t" + "start:"
					+ trackStartComb + "\tEnd:" + trackEndComb + "\n");
			trackStartComb++;
			arrayList[0] = loadTempArray(userItem);
		}
		return arrayList;
	}
	public static String[] loadTempArray(String name) {
		String[] temp = new String[name.length()];
		for (int index = 0; index < name.length(); index++) {
			temp[index] = "" + name.charAt(index);
		}
		return temp;
	}
	public static String[][] printOneDArray(String[] oneD) {
		String[] temp = oneD;
		for (int index = 0; index < oneD.length; index++) {
			System.out.print(oneD[index] + " ");
			duplicateList[counterForDuplicateList][index] = oneD[index];
		}
		counterForDuplicateList++;
		return duplicateList;
	}
	public static boolean compareEachRowForDebugging(String[] row, String name) {
		String temp = null;
		for (int index = 0; index < row.length; index++) {
			temp += row[index];
		}
		if (temp.equals(name)) {
			return true;
		} else {
			return false;
		}
	}
	public static int searchForIndex(String temp) {
		int indexNum = -1;
		String[] userChoiceInChar = translate2DArrayInto1DArray();
		for (int row = 1; row < duplicateList.length; row++) {
			if (userChoiceInChar[row].equals(temp)) {
				indexNum = row;
				break;
			}
		}
		System.out.println("\nThis string was found at " + indexNum);
		return indexNum;
	}
	public static String[] translate2DArrayInto1DArray() {
		String[] tempArray = new String[duplicateList.length];
		for (int row = 0; row < duplicateList.length; row++) {
			for (int col = 0; col < duplicateList[row].length; row++) {
				tempArray[row] = "" + duplicateList[row][col];
			}
		}
		return tempArray;
	}
	
	
	public static boolean checkForAppropriateChar(String user) throws UnsupportedEncodingException {
		boolean isPasswordSafe = true;
		boolean[] checkRequirements = new boolean[6];
		
		byte[] bytes = user.getBytes("US-ASCII");
		for (int index = 0; index < bytes.length; index++) {
			if (bytes[index] > 32 && bytes[index] < 65) {
				System.out.println("hello");
				break;
				// System.out.printf(" %c \n ", index , index);
			}
		}
		if (user.length() < 6) {
			checkRequirements[0] = false;
			System.out
					.println("Your password appears to be less than 6 characters long, usually secured websites will advise you to have a minimum of 8 characters (some allow 6 and above)");
		} else if (true) {
// add if for binary range from 48 to 56 for numbers
			//33 to 47 for special characters, 58 to 64, 91 to 96,  123 to 126 
				
				for (int index = 0; index < bytes.length; index++) {
					if (bytes[index] > 33 && bytes[index] < 65) {
						System.out.println("hello");
						break;
						// System.out.printf(" %c \n ", index , index);
					}
				}
		}
		
		
		
		
		
		
		return isPasswordSafe;
	}
	
	
	
	
	
	
}
