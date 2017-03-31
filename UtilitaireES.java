import java.io.File;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
/**
 * Classe implémentant des sous programmes utilitaires
 * qui permettent de faire de la saisie pour le jeu du divinateur.
 * 
 * @author Pierre Bélisle
 * @version (copyright H2017).
 */

public class UtilitaireES {


	/**
	 * Demande un animal ou un objet à l'utilisateur et une ou des questions 
	 * qui permettent de le distinguer des autres objets.
	 * 
	 * @param bd
	 */
	public static void demanderReponseValide(BdQuestionsReponses bd) {

		// Pour obtenir la réponse de l'utilisateur.
		String reponse;
		
		// Mettre un message distinctif si la bd est vide ou non.
		if(bd.estVide()){
			
			//JOptionPane.showMessageDialog(null, "La base de données est vide");
			// On demande une réponse.
			reponse = 
					JOptionPane.showInputDialog("Je ne connais rien, " + 
								" Entrez ce à quoi vous pensiez ? : ");
		}
		
		else{
			
			// On demande une réponse parce qu'on n'a pas trouvé la réponse.
			reponse = 
					JOptionPane.showInputDialog("Je n'ai pas trouvé" + 
							" votre réponse,  Entrez ce à quoi vous pensiez ? : ");
		}

		// L'utilisateur n'a pas annulé.
		if(reponse != null && !reponse.equals("")){
			
			reponse = reponse.toLowerCase();
			
			// S'il nous répéte ce qu'on vient de lui montrer.
			if(!bd.estVide() && reponse.equals(bd.getLaChaineActuelle())){
				
				JOptionPane.showMessageDialog(null, 
						"C'est ça que j'ai dit, non ????");

			}
			else{

				// Si la réponse  existe,  repUtilisateur sera différent de null.
				Reponse repUtilisateur = bd.getReponse(reponse);

				if (repUtilisateur != null) {

					// On affiche la réponse et le message qui indique l'erreur.
					JOptionPane.showMessageDialog(null,  repUtilisateur.getReponse() + 
							" existe déjà dans notre banque de donnée, " +
							bd.getDeroulementJeu(repUtilisateur));

				} 
				else {

					// Il faut une question pour accompagner la réponse.
					String question = 
							JOptionPane.showInputDialog("Entrez une question " +
									" concernant votre objet ou votre animal" + 
									" qui le distingue  : ");

					// Si l'utilisateur n'a pas annulé, on ajoute la réponse et sa 
					// question à la bd.
					if(question != null && !question.equals("")){

						// On veut un standard pour les questions
						// avec une majuscule en commençant et en minusule pour 
						// le reste.  Les étudiants sauvegardent le tout en minuscule.
						String chaine = question.toString().toLowerCase();
						StringBuffer str = new StringBuffer(chaine);
						str.setCharAt(0, Character.toUpperCase(str.charAt(0)));
						
						
						String nomFicImage = 
								UtilitaireFichier.nomFichierValide("", 
										UtilitaireFichier.OUVRE, "jpg");
						
							bd.ajouterQuestionReponse(reponse, str.toString(), 
									new ImageIcon(nomFicImage));
					}
				}
			}
		}
	}
	
	
	/**
	 * À chaque fois que le divinateur ne connait pas la réponse, on doit 
	 * saisir une nouvelle réponse et redémarrer le jeu.
	 * 
	 * Ce code est utilisé par les écouteurs des panneaux de l'application.
	 */
	public static void demarrerCollecteReponse(BdQuestionsReponses bd){
		
		UtilitaireES.demanderReponseValide(bd);

		// Si la bd est encore vide, on ne peut pas jouer.
		if(bd.estVide()){

			JOptionPane.showMessageDialog(null, "Il est impossible de " + 
					"jouer si on ne connait rien. Veuillez redémarrer l'application");
			
			// Termine le programme abruptement.
			System.exit(0);
		}
		

		// Si on est ici c'est que la bd n'est pas vide.
		JOptionPane.showMessageDialog(null, 
				"Pensez à quelque chose et appuyez sur ok pour commencer");


		bd.choisirPremiereQuestion();			

	}
}