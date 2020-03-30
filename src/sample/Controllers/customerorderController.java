package sample.Controllers;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.ToggleGroup;
import sample.Data.ItemDetails;
import sample.Data.orderList;
import sample.Utility.ReadCSV;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

// This is controller to handle operations
public class customerorderController implements Initializable {

    @FXML
    private ComboBox<String> foodID, BeveragesID;


    @FXML
    private Button BTenterData, BTchoiceDisplay, BTdisplayOrder,BTbill,BTPrepare,BTClear,BTquit;

    @FXML
    private TextField cusName, cusTable;

    @FXML
    private RadioButton rdBrkfast, rdLunch, rdDinner;

    @FXML
    private TableView tblDisplay;

    @FXML
    private ListView waitingListView, servingListView;

    @FXML
    private TableColumn col1, col2, col3, col4, col5, col6, col7;

    public ToggleGroup radioGroup = new ToggleGroup();

    private int OrderID = 0;
    ObservableList<ItemDetails> strList = FXCollections.observableArrayList();
    ObservableList<orderList> WaitingList = FXCollections.observableArrayList();
    ObservableList<orderList> ServingList = FXCollections.observableArrayList();
    public static final String DEST = "src/Bills/";


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        foodID.setDisable(true);
        BeveragesID.setDisable(true);
        BTPrepare.setDisable(true);
        BTbill.setDisable(true);
      
        rdBrkfast.setToggleGroup(radioGroup);
        rdLunch.setToggleGroup(radioGroup);
        rdDinner.setToggleGroup(radioGroup);

    }//close initialize


    //EnterData Button validation
    @FXML
    private void validateName(ActionEvent event) {
        int value;
      
        if (cusTable.getText().trim().isEmpty()) {
            value = 0;
        } else {
            value = Integer.parseInt(cusTable.getText());
        }
        if (event.getSource() == BTenterData && cusName.getText().trim().isEmpty()) {
            
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter customer name : ", ButtonType.CLOSE);
            alert.showAndWait();
          
        } else if (value <= 0 || value >= 9) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Enter the table number between 1 and 8 properly : ", ButtonType.CLOSE);
            alert.showAndWait();
           
        } else if (!rdBrkfast.isSelected() && !rdLunch.isSelected() && !rdDinner.isSelected()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "select Menu : ", ButtonType.CLOSE);
            alert.showAndWait();
           
        } else if (foodID.getSelectionModel().isEmpty() || BeveragesID.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Select Food and Beverages : ", ButtonType.CLOSE);
            alert.showAndWait();
           
        } else {
            RadioButton selectedRadioButton = (RadioButton) radioGroup.getSelectedToggle();
            String toogleGroupValue = selectedRadioButton.getText();
           
            ReadCSV rd = new ReadCSV();
            strList = rd.csvqueryLoadTableData(foodID.getSelectionModel().getSelectedItem(), BeveragesID.getSelectionModel().getSelectedItem());
            orderList ol = new orderList(cusName.getText().toString(), cusTable.getText().toString(), foodID.getSelectionModel().getSelectedItem(), BeveragesID.getSelectionModel().getSelectedItem());
            WaitingList.add(ol);
            if (strList != null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Order Placed Successfully ", ButtonType.CLOSE);
                alert.showAndWait();
               
                WaitingListLoad(WaitingList);
            }
        }
    }//close validate Name


    //Display Choice Button validation
    @FXML
    private void radioEventDisplay(ActionEvent event) {

        ObservableList<String> drFood = FXCollections.observableArrayList();
        ObservableList<String> drBeverage = FXCollections.observableArrayList();
        if (rdBrkfast.isSelected()) {
            loaddropDown("BreakFast");
        } else if (rdLunch.isSelected()) {
            loaddropDown("Lunch");
        } else if (rdDinner.isSelected()) {
            loaddropDown("Dinner");
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Select Menu : ", ButtonType.CLOSE);
            alert.showAndWait();
          

        }


    }

    //load data to table
    @FXML
    private void loadTableData(ActionEvent event) {

      
        if (!rdBrkfast.isSelected() && !rdLunch.isSelected() && !rdDinner.isSelected()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "select Menu : ", ButtonType.CLOSE);
            alert.showAndWait();
           
        } else if (event.getSource() == BTchoiceDisplay) {
            if (foodID.getSelectionModel().isEmpty() || BeveragesID.getSelectionModel().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Select Food and Beverages : ", ButtonType.CLOSE);
                alert.showAndWait();
                
            }
         
            else {
                ReadCSV rd = new ReadCSV();
                tblDisplay.getColumns().clear();
              
                strList = rd.csvqueryLoadTableData(foodID.getSelectionModel().getSelectedItem(), BeveragesID.getSelectionModel().getSelectedItem());
                tblDisplay.setEditable(true);


             
                TableColumn ItemNameCol = new TableColumn("Item Name");
                ItemNameCol.setMinWidth(200);
                ItemNameCol
                        .setCellValueFactory(new PropertyValueFactory<ItemDetails, String>(
                                "ItemName"));
                TableColumn EnergyCol = new TableColumn("Energy");
                EnergyCol.setMinWidth(200);
                EnergyCol
                        .setCellValueFactory(new PropertyValueFactory<ItemDetails, String>(
                                "Energy"));
                TableColumn ProtienCol = new TableColumn("Protien");
                ProtienCol.setMinWidth(200);
                ProtienCol
                        .setCellValueFactory(new PropertyValueFactory<ItemDetails, String>(
                                "Protien"));
                TableColumn CarbohydrateCol = new TableColumn("Carbohydrate");
                CarbohydrateCol.setMinWidth(200);
                CarbohydrateCol
                        .setCellValueFactory(new PropertyValueFactory<ItemDetails, String>(
                                "Carbohydrate"));
                TableColumn TotalFatCol = new TableColumn("TotalFat");
                TotalFatCol.setMinWidth(200);
                TotalFatCol
                        .setCellValueFactory(new PropertyValueFactory<ItemDetails, String>(
                                "TotalFat"));
                TableColumn FibreCol = new TableColumn("Fibre");
                FibreCol.setMinWidth(200);
                FibreCol
                        .setCellValueFactory(new PropertyValueFactory<ItemDetails, String>(
                                "Fibre"));
                TableColumn PriceCol = new TableColumn("Price");
                PriceCol.setMinWidth(200);
                PriceCol
                        .setCellValueFactory(new PropertyValueFactory<ItemDetails, String>(
                                "Price"));

            


                tblDisplay.setItems(strList);
                tblDisplay.setVisible(true);
                tblDisplay.getColumns().addAll(ItemNameCol, EnergyCol, ProtienCol, CarbohydrateCol, TotalFatCol, FibreCol, PriceCol);


            }
        }


    }


    private void loaddropDown(String mealType) {
        ObservableList<String> drFood = FXCollections.observableArrayList();
        ObservableList<String> drBeverage = FXCollections.observableArrayList();
        

        ReadCSV read = new ReadCSV();
        drFood = read.csvqueryDropDownList(mealType, "food");
        drBeverage = read.csvqueryDropDownList(mealType, "Beverage");
        foodID.setItems(drFood);
        BeveragesID.setItems(drBeverage);
        foodID.setDisable(false);
        BeveragesID.setDisable(false);
    }

    private void loadListView(ObservableList<ItemDetails> list) {
     
        String Order = cusName.getText().toString() + "|" + "Table:" + cusTable.getText().toString() + "|" + list.get(0).getItemName() + "&" + list.get(1).getItemName();
        
        waitingListView.getItems().add(Order);
        if(!waitingListView.getSelectionModel().isEmpty()) {
            BTPrepare.setDisable(false);
        }
    }

    @FXML
    private void loadListTransfer() {

        if(waitingListView.getSelectionModel().getSelectedItem()!=null)
        {
            BTPrepare.setDisable(false);
        }

        if(servingListView.getSelectionModel().getSelectedItem()!=null)
        {
            BTbill.setDisable(false);
        }

    }

    @FXML
    private void loadOrderTable(ActionEvent event) {

        if (event.getSource() == BTdisplayOrder) {
            tblDisplay.getColumns().clear();
            tblDisplay.setEditable(true);
            if (WaitingList != null) {
                TableColumn cusNameCol = new TableColumn("Customer Name");
                cusNameCol.setMinWidth(200);
                cusNameCol
                        .setCellValueFactory(new PropertyValueFactory<orderList, String>(
                                "cusName"));
                TableColumn TablenumCol = new TableColumn("Table No");
                TablenumCol.setMinWidth(200);
                TablenumCol
                        .setCellValueFactory(new PropertyValueFactory<orderList, String>(
                                "TableNum"));
                TableColumn FoodNameCol = new TableColumn("Food Name");
                FoodNameCol.setMinWidth(200);
                FoodNameCol
                        .setCellValueFactory(new PropertyValueFactory<orderList, String>(
                                "FoodName"));
                TableColumn BeverageNameCol = new TableColumn("Beverage Name");
                BeverageNameCol.setMinWidth(200);
                BeverageNameCol
                        .setCellValueFactory(new PropertyValueFactory<orderList, String>(
                                "BeverageName"));

                tblDisplay.setItems(WaitingList);
                tblDisplay.setVisible(true);
                tblDisplay.getColumns().addAll(cusNameCol, TablenumCol, FoodNameCol, BeverageNameCol);

            }


        }
    }//closeOrdertable

    @FXML
    private void PrepareOrder(ActionEvent event) {

        if(event.getSource()==BTPrepare && !WaitingList.isEmpty() && !waitingListView.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Order");
            alert.setContentText("You want to Prepare this order ?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
              
                    ServingList.add(WaitingList.get(waitingListView.getSelectionModel().getSelectedIndex()));
                    WaitingList.remove(waitingListView.getSelectionModel().getSelectedIndex());
                    WaitingListLoad(WaitingList);

                    ServingListLoad(ServingList);

            } else {
               

            }


        }

    }//PrepareOrder

    private void WaitingListLoad(ObservableList<orderList> list)
    {
        waitingListView.getItems().clear();

        for (int i = 0; i < list.size(); i++) {

            String Order = list.get(i).getCusName()+ "|" + "Table:" + list.get(i).getTableNum()+ "|" +list.get(i).getFoodName() + "&" + list.get(i).getBeverageName();

            waitingListView.getItems().add(Order);
           
        }


    }

    private void ServingListLoad(ObservableList<orderList> list)
    {

        servingListView.getItems().clear();

        for (int i = 0; i < list.size(); i++) {
            String Order = list.get(i).getCusName()+ "|" + "Table:" + list.get(i).getTableNum()+ "|" +list.get(i).getFoodName() + "&" + list.get(i).getBeverageName();
         
            servingListView.getItems().add(Order);
           
        }
    }

    // to generate bill
    @FXML
    private void GenerateBill(ActionEvent event)
    {


        if(event.getSource()==BTbill && !ServingList.isEmpty() && !servingListView.getSelectionModel().isEmpty() )
        {
            ObservableList<ItemDetails> items = FXCollections.observableArrayList();
            ReadCSV rd=new ReadCSV();
            items=rd.csvqueryLoadTableData(ServingList.get(servingListView.getSelectionModel().getSelectedIndex()).getFoodName(),ServingList.get(servingListView.getSelectionModel().getSelectedIndex()).getBeverageName());

            Document document = new Document();
            try
            {
                PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("src/sample/Bills/Bill.pdf"));
                document.open();
                document.add(new Paragraph("Invoice for the User: "+ServingList.get(servingListView.getSelectionModel().getSelectedIndex()).getCusName()));
                document.add(new Paragraph("Table Number: "+ServingList.get(servingListView.getSelectionModel().getSelectedIndex()).getTableNum()));
                PdfPTable table = new PdfPTable(7);
                PdfPCell cell1 = new PdfPCell(new Paragraph("Item Name"));
                PdfPCell cell2 = new PdfPCell(new Paragraph("Energy"));
                PdfPCell cell3 = new PdfPCell(new Paragraph("Protien"));
                PdfPCell cell4 = new PdfPCell(new Paragraph("CarboHydrates"));
                PdfPCell cell5 = new PdfPCell(new Paragraph("Total Fat"));
                PdfPCell cell6 = new PdfPCell(new Paragraph("Fibre"));
                PdfPCell cell7 = new PdfPCell(new Paragraph("Price"));


                table.addCell(cell1);
                table.addCell(cell2);
                table.addCell(cell3);
                table.addCell(cell4);
                table.addCell(cell5);
                table.addCell(cell6);
                table.addCell(cell7);
                for(int i=0;i<items.size();i++)
                {
                    table.addCell(items.get(i).getItemName());
                    table.addCell(items.get(i).getEnergy());
                    table.addCell(items.get(i).getProtien());
                    table.addCell(items.get(i).getCarbohydrate());
                    table.addCell(items.get(i).getTotalFat());
                    table.addCell(items.get(i).getFibre());
                    table.addCell(items.get(i).getPrice());

                }


                document.add(table);
                document.add(new Paragraph("Thank You :)"));
                document.close();
                writer.close();
                File f = new File("src/sample/Bills/Bill.pdf");
                if(f.exists()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Bill Generated under Bills Folder ", ButtonType.CLOSE);
                    alert.showAndWait();
                   
                    ServingList.remove(servingListView.getSelectionModel().getSelectedIndex());
                   
                    ServingListLoad(ServingList);
                }
            } catch (DocumentException e)
            {
            } catch (FileNotFoundException ex) {
                Logger.getLogger(customerorderController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//close Generate Bill

    @FXML
    private void clearDisplay(ActionEvent event)
    {
        if(event.getSource()==BTClear)
        {
            cusName.clear();
            cusTable.clear();
            rdBrkfast.setSelected(false);
            rdLunch.setSelected(false);
            rdDinner.setSelected(false);
            foodID.setDisable(true);
            BeveragesID.setDisable(true);

        }
    }

    @FXML
    private void clearDisplayDelete(ActionEvent event)
    {
        if(event.getSource()==BTClear)
        {
            cusName.clear();
            cusTable.clear();
            rdBrkfast.setSelected(false);
            rdLunch.setSelected(false);
            rdDinner.setSelected(false);
            foodID.setDisable(true);
            BeveragesID.setDisable(true);
            strList.clear();
            WaitingList.clear();
            ServingList.clear();
            BTPrepare.setDisable(false);
            BTbill.setDisable(false);


        }
    }


    @FXML
    private void QuitApp(ActionEvent event)
    {
        if(event.getSource()==BTquit)
        {
            Platform.exit();
        }
    }



}
