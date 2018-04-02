package pgg.com.signinteacherapp.service.Presenter;

import pgg.com.signinteacherapp.service.Model.SendNotificationModel;
import pgg.com.signinteacherapp.service.Model.OnLoadDataListener;
import pgg.com.signinteacherapp.service.View.INotificationView;
import pgg.com.signinteacherapp.service.domain.Results;
import pgg.com.signinteacherapp.service.domain.TeacherLocation;

/**
 * Created by PDD on 2018/3/24.
 */

public class INotificationPresenter implements OnLoadDataListener<Results> {

    private INotificationView mView;
    private SendNotificationModel mModel;

    public INotificationPresenter(INotificationView mView){
        this.mView=mView;
        mModel=new SendNotificationModel();
    }

    public void sendNotification(TeacherLocation location){
        mView.showProgress();
        mModel.sendNotification(this,location);
    }

    @Override
    public void onSuccess(Results data) {
        mView.hideProgress();
        mView.onShowSuccessMsg(data);
        mView.ToShowSignInActivity();
    }

    @Override
    public void onRequestFail(Throwable e) {
        mView.hideProgress();
        mView.onShowFailMsg();
    }

    @Override
    public void onRequestCodeFail(Results data) {
        mView.hideProgress();
        mView.onShowFailMsg(data.getMessage());
    }
}
