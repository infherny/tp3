import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;


public class PanneauOuiNon extends JPanel{

	
	private BdQuestionsReponses bd;
	private JPanel panneau;
	private JPanel sousPanneau; 
	
	private JButton oui;
	private JButton non;
	
	public PanneauOuiNon(BdQuestionsReponses bd, JPanel panneau){
		
		super();
		this.bd = bd;
		this.panneau = panneau;
		this.oui = new JButton("Oui");
		this.non = new JButton("Non");	
		this.sousPanneau = new JPanel( new FlowLayout(FlowLayout.LEADING));
		this.panneau.add(sousPanneau);
		sousPanneau.add(oui);
		sousPanneau.add(non);
		sousPanneau.setVisible(true);
	}
	
	public void ajouteComposantDessus(Component composant){
		
		panneau.remove(BoxLayout.LINE_AXIS);
		panneau.add(composant, BoxLayout.LINE_AXIS);
		
	}
	
	public void ajouterEcouteur(JButton boutton, ActionListener e){
		
		boutton.addActionListener(e);
	}

	public JButton getOui() {
		return oui;
	}

	public void setOui(JButton oui) {
		this.oui = oui;
	}

	public JButton getNon() {
		return non;
	}

	public void setNon(JButton non) {
		this.non = non;
	}

}


