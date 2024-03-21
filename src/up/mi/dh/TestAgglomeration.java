package up.mi.dh;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class TestAgglomeration {
	
	
	//Menu lancé quand l'utilisateur souhaite recharger un ville manuellement
	private static CommunauteAgglomeration menuAjouterZoneRecharge(Scanner sc, CommunauteAgglomeration agglomeration) {
		String nom;
		boolean egal;
		
		System.out.println("Entrez la ville que vous souhaitez recharger");
		nom = sc.next();
		egal = Ville.existe(nom, agglomeration.getVilles());
		if(egal)
			agglomeration.recharger(nom);
		else 
			System.out.println("La ville "+ nom +" n'existe pas");
		return agglomeration;
	}
	
	//Menu lancé quand l'utilisateur souhiate supprimer une zone de recharge mannuellement
	private static CommunauteAgglomeration menuSupprimerZoneRecharge(Scanner sc, CommunauteAgglomeration agglomeration) {
		String nom;
		boolean egal;
		System.out.println("Entrez la ville où vous souhaitez retirer la borne de recharge : ");
		nom = sc.next();
		egal = Ville.existe(nom, agglomeration.getVilles());
		if(egal)
			agglomeration.supprimerRecharge(nom);
		else 
			System.out.println("La ville "+ nom +" n'existe pas");
		
		return agglomeration;
	}
	
	/*Menu "principal" avec la possibilité de recharge manuellement ou automatique une agglo, de l'afficher,
	 * de la sauvegarder dans un fichier et mettre fin au progamme
	 */
	private static CommunauteAgglomeration menu(Scanner sc, CommunauteAgglomeration agglomeration) {
		boolean fin = false;
		String choix, cheminFichier;
		int ite = agglomeration.getRoutes().size();
		
		while(!fin) {
			System.out.println("\n1) Recharger manuellement \n2) Recharger automatiquement \n3) Afficher l'agglomération "
					+ "\n4) Sauvegarder  \n5) Fin");
			choix=sc.next();
			
			switch(choix) {
			case "1" :
				
				agglomeration = menuRechargeManuelle(sc,agglomeration);
				break;
			
			case "2" :
				System.out.println(ite);
				agglomeration = agglomeration.rechargeAuto(ite);
				System.out.println("La communauté d'agglomeration a été rechargée automatiquement.");
				break;
			
			case "3" :
				agglomeration.afficherAgglomeration();
				break;
				
			case "4" :
				System.out.println("Entrez le nom du fichier ou le chemin où vous souhaitez le créer :");
				cheminFichier = sc.next();
				agglomeration.sauvegarder(cheminFichier);
				break;
				
			case "5" :
				fin = true;
				break;
				
			default : 
				System.out.println("Votre entrée n'a pas été comprise.");
				break;
		}
	}
		return agglomeration;
	}
	
	
	/* Menu qui s'affiche quand l'utilisateur souhaite recharger son agglomeration manuellement 
	 * il peut ajouter, supprimer des recharges et affichier l'agglomeration
	 */
	private static CommunauteAgglomeration menuRechargeManuelle(Scanner sc, CommunauteAgglomeration agglomeration) {
		boolean fin = false;
		String choix;
		
			while(!fin) {
				System.out.println("\n1) Ajouter une zone de recharge \n2) Supprimer une zone de recharge \n3) Afficher l'agglomeration "
						+ "\n4) Fin");
				choix=sc.next();
				
				switch(choix) {
				case "1" :
					agglomeration = menuAjouterZoneRecharge(sc,agglomeration);
					agglomeration.afficherVillesCharges();
					break;
				
				case "2" :
					agglomeration = menuSupprimerZoneRecharge(sc,agglomeration);
					agglomeration.afficherVillesCharges();
					break;
				
				case "3" :
					agglomeration.afficherAgglomeration();
					break;
					
				case "4" :
					fin = true;
					break;
					
				default : 
					System.out.println("Votre entrée n'a pas été comprise.");
					break;
			}
		}
			return agglomeration;
	}
	
	
	public static void main(String[] args) throws IOException {
		Scanner sc= new Scanner (System.in);
		String cheminFichier=null;
		
		try {
			//Pour ne pas entrer le chemin du fichier en argument
			//System.out.println("Chemin fichier :");
			//String cheminFichier = sc.next(); 
			cheminFichier=args[0];
		}
		catch(ArrayIndexOutOfBoundsException e) {
			System.out.println("Vous n'avez pas entré d'argument");
		}
		if(cheminFichier!=null) {
			try {
				CommunauteAgglomeration agglomeration = EntreeFichier.creeAgglomerationFichier(sc, cheminFichier);
				
				if (agglomeration == null)
					System.out.println("L'agglomeration n'a pas pu être crée, veuillez revoir votre fichier.");
				
				else {
					agglomeration = menu(sc,agglomeration);
					agglomeration.afficherAgglomeration();
				}
				
			}
			catch (FileNotFoundException e) {
				System.out.println("Le fichier n'a pas été trouvé.");
			}
			
			
			sc.close();
		}
	}
}
