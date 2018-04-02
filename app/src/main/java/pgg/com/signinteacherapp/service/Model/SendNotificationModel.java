package pgg.com.signinteacherapp.service.Model;

import pgg.com.signinteacherapp.data.httpData.HttpData;
import pgg.com.signinteacherapp.service.domain.Results;
import pgg.com.signinteacherapp.service.domain.TeacherLocation;
import rx.Observer;

/**
 * Created by PDD on 2018/3/31.
 */

public class SendNotificationModel {

    public void sendNotification(final OnLoadDataListener listener, TeacherLocation location) {
        HttpData.getInstance().sendNotification(new Observer<Results>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onRequestFail(e);
            }

            @Override
            public void onNext(Results results) {
                if (results.getCode()==0){
                    listener.onSuccess(results);
                }else {
                    listener.onRequestCodeFail(results);
                }
            }
        },location);
    }
}
