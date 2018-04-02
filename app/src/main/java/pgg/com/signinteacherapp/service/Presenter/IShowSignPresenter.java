package pgg.com.signinteacherapp.service.Presenter;

import android.util.Log;

import java.util.List;

import pgg.com.signinteacherapp.service.Model.CloseSignApiModel;
import pgg.com.signinteacherapp.service.Model.OnLoadDataListener;
import pgg.com.signinteacherapp.service.View.IShowSignView;
import pgg.com.signinteacherapp.service.domain.Results;
import pgg.com.signinteacherapp.service.domain.StudentInfo;
import pgg.com.signinteacherapp.service.domain.TeacherLocation;

/**
 * Created by PDD on 2018/3/31.
 */

public class IShowSignPresenter implements OnLoadDataListener<Results>{

    private CloseSignApiModel mModel;
    private IShowSignView mView;

    public IShowSignPresenter(IShowSignView mView) {
        this.mView=mView;
        this.mModel=new CloseSignApiModel();
    }

    public void closeSignApi(TeacherLocation location){
        mView.showProgress();
        mModel.closeSignApi(this,location);
    }

    public void getAllStudentInfo(){
        mView.showProgress();
        mModel.getAllStudentInfo(this);
    }

    public void getLocationInfo(){
        mView.showProgress();
        mModel.getAllLocationInfo(this);
    }



    @Override
    public void onSuccess(Results data) {
        if (data.getData()==null){
            mView.hideProgress();
            mView.onShowSuccessMsg(data);
        }else {
            mView.newData(data);
            mView.hideProgress();
        }

    }

    @Override
    public void onRequestFail(Throwable e) {
        mView.hideProgress();
        mView.onShowFailMsg();
        Log.e("okhttp",e.getMessage());
    }

    @Override
    public void onRequestCodeFail(Results data) {
        mView.hideProgress();
        mView.onShowFailMsg(data.getMessage());
    }
}
