package com.example.citycycle.helpers;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {

    private final int verticalSpaceHeight; // Space height in pixels

    public VerticalSpaceItemDecoration(int verticalSpaceHeight) {
        this.verticalSpaceHeight = verticalSpaceHeight;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int position = parent.getChildAdapterPosition(view);

        // Add default bottom spacing
        outRect.bottom = verticalSpaceHeight;

        // Remove spacing for specific items (e.g., position 1 and 2)
        if (position == 1 || position == 2) {
            outRect.bottom = 0; // Remove space between these items
        }

        // Optionally, add top spacing only to the first item
        if (position == 0) {
            outRect.top = verticalSpaceHeight;
        }
    }
}


