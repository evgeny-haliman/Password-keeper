package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import sample.logic.Data;
import sample.logic.Logic;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.List;
import java.util.stream.Collectors;

public class Controller {
    public static String mainPassword = null;
    public static boolean isCorrectPassword = true;
    List<String> logins;
    List<String> passwords;
    int selectedLogin;
    List<String> listView;
    Logic logic = new Logic();
    Data data = new Data();
    Validator validator = new Validator();
    private String lastPassword = null;

    @FXML
    private PasswordField mainPassId;

    @FXML
    private Button confirmId;

    @FXML
    private ListView<String> listId;

    @FXML
    private PasswordField lastPassId;

    @FXML
    private Button copyId;

    @FXML
    private TextField serviceId;

    @FXML
    private PasswordField servPassId;

    @FXML
    private Button saveId;

    @FXML
    private ToggleButton toggleId;

    @FXML
    void initialize() {

        saveId.setDisable(true);
        toggleId.setDisable(true);
        copyId.setDisable(true);

        confirmId.setOnAction(actionEvent -> {
            mainPassword = mainPassId.getText();
            listView = listId.getItems();

            if (validator.isValidPassword(mainPassword)) {
                mainPassId.clear();
                logins = data.getEncryptedLogins();
                passwords = data.getEncryptedPasses();
                listView.addAll(logins.stream().map(logic::decrypt).collect(Collectors.toList()));

                if (isCorrectPassword) {
                    saveId.setDisable(false);
                    confirmId.setDisable(true);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("What are you doing?");
                    alert.setHeaderText("Password does not match");
                    alert.setContentText("It looks like you forgot your password");
                    alert.showAndWait();
                    listView.clear();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("What are you doing?");
                alert.setHeaderText("Password does not match");
                alert.setContentText("Should be 16 symbols only");
                alert.showAndWait();
            }
        });

        saveId.setOnAction(actionEvent -> {
            data.writeLoginPassToData(logic.encrypt(serviceId.getText()), logic.encrypt(servPassId.getText()));
            logins = data.getEncryptedLogins();
            listView.clear();
            listView.addAll(logins.stream().map(logic::decrypt).collect(Collectors.toList()));
            passwords = data.getEncryptedPasses();
            serviceId.clear();
            servPassId.clear();
        });

        listId.setOnKeyPressed(k -> {
            selectedLogin = listId.getSelectionModel().getSelectedIndex();
            if (k.getCode().equals(KeyCode.DELETE)) {
                data.deleteLoginPass(selectedLogin);
                logins = data.getEncryptedLogins();
                listView.clear();
                listView.addAll(logins.stream().map(logic::decrypt).collect(Collectors.toList()));
                passwords = data.getEncryptedPasses();
                lastPassId.clear();
            }
            if (k.getCode().equals(KeyCode.UP) || k.getCode().equals(KeyCode.DOWN)) {
                selectedLogin = listId.getSelectionModel().getSelectedIndex();
                lastPassId.setText(getPassword());
                toggleId.setDisable(false);
                copyId.setDisable(false);
            }
        });

        listId.setOnMouseClicked(k -> {
            selectedLogin = listId.getSelectionModel().getSelectedIndex();
            lastPassId.setText(getPassword());
            toggleId.setDisable(false);
            copyId.setDisable(false);
        });

        toggleId.setOnAction(event -> {
            lastPassword = getPassword();
            if (toggleId.isSelected()) {
                lastPassId.setPromptText(lastPassword);
                lastPassId.clear();
                toggleId.setText("Hide");
            } else {
                lastPassId.setText(lastPassword);
                lastPassId.setPromptText("Your password will be here");
                lastPassword = null;
                toggleId.setText("Show");
            }
        });

        copyId.setOnAction(actionEvent -> {
            StringSelection stringSelection = new StringSelection(getPassword());
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
        });
    }

    private String getPassword() {
        return logic.decrypt(passwords.get(selectedLogin));

    }


}
