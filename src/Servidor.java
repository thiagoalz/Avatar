package simulacao;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import javax.swing.WindowConstants;


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
public class Servidor extends javax.swing.JFrame {
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JLabel jLabelFundo;
	private JTextField jTextFieldNome;
	private JLabel jLabel3;
	private JToggleButton jButtonIniciar;
	private JTextField jTextFieldPorta;
	private Thread thread;
	/**
	* Auto-generated main method to display this JFrame
	*/
	public static void main(String[] args) {
		Servidor inst = new Servidor();
		inst.setVisible(true);
	}
	
	public Servidor() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			this.getContentPane().setLayout(null);
			this.setTitle("Servidor");
			this.setResizable(false);
			{
				jLabel2 = new JLabel();
				this.getContentPane().add(jLabel2);
				jLabel2.setText("Servidor");
				jLabel2.setBounds(95, 7, 60, 18);
				jLabel2.setForeground(new java.awt.Color(181,37,37));
			}
			{
				jButtonIniciar = new JToggleButton();
				this.getContentPane().add(jButtonIniciar);
				jButtonIniciar.setText("Iniciar");
				jButtonIniciar.setBounds(47, 93, 160, 23);
				jButtonIniciar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						jButtonEnviarMsgActionPerformed(evt);
					}
				});
			}
			{
				jLabelFundo = new JLabel();
				this.getContentPane().add(jLabelFundo);
				jLabelFundo.setBounds(0, 0, 238, 130);
				jLabelFundo.setIcon(new ImageIcon(getClass().getClassLoader().getResource("simulacao/avatar.JPG")));
				{
					jTextFieldNome = new JTextField();
					jLabelFundo.add(jTextFieldNome);
					jTextFieldNome.setText("1");
					jTextFieldNome.setBounds(130, 62, 49, 20);
				}
				{
					jLabel3 = new JLabel();
					jLabelFundo.add(jLabel3);
					jLabel3.setText("Nome:");
					jLabel3.setBounds(74, 62, 45, 18);
				}
				{
					jTextFieldPorta = new JTextField();
					jLabelFundo.add(jTextFieldPorta);
					jTextFieldPorta.setBounds(130, 35, 49, 20);
					jTextFieldPorta.setText("4321");
				}
				{
					jLabel1 = new JLabel();
					jLabelFundo.add(jLabel1);
					jLabel1.setText("Porta:");
					jLabel1.setBounds(75, 37, 45, 18);
				}
			}
			pack();
			this.setSize(246, 164);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void jButtonEnviarMsgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEnviarMsgActionPerformed
        
        if(jButtonIniciar.isSelected()){ //O envio de mensagens ser� ligado
            jButtonIniciar.setText("Parar");
            //Iniciar thread de recebimento
            int porta = Integer.parseInt(jTextFieldPorta.getText());
            String nome=jTextFieldNome.getText();
            thread = new ThreadEscuta(porta,nome);
            thread.start();
        } else { //O Recebimento de mensagens ser� desligado
            jButtonIniciar.setText("Iniciar");
            //Parar thread!
            thread.interrupt();
        }
    }//GEN-LAST:event_jButtonEnviarMsgActionPerformed

}
