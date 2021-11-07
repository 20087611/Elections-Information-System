package sample;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable{
    
    @FXML
    public TableView<Election> electionTable;
    @FXML
    public TableView<Politician> politicianTable;
    @FXML
    public TableView<Candidate> candidateTable;
    @FXML
    public TableView<Politician> searchPolT;
    @FXML
    public TableView<Election> searchElecT;
    
    @FXML
    public Tab politicianTab;
    @FXML
    public Tab electionTab;
    @FXML
    public Tab candidateTab;
    @FXML
    public Tab searchTabPol;

    @FXML
    public TextField politicianParty;
    @FXML
    public TextField imageUrl;
    @FXML
    public TextField politicianName;
    @FXML
    public TextField politicianCounty;
    @FXML
    public TableColumn<Politician, String> politicianNameT;
    @FXML
    public TableColumn<Politician, String> politicianPartyT;
    @FXML
    public TableColumn<Politician, String> dateOfBirthT;
    @FXML
    public TableColumn<Politician, String> countyLocationT;
    @FXML
    public TableColumn<Politician, String> imageUrlT;
    @FXML
    public TableColumn<Election, String> electionTypeT;
    @FXML
    public TableColumn<Election, String> countyT;
    @FXML
    public TableColumn<Election, String> electionYearT;
    @FXML
    public TableColumn<Election, String> numOfSeatsT;
    @FXML
    public TextField electionType;
    @FXML
    public TextField countyLocation;
    @FXML
    public TextField yearOfElection;
    @FXML
    public TextField numberOfSeats;
    @FXML
    public TextField dateOfBirth;
    @FXML
    public TableColumn<Candidate, String> candidateT;
    @FXML
    public TableColumn<Candidate, String> electionT;
    @FXML
    public TableColumn<Candidate, String> numberOfVotesT;
    @FXML
    public TextField numberOfVotes;
    @FXML
    public ChoiceBox electionList;
    @FXML
    public ChoiceBox updateElectionList;
    @FXML
    public ChoiceBox politicianList;
    @FXML
    public ChoiceBox updatePoliticianList;
    @FXML
    public ChoiceBox candidateList;
    @FXML
    public ImageView imgView;
    @FXML
    public TextField searchElecTab;
    @FXML
    public TextField searchPolTab;
    @FXML
    public TableColumn<Election, String> sTypeT;
    @FXML
    public TableColumn<Election, String> sLocationT;
    @FXML
    public TableColumn<Election, String> sYearT;
    @FXML
    public TableColumn<Election, String> sNOST;
    @FXML
    public TableColumn<Politician, String> sNameT;
    @FXML
    public TableColumn<Politician, String> sPartyT;
    @FXML
    public TableColumn<Politician, String> sDOBT;
    @FXML
    public TableColumn<Politician, String> sCountyT;
    @FXML
    public TableColumn<Politician, String> sURLT;
    @FXML
    public RadioButton searchByName;
    @FXML
    public RadioButton searchByParty;
    @FXML
    public RadioButton searchByHCounty;
    @FXML
    public RadioButton searchByType;
    @FXML
    public RadioButton searchByYear;
    @FXML
    public RadioButton searchByCounty;
    @FXML
    public ToggleGroup polGroup;
    @FXML
    public ToggleGroup elecGroup;

    MyList<Politician> myPoliticianList = new MyList<Politician>();
    MyList<Election> myElectionList = new MyList<Election>();
    MyList<Candidate> myCandidateList = new MyList<Candidate>();

    // Loads the election table and allows for multiple selection.
    //reference： https://blog.csdn.net/dnc8371/article/details/107258216

    public void loadETable() {
        electionTable.getItems().clear();
        electionTypeT.setCellValueFactory(new PropertyValueFactory<Election, String>("electionType"));
        countyT.setCellValueFactory(new PropertyValueFactory<Election, String>("countyLocation"));
        electionYearT.setCellValueFactory(new PropertyValueFactory<Election, String>("yearOfElection"));
        numOfSeatsT.setCellValueFactory(new PropertyValueFactory<Election, String>("numberOfSeats"));

        LinkedNode<Election> electionNode = myElectionList.head;
        while (electionNode != null) {
            electionTable.getItems().add(electionNode.getContents());
            electionNode = electionNode.next;
        }
        electionTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }
    // Loads the politician table and allows for multiple selection.
    public void loadPTable() {
        politicianTable.getItems().clear();
        politicianNameT.setCellValueFactory(new PropertyValueFactory<Politician, String>("fullName"));
        politicianPartyT.setCellValueFactory(new PropertyValueFactory<Politician, String>("politicalParty"));
        dateOfBirthT.setCellValueFactory(new PropertyValueFactory<Politician, String>("dateOfBirth"));
        countyLocationT.setCellValueFactory(new PropertyValueFactory<Politician, String>("homeCounty"));
        imageUrlT.setCellValueFactory(new PropertyValueFactory<Politician, String>("image"));

        LinkedNode<Politician> politicianNode = myPoliticianList.head;
        while (politicianNode != null) {
            politicianTable.getItems().add(politicianNode.getContents());
            politicianNode = politicianNode.next;
        }
        politicianTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public void loadCTable(Election election1) {
        candidateTable.getItems().clear();
        candidateT.setCellValueFactory(new PropertyValueFactory<Candidate, String>("candidateName"));
        electionT.setCellValueFactory(new PropertyValueFactory<Candidate, String>("electionName"));
        numberOfVotesT.setCellValueFactory(new PropertyValueFactory<Candidate, String>("numOfVotes"));

        LinkedNode<Candidate> candidateNode = election1.getCandidateList().head;
        while (candidateNode != null) {
            candidateTable.getItems().add(candidateNode.getContents());
            candidateNode = candidateNode.next;
        }
        candidateTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public void loadAllCandidateTables() {
        candidateTable.getItems().clear();
        LinkedNode<Election> electionNode = myElectionList.head;
        LinkedNode<Candidate> candidateNode = electionNode.getContents().getCandidateList().head;
        candidateT.setCellValueFactory(new PropertyValueFactory<Candidate, String>("candidateName"));
        electionT.setCellValueFactory(new PropertyValueFactory<Candidate, String>("electionName"));
        numberOfVotesT.setCellValueFactory(new PropertyValueFactory<Candidate, String>("numOfVotes"));
        while(electionNode != null) {
            while (candidateNode != null) {
                candidateTable.getItems().add(candidateNode.getContents());
                candidateNode = candidateNode.next;
            }
            electionNode = electionNode.next;
        }
    }

    //Loads Election choice box in Candidate tab
    public void loadElectionChoiceBox() {
        LinkedNode<Election> electionNode = myElectionList.head;
        while (electionNode != null) {
            electionList.getItems().add(electionNode.getContents());
            electionNode = electionNode.next;
        }
    }

    //Loads Politician choice box in Candidate tab
    public void loadPoliticianChoiceBox() {
        LinkedNode<Politician> politicianNode = myPoliticianList.head;
        while (politicianNode != null) {
            politicianList.getItems().add(politicianNode.getContents());
            politicianNode = politicianNode.next;
        }
    }

    public void loadCandidateChoiceBox() {
        LinkedNode<Candidate> candidateNode = myCandidateList.head;
        while (candidateNode != null) {
            candidateList.getItems().add(candidateNode.getContents());
            candidateNode = candidateNode.next;
        }
    }

    //Sets table fields to be editable.
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        politicianTable.setEditable(true);
        politicianNameT.setCellFactory(TextFieldTableCell.forTableColumn());
        politicianPartyT.setCellFactory(TextFieldTableCell.forTableColumn());
        dateOfBirthT.setCellFactory(TextFieldTableCell.forTableColumn());
        countyLocationT.setCellFactory(TextFieldTableCell.forTableColumn());
        imageUrlT.setCellFactory(TextFieldTableCell.forTableColumn());

        electionTable.setEditable(true);
        electionTypeT.setCellFactory(TextFieldTableCell.forTableColumn());
        countyT.setCellFactory(TextFieldTableCell.forTableColumn());
        electionYearT.setCellFactory(TextFieldTableCell.forTableColumn());
        numOfSeatsT.setCellFactory(TextFieldTableCell.forTableColumn());

        candidateTable.setEditable(true);
        candidateT.setCellFactory(TextFieldTableCell.forTableColumn());
        electionT.setCellFactory(TextFieldTableCell.forTableColumn());
        numberOfVotesT.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    /********************
     * Politician Method*
     ********************/

    //Adds politician
    public void addPolitician(ActionEvent actionEvent) {
        Politician p = new Politician(politicianName.getText(), politicianParty.getText(), dateOfBirth.getText(), politicianCounty.getText(), imageUrl.getText());
        myPoliticianList.addElement(p);
        System.out.println(myPoliticianList.listElementContents());
        loadPTable();
        loadPoliticianChoiceBox();
        saveToFile();
    }

    /**
     Edits method
     */
    public void editPoliticianName(TableColumn.CellEditEvent editedCell) {
        Politician politician1 = politicianTable.getSelectionModel().getSelectedItem();
        politician1.setFullName(editedCell.getNewValue().toString());
        saveToFile();
    }

    public void editPoliticianParty(TableColumn.CellEditEvent editedCell) {
        Politician politician1 = politicianTable.getSelectionModel().getSelectedItem();
        politician1.setPoliticalParty(editedCell.getNewValue().toString());
        saveToFile();
    }

    public void editPoliticianDOB(TableColumn.CellEditEvent editedCell) {
        Politician politician1 = politicianTable.getSelectionModel().getSelectedItem();
        politician1.setDateOfBirth(editedCell.getNewValue().toString());
        saveToFile();

    }

    public void editPoliticianCounty(TableColumn.CellEditEvent editedCell) {
        Politician politician1 = politicianTable.getSelectionModel().getSelectedItem();
        politician1.setHomeCounty(editedCell.getNewValue().toString());
        saveToFile();
    }

    public void editPoliticianImage(TableColumn.CellEditEvent editedCell) {
        Politician politician1 = politicianTable.getSelectionModel().getSelectedItem();
        politician1.setImage(editedCell.getNewValue().toString());
        saveToFile();
    }

    /**
     * Removes
     */
    public void removePolitician(ActionEvent actionEvent){
        myPoliticianList.deleteElement(politicianTable.getSelectionModel().getSelectedIndex());
        saveToFile();
        loadPTable();
    }

    /********************
     * Election   Method*
     ********************/

    public void addElection(ActionEvent actionEvent) {
        Election e = new Election(electionType.getText(), countyLocation.getText(), yearOfElection.getText(), numberOfSeats.getText());
        myElectionList.addElement(e);
        System.out.println(myElectionList.listElementContents());
        loadETable();
        electionTab.setDisable(false);
        loadElectionChoiceBox();
        saveToFile();
    }

    /**
     Edits method
     */
    public void editElectionName(TableColumn.CellEditEvent editedCell) {
        Election election1 = electionTable.getSelectionModel().getSelectedItem();
        election1.setElectionType(editedCell.getNewValue().toString());
        saveToFile();
    }

    public void editElectionCounty(TableColumn.CellEditEvent editedCell) {
        Election election1 = electionTable.getSelectionModel().getSelectedItem();
        election1.setCountyLocation(editedCell.getNewValue().toString());
        saveToFile();
    }

    public void editElectionYear(TableColumn.CellEditEvent editedCell) {
        Election election1 = electionTable.getSelectionModel().getSelectedItem();
        election1.setYearOfElection(editedCell.getNewValue().toString());
        saveToFile();
    }

    public void editElectionNoS(TableColumn.CellEditEvent editedCell) {
        Election election1 = electionTable.getSelectionModel().getSelectedItem();
        election1.setNumberOfSeats(editedCell.getNewValue().toString());
        saveToFile();
    }


    public void removeElection(ActionEvent actionEvent){
        myElectionList.deleteElement(electionTable.getSelectionModel().getSelectedIndex());
        saveToFile();
        loadETable();
    }

    /********************
     * Candidate  Method*
     ********************/
    // Adds new candidate
    public void addCandidate(ActionEvent actionEvent) {
        Candidate c;

        LinkedNode<Election> electionNode = myElectionList.head;
        LinkedNode<Candidate> candidateNode = electionNode.getContents().getCandidateList().head;

        while (electionNode != null) {
            if (electionNode.getContents().toString().equals(electionList.getValue().toString())) {
                Election electionSelected = electionNode.getContents();
                c = new Candidate(politicianList.getValue().toString(), electionList.getValue().toString(), numberOfVotes.getText());
                electionSelected.getCandidateList().addElement(c);
                while (candidateNode != null) {
                    System.out.println(candidateNode.getContents());
                    candidateNode = candidateNode.next;
                }
                loadCTable(electionSelected);
                electionNode = electionNode.next;
            }
            saveToFile();
        }
    }

    /**
     * Removes
     */
    public void removeCandidate(ActionEvent actionEvent) {
        myElectionList.head.getContents().getCandidateList().deleteElement(candidateTable.getSelectionModel().getSelectedIndex());
        saveToFile();
        loadAllCandidateTables();
    }

    /********************
     * Search  Method*
     ********************/

    public void searchForElection(ActionEvent actionEvent) {
        searchElecT.getItems().clear();
        sTypeT.setCellValueFactory(new PropertyValueFactory<Election, String>("electionType"));
        sLocationT.setCellValueFactory(new PropertyValueFactory<Election, String>("countyLocation"));
        sYearT.setCellValueFactory(new PropertyValueFactory<Election, String>("yearOfElection"));
        sNOST.setCellValueFactory(new PropertyValueFactory<Election, String>("numberOfSeats"));
        String matchedElection = searchElecTab.getText();
        if(searchByType.isSelected() == true) {
            LinkedNode<Election> electionNode = myElectionList.head;
            while(electionNode != null) {
                if(electionNode.getContents().getElectionType().contains(matchedElection)) {
                    searchElecT.getItems().add(electionNode.getContents());
                }
                electionNode = electionNode.next;
            }
        }
        else if(searchByYear.isSelected() == true) {
            LinkedNode<Election> electionNode = myElectionList.head;
            while(electionNode != null) {
                if(electionNode.getContents().getYearOfElection().contains(matchedElection)) {
                    searchElecT.getItems().add(electionNode.getContents());
                }
                electionNode = electionNode.next;
            }
        }
        else if(searchByCounty.isSelected() == true) {
            LinkedNode<Election> electionNode = myElectionList.head;
            while(electionNode != null) {
                if(electionNode.getContents().getCountyLocation().contains(matchedElection)) {
                    searchElecT.getItems().add(electionNode.getContents());
                }
                electionNode = electionNode.next;
            }
        }
        else {
            System.out.println("No Data is available");
        }
    }
    
    public void searchForPolitician(ActionEvent actionEvent) {
        searchPolT.getItems().clear();
        sNameT.setCellValueFactory(new PropertyValueFactory<Politician, String>("fullName"));
        sPartyT.setCellValueFactory(new PropertyValueFactory<Politician, String>("politicalParty"));
        sDOBT.setCellValueFactory(new PropertyValueFactory<Politician, String>("dateOfBirth"));
        sCountyT.setCellValueFactory(new PropertyValueFactory<Politician, String>("homeCounty"));
        String matchedPol = searchPolTab.getText();
        if(searchByName.isSelected() == true) {
            LinkedNode<Politician> politicianNode = myPoliticianList.head;
            while(politicianNode != null) {
                if(politicianNode.getContents().getFullName().contains(matchedPol)) {
                    searchPolT.getItems().add(politicianNode.getContents());
                }
                politicianNode = politicianNode.next;
            }
        }
        else if(searchByParty.isSelected() == true) {
            LinkedNode<Politician> politicianNode = myPoliticianList.head;
            while(politicianNode != null) {
                if(politicianNode.getContents().getPoliticalParty().contains(matchedPol)) {
                    searchPolT.getItems().add(politicianNode.getContents());
                }
                politicianNode = politicianNode.next;
            }
        }
        else if(searchByHCounty.isSelected() == true) {
            LinkedNode<Politician> politicianNode = myPoliticianList.head;
            while(politicianNode != null) {
                if(politicianNode.getContents().getHomeCounty().contains(matchedPol)) {
                    searchPolT.getItems().add(politicianNode.getContents());
                }
                politicianNode = politicianNode.next;
            }
        }
        else {
            System.out.println("No Data is available");
        }
    }



    /********************
     * Other  Method*
     ********************/
    //display an image from URL
    public void showImg(){
        String imageSource = politicianTable.getSelectionModel().getSelectedItem().getImage();
        Image image = new Image(imageSource);
        imgView.setImage(image);
    }

    //Can call this method to get auto-save function
    public void saveToFile()
    {
        try {
            save();
        } catch (Exception e) {
            System.err.println("Error writing to file: " + e);
        }
    }

    //Saves the system
    public void save() throws Exception {
        XStream xstream = new XStream(new DomDriver());
        ObjectOutputStream polout = xstream.createObjectOutputStream(new FileWriter("PoliticianData.xml"));
        ObjectOutputStream elecout = xstream.createObjectOutputStream(new FileWriter("ElectionData.xml"));
        polout.writeObject(myPoliticianList);
        elecout.writeObject(myElectionList);
        polout.close();
        elecout.close();
    }

    public void load(ActionEvent actionEvent) throws Exception {
        XStream xstream = new XStream(new DomDriver());
        ObjectInputStream pol = xstream.createObjectInputStream(new FileReader("PoliticianData.xml"));
        ObjectInputStream elec = xstream.createObjectInputStream(new FileReader("ElectionData.xml"));
        myPoliticianList = (MyList<Politician>) pol.readObject();
        myElectionList = (MyList<Election>) elec.readObject();
        pol.close();
        elec.close();
        //Enables tabs
        politicianTab.setDisable(false);
        electionTab.setDisable(false);
        candidateTab.setDisable(false);
        searchTabPol.setDisable(false);
        //Reloads tables and choice-boxes
        loadPoliticianChoiceBox();
        loadPTable();
        loadElectionChoiceBox();
        loadETable();
        loadCandidateChoiceBox();
        loadAllCandidateTables();
    }

    public void quit(){
        System.exit(0);
    }

}