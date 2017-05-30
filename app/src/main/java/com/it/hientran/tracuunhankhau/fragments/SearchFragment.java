package com.it.hientran.tracuunhankhau.fragments;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.it.hientran.tracuunhankhau.R;
import com.it.hientran.tracuunhankhau.activity.DetailItemActivity;
import com.it.hientran.tracuunhankhau.adapter.PeopleAdapter;
import com.it.hientran.tracuunhankhau.object.NguoiDan;
import com.it.hientran.tracuunhankhau.internet.Config;
import com.it.hientran.tracuunhankhau.util.NguoiDanUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 5/5/2017.
 */

public class SearchFragment extends BaseFragment {

    public static String TAG ;
    private final int ID_TAG = 0;
    private RecyclerView recyclerSearch;
    private LinearLayout SearchHelp;
    private PeopleAdapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<NguoiDan> listPeople;


    private void inIt(View view){
        recyclerSearch = (RecyclerView) view.findViewById(R.id.recy_search);
        recyclerSearch.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerSearch.setLayoutManager(mLayoutManager);
        SearchHelp = (LinearLayout) view.findViewById(R.id.view_search_help);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SearchFragment.TAG = getTag();
        View rootView = inflater.inflate(R.layout.fragment_search , container , false);
        inIt(rootView);
        return rootView;
    }

    public void updateListFollowQuery(String query){
        NguoiDanUtil nguoiDanUtil = new NguoiDanUtil((AppCompatActivity) getActivity());
        List<NguoiDan> list = nguoiDanUtil.getDataFollowQuery(query , (AppCompatActivity) getActivity());
        if (list != null){
            if (list.size() != 0 ){
                SearchHelp.setVisibility(View.INVISIBLE);
                adapter = new PeopleAdapter(getContext() , (ArrayList<NguoiDan>) list, this);
                recyclerSearch.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                setListPeople(list);
            }
            else {
                adapter.cleanData();
                SearchHelp.setVisibility(View.VISIBLE);
            }
        }else {
            adapter.cleanData();
            SearchHelp.setVisibility(View.VISIBLE);
        }
    }
    public void closeSearchView(){
        adapter.cleanData();
        SearchHelp.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(int position) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(getActivity().getBaseContext() , DetailItemActivity.class));
        intent.putExtra(Config.INTENT_KEY_DETAIL_ITEM , listPeople.get(position));
        intent.putExtra(Config.INTENT_KEY_TAG_ID , ID_TAG);
        getActivity().startActivity(intent);
    }

    @Override
    public void onLongClick(int position) {

    }

    private void setListPeople(List<NguoiDan> listPeople) {
        this.listPeople = listPeople;
    }
}
