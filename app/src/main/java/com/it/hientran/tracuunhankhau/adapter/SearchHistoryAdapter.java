package com.it.hientran.tracuunhankhau.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.it.hientran.tracuunhankhau.R;
import com.it.hientran.tracuunhankhau.object.NguoiDan;

import java.util.List;

/**
 * Created by admin on 4/22/2017.
 */

public class SearchHistoryAdapter extends CursorAdapter {

    private List<NguoiDan> items;
    private TextView text;

    public SearchHistoryAdapter(Context context, Cursor cursor, List<NguoiDan> items) {
        super(context, cursor, false);
        this.items = items;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        text.setText(items.get(cursor.getPosition()).getHoten());
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_history_searchview, parent, false);
        text = (TextView) view.findViewById(R.id.txt_item_search);
        return view;

    }

}
