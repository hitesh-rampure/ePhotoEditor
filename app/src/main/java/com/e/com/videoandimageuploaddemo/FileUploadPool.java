package com.e.com.videoandimageuploaddemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by jchheda on 7/25/2017.
 */

public class FileUploadPool implements Runnable
    {

        private String mFileName = "";
        private int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors() * 2;
        private final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;
        public static ArrayList<Map<Object, Object>> fileChunks = new ArrayList<>();
        private Context _context;
        private OnWebServiceResponse _onWebServiceResponse;

        public FileUploadPool(String fileName, OnWebServiceResponse onWebServiceResponse)
            {
                this.mFileName = fileName;
                this._onWebServiceResponse = onWebServiceResponse;
            }

        public void Upload()
            {
            }

        @Override
        public void run()
            {
                try
                    {
                        ArrayList<Map<Object, Object>> chunks = fileChunks;
                        if (chunks != null && chunks.size() > 0)
                            {
                                //            if (fileData != null && fileData.size() > 0 && fileData.get("FilePath") != null)
                                {
                                    String filePath = ImageEditFragment.filePath;//fileData.get("FilePath").toString();
                                    Uri uri = Uri.parse(filePath);
                                    File file = new File(uri.getPath());

                                    if (file.exists())
                                        {
                                            FileInputStream fis;
                                            ByteArrayOutputStream bos = null;
                                            int bytesAvailable;
                                            byte[] bufferChunk = new byte[0];
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

                                            for (Map<Object, Object> chunk : finalChunks)
                                                executorPool.execute(new FileUploadHandler(mFileName, chunk, _onWebServiceResponse));

                                        }
                                }
                            }
                    } catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
            }

        public static Bitmap rotateBitmap(Bitmap bitmap, int orientation)
            {
                Matrix matrix = new Matrix();
                switch (orientation)
                    {
                        case ExifInterface.ORIENTATION_NORMAL:
                            return bitmap;
                        case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                            matrix.setScale(-1, 1);
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_180:
                            matrix.setRotate(180);
                            break;
                        case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                            matrix.setRotate(180);
                            matrix.postScale(-1, 1);
                            break;
                        case ExifInterface.ORIENTATION_TRANSPOSE:
                            matrix.setRotate(90);
                            matrix.postScale(-1, 1);
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_90:
                            matrix.setRotate(90);
                            break;
                        case ExifInterface.ORIENTATION_TRANSVERSE:
                            matrix.setRotate(-90);
                            matrix.postScale(-1, 1);
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_270:
                            matrix.setRotate(-90);
                            break;
                        default:
                            return bitmap;
                    }
                try
                    {
                        Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                        bitmap.recycle();
                        return bmRotated;
                    } catch (OutOfMemoryError e)
                    {
                        e.printStackTrace();
                        return null;
                    }
            }
    }
