
public class TerritoireAbsolu {
	
	private Pion[][] territoire;
	private String couleur;
	private int taille;
	private int dim;
	
	public Pion[][] GetTerritoire(){
		return territoire;
	}
	
	public TerritoireAbsolu(int x, int y,Grille g){
		boolean enRecherche = true;
		taille = 0;
		dim = g.getDim();
		for(int i = 0;i<dim;i++){
			for(int j = 0; j<dim; j++){
				territoire[i][j] = null;
			}
		}
		while(enRecherche){
			
		}
	}
	
	public boolean cherchePion(int x, int y, Grille g){
		territoire[x][y] = new Pion(x,y,couleur);
		taille++;
		if((!g.caseOccupee(x, y) || g.getGrille()[x+1][y].getCouleur() != couleur) && territoire[x+1][y] == null){
			cherchePion(x+1,y,g);
		}
		if((!g.caseOccupee(x, y) || g.getGrille()[x-1][y].getCouleur() != couleur) && territoire[x-1][y] == null){
			cherchePion(x-1,y,g);
		}
		if((!g.caseOccupee(x, y) || g.getGrille()[x][y+1].getCouleur() != couleur) && territoire[x][y+1] == null){
			cherchePion(x,y+1,g);
		}
		if((!g.caseOccupee(x, y) || g.getGrille()[x][y-1].getCouleur() != couleur) && territoire[x][y-1] == null){
			cherchePion(x,y-1,g);
		}
		return false;
		
	}
	
	public void compare(TerritoireAbsolu other){
		int include = 0;
		int included = 0;
		for(int i = 0;i<dim;i++){
			for(int j = 0; j< dim ; j++){
				if(territoire[i][j] != null && other.GetTerritoire()[i][j] == null)
					include++;
				if(territoire[i][j] != null && other.GetTerritoire()[i][j] == null)
					included++;
			}
		}
	}
}
