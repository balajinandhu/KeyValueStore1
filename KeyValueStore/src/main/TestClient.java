package main;

import java.util.Scanner;

public class TestClient {
	public static KeyValueStoreSimulator ksTest = new KeyValueStoreSimulator();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int choice;

		Scanner sc = new Scanner(System.in);
		
		do{
			System.out.println("**MENU**");
			System.out.println("1. Join node");
			System.out.println("2. Insert Key");
			System.out.println("3. Retrieve Key");
			System.out.println("4. Quit");
			System.out.println("Enter choice:");
			choice = sc.nextInt();
			sc.nextLine();
			
			switch(choice){
				case 1:
					int nodeAddr = ksTest.getRandomPort();
					ksTest.join(nodeAddr);
					break;
				case 2:
					System.out.println("Enter Song Name:");
					String key=sc.nextLine();
					System.out.println("Enter Song Link:");
					String value=sc.nextLine();
					//System.setOut(dummyStream);
					ksTest.insertKey(key, value);
					//System.setOut(originalStream);
					break;
				case 3:
					System.out.println("Enter Song Name to retrieve:");
					String fetchKey=sc.nextLine();
					String link = ksTest.play(fetchKey);
					System.out.println(link);
					break;
				default:
					break;
					
					
			}
		
		}while(choice!=4);
	}

}
