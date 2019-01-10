import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

// Anne Hu 2017

public class ReadFile {
	static File users = new File("users.txt");
	static String [] indiData;

	/*
	 * This method is given the string to search and the type of string to search given an index number to check for 
	 * (to differentiate the different types, username, password)
	 */
	public static String[] getFileInfo(String infoToSearch, int indexNum) {

		try {
			BufferedReader getInfo = new BufferedReader(new FileReader(users));
			String info = getInfo.readLine();

			// splits data into an array seperated by commas, and if it found its infomation, it will return the string array
			while (info != null) {
				indiData = info.split(",");
				if (indiData[indexNum].equals(infoToSearch)) {
					return indiData;
				}
				info = getInfo.readLine();
			}
			getInfo.close();
		}

		catch (FileNotFoundException e) {
			System.out.println("Couldn't Find the File");
			System.exit(0);
		}

		catch (IOException e) {
			System.out.println("An I/O Error Occurred");
			System.exit(0);

		}
		return indiData;
	}
}
