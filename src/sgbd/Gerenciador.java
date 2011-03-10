package simulacao.sgbd;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * @author Thiago Lechuga
 * @author João Ferreira
 *
 */
public class Gerenciador {
	private Hashtable campos;
	private JTable tabela;
	
	public Gerenciador(Hashtable campos){
		this.campos=campos;
		this.constroiTabela();
		Valores form=new Valores(this.tabela);
		form.setVisible(true);
	}
	
	public synchronized void obtemBloqueio(int tipo,String cam,String trans){
		try{
			Campo campo=(Campo)campos.get(cam);
			while(!campo.bloquear(tipo,trans)){
				wait();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public synchronized void liberarBloqueio(String trans){
		Enumeration enume=campos.elements();
		Campo atual;
		while(enume.hasMoreElements()){
			atual=(Campo)enume.nextElement();
			atual.liberar(trans);
		}
		notifyAll();
	}
	
	public String read(String cam,String trans){
		Campo campo=(Campo)campos.get(cam);
		return campo.getValor(trans);
	}
	
	public String write(String cam,String valor,String trans){
		Campo campo=(Campo)campos.get(cam);		
		tabela.getModel().setValueAt(valor, Integer.parseInt(cam), 1);
		String aux=campo.setValor(valor,trans);
		return aux;
	}
	
	private void constroiTabela(){
		DefaultTableModel model = new DefaultTableModel();
	    this.tabela = new JTable(model);
	    model.addColumn("Campos");
	    model.addColumn("Valores");
	    
	    int qtd=this.campos.size();
	    int adicionados=0;
	    int cont=0;
	    Campo atual;
	    while(adicionados<qtd){
	    	atual=(Campo)campos.get(cont+"");
	    	if(atual!=null){
	    		model.insertRow(cont, new Object[]{cont+"",atual.getValor("local")});
	    		adicionados++;
	    	}else{
	    		model.insertRow(cont, new Object[]{cont+"",""});
	    	}
	    	cont++;
	    }
	}
}
