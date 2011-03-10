package simulacao;

import java.io.IOException;
import simulacao.sgbd.Coordenador;

/**
 * @author Thiago Lechuga
 * @author JoÃ£o Ferreira
 *
 */
public class ThreadServidor extends Thread {
    private ConexaoSocket s;
    private String numero;
    private Coordenador bd;
    private int tipo;//0->cliente 1->servidor
    private int transAtual;
    private Log log;
    
    /** Creates a new instance of ThreadCliente */
    /**
     * @param socket
     * @param numero
     * @param bd
     */
    public ThreadServidor(ConexaoSocket socket,String numero,Coordenador bd) {
        this.s=socket;
        this.numero=numero;
        this.bd=bd;
        this.transAtual=0;
        log=new Log();
    }
    
    public void run() {
        String msg,resp;
        log.addTexto("Cliente "+numero+" conectado");
        try{
            while(s!=null && s.isConnected()){
                msg = s.leSocket();
                resp=this.trataMsg(msg);
                s.escreveSocket(resp);
            }
        } catch (Exception e){
            if(this.tipo==0){
        	log.addTexto("Cliente "+numero+" Desconectado");
        	log.addTexto("Liberando Bloqueios");
        	bd.commit(this.numero+"-"+transAtual);
            }         
        }
    }
    
    private String trataMsg(String msg)throws IOException{
        log.addTexto(numero + "(In)->" +msg);
        String msgRes="";

        if( msg.equals("?qtdRecurso") ){
            msgRes="10";//msgRes="5";
            this.tipo=0;
            log.setVisible(true);
        }else{
        	String[] vetor;
        	vetor=msg.split(" ");
        	
        	if(vetor[0].equals("servidor")){
        		this.numero=vetor[1];
        		this.tipo=1;
        		msgRes="SOK";
        	}else if(vetor[0].equals("<START>")){
        		this.transAtual++;
        		msgRes="OK";
        	}else if(vetor[0].equals("<COMMIT>")){
        		bd.commit(this.numero+"-"+transAtual);
        		msgRes="OK";
        	}else if(vetor[0].equals("0")){
        		msgRes=bd.read(vetor[1],this.numero+"-"+transAtual);
        	}else if(vetor[0].equals("1")){
        		msgRes=bd.write(vetor[1],vetor[2],this.numero+"-"+transAtual);        		       		
        	}else if(vetor[0].equals("S0")){
        		msgRes=bd.servidorRead(vetor[1],this.numero);
        	}else if(vetor[0].equals("S1")){
        		msgRes="S"+bd.servidorWrite(vetor[1],vetor[2],this.numero);
        	}else if(vetor[0].equals("S2")){
        		bd.servidorBloqueio(vetor[1],vetor[2],this.numero);
        		msgRes="SOK";
        	}else if(vetor[0].equals("S3")){
        		bd.servidorLiberarBloqueio(this.numero);
        		msgRes="SOK";
        	}else{
        		msgRes="Comando não reconhecido";
        		s.getSocket().close();
        	}
        }
        
        if(this.tipo==0){            
            log.addTexto(numero + "(Out)->"+msgRes);
        }else{
            System.out.println(numero + "(In)->" +msg);
            System.out.println(numero + "(Out)->"+msgRes);
        }
        
        return msgRes;
    }
    
}
