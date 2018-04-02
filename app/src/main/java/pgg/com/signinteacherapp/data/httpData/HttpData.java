package pgg.com.signinteacherapp.data.httpData;

import java.io.File;
import java.util.List;

import io.rx_cache.DynamicKey;
import io.rx_cache.EvictDynamicKey;
import io.rx_cache.Reply;
import io.rx_cache.internal.RxCache;
import pgg.com.signinteacherapp.common.Constant;
import pgg.com.signinteacherapp.data.api.CacheProviders;
import pgg.com.signinteacherapp.data.api.NotificationService;
import pgg.com.signinteacherapp.data.api.StudentInfoService;
import pgg.com.signinteacherapp.data.retrofit.RetrofitUtils;
import pgg.com.signinteacherapp.data.util.FileUtils;
import pgg.com.signinteacherapp.service.domain.Location;
import pgg.com.signinteacherapp.service.domain.Results;
import pgg.com.signinteacherapp.service.domain.StudentInfo;
import pgg.com.signinteacherapp.service.domain.TeacherLocation;
import retrofit2.adapter.rxjava.Result;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by PDD on 2018/3/23.
 */

public class HttpData {
    private static File cacheDirectory = FileUtils.getChaheDirectory();
    private static final CacheProviders providers = new RxCache.Builder()
            .persistence(cacheDirectory)
            .using(CacheProviders.class);
    private NotificationService notificationService= RetrofitUtils.getRetrofit(Constant.BASE_URL).create(NotificationService.class);

    private StudentInfoService studentInfoService=RetrofitUtils.getRetrofit(Constant.BASE_URL).create(StudentInfoService.class);

    private static class SingletonHolder {
        private static final HttpData INSTANCE = new HttpData();
    }

    public static HttpData getInstance() {
        return SingletonHolder.INSTANCE;
    }
    public void sendNotification(Observer<Results> observable, TeacherLocation location) {
        Observable<Results> resultsObservable = notificationService.sendNotification(location);
        setSubscribe(resultsObservable,observable);
    }

    public void closeSignApi(Observer<Results> observer,TeacherLocation location){
        Observable<Results> resultsObservable=notificationService.closeSignApi(location);
        setSubscribe(resultsObservable,observer);
    }

    public void getAllLocationInfo(Observer<Results<List<Location>>> observer){
        Observable<Results<List<Location>>> allLocationInfo = notificationService.getAllLocationInfo();
        setSubscribe(allLocationInfo,observer);
    }

    public void getAllStudentInfo(Observer<Results<List<StudentInfo>>> observer){
        Observable<Results<List<StudentInfo>>> allStudentInfo = studentInfoService.getAllStudentInfo();
        Observable<Results<List<StudentInfo>>> cacheObserveable = providers.getAllStudentInfos(allStudentInfo, new DynamicKey("password"), new EvictDynamicKey(true)).map(new HttpCacheHandler<Results<List<StudentInfo>>>());
        setSubscribe(cacheObserveable,observer);
    }
    private static <T> void setSubscribe(Observable<T> listObservable, Observer<T> observable) {
        listObservable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observable);
    }

    private class HttpCacheHandler<T> implements Func1<Reply<T>, T> {
        @Override
        public T call(Reply<T> tReply) {
            return tReply.getData();
        }
    }

}
