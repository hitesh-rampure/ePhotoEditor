package com.e.com.videoandimageuploaddemo;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.e.com.videoandimageuploaddemo.ImageEditing.EditImageActivity;

public class ImageEditFragment extends DialogFragment implements OnTouchListener, OnClickListener
    {

        private ImageView imageView;
        private ScaleGestureDetector mScaleGestureDetector;
        private float mScaleFactor = 1.0f;
        private ImageView closeButton;
        private LinearLayout upperLayout;
        private LinearLayout bottomLayout;
        private final int ANIMATION_TIME = 800;
        private String url;
        private ImageView editImageView;
        public final int EDIT_IMAGE_REQUEST_CODE = 1111;
        private SaveEditedImagesListener _saveEditedImagesListener;


        @Override
        public void onCreate(Bundle savedInstanceState)
            {
                super.onCreate(savedInstanceState);
            }


        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
            {
                return inflater.inflate(R.layout.layout_edit, container, false);
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

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
            {
                super.onViewCreated(view, savedInstanceState);
                initView(view);
            }

        private void initView(View view)
            {
                imageView = view.findViewById(R.id.superSamplingView);
                closeButton = view.findViewById(R.id.closeIcon);
                editImageView = view.findViewById(R.id.editImageView);
                editImageView.setOnClickListener(this);
                closeButton.setOnClickListener(this);
                imageView.setOnTouchListener(this);

                view.findViewById(R.id.edit_parent).setOnClickListener(this);


                upperLayout = (view).findViewById(R.id.upperLayout);
                bottomLayout = (view).findViewById(R.id.bottomLayout);

                mScaleGestureDetector = new ScaleGestureDetector(getActivity(), new ScaleListener());
                url = getArguments().getString("url");

                Glide.with(getActivity()).load(url)
                        .thumbnail(1f)
                        .into((imageView));

            }

        @Override
        public boolean onTouch(View v, MotionEvent event)
            {
                mScaleGestureDetector.onTouchEvent(event);
                return true;
            }


        @Override
        public void onClick(View v)
            {
                switch (v.getId())
                    {
                        case R.id.closeIcon:
                            disMissFragment();
                            break;

                        case R.id.edit_parent:
                            toggleView();
                            break;

                        case R.id.editImageView:
                            editImage();
                            break;

                    }

            }

        private void toggleView()
            {
                if (upperLayout.getVisibility() == View.VISIBLE && bottomLayout.getVisibility() == View.VISIBLE)
                    {
                        upperLayout.setVisibility(View.GONE);
                        bottomLayout.setVisibility(View.GONE);
                    }
                else
                    {
                        upperLayout.setVisibility(View.VISIBLE);
                        bottomLayout.setVisibility(View.VISIBLE);
                    }
            }


        private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener
            {
                @Override
                public boolean onScale(ScaleGestureDetector scaleGestureDetector)
                    {
                        mScaleFactor *= scaleGestureDetector.getScaleFactor();
                        mScaleFactor = Math.max(0.1f,
                                Math.min(mScaleFactor, 10.0f));
                        imageView.setScaleX(mScaleFactor);
                        imageView.setScaleY(mScaleFactor);
                        return true;
                    }
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

        public void editImage()
            {
                Intent intent = new Intent(getActivity(), EditImageActivity.class);
                intent.putExtra("uri", url);
                intent.putExtra("id", getArguments().getInt("id"));
                startActivityForResult(intent, EDIT_IMAGE_REQUEST_CODE);

            }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data)
            {
                super.onActivityResult(requestCode, resultCode, data);

                if (resultCode == Activity.RESULT_OK)
                    {
                        if (requestCode == EDIT_IMAGE_REQUEST_CODE)
                            {
                                Intent intent = new Intent();
                                intent.putExtra("url", data.getExtras().getString("url"));
                                intent.putExtra("id", data.getExtras().getInt("id"));
                                _saveEditedImagesListener.onSaveEditedImages(data.getExtras().getString("url"), data.getExtras().getInt("id"));
                                dismiss();
                            }

                    }
            }

        public void setOnSaveEditedImageListener(SaveEditedImagesListener saveEditedImagesListener)
            {
                _saveEditedImagesListener = saveEditedImagesListener;
            }

    }
