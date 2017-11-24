package projetGO;

public class Main {

	public static void main(String[] args) 
	{
		Grille g = new Grille(13);
		
		while(!g.getPartieFinie())
		{
			//TODO déroulement d un tour de jeu
			try{
				Grille piti = new Grille(19);
				piti.placerPion(1, 1);
			}catch(CoupException e){
				System.out.println("blem");
			}catch(Exception e){
				System.out.println("big blem");
				
			}
			
		}
	}

}
