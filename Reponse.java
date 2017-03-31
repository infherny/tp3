/**
 * Classe qui représente une reponse dans le jeu du divinateur
 * 
 * @version  Hiver 2017
 * @author Olivier Michaud, Quentin Le Meur
 *
 */
import java.io.Serializable;

import javax.swing.ImageIcon;

public class Reponse  implements Serializable {
	
	/***************************************************************************
	 * 							Attributs 
	 **************************************************************************/
	
	//Attribut contenant le reponse
	private String reponse;
	
	//Attribut contenant les indices(O ou N), menant vers la reponse
	private Liste indice;
	
	private ImageIcon image;
	

	/***************************************************************************
	 * 							Constructeur 
	 **************************************************************************/
	
	/**
	 * Constructeur par default
	 * 
	 */
	public Reponse(){
		
		/*
		 * On met tout a null
		 */
		
		reponse = null;
		indice = new Liste();	
	}
	
	
	/**
	 * constructeur par copie d'attribut
	 * 
	 * @param reponse
	 * @param indice
	 */
	public Reponse(String reponse, Liste indice){
		
		this.reponse = reponse;
		this.indice = indice;	
	}
	
	public Reponse(String reponse, ImageIcon image){
		
		this.reponse = reponse;
		this.image = image;	
		indice = new Liste();
	}
	
	
	
	/***************************************************************************
	 * 							Mutateur 
	 **************************************************************************/
	
	/**
	 * Mutateur de l'attribut reponse
	 * 
	 * @param reponse
	 */
	public void setReponse(String reponse) {
		this.reponse = reponse;
	}
	
	
	/**
	 * Mutateur de l'atribut indice
	 * 
	 * @param indice
	 */
	public void setIndice(Liste indice) {
		this.indice = indice;
	}
	
	
	/***************************************************************************
	 * 							Accesseur 
	 **************************************************************************/
		
	/**
	 * Accesseur de l'attribut reponse
	 * 
	 * 
	 * @return une chaine contenant une reponse
	 */
	public String getReponse() {
		return reponse;
	}
	
	
	/**
	 * Accesseur de l'atribut indice
	 * 
	 * @return une Liste d'indice
	 */
	public Liste getIndice() {
		return indice;
	}
}