/*
 * ThreadCliente.java
 *
 * Created on 22 de Outubro de 2005, 17:56
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package simulacao;

import java.util.*;

/**
 * @author Thiago Lechuga
 * @author Joao Ferreira
 *
 */
public class ThreadCliente extends Thread{
    private Random r;
    private ConexaoSocket s;
    private int porta;
    private String ip;
    
    private int operRecurso; //operacaoo a ser realizada sobre recurso / 0 - leitura / 1 - escrita
    private int qtdRecurso; //Qnt de recursos do servidor
    private int qtdMsgTrans; //Qtd de mensagens por transacao
    private int waitTime; //Tempo maximo de espera
    private int maxValue = 10; //Valor maximo a ser inserido no recurso do servidor
    private int tipoCarga; //Quantidade percentual de carga R:W / 0 - 50:50 / 0 - 75:25 / 0 - 90:10
    private int rollbackChance; //Probabilidade de acontecer rollback pelo usuario
    
    private boolean stopThread = false; //Variavel para controlar o fim da thread
    
    /** Creates a new instance of ThreadCliente */
    public ThreadCliente(int qtdMsgTrans, int waitTime, int tipoCarga, int rollbackChance, String ip, int porta) {
        r = new Random();
        
        this.operRecurso = 2;
        this.qtdMsgTrans = qtdMsgTrans;
        this.waitTime = waitTime;
        this.tipoCarga = tipoCarga;
        this.rollbackChance = rollbackChance;
        
        this.ip = ip;
        this.porta=porta;
    }
    
    public void run() {
        try{
            s = new ConexaoSocket(ip,porta);
            s.escreveSocket("?qtdRecurso");
            this.qtdRecurso = Integer.parseInt(s.leSocket());
        } catch (Exception e){
            e.printStackTrace();
            this.qtdRecurso = 1;
        }
        
        String resposta;
        int msgTransCounter = -1;
        boolean rollback = false;
        int thisTransMax = 1;
        String message = "";
        while((s!=null && s.isConnected())&& this.stopThread==false){
            try{
                Thread.sleep(this.getRandomWaitTimeInSeconds());
            } catch (Exception e){
                e.printStackTrace();
            }
            try{
                
                /*Parte de transmissao de mensagens: Start, Commit, Normal, Rollback*/
                if (msgTransCounter == -1){
                    message = "<START>";
                    msgTransCounter++;
                    thisTransMax = this.getRandomQtdMsgTrans();
                } else if (msgTransCounter < thisTransMax){
                    message = this.createClientSingleMsg();
                    msgTransCounter++;
                    rollback = getRandomRollback(); //Probabilidade de dar rollback
                } else if (msgTransCounter >= thisTransMax){
                    message = "<COMMIT>";
                    rollback = getRandomRollback(); //Probabilidade de dar rollback
                    msgTransCounter = -1;
                }
                
                if (rollback){
                    rollback = false;
                    message = "<ROLLBACK>";
                    msgTransCounter = -1;
                } 

                s.escreveSocket(message);
                System.out.println(message);
                resposta=s.leSocket();//pega a resposta do comando enviado
                this.trataMsg(resposta);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    
    public void conectar(){
    	try{
            s = new ConexaoSocket(ip,porta);
            s.escreveSocket("?qtdRecurso");
            this.qtdRecurso = Integer.parseInt(s.leSocket());
        } catch (Exception e){
            e.printStackTrace();
            this.qtdRecurso = 1;
        }
    }
    
    public void enviaMensagem(String message){
    	try{
    		System.out.println(hora()+"->"+message);
	    	s.escreveSocket(message);	        
	        String resposta=s.leSocket();//pega a resposta do comando enviado
	        this.trataMsg(resposta);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    public void interrupt(){
        this.stopThread = true;
        super.interrupt();
    }
    
    private void setQtdRecurso(int qtd){
        this.qtdRecurso = qtd;
    }
    
    private void setQtdMsgTrans(int qtd){
        this.qtdMsgTrans = qtd;
    }
    
    private void setMaxWaitTime(int time){
        this.waitTime = time;
    }
    
    public int getRandomOperation(){
        int operation = 0;
        if(this.tipoCarga==0){ //50% de chance de ser escrita
            operation = r.nextInt(this.operRecurso);
        } else if (this.tipoCarga==1){
            if(r.nextInt(4)!=0){ //25% de chance de ser escrita
                operation = 0; //leitura
            } else {
                operation = 1; //escrita
            }
        } else if (this.tipoCarga==2){
            if(r.nextInt(10)!=0){ //10% de chance de ser escrita
                operation = 0; //leitura
            } else {
                operation = 1; //escrita
            }
        }
        return operation;
    }
    
    private boolean getRandomRollback() {
        boolean rb;
        if(r.nextInt(100)<this.rollbackChance){
            rb=true;
        } else {
            rb=false;
        }
        return rb;
    }
    
    private int getRandomRecurso(){
        return r.nextInt(this.qtdRecurso);
    }
    
    private int getRandomQtdMsgTrans(){
        return r.nextInt(this.qtdMsgTrans)+1;
    }
    
    private int getRandomWaitTimeInSeconds(){
        return r.nextInt(this.waitTime)*1000;
    }
    
    private int getRandomInt(int maxValue){
        return r.nextInt(maxValue);
    }
    
    private String createClientMsg(){
        String msg = "";
        int qtdMsgTrans = this.getRandomQtdMsgTrans();
        for (int i=0; i<qtdMsgTrans; i++) {
            msg += createClientSingleMsg();
        }
        return msg;
    }
    
    private String createClientSingleMsg(){
        String msg = "";
        int operation = this.getRandomOperation();
        int recurso = this.getRandomRecurso();
        if (operation == 1){
            int valor = this.getRandomInt(maxValue);
            msg += operation + " " + recurso + " " + valor;
        } else {
            msg += operation + " " + recurso;
        }
        return msg;
    }
    
    public void trataMsg(String msg){
        //TODO metodo para tratar a msg de resposta do servidor
        System.out.println(hora()+"->"+msg);
    }
    
    /**
    * Este método captura a hora do sistema
    * @return Retorna um String contendo a hora obtida
    */
    public String hora(){
	    Calendar cal = Calendar.getInstance( );
	    
	    int horas = cal.get(Calendar.HOUR_OF_DAY);
	    int minutos = cal.get(Calendar.MINUTE);
	    int segundos = cal.get(Calendar.SECOND);
	    int msec = cal.get(Calendar.MILLISECOND);
	
	    return horas+":"+minutos+":"+segundos+"."+msec;
    }
    
}
