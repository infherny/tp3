/**
 * Classe qui contient les informations necessaires pour le fonctionnement 
 * d'une partie du jeu divinateur
 * 
 * @version  Hiver 2017
 * @author Olivier Michaud, Quentin Le Meur
 *
 */
import java.io.Serializable;

public class InfoJeu  implements Serializable {
	
	/***************************************************************************
	 * 							Attributs 
	 **************************************************************************/
	
	//Attribut servant a se positionner dans l'arbre des questions-reponses
	public ArbreConnaissance premierNoeud;
	public ArbreConnaissance noeudCourant;
	public ArbreConnaissance noeudPrecedent;
	
	//Attribut servant a retenir la derniere reponse de l'utilisateur
	public boolean 	  derniereReponseEtaitPositive;	

	
	/***************************************************************************
	 * 							Constructeur 
	 **************************************************************************/
	
	/**
	 * Constructeur par default
	 * 
	 */
	public InfoJeu(){
		
		/*
		 * On met a null tout les noeudArbre, et a faux la dernière reponse
		 * 
		 */
		
		premierNoeud = noeudCourant = noeudPrecedent = null;
		derniereReponseEtaitPositive = false;	
	}	
}
