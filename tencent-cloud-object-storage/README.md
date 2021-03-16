# Tencent-Cloud-Object-Storage
**README** [English](README.md) | [简体中文](README_ZH.md)

## Introductions
**Tencent-Cloud-Object-Storage** is implementation of **Storage** project interfaces, based on [Tencent Cloud Object Storage(COS)](https://cloud.tencent.com/product/cos) Service.
It provides convenient data storage services for Spring Boot projects, including generate get, put, and remove urls to provide client access to avoid local IO.

[![Build Status](https://travis-ci.org/dustlight-cn/storage.svg?branch=main)](https://travis-ci.org/dustlight-cn/storage) 
[![GitHub](https://img.shields.io/github/license/dustlight-cn/storage)](LICENSE)
[![GitHub release (latest by date)](https://img.shields.io/github/v/release/dustlight-cn/storage)](https://github.com/dustlight-cn/storage/releases)
![Sonatype Nexus (Releases)](https://img.shields.io/nexus/r/cn.dustlight.storage/tencent-cloud-object-storage?server=https%3A%2F%2Foss.sonatype.org)
![Sonatype Nexus (Snapshots)](https://img.shields.io/nexus/s/cn.dustlight.storage/tencent-cloud-object-storage?server=https%3A%2F%2Foss.sonatype.org)

## Download
Grab via Maven:
```xml
<dependency>
  <groupId>cn.dustlight.storage</groupId>
    <artifactId>tencent-cloud-object-storage</artifactId>
  <version>0.0.4</version>
</dependency>
```

## Configurations
application.yaml:
```yaml
dustlight:
  storage:
    tencent:
      cos:
        secret-id: <SECRET_ID>
        secret-key: <SECRET_KEY>
        bucket: <BUCKET>
        region: <REGION>
#        general-api: 
#        service-api: service.cos.myqcloud.com
#        http-protocol: https
```

Or

application.properties: 
```properties
dustlight.storage.tencent.cos.secret-id=<SECRET_ID>
dustlight.storage.tencent.cos.secret-key=<SECRET_KEY>
dustlight.storage.tencent.cos.bucket=<BUCKET>
dustlight.storage.tencent.cos.region=<REGION>
#dustlight.storage.tencent.cos.general-api=
#dustlight.storage.tencent.cos.service-api=service.cos.myqcloud.com
#dustlight.storage.tencent.cos.http-protocol=https
```

Configuration item interpretation:
* enabled —— When enabled is true, it would create Bean of TencentCloudObjectStorage by configurations. (The default is true)
* secret-id —— Tencent cloud Secret ID, this key needs to contain COS access. [View or Create SecretID&SecretKey](https://console.cloud.tencent.com/cam/capi)
* secret-key —— Tencent cloud Secret Key, this key needs to contain COS access. [View or Create SecretID&SecretKey](https://console.cloud.tencent.com/cam/capi)
* bucket —— Storage bucket name, TencentCloudObjectStorage based on *Tencent Cloud Object Storage* service, the data will be stored in this bucket. [View or Create Bucket](https://console.cloud.tencent.com/cos5/bucket)
* region —— Storage area, such as "ap-guangzhou". [View Region](https://console.cloud.tencent.com/cos5/bucket)
* general-api —— Host of generated URL, don't need to change it.
* service-api —— Tencent Cloud Service API, don't need to change it.
* http-protocol —— The HTTP protocol for data transfer and generated URL. (The default is HTTPS)

## Use
Simple use cases with TencentCloudObjectStorage look like this:
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
    TencentCloudObjectStorage cloudStorage; // Tencent Cloud Storage
    LocalStorage localStorage = LocalStorage.defaultInstance; // Local Storage, the root path of defaultInstance is './'

    public void run(ApplicationArguments args) throws Exception {
        StorableObject test = localStorage.get("test.txt"); // Get the local object

        TencentCloudStorableObject test2 = cloudStorage.put("test.txt", test); // Copy to cloud storage

        String url = cloudStorage.generateGetUrl(test2.getKey(), 1000L * 60 * 5); // Generate a URL expired at 5 min later

        System.out.println(url);

        LocalStorableObject download = localStorage.put("download.txt", test2, Permission.PUBLIC); // Download from cloud storage to local storage

        System.out.println(download.getFile());
    }
}
```

See the [wiki](https://github.com/dustlight-cn/storage/wiki) for full instructions.

## Get Help
To report a specific problem or feature request, [open a new issue on Github](https://github.com/dustlight-cn/storage/issues/new).
For questions, suggestions, or anything else, email [hansin@dustlight.cn](mailto:hansin@dustlight.cn).