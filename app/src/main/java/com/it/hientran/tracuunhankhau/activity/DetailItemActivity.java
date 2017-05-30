package com.it.hientran.tracuunhankhau.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.it.hientran.tracuunhankhau.R;
import com.it.hientran.tracuunhankhau.object.HistorySeenDao;
import com.it.hientran.tracuunhankhau.object.NguoiDan;
import com.it.hientran.tracuunhankhau.internet.Config;
import com.it.hientran.tracuunhankhau.util.HistorySeenUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.it.hientran.tracuunhankhau.internet.Config.INTENT_KEY_TAG_ID;


public class DetailItemActivity extends AppCompatActivity {

    private TextView txtFullName;
    private TextView txtYearBirth;
    private TextView txtCmnd;
    private TextView txtPlaceBirth;
    private TextView txtNode;
    private TextView txtJob;
    private TextView txtReligion;
    private TextView txtStatus;
    private ImageView imgProfile;
    private final String FEMALE = "Nữ";

    private void initView() {
        txtFullName = (TextView) findViewById(R.id.text_detail_fullname);
        txtYearBirth = (TextView) findViewById(R.id.text_detail_yearbirth);
        txtCmnd = (TextView) findViewById(R.id.text_detail_cmnd);
        txtPlaceBirth = (TextView) findViewById(R.id.text_detail_placebirth);
        txtNode = (TextView) findViewById(R.id.text_detail_node);
        txtJob = (TextView) findViewById(R.id.text_detail_job);
        txtReligion = (TextView) findViewById(R.id.text_detail_religion);
        txtStatus = (TextView) findViewById(R.id.text_detail_status);
        imgProfile = (ImageView) findViewById(R.id.image_detail_profile);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_item);
        initView();
        Intent intent = getIntent();
        NguoiDan nguoiDan = intent.getParcelableExtra(Config.INTENT_KEY_DETAIL_ITEM);
        int idTag = intent.getIntExtra(INTENT_KEY_TAG_ID , 0);
        if (idTag != 1){
            new HistorySeenUtil().insertItemSeen(nguoiDan , getCurrentTime());
        }
        setDataForView(nguoiDan);
    }

    public Date getCurrentTime(){
        Calendar cal = Calendar.getInstance();
        return cal.getTime();
    }

    private void setDataForView(NguoiDan nguoiDan) {
        txtFullName.setText(nguoiDan.getHoten().toString());
        txtYearBirth.setText(this.getString(R.string.string_detail_yearbirth , nguoiDan.getNamSinh()));
        txtCmnd.setText(this.getString(R.string.string_detail_cmnd , nguoiDan.getSocmnd()));
        txtPlaceBirth.setText(this.getString(R.string.string_detail_placebirth , nguoiDan.getQueQuan()));
        txtNode.setText(this.getString(R.string.string_detail_node , nguoiDan.getGhichu()));
        txtJob.setText(this.getString(R.string.string_detail_job , nguoiDan.getNghenghiep()));
        txtReligion.setText(this.getString(R.string.string_detail_religion , nguoiDan.getTongiao()));
        String status ;
        if (nguoiDan.getStt() == 1)
            status = "Còn sống";
        else{
            status = "Đã qua đời";
        }
        txtStatus.setText(this.getString(R.string.string_detail_religion , status));
        int drawable = nguoiDan.getGioitinh().equalsIgnoreCase(FEMALE) ? R.drawable.ic_profile_female : R.drawable.ic_profile_male ;
        imgProfile.setImageResource(drawable);
    }


}
