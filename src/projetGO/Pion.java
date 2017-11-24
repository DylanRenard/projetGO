package projetGO;

public class Pion 
{
	//Variable
	private int x;
	private int y;
	private String couleur;
	private GroupePion gp;
	
	//Constructeur
	public Pion (int x, int y, String couleur)
	{
		this.x=x;
		this.y=y;
		this.couleur=couleur;
		
	}
	
	public Pion (int x, int y, String couleur, GroupePion gp)
	{
		this(x,y,couleur);
		this.gp = gp;
	}
	
	
	//Methodes
	
	
	//Getteur Setteur (si besoin)
	public int getX() {return x;}
	
	public int getY() {return y;}
	
	public String getCouleur(){return couleur;}
	
	public void setGP(GroupePion gp) {this.gp = gp;}
}
