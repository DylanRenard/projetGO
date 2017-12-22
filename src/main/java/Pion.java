import java.io.Serializable;

public class Pion implements Serializable
{
	//Variables
	private int x;				//coordonnee en x
	private int y;				//coordonnee en y
	private String couleur;		//couleur du pion : Blanc, Noir, Liberte ou Territoire
	private GroupePion gp;		//groupe auquel il appartient
	
	//Constructeurs
	public Pion (int x, int y, String couleur)	//utilise pour la creation d un pion lors de son placement
	{
		this.x=x;
		this.y=y;
		this.couleur=couleur;
		
	}
	
	public Pion (int x, int y, String couleur, GroupePion gp)	//utilise pour la creation de Liberte dans un groupe
	{
		this(x,y,couleur);
		this.gp = gp;
	}
	
	
	//Methode
	
	
	//Getteurs Setteurs
	public int getX() {return x;}
	
	public int getY() {return y;}

	public String getCouleur() {return couleur;}
	
	public GroupePion getGP() {return gp;}
	

	
	public void setGP(GroupePion gp) {this.gp = gp;}
}
