module Client {

    requires javafx.base;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.controls;
    requires Broker;
    requires netty.all;


    exports com.iskhak.DropBoxCloudStorage.Client to javafx.fxml,javafx.graphics,javafx.base,javafx.controls;
    opens com.iskhak.DropBoxCloudStorage.Client to javafx.fxml,javafx.graphics,javafx.base,javafx.controls,Broker;

}