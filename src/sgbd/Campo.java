package simulacao.sgbd;

import java.util.ArrayList;

public class Campo {
	private String valor;
	private boolean rLock,wLock;
	private ArrayList transLock;
	
	public Campo(String valor){
		this.valor=valor;
		this.rLock=false;
		this.wLock=false;
		this.transLock=new ArrayList();
	}
	
	public synchronized String getValor(String transacao){		
		if( wLock==true && !transLock.contains(transacao) && !transacao.equals("local")){			
			return "ERRO nao podia ler";
		}
		return this.valor;
	}
	
	public synchronized String setValor(String valor,String transacao){
		if( ( wLock==true || rLock==true ) && !transLock.contains(transacao)){
			return "ERRO nao podia Escrever";
		}
		this.valor=valor;
		return "OK";
	}
	
	public synchronized boolean bloquear(int tipo,String transacao){
		if(tipo==0){//leitura
			if( wLock==true && !transLock.contains(transacao)){			
				return false;
			}else{
				rLock=true;
				if(!transLock.contains(transacao)){
					transLock.add(transacao);
				}
			}
		}else if(tipo==1){//escrita
			transLock.trimToSize();
			//verifica os bloqueios e se mais alguem ja leu o dado;
			if( ( wLock==true || rLock==true ) && ( !transLock.contains(transacao) || transLock.size()>1 ) ){
				return false;
			}else{
				wLock=true;
				if(!transLock.contains(transacao)){
					transLock.add(transacao);
				}
			}
		}
		return true;
	}
	
	public synchronized void liberar(String transacao){
		if(transLock.contains(transacao)){
			transLock.remove(transacao);
		}
		transLock.trimToSize();
		if(transLock.size()==0){
			wLock=false;
			rLock=false;
		}
	}
}
