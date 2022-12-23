package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import association.GestionMembres;
import association.InformationPersonnelle;
import association.InterMembre;
import association.Membre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *  Tests unitaires de la classe GestionMembres.
 *
 * @author Kilian Malherbe
 *
 */
class TestGestionMembres extends GestionMembres {
  
  /**
   * Version de la classe.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Objet GestionMembres.
   */
  private GestionMembres gestionM;
  
  /**
   * Objet membre.
   */
  private InterMembre mb1;
  
  /**
   * Objet membre.
   */
  private InterMembre mb2;

  /**
   * Objet membre.
   */
  private InterMembre mb3;
  
  /**
   * Objet membre.
   */
  private InterMembre mb4;
  
  /**
   * Objet membre.
   */
  private InterMembre mb5;
  
  /**
   * Instantiation des classes et ajout de données de remplissage.
   */
  @BeforeEach
  void setUp() {
    mb1 = new Membre();
    mb2 = new Membre();
    mb3 = new Membre();
    mb4 = new Membre();
    mb5 = new Membre();
    gestionM = new GestionMembres();
    mb1.definirInformationPersonnnelle(new InformationPersonnelle("Robitzer", "Youenn"));
    mb2.definirInformationPersonnnelle(new InformationPersonnelle("Bihan", "Alexis"));
  }
  
  /**
   * Vérifie l'ajout d'un membre dans la liste de l'association.
   */
  @Test
  void testAjouterMembre() {
    mb3.definirInformationPersonnnelle(new InformationPersonnelle("Malherbe", "Youenn"));
    mb4.definirInformationPersonnnelle(new InformationPersonnelle("Robitzer", "Kilian"));
    mb5.definirInformationPersonnnelle(new InformationPersonnelle("Bihan", "Alexis"));
    
    assertTrue(gestionM.ajouterMembre(mb1));
    
    assertTrue(gestionM.ajouterMembre(mb2)); // Prénom et nom différent
    assertTrue(gestionM.ajouterMembre(mb3)); // Même prénom
    assertTrue(gestionM.ajouterMembre(mb4)); // Même nom
    assertFalse(gestionM.ajouterMembre(mb5)); // Même prénom et nom
    assertEquals(gestionM.ensembleMembres().size(), 4); // Vérification de la taille
  }
  
  /**
   * Vérifie l'ajout d'un membre dans la liste de l'association.
   */
  @Test
  void testSupprimerMembre() {
    gestionM.ajouterMembre(mb1);
    gestionM.ajouterMembre(mb2);
    
    assertTrue(gestionM.supprimerMembre(mb1)); // Membre dans la liste
    assertFalse(gestionM.supprimerMembre(mb1)); // Membre absent de la liste
    assertEquals(gestionM.ensembleMembres().size(), 1); // Liste vide après suppression
  }
  
  /**
   * Vérifie la désignation d'un président dans la liste de membres.
   */
  @Test
  void testDesignerPresident() {
    gestionM.ajouterMembre(mb1);
    gestionM.ajouterMembre(mb2);

    assertTrue(gestionM.designerPresident(mb1)); // Désignation d'un président
    assertTrue(gestionM.designerPresident(mb2)); // Changement de président
  }
  
  /**
   * Test de la méthode pour récupérer un président.
   */
  @Test
  void testPresident() {
    gestionM.ajouterMembre(mb1);
    
    assertNull(gestionM.president()); // Pas de président désigné
    gestionM.designerPresident(mb1);
    assertNotNull(gestionM.president()); // Président désigné
  }
}