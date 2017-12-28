

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.swing.JFileChooser;

public class Grille implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//Variables
	private int dim;							//dimension de la grille
	private Pion[][] grille;					//representation de la grille
	private String couleurJoueur = "Blanc";		//couleur du joueur en cours
	private Pion residu = null;					//memoire du pion unique detruit au tour precedent
	@SuppressWarnings("unused")
	private int nbPionBlancDetruit = 0;			//nombre de pion blanc detruit
	@SuppressWarnings("unused")
	private int nbPionNoirDetruit = 0;			//nombre de pion noir detruit
	private boolean joueurpasse = false;		//le joueur precedent a passe
	private ArrayList <GroupePion> listeGroupe;	//liste des groupes presents dans la grille
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
							if (!rencontreAllie)
							{
								listeGroupe.add(new GroupePion(grille[x][y],this));
								rencontreAllie = true;
							}
							nbDetruit=grille[x+1][y].getGP().destruction(this);
							if(nbDetruit==1) residu=grille[x+1][y];
							upDateResidu = true;
							if(couleurJoueur=="Blanc") nbPionNoirDetruit+=nbDetruit;
							else nbPionBlancDetruit+=nbDetruit;
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
							if (!rencontreAllie)
							{
								listeGroupe.add(new GroupePion(grille[x][y],this));
								rencontreAllie = true;
							}
							nbDetruit=grille[x][y+1].getGP().destruction(this);
							if(nbDetruit==1) residu=grille[x][y+1];
							upDateResidu = true;
							if(couleurJoueur=="Blanc") nbPionNoirDetruit+=nbDetruit;
							else nbPionBlancDetruit+=nbDetruit;
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
							if (!rencontreAllie)
							{
								listeGroupe.add(new GroupePion(grille[x][y],this));
								rencontreAllie = true;
							}
							nbDetruit=grille[x-1][y].getGP().destruction(this);
							if(nbDetruit==1) residu=grille[x-1][y];
							upDateResidu = true;
							if(couleurJoueur=="Blanc") nbPionNoirDetruit+=nbDetruit;
							else nbPionBlancDetruit+=nbDetruit;
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
							if (!rencontreAllie)
							{
								listeGroupe.add(new GroupePion(grille[x][y],this));
								rencontreAllie = true;
							}
							nbDetruit=grille[x][y-1].getGP().destruction(this);
							if(nbDetruit==1) residu=grille[x][y-1];
							upDateResidu = true;
							if(couleurJoueur=="Blanc") nbPionNoirDetruit+=nbDetruit;
							else nbPionBlancDetruit+=nbDetruit;
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
			Main.waitingForInput = false;
			if(joueurpasse)
			{
				partieFinie = true;
				decomptePoints();
			}
			
			joueurpasse=true;
			
			if(couleurJoueur=="Blanc") nbPionBlancDetruit++;
			else nbPionNoirDetruit++;
			
			if(couleurJoueur=="Blanc") couleurJoueur="Noir"; else couleurJoueur="Blanc" ;
		}else{
			System.out.println("il faut passer le mot passer en argument");
		}
	}
	
	public boolean jouerUnTour(int x,int y)	//Indique si le tour à bien été joué
	{
		if(placerPion(x,y))
		{
			Main.waitingForInput = false;		//indique au programme principal qu'il n'a plus à attendre d'informations
			if(couleurJoueur=="Blanc") couleurJoueur="Noir"; else couleurJoueur="Blanc" ;	//la couleur du joueur change pour le prochain tour
			joueurpasse=false;
			return true;
		}else{
			return false;
		}
	}
	
	 
	public int decomptePoints()
	{
		//mise en place des territoires
		for (int i = 0 ; i<dim ; i++)
			for (int j = 0 ; j<dim ; j++)
			{
				if (grille[i][j]==null) grille[i][j] = new Pion(i,j,"Territoire");
				
				boolean rencontreTerritoire = false;
				String couleur = "";
				
				if(caseOccupee(i+1,j))
				{
					if(grille[i+1][j].getCouleur()=="Territoire")
					{
						grille[i+1][j].getGP().ajouterPion(grille[i][j], this);
						rencontreTerritoire = true;
					}
					else couleur = grille[i+1][j].getCouleur();
					
				}
				if(caseOccupee(i,j+1))
				{
					if(grille[i][j+1].getCouleur()=="Territoire")
					{
						if(!rencontreTerritoire) 
						{
							grille[i][j+1].getGP().ajouterPion(grille[i][j], this);
							rencontreTerritoire = true;
						}
						else if(grille[i][j+1].getGP()!=grille[i][j].getGP()) grille[i][j+1].getGP().fusion(grille[i][j].getGP());
					}
					else
					{
						if(couleur!=grille[i][j+1].getCouleur() && couleur!="") 
						{
							couleur = "Neutre";
						} 
						else couleur = grille[i][j+1].getCouleur();
					}
				}
				if(caseOccupee(i-1,j))
				{
					if(grille[i-1][j].getCouleur()=="Territoire")
					{
						if(!rencontreTerritoire)
						{
							grille[i-1][j].getGP().ajouterPion(grille[i][j], this);
							rencontreTerritoire = true;
						}
						else if(grille[i-1][j].getGP()!=grille[i][j].getGP()) grille[i-1][j].getGP().fusion(grille[i][j].getGP());
					}
					else
					{
						if(couleur!=grille[i-1][j].getCouleur() && couleur!="") 
						{
							couleur = "Neutre";
						} 
						else couleur = grille[i-1][j].getCouleur();
					}
				}
				if(caseOccupee(i,j-1))
				{
					if(grille[i][j-1].getCouleur()=="Territoire")
					{
						if(!rencontreTerritoire) 
						{
							grille[i][j-1].getGP().ajouterPion(grille[i][j], this);
							rencontreTerritoire = true;
						}
						else if(grille[i][j-1].getGP()!=grille[i][j].getGP()) grille[i][j-1].getGP().fusion(grille[i][j].getGP());
					}
					else
					{
						if(couleur!=grille[i][j-1].getCouleur() && couleur!="") 
						{
							couleur = "Neutre";
						} 
						else couleur = grille[i][j-1].getCouleur();
					}
				}
				
				if(!rencontreTerritoire) grille[i][j].setGP(new Territoire(grille[i][j],this,couleur));
			}
		//calcul du nombre de pion sur le plateau
		int nbpoint = 0;
		for (int i = 0 ; i<dim ; i++)
			for (int j = 0 ; j<dim ; j++)
			{
				if (grille[i][j].getCouleur()=="Territoire")
				{
					Territoire t = (Territoire)grille[i][j].getGP();
					if(t.getCouleur()=="Noir") nbpoint++;
					else if (t.getCouleur()=="Blanc") nbpoint--;
				}
			}
		
		return nbpoint;
	}
	
	
	public void suppressionPionMort(ArrayList<Pion> listePionMort)		//TODO : envoyer message à la fenetre pour mettre à jour le bouton correspondant au pion mort
	{
		for(Pion p : listePionMort)
		{
			if(p.getCouleur()=="Blanc")nbPionBlancDetruit++;
			else nbPionNoirDetruit++;
			grille[p.getX()][p.getY()]=null;
		}
	}
	
	public String SauverPartie() {						//On sauvegarde la partie en cours
		ObjectOutputStream oos = null;
		int saveCount = 0;									//saveCount indique à quelle sauvegarde on est rendu
		try {											//Tout ce try sert à récupérer la valeur de la dernière sauvegarde et à l'augmenter. Ainsi on incrémente toujours la valeur même en re-exécutant le programme
		InputStream fCount = new FileInputStream("sauvegardes/count.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader((fCount)));
		saveCount = Integer.parseInt(br.readLine());
		OutputStream fCount2 = new FileOutputStream("sauvegardes/count.txt");
		PrintWriter pw = new PrintWriter(fCount2);
		pw.println(++saveCount);							//On réécrit le fichier avec son ancienne valeur augmentée de 1
		pw.close();
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {				//On va créer un fichier de sérialisation

			FileOutputStream fos = new FileOutputStream("sauvegardes/sauvegarde" + saveCount);			//on créer le fichier
			oos = new ObjectOutputStream(fos);
			oos.writeObject(this);								//on écrit dedans la sérialisation
			oos.flush();													// on "valide" l'écriture
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(oos != null){
				try {
					oos.flush();
					oos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return "sauvegarde" + saveCount;		//On retourne le nom de fichier;
	}
	
	public static Grille ChargerPartie(String savePath) {
		Grille charge = null;
		try {
			URI adr = new URI(savePath);
			JFileChooser choix = new JFileChooser();
			int retour = choix.showOpenDialog(null);
			if(retour == JFileChooser.APPROVE_OPTION){
				String c = choix.getSelectedFile().getAbsolutePath();
				ObjectInputStream ois = null;
				try {
					FileInputStream fichier = new FileInputStream(c);
					ois = new ObjectInputStream(fichier);
					charge = (Grille)ois.readObject();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return charge;
	}
	
	//Getteurs Setteurs
	public boolean getPartieFinie() {return partieFinie;}
	
	public int getDim() {return dim;}
	
	public Pion[][] getGrille() {return grille;}
	
	public Pion getResidu() {return residu;}
	
	public ArrayList <GroupePion> getListeGroupe() {return listeGroupe;}
	
	public String CouleurJoueur(){return couleurJoueur;}
}
