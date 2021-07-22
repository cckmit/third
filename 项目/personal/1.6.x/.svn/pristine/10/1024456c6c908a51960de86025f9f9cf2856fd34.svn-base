package org.opoo.apps.license.client.mina;

import java.io.Serializable;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;

public class ClientIoMessage implements Serializable {
	private static final long serialVersionUID = -1322112325619249841L;
	
	private String[] arguments;
	private int transactionId;
	private String command;
	public ClientIoMessage(){
	}
	public ClientIoMessage(String input){
		parseMessage(input);
	}
	public String[] getArguments() {
		return arguments;
	}
	public void setArguments(String[] arguments) {
		this.arguments = arguments;
	}
	public ClientIoMessage addArgument(String argument){
		if(arguments == null || arguments.length == 0){
			arguments = new String[]{argument};
		}else{
			String[] arr = new String[arguments.length + 1];
			System.arraycopy(arguments, 0, arr, 0, arguments.length);
			arr[arguments.length] = argument;
			arguments = arr;
		}
		return this;
	}
	public int getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append(command).append(" ").append(transactionId);
		for(String arg: arguments){
			sb.append(" ").append(arg);
		}
		return sb.toString();
	}
	
	private void parseMessage(String message) {
		if(StringUtils.isBlank(message)){
			throw new IllegalArgumentException();
		}
		message = message.trim();
		if(message.length() < 7){
			throw new IllegalArgumentException();
		}
		
		StringTokenizer st = new StringTokenizer(message, " ");
		int tokenCount = st.countTokens();
		if(tokenCount < 3){
			throw new IllegalArgumentException();
		}
		
		//System.out.println("Message token count: " + tokenCount);
		
		this.command = st.nextToken();
		this.transactionId = -1;
		this.arguments = new String[tokenCount - 2];
		
		if(command.length() != 3){
			throw new IllegalArgumentException("command invalid: " + command);
		}
		
		try {
			transactionId = Integer.parseInt(st.nextToken());
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("transaction id error", e);
		}
		if(transactionId < 1){
			throw new IllegalArgumentException("transaction id invalid: " + transactionId);
		}
		
		int index = 0;
		while(st.hasMoreTokens()){
			arguments[index++] = st.nextToken();
		}
	}
	
	public static void main(String[] args){
		ClientIoMessage message = new ClientIoMessage();
		message.addArgument("aaa").addArgument("bbb");
		System.out.println(message);
	}
}