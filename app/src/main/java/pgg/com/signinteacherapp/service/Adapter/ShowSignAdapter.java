package pgg.com.signinteacherapp.service.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import pgg.com.signinteacherapp.R;
import pgg.com.signinteacherapp.common.Constant;
import pgg.com.signinteacherapp.service.Activity.ShowLocationActivity;
import pgg.com.signinteacherapp.service.domain.Location;
import pgg.com.signinteacherapp.service.domain.StudentInfo;

/**
 * Created by PDD on 2018/4/1.
 */

public class ShowSignAdapter extends RecyclerView.Adapter<ShowSignAdapter.ShowSignViewHolder> {
    List<Location> locations=new ArrayList<>();
    List<StudentInfo> studentInfos=new ArrayList<>();
    Activity context;

    public List<Location> getLocations() {
        return locations;
    }

    public List<StudentInfo> getStudentInfos() {
        return studentInfos;
    }

    public ShowSignAdapter(Activity context, List<Location> locations, List<StudentInfo> studentInfos) {
        this.context=context;
        if (locations!=null&&studentInfos!=null){
            this.locations=locations;
            this.studentInfos=studentInfos;
        }
    }

    @Override
    public ShowSignViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ShowSignViewHolder(LayoutInflater.from(context).inflate(R.layout.item_show_sign,parent,false));
    }

    @Override
    public void onBindViewHolder(ShowSignViewHolder holder, int position) {
        final Location location=locations.get(position);
        final StudentInfo studentInfo=studentInfos.get(position);
        Glide.with(context).load(Constant.BASE_URL+studentInfo.getHead_icon()).centerCrop().into(holder.iv_head_icon);
        holder.tv_name.setText(studentInfo.getName());
        holder.tv_sign_num.setText(location.getSignnum()+" 经验值");
        if (location.getIssign()==0){
            holder.tv_issign.setText("未签到");
            holder.tv_issign.setTextColor(Color.GRAY);
        }else {
            holder.tv_issign.setText("已签到");
        }
        holder.tv_stu_id.setText(studentInfo.getId());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ShowLocationActivity.class);
                intent.putExtra(Constant.LONGITUDE,location.getLongitude());
                intent.putExtra(Constant.LATITUDE,location.getLatitude());
                intent.putExtra(Constant.HEAD_ICON,studentInfo.getHead_icon());
                intent.putExtra(Constant.NAME,studentInfo.getName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return studentInfos.size();
    }

    class ShowSignViewHolder extends RecyclerView.ViewHolder{
        View view;
        CircleImageView iv_head_icon;
        TextView tv_name,tv_stu_id,tv_issign,tv_sign_num;
        public ShowSignViewHolder(View itemView) {
            super(itemView);
            this.view=itemView;
            iv_head_icon=itemView.findViewById(R.id.iv_head_icon);
            tv_name=itemView.findViewById(R.id.tv_name);
            tv_stu_id=itemView.findViewById(R.id.tv_stu_id);
            tv_issign=itemView.findViewById(R.id.tv_issign);
            tv_sign_num=itemView.findViewById(R.id.tv_sign_num);
        }
    }
}
