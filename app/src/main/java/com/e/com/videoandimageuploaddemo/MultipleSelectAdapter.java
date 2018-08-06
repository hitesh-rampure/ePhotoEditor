package com.e.com.videoandimageuploaddemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.bignerdranch.android.multiselector.MultiSelector;
import com.bignerdranch.android.multiselector.SwappingHolder;
import com.bumptech.glide.Glide;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.LinkedList;

public class MultipleSelectAdapter extends RecyclerView.Adapter<SwappingHolder>
    {

        private MultiSelector _multiSelector;
        private Context _context;
        private LinkedList<SelectedData> _selectedData;
        private MultiSelectorListener _multiSelectorListener;
        private int _position = -1;
        private int tabPosition;


        MultipleSelectAdapter(Context context, MultiSelector multiSelector, LinkedList<SelectedData> selectedDataArrayList, MultiSelectorListener multiSelectorListener, int position)
            {
                _multiSelector = multiSelector;
                _context = context;
                _selectedData = selectedDataArrayList;
                _multiSelectorListener = multiSelectorListener;
                tabPosition = position;
            }


        public class PictureViewHolder extends SwappingHolder implements View.OnLongClickListener
            {

                private final CheckBox mSolvedCheckBox;

                public ImageView pictureImageView;

                PictureViewHolder(View itemView)
                    {
                        super(itemView, _multiSelector);


                        mSolvedCheckBox = itemView.findViewById(R.id.list_item_solvedCheckBox);
                        pictureImageView = itemView.findViewById(R.id.pictureImageView);
                        pictureImageView.setScaleType(ScaleType.FIT_XY);

                        itemView.setLongClickable(true);
                        itemView.setOnLongClickListener(this);
                    }


                void bindData(final SelectedData selectedData, final int position, final PictureViewHolder pictureViewHolder)
                    {

                        Glide.with(_context).load(selectedData.getUrl())
                                .thumbnail(1f)
                                .into((pictureImageView));

                        if (selectedData.isSolved)
                            {
                                if (_position == position || mSolvedCheckBox.isChecked())

                                    {
                                        mSolvedCheckBox.setChecked(true);
                                    }
                                else
                                    {
                                        mSolvedCheckBox.setChecked(false);
                                    }
                                mSolvedCheckBox.setVisibility(View.VISIBLE);
                                selectedData.setSolved(selectedData.isSolved);
                            }
                        else
                            {
                                mSolvedCheckBox.setChecked(false);
                                selectedData.setSolved(selectedData.isSolved);
                                mSolvedCheckBox.setVisibility(View.GONE);
                            }

                        itemView.setOnClickListener(new OnClickListener()
                            {
                                @Override
                                public void onClick(View v)
                                    {
                                        if (mSolvedCheckBox.getVisibility() == View.VISIBLE)
                                            {
                                                if (mSolvedCheckBox.isChecked())
                                                    {
                                                        mSolvedCheckBox.setChecked(false);

                                                        _multiSelectorListener.onClick(pictureViewHolder, false, position, mSolvedCheckBox.isChecked(), true);

                                                    }
                                                else
                                                    {
                                                        mSolvedCheckBox.setChecked(true);
                                                        _multiSelectorListener.onClick(pictureViewHolder, true, position, mSolvedCheckBox.isChecked(), true);
                                                    }
                                            }
                                        else
                                            {
                                                _multiSelectorListener.onClick(pictureViewHolder, false, position, false, false);
                                            }
                                    }
                            });
                    }

                @Override
                public boolean onLongClick(View v)
                    {

                        if (mSolvedCheckBox.getVisibility() == View.VISIBLE)
                            {
                                for (SelectedData selectedData : _selectedData)
                                    {
                                        selectedData.setSolved(false);
                                    }
                            }
                        else
                            {
                                for (SelectedData selectedData : _selectedData)
                                    {
                                        selectedData.setSolved(true);
                                    }
                                _position = getAdapterPosition();
                            }
                        notifyDataSetChanged();
                        return true;
                    }
            }

        public class DocumentViewHolder extends SwappingHolder implements View.OnLongClickListener
            {

                private final CheckBox mSolvedCheckBox;

                public ImageView webView;

                DocumentViewHolder(View itemView)
                    {
                        super(itemView, _multiSelector);


                        mSolvedCheckBox = itemView.findViewById(R.id.list_item_solvedCheckBox);
                        webView = itemView.findViewById(R.id.documentWebView);

                        itemView.setLongClickable(true);
                        itemView.setOnLongClickListener(this);
                    }


                void bindData(final SelectedData selectedData, final int position, final DocumentViewHolder documentViewHolder)
                    {

                        if (selectedData.isSolved)
                            {
                                if (_position == position || mSolvedCheckBox.isChecked())

                                    {
                                        mSolvedCheckBox.setChecked(true);
                                    }
                                else
                                    {
                                        mSolvedCheckBox.setChecked(false);
                                    }
                                mSolvedCheckBox.setVisibility(View.VISIBLE);
                                selectedData.setSolved(selectedData.isSolved);
                            }
                        else
                            {
                                mSolvedCheckBox.setChecked(false);
                                selectedData.setSolved(selectedData.isSolved);
                                mSolvedCheckBox.setVisibility(View.GONE);
                            }

                        itemView.setOnClickListener(new OnClickListener()
                            {
                                @Override
                                public void onClick(View v)
                                    {
                                        if (mSolvedCheckBox.getVisibility() == View.VISIBLE)
                                            {
                                                if (mSolvedCheckBox.isChecked())
                                                    {
                                                        mSolvedCheckBox.setChecked(false);

                                                        _multiSelectorListener.onClick(documentViewHolder, false, position, mSolvedCheckBox.isChecked(), true);

                                                    }
                                                else
                                                    {
                                                        mSolvedCheckBox.setChecked(true);
                                                        _multiSelectorListener.onClick(documentViewHolder, true, position, mSolvedCheckBox.isChecked(), true);
                                                    }
                                            }
                                        else
                                            {
                                                _multiSelectorListener.onClick(documentViewHolder, false, position, false, false);
                                            }
                                    }
                            });
                    }

                @Override
                public boolean onLongClick(View v)
                    {

                        if (mSolvedCheckBox.getVisibility() == View.VISIBLE)
                            {
                                for (SelectedData selectedData : _selectedData)
                                    {
                                        selectedData.setSolved(false);
                                    }
                            }
                        else
                            {
                                for (SelectedData selectedData : _selectedData)
                                    {
                                        selectedData.setSolved(true);
                                    }
                                _position = getAdapterPosition();
                            }
                        notifyDataSetChanged();
                        return true;
                    }
            }


        public class VideoViewHolder extends SwappingHolder implements View.OnLongClickListener
            {

                private final CheckBox mSolvedCheckBox;

                public ImageView pictureImageView;

                VideoViewHolder(View itemView)
                    {
                        super(itemView, _multiSelector);


                        mSolvedCheckBox = itemView.findViewById(R.id.list_item_solvedCheckBox);
                        pictureImageView = itemView.findViewById(R.id.pictureImageView);

                        itemView.setLongClickable(true);
                        itemView.setOnLongClickListener(this);
                    }


                void bindData(final SelectedData selectedData, final int position, final VideoViewHolder videoViewHolder)
                    {

                        Glide.with(_context).load(selectedData.getUrl())
                                .thumbnail(1f)
                                .into((pictureImageView));

                        if (selectedData.isSolved)
                            {
                                mSolvedCheckBox.setChecked(false);
                                mSolvedCheckBox.setVisibility(View.VISIBLE);
                            }
                        else
                            {
                                mSolvedCheckBox.setChecked(false);
                                mSolvedCheckBox.setVisibility(View.GONE);
                            }

                        itemView.setOnClickListener(new OnClickListener()
                            {
                                @Override
                                public void onClick(View v)
                                    {

                                        if (mSolvedCheckBox.getVisibility() == View.VISIBLE)
                                            {
                                                if (mSolvedCheckBox.isChecked())
                                                    {
                                                        mSolvedCheckBox.setChecked(false);

                                                        _multiSelectorListener.onClick(videoViewHolder, false, position, mSolvedCheckBox.isChecked(), true);

                                                    }
                                                else
                                                    {
                                                        mSolvedCheckBox.setChecked(true);
                                                        _multiSelectorListener.onClick(videoViewHolder, true, position, mSolvedCheckBox.isChecked(), true);
                                                    }
                                            }
                                        else
                                            {
                                                _multiSelectorListener.onClick(videoViewHolder, false, position, false, false);
                                            }
                                    }
                            });
                    }

                @Override
                public boolean onLongClick(View v)
                    {
                        if (mSolvedCheckBox.getVisibility() == View.VISIBLE)
                            {
                                for (SelectedData selectedData : _selectedData)
                                    {
                                        selectedData.setSolved(false);
                                    }
                            }
                        else
                            {
                                for (SelectedData selectedData : _selectedData)
                                    {
                                        selectedData.setSolved(true);
                                    }
                            }
                        notifyDataSetChanged();
                        _multiSelectorListener.onLongPress(this, true);
                        return true;
                    }
            }

        @NonNull
        @Override
        public SwappingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {

                View view;
                switch (viewType)
                    {
                        case 0:
                            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.picture_item_view, parent, false);
                            return new PictureViewHolder(view);
                        case 1:
                            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item_layout, parent, false);
                            return new VideoViewHolder(view);

                        case 2:
                            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.document_list_litem, parent, false);
                            return new DocumentViewHolder(view);
                        case 100:
                            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.picture_item_view, parent, false);
                            return new PictureViewHolder(view);
                    }
                return new PictureViewHolder(parent);
            }

        @Override
        public void onBindViewHolder(@NonNull SwappingHolder holder, int position)
            {

                SelectedData selectedData = _selectedData.get(position);
                if (selectedData != null)
                    {
                        switch (selectedData.getDataType())
                            {
                                case ITEM_TYPE_PICTURES:

                                    PictureViewHolder pictureViewHolder = (PictureViewHolder) holder;
                                    pictureViewHolder.bindData(selectedData, position, pictureViewHolder);
                                    break;

                                case ITEM_TYPE_VIDEOS:
                                    VideoViewHolder videoViewHolder = (VideoViewHolder) holder;
                                    videoViewHolder.bindData(selectedData, position, videoViewHolder);
                                    break;
                                case ITEM_TYPE_DOCUMENTS:
                                    DocumentViewHolder documentViewHolder = (DocumentViewHolder) holder;
                                    documentViewHolder.bindData(selectedData, position, documentViewHolder);
                                    break;
                            }
                    }
            }


        @Override
        public int getItemViewType(int position)
            {
                switch (_selectedData.get(position).getDataType())
                    {
                        case ITEM_TYPE_PICTURES:
                            return DataType.ITEM_TYPE_PICTURES.getType();
                        case ITEM_TYPE_VIDEOS:
                            return DataType.ITEM_TYPE_VIDEOS.getType();
                        case ITEM_TYPE_DOCUMENTS:
                            return DataType.ITEM_TYPE_DOCUMENTS.getType();
                        default:
                            return 100;
                    }
            }

        @Override
        public int getItemCount()
            {
                return _selectedData.size();
            }

        public LinkedList<SelectedData> get_selectedData()
            {
                return _selectedData;
            }

        public int getTabPosition()
            {
                return tabPosition;
            }
    }
