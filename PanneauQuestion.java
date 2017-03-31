import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class PanneauQuestion extends PanneauOuiNon {

	Label etiquette;
	
	public PanneauQuestion(BdQuestionsReponses bd, JPanel panneau){
		
		super( bd, panneau);
		etiquette = new Label((String)bd.getLaChaineActuelle());
		super.ajouteComposantDessus(etiquette);
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

			if(!bd.deplacerDansArbre(Constantes.REPONSE_POSITIVE)){
				
				UtilitaireES.demarrerCollecteReponse(bd);	
			}
			
			//Refresh panneau principal
		}

	}
	
	
	public class EcouteurNon implements ActionListener {

		
		private BdQuestionsReponses bd;
		
		EcouteurNon(BdQuestionsReponses bd){
			
			this.bd = bd;
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {

			bd.deplacerDansArbre(Constantes.REPONSE_NEGATIVE);
		}
		
		//Refresh panneau principal
	}	
}
