package up.mi.dh;

import java.util.List;

public class Route {
	private Ville ville1, ville2;
	
	public Route(Ville A,Ville B) {
		this.ville1=A;
		this.ville2=B;
	}
	
	public String toString() {
		return "Il y a une route qui relie "+ ville1.getNom() + " et "+ ville2.getNom();
	}
	
	public Ville getVille1() {
		return ville1;
	}
	
	public Ville getVille2() {
		return ville2;
	}
	
	
	// Methode qui vérifie si une route exite déjà 
	public static boolean existe(Ville ville1, Ville ville2, List <Route> routes) {
		boolean existe = false;
		
		for(Route r : routes) { // on parcourt la liste des routes déjà existante
			
			if(r.ville1.getNom().equals(ville1.getNom())){   // on vérifie si la route exact existe déjà (ex: on entre la route AB mais AB existe déjà)
				if(r.ville2.getNom().equals(ville2.getNom())) {
					existe = true;
				}
			}
			
			if(r.ville1.getNom().equals(ville2.getNom())){ // on vérifie si la route en sens inverse existe déjà (ex: on entre la route BA mais AB existe)
				if(r.ville2.getNom().equals(ville1.getNom())) {
					existe = true;
				}
			}
		}
		return existe;
	}
}
