package projetGO;

public class Territoire extends GroupePion
{
	private String couleur = "Neutre";
	private boolean couleurModifiee = false;
	
	Territoire(Pion p,Grille g)
	{
		super(p,g);
	}
	
	public void fusion(GroupePion gp)
	{
		
	}
	
	public String getCouleur() {return couleur;}
}
