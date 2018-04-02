package pgg.com.signinteacherapp.data.api;

import java.util.List;

import pgg.com.signinteacherapp.service.domain.Results;
import pgg.com.signinteacherapp.service.domain.StudentInfo;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by PDD on 2018/4/1.
 */

public interface StudentInfoService {

    @GET("User/getAllStudentInfo")
    Observable<Results<List<StudentInfo>>> getAllStudentInfo();
}
