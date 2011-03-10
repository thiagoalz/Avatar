package simulacao.sgbd;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import simulacao.ConexaoSocket;

/**
 * @author Thiago Lechuga
 * @author João Ferreira
 *
 */
public class Coordenador {

	private Gerenciador gerenciador;
	private Hashtable estrutura;
	private ArrayList servidores;
	private int porta;

	public Coordenador(Hashtable estrutura,Hashtable estruturaGerenciador,int porta) {
		gerenciador=new Gerenciador(estruturaGerenciador);
		this.estrutura=estrutura;
		this.porta=porta;
		this.servidores=new ArrayList();
		
		Enumeration enume=estrutura.elements();
		while(enume.hasMoreElements()){
			EstruturaCampo ec=(EstruturaCampo)enume.nextElement();
			String[] vetor=ec.getLocais();
			for(int i=0;i<vetor.length;i++){
				if(!servidores.contains(vetor[i])){
					servidores.add(vetor[i]);
				}
			}
		}
	}
	
	public String commit(String trans){	
		servidores.trimToSize();
		for(int i=0;i<servidores.size();i++){
			String saux=(String)servidores.get(i);
			if(saux.equals("localhost")){
				gerenciador.liberarBloqueio(trans);
			}else{
				try{
					ConexaoSocket s = new ConexaoSocket(saux,porta);
					s.escreveSocket("servidor "+trans);
					s.leSocket();
					s.escreveSocket("S3 ");
					s.leSocket();
					s.getSocket().close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}

		return "OK";
	}
	
	public String write(String cam,String val,String trans){
		EstruturaCampo campo=(EstruturaCampo)estrutura.get(cam);
		String lp=campo.getLocalPrimaria();
		String[] locais=campo.getLocais();
		String resp="";
		
		this.obtemBloqueio(1,cam,lp,trans);
		
		for(int i=0;i<locais.length;i++){
			resp=this.writeGerenciador(locais[i],cam,val,trans);
		}

		return resp;
	}
	
	public String read(String cam,String trans){
		EstruturaCampo campo=(EstruturaCampo)estrutura.get(cam);
		String lp=campo.getLocalPrimaria();
		String resp="";
		
		this.obtemBloqueio(0,cam,lp,trans);
		
		if(campo.isLocal()){
			resp=this.readGerenciador("localhost",cam,trans);
		}else{
			resp=this.readGerenciador(lp,cam,trans);
		}
		return resp;
	}
	
	private void obtemBloqueio(int tipo,String campo,String local,String trans){
		if(local.equals("localhost")){
			gerenciador.obtemBloqueio(tipo,campo,trans);
		}else{
			try{
				ConexaoSocket s = new ConexaoSocket(local,porta);
				s.escreveSocket("servidor "+trans);
				s.leSocket();
				s.escreveSocket("S2 "+tipo+" "+campo);
				s.leSocket();
				s.getSocket().close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	private String readGerenciador(String local,String campo,String trans){
		String resp="";
		if(local.equals("localhost")){
			resp=gerenciador.read(campo,trans);
		}else{
			try{
				ConexaoSocket s = new ConexaoSocket(local,porta);
				s.escreveSocket("servidor "+trans);
				resp=s.leSocket();
				s.escreveSocket("S0 "+campo);
				resp=s.leSocket();
				s.getSocket().close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return resp;
	}
	
	private String writeGerenciador(String local,String campo,String valor,String trans){
		String resp="";
		
		if(local.equals("localhost")){
			resp=gerenciador.write(campo,valor,trans);
		}else{
			try{
				ConexaoSocket s = new ConexaoSocket(local,porta);
				s.escreveSocket("servidor "+trans);
				resp=s.leSocket();
				s.escreveSocket("S1 "+campo+" "+valor);
				resp=s.leSocket();
				s.getSocket().close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	
		return resp;
	}
	
	public String servidorWrite(String cam,String val,String trans){		
		return gerenciador.write(cam,val,trans);
	}
	
	public String servidorRead(String cam,String trans){		
		return gerenciador.read(cam,trans);
	}
	
	public void servidorBloqueio(String tipo,String campo,String trans){
		int tp=Integer.parseInt(tipo);
		gerenciador.obtemBloqueio(tp,campo,trans);
	}
	
	public void servidorLiberarBloqueio(String trans){
		gerenciador.liberarBloqueio(trans);
	}
	
	
}
