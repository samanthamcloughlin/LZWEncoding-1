import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class LSWEncoding {

	public static void main(String[] args) throws IOException {
				
		BufferedReader br = new BufferedReader(new FileReader("code.txt"));
		char p = '\0';
		char c = (char)br.read();
		String concat = "" + c;
		
		//make the dictionary
		//initialize it for 0-255
		ArrayList<String> dictionary = new ArrayList<String>();
		
		for (int i = 0; i < 266; i++)
		{
			String letter = "" + (char)i;
			dictionary.add(letter);
		}
		
		while (br.ready())
		{
		
			if (dictionary.contains(concat))
			{
				
			}
			//if it's not in the dictionary
			{
				//add 
				String check = "" + p;
				System.out.print (dictionary.indexOf(check));
				dictionary.add(concat);
				p = c;
			}
			
		}
		
		c = (char)br.read();
		concat = "" + p + c;
	}
}