package com.it.hientran.tracuunhankhau.fragments;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.it.hientran.tracuunhankhau.R;
import com.it.hientran.tracuunhankhau.activity.DetailItemActivity;
import com.it.hientran.tracuunhankhau.activity.MainActivity;
import com.it.hientran.tracuunhankhau.adapter.PeopleAdapter;
import com.it.hientran.tracuunhankhau.object.NguoiDan;
import com.it.hientran.tracuunhankhau.internet.Config;
import com.it.hientran.tracuunhankhau.util.LogSystemUtil;
import com.it.hientran.tracuunhankhau.util.NguoiDanUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by admin on 5/5/2017.
 */

public class ListPeopleFragment extends BaseFragment {

    public static String TAG;
    private final int ID_TAG = 2;
    private List<NguoiDan> listPeople;
    private RecyclerView recyListPeole;
    private PeopleAdapter adapter;
    private static final int LIMIT_PAGE = 100;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ListPeopleFragment.TAG = getTag();
        return inflater.inflate(R.layout.fragment_list_people, container, false);
}

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyListPeole = (RecyclerView) view.findViewById(R.id.recycler_list_people);
        recyListPeole.setLayoutManager(new LinearLayoutManager(getContext()));
        int currentPage = this.getArguments().getInt(MainActivity.TAG_LIST_PEOPLE);
        listPeople =  getDataForTabListPeople(currentPage);
        if (listPeople != null){
            adapter = new PeopleAdapter(getContext(), (ArrayList<NguoiDan>) listPeople, this);
            recyListPeole.setAdapter(adapter);
        }

    }

    public void reloadListWhenChangePage(final int page){
      getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                List<NguoiDan> list = getDataForTabListPeople(page);
                if (list != null){
                    adapter.refreshData(list);
                }else {
                    Toast.makeText(getActivity(), "Kiểm tra lại kết nối của bạn!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private List<NguoiDan> getDataForTabListPeople(int currentPage) {
        List<NguoiDan> list = null;
        LogSystemUtil logSystemUtil = new LogSystemUtil();
        NguoiDanUtil nguoiDanUtil = new NguoiDanUtil((AppCompatActivity) getActivity());
        boolean checkExistPage = logSystemUtil.isExistLogPage(currentPage);
        if (checkExistPage == false) {
            try {
                String url = Config.convertToRequestGetList(Config.HOST_NAME_SERVER
                        , Config.JSON_FORMAT, Config.LIST_METHOD, currentPage, TAG);
                list = nguoiDanUtil.getDataFormServer(url);
                nguoiDanUtil.insertDataFromList(list , currentPage);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG , "getDataForTabListPeople" + e.getMessage());
            }
        } else {
            try {
                list = nguoiDanUtil.getDataFromDbFollowPage(LIMIT_PAGE, currentPage);
            } catch (Exception e) {
                Log.e(TAG , "getDataForTabListPeople" + e.getMessage());
                e.printStackTrace();
            }
        }
        return list;
    }

    public void refreshAndSortData(int page) {
//        List<NguoiDan> list = getDataForTabListPeople(page);
//        adapter.refreshData( list );
        adapter.sortItem(listPeople);
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
}
