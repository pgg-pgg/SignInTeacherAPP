package pgg.com.signinteacherapp.service.Model;

import java.util.List;

import pgg.com.signinteacherapp.data.httpData.HttpData;
import pgg.com.signinteacherapp.service.domain.Location;
import pgg.com.signinteacherapp.service.domain.Results;
import pgg.com.signinteacherapp.service.domain.StudentInfo;
import pgg.com.signinteacherapp.service.domain.TeacherLocation;
import rx.Observer;

/**
 * Created by PDD on 2018/3/31.
 */

public class CloseSignApiModel {

    public void closeSignApi(final OnLoadDataListener listener, TeacherLocation location){
        HttpData.getInstance().closeSignApi(new Observer<Results>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onRequestFail(e);
            }

            @Override
            public void onNext(Results results) {
                listener.onSuccess(results);
            }
        },location);
    }

    public void getAllStudentInfo(final OnLoadDataListener listener){
        HttpData.getInstance().getAllStudentInfo(new Observer<Results<List<StudentInfo>>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onRequestFail(e);
            }

            @Override
            public void onNext(Results<List<StudentInfo>> listResults) {
                if (listResults.getCode()==0){
                    listener.onSuccess(listResults);
                }else {
                    listener.onRequestCodeFail(listResults);
                }
            }
        });
    }

    public void getAllLocationInfo(final OnLoadDataListener listener){
        HttpData.getInstance().getAllLocationInfo(new Observer<Results<List<Location>>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onRequestFail(e);
            }

            @Override
            public void onNext(Results<List<Location>> listResults) {
                if (listResults.getCode()==0){
                    listener.onSuccess(listResults);
                }else {
                    listener.onRequestCodeFail(listResults);
                }
            }
        });
    }
}
