import java.io.Serializable;

/**
 * Classe qui représente une liste a simple enchainement.  Avec cette liste, 
 * on peut ajouter au debut, a la fin, avant et apres la position courante.  Il
 * est aussi possible de supprimer l'element sur la position actuelle et aussi 
 * d'acceder a l'ement sur la position actuelle.  Pour s'assurer que l'on ajoute
 * au bon endroit, la position courante est toujours inserré sur le dernier 
 * element ajouté, ou au debut si la liste est vide.  Il est aussi possible
 * de depelacer la position.  
 *
 * @author Olivier Michaud, Quentin Le Meur, Pierre Beslile
 * @version Hiver2017
 */
public class Liste  implements Serializable{

	
	/**
	 * Classe représentant un noeud a une simple enchainement
	 * 
	 * @author Olivier Michaud
	 * @version Hiver2017
	 */
	public class Noeud  implements Serializable{
		
		
		/***********************************************************************
		 * 						     Attribut 
		 **********************************************************************/
		
		//Attribut de l'element contenu dans le noeud
		private Object element;
		
		//Attribut qui pointe vers le noeud suivant
		private Noeud suivant;
		
		
		/***********************************************************************
		 * 							Constructeur 
		 **********************************************************************/
		
		/**
		 * Constructeur par copie d'attributs
		 * 
		 * @param element
		 * @param suivant
		 */
		public Noeud(Object element, Noeud suivant){
			
			this.element = element;
			this.suivant = suivant;
			
		}
	}
	
	
	/***************************************************************************
	 * 							Attribut 
	 **************************************************************************/
	
	//Attribut qui contient les positions des noeud
	private Noeud debut;
	private Noeud fin;
	private Noeud position;
	
	//Attribut qui contient le nombre d'element dans la liste
	private int nbElement;
	
	
	/***************************************************************************
	 * 							Constructeur 
	 **************************************************************************/
	
	/**
	 * Constructeur par default
	 * 
	 */
	public Liste(){
		
		/*
		 *On met les attribut des Noeuds a Null et le nombre d'element a 0 
		 */
		
		debut = fin = position = null;
		nbElement = 0;
	}
	
	
	/***************************************************************************
	 * 							Acceseur 
	 **************************************************************************/
	
	/**
	 * Acceseur de l'element a la position courante
	 * 
	 * @return l'Object contenu dans le noeud courant
	 */
	public Object getElement(){
		
		return position.element;
	}
	
	
	public int getNbElement() {
		return nbElement;
	}
	
	
	/***************************************************************************
	 * 							Les comportements 
	 **************************************************************************/
	



	/**
	 * Retourne une copie de la liste courante
	 * 
	 */
	public Liste clone(){
		
		
		/**
		 *Strategie: on créé une nouvelle liste et on copie chaque element
		 * de l'ancienne liste contenu dans des noeuds dans la nouvelle liste
		 * 
		 */
		Liste nouvelleListe = new Liste();
		
		Noeud position = this.position;
		this.setPosDebut();
		while(nouvelleListe.nbElement != this.nbElement){
			
			nouvelleListe.insererApres(this.position.element);
			this.setPosSuivant();	
		}
		
		this.position = position;
		return nouvelleListe;
	}
	
	
	/**
	 * Deplace la position au debut de la liste
	 * 
	 */
	public void setPosDebut(){
		
		position = debut;
	}
	
	
	/**
	 * Deplacer la position a la fin de la liste
	 * 
	 */
	public void setPosFin(){
		
		position = fin;
	}
	
	
	/**
	 * Deplace la position a la position
	 * 
	 * 
	 */
	public void setPosSuivant(){
		
		if(position != fin){
			
			position = position.suivant;
		}
	}
	
	
	/**
	 * Deplace la solutuon a l'element precedant
	 * 
	 */
	public void setPosPrecedent(){
		
		if(position != debut){
			
			Noeud tmp = debut;
			while(tmp.suivant != position){
				
				tmp = tmp.suivant;
			}
			
			position = tmp;
		}
	}
	
	
	/**
	 * Inserer au debut de la liste
	 * 
	 * @param element
	 */
	public void insererDebut(Object element){
		
		debut = new Noeud(element,debut);
		if(nbElement == 0){
			
			fin = debut;
		}
		position = debut;
		nbElement ++;
	}
	
	
	/**
	 * Inserer a la fin de la liste 
	 * 
	 * @param element
	 */
	public void insererFin(Object element){
		
		if(nbElement == 0){
			
			insererDebut(element);	
		}
		
		else{
			
			fin.suivant = new Noeud(element,null);
			fin = fin.suivant;
			position = fin;
			nbElement ++;
		}
	}
	
	
	/**
	 * Inserer apres la position actuel dans la liste
	 * 
	 * @param element
	 */
	public void insererApres(Object element){
		
		if(nbElement == 0){
			
			insererDebut(element);
		}
		
		else{
			
			position.suivant = new Noeud(element, position.suivant);
			if(position == fin){
				
				fin = position.suivant;
			}
			
			position = position.suivant;
			nbElement++;
		}
	}
	
	
	/**
	 * Inserer avant la position actuelle
	 * 
	 * @param element
	 */
	public void insererAvant(Object element){
		
		if(nbElement ==0){
			
			insererDebut(element);
		}
		
		else{
			
			Noeud tmp = position;
			insererApres(position.element);
			tmp.element = element;
			position = tmp;
		}	
	}
	
	
	/**
	 * Supprimer un element de la liste a la 
	 * position actuelle
	 * 
	 * @throws Exception
	 */
	public void supprimer() throws Exception{
		
		/*
		 * Stratwgie, si la liste est vide, on leve une exeption. Sinon, si 
		 * la postion est au debut, on met le debut a l'element suivant et on
		 * change la position au nouveau debut.  
		 * Si la position est a la fin, on depelace la fin vers la positon
		 * precedante et on met la position sur la nouvelle fin.
		 * Sinon, on met l'element de la positon suivant dans la postion 
		 * actuelle, et la position suivante a la position suivante de l'element
		 * suivant.
		 * Finalement, on baisse le nombre d'element dans la liste.
		 * 
		 * 
		 */
		if(nbElement == 0){
			
			throw new Exception("La file est vide");
		}
		if(position == debut){
			
			debut = debut.suivant;
			position = debut;
			nbElement--;
			if(debut == null){
				
				fin = null;
			}
		}
		else if(position == fin){
			
			setPosPrecedent();
			position.suivant = null;
			fin = position;
		}
		else{
			
			position.element = position.suivant.element;
			position.suivant = position.suivant.suivant;
			if(position.suivant == null){
				
				fin = position;
			}	
		}	
	}	
}