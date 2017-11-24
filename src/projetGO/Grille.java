package projetGO;

public class Grille 
{
	//Variables
	private int dim;						//dimension de la grille
	private Pion[][] grille;				//representation de la grille
	private String couleurJoueur = "Blanc";	//couleur du joueur en cour
	private boolean partieFinie = false;	//indicateur de fin de partie
	private Pion residu = null;				//memoire du pion unique detruit au tour precedent
	
	//Constructeur
	public Grille(int dim)
	{
		this.dim = dim;
		
		grille = new Pion[dim][dim];
		for (int i = 0 ; i<dim ; i++)
			for (int j = 0 ; j<dim ; j++)
				grille[i][j]=null;
	}
	
	
	//Methodes
	public boolean caseOccupee(int x, int y)		//renvoie vrai si la case est occupee, faux sinon
	{
		return grille[x][y]!=null;
	}
	
	public boolean placementValide(int x, int y)	//renvoie vrai si on peut placer le pion a l endroit indique, faux sinon
	{
		boolean valide = true;
		//si on est pas sur la grille
		if (x<0 || y<0 || x>=dim || y>=dim) valide = false;
		//si on place a l endroit du residu
		else if (residu != null && x == residu.getX() && y == residu.getY()) valide = false;
		//si on place ou il y a deja un pion
		else if (caseOccupee(x, y)) valide=false;
		//si on place au millieu de 4 adversaires sans engendrer la destruction d au moins un groupe adverse
		else if (caseOccupee(x+1,y) && grille[x+1][y].getCouleur() != couleurJoueur
				&& caseOccupee(x,y+1) && grille[x][y+1].getCouleur() != couleurJoueur
				&& caseOccupee(x-1,y) && grille[x-1][y].getCouleur() != couleurJoueur
				&& caseOccupee(x,y-1) && grille[x][y-1].getCouleur() != couleurJoueur)
		{
			if ( grille[x+1][y].getGP().getNbLiberte()-1 > 0
					&& grille[x][y+1].getGP().getNbLiberte()-1 > 0
					&& grille[x-1][y].getGP().getNbLiberte()-1 > 0
					&& grille[x][y-1].getGP().getNbLiberte()-1 > 0 ) valide = false;
		}
		//TODO ajout des conditions de placement
		//si on enleve la derniere liberte d un de ses propres groupes
		
		return valide;
	}
	
	public boolean placerPion(int x, int y)		//renvoie vrai si le pion a effectivement ete place a l endroit indique
	{
		boolean upDateResidu = false;			//indique si le residu a ete mis a jour pendant ce tour
		//a passer a true si on enleve un unique pion (si destruction renvoie 1)
		
		if (placementValide(x,y))				//on cree un pion si on peut le placer puis on verifie ses cases adjacentes
		{
			grille[x][y] = new Pion(x,y,couleurJoueur);
			
			//TODO associer a un groupe + enlever les pions adverses
			//verification à x+1 , x-1 , y+1 et y-1
			//si adversaire => regarder son groupe, si liberte => liberte-1, si plus de liberté => le supprimer
			//si alié => ajout au groupe et maj des libertés
			//si allié d'un 2è groupe => fusion des groupes et maj des libertés
			//si personne à la fin => création d'un groupe
			
			
			if(!upDateResidu) residu = null;	//si le residu n'a pas ete mis a jour il disparait
			
			if(couleurJoueur=="Blanc") couleurJoueur="Noir"; else couleurJoueur="Blanc" ;	//la couleur du joueur change pour le prochain tour
			
			return true;
		}
		else return false;
	}
	
	
	//Getteurs Setteurs
	public boolean getPartieFinie() {return partieFinie;}
	
	public int getDim() {return dim;}
	
	public Pion[][] getGrille() {return grille;}
}
