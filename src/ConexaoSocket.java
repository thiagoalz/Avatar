/*
 * ConexãoSocket.java
 *
 * Created on 22 de Outubro de 2005, 13:05
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package simulacao;

import java.io.*;
import java.net.*;

/**
 *
 * @author John Holiver
 */
public class ConexaoSocket{
	private Socket sock;
	private PrintWriter escreve;
	private BufferedReader leSocket;
	
    /** Creates a new instance of ConexãoSocket */
    
    public ConexaoSocket(String ip, int porta)throws UnknownHostException,IOException{
    	sock=new Socket(ip,porta);
    	escreve = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()),true);
    	leSocket = new BufferedReader(new InputStreamReader(sock.getInputStream()));
    }
    public ConexaoSocket(Socket sock)throws IOException{
    	this.sock=sock;
    	escreve = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()),true);
    	leSocket = new BufferedReader(new InputStreamReader(sock.getInputStream()));    	
    }
    
    public void escreveSocket(String msg)throws IOException{ 
    	escreve.println(msg);
    }
    
    public String leSocket() throws IOException{
    	return leSocket.readLine();
    }
    
    public boolean isClosed(){
    	return sock.isClosed();
    }
    
    
    public boolean isConnected(){
    	return sock.isConnected();
    }
    
    public Socket getSocket(){
    	return sock;
    }
    
    
    
}
