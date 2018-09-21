package com.e.com.videoandimageuploaddemo;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class FileUploadService extends IntentService implements OnWebServiceResponse
    {

        // public int counter = 0;
        public static final int UPDATE_PROGRESS = 0;
        private static OnWebServiceResponse _onWebServiceResponse = null;
        private String mFileName = "";
        private int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors() * 2;
        private final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;
        public static ArrayList<Map<Object, Object>> fileChunks = new ArrayList<>();
        private Context _context;
        private static UploadResultReceiver uploadResultReceiver;
        public int counter = 0;

        public FileUploadService(Context applicationContext, OnWebServiceResponse onWebServiceResponse, UploadResultReceiver uploadResult)
            {
                super("Service");
                uploadResultReceiver = uploadResult;
                _onWebServiceResponse = onWebServiceResponse;
            }

        public FileUploadService()
            {
                super("Service");
            }

//        @Override
//        public int onStartCommand(Intent intent, int flags, int startId)
//            {
//                super.onStartCommand(intent, flags, startId);
//                //startTimer();
////                String fileName = intent.getStringExtra("FileName");
////                StartFileUpload(fileName);
//                return START_STICKY;
//            }

        @Override
        public void onDestroy()
            {
                super.onDestroy();

//                Log.e("OnDestroy","Service On Destroy");
//                Intent broadcastIntent = new Intent("ac.in.ActivityRecognition.RestartSensor");
//                sendBroadcast(broadcastIntent);
//                stoptimertask();
            }


        private Timer timer;
        private TimerTask timerTask;

        public void startTimer()
            {
                //set a new Timer
                timer = new Timer();

                //initialize the TimerTask's job
                initializeTimerTask();

                //schedule the timer, to wake up every 1 second
                timer.schedule(timerTask, 1000, 1000); //
            }

        public void initializeTimerTask()
            {
                timerTask = new TimerTask()
                    {
                        public void run()
                            {
                                Log.i("in timer", "in timer ++++  " + (counter++));
                            }
                    };
            }

        public void stoptimertask()
            {
                //stop the timer, if it's not already null
                if (timer != null)
                    {
                        timer.cancel();
                        timer = null;
                    }
            }

        void StartFileUpload(String fileName)
            {
                if (fileName != null && !fileName.equals(""))
                    {
                        new FileUploadPool(fileName, _onWebServiceResponse).run();
                        try
                            {
                                ArrayList<Map<Object, Object>> chunks = fileChunks;
                                if (chunks != null && chunks.size() > 0)
                                    {
                                        //            if (fileData != null && fileData.size() > 0 && fileData.get("FilePath") != null)
                                        {
                                            String filePath = VideoEditFragment.filePath;//fileData.get("FilePath").toString();
                                            Uri uri = Uri.parse(filePath);
                                            File file = new File(uri.getPath());

                                            if (file.exists())
                                                {
                                                    FileInputStream fis;
                                                    ByteArrayOutputStream bos = null;
                                                    int bytesAvailable;
                                                    byte[] bufferChunk;
                                                    try
                                                        {
                                                            fis = new FileInputStream(file);
                                                            bytesAvailable = fis.available();
                                                            int bufferSize = Math.min(bytesAvailable, AppConstants.VideoFileChunkSize);
                                                            bufferChunk = new byte[bufferSize];
                                                            bos = new ByteArrayOutputStream();

                                                            for (int readNum; (readNum = fis.read(bufferChunk)) != -1; )
                                                                {
                                                                    bos.write(bufferChunk, 0, readNum);
                                                                }

                                                        } catch (Exception e)
                                                        {
                                                            e.printStackTrace();
                                                        }


                                                    //Bitmap bm = BitmapFactory.decodeStream(fis);
                                                    // ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                                    //bm.compress(CompressFormat.JPEG, 100, baos);
                                                    byte[] filebytes = bos.toByteArray();
                                                    //originalImage.recycle();
                                                    ArrayList<Map<Object, Object>> finalChunks = new ArrayList<>();
                                                    try
                                                        {

                                                            for (int i = 0; i < chunks.size(); i++)
                                                                {
                                                                    int offSet = Double.valueOf(chunks.get(i).get("Offset").toString()).intValue();
                                                                    int diff = filebytes.length - offSet > AppConstants.VideoFileChunkSize
                                                                            ? AppConstants.VideoFileChunkSize
                                                                            : filebytes.length - offSet;

                                                                    byte[] buffer = new byte[diff];
                                                                    System.arraycopy(filebytes, offSet, buffer, 0, diff);
                                                                    Map<Object, Object> tempChunk = new HashMap<>();
                                                                    tempChunk.put("ChunkData", Base64.encodeToString(buffer, Base64.DEFAULT));
                                                                    tempChunk.putAll(chunks.get(i));
                                                                    finalChunks.add(tempChunk);
                                                                }
                                                        } catch (Exception e)
                                                        {
                                                            e.printStackTrace();
                                                        }
                                                    ThreadFactory threadFactory = Executors.defaultThreadFactory();
                                                    //creating the ThreadPoolExecutor
                                                    int KEEP_ALIVE_TIME = 60;
                                                    ThreadPoolExecutor executorPool = new ThreadPoolExecutor(NUMBER_OF_CORES, NUMBER_OF_CORES,
                                                            KEEP_ALIVE_TIME, KEEP_ALIVE_TIME_UNIT, new LinkedBlockingQueue<Runnable>(),
                                                            threadFactory);
                                                    FileBO fileBO = new FileBO();
                                                    for (Map<Object, Object> chunk : finalChunks)
                                                        //executorPool.execute(new FileUploadHandler(mFileName, chunk, this));

                                                        {
                                                            Log.e("Chunk",chunk.get("ChunkID").toString());
                                                            fileBO.SaveChunkFiles(fileName, chunk, this);
                                                        }
                                                }
                                        }
                                    }
                            } catch (Exception ex)
                            {
                                ex.printStackTrace();
                            }

                    }
                else
                    {

                        //            ArrayList<String> files = CDHelper.GetPendingFilesName();
//            if (files != null && files.size() > 0) {
//                for (String file : files) {
//                    new FileUploadPool(file).run();
//                }
//            }
                    }

//                this.stopSelf();
            }

        @Override
        protected void onHandleIntent(@Nullable Intent intent)
            {

                String fileName = intent.getStringExtra("FileName");
                StartFileUpload(fileName);

            }

        @Override
        public void onSuccess(int fileChunckUploaded)
            {

                startTimer();
                Bundle resultData = new Bundle();
                resultData.putInt("progress", fileChunckUploaded);
                uploadResultReceiver.send(UPDATE_PROGRESS, resultData);
                _onWebServiceResponse.onSuccess(fileChunckUploaded);
            }

        @Override
        public void onFailure()
            {

            }
    }
