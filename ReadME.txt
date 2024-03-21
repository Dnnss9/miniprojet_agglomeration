Vous entrez le chemin où se trouve le fichier de l'agglomeration à lire en argument 
et lancez le programme. Le fichier TestAgglomeration.java qui contient la fonction main s'executera.

Lors de la lecture du fichier avec la fonction creerAgglomeration dans EntreeFichier,
des erreurs seront retournées et le programme s'arretera en vous demandant de reessayer après avoir modifier le fichier, 
dans les cas suivants :
 
- si le fichier n'est pas trouvé,
- si une route est initialisée avant une ville,
- si une zone de recharge est initialisée avant une ville ou une route,
- si une ville qui n'existe pas est entrée lors de la creation de route ou de borne de recharge,
- si une route est mal initialisée (ex : route(A).),
- si il y a une faute d'orthographe (ex : rute(A,B)., rcharge(D).),

Si l'agglomeration dans le fichier ne respecte pas les contraintes d'accesibilités, alors toutes les villes possèderont une borne de recharge.

On arrive sur le menu "principale" : 

1) Recharger manuellement 
2) Recharger automatiquement 
3) Afficher l'agglomération 
4) Sauvegarder  
5) Fin

Pour recharger et decharger manuellement l'agglomeration, on tape 1 et ce menu s'affiche : 

1) Ajouter une zone de recharge 
2) Supprimer une zone de recharge 
3) Afficher l'agglomeration 
4) Fin

Ici pour ajouter une zone de recharge ou en supprimer une on demandera d'entrer la ville où vous souhaitez le faire.
Pour recharger une ville, si cette ville l'est déjà un message vous avertira
sinon elle sera rechargée et un message vous avertira aussi. Pour decharger une ville, si les contraintes 
d'accesibilités de ne seront plus respectées alors la ville ne sera pas dechargée et un message vous avertira également.

En tapant 4, pour Fin on retournera sur le menu "principale" : 

1) Recharger manuellement 
2) Recharger automatiquement 
3) Afficher l'agglomération 
4) Sauvegarder  
5) Fin

Pour recharger automatiquement, après avoir entré 2 l'algorithme moins naïf se lancera alors et retournera l'agglomeration chargée.
La fonctionnalitée 3, affichera donc l'agglomeration sur le terminal dans ce format : 

Ville : D, Accessible : Oui, Borne de Recharge : Non
Voisins : 
A
C

Ainsi pour sauvegarder l'agglomeration, c'est l'option 4.
Donc un fichier sera crée et il vous sera demandé soit le nom du fichier si vous souhaitez le creer 
dans le répertoire où se trouve le projet ou le chemin où vous souhaitez le créer. Une erreur sera retournée si le chemin que vous avez entré n'existe pas ou si un fichier du même nom existe déjà.
Pour arreter le programme il vous suffira de taper 5.

PS : On ne comprend pas mais lorsque l'on teste notre programme sur l'exemple donné dans le sujet, ça nous retourne que l'agglomeration ne respecte pas les
contraintes d'accesibilités. Hors elle les respecte, on ne comprend pas pourquoi cette erreur apparée. Pourtant nous l'avons essayé sur plusieurs autres exemple tout fonctionne
parfaitement. Voici les erreurs que nous avons avec nos messages de gestion d'erreur :

Une ville a besoin de cette borne de recharge
La borne de recharge de cette ville ne peut pas être supprimée
Les zones de recharges entrées ne satisfaisant pas la contraite d'accessibilité
L'agglomeration va donc être initialiser de façon à ce que toute les villes ai des zones de recharge 

Le programme continue donc normalement seulement, toutes les villes ont des bornes de recharge au départ.