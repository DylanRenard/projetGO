package projetGO;

public class Grille 
{
	//Variables
	private int dim;
	private Pion[][] grille;
	private String couleurJoueur = "Blanc";
	private boolean partieFinie = false;
	
	//Constructeur
	public Grille(int dim)
	{
		this.dim = dim;
		
		grille = new Pion[dim][dim];
		for (int i = 0 ; i<13 ; i++)
			for (int j = 0 ; j<13 ; j++)
				grille[i][j]=null;
	}
	
	
	//Methodes
	public Pion caseOccupee(int x, int y)
	{
		return grille[x][y];
	}
	
	public boolean placementValide(int x, int y)
	{
		boolean valide = true;
		
		if (x<0 || y<0 || x>=dim || y>=dim) valide = false;
		else if (caseOccupee(x, y)!=null) valide=false;
		//TODO ajout des conditions de placement
		//position entre 4 pions adverses
		//emplacement ou le pion viens d etre enleve
		
		return valide;
	}
	
	public boolean placerPion(int x, int y)
	{
		if (placementValide(x,y))
		{
			grille[x][y] = new Pion(x,y,couleurJoueur);
			
			//TODO associer a un groupe + enlever les pions adverses
			//verification � x+1 , x-1 , y+1 et y-1
			//si adversaire => regarder son groupe, si plus de libert� => le supprimer
			//si ali� => ajout au groupe et maj des libert�s
			//si ali� d'un 2� groupe => fusion des groupes et maj des libert�s
			//si personne � la fin => cr�ation d'un groupe
			
			
			
			if(couleurJoueur=="Blanc") couleurJoueur="Noir"; else couleurJoueur="Blanc" ;
			
			return true;
		}
		else return false;
	}
	
	
	//Getteur Setteur (si besoin)
	public boolean getPartieFinie() {return partieFinie;}
	
	public int getDim() {return dim;}
}
