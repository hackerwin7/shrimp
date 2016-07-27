/*
* transfer file using the thrift interface
*
* command :
* cd ~/Project/shrimp/src/main/java
* thrift -out ./ -r --gen java com/github/hackerwin7/shrimp/thrift/TransFile.thrift
* */

/* package */
namespace java com.github.hackerwin7.shrimp.thrift.gen

/* constatns for download and upload transfer unit KB */
const i32 CHUNK_UNIT = 10240

/* data */

/**
 * information about file
 */
struct TFileInfo {
    1: string name,
    2: i64 length,
    3: string suffix,
    4: string t_from,
    5: i64 ts,
    6: string md5,
    7: i64 start
}

/**
 * a chunk of the file
 */
struct TFileChunk {
    1: binary bytes,
    2: string name,
    3: i64 length,
    4: i64 offset,
    5: string t_from,
    6: i64 ts
}

/**
* Thrift Err, Err class in the thrift
**/
struct TErr {
    1: i32 err,
    2: i64 commit
}

/* servive */

/**
*  file download service
**/
service TDFileService {

    /**
    * open connection
    * name : file name
    * startCon : download offset
    */
    TFileInfo open(1:string name, 2:i64 start),

    /**
    * get continue chunks of file
    */
    TFileChunk getChunk(),

    /**
    * close connection
    */
    oneway void close()
}

/**
* file upload service
**/
service TUFileService {
    /**
    * send the file info open in the client
    **/
    oneway void open(1:TFileInfo info),

    /**
    * send chunk to upload whole file
    **/
    oneway void sendChunk(1:TFileChunk chunk),

    /**
    * checking the upload cases
    **/
    TErr checking(1:TFileInfo info),

    /**
    * get the file info
    **/
    TFileInfo fileInfo(1:string name),

    /**
    * close the upload connection
    **/
    oneway void close()
}

/**
* file pool struct
**/
struct TFilePool {
    1: string host,
    2: i32 port,
    3: map<string, TFileInfo> pool
}

/*
* RPC communication
* */
enum TOperation {
    download = 0,
    upload = 1,
    heartbeat = 2,
    restart = 3
}

/**
* controller service, info transportation
**/
service TControllerService {

    /**
    * get the own complete file servers list
    **/
    list<string> fileServers(1:string name),


    /**
    * send a complete file info to controller
    * before the upload and after the download (both success)
    **/
    oneway void sendFileInfo(1:TFileInfo info),

    /**
    * register the server to the controller
    * @hostport such as 127.0.0.1:9091
    **/
    oneway void registerServer(1:string hostport),

    /**
    * send a file pool info to the controller
    **/
    oneway void sendFilePool(1:TFilePool pool),

    /**
    * heartbeat send file pools
    **/
    oneway void sendFilePools(1:map<string, TFilePool> pools),

    /**
    * signal such as reload operation
    **/
    oneway void sendSignal(1:TOperation op)
}

/**
* communication message
**/
struct TMessage {
    1: string src,
    2: string des,
    3: TOperation op,
    4: i64 ts,
    5: string name,
    6: i64 offset,
    7: string ext
}

/**
* message transfer
**/
service TTransService {

    /**
    * send message to the others
    **/
    oneway void sendMsg(1:TMessage msg),

    /**
    * whether exists the specific file, how to know the file is complete or not ?
    * the controller can load the max size of the file ?????????? can we make a complete file pool in controller ?, I think we can do it
    **/
    TFileInfo getFileInfo(1:string name)
}