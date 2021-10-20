# MinIO-Storage
**README** [English](README.md) | [简体中文](README_ZH.md)

## Introductions
**MinIO-Storage** is implementation of **Storage** project interfaces, based on [MinIO](https://min.io/) .
It provides convenient data storage services for Spring Boot projects, including generate get, put, and remove urls to provide client access to avoid local IO.

[![Build Status](https://travis-ci.org/dustlight-cn/storage.svg?branch=main)](https://travis-ci.org/dustlight-cn/storage) 
[![GitHub](https://img.shields.io/github/license/dustlight-cn/storage)](LICENSE)
[![GitHub release (latest by date)](https://img.shields.io/github/v/release/dustlight-cn/storage)](https://github.com/dustlight-cn/storage/releases)
![Sonatype Nexus (Releases)](https://img.shields.io/nexus/r/cn.dustlight.storage/minio-storage?server=https%3A%2F%2Foss.sonatype.org)
![Sonatype Nexus (Snapshots)](https://img.shields.io/nexus/s/cn.dustlight.storage/minio-storage?server=https%3A%2F%2Foss.sonatype.org)

## Download
Grab via Maven:
```xml
<dependency>
  <groupId>cn.dustlight.storage</groupId>
    <artifactId>minio-storage</artifactId>
  <version>0.0.6</version>
</dependency>
```

## Configurations
application.yaml:
```yaml
dustlight:
  storage:
    minio:
      access-key-id: YOUR_ACCESS_KEY_ID
      secret-access-key: YOUR_SECRET_ACCESS_KEY
      bucket: YOUR_BUCKET_NAME
      endpoint: YOUR_BUCKET_ENDPOINT
```

Or

application.properties: 
```properties
dustlight.storage.minio.access-key-id=YOUR_ACCESS_KEY_ID
dustlight.storage.minio.secret-access-key=YOUR_SECRET_ACCESS_KEY
dustlight.storage.minio.bucket=YOUR_BUCKET_NAME
dustlight.storage.minio.endpoint=YOUR_BUCKET_ENDPOINT
```

## Use
See the [wiki](https://github.com/dustlight-cn/storage/wiki) for full instructions.

## Get Help
To report a specific problem or feature request, [open a new issue on Github](https://github.com/dustlight-cn/storage/issues/new).
For questions, suggestions, or anything else, email [hansin@dustlight.cn](mailto:hansin@dustlight.cn).