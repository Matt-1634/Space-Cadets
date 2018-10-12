import java.io.BufferedReader;	// inports the BufferedReader and InputStreamReader classes
import java.io.InputStreamReader;
import java.net.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SCChallengeEmail{		// Creates our main class
	public static void main(String[] args) throws Exception{
		
		InputStreamReader isr = new InputStreamReader(System.in);	// Creates new object to get system input
		BufferedReader br = new BufferedReader(isr);	// creates the BufferedReader object to read input
		
		System.out.println("Enter an ID:");
		String id = br.readLine();
		
		String WebPage = WebAddress(id);
		
		URL Url = new URL(WebPage);	// saves the url as a url
		Scanner Scan = new Scanner(Url.openStream());	// scans the entire urlext
		
		Pattern namePattern = Pattern.compile("property=\"name\">([a-z A-Z]*)");
		while (Scan.hasNext()){
			Matcher name = namePattern.matcher(Scan.nextLine());
			if (name.find())
				System.out.println(name.group(1));
		}
	}
	public static String WebAddress(String id){
		String webHolder = "https://www.ecs.soton.ac.uk/people/";
		webHolder+=id;
		return webHolder;	
	}
}
