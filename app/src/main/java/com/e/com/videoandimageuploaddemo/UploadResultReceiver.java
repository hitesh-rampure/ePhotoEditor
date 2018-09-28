package com.e.com.videoandimageuploaddemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;

public class UploadResultReceiver extends ResultReceiver
    {
        private Context _context;

        public UploadResultReceiver(Handler handler, Context context)
            {
                super(handler);
                _context = context;

            }

        public UploadResultReceiver()
            {
                super(new Handler());
            }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData)
            {
                super.onReceiveResult(resultCode, resultData);
                if (resultCode == FileUploadService.UPDATE_PROGRESS)
                    {
                        int progress = resultData.getInt("progress");
                        showNotificationProgress(progress);
                        Log.e("Progress","--------"+progress);
                        if (progress == FileUploadService.fileChunks.size()-1)
                            {
                                cancelAllNotification();
                            }
                    }
            }

        private void showNotificationProgress(int progress)
            {
                Intent intent = new Intent();
                final PendingIntent pendingIntent = PendingIntent.getActivity(
                        _context, 0, intent, 0);
                Notification mBuilder = new Notification.Builder(_context)
                        .setContentTitle("Uploading")
                        .setContentText("Progress")
                        .setContentIntent(pendingIntent)
                        .setProgress(FileUploadService.fileChunks.size()
                                , progress, false)
                        .setAutoCancel(true)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .build();


                mBuilder.flags = mBuilder.flags | Notification.FLAG_NO_CLEAR;
                NotificationManager notificationManager = (NotificationManager) _context.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(1, mBuilder);
            }

        private void cancelAllNotification()
            {
                NotificationManager notificationManager = (NotificationManager) _context.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.cancelAll();
            }

    }
