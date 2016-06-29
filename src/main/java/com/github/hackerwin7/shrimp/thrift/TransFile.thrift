/*
* transfer file using the thrift interface
*
* command :
* cd ~/Project/shrimp/src/main/java
* thrift -out ./ -r --gen java com/github/hackerwin7/shrimp/thrift/TransFile.thrift
* */

/* package */
namespace java com.github.hackerwin7.shrimp.thrift.gen

/* constatns */
const i32 CHUNK_UNIT = 1024

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
    * start : download offset
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


/*
* RPC communication
* */
enum Operation {
    download = 0,
    upload = 1,
    heartbeat = 2
}

/**
* communication message
**/
struct TMessage {
    1: string src,
    2: string des,
    3: Operation op,
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
    oneway void sendMsg(1:TMessage msg)
}