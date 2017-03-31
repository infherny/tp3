import java.io.Serializable;
import java.util.Arrays;
import java.util.Stack;
import java.util.Vector;

import javax.swing.ImageIcon;
/**
 * La base de donnée est constituée d'un tableau de String pour retenir 
 * les questions et un tableau d'objets de la classe Reponse.
 * 
 * Nous utilisons le principe de Noeud des TDA pour créer une structure
 * arborescente qui contient les indices des questions ou de la réponse et le 
 * lien sur l'ordre dans lequel les questions sont posées.
 * 
 * L'algorithme d'utilisation de la bd est de poser une question choisit 
 * au hasard.  Selon la réponse de l'utilisateur, le noeud de la réponsePositive 
 * ou celui de la réponseNegative est sélectionné.  Tantque la référence
 * du noeud est une question, elle est posée. 
 * 
 * Si les deux Noeuds sont à null, c'est qu'on a trouvé une réponse.
 * 
 * Les tableaux de questions et de réponses sont externes à cette classe.
 * 
 * @author Pierre Bélisle
 * @version (copyright H2017).
 *
 */
public class BdQuestionsReponses implements Serializable{


	private static final int MAX_REPONSES = 100;

	// Les indices des Noeuds de l'arbre et de la liste de questions d'une 
	//réponse  font référence à ces tableaux.
	private Vector<String>tabQuestions = new Vector<String>();

    // Les réponses dans un tableau statique.
	private Reponse[] tabReponses = new Reponse[MAX_REPONSES];
	private int nbReponses = 0;

	// Une liste des réponses à donner pour retrouver une réponse
	// (ex: ONNONNOO).	
	private Liste cheminParcouru;
		
	// Regroupe toutes les références nécessaires au maintient de l'arbre et 
	// à la recherche des réponses.
	private InfoJeu infoJeu;		
	
	// Le nom du fichier pour la sauvegarde,
	private String nomFic = Constantes.NOM_FICHIER_BD;
	
	/**
	 * Constructeur par défaut.  Il initialise le jeu avec
	 * la bd fournit par l'utilisateur ou celle par défaut si l'utilisateur n'en 
	 * a pas.
	 */
	public BdQuestionsReponses(){
		
		initBd();
		
	}
	
	/**
	 * @return the tabQuestions
	 */
	public String[] getTabQuestions() {
		
		String[]tab = new String[tabQuestions.size()]; 
		return tabQuestions.toArray(tab);
	}

	/**
	 * @return the tabReponses
	 */
	public Reponse[] getTabReponses() {
		
		// Retourne un tableau de la taille exact.
		return  Arrays.copyOfRange(this.tabReponses, 0, nbReponses);
		
	}
	public String getNomFic(){
		return nomFic;
	}

	public void setNomFic(String nomFic){
		this.nomFic = nomFic;
	}
	/**
	 * Réinitialise tous les attributs de la bd.
	 */
	private void initBd(){

		// Les tableaux seront toujours pleins alors on ne retient pas 
		// le nombre d'éléments significatifs.
		tabQuestions.removeAll(tabQuestions);
		nbReponses = 0;

		
        // Toutes les références d'info jeu sont à nulles.		
		infoJeu = new InfoJeu();
		
		// On se fit au ramasse-miettes (System.gc()) de Java pour libérer 
		// la mémoire de la file s'il y avait des noeuds dedans avant.
		cheminParcouru = new Liste();
	}
	
	/**
	 * Retourne si la bd est vide ou non.  Elle est non vide si le 
	 * fichier NOM_FICHIER_BD existe et qu'il est valide.
	 * 
	 * @return rue si la bd est vide et false sinon.
	 */
	public boolean estVide(){
		
		return nbReponses == 0;
	}
	
	/**
	 * Ajoute la réponse au tableau de réponses si elle n'existe pas déjà.  
	 * Même chosepour la question.
	 * 
	 * L'indice de la question est ajouté à la liste de questions de la réponse.
	 * 
	 * @param reponse
	 * @param question
	 */
	public void ajouterQuestionReponse(String reponse, 
			String question, ImageIcon image){
			
		/*
		 * Stratégie : Si la réponse n'existe pas, on l'ajoute dans la collection. 
		 * Si elle existe, on obtient sa position à l'aide d'indexOf.
		 */
		Reponse r = null;
		
        // Sert à retenir l'indice de la question reçue dns le tableau.
		int indiceQuestion;
		
		// La position n'est pas la même selon qu'on l'ajoute ou qu'elle est déjà là.
		if (!tabQuestions.contains(question)){
			
			indiceQuestion = tabQuestions.size();			
			tabQuestions.add(question);		
		}
		else{
			indiceQuestion = tabQuestions.indexOf(question);
		}

		// On ajoute la réponse.
		r = new Reponse(reponse, image);
		
		tabReponses[nbReponses] = r;

		// Il reste à ajouter l'indice de la question à la liste de questions 
		// de cette réponse. On ajuste l'arbre avec les indices impliqués.
		ajouterNoeuds(r, nbReponses);
		
		
		nbReponses++;
		
		UtilitaireFichier.sauvegarde(this, nomFic);
		
	}
	
	
	/**
	 * On se déplace dans l'arbre selon la réponse de l'utilisateur.
	 * C'est un coup à gauche ou un coup à droite. sauf si les deux côtés sont
	 * à nulles.  Alors, il s'agit d'une réponse. 
	 * 
	 * @param bd
	 * @param reponse
	 * @return
	 */
	public  boolean deplacerDansArbre(char reponseJeu){
		
		  boolean resteQuestion = true;
			  
		  // Dans le cas oû l'utilisateur a répondu oui
		  // on se positionne a la prochaine question, à gauche. 
		  if (reponseJeu == Constantes.REPONSE_POSITIVE){
			  
			  allerAGauche();
			  cheminParcouru.insererApres(Constantes.REPONSE_POSITIVE);

		  }

		  //Il a répondu non et il reste des questions à poser, on va à droite.
		  else if (reponseJeu == Constantes.REPONSE_NEGATIVE && 
				  	  infoJeu.noeudCourant.getArbreDroit() != null){

			  allerADroite();
			  cheminParcouru.insererApres(Constantes.REPONSE_NEGATIVE);
			  
		  }
		  
		  // Il ne reste plus de question à poser.
		  else{
			  cheminParcouru.insererApres(Constantes.REPONSE_NEGATIVE);
			  resteQuestion = false;
		  }

		return resteQuestion;
	}
	
	
	/**
	 * Deplace la position courante sur la première chaîne de la bd sélectionné
	 * au hasard.
	 *
	 */
	public void choisirPremiereQuestion(){
				
		infoJeu.noeudCourant = infoJeu.premierNoeud;
		infoJeu.derniereReponseEtaitPositive = false;
		infoJeu.noeudPrecedent = null;
		
		cheminParcouru = new Liste();		
		
	}

	/**
	 * 
	 * @return Retourne la chaine de caractère actuellement
	 *         pointée par la position courante.
	 */
	public Object getLaChaineActuelle(){
		
		/*
		 * Stratégie : On utilise l'opérateur ternaire pour retourner la bonne 
		 * chaîne qui dépend si c'est une réponse ou une question.  
		 */
		return (reponseTrouvee())
				
				// Si c'est l'indice d'une réponse, il faut la description.
				? tabReponses[infoJeu.noeudCourant
						.getIndiceQuestionOuReponse()]
						
						//Sinon, c'est l'indice d'une question.
						: tabQuestions.get(infoJeu.noeudCourant
								.getIndiceQuestionOuReponse());
	}
	
	/**
	 * 
	 * @return si la position courante pointe sur une reponse ou non
	 */
	public boolean reponseTrouvee(){
		return infoJeu.noeudCourant.getArbreGauche() == null;
	}
	
	private void afficherDebug(){
		
		//DEBUG : On affiche le parcours des réponses seulement.
		if(infoJeu.noeudCourant.getArbreGauche() == null){
			Reponse r= tabReponses[infoJeu.noeudCourant
					.getIndiceQuestionOuReponse()];
			
			System.out.println(r);
			
		}
	}		
	
	/** 
	 * Ajuste la position courante sur la prochaine chaîne à partir
	 * d'une réponse négative.
	 */ 
	public void allerADroite(){

		infoJeu.derniereReponseEtaitPositive = false;

		infoJeu.noeudPrecedent = infoJeu.noeudCourant;
		infoJeu.noeudCourant = infoJeu.noeudCourant.getArbreDroit();

	}
	
	
	/** 
	 * Ajuste la position courante sur la prochaine chaîne à partir
	 * d'une réponse positive.
	 */ 
	public void allerAGauche(){
	
		infoJeu.noeudPrecedent = infoJeu.noeudCourant;
		infoJeu.noeudCourant = infoJeu.noeudCourant.getArbreGauche();
		infoJeu.derniereReponseEtaitPositive = true;
		
	}	
	

	/************************
	 * AJOUTER NOEUDS
	 * 
	 * Ajoute deux nouveaux noeuds dans l'arbre de connaissance.
	 * Une question et une réponse.  La position d'insertion dépends de la 
	 * dernière réponse de l'utilisateur.
	 * 
	 */
	private void ajouterNoeuds(Reponse reponseAjoute, int indiceQuestion){

		// Évite pls appels aux accesseurs pour obtenir les indices.
		int indiceReponse = nbReponses;
		
		// Création du noeud avec l'indice de la réponse.  
		// Il reste à connecter à la bonne question.
		ArbreConnaissance nTmp = 
				new ArbreConnaissance(indiceReponse,null, null);
		
		// Il faut  la réponse du noeud courant pour modifier 
		// son chemin à parcourir lorsqu'il change de côté..
	   Reponse repNoeudCourant;
					   
		// Si la bd est vide, racine == null alors on crée un autre  noeud
		//  pour l'indice de question.  On les rend accessibles par la racine.
		if(infoJeu.premierNoeud == null){
			
			infoJeu.premierNoeud = 
					new ArbreConnaissance(indiceQuestion, 
							nTmp, 
							null);
						
		}

		else {
			
			// Il faut  la réponse du noeud courant pour modifier son chemin à parcourir.
			repNoeudCourant = tabReponses[infoJeu.noeudCourant
					.getIndiceQuestionOuReponse()];

			// Pour le chemin parcouru du noeud droit.
			Liste copieChemin = cheminParcouru.clone();
			copieChemin.insererApres(Constantes.REPONSE_NEGATIVE);


			// Si la dernière réponse de l'utilisateur était positive, on ajoute le
			// noeud à gauche du noeud précédent le noeud courant.  
			if (infoJeu.derniereReponseEtaitPositive) {
		
				
				// On se fie que jamais noeudPrecedent est null si on se trouve ici.
				infoJeu.noeudPrecedent.setArbreGauche(
						new ArbreConnaissance(indiceQuestion,
								nTmp, 
								infoJeu.noeudCourant));
				
				// Il faut ajuster le chemin du noeud courant qui a changé de côté
				// dans l'arbre.
				repNoeudCourant.setIndice(copieChemin);					
				
			}


			// Si c'est la racine, on passe au else puisque c'est une question
			// et pareillement si on le noeud l'arbre droit est à null.

			// Si la réponse est ngative et que le noeud courant est une question,
			// on lie le noeud �à la droite du noeud précédent
			else 	if(infoJeu.noeudCourant.getArbreGauche() != null &&
					   infoJeu.noeudCourant.getArbreDroit() == null){
				
				// On se fit que jamais noeudPrecedent est null si on se trouve ici.
				infoJeu.noeudCourant.setArbreDroit(
						new ArbreConnaissance(indiceQuestion,
								nTmp, 
								null));				
			}

			// Sinon c'était une réponse, on replace les noeuds en mettant un 
			// nouveau noeud à droite du noeud courant et l'adresse de l'ancien 
			// noeud courant à droite du nouveau. 
			else {			
				
						// On insère à droite du noeud 
						// précédent en faisant le lien avec l'ancien courant.	
						infoJeu.noeudPrecedent.setArbreDroit(
								new ArbreConnaissance(indiceQuestion,
										nTmp, 
										infoJeu.noeudCourant));
					 					
						// Il faut ajuster le chemin du noeud courant qui a changé de côté
						// dans l'arbre.
						repNoeudCourant.setIndice(copieChemin);					
			}
		}

		// Pour le chemin parcouru du nouveau noeud.
		cheminParcouru.insererApres(Constantes.REPONSE_POSITIVE);
		reponseAjoute.setIndice(cheminParcouru);		
	}
	
	
	/**
	 *  Retourne la réponse qui correspond à la description reçue ou null
	 *  si inexistante.

	 * @param reponse : la réponse dont on doit verifier l'existence
	 * @return vrai si la réponse  se trouve dans la bd et faux sinon
	 */
	public Reponse getReponse(String reponse){

		
		/*
		 * Stratégie : Fouille linéaire.  On récupère une Reponse à la fois et 
		 * on compare l'attribut reponse à la réponse reçue.  La boucle se 
		 * termine si la collection est parcourue au complet ou que la réponse
		 * a été trouvée.
		 * 
		 */
		
		int i = 0;
		
		int taille = nbReponses;
		Reponse r = null;
		
		if(taille != 0){
			
			r = tabReponses[i];
		
			while(i < taille && !r.getReponse().equals(reponse)){
				i++;
				if(i < taille){
					r = tabReponses[i];
				}
				
				else {
					r = null;
				}
			}
			
		}
		
		// Autrement dit :: Si la taille du tableau de réponse est de 0 ou que i
		// == taile, c'est que la réponse n'existe pas.
		return r;
	}
	
	
	/**
	 * Retourne une chaîne avec les réponses obtenues et celles attendues 
	 * en regard à la réponse reçue.
	 * 
	 * @param reponse
	 * @return
	 */

	public String getDeroulementJeu(Reponse reponse) {

		String str = "\nVous auriez dû répondre";
		
		/*
		 * Stratégie : On parcourt le meme chemin parcouru en concaénant les 
		 * questions, les réponses obtenues et les réponses attendues.
		 */
		
		
		// Évite pls appels à l'accesseur.
		int nb = cheminParcouru.getNbElement();		
		
		try {
			
			// On se position au début..
			cheminParcouru.setPosDebut();

			// Un pointeur de parcours dans l'arbre.
			ArbreConnaissance tmp = infoJeu.premierNoeud;
			
			// On récupère le chemin parcouru de la réponse.
			Liste copie = reponse.getIndice();

			copie.setPosDebut();
			
			// On récupère la réponse obtenue à la première question.
			char obtenu = (char) cheminParcouru.getElement();
			char attendu = (char) copie.getElement();

			int i = 0;
			while (i < nb && obtenu == attendu){

				if(obtenu == Constantes.REPONSE_NEGATIVE){

					tmp = tmp.getArbreDroit();
				}
				else{
					tmp = tmp.getArbreGauche();
				}	

				
				// On déplace la position courante des deux listes.
				cheminParcouru.setPosSuivant();
				copie.setPosSuivant();
												
				obtenu = (char) cheminParcouru.getElement();
				attendu = (char) copie.getElement();
				
				if(attendu == obtenu){
					i++;
				}
			}
			
			// On a trouvé la réponse fautive si i < nb.
			if(i < nb){
				
				// On concatène la question récupère dans le tableau de questions 
				// à l'aide de l'indice du noeud actuellement récupérée.				
				// La copie du chemin de la réponse nous donne la réponse attendues.
				str+= ((attendu == Constantes.REPONSE_NEGATIVE)
						? " non "
								: " oui ") +
						"à la question : \n" +  
				        " ----" + tabQuestions.get(tmp.getIndiceQuestionOuReponse()) + 
				        " ?" + " ----" +
						" \nmais vous avez répondu " + 
				        ((obtenu == Constantes.REPONSE_NEGATIVE)
				        		? " non "
				        				: " oui ");
				
				i++;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
			
		return str;
	}
	
	/**
	 * Retourne une cha�ne contenant les caractères de la liste séparé par /.
	 * 
	 * @param cheminParcouru
	 * @return
	 */
	private String toString(Liste liste){

		String str = "";
		
		int nb = liste.getNbElement();
				
		try{

			liste.setPosDebut();

			for(int i = 0; i < nb; i ++){

				str += String.valueOf((char)liste.getElement()+"/");
				liste.setPosSuivant();
			}

		}catch(Exception e){
			e.printStackTrace();
		}		
		
		return str;
	}
}