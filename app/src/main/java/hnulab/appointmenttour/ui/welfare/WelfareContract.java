package hnulab.appointmenttour.ui.welfare;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import java.util.List;

import hnulab.appointmenttour.base.BasePresenter;
import hnulab.appointmenttour.base.BaseView;
import hnulab.appointmenttour.bean.GankResultWelfare;

/**
 * Created by dingmouren on 2016/12/1.
 */

public interface WelfareContract {

    interface View extends BaseView {
        void setDataRefresh(boolean refresh);
        StaggeredGridLayoutManager getLayoutManager();
        RecyclerView getRecyclerView();
        void setData(List<GankResultWelfare> list);
    }

    interface Presenter<V extends BaseView> extends BasePresenter<View> {
        void requestData();
    }

}
