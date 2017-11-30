package projetGO;

import java.util.ArrayList;

public class Grille
{
	//Variables
	private int dim;							//dimension de la grille
	private Pion[][] grille;					//representation de la grille
	private String couleurJoueur = "Blanc";		//couleur du joueur en cour
	private Pion residu = null;					//memoire du pion unique detruit au tour precedent
	private int nbPionBlancDetruit = 0;			//nombre de pion blanc detruit
	private int nbPionNoirDetruit = 0;			//nombre de pion noir detruit
	private boolean joueurpasse = false;		//le joueur precedent a passe
	private ArrayList <GroupePion> listeGroupe;	//liste des groupes presents dans la grille
	private int nbCaseLibre;
	private boolean partieFinie = false;		//indicateur de fin de partie
	
	//Constructeur
	public Grille(int dim)
	{
		this.dim = dim;
		
		grille = new Pion[dim][dim];
		for (int i = 0 ; i<dim ; i++)
			for (int j = 0 ; j<dim ; j++)
				grille[i][j]=null;
		listeGroupe = new ArrayList<GroupePion>();
		nbCaseLibre=dim*dim;
	}
	
	
	//Methodes
	public boolean caseOccupee(int x, int y)		//renvoie vrai si la case est occupee, faux sinon
	{
		if(x<0 || y<0 || x>=dim || y>=dim) return false;
		else return grille[x][y]!=null;
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
		//si on enleve la derniere liberte d un de ses propres groupes
		else if ((caseOccupee(x+1,y) 
				&& grille[x+1][y].getCouleur()==couleurJoueur 
				&& grille[x+1][y].getGP().getNbLiberte()==1)	
			|| (caseOccupee(x-1,y) 
				&& grille[x-1][y].getCouleur()==couleurJoueur 
				&& grille[x-1][y].getGP().getNbLiberte()==1)) valide = false;
		
		return valide;
	}
	
	public boolean placerPion(int x, int y)		//renvoie vrai si le pion a effectivement ete place a l endroit indique
	{
		boolean upDateResidu = false;			//indique si le residu a ete mis a jour pendant ce tour
		//a passer a true si on enleve un unique pion (si destruction renvoie 1)
		
		if (placementValide(x,y))				//on cree un pion si on peut le placer puis on verifie ses cases adjacentes
		{
			grille[x][y] = new Pion(x,y,couleurJoueur);
			nbCaseLibre--;
			
			boolean rencontreAllie = false;
			int nbDetruit;
			
			if(caseOccupee(x+1,y))
			{
				if(grille[x+1][y].getCouleur()==couleurJoueur)
				{
					grille[x+1][y].getGP().ajouterPion(grille[x][y], this);
					rencontreAllie = true;
				}
				else 
				{
					if (grille[x+1][y].getGP().getGroupe()[x][y]!=null)
					{
						grille[x+1][y].getGP().getGroupe()[x][y]=null;
						grille[x+1][y].getGP().incNbLiberte(-1);
						
						if (grille[x+1][y].getGP().getNbLiberte()==0) 
						{
							listeGroupe.remove(grille[x+1][y].getGP());
							nbDetruit=grille[x+1][y].getGP().destruction(this);
							if(nbDetruit==1) residu=grille[x+1][y];
							upDateResidu = true;
							if(couleurJoueur=="Blanc") nbPionNoirDetruit+=nbDetruit;
							else nbPionBlancDetruit+=nbDetruit;
							nbCaseLibre+=nbDetruit;
						}
					}
				}
			}
			if(caseOccupee(x,y+1))
			{
				if(grille[x][y+1].getCouleur()==couleurJoueur)
					if(rencontreAllie && grille[x][y].getGP()!=grille[x][y+1].getGP())
					{
						listeGroupe.remove(grille[x][y+1].getGP());
						grille[x][y].getGP().fusion(grille[x][y+1].getGP());
					}
					else grille[x][y+1].getGP().ajouterPion(grille[x][y], this);
				else 
				{
					if (grille[x][y+1].getGP().getGroupe()[x][y]!=null)
					{
						grille[x][y+1].getGP().getGroupe()[x][y]=null;
						grille[x][y+1].getGP().incNbLiberte(-1);
						
						if (grille[x][y+1].getGP().getNbLiberte()==0)
						{
							listeGroupe.remove(grille[x][y+1].getGP());
							nbDetruit=grille[x][y+1].getGP().destruction(this);
							if(nbDetruit==1) residu=grille[x][y+1];
							upDateResidu = true;
							if(couleurJoueur=="Blanc") nbPionNoirDetruit+=nbDetruit;
							else nbPionBlancDetruit+=nbDetruit;
							nbCaseLibre+=nbDetruit;
						}
					}
				}
			}		
			if(caseOccupee(x-1,y))
			{
				if(grille[x-1][y].getCouleur()==couleurJoueur)
					if(rencontreAllie && grille[x][y].getGP()!=grille[x-1][y].getGP())
					{
						listeGroupe.remove(grille[x-1][y].getGP());
						grille[x][y].getGP().fusion(grille[x-1][y].getGP());
					}
					else grille[x-1][y].getGP().ajouterPion(grille[x][y], this);
				else 
				{
					if (grille[x-1][y].getGP().getGroupe()[x][y]!=null)
					{
						grille[x-1][y].getGP().getGroupe()[x][y]=null;
						grille[x-1][y].getGP().incNbLiberte(-1);
						
						if (grille[x-1][y].getGP().getNbLiberte()==0) 
						{
							listeGroupe.remove(grille[x-1][y].getGP());
							nbDetruit=grille[x-1][y].getGP().destruction(this);
							if(nbDetruit==1) residu=grille[x-1][y];
							upDateResidu = true;
							if(couleurJoueur=="Blanc") nbPionNoirDetruit+=nbDetruit;
							else nbPionBlancDetruit+=nbDetruit;
							nbCaseLibre+=nbDetruit;
						}
					}
				}
			}	
			if(caseOccupee(x,y-1))
			{
				if(grille[x][y-1].getCouleur()==couleurJoueur)
					if(rencontreAllie && grille[x][y].getGP()!=grille[x][y-1].getGP())
					{
						listeGroupe.remove(grille[x][y-1].getGP());
						grille[x][y].getGP().fusion(grille[x][y-1].getGP());
					}
					else grille[x][y-1].getGP().ajouterPion(grille[x][y], this);
				else 
				{
					if (grille[x][y-1].getGP().getGroupe()[x][y]!=null)
					{
						grille[x][y-1].getGP().getGroupe()[x][y]=null;
						grille[x][y-1].getGP().incNbLiberte(-1);
						
						if (grille[x][y-1].getGP().getNbLiberte()==0) 
						{
							listeGroupe.remove(grille[x][y-1].getGP());
							nbDetruit=grille[x][y-1].getGP().destruction(this);
							if(nbDetruit==1) residu=grille[x][y-1];
							upDateResidu = true;
							if(couleurJoueur=="Blanc") nbPionNoirDetruit+=nbDetruit;
							else nbPionBlancDetruit+=nbDetruit;
							nbCaseLibre+=nbDetruit;
						}
					}
				}
			}
			
			if(!rencontreAllie) listeGroupe.add(new GroupePion(grille[x][y],this));
				
			if(!upDateResidu) residu = null;	//si le residu n'a pas ete mis a jour il disparait
			
			return true;
		}
		else return false;
	}
	
	public void jouerUnTour(String passer)
	{
		if (passer=="passer")
		{
			if(joueurpasse)
			{
				partieFinie = true;
				decomptePoints();
			}
			
			joueurpasse=true;
			
			if(couleurJoueur=="Blanc") nbPionBlancDetruit++;
			else nbPionNoirDetruit++;
			
			if(couleurJoueur=="Blanc") couleurJoueur="Noir"; else couleurJoueur="Blanc" ;
		}
	}
	
	public void jouerUnTour(int x,int y)
	{
		if(placerPion(x,y))
		{
			if(couleurJoueur=="Blanc") couleurJoueur="Noir"; else couleurJoueur="Blanc" ;	//la couleur du joueur change pour le prochain tour
			joueurpasse=false;
		}
	}
	
	//TODO calcul points 
	//=> a la fin de l partie
	//=> combler les liberte des ses groupes avec ses pions detruits
	//=> regarder a chaque groupe s il se fait manger (seuls les groupes ayant un pion sur un bord ne seront pas detruit)
	//=> reiterer
	//=> compter le nombre de pions poses - nb de pions detruit
	public void decomptePoints()
	{
		while(nbCaseLibre>0)
		{
			for(GroupePion gp : listeGroupe)
			{
				//TODO faire la fonction
			}
		}
	}
	
	//Getteurs Setteurs
	public boolean getPartieFinie() {return partieFinie;}
	
	public int getDim() {return dim;}
	
	public Pion[][] getGrille() {return grille;}
	
	public Pion getResidu() {return residu;}
	
	public ArrayList <GroupePion> getListeGroupe() {return listeGroupe;}
}
