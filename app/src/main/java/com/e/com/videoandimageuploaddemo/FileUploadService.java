package com.e.com.videoandimageuploaddemo;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public class FileUploadService extends Service
    {

        // public int counter = 0;
        private static OnWebServiceResponse _onWebServiceResponse=null;

        public FileUploadService(Context applicationContext, OnWebServiceResponse onWebServiceResponse)
            {
                super();
                _onWebServiceResponse = onWebServiceResponse;
            }

        public FileUploadService()
            {
            }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId)
            {
                super.onStartCommand(intent, flags, startId);
                //startTimer();
                String fileName = intent.getStringExtra("FileName");
                StartFileUpload(fileName);
                return START_STICKY;
            }

        @Override
        public void onDestroy()
            {
                super.onDestroy();
       /* Intent broadcastIntent = new Intent("com.eemphasys.eservice.UI.Services.RestartFileUpload");
        sendBroadcast(broadcastIntent);*/
                //stoptimertask();
            }

        void StartFileUpload(String fileName)
            {
                if (fileName != null && !fileName.equals(""))
                    {
                        new FileUploadPool(fileName, _onWebServiceResponse).run();

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

                this.stopSelf();
            }

    /*private Timer timer;
    private TimerTask timerTask;
    long oldTime = 0;

    public void startTimer() {
        //set a new Timer
        timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, to wake up every 1 second
        timer.schedule(timerTask, 1000, 2000); //
    }

    *//**
     * it sets the timer to print the counter every x seconds
     *//*
    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                Log.i("in timer", "in timer ++++  " + (counter++));
                String a = "123";
                String b = "123";
                String v = "123";
                String c = "123";
                String s = "123";
                String d = "123";
                String f = "123";
            }
        };
    }

    */

        /**
         * not needed
         *//*
    public void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }*/
        @Override
        public IBinder onBind(Intent intent)
            {
                // TODO: Return the communication channel to the service.
                // throw new UnsupportedOperationException("Not yet implemented");
                return null;
            }
    }
