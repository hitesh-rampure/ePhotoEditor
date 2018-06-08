package com.e.com.videoandimageuploaddemo.ImageEditing;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.e.com.videoandimageuploaddemo.R;
import com.eemphasys.eservie.imageannotations.library.OnPhotoEditorListener;
import com.eemphasys.eservie.imageannotations.library.PhotoEditor;
import com.eemphasys.eservie.imageannotations.library.PhotoEditorView;
import com.eemphasys.eservie.imageannotations.library.ViewType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class EditImageActivity extends BaseActivity implements OnPhotoEditorListener,
        View.OnClickListener,
        PropertiesBSFragment.Properties//,
//        EmojiBSFragment.EmojiListener,
//         StickerBSFragment.StickerListener
{

    private static final String TAG = EditImageActivity.class.getSimpleName();
    public static final String EXTRA_IMAGE_PATHS = "extra_image_paths";
    private static final int CAMERA_REQUEST = 52;
    private static final int PICK_REQUEST = 53;
    private PhotoEditor mPhotoEditor;
    private PhotoEditorView mPhotoEditorView;
    private PropertiesBSFragment mPropertiesBSFragment;
    //    private EmojiBSFragment mEmojiBSFragment;
//    private StickerBSFragment mStickerBSFragment;
    private TextView mTxtCurrentTool;
    private Typeface mWonderFont;

    ContentValues values;
    Uri imageUri;
    Bitmap thumbnail;
    int rotateID = 0;

    /**
     * launch editor with multiple image
     *
     * @param context
     * @param imagesPath
     */
    public static void launch(Context context, ArrayList<String> imagesPath) {
        Intent starter = new Intent(context, EditImageActivity.class);
        starter.putExtra(EXTRA_IMAGE_PATHS, imagesPath);
        context.startActivity(starter);
    }

    /**
     * launch editor with single image
     *
     * @param context
     * @param imagePath
     */
    public static void launch(Context context, String imagePath) {
        ArrayList<String> imagePaths = new ArrayList<>();
        imagePaths.add(imagePath);
        launch(context, imagePaths);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        makeFullScreen();
        setContentView(R.layout.activity_edit_image);

        initViews();

        mWonderFont = Typeface.createFromAsset(getAssets(), "beyond_wonderland.ttf");

        mPropertiesBSFragment = new PropertiesBSFragment();
//        mEmojiBSFragment = new EmojiBSFragment();
//        mStickerBSFragment = new StickerBSFragment();
//        mStickerBSFragment.setStickerListener(this);
//        mEmojiBSFragment.setEmojiListener(this);
        mPropertiesBSFragment.setPropertiesChangeListener(this);

        //Typeface mTextRobotoTf = ResourcesCompat.getFont(this, R.font.roboto_medium);
        //Typeface mEmojiTypeFace = Typeface.createFromAsset(getAssets(), "emojione-android.ttf");

        mPhotoEditor = new PhotoEditor.Builder(this, mPhotoEditorView)
                .setPinchTextScalable(true) // set flag to make text scalable when pinch
                //.setDefaultTextTypeface(mTextRobotoTf)
                //.setDefaultEmojiTypeface(mEmojiTypeFace)
                .build(); // build photo editor sdk

        mPhotoEditor.setOnPhotoEditorListener(this);

        //Set Image Dynamically
        String uri = getIntent().getExtras().getString("uri");
        // mPhotoEditorView.getSource().setImageResource(R.drawable.got);

        Glide.with(this).load(uri)
                .thumbnail(1f)
                .into((mPhotoEditorView.getSource()));

//        Drawing Mode by default
        mPhotoEditor.setBrushDrawingMode(true);

//        Default Brush Color
        mPhotoEditor.setBrushColor(ContextCompat.getColor(getApplicationContext(), R.color.red_color_picker));
    }

    private void initViews() {
        ImageView imgPencil;
        ImageView imgEraser;
//        ImageView imgUndo;
//        ImageView imgRedo;
        ImageView imgText;
//        ImageView imgCamera;
//        ImageView imgGallery;
//        ImageView imgSticker;
//        ImageView imgEmo;
        ImageView imgSave;
        ImageView imgClose;
//        ImageView imgRotateImage;
        ImageView imgUndo;
        ImageView imgRedo;

        TextView txtPencil;
        TextView txtText;
        TextView txtEraser;
        TextView txtUndo;
        TextView txtRedo;

        RelativeLayout layoutPencil;
        RelativeLayout layoutText;
        RelativeLayout layoutEraser;
        RelativeLayout layoutUndo;
        RelativeLayout layoutRedo;

        mPhotoEditorView = findViewById(R.id.photoEditorView);
        mTxtCurrentTool = findViewById(R.id.txtCurrentTool);

//        imgEmo = findViewById(R.id.imgEmoji);
//        imgEmo.setOnClickListener(this);

//        imgSticker = findViewById(R.id.imgSticker);
//        imgSticker.setOnClickListener(this);

        imgPencil = findViewById(R.id.imgPencil);
        imgPencil.setOnClickListener(this);

        imgText = findViewById(R.id.imgText);
        imgText.setOnClickListener(this);

        imgEraser = findViewById(R.id.imgEraser);
        imgEraser.setOnClickListener(this);

//        imgUndo = findViewById(R.id.imgUndo);
//        imgUndo.setOnClickListener(this);
//
//        imgRedo = findViewById(R.id.imgRedo);
//        imgRedo.setOnClickListener(this);
//
//        imgCamera = findViewById(R.id.imgCamera);
//        imgCamera.setOnClickListener(this);
//
//        imgGallery = findViewById(R.id.imgGallery);
//        imgGallery.setOnClickListener(this);

        imgSave = findViewById(R.id.imgSave);
        imgSave.setOnClickListener(this);

        imgClose = findViewById(R.id.imgClose);
        imgClose.setOnClickListener(this);

//        imgRotateImage = findViewById(R.id.imgRotateImage);
//        imgRotateImage.setOnClickListener(this);

        imgUndo = findViewById(R.id.imgUndo);
        imgUndo.setOnClickListener(this);

        imgRedo = findViewById(R.id.imgRedo);
        imgRedo.setOnClickListener(this);

        txtPencil = findViewById(R.id.txtPencil);
        txtPencil.setOnClickListener(this);

        txtText = findViewById(R.id.txtText);
        txtText.setOnClickListener(this);

        txtEraser = findViewById(R.id.txtEraser);
        txtEraser.setOnClickListener(this);

        txtUndo = findViewById(R.id.txtUndo);
        txtUndo.setOnClickListener(this);

        txtRedo = findViewById(R.id.txtRedo);
        txtRedo.setOnClickListener(this);

        layoutPencil = findViewById(R.id.layoutPencil);
        layoutPencil.setOnClickListener(this);

        layoutText = findViewById(R.id.layoutText);
        layoutText.setOnClickListener(this);

        layoutEraser = findViewById(R.id.layoutEraser);
        layoutEraser.setOnClickListener(this);

        layoutUndo = findViewById(R.id.layoutUndo);
        layoutUndo.setOnClickListener(this);

        layoutRedo = findViewById(R.id.layoutRedo);
        layoutRedo.setOnClickListener(this);
    }

    @Override
    public void onEditTextChangeListener(final View rootView, String text, int colorCode) {
        TextEditorDialogFragment textEditorDialogFragment =
                TextEditorDialogFragment.show(this, text, colorCode);
        textEditorDialogFragment.setOnTextEditorListener(new TextEditorDialogFragment.TextEditor() {
            @Override
            public void onDone(String inputText, int colorCode) {
                mPhotoEditor.editText(rootView, inputText, colorCode);
                mTxtCurrentTool.setText(R.string.label_text);
            }
        });
    }

    @Override
    public void onAddViewListener(ViewType viewType, int numberOfAddedViews) {
        Log.e(TAG, "onAddViewListener() called with: viewType = [" + viewType + "], numberOfAddedViews = [" + numberOfAddedViews + "]");
    }

    @Override
    public void onRemoveViewListener(int numberOfAddedViews) {
        Log.e(TAG, "onRemoveViewListener() called with: numberOfAddedViews = [" + numberOfAddedViews + "]");
    }

    @Override
    public void onStartViewChangeListener(ViewType viewType) {
        Log.e(TAG, "onStartViewChangeListener() called with: viewType = [" + viewType + "]");
    }

    @Override
    public void onStopViewChangeListener(ViewType viewType) {
        Log.e(TAG, "onStopViewChangeListener() called with: viewType = [" + viewType + "]");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgPencil:
            case R.id.txtPencil:
            case R.id.layoutPencil:

                mPhotoEditor.setBrushDrawingMode(true);
                mTxtCurrentTool.setText(R.string.label_brush);
//                mTxtCurrentTool.setText(R.string.app_name);
//                mPropertiesBSFragment.show(getSupportFragmentManager(), mPropertiesBSFragment.getTag());
                break;

            case R.id.imgEraser:
            case R.id.txtEraser:
            case R.id.layoutEraser:

                mPhotoEditor.brushEraser();
                mTxtCurrentTool.setText(R.string.label_eraser);
                break;

            case R.id.imgText:
            case R.id.txtText:
            case R.id.layoutText:

                TextEditorDialogFragment textEditorDialogFragment = TextEditorDialogFragment.show(this);
                textEditorDialogFragment.setOnTextEditorListener(new TextEditorDialogFragment.TextEditor() {
                    @Override
                    public void onDone(String inputText, int colorCode) {
                        mPhotoEditor.addText(inputText, colorCode);
//                        mTxtCurrentTool.setText(R.string.label_text);
//                        mTxtCurrentTool.setText(R.string.app_name);

//                        Drawing Mode by default
                        mPhotoEditor.setBrushDrawingMode(true);
                    }
                });
                mTxtCurrentTool.setText(R.string.label_text);
                break;

//            case R.id.imgRotateImage:
//                rotateImage();
//                break;


//            case R.id.imgUndo:
            case R.id.imgUndo:
            case R.id.txtUndo:
            case R.id.layoutUndo:
                mPhotoEditor.undo();
                mTxtCurrentTool.setText(R.string.label_undo);
                mPhotoEditor.setBrushDrawingMode(true);
                break;

//            case R.id.imgRedo:
            case R.id.imgRedo:
            case R.id.txtRedo:
            case R.id.layoutRedo:
                mPhotoEditor.redo();
                mTxtCurrentTool.setText(R.string.label_redo);
                mPhotoEditor.setBrushDrawingMode(true);
                break;

            case R.id.imgSave:
                saveImage();
                break;

            case R.id.imgClose:
                if (!mPhotoEditor.isCacheEmpty()) {
                    showSaveDialog();
                } else {
                    finish();
                }
                break;

//            case R.id.imgSticker:
//                mStickerBSFragment.show(getSupportFragmentManager(), mStickerBSFragment.getTag());
//                break;
//
//            case R.id.imgEmoji:
//                mEmojiBSFragment.show(getSupportFragmentManager(), mEmojiBSFragment.getTag());
//                break;
//
//            case R.id.imgCamera:
//
//                values = new ContentValues();
//                values.put(MediaStore.Images.Media.TITLE, "New Picture");
//                values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
//                imageUri = getContentResolver().insert(
//                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
//                Log.e("imageUri", imageUri.toString());
//                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//                startActivityForResult(cameraIntent, CAMERA_REQUEST);
//                break;
//
////                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
////                startActivityForResult(cameraIntent, CAMERA_REQUEST);
////                break;
//
//            case R.id.imgGallery:
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_REQUEST);
//                break;
        }
    }

    @SuppressLint("MissingPermission")
    private void saveImage() {
        if (requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            showLoading("Saving...");

            File myDir = new File(Environment.getExternalStorageDirectory() + "/eet");

            if (!myDir.exists()) {
                myDir.mkdirs();
            }

            File file = new File(myDir, System.currentTimeMillis() + ".png");

//            File file = new File(Environment.getExternalStorageDirectory()
//                    + File.separator + ""
//                    + System.currentTimeMillis() + ".png");

            try {
                if (file.exists()) {
                    file.delete();
                }

                file.createNewFile();

                mPhotoEditor.saveImage(file.getAbsolutePath(), new PhotoEditor.OnSaveListener() {
                    @Override
                    public void onSuccess(@NonNull String imagePath) {
                        hideLoading();
                        showSnackbar("Image Saved Successfully");
                        mPhotoEditorView.getSource().setImageURI(Uri.fromFile(new File(imagePath)));
                    }

                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        hideLoading();
                        showSnackbar("Failed to save Image");
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == RESULT_OK) {
//            switch (requestCode) {
//                case CAMERA_REQUEST:
//
//                    try {
//                        mPhotoEditor.clearAllViews();
//
//                        thumbnail = MediaStore.Images.Media.getBitmap(
//                                getContentResolver(), imageUri);
//
//                        // mPhotoEditorView.getSource().setScaleType(ImageView.ScaleType.FIT_XY);
//                        mPhotoEditorView.getSource().setImageBitmap(thumbnail);
//
//                        Log.e("Height", String.valueOf(thumbnail.getHeight()));
//
//                        Log.e("Width", String.valueOf(thumbnail.getWidth()));
//
//                        String imageurl = getRealPathFromURI(imageUri);
//                        Log.e("imageurl", imageurl);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                    break;
//
////                    mPhotoEditor.clearAllViews();
////                    Bitmap photo = (Bitmap) data.getExtras().get("data");
////                    photo.setHeight(2198);
////                    photo.setWidth(1500);
////                    Log.e("Height", String.valueOf(photo.getHeight()));
////                    Log.e("Width", String.valueOf(photo.getWidth()));
////                    mPhotoEditorView.getSource().setImageBitmap(photo);
////                    break;
//
//                case PICK_REQUEST:
//                    try {
//                        mPhotoEditor.clearAllViews();
//                        Uri uri = data.getData();
//                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
//                        mPhotoEditorView.getSource().setImageBitmap(bitmap);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    break;
//            }
//        }
//    }
//
//    public String getRealPathFromURI(Uri contentUri) {
//        String[] proj = {MediaStore.Images.Media.DATA};
//        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
//        int column_index = cursor
//                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//        cursor.moveToFirst();
//        return cursor.getString(column_index);
//    }
//
//    public void rotateImage() {
//        float rotAngle = 0.0f;
//
//        if (rotateID == 0) {
////            mPhotoEditorView.getSource().setRotation(90.0f);
//            rotAngle = 90.f;
//            rotateID = 1;
//        } else if (rotateID == 1) {
////            mPhotoEditorView.getSource().setRotation(180.0f);
//            rotAngle = 180.f;
//            rotateID = 2;
//        } else if (rotateID == 2) {
////            mPhotoEditorView.getSource().setRotation(270.0f);
//            rotAngle = 270.f;
//            rotateID = 3;
//        } else if (rotateID == 3) {
////            mPhotoEditorView.getSource().setRotation(360.0f);
//            rotAngle = 360.f;
//            rotateID = 0;
//        }
//
//        mPhotoEditorView.getSource().setRotation(rotAngle);
//    }

    @Override
    public void onColorChanged(int colorCode) {
        mPhotoEditor.setBrushColor(colorCode);
        mTxtCurrentTool.setText(R.string.label_brush);
    }

    @Override
    public void onOpacityChanged(int opacity) {
        mPhotoEditor.setOpacity(opacity);
        mTxtCurrentTool.setText(R.string.label_brush);
    }

    @Override
    public void onBrushSizeChanged(int brushSize) {
        mPhotoEditor.setBrushSize(brushSize);
        mTxtCurrentTool.setText(R.string.label_brush);
    }

//    @Override
//    public void onEmojiClick(String emojiUnicode) {
//        mPhotoEditor.addEmoji(emojiUnicode);
//        mTxtCurrentTool.setText(R.string.label_emoji);
//    }
//
//    @Override
//    public void onStickerClick(Bitmap bitmap) {
//        mPhotoEditor.addImage(bitmap);
//        mTxtCurrentTool.setText(R.string.label_sticker);
//    }

    @Override
    public void isPermissionGranted(boolean isGranted, String permission) {
        if (isGranted) {
            saveImage();
        }
    }

    private void showSaveDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to exit without saving image ?");
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveImage();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setNeutralButton("Discard", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        builder.create().show();
    }
}