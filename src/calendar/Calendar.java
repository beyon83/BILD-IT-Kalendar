package calendar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class Calendar {
	
	/**
	 * @author Bojan Aleksic
	 * 
	 * Naime, program koji trebamo napisati treba da pita korisnika 
	 * da unese mjesec i godinu te da mu ispiše, kao na slici ispod, 
	 * kalendar za taj mjesec i tu godinu. Kada program ispiše dati 
	 * mjesec korisniku na konzoli, program onda pita korisnika da 
	 * li želi unijeti neki reminder za određeni dan ili želi vidjeti 
	 * kalendar za neki drugi mjesec. Ukoliko korisnik želi neki 
	 * podsjetnik/reminder/note za određeni dan, program treba da sačuva 
	 * korisnikov unos i da ga prikaže pri sljedećem pokretanju programa. 
	 * 
	 *        January 2013
	 * ---------------------------      
	 * Sun Mon Tue Wed Thu Fri Sat
	 *          1   2   3   4   5
	 *  6   7   8   9  10  11  12
	 * 13  14  15  16  17  18  19
	 * 20  21  22  23  24  25  26
	 * 27  28  29  30  31
	 */
	
	/** ArrayList for storing reminder informations from the user */
	public static ArrayList<Calendar> list = new ArrayList<>();
	
	/** Data fields for year, month, day of the week, date and user's reminder */
	private int year;
	private int month;
	private int dayOfWeek;
	private int date;
	private String reminder;
	
	/** I've instantiated File object in the data field, because I use 
	 * the same object along with the couple of other methods */
	public static File file = new File("calendar_reminder.txt");
	
	/** Empty/default constructor */
	Calendar() {
		
	}
	
	/** Create constructor for the calendar and set parameters */
	Calendar(int year, int month, int dayOfWeek, int date, String setReminder) {
		this.year = year;
		this.month = month;
		this.dayOfWeek = dayOfWeek;
		this.date = date;
		this.reminder = setReminder;
	}
	
///////////////////////////////////////////////////////////////////////////////////
	
	public static void printMonth(int month, int year) {
		/** Print header of the calendar */
		System.out.println("       " + getMonthName(month) + " " + year);
		System.out.println("---------------------------");
		System.out.println("Sun Mon Tue Wed Thu Fri Sat");
		
		/** Instantiate an GregorianCalendar object */
		GregorianCalendar calendar = new GregorianCalendar();
		
		/** Set month and year based on user's input */
		calendar.clear(); // clear default properties of the current month
		calendar.set(year, month - 1, 1); // set year and month based on user's input
       
        /** Get the first day in the month */
        int startOfMonth = calendar.get(GregorianCalendar.DAY_OF_WEEK);
       
        /** Obtain number of days in a month */
        int numOfDays = calendar.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        
        /** Invoke method with first day in the month and actual days in the months */
        printMonthBody(startOfMonth, numOfDays);
		
	}
	
////////////////////////////////////////////////////////////////////////////////	
	
	public static void printMonthBody(int startOfMonth, int numOfDays) {
		
		int dayCounter = 0; // Counter for counting days in a week
		
		/** Enter empty spaces before first day of the month */
		for(int i = 1; i < startOfMonth; i++) {
			System.out.print("    ");
			dayCounter++; // Increment day counter
		}
		
		/** Iterate through actual count of certain month */
		for(int i = 1; i <= numOfDays; i++) {
			System.out.printf("%2d", i);
			dayCounter++; // Increment day counter 
			if(dayCounter == 7) { // When day counter reach 7
				dayCounter = 0;  // reset it so it can start from 0 again
				System.out.println(); // and print new line
			} else {
				System.out.print("  "); // Otherwise, print empty spaces between days
			}
		}
	}
	
/////////////////////////////////////////////////////////////////////////////	
	
	public static String getMonthName(int month) {
		/** Initialize an array of names of the months */
		String[] monthName = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November" , "December"};
		String str = ""; // Create empty string for storing name of the month
		for(int i = 0; i < monthName.length; i++) {
			if(month == i) {
				str = monthName[i-1]; // Indices in array are 0-based, so January is 0, February 1, etc.
			} else if(month == 12) { 
				str = monthName[11];
			}
		}
		return str;
	}
	
////////////////////////////////////////////////////////////////////////////////
	
	/** Set up getters and setters for data fields */
	
	public void setYear(int year) {
		this.year = year;
	}
	
	public void setMonth(int month) {
		this.month = month;
	}
	
	public void setDayOfWeek(int dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
	
	public int getYear() {
		return this.year;
	}
	
	public int getMonth() {
		return this.month;
	}
	
	public int getDayOfWeek() {
		return this.dayOfWeek;
	}
	
	public void setReminder(String reminder) {
		this.reminder = reminder;
	}
	
	public void setReminderDate(int date) {
		this.date = date;
	}
	
	public String getReminder() {
		return this.reminder;
	}
	
	public int getReminderDate() {
		return this.date;
	}
	
/////////////////////////////////////////////////////////////////////////////////
	
	public static void displayReminderMessage(int year, int month) {
		Scanner input = new Scanner(System.in);
		System.out.println();
		/** Display message information menu */
		System.out.println("\nChoose one of the options below: ");
		System.out.println("1. Set reminder for certain day in this month. ");
		System.out.println("2. Choose another month. ");
		System.out.println("3. Exit calendar. ");
		System.out.println("(Enter \"1\" for reminder, \"2\" to view another month, or enter \"3\" to exit the program.)");
		
		readFromFile(); // Read from the file if there is a reminder saved in it
		
		/** User's option */
		String option = input.next();
		
		/** Option 1 - Set reminder */
		if(option.equals("1")) {
			System.out.println("Enter day or date in this month that you want to create reminder for: ");
			/** Enter date for reminder */
			int dayOfWeek = input.nextInt();
			/** Copy date for display purpose and pass it to the getDayOfWeek(date) argument */
			int date = dayOfWeek; 
			
			System.out.println("Set reminder for: ");
			
			/** Invoking the method for setting the reminder for certain date */
			getDayOfWeek(year, month, dayOfWeek, date);
			
			/** Prompt the user to enter reminder */
			String setReminder = input.nextLine(); // Having an issue with the Scanner's nextLine() method
			setReminder = input.nextLine();        // so I had to put same input twice.
			
			/** Call constructor to store information to the ArrayList */
			Calendar reminderCalendar = new Calendar(year, month, dayOfWeek, date, setReminder);
			/** Store informations into ArrayList passing by constructor */
			list.add(reminderCalendar);
			
			writeToFile(); // Write informations to the file
			displayReminderMessage(month, year); // Call display calendar-menu method
			
		/** Option 2 - Pick another month */
		} else if(option.equals("2")) {
			System.out.println("Enter a month (as a number 1 - 12): ");
			month = input.nextInt(); // Insert a month
			System.out.println("Enter year: ");
			year = input.nextInt(); // Insert a year
			printMonth(month, year); // Call printMonth() method and pass arguments
			displayReminderMessage(month, year); // Display menu 
			
		/** Option 3 - Exit the program */	
		} else if(option.equals("3")) {
			System.out.println(">> Exit.");
			System.exit(0);
		}
		input.close();
	}
	
//////////////////////////////////////////////////////////////////////////////////
	
	public static void writeToFile() {
	//	File file = new File("calendar_reminder.txt");
		try {
			/** Create an PrintWriter object to write some data to the file */
			PrintWriter writer = new PrintWriter(file);
			for(Calendar l: list) {
				writer.println("*********************************");
				/** Write year, month and date/day to the file */
				writer.println(l.getYear() + "-" + l.getMonth() + "-" + l.getDayOfWeek());
				/** Write user's message/reminder/note */
				writer.println(l.getReminder());
				writer.println("*********************************");
			}
			writer.close(); // close the writer
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
	}
	
//////////////////////////////////////////////////////////////////////////////////
	
	public static void readFromFile() {
	//	File file = new File("calendar_reminder.txt");
		try {
			/** Create a Scanner object to read data from the file */
			Scanner readScan = new Scanner(file);
			/** Check if file contains some data */
			if(readScan.hasNext()) {
				System.out.println("\nYou have one saved reminder: ");
			} 
			while(readScan.hasNext()) {
				/** Read everything from the file in one single string */
				String str = readScan.nextLine();
				System.out.println(str);
			}
			readScan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
//////////////////////////////////////////////////////////////////////////////////	
	
	public static void getDayOfWeek(int year, int month, int dayOfWeek, int date) {
		
		/** Call GregorianCalendar's constructor with specified year, month and day of the week */
		GregorianCalendar calendar = new GregorianCalendar(year, month-1, dayOfWeek);
		
		/** Initialize an array of days in the week */
		String[] dayOfWeekArray = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
		
		/** Initialize an array of months */
		String[] monthsArray = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November" , "December"};
		
		year = calendar.get(GregorianCalendar.YEAR); // Get actual year using GregorianCalendar class
		month = calendar.get(GregorianCalendar.MONTH); // Get actual month using GregorianCalendar class
		System.out.println(year + " " + monthsArray[month] + " " + date + ". - " + dayOfWeekArray[calendar.get(GregorianCalendar.DAY_OF_WEEK) - 1]);;
		
	}
	
}
