package com.it.hientran.tracuunhankhau.fragments;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.it.hientran.tracuunhankhau.R;
import com.it.hientran.tracuunhankhau.activity.DetailItemActivity;
import com.it.hientran.tracuunhankhau.adapter.HistoryAdapter;
import com.it.hientran.tracuunhankhau.adapter.PeopleAdapter;
import com.it.hientran.tracuunhankhau.internet.Config;
import com.it.hientran.tracuunhankhau.object.HistorySeen;
import com.it.hientran.tracuunhankhau.object.NguoiDan;
import com.it.hientran.tracuunhankhau.util.HistorySeenUtil;
import com.it.hientran.tracuunhankhau.util.NguoiDanUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 5/5/2017.
 */

public class HistorySeenFragment extends Fragment implements HistoryAdapter.OnItemClick,HistoryAdapter.OnDataChangeListener {

    public static String TAG;
    private final int ID_TAG = 1;
    private List<NguoiDan> listPeople;
    private List<HistorySeen> listHistory;
    private RecyclerView recyHistory;
    private HistoryAdapter adapter;
    private View helpView;
    private HistorySeenUtil historySeenUtil;

    private void inIt(View rootView) {
        recyHistory = (RecyclerView) rootView.findViewById(R.id.recy_history_seen);
        helpView = rootView.findViewById(R.id.view_history_help);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDataFromDb();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        HistorySeenFragment.TAG = getTag();
        View rootView = inflater.inflate(R.layout.fragment_history_search, container, false);
        inIt(rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new HistoryAdapter(getContext(), listPeople , listHistory , this);
        adapter.setOnDataChangeListener(this);
        recyHistory.setLayoutManager(new LinearLayoutManager(getContext()));
        recyHistory.setAdapter(adapter);
    }

    public void deleteHistory(boolean isDeleteHistory) {
        if (isDeleteHistory == true) {
            if(historySeenUtil.deleteHistory() == true){
                Toast.makeText(getContext(), "Delete history complete " + isDeleteHistory, Toast.LENGTH_SHORT).show();
                getDataFromDb();
                adapter = new HistoryAdapter(getContext(), listPeople , listHistory , this);
                recyHistory.setAdapter(adapter);
                adapter.setOnDataChangeListener(this);
            } else
                Toast.makeText(getContext(), "Delete history fail " + isDeleteHistory, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(int position) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(getActivity().getBaseContext(), DetailItemActivity.class));
        intent.putExtra(Config.INTENT_KEY_DETAIL_ITEM, listPeople.get(position));
        intent.putExtra(Config.INTENT_KEY_TAG_ID, ID_TAG);
        getActivity().startActivity(intent);
    }

    @Override
    public void onLongClick(int position) {

    }

    @Override
    public void onDataChanged(int size) {
        if (size != 0){
            helpView.setVisibility(View.INVISIBLE);
        }else {
            helpView.setVisibility(View.VISIBLE);
        }
    }
    private void getDataFromDb(){
        historySeenUtil = new HistorySeenUtil();
        try {
            listHistory = historySeenUtil.getDataFromDb();
            listPeople = new NguoiDanUtil().getDataFollowMaHdHisSeen(listHistory);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
