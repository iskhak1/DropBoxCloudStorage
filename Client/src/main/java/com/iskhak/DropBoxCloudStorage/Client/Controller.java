package com.iskhak.DropBoxCloudStorage.Client;
import com.iskhak.DropBoxCloudStorage.Broker.CloudMessage;
import com.iskhak.DropBoxCloudStorage.Broker.FileMessage;
import com.iskhak.DropBoxCloudStorage.Broker.FileRequest;
import com.iskhak.DropBoxCloudStorage.Broker.ListFiles;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private String homeDir ;
    private byte[] buf;
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
        homeDir = System.getProperty("user.home");
        listViewClient.getItems().clear();
        listViewClient.getItems().addAll(getFiles(homeDir));
    }
    private List<String> getFiles(String dir){
       String [] list = new File(dir).list();
       return Arrays.asList(list);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
