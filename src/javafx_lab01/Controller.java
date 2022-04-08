package javafx_lab01;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class Controller implements Initializable {
    //khai báo text
    public TextField inputName, inputPhone, txtSearchName;
    public Text msgE_S, msgSearch;
    //khai báo table view cho controller
    public TableView<PhoneNumber> tbViewPhone;
    public TableColumn<PhoneNumber, String> outName;
    public TableColumn<PhoneNumber, String> outPhone;
    public TableColumn<PhoneNumber, Button> cEdit;
    public TableColumn<PhoneNumber, Button> cDelete;

    public PhoneNumber phoneNumber;

    //set data: lấy dữ liệu từ output đưa ngược lại cho input để edit
    public void setData(PhoneNumber pn) {
        this.phoneNumber = pn;
        inputName.setText(pn.getName());
        inputPhone.setText(pn.getPhone().toString());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        outName.setCellValueFactory(new PropertyValueFactory<PhoneNumber, String>("name")); //...<>("value")
        outPhone.setCellValueFactory(new PropertyValueFactory<PhoneNumber, String>("phone"));
        cEdit.setCellValueFactory(new PropertyValueFactory<PhoneNumber, Button>("edit"));
        cDelete.setCellValueFactory(new PropertyValueFactory<PhoneNumber, Button>("delete"));

        tbViewPhone.setItems(Main.phonesList);
    }

    public void addPhoneList() {
        try {
            if (inputName.getText().isEmpty() || inputPhone.getText().isEmpty()) {  //isEmpty() <=> == ""
                throw new Exception("Please enter full contact information!");
            }

            PhoneNumber p = new PhoneNumber(inputName.getText(), inputPhone.getText());  //add contact

            //TH1: trùng tên
            for (PhoneNumber pn : Main.phonesList) {
                if (pn.getName().equals(inputName.getText())) {    //nếu danh bạ đã có tên hoặc trùng tên
                    for (String s : pn.phone) {   //tiếp tục duyệt mảng ktra phone
                        if (s.equals(inputPhone.getText())) {    //nếu inputPhone trùng với outputPhone thì return và dừng lại
                            msgE_S.setText("This phone number has been added to the contact, please check again.");
                            msgE_S.setFill(Paint.valueOf("RED"));
                            msgE_S.setVisible(true);
                            return;
                        }
                    }
                    pn.phone.add(inputPhone.getText());     //nếu phone không trùng thì add (trùng tên nhưng add thêm phone vào mảng)
                    msgE_S.setText("Phone number has been added to contact: " + pn.getName());
                    msgE_S.setFill(Paint.valueOf("BLUE"));
                    msgE_S.setVisible(true);
                    return;
                }
                //TH2: nếu tên không trùng thì nhảy vào đây, tiếp tục kiểm tra phone nhập vào
                for (String s : pn.phone) {   //duyệt mảng ktra phone
                    if (s.equals(inputPhone.getText())) {    //nếu inputPhone trùng với outputPhone thì return và dừng lại
                        msgE_S.setText("This phone number has been added to the contact, please check again.");
                        msgE_S.setFill(Paint.valueOf("RED"));
                        msgE_S.setVisible(true);
                        return;
                    }
                }
            }

            //nếu mà input rỗng <=> người dùng nhập vào thì add
            if (this.phoneNumber == null) {   //nếu mà person này rỗng (input không có dữ liệu)
                Main.phonesList.add(p);   //xuất ra list

                msgE_S.setFill(Paint.valueOf("BLUE"));
                msgE_S.setText("Add Contact Success!");
                msgE_S.setVisible(true);

            } else { //input đã được setData từ output thì edit
                try {
                    if (inputName.getText().isEmpty() || inputPhone.getText().isEmpty()) {
                        throw new Exception("Please enter full contact information!");
                    }
                    int i = Main.phonesList.indexOf(this.phoneNumber);
                    Main.phonesList.set(i, p);

                    msgE_S.setVisible(true);
                    msgE_S.setFill(Paint.valueOf("BLUE"));
                    msgE_S.setText("Edit successful");
                } catch (Exception e) {
                    msgE_S.setVisible(true);
                    msgE_S.setFill(Paint.valueOf("RED"));
                    msgE_S.setText(e.getMessage());
                }
            }

            renderList();   //reset input

        } catch (Exception e) {
            msgE_S.setVisible(true);
            msgE_S.setFill(Paint.valueOf("RED"));
            msgE_S.setText(e.getMessage());
        }

    }

    public void search() {
        try {
            String s = txtSearchName.getText();
            if (txtSearchName.getText().isEmpty()) {
                throw new Exception("Please enter the name of the contact you are looking for");
            }

            List<PhoneNumber> kq = Main.phonesList.stream()
                    .filter(item -> item.getName().contains(s))
                    .collect(Collectors.toList());

            ObservableList result = FXCollections.observableArrayList();  //tạo mảng result không liên quan dữ liệu gốc
            result.addAll(kq);

            if (kq.isEmpty()){
                msgSearch.setVisible(true);
                msgSearch.setFill(Paint.valueOf("RED"));
                msgSearch.setText("No contacts found whose name contains these characters: " + s);
            } else {
                msgSearch.setVisible(true);
                msgSearch.setFill(Paint.valueOf("BLUE"));
                msgSearch.setText("Found " + kq.size() + " contacts whose names contain characters: " + s);
            }

            tbViewPhone.setItems(result);
        } catch (Exception e) {
            msgSearch.setVisible(true);
            msgSearch.setFill(Paint.valueOf("RED"));
            msgSearch.setText(e.getMessage());
        }

    }

    private void renderList() {
        inputName.setText("");
        inputPhone.setText("");
    }

}
