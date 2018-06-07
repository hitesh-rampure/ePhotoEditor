package com.e.com.videoandimageuploaddemo;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class VideoEditFragment extends DialogFragment
    {
        private final int ANIMATION_TIME = 800;
        private VideoView videoView;

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

                videoView = (VideoView) view.findViewById(R.id.video_view);
                final MediaController mediaController = new MediaController(getActivity());
                mediaController.setMediaPlayer(videoView);
                mediaController.setId(123);
                videoView.setMediaController(mediaController);
                String uri = getArguments().getString("uri");
                videoView.setVideoURI(Uri.parse(uri));
                ((ViewGroup) mediaController.getParent()).removeView(mediaController);
                FrameLayout parentVideoLayout = ((FrameLayout) view.findViewById(R.id.layout_video));

                FrameLayout.LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.gravity = Gravity.BOTTOM;
                mediaController.setLayoutParams(layoutParams);
                parentVideoLayout.addView(mediaController, layoutParams);
                videoView.setOnCompletionListener(new OnCompletionListener()
                    {
                        @Override
                        public void onCompletion(MediaPlayer mp)
                            {

                                videoView.seekTo(0);
                                Toast.makeText(getActivity(), "Playback Completed", Toast.LENGTH_SHORT).show();
                            }
                    });
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
                        videoView.start();
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


    }
