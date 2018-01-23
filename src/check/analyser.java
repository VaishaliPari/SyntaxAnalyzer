package check;

import java.io.*;

public class analyser {
	public static void main (String args[]) throws IOException {
		analyser objectAnalyser = new analyser();
		FileReader fileReader =new FileReader("inputFile.txt");
		FileWriter fileWriter =new FileWriter("outputFile.txt");
		BufferedReader bufferedReader = new BufferedReader(fileReader);	 
		String line,result;
		int i=1;
		while ((line = bufferedReader.readLine()) != null) {
			result=objectAnalyser.syntaxCheck(line);
			fileWriter.write("Line "+(i++)+": Syntax "+result+"\n");
		}
		bufferedReader.close();
		fileReader.close();
		fileWriter.close();		
	}
	
	/**
	 * 
	 * @param input - entire line for which the syntax check is applied
	 * @return - the Result OK/err
	 */
	public String syntaxCheck(String input) {
		String result,val[];
		String[] str = input.split(" ",4);
		if(checkPN(str[0])==false)
			result="err";
		else {
			if(nestedOperation(str[1],1)==false)
				result="err";
			else {
				if(comparator(str[2])==false) {
					val=str[3].split(" ",2);
					if(context(str[2])==false)
						result="err";
					else {
						if(comparator(val[0])==false){
							result="err";}
						else{
							if(value(val[1])==true)
								result="OK";
							else
								result="err";
						}
					}
				}
				else {
					if(value(str[3])==true)
						result="OK";
					else
						result="err";
				}
			}					
		}
		return result;		
	}
	
	/**
	 * Check if first character is P/N
	 * @param s
	 * @return
	 */
	public boolean checkPN(String s) {
		if(s.equals("N")||s.equals("P")) {
			return true;
		}
		return false;
	}
	
	/**
	 * Property can be a “.” (dot) separated alphanumeric string
	 * @param s
	 * @return
	 */
	public boolean property(String s) {
		for(int i=0;i<s.length();i++) {
			if(!((s.charAt(i)>=97 && s.charAt(i)<=122) || s.charAt(i)==46 
					|| (s.charAt(i)>=65 && s.charAt(i)<=90) 
					|| (s.charAt(i)>=48 && s.charAt(i)<=57)))
				return false;
		}
		return true;		
	}
	
	/**
	 * Check for validity of nested operations
	 * @param s - operation string
	 * @param n - 1:property 2:value
	 * @return
	 */
	public boolean nestedOperation(String s,int n) {
		int k=0;
		boolean flag=false;
		String temp="";
		if(s.charAt(k)=='@') {
			do{
				flag=operation(s.substring(k, k+6));
				k=k+7;
				temp=temp+")";
			}while(s.charAt(k)=='@' && flag==true);
			if (flag==true) {
				flag=property(s.substring(k, s.indexOf(')')));
				k=s.indexOf(')');
				if(s.substring(k, s.length()).equals(temp)==false)
					flag=false;
			}
		}
		else{
			switch(n){
			case 1:flag=property(s);
				break;
			case 2: flag=true;
			}			
		}
		return flag;
		
	}
	
	/**
	 * Check if its a valid operator
	 * @param s
	 * @return
	 */
	public boolean operation(String s) {
		switch(s) {
		case "@count":
		case "@lower":
		case "@upper":return true;
		}
		return false;		
	}
	
	/**
	 * Check if its a valid comparator
	 * @param s
	 * @return
	 */
	public boolean comparator(String s) {
		switch(s) {
		case "==":
		case ">":
		case "<":
		case ">=":
		case "<=":
		case "!=":
		case "has":
		case "not has":
		case "in":
		case "not in":
		case "contains":
		case "not contains":
		case "match":
		case "not match":
		case "added":
		case "removed": return true;
		}
		return false;		
	}
	
	/**
	 * Value can be any of the values we want to use for comparison. 
	 * This can either be scalar value or an array. 
	 * Array will be represented as “,” (comma separated) string.
	 * 
	 * @param s
	 * @return
	 */
	public boolean value(String s) {
		String[] array = s.split(",");
		boolean flag=true;
		for(int i=0;i<array.length && flag==true;i++) {
			if(array[i].length()==0) {
				flag=false;
				break;
			}
			flag=nestedOperation(array[i],2);
		}
		return flag;	
	}
	
	/**
	 * Context can be all lowercase alphabets only string.
	 * @param s
	 * @return
	 */
	public boolean context(String s) {
		for(int i=0;i<s.length();i++) {
			if(s.charAt(i)<97 || s.charAt(i)>122)
				return false;
		}
		return true;
	}
}
