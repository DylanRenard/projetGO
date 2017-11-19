package projetGO;

public class Grille 
{
	//Variables
	private Pion[][] grille;
	private String couleurJoueur = "Blanc";
	
	
	//Constructeur
	Grille(int dim)
	{
		grille = new Pion[dim][dim];
		for (int i = 0 ; i<13 ; i++)
			for(int j = 0 ; j<13 ; j++)
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
		
		if(caseOccupee(x, y)!=null) valide=false;
		//TODO ajout des conditions de placement : position entre 4 pions adverses et/ou emplacement ou le pion viens d etre enleve
		
		return valide;
	}
	
	public boolean placerPion(int x, int y)
	{
		if (placementValide(x,y))
		{
			grille[x][y] = new Pion(x,y,couleurJoueur);
			//TODO associer a un groupe + enlever les pions adverses
			
			return true;
		}
		else return false;
	}
	
	
	//Getteur Setteur (si besoin)
	
}
