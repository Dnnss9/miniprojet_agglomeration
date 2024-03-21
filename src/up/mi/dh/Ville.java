package up.mi.dh;

import java.util.List;

public class Ville {
	private String nom;
	private boolean accessible,borneDeRecharge;
	
	public Ville(String nom) {
		this.nom = nom;
		accessible = true;
		borneDeRecharge=true;
	}
	
	public String getNom() {
		return this.nom;
	}
	
	public boolean getAccessibilite() {
		return this.accessible;
	}
	
	public boolean getBorneDeRecharge() {
		return this.borneDeRecharge;
	}
	
	public void setNom(String nom) {
		this.nom=nom;
	}
	
	public void setAccessibilite(boolean accessible) {
		this.accessible=accessible;
	}
	
	public void setBorneDeRecharge(boolean borneDeRecharge) {
		this.borneDeRecharge = borneDeRecharge;
	}
	
	//Methode pour afficher Oui ou Non à la place de true et false
	private String accessible() {
		String acces = "Oui";
		if(!this.accessible)
			acces="Non";
		return acces;
	}
	
	private String aBorneDeRecharge() {
		String aBorne = "Oui";
		if (!this.borneDeRecharge)
			aBorne="Non";
		return aBorne;
	}
	
	//Methode toString pour afficher les informations des villes
	public String toString() {
		return "Ville : " + nom +
				", Accessible : " + this.accessible() +
				", Borne de Recharge : " + this.aBorneDeRecharge();
	}
	
	//Methode qui vérifie si une villes existe déjà
	public static boolean existe(String nom, List <Ville> villes) {
		boolean existe = false;
		for(Ville v : villes) {            // on parcourt la lliste des villes déjà existantes
			if (nom.equals(v.getNom())) { // si les noms sont égaux signifie que la ville existe déjà 
				existe=true;              
			}
		}
		return existe;
	}
}
