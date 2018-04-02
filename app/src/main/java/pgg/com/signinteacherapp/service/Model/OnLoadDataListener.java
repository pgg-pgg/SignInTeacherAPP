package pgg.com.signinteacherapp.service.Model;

/**
 * Created by PDD on 2018/3/24.
 */

public interface OnLoadDataListener<T> {

    void onSuccess(T data);
    void onRequestFail(Throwable e);
    void onRequestCodeFail(T data);


}
