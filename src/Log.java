package simulacao;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.WindowConstants;
import java.util.*;


/**
 * This code was generated using CloudGarden's Jigloo
 * SWT/Swing GUI Builder, which is free for non-commercial
 * use. If Jigloo is being used commercially (ie, by a corporation,
 * company or business for any purpose whatever) then you
 * should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details.
 * Use of Jigloo implies acceptance of these licensing terms.
 * *************************************
 * A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED
 * for this machine, so Jigloo or this code cannot be used legally
 * for any corporate or commercial purpose.
 * *************************************
 */
public class Log extends javax.swing.JFrame {
    private JScrollPane jScrollPane1;
    private JTextPane jTextPaneLog;
    
    public Log() {
        super("Log");
        initGUI();
    }
    
    private void initGUI() {
        try {
            setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); {
                jScrollPane1 = new JScrollPane();
                this.getContentPane().add(jScrollPane1, null);
                jScrollPane1.setAutoscrolls(true); {
                    jTextPaneLog = new JTextPane();
                    jTextPaneLog.setEditable(false);
                    jScrollPane1.getViewport().add(jTextPaneLog, null);
                }
            }
            pack();
            this.setSize(300, 400);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void addTexto(String tx){
        jTextPaneLog.setText(jTextPaneLog.getText()+"\n"+hora()+tx);
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
        
        return "["+horas+":"+minutos+":"+segundos+"."+msec+"] ";
    }
    
}
