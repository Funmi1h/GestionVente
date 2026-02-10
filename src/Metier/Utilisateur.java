package Metier;


public class Utilisateur {
    private int idUtilisateur;
    private String email;
    private String motDePasse;
    private RoleUtilisateur role;
    private int idClient;
    private Boolean estActif;
    private String derniereConnexion;
    private String createdAt;
    
    // Enum pour les rôles
    public enum RoleUtilisateur {
        CLIENT("Client"),
        ADMIN("Administrateur");
        
        private final String libelle;
        
        RoleUtilisateur(String libelle) {
            this.libelle = libelle;
        }
        
        public String getLibelle() {
            return libelle;
        }
    }
    
    // Constructeurs
    public Utilisateur() {
        this.estActif = true;
    }
    
    public Utilisateur(String email, String motDePasse, RoleUtilisateur role) {
        this.email = email;
        this.motDePasse = motDePasse;
        this.role = role;
        this.estActif = true;
    }
    
    // Getters/Setters
    public int getIdUtilisateur() { return idUtilisateur; }
    public void setIdUtilisateur(int idUtilisateur) { this.idUtilisateur = idUtilisateur; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getMotDePasse() { return motDePasse; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }
    
    public RoleUtilisateur getRole() { return role; }
    public void setRole(RoleUtilisateur role) { this.role = role; }
    
    public int getIdClient() { return idClient; }
    public void setIdClient(int idClient) { this.idClient = idClient; }
    
    public Boolean getEstActif() { return estActif; }
    public void setEstActif(Boolean estActif) { this.estActif = estActif; }
    
    public String getDerniereConnexion() { return derniereConnexion; }
    public void setDerniereConnexion(String derniereConnexion) { this.derniereConnexion = derniereConnexion; }
    
    // Méthode utilitaire pour vérifier le rôle
    public boolean estAdmin() {
        return RoleUtilisateur.ADMIN.equals(this.role);
    }
    
    public boolean estClient() {
        return RoleUtilisateur.CLIENT.equals(this.role);
    }
}