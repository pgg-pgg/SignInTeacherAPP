package pgg.com.signinteacherapp.service.View;

import pgg.com.signinteacherapp.service.domain.Results;

/**
 * Created by PDD on 2018/3/31.
 */

public interface IShowSignView {
    void showProgress();

    void hideProgress();

    void onShowFailMsg();

    void onShowSuccessMsg(Results results);

    void onShowFailMsg(String s);

    void newData(Results data);
}
