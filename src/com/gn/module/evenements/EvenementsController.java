/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gn.module.evenements;

import animatefx.animation.Pulse;
import com.gn.global.commentaire;
import com.gn.global.evenements;
import com.gn.module.commentaire.CommentaireController;
import static db.DbConnect.getConnection;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

import java.awt.Toolkit;
import java.io.File;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import javafx.util.Duration;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * FXML Controller class
 *
 * @author Asma Srairi
 */
public class EvenementsController implements Initializable {

    @FXML
    private TableView<evenements> tvevenements;
    @FXML
    private TableColumn<evenements, Integer> colidevent;
    @FXML
    private TableColumn<evenements, String> colrespo;
    @FXML
    private TableColumn<evenements, String> colnom;
    @FXML
    private TableColumn<evenements, Date> coldate;
    @FXML
    private TableColumn<evenements, String> colcate;
    @FXML
    private TextField tfidevent;
    @FXML
    private TextField tfrespo;
    @FXML
    private TextField tfnom;
    @FXML
    private DatePicker tfdate;
    @FXML
    private TextField tfcate;
    @FXML
    private Button btnajoutevent;
    private Button btnmodifievent;
    private Button btnsuppevent;
    @FXML
    private TextField tfloca;
    @FXML
    private TextField tfnbrplace;
    @FXML
    private TextField tffrais;
    @FXML
    private TableColumn<evenements, String> colloca;
    @FXML
    private TableColumn<evenements, Integer> colnbrplaces;
    @FXML
    private TableColumn<evenements, Integer> coltotfrais;
    @FXML
    private TableColumn<evenements, String> coleditevent;
    @FXML
    private Label label;
    @FXML
    private Button btnbrowse;
    @FXML
    private TextField tfimage;
    @FXML
    private Label label1;
    @FXML
    private Label lbl_nomrespo;
    @FXML
    private Label lbl_nomevent;
    @FXML
    private Label lbl_cate;
    @FXML
    private Label lbl_loca;
    @FXML
    private Label lbl_nbrplaces;
    @FXML
    private Label lbl_totalfrais;

    @FXML

    private void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnajoutevent) {
            insertRecord();

        } else if (event.getSource() == btnmodifievent) {
            updateRecord();
        } else if (event.getSource() == btnsuppevent) {
            deleteRecord();

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showevenements();
    }

    public ObservableList<evenements> getevenementsList() {
        ObservableList<evenements> evenementsList = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM evenements";
        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            evenements evenements;
            while (rs.next()) {
                evenements = new evenements(rs.getInt("id_event"), rs.getString("nom_responsable"), rs.getString("nom_event"), rs.getDate("date_event"), rs.getString("category_event"), rs.getString("localisation_event"), rs.getInt("nbr_places"), rs.getInt("total_frais"));
                evenementsList.add(evenements);
            }

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return evenementsList;

    }

    //control de saisie événements
    private boolean validnomresponsable() {
        return !tfrespo.getText().isEmpty() && tfrespo.getLength() > 3;
    }

    private boolean validnomevenement() {
        return !tfnom.getText().isEmpty() && tfnom.getLength() > 3;
    }

    private boolean validcatégorie() {
        return !tfcate.getText().isEmpty() && tfcate.getLength() > 3;
    }

    private boolean validlocalisation() {
        return !tfloca.getText().isEmpty() && tfloca.getLength() > 3;
    }

    private boolean validnombredeplaces() {
        return !tfnbrplace.getText().isEmpty() && tfnbrplace.getLength() > 1;
    }

    private boolean validtotalfrais() {
        return !tffrais.getText().isEmpty() && tffrais.getLength() > 1;
    }

    private void enterAction() {
        Pulse pulse = new Pulse(btnajoutevent);
        pulse.setDelay(Duration.millis(20));
        pulse.play();
        if (validnomresponsable() && validnomevenement()&&validcatégorie()&&validlocalisation()&& validnombredeplaces()&& validtotalfrais()) {
            insertRecord();
        } else {
            lbl_nomrespo.setVisible(true);
            lbl_nomevent.setVisible(true);
            lbl_cate.setVisible(true);
            lbl_loca.setVisible(true);
            lbl_nbrplaces.setVisible(true);
            lbl_totalfrais.setVisible(true);

        }
    }

    private void refreshTable() {
        ObservableList<evenements> List = getevenementsList();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            List.clear();
            Connection conn = getConnection();

            String query = "SELECT * FROM `evenements`";
            preparedStatement = conn.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                List.add(new evenements(
                        resultSet.getInt("id_event"),
                        resultSet.getString("nom_responsable"),
                        resultSet.getString("nom_event"),
                        resultSet.getDate("date_event"),
                        resultSet.getString("category_event"),
                        resultSet.getString("localisation_event"),
                        resultSet.getInt("nbr_places"),
                        resultSet.getInt("total_frais")
                ));
                tvevenements.setItems(List);

            }

        } catch (SQLException ex) {
            Logger.getLogger(EvenementsController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    PreparedStatement ps = null;

    public void showevenements() {
        ObservableList<evenements> list = getevenementsList();
        colidevent.setCellValueFactory(new PropertyValueFactory<>("id_event"));
        colrespo.setCellValueFactory(new PropertyValueFactory<>("nom_responsable"));
        colnom.setCellValueFactory(new PropertyValueFactory<>("nom_event"));
        coldate.setCellValueFactory(new PropertyValueFactory<>("date_event"));
        colcate.setCellValueFactory(new PropertyValueFactory<>("category_event"));
        colloca.setCellValueFactory(new PropertyValueFactory<>("localisation_event"));
        colnbrplaces.setCellValueFactory(new PropertyValueFactory<>("nbr_places"));
        coltotfrais.setCellValueFactory(new PropertyValueFactory<>("total_frais"));
        tvevenements.setItems(list);
        Callback<TableColumn<evenements, String>, TableCell<evenements, String>> cellFoctory = (TableColumn<evenements, String> param) -> {
            // make cell containing buttons
            final TableCell<evenements, String> cell = new TableCell<evenements, String>() {
                @Override
                public void updateItem(String item, boolean empty) {

                    evenements evenements = null;
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
                                    evenements evenements;
                                    evenements = tvevenements.getSelectionModel().getSelectedItem();
                                    String query = "DELETE FROM `evenements` WHERE id_event =" + evenements.getId_event();
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
                                evenements evenements = tvevenements.getSelectionModel().getSelectedItem();

                                tfrespo.setText(evenements.getNom_responsable());
                                tfnom.setText(evenements.getNom_event());
                                tfdate.setValue(evenements.getDate_event().toLocalDate());
                                tfcate.setText(evenements.getCategory_event());
                                tfloca.setText(evenements.getLocalisation_event());
                                tfnbrplace.setText("" + evenements.getNbr_places());
                                tffrais.setText("" + evenements.getTotal_frais());

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
        coleditevent.setCellFactory(cellFoctory);
        tvevenements.setItems(list);

    }
    //ajouter un evenement

    private void insertRecord() {

        String query = "INSERT INTO evenements(nom_responsable,nom_event,date_event,category_event,localisation_event,nbr_places,total_frais,image) VALUES('" + tfrespo.getText() + "','" + tfnom.getText() + "','" + tfdate.getValue() + "','" + tfcate.getText() + "','" + tfloca.getText() + "'," + tfnbrplace.getText() + "," + tffrais.getText() + ",'" + tfimage.getText() + "')";
        executeQuery(query);
        showevenements();
    }
    //modifier un evenement

    private void updateRecord() {
        String query = "UPDATE evenements SET nom_responsable = '" + tfrespo.getText() + "', nom_event = '" + tfnom.getText() + "', date_event ='" + tfdate.getValue() + "', category_event = '" + tfcate.getText() + "', localisation_event = '" + tfloca.getText() + "', nbr_places = '" + tfnbrplace.getText() + "', total_frais = '" + tffrais.getText() + "' WHERE id_event=" + tfidevent.getText() + "";
        executeQuery(query);
        showevenements();
    }
    //supprimer un evenement

    private void deleteRecord() {
        String query = "DELETE FROM evenements WHERE id_event=" + tfidevent.getText() + "";
        executeQuery(query);
        showevenements();
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

    @FXML
    private void actionperformed(ActionEvent event) {
        String s;

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.IMAGE", "jpg", "gif", "png");
        fileChooser.addChoosableFileFilter(filter);
        int result = fileChooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String path = selectedFile.toURI().toString();
            Image img = new Image(path);
            ImageView view = new ImageView(img);
            view.setFitHeight(80);
            view.setPreserveRatio(true);
            label.setGraphic(view);
            tfimage.setText(path);
            s = path;
        } else if (result == JFileChooser.CANCEL_OPTION) {
            System.out.println("No Data");
        }
    }

}
