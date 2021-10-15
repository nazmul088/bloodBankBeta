package com.example.blood.RecyclerViewSize;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class InsertDivider extends RecyclerView.ItemDecoration {

    private final int verticalSpaceHeight;

    public InsertDivider(int verticalSpaceHeight) {
        this.verticalSpaceHeight = verticalSpaceHeight;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.bottom = verticalSpaceHeight;
    }
}