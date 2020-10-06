import java.io.*;
import java.util.*;

public class LSWEncoding {

	public LSWEncoding() {

	}

	public void encoding(String input) throws IOException {
		String inputFile = input;
		BufferedReader br = new BufferedReader(new FileReader(inputFile));
		// create new file to write your encoded codestream and dictionary on
		BufferedWriter bw = new BufferedWriter(
				new FileWriter(inputFile.substring(0, inputFile.length() - 4) + " encoded.txt"));
		String p = "";
		String c = "" + (char) br.read();
		String concat = "" + c;

		ArrayList<String> dictionary = new ArrayList<String>();

		// fill in the dictionary with the first known 255 characters and codes
		for (int i = 0; i < 256; i++) {
			String letter = "" + (char) i;
			dictionary.add(letter);
		}

		while (br.ready()) {
			// if it is already in the dictionary
			if (dictionary.contains(concat)) {
				p = concat;
			}
			// if it's not in the dictionary
			else {
				String check = "" + p;
				if (dictionary.indexOf(check) <= 10000) {
					bw.write(dictionary.indexOf(check) + ",");
					dictionary.add(concat);
					p = c;
				}
				// if the maximum number of entries in the dictionary is reached, exit the while
				// loop
				else {
					br.close();
					break;
				}
			}
			c = "" + (char) br.read();
			// update c and concat
			concat = "" + p + c;
		}
		// end of while loop case - if the next P+C isn't in the dictionary, add it
		if (!dictionary.contains(concat)) {
			dictionary.add("" + p);
		}
		// get the last index of the dictionary, write that to the file since the while loop ends
		// one turn too early
		int lastIndex = dictionary.indexOf(concat);

		bw.write("" + lastIndex + ",");
		bw.write(";");
		// output all the unknown codes to the end of the encoded file
		for (int i = 256; i < dictionary.size(); i++) {
			bw.write(dictionary.get(i).length() + "=" + dictionary.get(i));
		}
		bw.close();
		br.close();
	}

	public void decoding(String input) throws IOException {
		// get the original file name
		String outputFile = input.substring(0, input.length() - 12) + ".txt";
		BufferedWriter decodeWriter = new BufferedWriter(new FileWriter(outputFile));
		BufferedReader br2 = new BufferedReader(new FileReader(input));
		// create a new dictionary arraylist that will be filled in from the dictionary on the
		// encoded file
		ArrayList<String> dictionary2 = new ArrayList<String>();
		for (int i = 0; i < 256; i++) {
			dictionary2.add("" + (char) i);
		}
		char temp = (char) br2.read();
		String value = "";
		// the first time you read the file, you have to go through until you find ';', signifying
		// the start of the dictionary, then you read it into an array list
		while (temp != ';') {
			temp = (char) br2.read();
		}
		// setting up dictionary to decode file
		while (br2.ready()) {
			temp = (char) br2.read();
			while (temp != '=') {
				value += temp;
				temp = (char) br2.read();
			}
			// convert string value to int
			int len = Integer.parseInt("" + value);
			String dictVal = "";
			// finds full dictionary value
			for (int i = 0; i < len; i++) {
				dictVal += (char) br2.read();
			}
			dictionary2.add(dictVal);
			value = "";
		}

		br2.close();
		BufferedReader br3 = new BufferedReader(new FileReader(input));
		String code = "";
		// while you're still reading the codestream part of the file
		while (temp != ';') {
			temp = (char) br3.read();
			if (temp == ';') {
				break;
			}
			// read each word individually
			while (temp != ',') {
				code += "" + temp;
				temp = (char) br3.read();
			}
			int codeValue = Integer.parseInt(code);
			// access the code's word in the dictionary, write it
			decodeWriter.write(dictionary2.get(codeValue));
			code = "";
		}
		br3.close();
		decodeWriter.close();
	}
}