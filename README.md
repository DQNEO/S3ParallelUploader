# S3ParallelUploader

This is a Java command line tool which uploads many files in parallel into a AmazonS3 bucket.

In short, it's a very *fast* uploader :wink:

## Motivation

My CPAN module Amazon::S3::FastUploader does'nt work on Windows.

http://search.cpan.org/~dqneo/Amazon-S3-FastUploader-0.06/

So I re-invented it in Java language.

The idea of multi threading is inspired by this article :

http://dev.classmethod.jp/cloud/amazon-s3-java-util-concurrent-file-upload/

## 

## Requirement

This tool requires AWS SDK for Java.

see http://aws.amazon.com/sdkforjava
## Liscence

MIT Liscence

copyright @DQNEO 2012