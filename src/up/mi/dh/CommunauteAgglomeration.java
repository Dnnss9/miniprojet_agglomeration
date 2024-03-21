package up.mi.dh;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CommunauteAgglomeration {
	private List <Ville> villes;
	private List <Route> routes;
	private ArrayList <ArrayList<Ville>> listeAdjacence;
	
	public CommunauteAgglomeration(List <Ville> villes,List <Route> routes) {
		this.villes = villes;
		this.routes = routes;
		listeAdjacence = new ArrayList <ArrayList<Ville>> (creerListeAdjacence(routes));
		
	}
	
	public List<Ville> getVilles() {
		return villes;
	}
	
	public List<Route> getRoutes() {
		return routes;
	}

	 
	//Methode pour creer la liste d'adjacence du graphe que represente les villes reliées entre elles
	
	public ArrayList<ArrayList <Ville>> creerListeAdjacence(List <Route> routes){
		ArrayList<ArrayList<Ville>> listeAdjacence = new ArrayList <ArrayList <Ville>> (); 	//initialisation d'une liste vide
		
		for (int i = 0; i<villes.size(); i++) { 
			ArrayList <Ville> voisins = new ArrayList<Ville>();      //Pour chaque ville une liste pour stocker ses voisins est créé
			for(Route r : routes) {                                  //On vérifie chaque route pour trouver les villes voisines de la ville
																	//à la position i dans villes[]
				if((r.getVille1().getNom()).equals(villes.get(i).getNom())) 
					voisins.add(r.getVille2());
				 
				 if((r.getVille2().getNom()).equals(villes.get(i).getNom())) 
					 voisins.add(r.getVille1());
			 }
			 listeAdjacence.add(voisins); //On ajoute la liste de voisins avant de passer à la position d'après, donc la ville d'après
			 
			 //Affichage de liste d'adjacence
			//System.out.println("******");
			 //for(int j=0; j<voisins.size();j++) {
				// System.out.println(voisins.get(j));
			 //}
		 }
		 return listeAdjacence;
	 }
	
	//Methode pour trouver la position d'une ville dans la liste villes[]
	 public static int chercherPositionVille(String nom,List <Ville> listeVilles) {
			boolean trouver=false;
			int posVilleChercher = -1 ;
			while(!trouver) {   
				for(int i =0 ; i<listeVilles.size(); i++) {
					if (listeVilles.get(i).getNom().equals(nom)) //On vérifie les noms pour trouver le nom de la ville que l'on cherche
						posVilleChercher = i;
						trouver = true;							//Une fois trouvée on récupére l'index i et quitte la boucle
				}
			}
			return posVilleChercher;
		}
	
	//Methode "recharger" une ville
	//Elle verifie si la ville a déjà une borne sinon elle met à true la variable
	
	 public void recharger(String nom) {
		int pos = chercherPositionVille(nom,villes);
		Ville ville = villes.get(pos);
		if(ville.getBorneDeRecharge())   //Si la ville possède déjà une recharge on retourne ce message
			System.out.println("La ville possède déjà une borne de recharge");
		
		else {
			ville.setBorneDeRecharge(true);//sinon on met les variables bornederecharge et accessibilite à true et on modifie les informations de la ville dans la liste aussi
			ville.setAccessibilite(true);
			villes.set(pos, ville);
			for(Ville v : getVoisins(ville)) { //on met à jour les accessibilités des villes voisines après avoir recharge une ville
					v.setAccessibilite(true);
				}
			System.out.println("La ville " + nom + " a été rechargé ");
		}
		
	}

	//Méthode pour vérifier si deux villes sont voisines à partir de leur nom
	 //on utilise la liste d'adjacence 
	public boolean estVoisin(String ville1, String ville2) {
		boolean estVoisin = false;
		int pos = chercherPositionVille(ville1, villes);
		List <Ville> voisins = new ArrayList <Ville> ();
		voisins = listeAdjacence.get(pos);
		for(int i = 0;i<voisins.size() && !estVoisin ;i++) {
			if(ville2.equals(voisins.get(i).getNom()))
				estVoisin = true;
		}
		return estVoisin;
	}
	
	//Méthode qui renvoie la liste des villes voisines d'une ville donnée
	public ArrayList<Ville> getVoisins(Ville ville){
		ArrayList<Ville> voisins = new ArrayList <Ville> ();
		for(Ville v : villes) {
			if(estVoisin(ville.getNom(), v.getNom())) {
				voisins.add(v);
			}
		}
		return voisins;
	}	
	
	//Méthode pour vérifier si une ville est accessible
	public boolean estAccessible(Ville ville) {
		boolean access = false;
		if(ville.getBorneDeRecharge()) { // si la ville à une borne de recharge alors est accessible
			access = true ;
		}
		else {
			List <Ville> voisins = getVoisins(ville); //sinon récupère les voisins de la ville
			for (int i = 0; i<voisins.size() && !access;i++) {
				if(voisins.get(i).getBorneDeRecharge()) { // pour chaque voisins on verifie si ils ont une borne de recharge 
					access = true;     // si oui la ville est accessible (il suffit d'un voisin avec une borne de recharge)
				}
			}
		}		
		return access;
	}
	
	//Méthode pour supprimer une borne de recharge 
		public void supprimerRecharge(String nom) {
			
			boolean p = true;
			int pos = chercherPositionVille(nom, villes), tmp = 0;
			
			List <Ville> voisinsPasCharges = new ArrayList <Ville>();
			List <Ville> voisins = new ArrayList <Ville>();
			
			voisins = getVoisins(villes.get(pos));
			for(Ville v : voisins) { // pour chaque voisins de la ville que l'on souhaite décharger 
				if(!v.getBorneDeRecharge()) {  // on vérifie si ils n'ont pas de borne de recharge
					voisinsPasCharges.add(v); // et on ajoute les voisins ne possédant pas de borne de recharge (susceptible de dépendre de la ville) dans une liste
				}
			}
			
			if (voisinsPasCharges.size()==voisins.size()) // si le nombre de voisin pas chargés est égale au nombre de voisins chargés
				p = false;                           // la borne de recharge ne peut pas être supprimé car la ville ne sera plus accessible 
			
			else if (voisinsPasCharges.size()==0) // si tous les voisins sont chargés 
				p = true;                    // on peut supprimer la borne de recharge de la ville 
				
			else {
				for(int i = 0; i<voisinsPasCharges.size();i++) {      // sinon pour chaque voisins pas chargés  
					voisins = getVoisins(voisinsPasCharges.get(i)); 
					
					for(Ville v : getVoisins(voisinsPasCharges.get(i))) { //on parcourt ses voisins
						if(!v.getNom().equals(nom)) { // en excluant le voisin dont on souhaite supprimer la borne de recharge
							tmp++;
							
							if(!v.getBorneDeRecharge())
								p=p&&v.getBorneDeRecharge();
						}
					}
				}
				
				if(tmp!=voisinsPasCharges.size()) {
					p = false;
					System.out.println("Une ville a besoin de cette borne de recharge");
				}
			}
			if(p) {
				villes.get(pos).setBorneDeRecharge(false);
				System.out.println("La borne de recharge de la ville "+ nom + " a bien été supprimé.");
			}
			else
				System.out.println("La borne de recharge de cette ville ne peut pas être supprimée");
		}
	
	
	//Methode qui affiche l'agglomeration : les villes, si elles sont accessibles ou non, si elles ont une borne de recharge ou non et ses voisins
	public void afficherAgglomeration() {
		for(Ville v : villes) {
			System.out.println("\n");
			System.out.println(v.toString());
			
			//Affichage de liste d'adjacence
			System.out.println("Voisins : ");
			for(int j=0; j<getVoisins(v).size();j++) {
				System.out.println(getVoisins(v).get(j).getNom());
			}
		}
	}
	
	// Methode qui affiche le nom des villes possèdant une borne de recharge
	public void afficherVillesCharges() {
		System.out.println("Villes possédant une borne de recharge :");
		for(Ville v : villes) {
			if (v.getBorneDeRecharge())
				System.out.println(v.getNom());
		}
	}
	
	// Méthode qui donne le nombre de borne de recharge d'une agglomeration
	public int score() {
		int score = 0;
		for(int i=0;i<villes.size();i++) {
			if(villes.get(i).getBorneDeRecharge())
				score ++;
		}
		return score;
	}
	
	//Methode de recharge auto (algorithme moins naif)
	public CommunauteAgglomeration rechargeAuto(int iteration) {
		int i = 0;
		int scoreCourant = score() ;
		List<Ville> villes = getVilles();
		Random random = new Random();
		Ville ville;
		while (i<iteration) {
			ville = villes.get(random.nextInt(villes.size()));
			
			if(ville.getBorneDeRecharge()) 
				supprimerRecharge(ville.getNom());
			else 
				recharger(ville.getNom());
			
			if(score()<scoreCourant) {
				i = 0;
				scoreCourant = score();
			}
			else
				i++;
		}
		afficherVillesCharges();
		return this;
	}
	
	//Methode de sauvagerde d'agglomeration dans un fichier
	public void sauvegarder(String cheminFichier) {

		String ligne;
		List <String> villesRecharges = new ArrayList <String>(); // liste pour stocker les villes chargés et ne pas parcourir deux fois la liste des villes

		 try { 

			 File file = new File(cheminFichier); // creation d'un fichier 
	
			 if (file.createNewFile()){
				 PrintWriter pw = new PrintWriter(file);
				 
				 for(Ville v : villes ) {  
					 ligne = "ville(" + v.getNom();
					 ligne += ").\n";
					 pw.write(ligne);
					 
					 if (v.getBorneDeRecharge()) 
						 villesRecharges.add(v.getNom()); // si la ville est chargée on la stock dasn une liste pour la retrouver plus rapidement
					 
				 }
		
				 for(Route r : routes ) {
					 ligne = "route(" + r.getVille1().getNom();
					 ligne += ","+r.getVille2().getNom();
					 ligne += ").\n";
					 pw.write(ligne);
				 }
		
				 for(String nom : villesRecharges ) {
					 ligne = "recharge(" + nom;
					 ligne += ").\n";
					 pw.write(ligne);
				 }
				 pw.close();
				 System.out.println("La communauté agglomeration a été sauvegardée");
			 }
			 else
			 System.out.println("Un fichier de ce nom existe déjà.");
		 } 
		 
		 catch (IOException e) {
			 System.out.println("La sauvegarde n'a pas pu être effectuée car le chemin n'a pas été trouvé.");
		 }
	}	
}
	
	