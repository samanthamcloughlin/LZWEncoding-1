import java.io.*;
import java.util.*;

public class LSWEncoding {
	
	public LSWEncoding() {
		
	}

	public void encoding(String input) throws IOException {
		String inputFile = input;
		BufferedReader br = new BufferedReader(new FileReader(inputFile));
		BufferedWriter bw = new BufferedWriter(new FileWriter(inputFile.substring(0,inputFile.length()-4)+" encoded.txt"));
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
					bw.write(dictionary.indexOf(check) + ",");
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
		if(!dictionary.contains(concat)){
			dictionary.add(concat);
		}
		int lastIndex = dictionary.indexOf(concat);
		
		System.out.println("lastIndex: "+lastIndex+",="+dictionary.get(lastIndex));
		bw.write(""+lastIndex+",");
		System.out.println(dictionary.toString());
		bw.write(";");
		for(int i=256;i<dictionary.size();i++){
			bw.write(dictionary.get(i).length()+"="+dictionary.get(i)+",");
		}
		bw.close();
		br.close();
	}
	
	public void decoding(String input) throws IOException{
		String outputFile = input.substring(0,input.length()-12)+".txt";
		BufferedWriter decodeWriter = new BufferedWriter(new FileWriter(outputFile));
		BufferedReader br2 = new BufferedReader(new FileReader(input));	
		ArrayList<String> dictionary2 = new ArrayList<String>();
		for(int i=0;i<256;i++) {
			dictionary2.add(""+(char)i);
		}
		char temp = (char)br2.read();
		String value = "";
		while(temp != ';') {
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
			String comma=""+(char)br2.read();
			value="";
		}
		
		System.out.println("dict:");
		for (int k=0;k<dictionary2.size();k++){
			System.out.println(""+k+":"+dictionary2.get(k));
		}
		System.out.println("size:"+dictionary2.size());
		br2.close();
		BufferedReader br3 = new BufferedReader(new FileReader(input));
		String code = "";
		while(temp != ';') {
			temp = (char)br3.read();
			if(temp==';') {
				break;
			}
			while(temp != ',') {
				code += ""+temp;
				temp = (char)br3.read();
			}
			int codeValue = Integer.parseInt(code);
			decodeWriter.write(dictionary2.get(codeValue));
			code="";
			
		}
		br3.close();
		decodeWriter.close();
	}
}