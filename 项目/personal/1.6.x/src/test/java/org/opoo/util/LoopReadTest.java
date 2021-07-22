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
//			System.out.print("请输入你的选择（C本条、N 条、Q 退出]：");
//			String next = scanner.next();
//			if("2".equalsIgnoreCase(next)){
//				System.out.println("处理下一条");
//			}else if("1".equalsIgnoreCase(next)){
//				System.out.println("继续本次处理...");
//			}else if("9".equalsIgnoreCase(next)){
//				System.out.println("退出");
//				break;
//			}
//		}
//		Boolean.getBoolean("stepByStep");
		
		System.setProperty("stepByStep", "true");
		
		//是否单步执行
		boolean stepByStep = Boolean.getBoolean("stepByStep");
		for(int i = 0 ; i < 100 ; i++){
			if(stepByStep){
				char option = getInputOption(i);
				if('c' == option){
					//执行
				}else if('n' == option){
					continue;
				}else if('q' == option){
					System.out.println("退出");
					break;
				}
			}
			
			System.out.println(i + " ...");
		}
	}
	
	private static char getInputOption(int index){
		Scanner scanner = new Scanner(System.in);
		while(true){
			System.out.print("即将开始迁移服务‘" + index + "’（C 执行/N 跳过/Q 退出）:");
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
