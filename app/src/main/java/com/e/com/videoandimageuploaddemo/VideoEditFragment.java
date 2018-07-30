package com.e.com.videoandimageuploaddemo;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.net.URI;

public class VideoEditFragment extends DialogFragment implements OnClickListener
    {
        private final int ANIMATION_TIME = 800;
        private VideoView videoView;
        private MediaController media;
        private FrameLayout parentVideoLayout;
        private String uri;
        private Handler handler;


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

                handler = new Handler();
                new BackgroundAsyncTask().execute(uri);
                ImageView closeVideo = view.findViewById(R.id.closeVideo);
                closeVideo.setOnClickListener(this);

                ImageView shareVideo = view.findViewById(R.id.shareVideo);
                shareVideo.setOnClickListener(this);

                ImageView deleteVideo = view.findViewById(R.id.deleteVideo);
                deleteVideo.setOnClickListener(this);
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
                                                mp.start();
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


    }
