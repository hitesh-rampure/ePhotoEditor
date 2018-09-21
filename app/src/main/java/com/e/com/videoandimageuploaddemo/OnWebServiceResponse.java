package com.e.com.videoandimageuploaddemo;

public interface OnWebServiceResponse
    {

        void onSuccess(int fileChunckUploaded);

        void onFailure();
    }
