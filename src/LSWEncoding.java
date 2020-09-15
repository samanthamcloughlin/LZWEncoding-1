import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class LSWEncoding {
	
	

	public void encoding(String input) throws IOException {
				
		BufferedReader br = new BufferedReader(new FileReader(input));
		String p = "";
		String c = "" + (char)br.read();
		String concat = "" + c;
		
		ArrayList<String> dictionary = new ArrayList<String>();
		
		for (int i = 0; i < 256; i++)
		{
			String letter = "" + (char)i;
			dictionary.add(letter);
		}
		
		while (br.ready())
		{
			//if it is already in the dictionary 
			if (dictionary.contains(concat))
			{
				p = concat; 
			}
			else //if it's not in the dictionary
			{
				//add 
				String check = "" + p;
				if (dictionary.indexOf(check) <= 500) 
				{
					System.out.print (dictionary.indexOf(check) + " ");
					dictionary.add(concat);
					p = c;
				}
				else 
				{
					br.close();
					break; 
				}
			}
			c = "" + (char)br.read();
			concat = "" + p + c;
		}	
	}
	
	public void decoding(String input) throws IOException{
		BufferedWriter decodeWriter = new BufferedWriter(new FileWriter(input));
		BufferedReader br2 = new BufferedReader(new FileReader(input));	
		ArrayList<String> dictionary2 = new ArrayList<String>();
		char temp = (char)br2.read();
		String value = "";
		while(temp != ';') {
			temp = (char)br2.read();
		}
		while(br2.ready()) {//setting up dictionary to decode file
			while(temp != '=') {//loop to get full length number 
				value += temp;
				temp = (char)br2.read();
			}
			int len = Integer.parseInt(value);//convert string value to int
			temp = (char)br2.read();
			String dictVal = "";
			for(int i =0;i<len;i++) {//finds full dictionary value
				dictVal += (char)br2.read();
			}
			dictionary2.add(dictVal);	
		}
		br2.close();
		BufferedReader br3 = new BufferedReader(new FileReader(input));
		while(temp != ';') {
			while(temp != ',') {
				
			}
		}
	}
}