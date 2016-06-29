package com.github.hackerwin7.shrimp.common;

/**
 * Created by IntelliJ IDEA.
 * User: hackerwin7
 * Date: 2016/06/22
 * Time: 5:04 PM
 * Desc: err code response and request
 * Tips:
 */
public class Err {

    /* error judge */
    public static final int OK = 0;
    public static final int ERR = 1;

    /* error cases */
    public static final int HEART_BEAT_ERR = 100;
    public static final int DOWNLOAD_FAIL = 200;
    public static final int UPLOAD_FAIL = 300;
    public static final int MD5_CHECKING = 400;

    /* error code */
    private int errCode = 0;

    /* download fail and upload fail */
    private long commitOffset = 0;

    /* getter and setter */

    public long getCommitOffset() {
        return commitOffset;
    }

    public void setCommitOffset(long commitOffset) {
        this.commitOffset = commitOffset;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }
}
