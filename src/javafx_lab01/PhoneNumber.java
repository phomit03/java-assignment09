package javafx_lab01;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import javax.swing.*;
import java.util.ArrayList;

public class PhoneNumber {
    public String name;
    public ArrayList<String> phone = new ArrayList<String>();
    public Button edit;
    public Button delete;
    public Text msgE_S;

    //contructor
    public PhoneNumber(){
    }
    public PhoneNumber(String name, String phone) {
        this.name = name;
        this.phone.add(phone);
        this.edit = new Button("Edit");
        this.delete = new Button("Delete");

        //sự kiện edit
        this.edit.setOnAction(event -> {    //đặt onAction cho nút edit
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("listUserPhone.fxml"));
                Parent root = loader.load();
                //in ra giao diện
                Main.rootStage.setScene(new Scene(root, 800, 800));

                Controller c = loader.getController(); //gọi controller gắn kèm giao diện
                c.setData(this);   //lấy dữ liệu đã nhập từ controller

//                msgE_S.setVisible(true);
//                msgE_S.setFill(Paint.valueOf("BLUE"));
//                msgE_S.setText("Update Success!");

            } catch (Exception e){
                msgE_S.setVisible(true);
                msgE_S.setFill(Paint.valueOf("RED"));
                msgE_S.setText(e.getMessage());
            }
        });


        //sự kiện delete
        this.delete.setOnAction(event -> {    //đặt onAction cho nút delete
            try{
                //popup confirm
                int select = JOptionPane.showOptionDialog(null,
                        "Do you really want to delete this contact?",
                        "Delete", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (select == 0) {
                    //lấy mảng từ Main sau đó thực hiện lệnh remove(object)
                    Main.phonesList.remove(this); //this: object là nó luôn
                }
            } catch (Exception e){
                e.getMessage();
            }
        });
    }

    //g&s
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getPhone() {
        return phone;
    }

    public void setPhone(ArrayList<String> phone) {
        this.phone = phone;
    }

    public Button getEdit() {
        return edit;
    }

    public void setEdit(Button edit) {
        this.edit = edit;
    }

    public Button getDelete() {
        return delete;
    }

    public void setDelete(Button delete) {
        this.delete = delete;
    }
}
