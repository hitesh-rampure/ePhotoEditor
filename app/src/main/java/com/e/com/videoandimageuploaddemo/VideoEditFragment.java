package com.e.com.videoandimageuploaddemo;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.github.tcking.giraffecompressor.GiraffeCompressor;
import com.github.tcking.giraffecompressor.GiraffeCompressor.Result;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

public class VideoEditFragment extends DialogFragment implements OnClickListener, OnWebServiceResponse
    {
        private final int ANIMATION_TIME = 800;
        private VideoView videoView;
        private MediaController media;
        private FrameLayout parentVideoLayout;
        private String uri;
        private Handler handler;
        private FileUploadService mFileUploadService;
        private Intent mServiceIntent;
        private ProgressBar progressBar;
        private int BIT_RATE = 999999;
        private float SCALE_FACTOR = 1.0f;

        public static String filePath = "";
        private UploadResultReceiver uploadResultReceiver;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
            {
                return inflater.inflate(R.layout.video_edit_layout, container);
            }


        @SuppressLint("ResourceType")
        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
            {
                super.onViewCreated(view, savedInstanceState);

                videoView = view.findViewById(R.id.video_view);
                parentVideoLayout = (view.findViewById(R.id.layout_video));
                uri = getArguments().getString("uri");
                uploadResultReceiver = new UploadResultReceiver(new Handler(), getActivity());

                handler = new Handler();
                new BackgroundAsyncTask().execute(uri);
                ImageView closeVideo = view.findViewById(R.id.closeVideo);
                closeVideo.setOnClickListener(this);

                ImageView shareVideo = view.findViewById(R.id.shareVideo);
                shareVideo.setOnClickListener(this);

                ImageView deleteVideo = view.findViewById(R.id.deleteVideo);
                deleteVideo.setOnClickListener(this);
                view.findViewById(R.id.uploadVideo).setOnClickListener(this);

                progressBar = view.findViewById(R.id.pb_default);
            }

        @Override
        public void onStart()
            {
                super.onStart();
                Dialog d = getDialog();
                if (d != null)
                    {
                        int width = ViewGroup.LayoutParams.MATCH_PARENT;
                        int height = ViewGroup.LayoutParams.MATCH_PARENT;
                        d.getWindow().setLayout(width, height);
                        final View decorView = getDialog()
                                .getWindow()
                                .getDecorView();
                        ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(decorView,
                                PropertyValuesHolder.ofFloat("scaleX", 0.0f, 1.0f),
                                PropertyValuesHolder.ofFloat("scaleY", 0.0f, 1.0f),
                                PropertyValuesHolder.ofFloat("alpha", 0.0f, 1.0f));
                        scaleDown.setDuration(ANIMATION_TIME);
                        scaleDown.start();

                    }
            }


        void compreesVideo()
            {

                progressBar.setVisibility(View.VISIBLE);

                final File inputFile = new File(uri);



                try
                    {
                        final File outputFile = new File("/sdcard/temp_"+System.currentTimeMillis());
                        GiraffeCompressor.init(getActivity());

                        GiraffeCompressor.create() //two implementations: mediacodec and ffmpeg,default is mediacodec
                                .input(inputFile) //set video to be compressed
                                .output(outputFile) //set compressed video output
                                .bitRate(BIT_RATE)//set bitrate 码率
                                .resizeFactor(SCALE_FACTOR)//set video resize factor
                                // .watermark("/sdcard/videoCompressor/watermarker.png")//add watermark(take a long time) 水印图片(需要长时间处理)
                                .ready()
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Subscriber<Result>()
                                    {
                                        @Override
                                        public void onCompleted()
                                            {

                                                try
                                                    {
                                                        Toast.makeText(getActivity(), "Compressed", Toast.LENGTH_SHORT).show();
                                                        Log.e("OutputFilePath", outputFile.getAbsolutePath());
                                                        uploadVideo(outputFile);

                                                    } catch (Exception e)
                                                    {
                                                        e.printStackTrace();
                                                    }
                                            }

                                        @Override
                                        public void onError(Throwable e)
                                            {
                                                try
                                                    {
                                                        uploadVideo(inputFile);
                                                    } catch (Exception ex)
                                                    {
                                                        ex.printStackTrace();
                                                    }
                                            }

                                        @Override
                                        public void onNext(GiraffeCompressor.Result s)
                                            {

                                            }
                                    });
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }

            }

        private void releasePlayer()
            {
                videoView.stopPlayback();
            }

        @Override
        public void onStop()
            {
                super.onStop();
                releasePlayer();
            }

        @Override
        public void onPause()
            {
                super.onPause();
                videoView.pause();
            }

        private void disMissFragment()
            {
                final View decorView = getDialog()
                        .getWindow()
                        .getDecorView();

                ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(decorView,
                        PropertyValuesHolder.ofFloat("scaleX", 1.0f, 0.0f),
                        PropertyValuesHolder.ofFloat("scaleY", 1.0f, 0.0f),
                        PropertyValuesHolder.ofFloat("alpha", 1.0f, 0.0f));
                scaleDown.addListener(new Animator.AnimatorListener()
                    {
                        @Override
                        public void onAnimationEnd(Animator animation)
                            {
                                dismiss();
                            }

                        @Override
                        public void onAnimationStart(Animator animation)
                            {
                            }

                        @Override
                        public void onAnimationCancel(Animator animation)
                            {
                            }

                        @Override
                        public void onAnimationRepeat(Animator animation)
                            {
                            }
                    });
                scaleDown.setDuration(ANIMATION_TIME);
                scaleDown.start();

            }

        @Override
        public void onClick(View v)
            {
                switch (v.getId())
                    {
                        case R.id.closeVideo:
                            disMissFragment();
                            break;

                        case R.id.uploadVideo:

                            try
                                {

                                    compreesVideo();
                                    //uploadVideo();
                                } catch (Exception e)
                                {
                                    e.printStackTrace();
                                }
                            break;

                        case R.id.shareVideo:
                            shareVideo();
                            break;

                        case R.id.deleteVideo:
                            deleteVideo();
                            break;
                    }
            }

        private void deleteVideo()
            {

            }

        private void shareVideo()
            {
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("video/*");
                File imageFileToShare = new File(uri);


                Uri apkURI = FileProvider.getUriForFile(
                        getActivity(),
                        getActivity().getApplicationContext()
                                .getPackageName() + ".provider", imageFileToShare);
                share.setDataAndType(apkURI, Intent.EXTRA_MIME_TYPES);
                share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                share.putExtra(Intent.EXTRA_STREAM, uri);
                startActivity(Intent.createChooser(share, "Share Video!"));
            }

        int _progress;

        @Override
        public void onSuccess(final int fileChunckUploaded)
            {

                getActivity().runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                            {
                                progressBar.setVisibility(View.GONE);
//                                Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();

                            }
                    });

                _progress = fileChunckUploaded;
            }

        @Override
        public void onFailure()
            {

            }


        public class BackgroundAsyncTask extends AsyncTask<String, Uri, Void>
            {
                ProgressDialog dialog;

                protected void onPreExecute()
                    {
                        dialog = new ProgressDialog(getActivity());
                        dialog.setMessage("Loading, Please Wait...");
                        dialog.setCancelable(true);
                        dialog.show();
                    }

                protected void onProgressUpdate(final Uri... uri)
                    {

                        try
                            {

                                media = new MediaController(getActivity());
                                media.setAnchorView(videoView);
                                videoView.setMediaController(media);
                                videoView.setVideoURI(Uri.parse(uri[0].toString()));

                                ((ViewGroup) media.getParent()).removeView(media);

                                FrameLayout.LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                layoutParams.gravity = Gravity.BOTTOM;
                                media.setLayoutParams(layoutParams);
                                parentVideoLayout.addView(media, layoutParams);


                                videoView.setOnPreparedListener(new OnPreparedListener()
                                    {

                                        public void onPrepared(MediaPlayer mp)
                                            {
                                                //mp.start();
                                                media.show(0);
                                                handler.postDelayed(updateProgress, 0);
                                                dialog.dismiss();
                                                mp.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT);
                                            }
                                    });


                            } catch (IllegalArgumentException e)
                            {
                                e.printStackTrace();
                            } catch (IllegalStateException e)
                            {
                                e.printStackTrace();
                            } catch (SecurityException e)
                            {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                        videoView.setOnCompletionListener(new OnCompletionListener()
                            {
                                @Override
                                public void onCompletion(MediaPlayer mp)
                                    {
                                        handler.removeCallbacks(updateProgress);
                                        mp.seekTo(0);
                                        mp.pause();
                                        handler.postDelayed(updateProgress, 1000);
                                    }
                            });
                    }

                @Override
                protected Void doInBackground(String... params)
                    {
                        try
                            {
                                Uri uri = Uri.parse(params[0]);
                                publishProgress(uri);
                            } catch (Exception e)
                            {
                                e.printStackTrace();
                            }

                        return null;
                    }


                private Runnable updateProgress = new Runnable()
                    {
                        @Override
                        public void run()
                            {

                                media.show(0);
                                handler.postDelayed(updateProgress, 1000);
                            }
                    };
            }


        void uploadVideo(File inputFile) throws IOException
            {

                File imageFileToShare = inputFile;
                filePath = imageFileToShare.getAbsolutePath();


                ArrayList<Map<Object, Object>> fileChunks = fileToChunks(imageFileToShare.getAbsolutePath().toString(),
                        imageFileToShare.getName());
                ArrayList<String> files = null;

                if (fileChunks != null && fileChunks.size() > 0)
                    {
                        // CDHelper.SaveFileChunks(fileName, fileChunks);
                        if (files == null)
                            files = new ArrayList<String>();
                        files.add(imageFileToShare.getName());
                        FileUploadService.fileChunks = fileChunks;
                    }

                if (files != null && files.size() > 0)
                    {
                        //4. call the service to send chunks to server
                        {

                            uploadResultReceiver = new UploadResultReceiver(new Handler(), getActivity());

                            mFileUploadService = new FileUploadService(getActivity(), this, uploadResultReceiver);

                            mServiceIntent = new Intent(getActivity(), mFileUploadService.getClass());

                            mServiceIntent.putExtra("FileName", imageFileToShare.getName());

                            if (isMyServiceRunning(mFileUploadService.getClass()))
                                getActivity().stopService(mServiceIntent);
                            getActivity().startService(mServiceIntent);
                        }
                    }
            }

        private ArrayList<Map<Object, Object>> fileToChunks(String filePath, String fileName)
            {
                ArrayList<Map<Object, Object>> chunks = null;
                File file = new File(filePath);
                if (file.exists())
                    {
                        chunks = new ArrayList<>();
                        FileInputStream fis;
                        ByteArrayOutputStream bos;
                        int bytesAvailable;
                        byte[] buffer;
                        try
                            {
                                fis = new FileInputStream(file);
                                bytesAvailable = fis.available();
                                int bufferSize = Math.min(bytesAvailable, AppConstants.VideoFileChunkSize);
                                buffer = new byte[bufferSize];
                                bos = new ByteArrayOutputStream();
                                for (int readNum; (readNum = fis.read(buffer)) != -1; )
                                    {
                                        bos.write(buffer, 0, readNum);
                                    }

                                byte[] filebytes = bos.toByteArray();
                                int j = 1;
                                for (int i = 0; i <= filebytes.length; i += AppConstants.VideoFileChunkSize)
                                    {
                                        Map<Object, Object> fileChunk = new HashMap();
                                        fileChunk.put("ChunkID", j);
                                        fileChunk.put("FileName", fileName);
                                        fileChunk.put("Offset", i);
                                        fileChunk.put("Uploaded", "0");
                                        chunks.add(fileChunk);
                                        j++;
                                    }
                                for (Map<Object, Object> chunk : chunks)
                                    chunk.put("ChunkName", fileName + ".part_" + chunk.get("ChunkID").toString() + "." + String.valueOf(chunks.size())+".mp4");

                            } catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                    }
                return chunks;
            }

        public boolean isMyServiceRunning(Class<?> serviceClass)
            {
                ActivityManager manager = (ActivityManager) getActivity().getSystemService(getActivity().ACTIVITY_SERVICE);
                for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE))
                    {
                        if (serviceClass.getName().equals(service.service.getClassName()))
                            {
                                return true;
                            }
                    }
                return false;
            }

    }
