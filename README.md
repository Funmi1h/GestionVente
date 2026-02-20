Comment exécuter le projet?

Réponse:

Installer MySQL

Intaller java (La version 25.0.2 ou plus)

Créer la base de données :

CREATE DATABASE gestionvente;

Importer le script SQL des tables (le fichier gestionvente.sql)

Modifier les constantes de la classe : ConnexionDB 

    private static final String  URL"jdbc:mysql://localhost:3307/gestionvente";
    
    private static final String  USER = "root";
    
    private static final String  PASSWORD = "";

Ouvrir le projet dans NetBeans

Exécuter la classe principale(Groupe3_GestionVente)
