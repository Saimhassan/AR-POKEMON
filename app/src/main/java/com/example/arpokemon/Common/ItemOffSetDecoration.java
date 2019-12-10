package com.example.arpokemon.Common;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.DimenRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemOffSetDecoration extends RecyclerView.ItemDecoration {
    private int itemOffSet;

    public ItemOffSetDecoration(int itemOffSet) {
        this.itemOffSet = itemOffSet;
    }

    public ItemOffSetDecoration(@NonNull Context context, @DimenRes int dimens)
    {
        this(context.getResources().getDimensionPixelSize(dimens));
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(itemOffSet,itemOffSet,itemOffSet,itemOffSet);

    }
}
