package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.controllers.DataSession;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

/**
 * Created by dima on 24.03.15.
 */
public class StartController implements Initializable {
    String basname;
    int indexlist;
    DataSession dataSession;
    private Connection connection;
    ObservableList base_list, user_list;

    @FXML
    public static ListView baselist;
    @FXML
    public static Button baseenter, adminenter, baseedit, baseadd, basedrop, exit, addcancekbutton, addokbutton,
            userentercancel;
    @FXML
    TextField nameTextField, adresTextField, userTextField, baseTextField;
    @FXML
    PasswordField passwdTextField;

    @FXML
    public static ComboBox userchoicer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        assert baselist != null;
        assert baseenter != null;
        assert baseedit != null;
        assert baseadd != null;
        assert basedrop != null;
        assert exit != null;
        assert addcancekbutton != null;
        assert addokbutton != null;
        assert nameTextField != null;
        assert adresTextField != null;
        assert userTextField != null;
        assert baseTextField != null;
        assert passwdTextField != null;
        assert adminenter !=null;
        assert userchoicer != null;
        assert userentercancel != null;

        base_list = FXCollections.observableArrayList();
        user_list = FXCollections.observableArrayList();

        connect();
    }

    public void enter() throws Exception{
        Stage stage = (Stage) baseenter.getScene().getWindow();
        stage.close();

        Stage userenterstage = new Stage();
        userenter(userenterstage);
        getUserList();
        userchoicer.setItems(user_list);
    }
    public void admin() {
    }
    public void edit() {

    }

    public void add() throws Exception {
        Stage addwindow = new Stage();
        addbaseWindow(addwindow);
    }
    public void drop() {
        System.out.println(DataSession.sqlpath);
        if (baselist.getSelectionModel().getSelectedItem() != null) {
            try {
                Class.forName("org.sqlite.JDBC");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                connection = DriverManager.getConnection(dataSession.sqlpath);
                connection.setAutoCommit(false);
                String prepared = ("DELETE FROM bases WHERE name = '" + basname + "';");
                PreparedStatement preparedStatement = connection.prepareStatement(prepared);
                preparedStatement.executeUpdate();
                connection.commit();
                base_list.remove(basname);
                baselist.setItems(base_list);
                basedrop.setDisable(true);


            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Problems with connecting to the database!");
            }
        } else {
            basedrop.setDisable(true);
        }
    }
    public void exit() {
        System.exit(1);
    }

    public void addcancel() throws Exception{
        Stage stage = (Stage) addcancekbutton.getScene().getWindow();
        stage.close();
    }
    public void addok(){
        if (nameTextField.getText().equals("") || adresTextField.getText().equals("") || baseTextField.getText().equals("") ||
                userTextField.getText().equals("") || passwdTextField.getText().equals("")) {
            System.out.println("Заполнить все поля");
        } else {
            try {
                Class.forName("org.sqlite.JDBC");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                connection = DriverManager.getConnection(dataSession.sqlpath);
                connection.setAutoCommit(false);
                String prepared = "INSERT INTO bases (name, addres, base, user, password) values ('" +
                        nameTextField.getText().toString() + "','" + adresTextField.getText().toString() + "','" +
                        baseTextField.getText().toString() + "','" + userTextField.getText().toString() + "','" +
                        passwdTextField.getText().toString() +
                        "');";
                PreparedStatement preparedStatement = connection.prepareStatement(prepared);
                preparedStatement.executeUpdate();
                connection.commit();
                base_list.addAll(nameTextField.getText().toString());
                baselist.setItems(base_list);
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Problems with connecting to the database!");
            }

            Stage stage = (Stage) addokbutton.getScene().getWindow();
            stage.close();
        }

    }

    public void baselistmouseclick() {

        if (baselist.getSelectionModel().getSelectedItem() != null){
            baseenter.setDisable(false);
            basedrop.setDisable(false);
            basname = baselist.getSelectionModel().getSelectedItem().toString();
            indexlist = baselist.getSelectionModel().getSelectedIndex();
            System.out.println(basname);
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(dataSession.sqlpath);
            Statement st = connection.createStatement();
            ResultSet rs_base = st.executeQuery("select * from bases where name="+"'"+basname+"'");
            while(rs_base.next()) {
                System.out.println(rs_base.getString("name"));
                dataSession.url = "jdbc:mysql://" + rs_base.getString("addres")+"/"+rs_base.getString("base");
                dataSession.user = rs_base.getString("user");
                dataSession.password = rs_base.getString("password");
                System.out.println("jdbc:mysql://" + rs_base.getString("addres")+"/"+rs_base.getString("base"));
                System.out.println(dataSession.user);
                System.out.println(dataSession.password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Problems with connecting to the database!");
        } } else {
            baseenter.setDisable(true);
        }
    }

    public void userchoice() {

    }

    public void userentercancelAction(){
        Stage stage = (Stage) userentercancel.getScene().getWindow();
        stage.close();
    }
    public void userenteroklAction() throws Exception{
        Stage stage = (Stage) userentercancel.getScene().getWindow();
        stage.close();

        Stage addwindow = new Stage();
        startMainSale(addwindow);
    }

    public void connect() {

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(dataSession.sqlpath);
            Statement st = connection.createStatement();
            ResultSet rs_users = st.executeQuery("select * from bases");
            while(rs_users.next()) {
                System.out.println(rs_users.getString("name"));
                base_list.addAll(rs_users.getString("name"));
                baselist.setItems(base_list);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Problems with connecting to the database!");
        }
    }

    public void  getUserList() {
        try {
            Class.forName("com.mysql.jdbc.Driver");//Регистрируем драйвер
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Connection c = null;//Соединение с БД
        try{
            c = DriverManager.getConnection(dataSession.url, dataSession.user, dataSession.password);//Установка соединения с БД
            Statement st = c.createStatement();//Готовим запрос
            ResultSet rs_users = st.executeQuery("select * from users");//Выполняем запрос к БД, результат в переменной rs
            while(rs_users.next()){
                System.out.println(rs_users.getString("username"));//Последовательно для каждой строки выводим значение из колонки ColumnName
                user_list.addAll(rs_users.getString("username"));

            }

        } catch(Exception e){
            e.printStackTrace();

        }
        finally{

            //Обязательно необходимо закрыть соединение
            try {
                if(c != null)
                    c.close();

            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }


    }

    public void addbaseWindow(Stage addwindow) throws Exception{
        Parent addwin = FXMLLoader.load(getClass().getResource("forms/addbase.fxml"));
        addwindow.setTitle("Добавить БД");
        addwindow.setScene(new Scene(addwin, 400, 250));
        addwindow.setResizable(false);
        addwindow.toFront();
        addwindow.show();
    }

    public void userenter(Stage userenterstage) throws Exception{

        Parent addwin = FXMLLoader.load(getClass().getResource("forms/userenter.fxml"));
        userenterstage.setTitle("Выбор пользователя");
        userenterstage.setScene(new Scene(addwin, 400, 200));
        userenterstage.setResizable(false);
        userenterstage.toFront();
        userenterstage.show();

    }
    public  void startMainSale(Stage addwindow) throws Exception{
        Parent addwin = FXMLLoader.load(getClass().getResource("forms/mainsale.fxml"));
        addwindow.setTitle("Рабочее место");
        addwindow.setScene(new Scene(addwin, 800, 600));
        addwindow.setResizable(false);
        addwindow.toFront();
        addwindow.show();
    }

}