import java.awt.Image;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class PanneauReponse extends PanneauOuiNon {

	Label etiquetteReponse;
	
	public PanneauReponse(BdQuestionsReponses bd, JPanel panneau) {
		
		super(bd, panneau);
		etiquetteReponse = new Label((String) bd.getLaChaineActuelle());
		super.ajouteComposantDessus(etiquetteReponse);
		super.ajouterEcouteur(getOui(), new EcouteurOui(bd));
		super.ajouterEcouteur(getNon(), new EcouteurNon(bd));
		
	}
	
	private class EcouteurOui implements ActionListener {

		private BdQuestionsReponses bd;

		EcouteurOui(BdQuestionsReponses bd){
			
			this.bd = bd;
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {

			JOptionPane.showMessageDialog(null,
					"Bravo nous avons trouve votre reponse");	
			bd.choisirPremiereQuestion();
			
			//Mettre paneau principal a jour

		}

	}
	
	
	public class EcouteurNon implements ActionListener {

		
		private BdQuestionsReponses bd;
		
		EcouteurNon(BdQuestionsReponses bd){
			
			this.bd = bd;
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {

		UtilitaireES.demanderReponseValide(bd);
		}
		
		//Refresh panneau principal
	}	
}
