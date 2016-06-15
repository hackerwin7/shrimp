/*
* transfer file using the thrift interface
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
    6: string md5
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

/* servive */

service TFileService {

    /**
    * open connection
    * name : file name
    * download : download offset
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