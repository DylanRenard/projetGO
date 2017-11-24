package projetGO;

public class GroupePion 
{
	//rassembler les pions par groupe pour avoir les "libertes" du groupe
	
	//Variables
	private Pion[][] groupe;
	private int nbLiberte=0;
	
	//Constructeur
	public GroupePion(Pion p, Grille g)
	{
		int x = p.getX();
		int y = p.getY();
		
		groupe = new Pion[g.getDim()][g.getDim()];
		for (int i = 0 ; i<13 ; i++)
			for (int j = 0 ; j<13 ; j++)
				groupe[i][j]=null;
		groupe[x][y]=p;
		p.setGP(this);
		
		if (x+1<groupe.length && g.caseOccupee(x+1, y)) { groupe[x+1][y] = new Pion(x+1, y , "Liberte", this); nbLiberte++; }
		if (y+1<groupe.length && g.caseOccupee(x, y+1)) { groupe[x][y+1] = new Pion(x, y+1 , "Liberte", this); nbLiberte++; }
		if (x-1>=0 && g.caseOccupee(x-1, y)) { groupe[x-1][y] = new Pion(x-1, y , "Liberte", this); nbLiberte++; }
		if (y-1>=0 && g.caseOccupee(x, y-1)) { groupe[x][y-1] = new Pion(x, y-1 , "Liberte", this); nbLiberte++; }
	}
	
	//Methodes
	public void ajouterPion (Pion p, Grille g)
	{
		int x = p.getX();
		int y = p.getY();
			
		groupe[x][y]=p;
		p.setGP(this);
		nbLiberte--;
		
		if (x+1<groupe.length && g.caseOccupee(x+1,y) && groupe[x+1][y] == null) { groupe[x+1][y] = new Pion(x+1, y , "Liberte", this); nbLiberte++; }
		if (y+1<groupe.length && g.caseOccupee(x,y+1) && groupe[x][y+1] == null) { groupe[x][y+1] = new Pion(x, y+1 , "Liberte", this); nbLiberte++; }
		if (x-1>=0 && g.caseOccupee(x-1,y) && groupe[x-1][y] == null) { groupe[x-1][y] = new Pion(x-1, y , "Liberte", this); nbLiberte++; }
		if (y-1>=0 && g.caseOccupee(x,y-1) && groupe[x][y-1] == null) { groupe[x][y-1] = new Pion(x, y-1 , "Liberte", this); nbLiberte++; }
	}
		
	public void fusion(GroupePion gp)
	{
		Pion[][] groupeGP = gp.getGroupe();
		for (int i=0 ; i<groupe.length ; i++)
			for (int j=0 ; j<groupe.length ; j++)
			{
				if (groupeGP[i][j] != null && groupe[i][j] != null)
				{
					if (groupeGP[i][j].getCouleur()!="Liberte" && groupe[i][j].getCouleur()=="Liberte")
					{
						groupe[i][j]=groupeGP[i][j];
						nbLiberte--;
						groupe[i][j].setGP(this);
					}
				}
				else if (groupe[i][j] == null && groupeGP[i][j] != null)
				{
					groupe[i][j]=groupeGP[i][j];
					if(groupeGP[i][j].getCouleur()=="Liberte") nbLiberte++;
					groupe[i][j].setGP(this);
				}
			}
	}
	
	public int destruction(Grille g)
	{
		int nbPionEnleve = 0;
		
		if(nbLiberte==0)
		{
			for (int i=0 ; i<groupe.length ; i++)
				for (int j=0 ; j<groupe.length ; j++)
				{
					if(groupe[i][j]!=null) 
					{
						nbPionEnleve++;
						g.getGrille()[i][j] = null;
						
						if (i+1<groupe.length 
								&& !g.caseOccupee(i+1,j) 
								&& g.getGrille()[i+1][j].getCouleur() != groupe[i][j].getCouleur())
						{
							g.getGrille()[i+1][j].getGP().getGroupe()[i][j] = new Pion(i, j , "Liberte", g.getGrille()[i+1][j].getGP());
							g.getGrille()[i+1][j].getGP().incNbLiberte(1);
						}			
						if (j+1<groupe.length 
								&& !g.caseOccupee(i,j+1)
								&& g.getGrille()[i][j+1].getCouleur() != groupe[i][j].getCouleur())
						{
							g.getGrille()[i][j+1].getGP().getGroupe()[i][j] = new Pion(i, j , "Liberte", g.getGrille()[i][j+1].getGP());
							g.getGrille()[i][j+1].getGP().incNbLiberte(1);
						}
						if (i-1>=0 
								&& !g.caseOccupee(i-1,j) 
								&& g.getGrille()[i-1][j].getCouleur() != groupe[i][j].getCouleur())
						{
							g.getGrille()[i-1][j].getGP().getGroupe()[i][j] = new Pion(i, j , "Liberte", g.getGrille()[i-1][j].getGP());
							g.getGrille()[i-1][j].getGP().incNbLiberte(1);
						}
						if (j-1>=0 
								&& !g.caseOccupee(i,j-1) 
								&& g.getGrille()[i][j-1].getCouleur() != groupe[i][j].getCouleur())
						{
							g.getGrille()[i][j-1].getGP().getGroupe()[i][j] = new Pion(i, j , "Liberte", g.getGrille()[i][j-1].getGP());
							g.getGrille()[i][j-1].getGP().incNbLiberte(1);
						}
					}
				}
		}
		
		return nbPionEnleve;
	}
	
	//Getteur Setteur (si besoin)
	public Pion[][] getGroupe() {return groupe;}
	
	public int getNbLiberte() {return nbLiberte;}
	
	public void incNbLiberte(int n) {nbLiberte+=n;}
}
