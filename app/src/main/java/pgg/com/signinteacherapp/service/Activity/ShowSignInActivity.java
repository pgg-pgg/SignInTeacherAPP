package pgg.com.signinteacherapp.service.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import pgg.com.signinteacherapp.R;
import pgg.com.signinteacherapp.service.Adapter.ShowSignAdapter;
import pgg.com.signinteacherapp.service.Presenter.IShowSignPresenter;
import pgg.com.signinteacherapp.service.View.IShowSignView;
import pgg.com.signinteacherapp.service.domain.Location;
import pgg.com.signinteacherapp.service.domain.Results;
import pgg.com.signinteacherapp.service.domain.StudentInfo;
import pgg.com.signinteacherapp.service.domain.TeacherLocation;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by PDD on 2018/3/31.
 */

public class ShowSignInActivity extends AppCompatActivity implements IShowSignView{

    @Bind(R.id.toolbar_view)
    Toolbar toolbar_view;
    @Bind(R.id.progress_show_sign)
    ProgressBar progress_show_sign;

    @Bind(R.id.rv_student_info)
    RecyclerView rv_student_info;

    @Bind(R.id.swipyrefreshlayout)
    SwipyRefreshLayout swipyRefreshLayout;

    @Bind(R.id.tv_stu_num)
    TextView tv_stu_num;

    private List<Location> locations=new ArrayList<>();
    private List<StudentInfo> studentInfos=new ArrayList<>();

    private IShowSignPresenter presenter;
    private ShowSignAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_sign);
        ButterKnife.bind(this);
        toolbar_view.setTitle("签到");
        toolbar_view.inflateMenu(R.menu.navigation);
        toolbar_view.setOnMenuItemClickListener(onMenuItemClick);
        presenter = new IShowSignPresenter(this);
        initRecyclerView();
        initSwipyRefreshLayout();
        getData();
    }

    private void getData() {
        locations.clear();
        studentInfos.clear();
        presenter.getAllStudentInfo();
        presenter.getLocationInfo();
    }

    private void initSwipyRefreshLayout() {
        swipyRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        swipyRefreshLayout.setDirection(SwipyRefreshLayoutDirection.BOTH);
        swipyRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                Observable.timer(2, TimeUnit.SECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Long>() {
                            @Override
                            public void call(Long aLong) {
                                swipyRefreshLayout.setRefreshing(false);
                            }
                        });
                getData();
            }
        });
    }

    private void initRecyclerView() {
        rv_student_info.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        rv_student_info.setItemAnimator(new DefaultItemAnimator());
        adapter = new ShowSignAdapter(this,null,null);
        rv_student_info.setAdapter(adapter);
    }

    private Toolbar.OnMenuItemClickListener onMenuItemClick=new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()){
                case R.id.action_close_sign:
                    //关闭签到
                    TeacherLocation location=new TeacherLocation();
                    location.setId(1+"");
                    location.setIsopen(0);
                    presenter.closeSignApi(location);
                    break;
            }
            return true;
        }
    };

    @Override
    public void showProgress() {
        progress_show_sign.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progress_show_sign.setVisibility(View.GONE);
    }

    @Override
    public void onShowFailMsg() {
        Toast.makeText(this,"关闭失败，请检查网络重试",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onShowSuccessMsg(Results results) {
        Toast.makeText(this,results.getMessage(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onShowFailMsg(String s) {
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void newData(Results data) {
        if (data.getData() instanceof List){
            List list=(List)data.getData();
            tv_stu_num.setText(list.size()+"人");
            if (list.get(0) instanceof Location){
                locations.addAll(list);
            }else if (list.get(0) instanceof StudentInfo){
                studentInfos.addAll(list);
            }
        }
        dealWithDataInRecyclerView(locations,studentInfos);
    }

    private void dealWithDataInRecyclerView(List<Location> locations, List<StudentInfo> studentInfos) {
        adapter.getLocations().clear();
        adapter.getStudentInfos().clear();
        adapter.getLocations().addAll(locations);
        adapter.getStudentInfos().addAll(studentInfos);
        adapter.notifyDataSetChanged();
    }
}
