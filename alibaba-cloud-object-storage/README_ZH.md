# Alibaba-Cloud-Object-Storage
**README** [English](README.md) | [简体中文](README_ZH.md)

## 简介
**Alibaba-Cloud-Object-Storage**是**Storage**项目的云存储实现，基于 [阿里云对象储存服务(OSS)](https://www.aliyun.com/product/oss) 。
它为Spring Boot项目提供方便的数据存储服务，并且可以生成上传，下载以及删除URL以提供客户端访问来避免本地IO。

[![Build Status](https://travis-ci.org/dustlight-cn/storage.svg?branch=main)](https://travis-ci.org/dustlight-cn/storage)
[![GitHub](https://img.shields.io/github/license/dustlight-cn/storage)](LICENSE)
[![GitHub release (latest by date)](https://img.shields.io/github/v/release/dustlight-cn/storage)](https://github.com/dustlight-cn/storage/releases)
![Sonatype Nexus (Releases)](https://img.shields.io/nexus/r/cn.dustlight.storage/alibaba-cloud-object-storage?server=https%3A%2F%2Foss.sonatype.org)
![Sonatype Nexus (Snapshots)](https://img.shields.io/nexus/s/cn.dustlight.storage/alibaba-cloud-object-storage?server=https%3A%2F%2Foss.sonatype.org)

## 集成
将依赖添加到Maven项目中：
```xml
<dependency>
    <groupId>cn.dustlight.storage</groupId>
    <artifactId>alibaba-cloud-object-storage</artifactId>
    <version>0.0.5</version>
</dependency>
```

## 配置
application.yaml:
```yaml
dustlight:
  storage:
    alibaba:
      oss:
        access-key-id: YOUR_ACCESS_KEY_ID
        secret-access-key: YOUR_SECRET_ACCESS_KEY
        bucket: YOUR_BUCKET_NAME
        endpoint: YOUR_BUCKET_ENDPOINT
```

或者

application.properties: 
```properties
dustlight.storage.alibaba.oss.access-key-id=YOUR_ACCESS_KEY_ID
dustlight.storage.alibaba.oss.secret-access-key=YOUR_SECRET_ACCESS_KEY
dustlight.storage.alibaba.oss.bucket=YOUR_BUCKET_NAME
dustlight.storage.alibaba.oss.endpoint=YOUR_BUCKET_ENDPOINT
```

## 使用方法

完整说明请查看 [wiki](https://github.com/dustlight-cn/storage/wiki) 。

## 获取帮助
如果需要报告问题或者功能需求，请在Github中 [创建issue](https://github.com/dustlight-cn/storage/issues/new) 。若有其他问题或建议，请发送电子邮件至 [hansin@dustlight.cn](mailto:hansin@dustlight.cn)