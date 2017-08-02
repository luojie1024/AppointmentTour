package hnulab.appointmenttour.ui.search;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hnulab.appointmenttour.MyApplication;
import hnulab.appointmenttour.R;
import hnulab.appointmenttour.ui.adapter.SearchResultAdapter;
import hnulab.appointmenttour.ui.search_result.SearchResultActivity;
import hnulab.appointmenttour.widgets.CustomRecyclerView;


public class SearchActivity extends FragmentActivity implements PoiSearch.OnPoiSearchListener{
    private static final String TAG = SearchActivity.class.getName();
    @BindView(R.id.search_bar)
    MaterialSearchBar mSearchBar;
    @BindView(R.id.recycler)
    CustomRecyclerView mRecycler;
    @BindView(R.id.progressbar) ProgressBar mProgressBar;
    private SearchResultAdapter mSearchResultAdapter;
    private PoiSearch mPoiSearch;//POI搜索
    private PoiSearch.Query mPoitQuery;//POI查询条件类
    private InputMethodManager inputMethodManager;
    private String mCurrentCity;

    public static void newInstance(Activity activity,String cityName){
        Intent intent = new Intent(activity,SearchActivity.class);
        intent.putExtra("city_name",cityName);
        activity.startActivity(intent);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        init();
        initListener();
    }

    private void init() {
        if (null != getIntent()){
            mCurrentCity = getIntent().getStringExtra("city_name");
            Log.e(TAG,"mCurrentCity:" + mCurrentCity);
        }
        if (null == inputMethodManager)inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if (null == mSearchResultAdapter) mSearchResultAdapter = new SearchResultAdapter(getApplicationContext());
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setHasFixedSize(true);
        mRecycler.setItemAnimator(new DefaultItemAnimator());
        mRecycler.setAdapter(mSearchResultAdapter);
    }

    private void initListener() {
         mSearchBar.enableSearch();
        mSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean b) {

            }

            @Override
            public void onSearchConfirmed(CharSequence charSequence) {
                Log.e(TAG,"mCurrentCity：" + mCurrentCity);
                mPoitQuery = new PoiSearch.Query(String.valueOf(charSequence),"",mCurrentCity);
                mPoitQuery.setPageSize(Integer.MAX_VALUE);
                mPoitQuery.setPageNum(1);
                mPoiSearch = new PoiSearch(SearchActivity.this,mPoitQuery);
                mPoiSearch.setOnPoiSearchListener(SearchActivity.this);
                mPoiSearch.searchPOIAsyn();
                mProgressBar.setVisibility(View.VISIBLE);
                if (null != inputMethodManager){//隐藏软件盘
                    inputMethodManager.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(),0);
                }

            }

            @Override
            public void onButtonClicked(int i) {

            }
        });
        mSearchResultAdapter.setOnItemClickListener((view, poiItem, position) -> {
            SearchResultActivity.newInstance(SearchActivity.this,poiItem);
            finish();
        });

    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int rCode) {
        if (rCode == AMapException.CODE_AMAP_SUCCESS /*&& null != poiResult && null != poiResult.getQuery() && poiResult.getQuery() == mPoitQuery*/){
            if (poiResult.getPois().size() != 0) {
                List<PoiItem> poiItems = poiResult.getPois();
                mSearchResultAdapter.setList(poiItems);
                mSearchResultAdapter.notifyDataSetChanged();
                mRecycler.scheduleLayoutAnimation();//执行Item进入动画
                mProgressBar.setVisibility(View.INVISIBLE);
            }else {
                mProgressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(MyApplication.applicationContext,"对不起,没有搜索到相关数据",Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(MyApplication.applicationContext,"对不起,没有搜索到相关数据",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int rCode) {

    }
}

