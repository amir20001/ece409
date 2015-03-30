package com.security;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) {
		try {
			System.out.println("Waiting for user request...");
			ServerSocket serverSocket = new ServerSocket(Util.PORT);
			Socket client = serverSocket.accept();
			System.out.println("reading line");
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			String line = null;

			while ((line = in.readLine()) != null) {
				System.out.print(line);
				client.close();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
