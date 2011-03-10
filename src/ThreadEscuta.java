package simulacao;

import java.net.ServerSocket;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;

import simulacao.sgbd.Campo;
import simulacao.sgbd.Coordenador;
import simulacao.sgbd.EstruturaCampo;

/**
 * @author Thiago Lechuga
 * @author João Ferreira
 *
 */
public class ThreadEscuta extends Thread {
    private ConexaoSocket s;
    private ServerSocket server;
    private int porta;
    private int qtdConexoes;
    private String nome;
    private Coordenador bd;
    
    /** Creates a new instance of ThreadCliente */
    public ThreadEscuta(int porta,String nome) {//criando um servidor de escuta que atende a rec e passa para uma nova thread
        this.porta=porta;
        this.nome=nome;
        qtdConexoes=0;
        
        String serv1="";
        String serv2="";
        
        Hashtable coord=new Hashtable();
        Hashtable ger=new Hashtable();
        
        String[] vet1=new String[1];
        String[] vet2=new String[1];
        String[] vet3=new String[2];
        
        //CONFIGURACOES DA ESTRUTURA DOS DADOS
        if(nome.equals("1")){//    	configuracoes servidor1
        	serv1="localhost";
        	serv2="10.0.9.3";
        	
        	ger.put("0",new Campo("01"));
        	ger.put("1",new Campo("11"));
        	ger.put("2",new Campo("21"));
            ger.put("3",new Campo("31"));
            ger.put("4",new Campo("44"));
            ger.put("5",new Campo("55"));
        	
        }else if(nome.equals("2")){//    	configuracoes servidor2
        	serv1="10.0.9.4";
            serv2="localhost";
        	
        	ger.put("4",new Campo("44"));
        	ger.put("5",new Campo("55"));
            ger.put("6",new Campo("62"));
            ger.put("7",new Campo("72"));
            ger.put("8",new Campo("82"));
            ger.put("9",new Campo("92"));
        }
        
        vet1[0]=serv1;
	   	
    	vet2[0]=serv2;       	
    	
    	vet3[0]=serv1;
    	vet3[1]=serv2;
    	
    	
    	coord.put("0",new EstruturaCampo(vet1,serv1));
    	coord.put("1",new EstruturaCampo(vet1,serv1));
    	coord.put("2",new EstruturaCampo(vet1,serv1));
    	coord.put("3",new EstruturaCampo(vet1,serv1));
    	coord.put("4",new EstruturaCampo(vet3,serv1));
        coord.put("5",new EstruturaCampo(vet3,serv2));
        coord.put("6",new EstruturaCampo(vet2,serv2));
        coord.put("7",new EstruturaCampo(vet2,serv2));
        coord.put("8",new EstruturaCampo(vet2,serv2));
        coord.put("9",new EstruturaCampo(vet2,serv2));
        	
        bd=new Coordenador(coord,ger,porta);
    }     

    public void run() {
        try{
            this.server = new ServerSocket(porta);
            System.out.println("Escutando na porta: "+porta);
            while(true){
                s = new ConexaoSocket(server.accept());
                new ThreadServidor(s,qtdConexoes+"-"+this.nome,bd).start();
                qtdConexoes++;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void interrupt(){
        try{
            server.close();
        } catch(Exception e){
            e.printStackTrace();
        }
        super.interrupt();
    }
}
