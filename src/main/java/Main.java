public class Main {

	public static boolean waitingForInput = true;
	public static void main(String[] args) 
	{
		Grille g = new Grille(19);
		
		System.out.println("HOOOOOO");
		
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
		

		Fenetre fen = new Fenetre(g);
		fen.pannel();
		 
		System.out.println("La partie en cours : " + g.SauverPartie());
		
		//afficherEtatConsole(g);
		Grille.ChargerPartie("/sauvegardes");
		
		while(!g.getPartieFinie())
		{
			System.out.println("C'est au tour du joueur " + g.CouleurJoueur() + " de jouer");
			waitingForInput = true;
			while(waitingForInput){
				
			}
		}
	}
	
	public static void afficherEtatConsole(Grille g){
		for(int i = 0; i<g.getDim();i++){
			for(int j = 0; j<g.getDim(); j++){
				if(g.getGrille()[i][j]==null){
					System.out.print("+");
				}else if(g.getGrille()[i][j].getCouleur()=="Blanc"){
					System.out.print("b");
				}else if(g.getGrille()[i][j].getCouleur()=="Noir"){
					System.out.print("n");
				}
			}
			System.out.print("\n");
		}
	}
}
