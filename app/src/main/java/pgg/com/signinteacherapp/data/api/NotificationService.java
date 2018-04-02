package pgg.com.signinteacherapp.data.api;

import java.util.List;

import pgg.com.signinteacherapp.service.domain.Location;
import pgg.com.signinteacherapp.service.domain.Results;
import pgg.com.signinteacherapp.service.domain.TeacherLocation;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by PDD on 2018/3/31.
 */

public interface NotificationService {

    @POST("Notification/sendNotification")
    Observable<Results> sendNotification(@Body TeacherLocation teacherLocation);

    @POST("Notification/closeSignApi")
    Observable<Results> closeSignApi(@Body TeacherLocation teacherLocation);

    @GET("Location/getAllLocationInfo")
    Observable<Results<List<Location>>> getAllLocationInfo();
}
