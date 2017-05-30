package com.it.hientran.tracuunhankhau.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.util.SortedListAdapterCallback;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.it.hientran.tracuunhankhau.R;
import com.it.hientran.tracuunhankhau.object.NguoiDan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by admin on 5/19/2017.
 */

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.ViewHolder> {

    private ArrayList<NguoiDan> mDataset;
    private Context context;
    private OnItemClick onItemClick;
    private final String MALE = "Nam";
    private final String FEMALE = "Nữ";
    OnDataChangeListener mOnDataChangeListener;

    public PeopleAdapter(Context context , ArrayList<NguoiDan> list, OnItemClick onItemClick){
        this.mDataset = list;
        this.context = context;
        this.onItemClick = onItemClick;
    }

    @Override
    public PeopleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_util, parent, false);
        ViewHolder vh = new ViewHolder(v, onItemClick);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NguoiDan nguoiDan = mDataset.get(position);
        Spanned spYearBirth = Html.fromHtml( context.getString(R.string.string_year_birth , nguoiDan.getNamSinh().toString()));
        Spanned spPlaceBirth = Html.fromHtml( context.getString(R.string.string_place_birth , nguoiDan.getQueQuan().toString()));
        holder.mTxtFullName.setText(nguoiDan.getHoten().toString());
        holder.mTxtYearBirth.setText(spYearBirth);
        holder.mTxtPlaceBirth.setText(spPlaceBirth);

        String GioiTinh = nguoiDan.getGioitinh();
        int drawable = GioiTinh.equalsIgnoreCase(FEMALE) ? R.drawable.ic_profile_female : R.drawable.ic_profile_male ;
        holder.imgProfile.setImageResource(drawable);
    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public NguoiDan getItem(int position){
        return mDataset.get(position);
    }

    /**
     * from the activity or fragment --> mAdapter.refreshData(newList);
     *  if the data is updated after a server request -->
     *  getActivity().runOnUiThread(new Runnable() {
     * @Override
     * public void run() {
     * mAdapter.refreshData(newList);
     * });
     * @param lists --> new list
     */
    public void refreshData(List<NguoiDan> lists) {
        this.mDataset.clear();
        this.mDataset.addAll(lists);

        if (mOnDataChangeListener != null) {
            mOnDataChangeListener.onDataChanged(mDataset.size());
        }
        notifyDataSetChanged();
    }

    public void cleanData() {
        if (mDataset!=null){
            this.mDataset.clear();
            if (mOnDataChangeListener != null) {
                mOnDataChangeListener.onDataChanged(mDataset.size());
            }
            notifyDataSetChanged();
        }
    }

    public void sortItem(List<NguoiDan> listInput){
        Collections.sort(listInput , new Comparator<NguoiDan>() {
           @Override
           public int compare(NguoiDan o1, NguoiDan o2) {
               return o1.getBidanh().compareToIgnoreCase(o2.getBidanh());
           }
       });
        notifyDataSetChanged();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener , View.OnLongClickListener{
        protected TextView mTxtFullName;
        protected TextView mTxtYearBirth;
        protected TextView mTxtPlaceBirth;
        protected ImageView imgProfile;
        private OnItemClick onItemClick;


        public ViewHolder(View itemView ,OnItemClick onItemClick) {
            super(itemView);
            mTxtFullName = (TextView) itemView.findViewById(R.id.text_item_fullname);
            mTxtYearBirth = (TextView) itemView.findViewById(R.id.text_item_year_birth);
            mTxtPlaceBirth = (TextView) itemView.findViewById(R.id.text_item_place_birth);
            imgProfile = (ImageView) itemView.findViewById(R.id.image_item_profile);
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

    public void setOnDataChangeListener(OnDataChangeListener onDataChangeListener) {
        mOnDataChangeListener = onDataChangeListener;
    }

    public interface OnDataChangeListener {
        void onDataChanged(int size);
    }
}
