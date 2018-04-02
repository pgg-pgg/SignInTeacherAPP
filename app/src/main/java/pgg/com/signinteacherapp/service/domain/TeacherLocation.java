package pgg.com.signinteacherapp.service.domain;

/**
 * Created by PDD on 2018/3/31.
 */

public class TeacherLocation {
    private String id;
    private String name;
    private String longitude;
    private String latitude;
    private int isopen;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public int getIsopen() {
        return isopen;
    }

    public void setIsopen(int isopen) {
        this.isopen = isopen;
    }
}
