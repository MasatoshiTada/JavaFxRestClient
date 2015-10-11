package com.example;

import com.example.dto.EmployeeDto;
import com.example.exception.dto.ExceptionDto;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static javax.ws.rs.core.Response.Status.*;
import static javafx.scene.control.Alert.AlertType.*;

public class FXMLController implements Initializable {

    @FXML
    private TextField nameTextField;
    @FXML
    private TextField targetUrlTextField;
    @FXML
    private TableView<EmployeeDto> empTableView;
    @FXML
    private TableColumn<EmployeeDto, Integer> empIdColumn;
    @FXML
    private TableColumn<EmployeeDto, String> nameColumn;
    @FXML
    private TableColumn<EmployeeDto, String> joinedDateColumn;
    @FXML
    private TableColumn<EmployeeDto, String> deptNameColumn;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        sendRequest();
    }
    
    @FXML
    private void handleButtonKeyPressAction(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            sendRequest();
        }
    }

    @FXML
    private void handleClearButtonAction(ActionEvent event) {
        clearTable();
    }

    private void sendRequest() {
        String url = targetUrlTextField.getText();
        String nameKey = nameTextField.getText();
        Response response = null;
        try {
            // サーバーにリクエストを送信する
            response = ClientBuilder.newClient()
                    .target(url)
                    .queryParam("name", nameKey)
                    .request(MediaType.APPLICATION_JSON)
                    .get();
            Response.StatusType statusInfo = response.getStatusInfo();
            
            if (statusInfo.equals(OK)) {
                // レスポンスが正常に返ってきた場合
                List<EmployeeDto> list = 
                        response.readEntity(new GenericType<List<EmployeeDto>>() {});
                // コンソールに表示
                list.forEach(emp -> System.out.println(emp));
                // テーブルにデータを表示
                clearTable();
                setEmployeeToTable(list);
            } else {
                // レスポンスが正常でない場合
                ExceptionDto exceptionDto = response.readEntity(ExceptionDto.class);
                String errorCode = statusInfo.getStatusCode() 
                        + ":" + statusInfo.getReasonPhrase();
                String message = Arrays.stream(exceptionDto.getMessages())
                        .collect(Collectors.joining(System.lineSeparator()));
                if (statusInfo.equals(NOT_FOUND)) {
                    // 404 NOT FOUND（検索結果なし）の場合
                    showDialog(INFORMATION, "お知らせ", errorCode, message);
                    clearTable();
                } else if (statusInfo.equals(BAD_REQUEST)) {
                    // 400 BAD REQUEST（バリデーションエラー）の場合
                    showDialog(WARNING, "入力エラー", errorCode, message);
                } else if (statusInfo.equals(INTERNAL_SERVER_ERROR)) {
                    // 500 INTERNAL SERVER ERROR（例外発生）の場合
                    showDialog(ERROR, "サーバー内部エラー", errorCode, message);
                } else {
                    // それ以外のステータスコードの場合
                    showDialog(ERROR, "未知のエラー", errorCode, message);
                }
            }
        } catch (ProcessingException e) {
            // サーバーが起動していない場合
            e.printStackTrace();
            showDialog(ERROR, "エラー", "エラー", "ターゲットURLが正しくありません");
        } catch (Exception e) {
            e.printStackTrace();
            showDialog(ERROR, "エラー", "エラー", "エラー");
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

    private void clearTable() {
        empTableView.getItems().clear();
    }

    private void showDialog(AlertType alertType, String title, 
            String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.getDialogPane().setHeaderText(header);
        alert.getDialogPane().setContentText(content);
        alert.showAndWait();
    }

    private void setEmployeeToTable(List<EmployeeDto> list) {
        list.stream().forEach(emp -> empTableView.getItems().add(emp));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        empIdColumn.setCellValueFactory(new PropertyValueFactory<>("empId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        joinedDateColumn.setCellValueFactory(new PropertyValueFactory<>("joinedDateString"));
        deptNameColumn.setCellValueFactory(new PropertyValueFactory<>("departmentName"));
    }
}
