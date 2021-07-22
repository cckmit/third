/**
 * 
 */
package org.opoo.util;

import java.util.Scanner;

/**
 * @author lcj
 *
 */
public class LoopReadTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		Scanner scanner = new Scanner(System.in);
//		while(true){
//			System.out.print("���������ѡ��C������N ����Q �˳�]��");
//			String next = scanner.next();
//			if("2".equalsIgnoreCase(next)){
//				System.out.println("������һ��");
//			}else if("1".equalsIgnoreCase(next)){
//				System.out.println("�������δ���...");
//			}else if("9".equalsIgnoreCase(next)){
//				System.out.println("�˳�");
//				break;
//			}
//		}
//		Boolean.getBoolean("stepByStep");
		
		System.setProperty("stepByStep", "true");
		
		//�Ƿ񵥲�ִ��
		boolean stepByStep = Boolean.getBoolean("stepByStep");
		for(int i = 0 ; i < 100 ; i++){
			if(stepByStep){
				char option = getInputOption(i);
				if('c' == option){
					//ִ��
				}else if('n' == option){
					continue;
				}else if('q' == option){
					System.out.println("�˳�");
					break;
				}
			}
			
			System.out.println(i + " ...");
		}
	}
	
	private static char getInputOption(int index){
		Scanner scanner = new Scanner(System.in);
		while(true){
			System.out.print("������ʼǨ�Ʒ���" + index + "����C ִ��/N ����/Q �˳���:");
			String next = scanner.next();
			if(next != null && next.length() > 0){
				char x = next.toLowerCase().charAt(0);
				if(x == 'c' || x == 'n' || x == 'q'){
					return x;
				}
			}
			//System.out.println();
		}
	}

}
