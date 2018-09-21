package com.e.com.videoandimageuploaddemo;

import android.content.Context;

import java.util.Map;

/**
 * Created by jchheda on 7/25/2017.
 */

public class FileUploadHandler implements Runnable {

    Map<Object, Object> mChunk = null;
    String mFileName = "";
    private OnWebServiceResponse _onWebServiceResponse;

    public FileUploadHandler(String fileName, Map<Object, Object> chunk, OnWebServiceResponse onWebServiceResponse) {
        this.mChunk = chunk;
        this.mFileName = fileName;
        this._onWebServiceResponse = onWebServiceResponse;
    }

    @Override
    public void run() {
        FileBO fileBO = new FileBO();
        fileBO.SaveChunkFiles(mFileName, mChunk,_onWebServiceResponse);
    }
}
