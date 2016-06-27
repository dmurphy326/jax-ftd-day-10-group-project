package com.cooksys.ftd.chat.server;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientHandler implements Runnable, Closeable {

	Logger log = LoggerFactory.getLogger(ClientHandler.class);

	private Socket client;
	private PrintWriter writer;
	private BufferedReader reader;
	private BufferedReader name;
	private String user;

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
	public Socket getSocket() {
		return this.client;
	}

	public ClientHandler(Socket client) throws IOException {
		super();
		this.client = client;
		this.reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
		this.writer = new PrintWriter(client.getOutputStream(), true);
		this.name = new BufferedReader(new InputStreamReader(client.getInputStream()));
	}

	@Override
	public void run() { //do process
		try {
			log.info("handling client {}", this.client.getRemoteSocketAddress());
			
		while (!this.client.isClosed()) {
				String user = name.readLine();
				log.info("Welcome {} !!!",user);
		while (!this.client.isClosed()) {
				String echo = reader.readLine();
				log.info("Message from client {}:{}", user,echo,
						this.client.getRemoteSocketAddress());
//				writer.print(echo);
//				writer.flush();
			}
		}   log.info("{}: has diconnected",user);
			this.close();
			} catch (IOException e) {
			log.error("Handler fail! oh noes :(", e);
		}
	}
	void writer(String echo) throws IOException {
		writer.print(echo);
		writer.flush();
	}

	@Override
	public void close() throws IOException {
		log.info("closing connection to client {}", this.client.getRemoteSocketAddress());
		this.client.close();
	}

}
