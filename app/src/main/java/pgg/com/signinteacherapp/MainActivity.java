package pgg.com.signinteacherapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import pgg.com.signinteacherapp.service.Activity.ShowSignInActivity;
import pgg.com.signinteacherapp.service.Presenter.INotificationPresenter;
import pgg.com.signinteacherapp.service.View.INotificationView;
import pgg.com.signinteacherapp.service.domain.Results;
import pgg.com.signinteacherapp.service.domain.TeacherLocation;

public class MainActivity extends AppCompatActivity implements INotificationView {

    private INotificationPresenter presenter;
    private ProgressBar progress_send;

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;

    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    private double latitude;
    private double longitude;

    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {

        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            // TODO Auto-generated method stub
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    //可在其中解析amapLocation获取相应内容。
                    double locationType = amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                    //获取纬度
                    latitude = amapLocation.getLatitude();
                    longitude = amapLocation.getLongitude();
                }else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError","location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);

        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Battery_Saving，低功耗模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);


        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
        //该方法默认为false，true表示只定位一次
        mLocationOption.setOnceLocation(true);

        progress_send = (ProgressBar) findViewById(R.id.progress_send);
        presenter=new INotificationPresenter(this);
        findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TeacherLocation location=new TeacherLocation();
                location.setId(1+"");
                location.setIsopen(1);
                location.setLatitude(latitude+"");
                location.setLongitude(longitude+"");
                presenter.sendNotification(location);
            }
        });
    }

    @Override
    public void showProgress() {
        progress_send.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progress_send.setVisibility(View.GONE);
    }

    @Override
    public void onShowFailMsg() {
        Toast.makeText(this,"发布签到失败，请检查网络重试...",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onShowSuccessMsg(Results results) {
        Toast.makeText(this,"发布签到成功",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onShowFailMsg(String s) {
        Toast.makeText(this,"发布签到失败，请检查网络重试",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ToShowSignInActivity() {
        Intent intent=new Intent(MainActivity.this, ShowSignInActivity.class);
        startActivity(intent);
    }
}
