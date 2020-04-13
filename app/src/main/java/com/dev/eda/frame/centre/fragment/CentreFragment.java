package com.dev.eda.frame.centre.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dev.eda.R;
import com.dev.eda.app.base.BaseFragment;
import com.dev.eda.frame.centre.adapter.CentreAdapter;
import com.dev.eda.frame.centre.model.Centre;
import com.dev.eda.frame.home.model.PluginModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CentreFragment extends BaseFragment {


    private static CentreFragment mInstance;
    @BindView(R.id.recycler_view_centre)
    RecyclerView recyclerViewCentre;

    private List<Centre> centreList = new ArrayList<>();

    public static CentreFragment getInstance() {
        if (null == mInstance) {
            mInstance = new CentreFragment();
        }
        return mInstance;
    }


    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
//        if(this.getView() != null){
//            this.getView().setVisibility(menuVisible ? View.VISIBLE : View.GONE);
//        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ImmersionBar.with(this).statusBarColor(R.color.black).statusBarDarkFont(false).init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_centre;
    }

    @Override
    protected void initData() {
        for (int i = 0; i < 4; i++) {
            Centre centre = new Centre();
            List<PluginModel> entryModels = new ArrayList();
            for (int j = 0; j < 7; j++) {
                PluginModel entryModel = new PluginModel();
                entryModel.setImageResource(R.drawable.shanghai);
                entryModel.setName("name"+j);
                entryModels.add(entryModel);
            }
            centre.setTitle("分类"+i);
            centre.setEntryModelList(entryModels);
            centreList.add(centre);
        }
    }

    @Override
    protected void initView() {
        recyclerViewCentre.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewCentre.setAdapter(new CentreAdapter(R.layout.item_centre, centreList, getContext()));
    }

    @Override
    protected void setListener() {
        super.setListener();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mInstance = null;
    }
}
