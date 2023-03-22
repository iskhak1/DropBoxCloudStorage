package com.iskhak.DropBoxCloudStorage.Client;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
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
                String command = network.readString();
                if(command.equals("#list#")){
                    Platform.runLater(()->listViewServer.getItems().clear());
                    int len = network.readInt();
                    for (int i = 0; i < len; i++) {
                        String file = network.readString();
                        Platform.runLater(()-> listViewServer.getItems().add(file));
                    }
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void upload(ActionEvent event) throws IOException {
        network.getOs().writeUTF("#file#");
        String file = (String)listViewClient.getSelectionModel().getSelectedItem();
        network.getOs().writeUTF(file);
        File toSend = Path.of(homeDir).resolve(file).toFile();
        network.getOs().writeLong(toSend.length());
        try(FileInputStream fis = new FileInputStream(toSend)){
            while(fis.available()>0){
               int read = fis.read(buf);
               network.getOs().write(buf,0,read);
            }
        }
        network.getOs().flush();
    }

    public void download(ActionEvent event) {
    }
}
