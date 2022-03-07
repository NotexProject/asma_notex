/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gn.module.commentaire;

import animatefx.animation.Pulse;
import com.gn.global.commentaire;
import static db.DbConnect.getConnection;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import javafx.util.Duration;
import javax.mail.MessagingException;

/**
 * FXML Controller class
 *
 * @author Asma Srairi
 */
public class CommentaireController implements Initializable {

    @FXML
    private TableColumn<commentaire, String> colpersonne;
    @FXML
    private TableColumn<commentaire, String> colcontenue;
    @FXML
    private TableColumn<commentaire, Date> coldatedecreation;
    @FXML
    private TextField tfidc;
    @FXML
    private TextField tfpersonne;
    @FXML
    private TextField tfcontenue;
    @FXML
    private DatePicker tfdatedecreation;
    @FXML
    private Button btnajouter;
    private Button btnmodifier;
    private Button btnsupprimer;
    @FXML
    private TableView<commentaire> tvcommentaire;
    @FXML
    private Label lbl_contenue;
    @FXML
    private Label lbl_personne;
    @FXML
    private TableColumn<commentaire,String> coledit;

    @FXML
    private void handleButtonAction(ActionEvent event) throws MessagingException {
        if (event.getSource() == btnajouter) {
           enterAction() ; 
           mail.sendMail("asma.srairi007@gmail.com"); 

        } else if (event.getSource() == btnmodifier) {
            updateRecord();
        } else if (event.getSource() == btnsupprimer) {
            deleteRecord();

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showcommentaire();
    }

        public ObservableList<commentaire> getcommentaireList() {
        ObservableList<commentaire> commentaireList = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM commentaire";
        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            commentaire commentaire;
            while (rs.next()) {
                commentaire = new commentaire(rs.getInt("idc"), rs.getString("personne"), rs.getString("contenue"), rs.getDate("date_de_creation"));
                commentaireList.add(commentaire);
            }

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return commentaireList;
    }
      //control de saisie commentaire
           private boolean validcontenue(){
        return !tfcontenue.getText().isEmpty() && tfcontenue.getLength() > 3;
    }
              private boolean validpersonne(){
        return !tfpersonne.getText().isEmpty() && tfpersonne.getLength() > 3;
    }
        private void enterAction(){
        Pulse pulse = new Pulse(btnajouter);
        pulse.setDelay(Duration.millis(20));
        pulse.play();
        if(validcontenue()&& validpersonne() )
            insertRecord() ; 
        else {
            lbl_contenue.setVisible(true);
            lbl_personne.setVisible(true);
            
        }
    }    
         private void refreshTable() {
        ObservableList<commentaire> List = getcommentaireList();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            List.clear();
            Connection conn = getConnection();

            String query = "SELECT * FROM `commentaire`";
            preparedStatement = conn.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                List.add(new commentaire(
                        resultSet.getInt("idc"),
                        resultSet.getString("personne"),
                        resultSet.getString("contenue"),
                        resultSet.getDate("date_de_creation")
                ));
                tvcommentaire.setItems(List);

            }

        } catch (SQLException ex) {
            Logger.getLogger(CommentaireController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
            PreparedStatement ps = null;

    public void showcommentaire() {
        ObservableList<commentaire> list = getcommentaireList();
      
        colpersonne.setCellValueFactory(new PropertyValueFactory<>("personne"));
        colcontenue.setCellValueFactory(new PropertyValueFactory<>("contenue"));
        coldatedecreation.setCellValueFactory(new PropertyValueFactory<>("date_de_creation"));
        tvcommentaire.setItems(list);
         Callback<TableColumn<commentaire, String>, TableCell<commentaire, String>> cellFoctory = (TableColumn<commentaire, String> param) -> {
            // make cell containing buttons
            final TableCell<commentaire, String> cell = new TableCell<commentaire, String>() {
                @Override
                public void updateItem(String item, boolean empty) {

                    commentaire commentaire = null;
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {
                        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);

                        deleteIcon.setStyle(
                                " -fx-cursor: hand ;"
                                + "-glyph-size:28px;"
                                + "-fx-fill:#ff1744;"
                        );
                        editIcon.setStyle(
                                " -fx-cursor: hand ;"
                                + "-glyph-size:28px;"
                                + "-fx-fill:#00E676;"
                        );
                        deleteIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                try {
                                    commentaire commentaire;
                                    commentaire = tvcommentaire.getSelectionModel().getSelectedItem();
                                    String query = "DELETE FROM `commentaire` WHERE idc  =" + commentaire.getIdc();
                                    Connection conn = getConnection();
                                    ps = conn.prepareStatement(query);

                                    ps.execute();
                                    refreshTable();

                                } catch (SQLException ex) {
                                    Logger.getLogger(CommentaireController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        });
                        editIcon.setOnMouseClicked((new EventHandler<MouseEvent>() {
                              @Override
                            public void handle(MouseEvent event) {
                                commentaire commentaire = tvcommentaire.getSelectionModel().getSelectedItem();
                                
                                tfpersonne.setText(commentaire.getPersonne());
                                tfcontenue.setText(commentaire.getContenue());
                                tfdatedecreation.setValue(commentaire.getDate_de_creation().toLocalDate());
                               
                            }
                        
                        }));
                        HBox managebtn = new HBox(editIcon, deleteIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                        HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));

                        setGraphic(managebtn);

                        setText(null);
                    }
                }

            };
            return cell;
        };
        coledit.setCellFactory(cellFoctory);
        tvcommentaire.setItems(list);
    }
    //ajouter un commentaire 
    private void insertRecord() {

        String query = "INSERT INTO commentaire(personne,contenue,date_de_creation) VALUES('" + tfpersonne.getText() + "','" + tfcontenue.getText() + "','" + tfdatedecreation.getValue() + "')";
        executeQuery(query);
        showcommentaire();
    }

    //modifier un commentaire 
    private void updateRecord() {
        String query = "UPDATE commentaire SET personne = '" + tfpersonne.getText() + "', contenue = '" + tfcontenue.getText() + "', date_de_creation ='" + tfdatedecreation.getValue() + "' WHERE idc=" + tfidc.getText() + "";
        executeQuery(query);
        showcommentaire();
    }
    //supprimer un commentaire 
    private void deleteRecord() {
        String query = "DELETE FROM commentaire WHERE idc=" + tfidc.getText() + "";
        executeQuery(query);
        showcommentaire();
    }

    private void executeQuery(String query) {
        Connection conn = getConnection();
        Statement st;
        try {
            st = conn.createStatement();
            st.executeUpdate(query);
        } catch (SQLException ex) {
            ex.printStackTrace();

        }

    }

   
}
