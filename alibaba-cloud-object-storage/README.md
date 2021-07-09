# Alibaba-Cloud-Object-Storage
**README** [English](README.md) | [简体中文](README_ZH.md)

## Introductions
**Alibaba-Cloud-Object-Storage** is implementation of **Storage** project interfaces, based on [Alibaba Cloud Object Storage Service(OSS)](https://www.aliyun.com/product/oss) .
It provides convenient data storage services for Spring Boot projects, including generate get, put, and remove urls to provide client access to avoid local IO.

[![Build Status](https://travis-ci.org/dustlight-cn/storage.svg?branch=main)](https://travis-ci.org/dustlight-cn/storage) 
[![GitHub](https://img.shields.io/github/license/dustlight-cn/storage)](LICENSE)
[![GitHub release (latest by date)](https://img.shields.io/github/v/release/dustlight-cn/storage)](https://github.com/dustlight-cn/storage/releases)
![Sonatype Nexus (Releases)](https://img.shields.io/nexus/r/cn.dustlight.storage/alibaba-cloud-object-storage?server=https%3A%2F%2Foss.sonatype.org)
![Sonatype Nexus (Snapshots)](https://img.shields.io/nexus/s/cn.dustlight.storage/alibaba-cloud-object-storage?server=https%3A%2F%2Foss.sonatype.org)

## Download
Grab via Maven:
```xml
<dependency>
  <groupId>cn.dustlight.storage</groupId>
    <artifactId>alibaba-cloud-object-storage</artifactId>
  <version>0.0.5</version>
</dependency>
```

## Configurations
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

Or

application.properties: 
```properties
dustlight.storage.alibaba.oss.access-key-id=YOUR_ACCESS_KEY_ID
dustlight.storage.alibaba.oss.secret-access-key=YOUR_SECRET_ACCESS_KEY
dustlight.storage.alibaba.oss.bucket=YOUR_BUCKET_NAME
dustlight.storage.alibaba.oss.endpoint=YOUR_BUCKET_ENDPOINT
```

## Use
See the [wiki](https://github.com/dustlight-cn/storage/wiki) for full instructions.

## Get Help
To report a specific problem or feature request, [open a new issue on Github](https://github.com/dustlight-cn/storage/issues/new).
For questions, suggestions, or anything else, email [hansin@dustlight.cn](mailto:hansin@dustlight.cn).