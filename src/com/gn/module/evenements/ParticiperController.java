/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gn.module.evenements;


import com.gn.global.evenements;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import com.gn.global.participation;
import static db.DbConnect.getConnection;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.TableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Asma Srairi
 */
public class ParticiperController implements Initializable {

    @FXML
    private TableView<participation> tvparticipation;
   
    @FXML
    private TextField tfidpart;
    @FXML
    private TextField tfideventpart;
    @FXML
    private TextField tfuser;
    @FXML
    private TextField tftype;
    @FXML
    private Label label;
    private TableColumn<participation, Integer> colidpart;
    @FXML
    private TableColumn<participation, Integer> coldideventpart;
    @FXML
    private TableColumn<participation, String> colaprt;
    @FXML
    private TableColumn<participation, Integer> colfraispart;
    @FXML
    private TableColumn<participation, String> coltype;
    @FXML
    private Button btnajoutpart;
    private Button btnmodifierpart;
    private Button btnsupppart;
    @FXML
    private TextField tffraispart;
    @FXML
    private TableColumn<participation, String> coleditpart;

    @FXML
       private void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnajoutpart) {
            insertRecord();

        } else if (event.getSource() == btnmodifierpart) {
            updateRecord();
        } else if (event.getSource() == btnsupppart) {
            deleteRecord();

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showparticipation();
    }

    public ObservableList<participation> getparticipationList() {
        ObservableList<participation> participationList = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM participation";
        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
           participation participation;
            while (rs.next()) {
                participation = new participation(rs.getInt("id_part"),rs.getInt("id_event"), rs.getString("personne_part"), rs.getInt("frais_part"), rs.getString("type_part"));
                participationList.add(participation);
            }

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return participationList;

    }

    private void refreshTable() {
        ObservableList<participation> List = getparticipationList();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            List.clear();
            Connection conn = getConnection();

            String query = "SELECT * FROM `participation`";
            preparedStatement = conn.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                List.add(new participation(
                        resultSet.getInt("id_part"),
                        resultSet.getInt("id_event"),
                        resultSet.getString("personne_part"),
                        resultSet.getInt("frais_part"),
                         resultSet.getString("type_part")
                        
                ));
                tvparticipation.setItems(List);

            }

        } catch (SQLException ex) {
            Logger.getLogger(EvenementsController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    PreparedStatement ps = null;

    public void showparticipation() {
        ObservableList<participation> list = getparticipationList();
       
        coldideventpart.setCellValueFactory(new PropertyValueFactory<>("id_event"));
        colaprt.setCellValueFactory(new PropertyValueFactory<>("personne_part"));
        colfraispart.setCellValueFactory(new PropertyValueFactory<>("frais_part"));
        coltype.setCellValueFactory(new PropertyValueFactory<>("type_part"));

        tvparticipation.setItems(list);
        Callback<TableColumn<participation, String>, TableCell<participation, String>> cellFoctory = (TableColumn<participation, String> param) -> {
            // make cell containing buttons
            final TableCell<participation, String> cell = new TableCell<participation, String>() {
                @Override
                public void updateItem(String item, boolean empty) {

                    participation participation = null;
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
                                    participation participation;
                                    participation = tvparticipation.getSelectionModel().getSelectedItem();
                                    String query = "DELETE FROM `participation` WHERE id_part =" + participation.getId_part();
                                    Connection conn = getConnection();
                                    ps = conn.prepareStatement(query);

                                    ps.execute();
                                    refreshTable();

                                } catch (SQLException ex) {
                                    Logger.getLogger(EvenementsController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        });
                        
                        editIcon.setOnMouseClicked((new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                participation participation = tvparticipation.getSelectionModel().getSelectedItem();

                                tfideventpart.setText(""+participation.getId_event());
                                tfuser.setText(participation.getPersonne_part());
                                tffraispart.setText(""+participation.getFrais_part());
                                tftype.setText(participation.getType_part());
                  

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
        coleditpart.setCellFactory(cellFoctory);
        tvparticipation.setItems(list);

    }
    //ajouter un evenement

    private void insertRecord() {

        String query = "INSERT INTO participation(id_event,personne_part,frais_part,type_part) VALUES(" + tfideventpart.getText() + ",'" + tfuser.getText() + "'," + tffraispart.getText() + ",'" + tftype.getText()+"')";
        executeQuery(query);
        showparticipation();
    }
    //modifier un evenement

    private void updateRecord() {
        String query = "UPDATE participation SET id_event = '" + tfideventpart.getText() + "', personne_part = '" + tfuser.getText() + "', frais_part ='" + tffraispart.getText() + "', type_part = '" + tftype.getText() + "' WHERE id_part=" + tfideventpart.getText() + "";
        executeQuery(query);
        showparticipation();
    }
    //supprimer un evenement

    private void deleteRecord() {
        String query = "DELETE FROM participation WHERE id_part=" + tfideventpart.getText() + "";
        executeQuery(query);
        showparticipation();
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
