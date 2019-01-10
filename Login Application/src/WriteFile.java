import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class WriteFile {
	PrintWriter writer;

	/*
	 * Constructor to create a file writer object
	 */
	public WriteFile() {
		writer = createFile("users.txt");
	}

	/*
	 * receives string array, than in a for loop it writes each string to the file
	 */
	public void write(String[] toWrite) {

		for (String x : toWrite) {
			writer.print(x + ",");
		}
		writer.println(""); // new line
		writer.close(); // closes the file
	}

	/*
	 * this method creates a file, parameters is the filename
	 */
	private PrintWriter createFile(String fileName) {
		try {
			// creates a new file and print writer
			File file = new File(fileName);
			PrintWriter infoToWrite = new PrintWriter(new BufferedWriter(new FileWriter(file,true)));
			return infoToWrite; // returns the print writer

		} catch (IOException e) {

			System.out.println("An I/O Error Occurred");
			System.out.println(e);
			System.exit(0);
		}
		return null;

	}
}
