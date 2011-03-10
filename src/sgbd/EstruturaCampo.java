package simulacao.sgbd;

/**
 * @author Thiago Lechuga
 * @author João Ferreira
 *
 */
public class EstruturaCampo {
	private String[] locais;
	private String localPrimaria;
	
	public EstruturaCampo(String[] local,String lp) {
		this.locais = local;
		this.localPrimaria = lp;
		
	}
	
	public String[] getLocais(){
		return locais;
	}
	
	public String getLocalPrimaria(){
		return localPrimaria;
	}
	
	public boolean isLocal(){
		for(int i=0;i<this.locais.length;i++){
			if(locais[i]=="localhost"){
				return true;
			}
		}
		return false;
	}
	
	
	
}
