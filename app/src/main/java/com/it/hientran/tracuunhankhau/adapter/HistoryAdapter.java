package com.it.hientran.tracuunhankhau.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.it.hientran.tracuunhankhau.R;
import com.it.hientran.tracuunhankhau.object.HistorySeen;
import com.it.hientran.tracuunhankhau.object.NguoiDan;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 5/29/2017.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private static final String TAG = "HistoryAdapter";
    private List<NguoiDan> mDataset;
    private List<HistorySeen> mDataHistory;
    private Context context;
    private HistoryAdapter.OnItemClick onItemClick;
    private final String MALE = "Nam";
    private final String FEMALE = "Nữ";
    HistoryAdapter.OnDataChangeListener mOnDataChangeListener;
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public HistoryAdapter(Context context , List<NguoiDan> list ,List<HistorySeen> mDataHistory, HistoryAdapter.OnItemClick onItemClick){
        this.mDataset = list;
        this.mDataHistory = mDataHistory;
        this.context = context;
        this.onItemClick = onItemClick;

    }

    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_history, parent, false);
        HistoryAdapter.ViewHolder vh = new HistoryAdapter.ViewHolder(v, onItemClick);
        return vh;
    }

    @Override
    public void onBindViewHolder(HistoryAdapter.ViewHolder holder, int position) {
        NguoiDan nguoiDan = mDataset.get(position);
        HistorySeen historySeen = mDataHistory.get(position);
        Spanned spYearBirth = Html.fromHtml( context.getString(R.string.string_year_birth , nguoiDan.getNamSinh().toString()));
        String dateSeen = dateFormat.format(historySeen.getDateSeen());
        String GioiTinh = nguoiDan.getGioitinh();
        int drawable = GioiTinh.equalsIgnoreCase(FEMALE) ? R.drawable.ic_profile_female : R.drawable.ic_profile_male ;

        try {
            holder.mTxtFullName.setText(nguoiDan.getHoten());
            holder.mTxtYearBirth.setText(spYearBirth);
            holder.mTxtDateSeen.setText(dateSeen);
            holder.imgProfile.setImageResource(drawable);
        }catch (Exception e){
            Log.e(TAG, "onBindViewHolder: "+ e.getMessage());
        }

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener , View.OnLongClickListener{
        protected TextView mTxtFullName;
        protected TextView mTxtYearBirth;
        protected ImageView imgProfile;
        protected TextView mTxtDateSeen;
        private HistoryAdapter.OnItemClick onItemClick;


        public ViewHolder(View itemView ,HistoryAdapter.OnItemClick onItemClick) {
            super(itemView);
            mTxtFullName = (TextView) itemView.findViewById(R.id.text_his_item_fullname);
            mTxtYearBirth = (TextView) itemView.findViewById(R.id.text_his_item_year_birth);
            mTxtDateSeen = (TextView) itemView.findViewById(R.id.text_his_item_date_seen);
            imgProfile = (ImageView) itemView.findViewById(R.id.image_his_item_profile);
            this.onItemClick = onItemClick;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemClick.onClick(getLayoutPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            onItemClick.onLongClick(getLayoutPosition());
            return false;
        }
    }

    public interface OnItemClick {
        void onClick(int position);
        void onLongClick(int position);
    }

    public void setOnDataChangeListener(HistoryAdapter.OnDataChangeListener onDataChangeListener) {
        mOnDataChangeListener = onDataChangeListener;
        mOnDataChangeListener.onDataChanged(getItemCount());
    }

    public interface OnDataChangeListener {
        void onDataChanged(int size);
    }
}
