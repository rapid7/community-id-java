community-id-java
=================

This package provides a Java 8 implementation of the open
[Community ID](https://github.com/corelight/community-id-spec)
flow hashing standard.

This library supports IPV4 and IPV6 addresses and TCP, UDP, and STCP network protocols. It does not support ICMP protocols.

### How it works

The Community ID Generator creates a Community ID string by taking in a protocol, source IP address, source port, destination address, and destination port. It creates two IpPortPair objects and sorts them in a deterministic order. It then packs the necessary bytes in NBO order, and produces a 20-byte SHA1 digest. Then, base64 encodes it or turns it into a hexadecimal string depending on configuration. It appends the value to "1:" for version 1.  

The Community ID generator is configurable to take in a seed value to be packed and encoded as well as a boolean for base64 encoding. Only the first two bytes of the seed value will be used in the Community ID.

### Getting community-id-java

To add community-id-java to your Maven project, use the following:

```xml
<dependency>
    <groupId>com.rapid7.communityid</groupId>
    <artifactId>community-id-java</artifactId>
    <version>2.0.0</version>
</dependency>
```

### Example of creating a Community ID:
```java 
CommunityIdGenerator generator = new CommunityIdGenerator();  

//The default constructor will set seed to 0 and base64 to true

String result = generator.generateCommunityId(Protocol.UDP, 
    InetAddress.getByName("192.168.1.52"), 54585, InetAddress.getByName("8.8.8.8"), 53);
```

result will look like:

```
"1:Q9We8WO3piVF8yEQBNJF4uiSVrI=".
```

