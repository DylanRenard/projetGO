package projetGO;

public class GroupePion 
{
	//TODO rassembler les pions par groupe pour avoir les "libertes" du groupe
	
	//Variables
	private Pion[][] groupe;
	private int nbLiberte=0;
	
	//Constructeur
	public GroupePion(Pion p, Grille e)
	{
		int x = p.getX();
		int y = p.getY();
		
		groupe = new Pion[e.getDim()][e.getDim()];
		for (int i = 0 ; i<13 ; i++)
			for (int j = 0 ; j<13 ; j++)
				groupe[i][j]=null;
		groupe[x][y]=p;
		p.setGP(this);
		
		if (e.caseOccupee(x+1, y) != null) { groupe[x+1][y] = new Pion(x+1, y , "Liberte", this); nbLiberte++; }
		if (e.caseOccupee(x, y+1) != null) { groupe[x][y+1] = new Pion(x, y+1 , "Liberte", this); nbLiberte++; }
		if (e.caseOccupee(x-1, y) != null) { groupe[x-1][y] = new Pion(x-1, y , "Liberte", this); nbLiberte++; }
		if (e.caseOccupee(x, y-1) != null) { groupe[x][y-1] = new Pion(x, y-1 , "Liberte", this); nbLiberte++; }
	}
	
	//Methodes
	public void fusion(GroupePion gp)
	{
		//TODO fusion de this avec gp
	}
	
	public void destruction()
	{
		if(nbLiberte==0)
		{
			//TODO 
		}
	}
	
	//Getteur Setteur (si besoin)
	
}
