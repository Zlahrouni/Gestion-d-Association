package ui;

import association.Evenement;
import association.GestionAssociation;
import association.InformationPersonnelle;
import association.InterMembre;
import association.Membre;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Contrôleur de l'interface graphique de l'application.

 * @author Ziad Lahrouni / Hanane Erraji / Kilian Malherbe / Youenn Robitzer
 */
public class Controleur implements Initializable {
  
  /**
   * Fenêtre de l'application.
   */
  private Stage stage;
  
  /**
   * Gestionnaire de l'association.
   */
  private GestionAssociation gestionAssociation = new GestionAssociation();
  
  /**
   * L'événement sélectionné par l'utilisateur.
   */
  private Evenement evtEncours;
  
  /**
   * Le membre sélectionné par l'utilisateur.
   */
  private Membre mbEncours;
  
  /**
   * Permet de modifier un événement déjà créé.
   */
  private Evenement eveModif = null;
  
  /**
   * Champ adresse d'un membre.
   */
  @FXML
  private TextField entreAdresseMembre;
  
  /**
   * Champ age d'un membre.
   */
  @FXML
  private TextField entreAgeMembre;
  
  /**
   * Champ date d'un membre.
   */
  @FXML
  private DatePicker entreeDateEvt;
  
  /**
   * Champ durée d'un événement.
   */
  @FXML
  private TextField entreeDureeEvt;
  
  /**
   * Champ heure d'un événement.
   */
  @FXML
  private Spinner<Integer> entreeHeureEvt;
  
  /**
   * Champ minutes d'un événement.
   */
  @FXML
  private Spinner<Integer> entreeMinEvt;
  
  /**
   * Champ lieu d'un événement.
   */
  @FXML
  private TextField entreeLieuEvt;
  
  /**
   * Champ maximum de participants d'un événement.
   */
  @FXML
  private TextField entreeMaxParticipantsEvt;
  
  /**
   * Nom d'un événement.
   */
  @FXML
  private TextField entreeNomEvt;
  
  /**
   * Nom d'un membre.
   */
  @FXML
  private TextField entreeNomMembre;
  
  /**
   * Prénom d'un membre.
   */
  @FXML
  private TextField entreePrenomMembre;
  
  /**
   * Informations du contenu de la liste d'événement.
   */
  @FXML
  private Label labelListeAfficheeEvt;
  
  /**
   * Informations du contenu de la liste de membres.
   */
  @FXML
  private Label labelListeAfficheeMembre;
  
  /**
   * Liste JavaFX d'événements.
   */
  @FXML
  private ListView<Evenement> listeEvenements;
  
  /**
   * Liste JavaFX de membres.
   */
  @FXML
  private ListView<Membre> listeMembres;
  
  /**
   * Remplis les champs au dessus de la liste avec les informations personnelles
   * du membre sélectionné.

   * @param event Événement associé au bouton
   */
  @FXML
  void actionBoutonAfficherMembreSelectionneMembre(ActionEvent event) {
    mbEncours = listeMembres.getSelectionModel().getSelectedItem();
    
    if (mbEncours != null) {
      entreeNomMembre.setText(mbEncours.getInformationPersonnelle().getNom());
      entreePrenomMembre
          .setText(mbEncours.getInformationPersonnelle().getPrenom());
      entreAdresseMembre
          .setText(mbEncours.getInformationPersonnelle().getAdresse());
      entreAgeMembre.setText(
          Integer.toString(mbEncours.getInformationPersonnelle().getAge()));
    } else {
      Alert alert = new Alert(AlertType.WARNING);
      alert.setHeaderText("Attention");
      alert.setContentText("Veuillez sélectionner un membre.");
      alert.showAndWait();
    }
  }
  
  /**
   * Affiche les participants d'un événement sélectionné.

   * @param event Événement associé au bouton
   */
  @FXML
  void actionBoutonAfficherParticipantsEvt(ActionEvent event) {
    listeMembres.getItems().clear();
    
    if (evtEncours == null) {
      alertePasEvenement();
      return;
    }
    if (!evtEncours.getNom().equals("") && !evtEncours.getNom().equals(null)) {
      labelListeAfficheeMembre.setText("Participants à " + evtEncours.getNom());
    }
    if (evtEncours.getParticipants().size() <= 0) {
      return;
    }
    for (InterMembre m : evtEncours.getParticipants()) {
      
      listeMembres.getItems().add(listeMembres.getItems().size(), (Membre) m);
    }
  }
  
  /**
   * Affiche tout les membres de l'association.

   * @param event Événement associé au bouton
   */
  @FXML
  void actionBoutonAfficherTousMembresMembre(ActionEvent event) {
    updateListeMembres();
  }
  
  /**
   * Sélectionne l'événement et affiche ses informations.

   * @param event Événement associé au bouton
   */
  @FXML
  void actionBoutonEvenementSelectionneEvt(ActionEvent event) {
    evtEncours = listeEvenements.getSelectionModel().getSelectedItem();
    eveModif = evtEncours;
    if (evtEncours != null) {
      entreeNomEvt.setText(evtEncours.getNom());
      entreeLieuEvt.setText(evtEncours.getLieu());
      entreeDateEvt.setValue(evtEncours.getDate().toLocalDate());
      entreeHeureEvt.getValueFactory().setValue(evtEncours.getDate().getHour());
      entreeMinEvt.getValueFactory().setValue(evtEncours.getDate().getMinute());
      entreeDureeEvt.setText(Integer.toString(evtEncours.getDuree()));
      entreeMaxParticipantsEvt
          .setText(Integer.toString(evtEncours.getNbParticipantsMax()));
    } else {
      alertePasEvenement();
    }
  }
  
  /**
   * Affiche tout les événements à venir.

   * @param event Événement associé au bouton
   */
  @FXML
  void actionBoutonEvenementsFutursAssociation(ActionEvent event) {
    labelListeAfficheeEvt.setText("Tout les évenements à venir");
    listeEvenements.getItems().clear();
    for (Evenement e : gestionAssociation.gestionnaireEvenements()
        .ensembleEvenementAvenir()) {
      listeEvenements.getItems().add(e);
    }
  }
  
  /**
   * Affiche les événements à venir pour un membre.

   * @param event Événement associé au bouton
   */
  @FXML
  void actionBoutonEvenementsFutursMembre(ActionEvent event) {
    if (verificationChampsMembres()) {
      listeEvenements.getItems().clear();
      
      String nom = entreeNomMembre.getText();
      String prenom = entreePrenomMembre.getText();
      
      InterMembre m = getMembre(nom, prenom);
      
      if (m != null) {
        for (Evenement e : m.ensembleEvenementsAvenir()) {
          listeEvenements.getItems().add(e);
        }
        labelListeAfficheeMembre
            .setText("Les événement à venir de " + nom + " " + prenom);
      }
    }
  }
  
  /**
   * Affiche les événement d'un membre.

   * @param event Événement associé au bouton
   */
  @FXML
  void actionBoutonEvenementsMembreMembre(ActionEvent event) {
    if (verificationChampsMembres()) {
      listeEvenements.getItems().clear();
      
      String nom = entreeNomMembre.getText();
      String prenom = entreePrenomMembre.getText();
      
      InterMembre m = getMembre(nom, prenom);
      
      if (m != null) {
        for (Evenement e : m.ensembleEvenements()) {
          listeEvenements.getItems().add(e);
        }
        labelListeAfficheeMembre
            .setText("Les événement de " + nom + " " + prenom);
      }
    }
  }
  
  /**
   * Désinscrit le membre à l'événement, sélectionnés respectivement dans leurs
   * listes.

   * @param event Événement associé au bouton
   */
  @FXML
  void actionBoutonDesinscrireMembreEvenement(ActionEvent event) {
    Membre membre = mbEncours;
    Evenement evenement = evtEncours;
    
    if (membre != null && evenement != null) {
      
      
      if (gestionAssociation.gestionnaireEvenements()
          .annulerEvenement(evenement, membre)) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setHeaderText("Succès");
        alert.setContentText("Le membre a été désincrit de l'événement.");
        alert.showAndWait();
        actionBoutonTousEvenementsAssociationEvt(null);
      } else {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setHeaderText("Erreur");
        alert.setContentText(
            "Le membre n'a pas pu être désinscrit de la liste de participants.");
        alert.showAndWait();
      }
      
    } else {
      Alert alert = new Alert(AlertType.WARNING);
      alert.setHeaderText("Attention");
      alert.setContentText("Veuillez sélectionner un membre et un événement.");
      alert.showAndWait();
    }
  }
  
  /**
   * Inscrit le membre à l'événement, sélectionnés respectivement dans leurs
   * listes.

   * @param event Événement associé au bouton
   */
  @FXML
  void actionBoutonInscrireMembreEvenement(ActionEvent event) {
    Evenement evenement = evtEncours;
    
    if (mbEncours != null && evenement != null) {
      
      
      if (gestionAssociation.gestionnaireEvenements()
          .inscriptionEvenement(evenement, mbEncours)) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setHeaderText("Succès");
        alert.setContentText("Le membre a été inscrit à l'événément.");
        alert.showAndWait();
        actionBoutonTousEvenementsAssociationEvt(null);
      } else {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setHeaderText("Erreur");
        alert.setContentText(
            "Le membre n'a pas pu être ajouté à la liste de participants.");
        alert.showAndWait();
      }
    } else {
      Alert alert = new Alert(AlertType.WARNING);
      alert.setHeaderText("Attention");
      alert.setContentText("Veuillez sélectionner un membre et un événement.");
      alert.showAndWait();
    }
  }
  
  /**
   * Vide les champs pour créer un nouvel événement.

   * @param event Événement associé au bouton
   */
  @FXML
  void actionBoutonNouveauEvt(ActionEvent event) {
    eveModif = null;
    entreeNomEvt.clear();
    entreeLieuEvt.clear();
    entreeDateEvt.setValue(null);
    entreeHeureEvt.getValueFactory().setValue(0);
    entreeMinEvt.getValueFactory().setValue(0);
    entreeDureeEvt.clear();
    entreeMaxParticipantsEvt.clear();
    evtEncours = null;
  }
  
  /**
   * Vide les champs pour créer un nouveau membre.

   * @param event Événement associé au bouton
   */
  @FXML
  void actionBoutonNouveauMembre(ActionEvent event) {
    entreeNomMembre.clear();
    entreePrenomMembre.clear();
    entreAdresseMembre.clear();
    entreAgeMembre.clear();
    mbEncours = null;
  }
  
  /**
   * Supprime l'événement sélectionné de la liste d'événements.

   * @param event Événement associé au bouton
   */
  @FXML
  void actionBoutonSupprimerEvt(ActionEvent event) {
    Alert alerte;
    if (evtEncours != null) {
      alerte = new Alert(AlertType.CONFIRMATION);
      alerte.setHeaderText("Êtes-vous sur de vouloir suprimer l'évènement ?");
      alerte.setTitle("Supression évènement");
      Optional<ButtonType> option = alerte.showAndWait();
      if (option.get() == ButtonType.OK) {
        gestionAssociation.gestionnaireEvenements()
            .supprimerEvenement(evtEncours);
        actionBoutonNouveauEvt(null);
        updateListeEvenement();
      }
      
    } else {
      alertePasEvenement();
    }
    
  }
  
  /**
   * Alerte l'utilisateur qu'il n'a pas sélectionné un événement spécifique.
   */
  void alertePasEvenement() {
    Alert alerte;
    alerte = new Alert(AlertType.INFORMATION);
    alerte.setHeaderText("Pas d'évènement séléctionné");
    alerte.setTitle("Évènement");
    alerte.show();
  }
  
  /**
   * Supprime un membre de la liste des membres.

   * @param event Événement associé au bouton
   */
  @FXML
  void actionBoutonSupprimerMembre(ActionEvent event) {
    if (verificationChampsMembres()) {
      
      Membre m = mbEncours;
      
      Alert alertConf = new Alert(AlertType.CONFIRMATION);
      alertConf.setHeaderText("Confirmation de suppresion");
      alertConf.setContentText("Etes-vous sûr de vouloir supprimer "
          + m.getInformationPersonnelle().getNom() + " "
          + m.getInformationPersonnelle().getPrenom() + " ?");
      Optional<ButtonType> option = alertConf.showAndWait();
      
      // Si l'utilisateur confirme la suppression
      if (option.get() == ButtonType.OK) {
        if (gestionAssociation.gestionnaireMembre().supprimerMembre(m)) {
          Alert alertSuppr = new Alert(AlertType.INFORMATION);
          alertSuppr.setHeaderText("Succès");
          alertSuppr
              .setContentText("Le membre a bien été supprimé de la liste");
          alertSuppr.showAndWait();
          updateListeMembres();
          actionBoutonNouveauMembre(null);
        } else {
          Alert alertErr = new Alert(AlertType.ERROR);
          alertErr.setHeaderText("Erreur");
          alertErr.setContentText("Le membre n'est pas présent dans la liste");
          alertErr.showAndWait();
        }
      }
    }
  }
  
  /**
   * Affiche tout les événements de l'association.

   * @param event Événement associé au bouton
   */
  @FXML
  void actionBoutonTousEvenementsAssociationEvt(ActionEvent event) {
    labelListeAfficheeEvt.setText("Tout les évenements");
    listeEvenements.getItems().clear();
    for (Evenement e : gestionAssociation.gestionnaireEvenements()
        .ensembleEvenements()) {
      listeEvenements.getItems().add(e);
    }
  }
  
  /**
   * (Cas general) Ajoute d'un événement dans l'association.
   * Si l'événement existe déjà et a été sélectionné pour être modifié, cet
   * événement sera mis à jour. <br>
   * (Cas particulier non géré) si l'on modifie l'événement, on supprime tout
   * les participants; on peut les notifier par mail pour vérifier si ils
   * veulent toujours participer à l'événement.

   * @param event Événement associé au bouton
   */
  @FXML
  void actionBoutonValiderEvt(ActionEvent event) {
    if (eveModif != null) {
      final Evenement eve = eveModif;
      gestionAssociation.gestionnaireEvenements().supprimerEvenement(eve);
    }
    
    String nom = entreeNomEvt.getText().trim();
    String lieu = entreeLieuEvt.getText().trim();
    Alert alerte;
    
    if (nom.isEmpty()) {
      alerte = new Alert(AlertType.ERROR);
      alerte.setHeaderText("L'évènement n'a pas pus être créé");
      alerte.setContentText("Veuillez entrer le nom d'evenement");
      alerte.show();
      return;
    }
    
    if (lieu.isEmpty()) {
      alerte = new Alert(AlertType.ERROR);
      alerte.setHeaderText("L'évènement n'a pas pus être créé");
      alerte.setContentText("Veuillez entrer le lieu d'evenement");
      alerte.show();
      return;
    }
    
    int heure = entreeHeureEvt.getValue();
    int minutes = entreeMinEvt.getValue();
    int duree;
    int maxParti;
    evtEncours = null;
    
    if (entreeDureeEvt.getText().trim().equals("")) {
      duree = 0;
    } else {
      duree = Integer.parseInt(entreeDureeEvt.getText().trim());
    }
    
    if (entreeMaxParticipantsEvt.getText().trim().equals("")) {
      maxParti = 0;
    } else {
      maxParti = Integer.parseInt(entreeMaxParticipantsEvt.getText().trim());
    }
    
    LocalDate date = entreeDateEvt.getValue();
    
    if (date != null) {
      evtEncours = gestionAssociation.gestionnaireEvenements().creerEvenement(
          nom, lieu, date.getDayOfMonth(), date.getMonth(), date.getYear(),
          heure, minutes, duree, maxParti);
    }
    
    if (evtEncours == null) {
      
      if (eveModif != null) {
        evtEncours = gestionAssociation.gestionnaireEvenements().creerEvenement(
            eveModif.getNom(), eveModif.getLieu(),
            eveModif.getDate().getDayOfMonth(), eveModif.getDate().getMonth(),
            eveModif.getDate().getYear(), eveModif.getDate().getHour(),
            eveModif.getDate().getMinute(), eveModif.getDuree(),
            eveModif.getNbParticipantsMax());
        gestionAssociation.gestionnaireEvenements().ensembleEvenements()
            .forEach(i -> {
              if (i.equals(eveModif)) {
                eveModif.getParticipants().forEach(j -> i.ajoutParticipant(j));
              }
            });
        eveModif = null;
      }
      
      alerte = new Alert(AlertType.ERROR);
      alerte.setHeaderText("L'évènement n'a pas pus être créé");
      alerte.setContentText("Verifié que la date et l'heure sont valide et "
          + "qu'il n'y a pas de chevauchement avec d'autres évènements");
      alerte.show();
      
    } else {
      
      if (eveModif != null) {
        gestionAssociation.gestionnaireEvenements().ensembleEvenements()
            .forEach(i -> {
              if (i.equals(eveModif)) {
                
                eveModif.getParticipants().forEach(j -> i.ajoutParticipant(j));
                
              }
            });
        eveModif = null;
      }
      
      alerte = new Alert(AlertType.INFORMATION);
      alerte.setHeaderText("L'évènement a été créé avec succès");
      alerte.show();
      actionBoutonTousEvenementsAssociationEvt(null);
    }
  }
  
  /**
   * Valide un événement si les champs entrés sont correctes. Si l'événement
   * existait déjà, on le modifie. Le cas où, à cause d'une modification de date un
   * chevauchement d'évènement peut se produire pour un participant, n'as pas été traité.

   * @param event Événement associé au bouton
   */
  @FXML
  void actionBoutonValiderMembre(ActionEvent event) {
    if (verificationChampsMembres()) {
      String nom = entreeNomMembre.getText();
      String prenom = entreePrenomMembre.getText();
      String adresse = entreAdresseMembre.getText();
      int age = Integer.parseInt(entreAgeMembre.getText());
      
      InterMembre membreAjout = new Membre();
      InformationPersonnelle info =
          new InformationPersonnelle(nom, prenom, adresse, age);
      membreAjout.definirInformationPersonnnelle(info);
      
      if (!gestionAssociation.gestionnaireMembre().ajouterMembre(membreAjout)) {
        InterMembre membre = getMembre(nom, prenom);
        
        if (membre != null) {
          membre.definirInformationPersonnnelle(info);
        }
      }
      mbEncours = (Membre) membreAjout;
      updateListeMembres();
    }
  }
  
  /**
   * Rafraîchie la liste de membres.
   */
  private void updateListeMembres() {
    labelListeAfficheeMembre.setText("Tout les membres de l'association");
    listeMembres.getItems().clear();
    
    // Remplissage de la ListView avec la liste de gestionMembre
    for (InterMembre membreL : gestionAssociation.gestionnaireMembre()
        .ensembleMembres()) {
      
      listeMembres.getItems().add((Membre) membreL);
    }
  }
  
  /**
   * Rafraîchie la liste d'événements.
   */
  private void updateListeEvenement() {
    labelListeAfficheeMembre.setText("Tout les évènements");
    listeEvenements.getItems().clear();
    
    // Remplissage de la ListView avec la liste de gestionMembre
    for (Evenement evt : gestionAssociation.gestionnaireEvenements()
        .ensembleEvenementAvenir()) {
      
      listeEvenements.getItems().add(evt);
    }
  }
  
  /**
   * Vérifie la cohérence des données entré par l'utilisateur pour la partie
   * membre.

   * @exception NumberFormatException L'âge entré par l'utilisateur n'est pas un nombre
   * @return    <code>true</code> si les données sont correctes 
   *            <code>false</code> sinon.
   */
  private boolean verificationChampsMembres() {
    boolean flag = true;
    String nom = entreeNomMembre.getText();
    String prenom = entreePrenomMembre.getText();
    String adresse = entreAdresseMembre.getText();
    
    if (nom.isEmpty() || prenom.isEmpty() || adresse.isEmpty()
        || entreAgeMembre.getText().isEmpty()) {
      
      flag = false;
      Alert alert = new Alert(AlertType.WARNING);
      alert.setHeaderText("Attention");
      alert.setContentText("Veuillez remplir tout les champs.");
      alert.showAndWait();
    }
    
    if (nom.matches("[a-zA-Z]") || prenom.matches("[a-zA-Z]")
        || adresse.matches("[a-zA-Z]")) {
      flag = false;
      Alert alert = new Alert(AlertType.WARNING);
      alert.setHeaderText("Attention");
      alert.setContentText("Les champs ne sont pas correctement remplis");
      alert.showAndWait();
    }
    
    try {
      Integer.parseInt(entreAgeMembre.getText());
      entreAgeMembre.setBorder(null);
      
    } catch (NumberFormatException e) {
      flag = false;
      Alert alert = new Alert(AlertType.WARNING);
      alert.setHeaderText("Attention");
      alert.setContentText("Veuillez entrer un entier.");
      alert.showAndWait();
    }

    return flag;
  }
  
  
  /**
   * Récupère le membre avec les champs utilisateur.

   * @param nom    Nom du membre
   * @param prenom Prénom du membre
   * @return       Un objet InterMembre si le membre existe sinon <code>null</code>
   */
  private InterMembre getMembre(String nom, String prenom) {
    
    for (InterMembre membreL : gestionAssociation.gestionnaireMembre()
        .ensembleMembres()) {
      if (nom.equals(membreL.getInformationPersonnelle().getNom())) {
        if (prenom.equals(membreL.getInformationPersonnelle().getPrenom())) {
          return membreL;
        }
      }
    }
    
    Alert alertErr = new Alert(AlertType.ERROR);
    alertErr.setHeaderText("Erreur");
    alertErr.setContentText("Le membre n'était pas présent dans la liste");
    alertErr.showAndWait();
    
    return null;
  }
  
  /**
   * Affiche un menu avec le nom des développeurs.

   * @param event Événement associé au bouton
   */
  @FXML
  void actionMenuApropos(ActionEvent event) {
    Alert alertInfo = new Alert(AlertType.INFORMATION);
    alertInfo.setHeaderText("Développeurs");
    alertInfo
        .setContentText("- Youenn Robitzer\n- Ziad Lahrouni\n- Hanane Erraji\n- Kilian Malherbe");
    alertInfo.showAndWait();
  }
  
  /**
   * Charge les données d'un fichier.

   * @param event Événement associé au bouton
   */
  @FXML
  void actionMenuCharger(ActionEvent event) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters()
        .addAll(new FileChooser.ExtensionFilter("DAT", "*.dat"));
    File file = fileChooser.showOpenDialog(stage);
    
    if (file != null) {
      try {
        gestionAssociation.chargerDonnees(file.getPath());
      } catch (IOException e) {
        Alert alerte = new Alert(AlertType.ERROR);
        alerte.setHeaderText("Les données n'ont pas été chargée");
        alerte.show();
      }
    }
  }
  
  /**
   * Supprime les données de l'association.

   * @param event Événement associé au bouton
   */
  @FXML
  void actionMenuNouveau(ActionEvent event) {
    gestionAssociation = new GestionAssociation();
    actionBoutonNouveauMembre(null);
    actionBoutonNouveauEvt(null);
    listeEvenements.getItems().clear();
    listeMembres.getItems().clear();
    
  }
  
  /**
   * Quitte l'application.

   * @param event Événement associé au bouton
   */
  @FXML
  void actionMenuQuitter(ActionEvent event) {
    System.exit(0);
  }
  
  /**
   * Sauvegarde les données dans un fichier <code>.dat</code>.

   * @param event Action utilisateur
   */
  @FXML
  void actionMenuSauvegarder(ActionEvent event) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters()
        .addAll(new FileChooser.ExtensionFilter("DAT", "*.dat"));
    File file = fileChooser.showSaveDialog(stage);
    
    try {
      gestionAssociation.sauvegarderDonnees(file.getPath());
    } catch (IOException e) {
      Alert alerte = new Alert(AlertType.ERROR);
      alerte.setHeaderText("Les données n'ont pas été sauvegardé");
      alerte.show();
    }
    
  }
  
  /**
   * Méthode d'initialisation propre à JavaFX.
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    System.out.println("Initialisation de l'interface");
    
    SpinnerValueFactory<Integer> spinnValFactHeure =
        new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23);
    entreeHeureEvt.setValueFactory(spinnValFactHeure);
    SpinnerValueFactory<Integer> spinnValFactMinute =
        new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59);
    entreeMinEvt.setValueFactory(spinnValFactMinute);
    
    entreeDureeEvt.textProperty().addListener(new ChangeListener<String>() {
      
      @Override
      public void changed(ObservableValue<? extends String> observable,
          String oldValue, String newValue) {
        if (!newValue.matches("[0-9]*")) {
          entreeDureeEvt.setText(oldValue);
        }
        
      }
    });
    
    entreAgeMembre.textProperty().addListener(new ChangeListener<String>() {
      
      @Override
      public void changed(ObservableValue<? extends String> observable,
          String oldValue, String newValue) {
        if (!newValue.matches("[0-9]*")) {
          entreAgeMembre.setText(oldValue);
        }
        
      }
    });
    entreeMaxParticipantsEvt.textProperty()
        .addListener(new ChangeListener<String>() {
          
          @Override
          public void changed(ObservableValue<? extends String> observable,
              String oldValue, String newValue) {
            if (!newValue.matches("[0-9]*")) {
              entreeMaxParticipantsEvt.setText(oldValue);
            }
            
          }
        });
    
    listeEvenements.setCellFactory(param -> new ListCell<Evenement>() {
      @Override
      protected void updateItem(Evenement evenement, boolean empty) {
        super.updateItem(evenement, empty);
        
        if (empty || evenement == null) {
          setText(null);
        } else {
          String evt = evenement.getNom() + " " + evenement.getLieu() + " "
              + evenement.getDate().format(
                  DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
              + "\t" + evenement.getDuree() + " \t"
              + evenement.getParticipants().size() + "/"
              + evenement.getNbParticipantsMax();
          setText(evt);
        }
      }
    });
    listeMembres.setCellFactory(param -> new ListCell<Membre>() {
      @Override
      protected void updateItem(Membre membre, boolean empty) {
        super.updateItem(membre, empty);
        
        if (empty || membre == null) {
          setText(null);
        } else {
          InformationPersonnelle infoM = membre.getInformationPersonnelle();
          String mb = infoM.getPrenom() + ", " + infoM.getNom() + ", "
              + infoM.getAdresse() + ", " + infoM.getAge();
          setText(mb);
        }
      }
    });
    
    
    int maxLength = 100;
    entreAdresseMembre.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(final ObservableValue<? extends String> ov,
          final String oldValue, final String newValue) {
        if (entreAdresseMembre.getText().length() > maxLength) {
          String s = entreAdresseMembre.getText().substring(0, maxLength);
          entreAdresseMembre.setText(s);
        }
      }
    });
    entreAgeMembre.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(final ObservableValue<? extends String> ov,
          final String oldValue, final String newValue) {
        if (entreAgeMembre.getText().length() > maxLength) {
          String s = entreAgeMembre.getText().substring(0, maxLength);
          entreAgeMembre.setText(s);
        }
      }
    });
    entreeDureeEvt.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(final ObservableValue<? extends String> ov,
          final String oldValue, final String newValue) {
        if (entreeDureeEvt.getText().length() > maxLength) {
          String s = entreeDureeEvt.getText().substring(0, maxLength);
          entreeDureeEvt.setText(s);
        }
      }
    });
    entreeNomEvt.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(final ObservableValue<? extends String> ov,
          final String oldValue, final String newValue) {
        if (entreeNomEvt.getText().length() > maxLength) {
          String s = entreeNomEvt.getText().substring(0, maxLength);
          entreeNomEvt.setText(s);
        }
      }
    });
    entreeLieuEvt.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(final ObservableValue<? extends String> ov,
          final String oldValue, final String newValue) {
        if (entreeLieuEvt.getText().length() > maxLength) {
          String s = entreeLieuEvt.getText().substring(0, maxLength);
          entreeLieuEvt.setText(s);
        }
      }
    });
    entreeNomMembre.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(final ObservableValue<? extends String> ov,
          final String oldValue, final String newValue) {
        if (entreeNomMembre.getText().length() > maxLength) {
          String s = entreeNomMembre.getText().substring(0, maxLength);
          entreeNomMembre.setText(s);
        }
      }
    });
    entreePrenomMembre.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(final ObservableValue<? extends String> ov,
          final String oldValue, final String newValue) {
        if (entreePrenomMembre.getText().length() > maxLength) {
          String s = entreePrenomMembre.getText().substring(0, maxLength);
          entreePrenomMembre.setText(s);
        }
      }
    });
    entreeMaxParticipantsEvt.textProperty()
        .addListener(new ChangeListener<String>() {
          @Override
          public void changed(final ObservableValue<? extends String> ov,
              final String oldValue, final String newValue) {
            if (entreeMaxParticipantsEvt.getText().length() > maxLength) {
              String s =
                  entreeMaxParticipantsEvt.getText().substring(0, maxLength);
              entreeMaxParticipantsEvt.setText(s);
            }
          }
        });
  }
  
  /**
   * Permet de récupérer la fenêtre.

   * @param stage Fenêtre de l'apllication
   */
  public void setStage(Stage stage) {
    this.stage = stage;
  }
}
