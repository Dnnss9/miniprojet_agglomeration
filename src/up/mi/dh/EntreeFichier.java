package up.mi.dh;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.StringTokenizer;


public class EntreeFichier {
	public static CommunauteAgglomeration creeAgglomerationFichier(Scanner sc,String cheminFichier/*=args[0] ds le main*/) throws IOException {
		CommunauteAgglomeration agglo;
		String ligne,type,nomVille;
		Ville ville1,ville2;
		boolean accessible = true ,ligneEstRoute = false, sortieBoucle=false;
		int j=0;
		
		List<Ville> villes = new ArrayList <Ville>();
		List<Route> routes = new ArrayList <Route>();
		try {
			FileReader file = new FileReader(cheminFichier);
			BufferedReader reader =new BufferedReader(file);
			ligne=reader.readLine();
			if(ligne==null) {
				System.out.println("Erreur Fichier :");
				System.out.println("Votre fichier est vide");
				reader.close();
				return null;
			}
			while(ligne!=null && !sortieBoucle) {
				
				StringTokenizer var= new StringTokenizer(ligne,"("); //met sous forme de StringTokenizer la ligne du fichier
				type=var.nextToken(); // recupère le premier mot d'une ligne( ex : ville, route, recharge)
				StringTokenizer nom = new StringTokenizer(var.nextToken(")"),",");
				
				if(type.equals("ville")) { //verifie si la ligne correspond à une ville
					String nom2=(nom.nextToken()).substring(1);
					if(ligneEstRoute) {//le boolean ligneEstRoute a pour valeur true si une route a deja été initialiser 
						System.out.println("Erreur fichier : "); //Il y a une erreur dans le fichier on a essayer d'initialiser une ville après une route ce qui est impossible
						System.out.println("Vous avez inscrit une route dans votre fichier avant une ville");
						reader.close();
						return null;  //On retourne la valeur null car il y a une erreur dans le fichier
						
					}
					else {
						
						if(!Ville.existe(nom2,villes)) { // On verrifie si il n'existe pas deja une ville du même nom qui a été initialiser
							Ville v = new Ville(nom2);
							villes.add(v); //On ajoutte la ville a la liste des villes
							System.out.println("La ville "+ nom2+ " a bien été ajouté");
						}
						
						else 
							System.out.println("la ville "+ nom2 + " a été inscite 2 fois dans le fichier");
						
					}
					
				}
				else {
					
					if(type.equals("route")) {//Vérifie si la ligne correspond à une route
						try {
							nomVille = (nom.nextToken()).substring(1); //On recupère le nom de la première ville
							if(Ville.existe(nomVille, villes)) { //Vérifie si la ville existe dans la liste des villes
								ville1=new Ville(nomVille);
								nomVille = nom.nextToken();
								if(Ville.existe(nomVille, villes)) {
									ville2=new Ville(nomVille);
									if(!ville1.getNom().equals(ville2.getNom())) {//Vérifie si la ville existe dans la liste des villes
										if(!Route.existe(ville1, ville2, routes)) {
											routes.add(new Route(ville1,ville2)); //Si les deux villes sont différente et  appartiennent à la liste des villes crée une route entre les 2
											System.out.println("La route "+ ville1.getNom() +" "+ ville2.getNom()+ " a bien été ajouté");
										}	
										else
											System.out.println("Une route a été inscrite 2 fois dans le fichier");
									}
									else 
										System.out.println("Vous avez entré deux fois la même ville pour une route");
									
								}
								else
									System.out.println("Vous avez entré une ville qui n'existe pas");
							}
							else
								System.out.println("Vous avez entré une ville qui n'existe pas");
							
						}
						catch (NoSuchElementException e) {
							System.out.println("Erreur fichier : ");
							System.out.println("Une route n'a pas été initialisée correctement dans le fichier.");
							reader.close();
							return null;
						}
					ligneEstRoute=true;
					}
					else {
						
						if(type.equals("recharge"))//Vérifie si la ligne correspond à une borne de recharge
							sortieBoucle=true;	
						else { //Cas où la ligne ne correspond ni a une ville, ni a une route, ni a une recharge
							System.out.println("Une ligne du fichier ne correspond ni a une ville, ni a une route, ni a une zone de recharge");
							reader.close();
							return null;
						}
					}
				}
				if(!sortieBoucle)//On ne lis la ligne suivante que si on ne sort pas de la boucle
					ligne=reader.readLine();
			}
			agglo= new CommunauteAgglomeration(villes,routes);
			
			
			List<String> villesRecharge = new ArrayList <String>();
			
			//Crée une liste contenant toutes les villes qui devront posséder une borne de recharge
			while(ligne != null) {
				StringTokenizer var = new StringTokenizer(ligne,"(");
				type = var.nextToken();
				
				if(type.equals("recharge")) { //Vérifie si la ligne correspond à une recharge
					nomVille=var.nextToken(")").substring(1);
					if(Ville.existe(nomVille, villes))//Vérifie si la ville appartient à la liste des villes
						villesRecharge.add(nomVille);
					else {
						System.out.println("Erreur Fichier :");
						System.out.println("Vous avez essayer de recharger une ville qui n'existe pas.");
						reader.close();
						return null;
					}
				}
				else { //Cas ou la ligne ne correspond pas a une borne de recharge alors on a une erreur fichier
					System.out.println("Erreur fichier : ");
					System.out.print("La ligne du fichier ne correspond pas a une recharge");
					reader.close();
					return null;
				}
				ligne=reader.readLine();
			}
			
			for(String s : villesRecharge) {
				System.out.println("La ville "+ s + " est rechargée.");
			}
			
			//On parcours la liste de villes et on decharge toute les villes n'appartenant pas à la liste des villes qui ont une recharge
			while(j<agglo.getVilles().size() && accessible) {
				if(!(villesRecharge.contains(agglo.getVilles().get(j).getNom()))) { //
					agglo.supprimerRecharge(agglo.getVilles().get(j).getNom());
					if(agglo.getVilles().get(j).getBorneDeRecharge()) {
						System.out.println("Les zones de recharges entrées ne satisfaisant pas la contraite d'accessibilité");
						System.out.println("L'agglomeration va donc être initialiser de façon à ce que toute les villes ai des zones de recharge \n");
						for(Ville v : villes) {
							v.setBorneDeRecharge(true);
						}
						agglo=new CommunauteAgglomeration(villes,routes);
						accessible=false;
						
					}
				}
				j++;
			}
			
			reader.close();

		} catch (FileNotFoundException e) {
			agglo=null;
			System.out.println("Le fichier n'as pas été trouver");
		}
		
		return agglo;
	}
}
	

