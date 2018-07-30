package com.e.com.videoandimageuploaddemo;

import android.Manifest;
import android.Manifest.permission;
import android.animation.Animator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.TextUtils;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.transition.Fade;
import android.transition.TransitionSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bignerdranch.android.multiselector.MultiSelector;
import com.bignerdranch.android.multiselector.SelectableHolder;
import com.e.com.videoandimageuploaddemo.MultipleSelectAdapter.PictureViewHolder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class TabFragment extends android.support.v4.app.Fragment implements MultiSelectorListener, SaveEditedImagesListener
    {
        int position;
        private RecyclerView _recyclerView;
        private LinkedList<SelectedData> selectedDataList;
        private MultiSelector multiSelector;
        private final int SELECT_PICTURE = 1;
        private HashSet<String> selectedImagePath;
        private int CAMERA_REQUEST_FOR_IMAGE = 1888;
        private int CAMERA_REQUEST_FOR_VIDEO = 1889;
        private updateViewPager updateViewPager;
        private Uri imageUri;

        public static TabFragment getInstance(int position)
            {
                Bundle bundle = new Bundle();
                bundle.putInt("pos", position);
                TabFragment tabFragment = new TabFragment();
                tabFragment.setArguments(bundle);
                return tabFragment;
            }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState)
            {
                super.onCreate(savedInstanceState);
                position = getArguments().getInt("pos");
            }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState)
            {
                return inflater.inflate(R.layout.fragment_tab, container, false);
            }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
            {
                super.onViewCreated(view, savedInstanceState);
                ((AppCompatActivity) getActivity()).getSupportActionBar().show();

                selectedDataList = new LinkedList<>();
                setHasOptionsMenu(true);
                multiSelector = new MultiSelector();
                _recyclerView = view.findViewById(R.id.recyclerView);
                _recyclerView.setHasFixedSize(false);
                _recyclerView.setNestedScrollingEnabled(false);


                if (position == 0)
                    {
                        for (int i = 0; i < 5; i++)
                            {
                                SelectedData selectedData = new SelectedData();
                                selectedData.setId(i);
                                selectedData.setDataType(DataType.ITEM_TYPE_PICTURES);
                                selectedData.setType("pictures");
                                selectedData.setSolved(false);
                                selectedData.setUrl("http://i.imgur.com/zuG2bGQ.jpg");
                                selectedDataList.add(selectedData);
                            }
                    }
                else if (position == 1)
                    {
                        for (int i = 0; i < 5; i++)
                            {
                                SelectedData selectedData = new SelectedData();
                                selectedData.setId(i);
                                selectedData.setDataType(DataType.ITEM_TYPE_VIDEOS);
                                selectedData.setType("video");
                                selectedData.setSolved(false);
                                String path = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.big_buck_bunny;
                                //selectedData.setUrl("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4");
                                selectedData.setUrl(path);
                                selectedDataList.add(selectedData);
                            }
                    }
                _recyclerView.setLayoutManager(new

                        GridLayoutManager(getActivity(), 5));


                _recyclerView.addItemDecoration(new

                        SpacesItemDecoration(10));

                ViewTreeObserver viewTreeObserver = _recyclerView.getViewTreeObserver();
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()

                    {
                        @Override
                        public void onGlobalLayout()
                            {
                                calculateSize();
                            }
                    });

                MultipleSelectAdapter multipleSelectAdapter = new MultipleSelectAdapter(getActivity(), multiSelector, selectedDataList, this, position);

                _recyclerView.setAdapter(multipleSelectAdapter);
            }


        private void calculateSize()
            {
                int spanCount = (int) Math.floor(_recyclerView.getWidth() / convertDPToPixels(120));
                ((GridLayoutManager) _recyclerView.getLayoutManager()).setSpanCount(spanCount);
            }

        private float convertDPToPixels(int dp)
            {
                DisplayMetrics metrics = new DisplayMetrics();
                getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
                float logicalDensity = metrics.density;
                return dp * logicalDensity;
            }

        @Override
        public void onLongPress(SelectableHolder viewHolder, boolean isSelected)
            {
            }

        @Override
        public void onClick(ViewHolder view, boolean isSolved, int position, boolean isChecked, boolean isEditCheckBoxEnabled)
            {
                if (isEditCheckBoxEnabled)
                    {
                        int counter = 0;
                        selectedDataList.get(position).setSolved(isSolved);
                        selectedDataList.get(position).setChecked(isChecked);
                        for (SelectedData selectedData : selectedDataList)
                            {
                                if (selectedData.isChecked == false)
                                    {
                                        counter = counter + 1;
                                    }
                            }

                        if (counter == selectedDataList.size())
                            {

                                for (SelectedData selectedData : selectedDataList)
                                    {
                                        selectedData.setSolved(false);
                                    }
                                _recyclerView.getAdapter().notifyDataSetChanged();
                            }
                    }
                else if (selectedDataList.get(position).getDataType() == DataType.ITEM_TYPE_PICTURES)
                    {
                        ImageEditFragment imageEditFragment = new ImageEditFragment();
                        setExitTransition(new Fade());
                        Bundle bundle = new Bundle();
                        bundle.putString("url", selectedDataList.get(position).getUrl());
                        bundle.putInt("id", selectedDataList.get(position).getId());
                        imageEditFragment.setArguments(bundle);
                        imageEditFragment.setOnSaveEditedImageListener(this);

                        FragmentManager fragmentManager = getActivity().getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        imageEditFragment.show(fragmentManager, "tag");
                        fragmentTransaction.commit();
                    }
                else if (selectedDataList.get(position).getDataType() == DataType.ITEM_TYPE_VIDEOS)
                    {


                        VideoEditFragment videoEditFragment = new VideoEditFragment();
                        Bundle bundle = new Bundle();
                        if (selectedDataList.get(position).getUrl().startsWith("android.resource://"))
                            {
                                bundle.putString("uri", selectedDataList.get(position).getUrl());
                            }
                        else
                            {
                                bundle.putString("uri", getRealPathFromURI(getActivity(), Uri.parse(selectedDataList.get(position).getUrl())));
                            }
                        videoEditFragment.setArguments(bundle);
                        FragmentManager fragmentManager = getActivity().getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        videoEditFragment.show(fragmentManager, "tag");
                        fragmentTransaction.commit();
                    }
            }


        public class SpacesItemDecoration extends RecyclerView.ItemDecoration
            {
                private int space;

                public SpacesItemDecoration(int space)
                    {
                        this.space = space;
                    }

                @Override
                public void getItemOffsets(Rect outRect, View view,
                                           RecyclerView parent, RecyclerView.State state)
                    {
                        outRect.left = space;
                        outRect.right = space;
                        outRect.bottom = space;

                        // Add top margin only for the first item to avoid double space between items
                        if (parent.getChildLayoutPosition(view) == 0)
                            {
                                outRect.top = space;
                            }
                        else
                            {
                                outRect.top = space;
                            }
                    }
            }


        @Override
        public boolean onOptionsItemSelected(MenuItem item)
            {
                switch (item.getItemId())
                    {
                        case R.id.action_select:
                            openGallary();
                            return true;
                        case R.id.action_camera_image:
                            openCameraForImage();
                            return true;

                        case R.id.action_camera_video:
                            openCameraForVideo();
                            return true;
                        case R.id.action_upload:
                            uploadSelectedItems();
                            return true;

                        default:
                            return super.onOptionsItemSelected(item);
                    }
            }

        private void uploadSelectedItems()
            {

                int counter = 0;
                for (SelectedData selectedData : selectedDataList)
                    {
                        if (selectedData.isChecked)
                            {
                                counter = counter + 1;
                            }
                    }

                if (counter == 0)
                    {
                        Toast.makeText(getActivity(), "Select Pictures,Videos or Documents to Upload", Toast.LENGTH_SHORT).show();
                    }
            }

        private void openCameraForVideo()
            {
                selectedImagePath = new HashSet<>();
                if (android.os.Build.VERSION.SDK_INT >= VERSION_CODES.M)
                    {
                        if (getActivity().checkSelfPermission(Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED && getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED)
                            {
                                requestPermissions(new String[]{Manifest.permission.CAMERA, permission.WRITE_EXTERNAL_STORAGE},
                                        102);
                            }
                        else
                            {
                                Intent cameraIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                                List<Intent> intentList = new ArrayList<>();
                                intentList = addIntentsToList(getActivity(), intentList, cameraIntent);
                                if (intentList.size() > 1)
                                    {
                                        intentList.clear();
                                        intentList.add(cameraIntent);
                                        cameraIntent = Intent.createChooser(intentList.get(0),
                                                "");
                                    }
                                if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null)
                                    {
                                        startActivityForResult(cameraIntent, CAMERA_REQUEST_FOR_VIDEO);
                                    }
                            }
                    }
                else
                    {
                        Intent cameraIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

                        List<Intent> intentList = new ArrayList<>();
                        intentList = addIntentsToList(getActivity(), intentList, cameraIntent);
                        if (intentList.size() > 1)
                            {
                                intentList.clear();
                                intentList.add(cameraIntent);
                                cameraIntent = Intent.createChooser(intentList.get(0),
                                        "");
                            }

                        if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null)
                            {
                                startActivityForResult(cameraIntent, CAMERA_REQUEST_FOR_VIDEO);
                            }
                    }
            }


        private List<Intent> addIntentsToList(Context context, List<Intent> list, Intent intent)
            {
                List<ResolveInfo> resInfo = context.getPackageManager().queryIntentActivities(intent, 0);
                for (ResolveInfo resolveInfo : resInfo)
                    {
                        String packageName = resolveInfo.activityInfo.packageName;
                        Intent targetedIntent = new Intent(intent);
                        targetedIntent.setPackage(packageName);
                        list.add(targetedIntent);
                    }
                return list;
            }


        private void openCameraForImage()
            {
                selectedImagePath = new HashSet<>();

                if (android.os.Build.VERSION.SDK_INT >= VERSION_CODES.M)
                    {

                        if (getActivity().checkSelfPermission(Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED && getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED)
                            {
                                requestPermissions(new String[]{Manifest.permission.CAMERA, permission.WRITE_EXTERNAL_STORAGE},
                                        100);
                            }
                        else
                            {

                                ContentValues values = new ContentValues();
                                imageUri = getActivity().getContentResolver().insert(
                                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

                                if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null)
                                    {
                                        startActivityForResult(cameraIntent, CAMERA_REQUEST_FOR_IMAGE);
                                    }
                            }
                    }
                else
                    {

                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null)
                            {
                                startActivityForResult(cameraIntent, CAMERA_REQUEST_FOR_IMAGE);
                            }
                    }
            }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
            {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                if (requestCode == 100)
                    {
                        if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                            {
                                Toast.makeText(getActivity(), "camera permission granted", Toast.LENGTH_LONG).show();
                                Intent cameraIntent = new
                                        Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(cameraIntent, CAMERA_REQUEST_FOR_IMAGE);
                            }
                        else
                            {
                                Toast.makeText(getActivity(), "camera permission denied", Toast.LENGTH_LONG).show();
                            }

                    }

                else if (requestCode == 102)
                    {
                        if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                            {
                                Toast.makeText(getActivity(), "camera permission granted", Toast.LENGTH_LONG).show();
                                Intent cameraIntent = new
                                        Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                                startActivityForResult(cameraIntent, CAMERA_REQUEST_FOR_VIDEO);
                            }
                        else
                            {
                                Toast.makeText(getActivity(), "camera permission denied", Toast.LENGTH_LONG).show();
                            }
                    }
            }

        private void openGallary()
            {
                selectedImagePath = new HashSet<>();
                Intent intent = new Intent();
                intent.setType("image/* video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(Intent.createChooser(intent,
                        "Select Picture"), SELECT_PICTURE);
            }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data)
            {
                super.onActivityResult(requestCode, resultCode, data);
                if (resultCode == Activity.RESULT_OK)
                    {
                        if (requestCode == SELECT_PICTURE)
                            {
                                if (data.getData() != null)
                                    {
                                        Uri mImageUri = data.getData();
                                        selectedImagePath.add(mImageUri.toString());
                                        setVideoOrImage(mImageUri.toString());
                                    }
                                else if (data.getClipData() != null)
                                    {

                                        if (data.getClipData().getItemCount() > 0)
                                            {
                                                for (int i = 0; i < data.getClipData().getItemCount(); i++)
                                                    {
                                                        selectedImagePath.add(data.getClipData().getItemAt(i).getUri().toString());
                                                        setVideoOrImage(data.getClipData().getItemAt(i).getUri().toString());
                                                    }
                                            }
                                    }
                            }
                        else if (requestCode == CAMERA_REQUEST_FOR_IMAGE)
                            {
                                Bitmap photo = null;
                                try
                                    {
                                        photo = Images.Media.getBitmap(
                                                getActivity().getContentResolver(), imageUri);
                                    } catch (IOException e)
                                    {
                                        e.printStackTrace();
                                    }
                                //Uri tempUri = getImageUri(getContext(), photo);
                                if (imageUri != null)
                                    {
                                        selectedImagePath.add(imageUri.toString());
                                    }
                                updateTheView("pictures", DataType.ITEM_TYPE_PICTURES);
                            }
                        else if (requestCode == CAMERA_REQUEST_FOR_VIDEO)
                            {
                                Uri uri = data.getData();
                                selectedImagePath.add(uri.toString());
                                updateTheView("video", DataType.ITEM_TYPE_VIDEOS);
                            }
                        selectedImagePath.clear();
                    }
            }

        public Uri getImageUri(Context inContext, Bitmap inImage)
            {
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                inImage.compress(CompressFormat.JPEG, 100, bytes);
                String path = Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
                return Uri.parse(path);
            }

        private void updateTheView(String type, DataType dataType)
            {

                for (String path : selectedImagePath)
                    {
                        SelectedData selectedData = new SelectedData();
                        selectedData.setUrl(path);
                        selectedData.setSolved(false);
                        selectedData.setChecked(false);
                        selectedData.setType(type);
                        selectedData.setDataType(dataType);
                        Random random = new Random();
                        selectedData.setId(random.nextInt());
                        selectedDataList.add(selectedData);
                    }
                LinkedHashSet<SelectedData> stringHashSet = new LinkedHashSet<>();
                stringHashSet.addAll(selectedDataList);
                selectedDataList.clear();
                selectedDataList.addAll(stringHashSet);
                _recyclerView.getAdapter().notifyDataSetChanged();
            }

        private void updateTheViewForSelectedGallaryImages(String type, DataType dataType, String url)
            {
                for (SelectedData data : selectedDataList)
                    {
                        if ((data).getUrl().equalsIgnoreCase(url))
                            {
                                return;
                            }
                    }
                SelectedData selectedData = new SelectedData();
                selectedData.setUrl(url);
                selectedData.setSolved(false);
                selectedData.setChecked(false);
                selectedData.setType(type);
                selectedData.setDataType(dataType);
                Random random = new Random();
                selectedData.setId(random.nextInt());
                LinkedHashSet<SelectedData> stringHashSet = new LinkedHashSet<>();
                stringHashSet.add(selectedData);
                stringHashSet.addAll(selectedDataList);
                selectedDataList.clear();
                selectedDataList.addAll(stringHashSet);
                _recyclerView.getAdapter().notifyDataSetChanged();
            }


        public String getRealPathFromURI(Context context, Uri contentUri)
            {
                Cursor cursor = null;
                try
                    {
                        String[] proj = {MediaStore.Video.Media.DATA};
                        cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
                        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
                        cursor.moveToFirst();
                        return cursor.getString(column_index);
                    }
                finally
                    {
                        if (cursor != null)
                            {
                                cursor.close();
                            }
                    }
            }

        @Override
        public void onSaveEditedImages(String url, int id)
            {
                Log.e("MyData", url + "  " + id);

                for (SelectedData selectedData : selectedDataList)
                    {
                        if (id == selectedData.getId())
                            {
                                selectedData.setUrl(url);
                                break;
                            }
                    }
                _recyclerView.getAdapter().notifyDataSetChanged();
            }


        void setVideoOrImage(String uri)
            {

                if (uri.contains("video"))
                    {
                        updateTheViewForSelectedGallaryImages("video", DataType.ITEM_TYPE_VIDEOS, uri);
                    }
                else
                    {
                        updateTheViewForSelectedGallaryImages("pictures", DataType.ITEM_TYPE_PICTURES, uri);
                    }
            }

        public void updateViewPager(updateViewPager updatePager)
            {
                updateViewPager = updatePager;
            }

        interface updateViewPager
            {
                void updateViewPager(int pageNumber);
            }

    }
