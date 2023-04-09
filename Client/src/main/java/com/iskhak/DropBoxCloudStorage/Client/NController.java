package com.iskhak.DropBoxCloudStorage.Client;

import com.iskhak.DropBoxCloudStorage.Broker.CloudMessage;
import com.iskhak.DropBoxCloudStorage.Broker.FileMessage;
import com.iskhak.DropBoxCloudStorage.Broker.FileRequest;
import com.iskhak.DropBoxCloudStorage.Broker.ListFiles;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class NController {
    private String homeDir ;
    private byte[] buf;
    private Network network;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private HBox download;

    @FXML
    private ListView listViewClient;

    @FXML
    private ListView listViewServer;

    @FXML
    private Label pathToCurDirClient;

    @FXML
    private Label pathToCurDirServer;

    @FXML
    private HBox upload;

    @FXML
    void download(MouseEvent event) {

    }

    @FXML
    void upload(MouseEvent event) {

    }

    @FXML
    void initialize() {
        try {
            buf = new byte[256];
            network = new Network(8189);
            Thread readT = new Thread(this::readLoop);
            readT.setDaemon(true);
            readT.start();
            initListClientView();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void initListClientView(){
        homeDir = System.getProperty("user.home");
        listViewClient.getItems().clear();
        listViewClient.getItems().addAll(getFiles(homeDir));
    }
    private List<String> getFiles(String dir){
        String [] list = new File(dir).list();
        return Arrays.asList(list);
    }

    private void readLoop(){
        try{
            while(true){
                CloudMessage message = (CloudMessage) network.read();
                if(message instanceof ListFiles listFiles){
                    Platform.runLater(()-> {
                        listViewServer.getItems().clear();
                        listViewServer.getItems().addAll(listFiles.getFiles());
                    });
                }else if(message instanceof FileMessage fileMessage){
                    Path current = Path.of(homeDir).resolve(fileMessage.getName());
                    Files.write(current,fileMessage.getData());
                    Platform.runLater(()-> {
                        listViewClient.getItems().clear();
                        listViewClient.getItems().addAll(getFiles(homeDir));
                    });
                }

            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void upload(ActionEvent event) throws IOException {
        String file = (String)listViewClient.getSelectionModel().getSelectedItem();
        network.write(new FileMessage(Path.of(homeDir).resolve(file)));
    }

    public void download(ActionEvent event) throws IOException {
        String file = (String)listViewServer.getSelectionModel().getSelectedItem();
        network.write(new FileRequest(file));
    }

}
