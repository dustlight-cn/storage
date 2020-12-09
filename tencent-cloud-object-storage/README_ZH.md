# Tencent-Cloud-Object-Storage
**README** [English](README.md) | [简体中文](README_ZH.md)

## 简介
**Tencent-Cloud-Object-Storage**是**Storage**项目的云存储实现，基于 [腾讯云对象存储服务(COS)](https://cloud.tencent.com/product/cos) 。
它为Spring Boot项目提供方便的数据存储服务，并且可以生成上传，下载以及删除URL以提供客户端访问来避免本地IO。

[![Build Status](https://travis-ci.org/Hansin1997/Storage.svg?branch=main)](https://travis-ci.org/Hansin1997/Storage) 
[![GitHub](https://img.shields.io/github/license/Hansin1997/Storage)](LICENSE)
[![GitHub release (latest by date)](https://img.shields.io/github/v/release/Hansin1997/Storage)](https://github.com/Hansin1997/Storage/releases)
[![Maven Central](https://img.shields.io/maven-central/v/cn.dustlight.storage/tencent-cloud-object-storage)](https://mvnrepository.com/artifact/cn.dustlight.storage/tencent-cloud-object-storage)

## 集成
将依赖添加到Maven项目中：
```xml
<dependency>
    <groupId>cn.dustlight.storage</groupId>
    <artifactId>tencent-cloud-object-storage</artifactId>
    <version>0.0.2</version>
</dependency>
```

## 配置
application.yaml:
```yaml
dustlight:
  storage:
    tencent:
      cos:
        enabled: true
        secret-id: <SECRET_ID>
        secret-key: <SECRET_KEY>
        bucket: <BUCKET>
        region: <REGION>
#        general-api: 
#        service-api: service.cos.myqcloud.com
#        http-protocol: https
```

或者

application.properties: 
```properties
dustlight.storage.tencent.cos.enabled=true
dustlight.storage.tencent.cos.secret-id=<SECRET_ID>
dustlight.storage.tencent.cos.secret-key=<SECRET_KEY>
dustlight.storage.tencent.cos.bucket=<BUCKET>
dustlight.storage.tencent.cos.region=<REGION>
#dustlight.storage.tencent.cos.general-api=
#dustlight.storage.tencent.cos.service-api=service.cos.myqcloud.com
#dustlight.storage.tencent.cos.http-protocol=https
```

配置项解释：
* enabled —— 是否启用模块：当 enabled=true 时将自动创建TencentCloudObjectStorage的Bean。（默认为true）
* secret-id —— 腾讯云SecretID：腾讯云服务访问密钥ID，此密钥需要包含COS访问权限。[查看或创建SecretID&SecretKey](https://console.cloud.tencent.com/cam/capi)
* secret-key —— 腾讯云SecretID：腾讯云服务访问密钥Key，此密钥需要包含COS访问权限。[查看或创建SecretID&SecretKey](https://console.cloud.tencent.com/cam/capi)
* bucket —— 储存桶名：TencentCloudObjectStorage基于腾讯云对象储存实现，数据将存储于此存储桶之中。[查看或创建Bucket](https://console.cloud.tencent.com/cos5/bucket)
* region —— 地区：储存桶所在地区，例如 "ap-guangzhou"。[查看Region](https://console.cloud.tencent.com/cos5/bucket)
* general-api —— 腾讯云服务URL生成地址：通常无需更改。
* service-api —— 腾讯云服务API地址：通常无需更改。
* http-protocol —— HTTP协议：后台数据传输以及生成URL的HTTP协议。（默认为HTTPS）

## 使用方法
TencentCloudObjectStorage的简单用例如下所示：
```java
package com.example.demo;

import cn.dustlight.storage.core.Permission;
import cn.dustlight.storage.core.StorableObject;
import cn.dustlight.storage.local.LocalStorableObject;
import cn.dustlight.storage.local.LocalStorage;
import cn.dustlight.storage.tencent.cos.TencentCloudObjectStorage;
import cn.dustlight.storage.tencent.cos.TencentCloudStorableObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
@Component
public class DemoApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Autowired
    TencentCloudObjectStorage cloudStorage; // 腾讯云对象存储库
    LocalStorage localStorage = LocalStorage.defaultInstance; // 本地存储库，默认实例路径为 './'

    public void run(ApplicationArguments args) throws Exception {
        StorableObject test = localStorage.get("test.txt"); // 获取本地Object

        TencentCloudStorableObject test2 = cloudStorage.put("test.txt", test); // 复制Object到腾讯云对象存储库。

        String url = cloudStorage.generateGetUrl(test2.getKey(), 1000L * 60 * 5); // 生成访问URL,有效时间5分钟。

        System.out.println(url);

        LocalStorableObject download = localStorage.put("download.txt", test2, Permission.PUBLIC); // 从腾讯云对象储存库下载到本地

        System.out.println(download.getFile());
    }
}
```

完整说明请查看 [wiki](https://github.com/Hansin1997/Storage/wiki) 。

## 获取帮助
如果需要报告问题或者功能需求，请在Github中 [创建issue](https://github.com/Hansin1997/Storage/issues/new) 。若有其他问题或建议，请发送电子邮件至 [hansin@dustlight.cn](mailto:hansin@dustlight.cn)