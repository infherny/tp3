import java.io.Serializable;

	/**
	 * Classe  pour maintenir le liens entre les questions et leur réponse.
	 * 
	 * C'est une arborescence de question/réponse dans le jeu du divinateur.  
	 * Elle sert essentiellement à conserver les liens entre les questions et leur 
	 * réponse dans le jeu du divinateur (voir énoncé tp2 hiver2017).
	 * 
	 * Chaque noeud contient un numéro d'indice et deux liens vers un autre
	 * NoeudConnnaissance.  
	 * 
	 * Le numéro de référence est un indice dans un tableau de questions ou
	 * d'un tableau de réponses.
	 * 
	 * Si le deux liens sont à null, c'est que la référence est celle d'une réponse 
	 * dans le tableau des réponses.  Autrement, c'est une question dans le 
	 * tableau de questions.
	 * 
	 * Le noeud reponsePositive est la référence vers le noeud de connaissance 
	 * qui doit étre utilisée si la réponse de l'utilisateur est oui à la question 
	 * référencée dans le tableau de questions.  
	 * Ce noeud est null seulement pour une réponse.
	 * 
	 * S'il répond non, il faut utiliser l'autre noeud.  Si ce noeud est à null et 
	 * que l'utilisateur a répondu non, on est dans une situation où on ne 
	 * connait pas ce à quoi pense l'utilisateur. 
	 * 
	 * @author Pierre Bélisle
     * @version (copyright 2017).
	 */
	public class ArbreConnaissance implements Serializable{
		
		// Le numéro de case d'une question ou d'une réponse.
		private int indiceQuestionOuReponse;
		private ArbreConnaissance arbreGauche;
		private ArbreConnaissance arbreDroit;
		
		/**
		 * Constructeur d'un noeud de l'arbre.
		 * 
		 * @param ref Un indice de tableau sur la question ou la réponse.
		 * @param reponsePositive La prochaine question à poser si la 
		 *                                             réponse est positive.
		 * @param reponseNegative La prochaine question à poser si la 
		 *                                              réponse est négative.
		 */
		public ArbreConnaissance(int indiceQuestionOuReponse, 
				ArbreConnaissance reponsePositive, 
				ArbreConnaissance reponseNegative) {

			this.indiceQuestionOuReponse = indiceQuestionOuReponse;
			this.arbreGauche = reponsePositive;
			this.arbreDroit = reponseNegative;
		}
		
		/**
		 * @return the reponsePositive
		 */
		public ArbreConnaissance getArbreGauche() {
			return arbreGauche;
		}

		/**
		 * @param reponsePositive the reponsePositive to set
		 */
		public void setArbreGauche(ArbreConnaissance reponsePositive) {
			this.arbreGauche = reponsePositive;
		}

		/**
		 * @return the reponseNegative
		 */
		public ArbreConnaissance getArbreDroit() {
			return arbreDroit;
		}

		/**
		 * @param reponseNegative La nouvelle reponseNegative
		 */
		public void setArbreDroit(ArbreConnaissance reponseNegative) {
			this.arbreDroit = reponseNegative;
		}

		/**
		 * @return the refQuestionOuReponse
		 */
		public int getIndiceQuestionOuReponse() {
			return indiceQuestionOuReponse;
		}		
	}