package com.iskhak.DropBoxCloudStorage.Server.terminal;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class Client {
    private Network network;
    private String clientDir;

    public Client(){
        network = new Network();
        clientDir = System.getProperty("user.home");
    }

    private void readLoop(){

        try{

            while(true){
                String command = network.readString();

                switch (command.split(" ")[0]){

                    case "cd":
                        break;
                    case "ls":
                        break;
                    case "cat":
                        break;

                }


                if(command.equals("cd")){

                }


            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }


}
