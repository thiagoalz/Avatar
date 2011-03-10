package simulacao.sgbd;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

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
public class Valores extends javax.swing.JFrame {
	private JScrollPane jScrollPane1;
	private JTable campos;

	public Valores(JTable campos) {
		super("Valores");
		this.campos=campos;
		initGUI();
	}
	
	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			{
				jScrollPane1 = new JScrollPane();
				this.getContentPane().add(jScrollPane1, BorderLayout.CENTER);
				{
					jScrollPane1.setViewportView(campos);
				}
			}
			pack();
			this.setSize(209, 225);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
