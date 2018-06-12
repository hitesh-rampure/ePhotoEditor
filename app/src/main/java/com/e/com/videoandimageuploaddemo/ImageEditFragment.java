package com.e.com.videoandimageuploaddemo;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.e.com.videoandimageuploaddemo.ImageEditing.EditImageActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Random;

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
        private String _savedImagePath;
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
                ImageView shareImageView = view.findViewById(R.id.shareImageView);
                shareImageView.setOnClickListener(this);
                editImageView.setOnClickListener(this);
                closeButton.setOnClickListener(this);
                imageView.setOnTouchListener(this);

                view.findViewById(R.id.edit_parent).setOnClickListener(this);


                upperLayout = (view).findViewById(R.id.upperLayout);
                bottomLayout = (view).findViewById(R.id.bottomLayout);

                mScaleGestureDetector = new ScaleGestureDetector(getActivity(), new ScaleListener());
                url = getArguments().getString("url");

                if (url != null && (url.startsWith("http") || url.startsWith("https")))
                    {
                        Glide.with(getActivity())
                                .asBitmap()
                                .load(url).into(new SimpleTarget<Bitmap>()
                            {
                                @Override
                                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition)
                                    {
                                        saveImage(resource);
                                        imageView.setImageBitmap(resource);
                                    }
                            });

                    }
                else
                    {
                        Glide.with(getActivity()).load(url)
                                .thumbnail(1f)
                                .into((imageView));
                    }

            }

        private String saveImage(Bitmap image)
            {
                String savedImagePath = null;
                Random random = new Random();
                String imageFileName = "JPEG_" + random.nextInt() + ".jpg";
                File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                        + "/e-Emphasys");
                boolean success = true;
                if (!storageDir.exists())
                    {
                        success = storageDir.mkdirs();
                    }
                if (success)
                    {
                        File imageFile = new File(storageDir, imageFileName);
                        savedImagePath = imageFile.getAbsolutePath();
                        try
                            {
                                OutputStream fOut = new FileOutputStream(imageFile);
                                image.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                                fOut.close();
                            } catch (Exception e)
                            {
                                e.printStackTrace();
                            }

                        galleryAddPic(savedImagePath);
                        _savedImagePath = savedImagePath;
                    }
                return savedImagePath;
            }

        private void galleryAddPic(String imagePath)
            {
                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                File f = new File(imagePath);
                Uri contentUri = Uri.fromFile(f);
                mediaScanIntent.setData(contentUri);
                getActivity().sendBroadcast(mediaScanIntent);
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

                        case R.id.shareImageView:
                            shareImage();
                            break;

                    }

            }

        private void shareImage()
            {
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("image/*");
                File imageFileToShare = new File(url);

                if (!TextUtils.isEmpty(_savedImagePath))
                    {
                        imageFileToShare = new File(_savedImagePath);
                    }
                Uri uri = Uri.fromFile(imageFileToShare);
                share.putExtra(Intent.EXTRA_STREAM, uri);
                startActivity(Intent.createChooser(share, "Share Image!"));
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
                if (TextUtils.isEmpty(_savedImagePath))
                    {
                        Intent intent = new Intent(getActivity(), EditImageActivity.class);
                        intent.putExtra("uri", url);
                        intent.putExtra("id", getArguments().getInt("id"));
                        startActivityForResult(intent, EDIT_IMAGE_REQUEST_CODE);
                    }
                else
                    {
                        Intent intent = new Intent(getActivity(), EditImageActivity.class);
                        intent.putExtra("uri", Uri.fromFile(new File(_savedImagePath)).toString());
                        intent.putExtra("id", getArguments().getInt("id"));
                        startActivityForResult(intent, EDIT_IMAGE_REQUEST_CODE);
                    }

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
                                //  dismiss();
                                _savedImagePath = data.getExtras().getString("url");
                                Glide.with(getActivity()).load(data.getExtras().getString("url"))
                                        .thumbnail(1f)
                                        .into((imageView));
                            }
                    }
            }

        public void setOnSaveEditedImageListener(SaveEditedImagesListener saveEditedImagesListener)
            {
                _saveEditedImagesListener = saveEditedImagesListener;
            }
    }
