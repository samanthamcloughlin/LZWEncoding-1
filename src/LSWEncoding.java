import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class LSWEncoding {

	public void encoding(String input) throws IOException {
		String inputFile = input;
		BufferedReader br = new BufferedReader(new FileReader(inputFile));
		BufferedWriter bw = new BufferedWriter(new FileWriter(inputFile+".txt"));
		String p = "";
		String c = "" + (char)br.read();
		String concat = "" + c;
		
		//make the dictionary
		//initialize it for 0-255
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
					bw.write(dictionary.indexOf(check) + " ");
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
		bw.write(";");
		for(int i=0;i<dictionary.size();i++){
			bw.write(dictionary.get(i).length()+"="+dictionary.get(i)+",");
		}
		bw.close();
	}
}