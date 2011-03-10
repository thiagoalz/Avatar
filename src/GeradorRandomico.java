/*
 * GeradorRandomico.java
 *
 * Created on 22 de Outubro de 2005, 13:05
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package simulacao;

import java.util.*;
/**
 *
 * @author John Holiver
 */
public class GeradorRandomico {
    private Random r;
    
    private int operRecurso; //operação a ser realizada sobre recurso / 0 - leitura / 1 - escrita
    private int qtdRecurso; //Qnt de recursos do servidor
    private int qtdMsgTrans; //Qtd de mensagens por transação
    private int waitTime; //Tempo máximo de espera
    /** Creates a new instance of GeradorRandomico */
    public GeradorRandomico(int qtdRecurso, int qtdMsgTrans, int waitTime) {
        r = new Random();
        this.operRecurso = 2;
        this.qtdRecurso = qtdRecurso;
        this.qtdMsgTrans = qtdMsgTrans;
        this.waitTime = waitTime;
    }
    
    public void setQtdRecurso(int qtd){
        this.qtdRecurso = qtd;
    }
    
    public void setQtdMsgTrans(int qtd){
        this.qtdMsgTrans = qtd;
    }
    
    public void setMaxWaitTime(int time){
        this.waitTime = time;
    }
    
    public int getRandomOperation(){
        return r.nextInt(this.operRecurso);
    }
    
    public int getRandomRecurso(){
        return r.nextInt(this.qtdRecurso);
    }
    
    public int getRandomQtdMsgTrans(){
        return r.nextInt(this.qtdMsgTrans);
    }
    
    public int getRandomWaitTime(){
        return r.nextInt(this.waitTime);
    }
    
    public int getRandomInt(int maxValue){
        return r.nextInt(maxValue);
    }
    
    
    public String createClientMsg(){
        GeradorRandomico gerRand = new GeradorRandomico(2,2,2);
        String msg = "";
        int maxValue = 10;
        int operation = 0;
        int recurso = 0;
        
        int qtdMsgTrans = gerRand.getRandomQtdMsgTrans();
        for (int i=0; i<qtdMsgTrans; i++) {
            operation = gerRand.getRandomOperation();
            recurso = gerRand.getRandomRecurso();
            if (operation == 1){
                int valor = gerRand.getRandomInt(maxValue);
                msg += operation + " " + recurso + " " + valor + ";\n";
            } else {
                msg += operation + " " + recurso + ";\n";
            }
        }
        return msg;
    }
    
    public String createClientSingleMsg(){
        GeradorRandomico gerRand = new GeradorRandomico(2,2,2);
        String msg = "";
        int maxValue = 10;
        int operation = gerRand.getRandomOperation();
        int recurso = gerRand.getRandomRecurso();
        if (operation == 1){
            int valor = gerRand.getRandomInt(maxValue);
            msg += operation + " " + recurso + " " + valor + ";\n";
        } else {
            msg += operation + " " + recurso + ";\n";
        }
        return msg;
    }
    
}
