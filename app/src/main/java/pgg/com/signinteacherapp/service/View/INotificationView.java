package pgg.com.signinteacherapp.service.View;

import pgg.com.signinteacherapp.service.domain.Results;

/**
 * Created by PDD on 2018/3/24.
 */

public interface INotificationView {
    void showProgress();

    void hideProgress();

    void onShowFailMsg();

    void onShowSuccessMsg(Results results);

    void onShowFailMsg(String s);

    void ToShowSignInActivity();
}
