package com.it.hientran.tracuunhankhau.activity;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.database.MatrixCursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.it.hientran.tracuunhankhau.R;

import com.it.hientran.tracuunhankhau.fragments.HistorySeenFragment;
import com.it.hientran.tracuunhankhau.fragments.ListPeopleFragment;
import com.it.hientran.tracuunhankhau.fragments.SearchFragment;
import com.it.hientran.tracuunhankhau.object.NguoiDan;
import com.it.hientran.tracuunhankhau.customview.CircleTransform;
import com.it.hientran.tracuunhankhau.adapter.SearchHistoryAdapter;
import com.it.hientran.tracuunhankhau.customview.MySearchView;
import com.it.hientran.tracuunhankhau.util.NguoiDanUtil;
import com.it.hientran.tracuunhankhau.util.SharedPreferencesUtil;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private static final int LIMIT_PAGE = 100;
    private final String TAG = "MainActivity";
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private FloatingActionButton fabNextPage;
    private FloatingActionButton fabBackPage;

    public static final String TAG_SEARCH = "search";
    public static final String TAG_HISTORY_SEARCH = "history";
    public static final String TAG_LIST_PEOPLE = "ls_people";
    public static String CURRENT_TAG = TAG_SEARCH;
    private int navItemIndex = 0;
    private Handler mHandler;

    private MenuItem deleteActionMenuItem;
    private MenuItem searchActionMenuItem;
    private MenuItem refreshActionMenuItem;
    private MenuItem nextpageActionMenuItem;

    private final boolean VISIABLE_MENU_SEARCH = true;
    private final boolean VISIABLE_MENU_DELETE_HISTORY = true;
    private final boolean VISIABLE_MENU_LIST_TAB = true;

    private SharedPreferencesUtil sharedPreferencesUtil;
    private int currentPage;
    private Fragment currentFragment ;

    private void inIt() {
        fabNextPage = (FloatingActionButton) findViewById(R.id.fab_main_next_page);
        fabBackPage = (FloatingActionButton) findViewById(R.id.fab_main_back_page);
        fabNextPage.setOnClickListener(this);
        fabBackPage.setOnClickListener(this);
        mHandler = new Handler();
        sharedPreferencesUtil = new SharedPreferencesUtil(this);
        setCurrentPage(sharedPreferencesUtil.getIntPageCurrent());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inIt();
        ActivityNavigation();
        loadCurrentFragment();

        View navHeader = navigationView.getHeaderView(0);
        loadNavHeader(navHeader);

    }

    //============================================================================
    //                              Handler View
    //============================================================================

    void showFabNextPage(int tab) {
        if (tab != 2) {
            fabNextPage.hide();
            fabBackPage.hide();
        } else {
            fabNextPage.show();
            fabBackPage.show();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_main_search:
                navItemIndex = 0;
                CURRENT_TAG = TAG_SEARCH;
                fabNextPage.hide();
                updateActionBar(VISIABLE_MENU_SEARCH, false, false);
                break;
            case R.id.nav_main_history:
                navItemIndex = 1;
                CURRENT_TAG = TAG_HISTORY_SEARCH;
                fabNextPage.hide();
                updateActionBar(false, VISIABLE_MENU_DELETE_HISTORY, false);
                break;
            case R.id.nav_main_list:
                CURRENT_TAG = TAG_LIST_PEOPLE;
                navItemIndex = 2;
                fabNextPage.show();
                updateActionBar(false, false, VISIABLE_MENU_LIST_TAB);
                break;
        }
        showFabNextPage(navItemIndex);
        drawer.closeDrawer(GravityCompat.START);
        loadCurrentFragment();
        return true;
    }

    private Fragment getCurrentFragment(int navItemIndex) {
        switch (navItemIndex) {
            case 0:
                return new SearchFragment();
            case 1:
                return new HistorySeenFragment();
            case 2:
                return new ListPeopleFragment();
            default:
                return new SearchFragment();
        }
    }

    private void ActivityNavigation() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void loadCurrentFragment() {
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getCurrentFragment(navItemIndex);
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                if (fragment instanceof ListPeopleFragment) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(TAG_LIST_PEOPLE, getCurrentPage());
                    fragment.setArguments(bundle);
                }
                fragmentTransaction.replace(R.id.content_main, fragment, CURRENT_TAG);
                fragmentTransaction.commit();
                setCurrentFragment(fragment);
            }
        };
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }
    }

    private void loadNavHeader(View view) {
        TextView txtName = (TextView) view.findViewById(R.id.txt_nav_header_title);
        TextView txtDescription = (TextView) view.findViewById(R.id.txt_nav_header_description);
        ImageView imgNavHeaderBg = (ImageView) view.findViewById(R.id.img_nav_header_bg);
        ImageView imgProfile = (ImageView) view.findViewById(R.id.img_nav_header_profile);

        txtName.setText(this.getString(R.string.nav_header_title));
        txtDescription.setText(this.getString(R.string.nav_header_detail));

        // loading header background image
        Glide.with(this).load(R.drawable.bg_nav_header)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgNavHeaderBg);

        // Loading profile image
        Glide.with(this).load(R.drawable.profile_nav_header)
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new CircleTransform(this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgProfile);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        deleteActionMenuItem = menu.findItem(R.id.action_delete_history);
        searchActionMenuItem = menu.findItem(R.id.action_search);
        refreshActionMenuItem = menu.findItem(R.id.action_sorting);
        nextpageActionMenuItem = menu.findItem(R.id.action_next_page);
        refreshTitlePage(getCurrentPage());

        // show icon action home fragment when Created Menu
        updateActionBar(true, false, false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            final MySearchView search = (MySearchView) menu.findItem(R.id.action_search).getActionView();
            search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
            search.setOnSubmitListener(new MySearchView.searchOnSubmitListener() {

                @Override
                public void onSubmitButtonSearch(String query) {
                    ((SearchFragment) MainActivity.this
                            .getSupportFragmentManager()
                            .findFragmentByTag(SearchFragment.TAG))
                            .updateListFollowQuery(query);
                }

                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    if (newText.isEmpty()){
                        ((SearchFragment) MainActivity.this
                                .getSupportFragmentManager()
                                .findFragmentByTag(SearchFragment.TAG))
                                .updateListFollowQuery(newText);
                    }
                    return false;
                }
            });
            search.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {
                    ((SearchFragment) MainActivity.this
                            .getSupportFragmentManager()
                            .findFragmentByTag(SearchFragment.TAG)).closeSearchView();
                    return false;
                }
            });
        }
        return true;
    }

    private void refreshTitlePage(int currentPage) {
        nextpageActionMenuItem.setTitle(this.getString(R.string.acbar_page_list,
                "" + currentPage));
    }


    private void updateActionBar(boolean searchTab, boolean historyTab, boolean showMenuList) {
        if (searchActionMenuItem != null) {
            searchActionMenuItem.setVisible(searchTab);
        }
        if (deleteActionMenuItem != null) {
            deleteActionMenuItem.setVisible(historyTab);
        }
        if (refreshActionMenuItem != null && nextpageActionMenuItem != null) {
            refreshActionMenuItem.setVisible(showMenuList);
            nextpageActionMenuItem.setVisible(showMenuList);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (item.getItemId()) {
            case R.id.action_search:
                return true;
            case R.id.action_delete_history:
                showDiaLogDeleteHistory(fragmentManager);
                return true;
            case R.id.action_sorting:
                ((ListPeopleFragment) fragmentManager.findFragmentByTag(ListPeopleFragment.TAG))
                        .refreshAndSortData(getCurrentPage());
                return true;
            case R.id.action_next_page:
//                ((ListPeopleFragment) fragmentManager.findFragmentByTag(ListPeopleFragment.TAG)).reloadListWhenNextPage();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //Search History
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void loadHistory(String query, Menu menu) {
        List<NguoiDan> items = null;
        try {
            items = new NguoiDanUtil(this).getDataFromDbFollowPage(LIMIT_PAGE, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (items == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

            // Cursor
            String[] columns = new String[]{"_id", "text"};
            Object[] temp = new Object[]{0, "default"};

            MatrixCursor cursor = new MatrixCursor(columns);

            for (int i = 0; i < items.size(); i++) {
                temp[0] = i;
                temp[1] = items.get(i); // replaced s with i as s not used anywhere.
                cursor.addRow(temp);
            }

            // SearchView
            SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            final SearchView search = (SearchView) menu.findItem(R.id.action_search).getActionView();
            search.setSuggestionsAdapter(new SearchHistoryAdapter(this, cursor, items));

        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.fab_main_next_page:
                int Nextpage = getCurrentPage();
                Nextpage++;   //  but not save
                showDiaLogMessigeChangePage(checkPageInvalid(Nextpage));
                break;
            case R.id.fab_main_back_page:
                int backPage = getCurrentPage();
                backPage--;   //  but not save
                showDiaLogMessigeChangePage(checkPageInvalid(backPage));
                break;
        }
    }

    private int checkPageInvalid(int page){
        if (page < SharedPreferencesUtil.FIRSTINITPAGE)
            return SharedPreferencesUtil.FIRSTINITPAGE;
        else if (page >  SharedPreferencesUtil.NUMBER_PAGE)
            return SharedPreferencesUtil.NUMBER_PAGE;
        return page;
    }

    private void showDiaLogMessigeChangePage(final int page) {
        AlertDialog.Builder b = new AlertDialog.Builder(MainActivity.this);
        b.setTitle(this.getString(R.string.string_title_dialog_change_page));
        b.setMessage(this.getString(R.string.string_mes_dialog_change_page));
        b.setPositiveButton(this.getString(R.string.dialog_yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                sharedPreferencesUtil.savePageCurrent(page);
                refreshTitlePage(page);
                setCurrentPage(page);
                notifyPageChange(page);
            }
        });
        b.setNegativeButton(this.getString(R.string.dialog_no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        b.create().show();
    }

    private void notifyPageChange(int page){
        ListPeopleFragment fragment = (ListPeopleFragment) getCurrentFragment();
        if (fragment!=null){
            fragment.reloadListWhenChangePage(page);
        }else{
            Log.e(TAG, "showDiaLog onClick: null");
        }
    }

    private void showDiaLogDeleteHistory(final FragmentManager fragmentManager) {
        AlertDialog.Builder b = new AlertDialog.Builder(MainActivity.this);
        b.setTitle(this.getString(R.string.string_title_dialog_del_history));
        b.setMessage(this.getString(R.string.string_mes_dialog_del_history));
        b.setPositiveButton(this.getString(R.string.dialog_yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((HistorySeenFragment) fragmentManager.findFragmentByTag(HistorySeenFragment.TAG)).deleteHistory(true);
            }
        });
        b.setNegativeButton(this.getString(R.string.dialog_no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((HistorySeenFragment) fragmentManager.findFragmentByTag(HistorySeenFragment.TAG)).deleteHistory(false);
                dialog.cancel();
            }
        });
        b.create().show();
    }

    //============================================================================
    //                              Control Data
    //============================================================================

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getNavItemIndex() {
        return navItemIndex;
    }

    public Fragment getCurrentFragment() {
        return currentFragment;
    }

    public void setCurrentFragment(Fragment currentFragment) {
        this.currentFragment = currentFragment;
    }
}
