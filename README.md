## shrimp
-----------
a distributed system which transfer data or file smoothly

### installation && usage
1.mvn to generate a tar package
```
mvn clean install
```

2.unpackage the tar and enter the dir
```
tar xvf xxx.tar.gz -C ./
cd xxx/
```

3.config the conf/controller.properties and conf/serverproperties
such as:
```
# if not set, the server
# will use java.net.InetAddress.getHostAddress() to get the ip address
#host =
port =9097
zookeeper.connect =192.168.200.118:2181
```
```
# if not set, the server
# will use java.net.InetAddress.getHostAddress() to get the ip address
#host =
port =9091
# the downloading file will save in here temporarily
downloading.dir.path =/export/ss/shrimp-1.0-SNAPSHOT/data/ing
# when downloaded, the file will move to the downloaded directory
downloaded.dir.path =/export/ss/shrimp-1.0-SNAPSHOT/data/ed
zookeeper.connect =192.168.200.118:2181
```
the ```downloading.dir.path``` is a temporary dir for downloading files  
the ```downloaded.dir.path``` is a complete dir for downloaded files  
**controller.properties** is necessary to start controller  
**server.properties** is necessary to start server  

4.start Controller or spServer(generally, two controllers and multiple servers)  
```
./bin/spController.sh start
```
or
```
./bin/spServer.sh start
```

5.use bin/spUpload.sh or bin/spDownload to transfer file  
```
./bin/spUpload.sh ~/dir1/dir2/filename
```
or
```
./bin/spDownload.sh filename
```
### configuraton

***controller.properties***
```
if not set, the server
# will use java.net.InetAddress.getHostAddress() to get the ip address
#host =
port =
zookeeper.connect =
```
```host``` is a ip of the local server, if not set we will call getHostAddress() to get the value  
```port``` is a port which the server will bind  
```zookeeper.connect``` is a zk connection such as 172.17.17.17:2181,172.17.17.18:2181,172.17.17.19:2181  

***server.properties***
```
# if not set, the server
# will use java.net.InetAddress.getHostAddress() to get the ip address
#host =
port =
# the downloading file will save in here temporarily
downloading.dir.path =
# when downloaded, the file will move to the downloaded directory
downloaded.dir.path =
zookeeper.connect =
```
```host``` is a ip of the local controller, if not set we will call getHostAddress() to get the value  
```port``` is a port which the controller will bind  
```downloading.dir.path``` a dir to save the downloading files, incomplete files  
```downloaded.dir.path``` a dir to save the downloaded files, complete files  
```zookeeper.connect``` a zk connection

### issues
* a single huge file will cause high load to calculate MD5
* transfer performance: BT protocol ? 
* strategy of the transfer such as every file has 3 repilcas
* further...

### Authors
* hackerwin7

### version
0.1.0-SNAPSHOT

