package com.soenadiwai.tmdbmobile.interfaces;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Nadi on 21/1/22.
 */

public abstract class RecyclerViewScrollListener extends RecyclerView.OnScrollListener {

    private static final String TAG = "RecylcerScrollListener";
    private static final int HIDE_THRESHOLD = 20;
    int firstVisibleItem, visibleItemCount, totalItemCount;
    private int mScrollThreshold = 40;
    private int scrolledDistance = 0;
    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 0;
    private boolean infiniteScrollingEnabled = true;

    private boolean controlsVisible = true;

    public RecyclerViewScrollListener() {

    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();

        visibleItemCount = recyclerView.getChildCount();
        if (manager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) manager;
            firstVisibleItem = gridLayoutManager.findFirstVisibleItemPosition();
            totalItemCount = gridLayoutManager.getItemCount();
        } else if (manager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) manager;
            firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
            totalItemCount = linearLayoutManager.getItemCount();
        }


        if (infiniteScrollingEnabled) {
            if (loading) {
                if (totalItemCount > previousTotal) {
                    loading = false;
                    previousTotal = totalItemCount;
                }
            }

            if (!loading && (totalItemCount - visibleItemCount <= firstVisibleItem + visibleThreshold)) {
                onLoadMore();
                loading = true;
            }
        }

        if (firstVisibleItem == 0) {
            if (!controlsVisible) {
                onScrollUp();
                controlsVisible = true;
            }

            return;
        }

        if (scrolledDistance > HIDE_THRESHOLD && controlsVisible) {
            onScrollDown();
            controlsVisible = false;
            scrolledDistance = 0;
        } else if (scrolledDistance < -HIDE_THRESHOLD && !controlsVisible) {
            onScrollUp();
            controlsVisible = true;
            scrolledDistance = 0;
        }

        if ((controlsVisible && dy > 0) || (!controlsVisible && dy < 0)) {
            scrolledDistance += dy;
        }
    }


    public abstract void onScrollUp();

    public abstract void onScrollDown();

    public abstract void onLoadMore();

}

