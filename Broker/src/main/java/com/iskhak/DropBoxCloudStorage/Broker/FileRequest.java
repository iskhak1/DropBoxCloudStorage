package com.iskhak.DropBoxCloudStorage.Broker;

import lombok.Data;

@Data
public class FileRequest implements CloudMessage{

    private final String name;


}
