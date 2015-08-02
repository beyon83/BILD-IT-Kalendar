package calendar;

import java.util.Scanner;

public class CalendarMain {

	public static void main(String[] args) {
		
		Scanner input = new Scanner(System.in);
		
		/** Prompt the user to enter a month */
		System.out.println("Enter a month (as a number 1 - 12): ");
		int month = input.nextInt();
		
		/** Prompt the user to enter a year */
		System.out.println("Enter year: ");
		int year = input.nextInt();
		
		Calendar.printMonth(month, year); // Invoke printMonth() method
		Calendar.displayReminderMessage(year, month); // Invoke displayReminderMessage() method
		
		input.close();
	}

}

