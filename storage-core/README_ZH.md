# Storage-Core
**README** [English](README.md) | [简体中文](README_ZH.md)

## 简介
**Storage-Core**是**Storage**项目的核心模块，提供存储服务相关的抽象接口以及本地存储实现。

[![Build Status](https://travis-ci.org/Hansin1997/Storage.svg?branch=main)](https://travis-ci.org/Hansin1997/Storage) 
[![GitHub](https://img.shields.io/github/license/Hansin1997/Storage)](LICENSE)
[![GitHub release (latest by date)](https://img.shields.io/github/v/release/Hansin1997/Storage)](https://github.com/Hansin1997/Storage/releases)
[![Maven Central](https://img.shields.io/maven-central/v/cn.dustlight.storage/storage-core)](https://mvnrepository.com/artifact/cn.dustlight.storage/storage-core)

## 集成
将依赖添加到Maven项目中：
```xml
<dependency>
  <groupId>cn.dustlight.storage</groupId>
  <artifactId>storage-core</artifactId>
  <version>0.0.2</version>
</dependency>
```

## 使用方法
LocalStorage的简单用例如下所示：
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

        Storage storage = LocalStorage.defaultInstance; // 获取默认实例，路径为 '.'
        StorableObject test = storage.create("test.txt", Permission.PUBLIC); // 创建一个Key为 'test.txt'，权限为 'PUBLIC' 的Object

        OutputStream out = test.getOutputStream(); // 获取Object输出流
        out.write("Hello World!".getBytes()); // 写输出流
        out.close();

        InputStream in = test.getInputStream(); // 获取Object输入流
        byte[] data = new byte[13]; // 读输入流
        in.read(data);
        in.close();
        String str = new String(data);
        System.out.println(str);

    }
}
```
其他操作，例如复制、检查是否存在以及删除：
```java
storage.put("text2.txt",test); // 将object 'test' 另存为 'test2.txt'

storage.isExist("text.txt"); // 检查object是否存在

storage.remove("text.txt"); // 删除object
```

[腾讯云对象存储模块](tencent-cloud-object-storage) 中的 TencentCloudObjectStorage 提供了URL的生成，可以生成 get,put,remove 的URL。

完整说明请查看 [wiki](https://github.com/Hansin1997/Storage/wiki) 。

## 获取帮助
如果需要报告问题或者功能需求，请在Github中 [创建issue](https://github.com/Hansin1997/Storage/issues/new) 。若有其他问题或建议，请发送电子邮件至 [hansin@dustlight.cn](mailto:hansin@dustlight.cn)