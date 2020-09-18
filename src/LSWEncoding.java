import java.io.*;
import java.util.*;

public class LSWEncoding {
	
	public LSWEncoding() {
		
	}

	public void encoding(String input) throws IOException {
		String inputFile = input;
		BufferedReader br = new BufferedReader(new FileReader(inputFile));
		BufferedWriter bw = new BufferedWriter(new FileWriter(inputFile.substring(0,inputFile.length()-4)+" encoded.txt"));//create new file to write your encoded codestream and dictionary on
		String p = "";
		String c = "" + (char)br.read();
		String concat = "" + c;
		
		ArrayList<String> dictionary = new ArrayList<String>();
		
		for (int i = 0; i < 256; i++) //fill in the dictionary with the first known 255 characters and codes
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
				
				String check = "" + p;
				if (dictionary.indexOf(check) <= 10000) 
				{
					bw.write(dictionary.indexOf(check) + ",");
					dictionary.add(concat);
					p = c;
				}
				else //if the maximum number of entries in the dictionary is reached, exit the while loop
				{
					br.close();
					break; 
				}
			}
			c = "" + (char)br.read();
			concat = "" + p + c; //update c and concat
		}
		//end of while loop case - if the next P+C isn't in the dictionary, add it
		if(!dictionary.contains(concat)){
			dictionary.add(""+p);
		}
		int lastIndex = dictionary.indexOf(concat);//get the last index of the dictionary, write that to the file since the while loop ends one turn too early
		
		
		bw.write(""+lastIndex+",");
		bw.write(";");
		for(int i=256;i<dictionary.size();i++){//output all the unknown codes to the end of the encoded file
			bw.write(dictionary.get(i).length()+"="+dictionary.get(i));
		}
		bw.close();
		br.close();
	}
	
	public void decoding(String input) throws IOException{
		String outputFile = input.substring(0,input.length()-12)+".txt";//get the original file name
		BufferedWriter decodeWriter = new BufferedWriter(new FileWriter(outputFile));
		BufferedReader br2 = new BufferedReader(new FileReader(input));	
		ArrayList<String> dictionary2 = new ArrayList<String>();//create a new dictionary arraylist that will be filled in from the dictionary on the encoded file
		for(int i=0;i<256;i++) {
			dictionary2.add(""+(char)i);
		}
		char temp = (char)br2.read();
		String value = "";
		while(temp != ';') {//the first time you read the file, you have to go through until you find ';', signifying the start of the dictionary, then you read it into an array list
			temp = (char)br2.read();
		}
	
		while(br2.ready()) {//setting up dictionary to decode file
			temp = (char)br2.read();
			while(temp!='=') {
				value += temp;
				temp = (char)br2.read();
			}
			int len = Integer.parseInt(""+value);//convert string value to int
			String dictVal = "";
			for(int i =0;i<len;i++) {//finds full dictionary value
				dictVal += (char)br2.read();
			}
			dictionary2.add(dictVal);
			value="";
		}
		

		br2.close();
		BufferedReader br3 = new BufferedReader(new FileReader(input));
		String code = "";
		while(temp != ';') {//while you're still reading the codestream part of the file
			temp = (char)br3.read();
			if(temp==';') {
				break;
			}
			while(temp != ',') {//read each word individually
				code += ""+temp;
				temp = (char)br3.read();
			}
			int codeValue = Integer.parseInt(code);
			decodeWriter.write(dictionary2.get(codeValue));//access the code's word in the dictionary, write it
			code="";
			
		}
		br3.close();
		decodeWriter.close();
	}
}