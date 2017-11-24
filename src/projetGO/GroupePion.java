package projetGO;

public class GroupePion 
{
	//rassembler les pions par groupe pour avoir les "libertes" du groupe
	
	//Variables
	private Pion[][] groupe;		//"grille" secondaire contenant que les elements du groupe
	private int nbLiberte=0;		//nombre de liberte du groupe
	private boolean occupeUnBord;	//indique si un pion du groupe est sur le bord de la grille
	
	//Constructeur
	public GroupePion(Pion p, Grille g)		//on cree un groupe pour un pion isole
	{
		//recuperation des coordonnees du pion
		int x = p.getX();
		int y = p.getY();
		//initialisation de la "grille" secondaire
		groupe = new Pion[g.getDim()][g.getDim()];
		for (int i = 0 ; i<g.getDim() ; i++)
			for (int j = 0 ; j<g.getDim() ; j++)
				groupe[i][j]=null;
		//ajout du pion dans le groupe
		groupe[x][y]=p;
		p.setGP(this);
		//ajout des libertes sur la "grille" secondaire
		if (x+1<groupe.length && !g.caseOccupee(x+1, y)) { groupe[x+1][y] = new Pion(x+1, y , "Liberte", this); nbLiberte++; }
		if (y+1<groupe.length && !g.caseOccupee(x, y+1)) { groupe[x][y+1] = new Pion(x, y+1 , "Liberte", this); nbLiberte++; }
		if (x-1>=0 && !g.caseOccupee(x-1, y)) { groupe[x-1][y] = new Pion(x-1, y , "Liberte", this); nbLiberte++; }
		if (y-1>=0 && !g.caseOccupee(x, y-1)) { groupe[x][y-1] = new Pion(x, y-1 , "Liberte", this); nbLiberte++; }
		
		if(x==0 || y==0 || x==g.getDim()-1 || y==g.getDim()-1) occupeUnBord = true;
		else occupeUnBord=false;
	}
	
	//Methodes
	public void ajouterPion (Pion p, Grille g)
	{
		int x = p.getX();
		int y = p.getY();
		//ajout du pion dans le groupe
		groupe[x][y]=p;
		p.setGP(this);
		nbLiberte--;
		//maj des libertes sur la "grille" secondaire
		if (x+1<groupe.length && !g.caseOccupee(x+1,y) && groupe[x+1][y] == null) { groupe[x+1][y] = new Pion(x+1, y , "Liberte", this); nbLiberte++; }
		if (y+1<groupe.length && !g.caseOccupee(x,y+1) && groupe[x][y+1] == null) { groupe[x][y+1] = new Pion(x, y+1 , "Liberte", this); nbLiberte++; }
		if (x-1>=0 && !g.caseOccupee(x-1,y) && groupe[x-1][y] == null) { groupe[x-1][y] = new Pion(x-1, y , "Liberte", this); nbLiberte++; }
		if (y-1>=0 && !g.caseOccupee(x,y-1) && groupe[x][y-1] == null) { groupe[x][y-1] = new Pion(x, y-1 , "Liberte", this); nbLiberte++; }
	
		if(x==0 || y==0 || x==g.getDim()-1 || y==g.getDim()-1) occupeUnBord = true;
	}
		
	public void fusion(GroupePion gp)	//fusion du groupe actuel avec un groupe a fusioner
	{
		Pion[][] groupeGP = gp.getGroupe();
		//on parcours toute la "grille" secondaire du groupe a fusioner
		for (int i=0 ; i<groupe.length ; i++)
			for (int j=0 ; j<groupe.length ; j++)
			{
				if (groupeGP[i][j] != null && groupe[i][j] != null)	//si les deux cases sont occupees
				{
					if (groupeGP[i][j].getCouleur()!="Liberte" && groupe[i][j].getCouleur()=="Liberte")	//un pion du groupe a fusioner est a la place d une liberte du groupe actuel
					{
						//le pion remplace la liberte
						groupe[i][j]=groupeGP[i][j];
						nbLiberte--;
						groupe[i][j].setGP(this);
					}
				}
				else if (groupe[i][j] == null && groupeGP[i][j] != null)	//si le groupe a fusioner contient un element a un endroit vide du groupe actuel
				{
					//on place l element dans le groupe actuel
					groupe[i][j]=groupeGP[i][j];
					if(groupeGP[i][j].getCouleur()=="Liberte") nbLiberte++;
					groupe[i][j].setGP(this);
				}
			}
		if (gp.getOccupeUnBord()) occupeUnBord=true;
	}
	
	public int destruction(Grille g)	//renvoie le nombre de pion effectivement detruit
	{
		int nbPionEnleve = 0;
		
		if(nbLiberte==0)	//on verifie que le nombre de libertes est egal a 0
		{
			//on parcours toute la "grille" secondaire
			for (int i=0 ; i<groupe.length ; i++)
				for (int j=0 ; j<groupe.length ; j++)
				{
					//si on rencontre un pion
					if(groupe[i][j]!=null) 
					{
						nbPionEnleve++;
						//on enleve le pion de la grille
						g.getGrille()[i][j] = null;
						//on verifie les cases adjacentes pour mettre les libertes des groupes de pions alentours a jour
						if (i+1<groupe.length 
								&& g.caseOccupee(i+1,j) 
								&& g.getGrille()[i+1][j].getCouleur() != groupe[i][j].getCouleur())
						{
							g.getGrille()[i+1][j].getGP().getGroupe()[i][j] = new Pion(i, j , "Liberte", g.getGrille()[i+1][j].getGP());
							g.getGrille()[i+1][j].getGP().incNbLiberte(1);
						}			
						if (j+1<groupe.length 
								&& g.caseOccupee(i,j+1)
								&& g.getGrille()[i][j+1].getCouleur() != groupe[i][j].getCouleur())
						{
							g.getGrille()[i][j+1].getGP().getGroupe()[i][j] = new Pion(i, j , "Liberte", g.getGrille()[i][j+1].getGP());
							g.getGrille()[i][j+1].getGP().incNbLiberte(1);
						}
						if (i-1>=0 
								&& g.caseOccupee(i-1,j) 
								&& g.getGrille()[i-1][j].getCouleur() != groupe[i][j].getCouleur())
						{
							g.getGrille()[i-1][j].getGP().getGroupe()[i][j] = new Pion(i, j , "Liberte", g.getGrille()[i-1][j].getGP());
							g.getGrille()[i-1][j].getGP().incNbLiberte(1);
						}
						if (j-1>=0 
								&& g.caseOccupee(i,j-1) 
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
	
	public int remplirLiberte()
	{
		//TODO faire la fonction renvoie le nombre de pion ajoute
		
		return 0;
	}
	
	//Getteurs Setteurs
	public Pion[][] getGroupe() {return groupe;}
	
	public int getNbLiberte() {return nbLiberte;}
	
	public boolean getOccupeUnBord() {return occupeUnBord;}
	
	public void incNbLiberte(int n) {nbLiberte+=n;}
}
