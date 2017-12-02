package projetGO;

public class Main {

	public static void main(String[] args) 
	{
		Grille g = new Grille(13);
		
		g.jouerUnTour(3, 3);
		System.out.println(g.getGrille()[3][3].getCouleur());
		System.out.println(g.getGrille()[3][3].getGP().getNbLiberte());
		
		g.jouerUnTour(3, 4);
		System.out.println(g.getGrille()[3][4].getCouleur());
		System.out.println(g.getGrille()[3][3].getGP().getNbLiberte());
		
		g.jouerUnTour("passer");
		System.out.println("passer");
		
		g.jouerUnTour(3, 2);
		System.out.println(g.getGrille()[3][2].getCouleur());
		System.out.println(g.getGrille()[3][3].getGP().getNbLiberte());
		
		g.jouerUnTour("passer");
		System.out.println("passer");
		
		g.jouerUnTour(2, 3);
		System.out.println(g.getGrille()[2][3].getCouleur());
		System.out.println(g.getGrille()[3][3].getGP().getNbLiberte());
		
		g.jouerUnTour("passer");
		System.out.println("passer");
		
		g.jouerUnTour(4, 3);
		System.out.println(g.getGrille()[4][3].getCouleur());
		
		System.out.println(g.caseOccupee(3, 3));
		
		System.out.println(g.getListeGroupe());
		
		
		
		
		/*while(!g.getPartieFinie())
		Fenetre fen = new Fenetre();
		fen.pannel();
		
		while(!g.getPartieFinie())
		{
			//TODO d�roulement d un tour de jeu
			
			
		}*/
	}

}
