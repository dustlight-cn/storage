# Storage 
**README** [English](README.md) | [简体中文](README_ZH.md)

## Introductions
**Storage** is project based on java language development, it provides an easy-to-use storage service for the Spring Boot project, including local storage and cloud storage.
This project is under maintenance. Welcome for your issues and pull request. 😀

[![Build Status](https://travis-ci.org/dustlight-cn/storage.svg?branch=main)](https://travis-ci.org/dustlight-cn/storage) 
[![GitHub](https://img.shields.io/github/license/dustlight-cn/storage)](LICENSE)
[![GitHub release (latest by date)](https://img.shields.io/github/v/release/dustlight-cn/storage)](https://github.com/dustlight-cn/storage/releases)

## Module
* [Core Module](storage-core): Provides abstract interfaces and a local storage implementation.

* [Tencent Cloud Object Storage Module](tencent-cloud-object-storage): The implementation of the service based on [Tencent Cloud Object Storage (COS)](https://cloud.tencent.com/product/cos).

## Download
Grab via Maven:
```xml
<dependency>
  <groupId>cn.dustlight.storage</groupId>
  <artifactId>storage-core</artifactId>
  <version>0.0.3</version>
</dependency>
```

## Use
Simple use cases with LocalStorage look like this:
```java
package com.example.demo;

import cn.dustlight.storage.core.Permission;
import cn.dustlight.storage.core.StorableObject;
import cn.dustlight.storage.core.Storage;
import cn.dustlight.storage.local.LocalStorage;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.OutputStream;

@SpringBootApplication
@Component
public class DemoApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    public void run(ApplicationArguments args) throws Exception {

        Storage storage = LocalStorage.defaultInstance; // Default instance's path is '.'
        StorableObject test = storage.create("test.txt", Permission.PUBLIC); // Create a object with key 'test.txt', with permission 'PUBLIC'

        OutputStream out = test.getOutputStream(); // Get object output stream
        out.write("Hello World!".getBytes()); // Write OutputStream
        out.close();

        InputStream in = test.getInputStream(); // Get object input stream
        byte[] data = new byte[13]; // Read InputStream
        in.read(data);
        in.close();
        String str = new String(data);
        System.out.println(str);

    }
}
```
Other operations, such as copying, checking for existence, and deleting:
```java
storage.put("text2.txt",test); // Put object to 'test2.txt'.

storage.isExist("text.txt"); // Check object exists or not.

storage.remove("text.txt"); // Delete object
```
TencentCloudObjectStorage in [Tencent Cloud Object Storage](tencent-cloud-object-storage) provides the URL generated, it can generate the URL for get, put and remove.

See the [wiki](https://github.com/dustlight-cn/storage/wiki) for full instructions.

## Get Help
To report a specific problem or feature request, [open a new issue on Github](https://github.com/dustlight-cn/storage/issues/new).
For questions, suggestions, or anything else, email [hansin@dustlight.cn](mailto:hansin@dustlight.cn).