/*
 * Created on 14/02/2005
 */
package br.com.relato.chat;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

public class ControlChat {
	private ControlChat controlchat;
	private ServletContext ctx;
	private Map chat;
	
	private ControlChat() {
	}
		
	private ControlChat(ServletContext ctx) {
		this.ctx = ctx;
	}

	public synchronized void init(ServletContext ctx) {
		if(controlchat == null)
			controlchat = new ControlChat(ctx);
		chat = new HashMap();
	}

	public synchronized ControlChat getInstance() {
		if(controlchat == null)
			throw new RuntimeException("no control chat started");
		return controlchat;
	}
	
	public synchronized void reload(ServletContext ctx) {
		if(controlchat != null)
			controlchat = new ControlChat(ctx);
	}
	
	public void openLog(String chatroom, String admin){
		if ( chat == null )
			throw new RuntimeException("no buffer initialized");
	}

	public synchronized void putMsg(String chatroom, String msg){
		if ( chat == null )
			throw new RuntimeException("no buffer initialized");
		
	}	
	
	public void closeLog(String chatroom, String admin){
		if ( chat == null )
			throw new RuntimeException("no buffer initialized");		
		
	}
	
	public boolean isOpen(String chatroom, String user){
		if ( chat.get(chatroom) != null )
			return true;
		return false;
	}	
}