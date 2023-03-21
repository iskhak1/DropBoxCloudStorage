package com.iskhak.DropBoxCloudStorage.Client;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private Network network;
    @FXML
    public ListView listViewServer;
    @FXML
    public Button btnToClient;
    @FXML
    public ListView listViewClient;
    @FXML
    public Button btnToServer;


    private void initListClientView(){
        String dir = System.getProperty("user.home");
        listViewClient.getItems().clear();
        listViewClient.getItems().addAll(getFiles(dir));
    }
    private List<String> getFiles(String dir){
       String [] list = new File(dir).list();
       return Arrays.asList(list);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
            try {
                network = new Network(8189);
                Thread readT = new Thread(this::readLoop);
                readT.setDaemon(true);
                readT.start();
                initListClientView();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
    }

    private void readLoop(){
        try{
            while(true){
                String msg = network.readMessage();
                Platform.runLater(()-> listViewServer.getItems().add(msg));
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

}
