package com.security;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client {

	private static final String PATTERN = "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

	public static void main(String[] args) {
		String identity = getIdentity();
		String ipAddress = getIp();

		try {
			System.out.println("setting up connection");
			Socket connection = new Socket(ipAddress, Util.PORT);
			System.out.println("sending data");
			sendToServer(connection, "test1");
			//sendToServer(connection, "test2");
		} catch (UnknownHostException e) {
			System.out.println("could not connect to server exiting");
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static String getIdentity() {
		String identity = "";
		while (identity.length() > 10 || identity.length() == 0) {
			System.out.println("enter the user's identity");
			identity = Util.readLine();
		}
		return identity;
	}

	private static String getIp() {
		String ip = "";
		while (!validateIp(ip)) {
			System.out.println("Enter the IP address of CA:");
			ip = Util.readLine();
		}
		return ip;
	}

	private static boolean validateIp(final String ip) {
		Pattern pattern = Pattern.compile(PATTERN);
		Matcher matcher = pattern.matcher(ip);
		return matcher.matches();
	}

	private static void sendToServer(Socket conn, String identity) {
		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			bw.write(identity + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
