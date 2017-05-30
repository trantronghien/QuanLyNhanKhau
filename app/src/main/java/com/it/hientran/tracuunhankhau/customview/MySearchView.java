package com.it.hientran.tracuunhankhau.customview;

import android.content.Context;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;


/**
 * Created by admin on 5/27/2017.
 */

public class MySearchView extends SearchView implements SearchView.OnQueryTextListener {
    private boolean expanded;
    private searchOnSubmitListener submitListener;

    public MySearchView(Context context) {
        super(context);
        this.setOnQueryTextListener(this);
    }

    @Override
    public void onActionViewExpanded() {
        super.onActionViewExpanded();
        expanded = true;
    }

    @Override
    public void onActionViewCollapsed() {
        super.onActionViewCollapsed();
        expanded = false;
    }

    public boolean isExpanded() {
        return expanded;
    }



    @Override
    public boolean onQueryTextSubmit(String query) {
        if (submitListener != null)
            submitListener.onQueryTextSubmit(query);
            submitListener.onSubmitButtonSearch(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (submitListener != null){
            submitListener.onQueryTextChange(newText);
            if (this.isExpanded() && TextUtils.isEmpty(newText)) {
                submitListener.onSubmitButtonSearch("");
            }
        }
        return true;
    }

    public void setOnSubmitListener(searchOnSubmitListener submitListener) {
//        this.setOnQueryTextListener(this);
        this.submitListener = submitListener;
    }

    public interface searchOnSubmitListener {
        /**
         * called when submit button search in keyboard
         * @param query
         */
        void onSubmitButtonSearch(String query);
        /**
         * like onQueryTextSubmit of OnQueryTextListener
         * @param query
         * @return
         */
        boolean onQueryTextSubmit(String query);
        /**
         * like onQueryTextChange of OnQueryTextListener
         * @param newText
         * @return
         */
        boolean onQueryTextChange(String newText);
    }
}
