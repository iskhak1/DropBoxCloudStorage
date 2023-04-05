module Client {

    requires javafx.base;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.controls;
    requires netty.all;
    requires Broker;


    exports com.iskhak.DropBoxCloudStorage.Client to javafx.fxml,javafx.graphics,javafx.base;
    opens com.iskhak.DropBoxCloudStorage.Client to javafx.fxml;

}