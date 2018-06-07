package com.e.com.videoandimageuploaddemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.bignerdranch.android.multiselector.MultiSelector;
import com.bignerdranch.android.multiselector.SwappingHolder;
import com.bumptech.glide.Glide;

import java.util.LinkedList;

public class MultipleSelectAdapter extends RecyclerView.Adapter<SwappingHolder>
    {

        private MultiSelector _multiSelector;
        private Context _context;
        private LinkedList<SelectedData> _selectedData;
        private MultiSelectorListener _multiSelectorListener;
        int _position = -1;


        MultipleSelectAdapter(Context context, MultiSelector multiSelector, LinkedList<SelectedData> selectedDataArrayList, MultiSelectorListener multiSelectorListener)
            {
                _multiSelector = multiSelector;
                _context = context;
                _selectedData = selectedDataArrayList;
                _multiSelectorListener = multiSelectorListener;
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
                                if (_position == position)
                                    {
                                        mSolvedCheckBox.setChecked(true);
                                    }
                                else
                                    {
                                        mSolvedCheckBox.setChecked(false);
                                    }
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
                            //view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_type, parent, false);
                            //return new ImageTypeViewHolder(view);
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
                            return DataType.ITEM_TYPE_VIDEOS.getType();
                        default:
                            return 100;
                    }
            }

        @Override
        public int getItemCount()
            {
                return _selectedData.size();
            }

    }
