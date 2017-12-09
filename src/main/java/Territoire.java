

public class Territoire extends GroupePion
{
	private String couleur;
	
	Territoire(Pion p, Grille g, String couleur)
	{
		super();
		
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
		this.couleur=couleur;
	}
	
	public void ajouterPion (Pion p, Grille g)
	{
		int x = p.getX();
		int y = p.getY();
		//ajout du pion dans le groupe
		groupe[x][y]=p;
		p.setGP(this);
	}
	
	public void fusion(Territoire gp)
	{
		Pion[][] groupeGP = gp.getGroupe();
		//on parcours toute la "grille" secondaire du groupe a fusioner
		for (int i=0 ; i<groupe.length ; i++)
			for (int j=0 ; j<groupe.length ; j++)
			{
				if(groupeGP[i][j] != null && groupeGP[i][j].getCouleur()=="Territoire" )
				{
					groupe[i][j]=groupeGP[i][j];
				}
			}
		if(gp.getCouleur()=="Neutre" || couleur=="Neutre" || (gp.getCouleur()!="" && couleur!="" && gp.getCouleur()!=couleur))
		{
			couleur = "Neutre";
		}
		else
		{
			if (gp.getCouleur()!="") couleur = gp.getCouleur();
		}
	}
	
	public String getCouleur() {return couleur;}
}
