package com.e.com.videoandimageuploaddemo;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.Menu;
import android.view.View;

import com.bignerdranch.android.multiselector.SelectableHolder;
import com.e.com.videoandimageuploaddemo.MultipleSelectAdapter.PictureViewHolder;

public interface MultiSelectorListener
    {

        void onClick(ViewHolder view, boolean isSolved, int position, boolean isChecked, boolean isCheckBoxEnabled);

        void onLongPress(SelectableHolder viewHolder, boolean isSelected);

    }
