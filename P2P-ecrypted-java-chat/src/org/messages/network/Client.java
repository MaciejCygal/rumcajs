package org.messenges.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JOptionPane;
//BE------------------------------------------------------------------------------------------------------------------------------------------------------------------------
public class Client {
	
	// Class variables
	private static Socket sockClient;
	
	private static PrintWriter out;
	private static BufferedReader in;
	
	// Starts the client, which has the ability to send messages
	public Client(String IP, int puerto) {
		try {
			sockClient = new Socket(IP, puerto);
			out = new PrintWriter(sockClient.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(sockClient.getInputStream()));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Failed to connect to the other user \n Are you sure you are connected?", "ERROR", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
	
	// Method to send messages
	public static void sendMessage(String msg, SentCallback call) {
		System.out.println("enviado a "+sockClient.getLocalAddress()+"\nmsg: "+msg);
		out.println(msg);
		Thread get = new Thread(new Runnable() {
			
			@Override
			public void run() {
				String ret;
				try {
					ret = in.readLine();
					System.out.println(ret);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "Error in verifying the reception of the message by the other user", "ERROR", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
			}
		});
		
		get.start();
	}
}
