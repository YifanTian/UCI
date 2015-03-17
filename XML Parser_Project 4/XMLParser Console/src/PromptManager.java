import java.util.Scanner;

public class PromptManager 
{
	private Scanner userIn = null;
	
	public PromptManager()
	{
		openScanner();
	}
	
	private void openScanner()
	{
		if (userIn == null)
		{
			userIn = new Scanner(System.in);
		}
	}
	
	public void closeScanner()
	{
		if (userIn != null)
		{
			userIn.close();
		}
		userIn = null;
	}
	
	public String requestWord(String prompt)
	{
		System.out.println(prompt);
		
		String word = userIn.next().trim();
		userIn.nextLine();
		
		return word;
	}
	
	public String requestString(String prompt)
	{
		System.out.println(prompt);
		
		return userIn.nextLine();
	}
	
	public int requestInt(String prompt)
	{
		System.out.println(prompt);
		
		int value = -1;
		try {
			value = userIn.nextInt();
			userIn.nextLine();
		} catch (Exception e) { value = -1;}
		return value;
	}
}