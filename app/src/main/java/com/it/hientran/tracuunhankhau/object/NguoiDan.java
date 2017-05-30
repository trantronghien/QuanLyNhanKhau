package com.it.hientran.tracuunhankhau.object;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by admin on 5/17/2017.
 */
@Entity()
public class NguoiDan implements Parcelable {

    /**
     * MaHd : 17278
     * idChuHo : 15480
     * hoten : Phan Thi Xuân Câm
     * bidanh : phan thi xuan cam
     * NamSinh : 1985
     * socmnd : 83842851
     * Noicap : Lạng Sơn
     * ngaycap : 1993-09-02
     * QueQuan : Tân Bình, Đầm Hà, Quảng Ninh, Vietnam
     * nghenghiep : Y tá
     * hocvan : Unknown
     * tongiao : Minh Lý Đạo
     * quanhe : Unknown
     * Ghichu : Unknown
     * anh : Unknown
     * Gioitinh : Nữ
     * stt : 1
     */
    @Id
    private long MaHd;
    private String idChuHo;
    private String hoten;
    private String bidanh;
    private String NamSinh;
    private String socmnd;
    private String Noicap;
    private String ngaycap;
    private String QueQuan;
    private String nghenghiep;
    private String hocvan;
    private String tongiao;
    private String quanhe;
    private String Ghichu;
    private String anh;
    private String Gioitinh;
    private int stt;

    @Generated(hash = 399992926)
    public NguoiDan(long MaHd, String idChuHo, String hoten, String bidanh,
            String NamSinh, String socmnd, String Noicap, String ngaycap,
            String QueQuan, String nghenghiep, String hocvan, String tongiao,
            String quanhe, String Ghichu, String anh, String Gioitinh, int stt) {
        this.MaHd = MaHd;
        this.idChuHo = idChuHo;
        this.hoten = hoten;
        this.bidanh = bidanh;
        this.NamSinh = NamSinh;
        this.socmnd = socmnd;
        this.Noicap = Noicap;
        this.ngaycap = ngaycap;
        this.QueQuan = QueQuan;
        this.nghenghiep = nghenghiep;
        this.hocvan = hocvan;
        this.tongiao = tongiao;
        this.quanhe = quanhe;
        this.Ghichu = Ghichu;
        this.anh = anh;
        this.Gioitinh = Gioitinh;
        this.stt = stt;
    }

    @Generated(hash = 1470614212)
    public NguoiDan() {
    }

    protected NguoiDan(Parcel in) {
        MaHd = in.readLong();
        idChuHo = in.readString();
        hoten = in.readString();
        bidanh = in.readString();
        NamSinh = in.readString();
        socmnd = in.readString();
        Noicap = in.readString();
        ngaycap = in.readString();
        QueQuan = in.readString();
        nghenghiep = in.readString();
        hocvan = in.readString();
        tongiao = in.readString();
        quanhe = in.readString();
        Ghichu = in.readString();
        anh = in.readString();
        Gioitinh = in.readString();
        stt = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(MaHd);
        dest.writeString(idChuHo);
        dest.writeString(hoten);
        dest.writeString(bidanh);
        dest.writeString(NamSinh);
        dest.writeString(socmnd);
        dest.writeString(Noicap);
        dest.writeString(ngaycap);
        dest.writeString(QueQuan);
        dest.writeString(nghenghiep);
        dest.writeString(hocvan);
        dest.writeString(tongiao);
        dest.writeString(quanhe);
        dest.writeString(Ghichu);
        dest.writeString(anh);
        dest.writeString(Gioitinh);
        dest.writeInt(stt);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NguoiDan> CREATOR = new Creator<NguoiDan>() {
        @Override
        public NguoiDan createFromParcel(Parcel in) {
            return new NguoiDan(in);
        }

        @Override
        public NguoiDan[] newArray(int size) {
            return new NguoiDan[size];
        }
    };

    public long getMaHd() {
        return MaHd;
    }

    public void setMaHd(long MaHd) {
        this.MaHd = MaHd;
    }

    public String getIdChuHo() {
        return idChuHo;
    }

    public void setIdChuHo(String idChuHo) {
        this.idChuHo = idChuHo;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getBidanh() {
        return bidanh;
    }

    public void setBidanh(String bidanh) {
        this.bidanh = bidanh;
    }

    public String getNamSinh() {
        return NamSinh;
    }

    public void setNamSinh(String NamSinh) {
        this.NamSinh = NamSinh;
    }

    public String getSocmnd() {
        return socmnd;
    }

    public void setSocmnd(String socmnd) {
        this.socmnd = socmnd;
    }

    public String getNoicap() {
        return Noicap;
    }

    public void setNoicap(String Noicap) {
        this.Noicap = Noicap;
    }

    public String getNgaycap() {
        return ngaycap;
    }

    public void setNgaycap(String ngaycap) {
        this.ngaycap = ngaycap;
    }

    public String getQueQuan() {
        return QueQuan;
    }

    public void setQueQuan(String QueQuan) {
        this.QueQuan = QueQuan;
    }

    public String getNghenghiep() {
        return nghenghiep;
    }

    public void setNghenghiep(String nghenghiep) {
        this.nghenghiep = nghenghiep;
    }

    public String getHocvan() {
        return hocvan;
    }

    public void setHocvan(String hocvan) {
        this.hocvan = hocvan;
    }

    public String getTongiao() {
        return tongiao;
    }

    public void setTongiao(String tongiao) {
        this.tongiao = tongiao;
    }

    public String getQuanhe() {
        return quanhe;
    }

    public void setQuanhe(String quanhe) {
        this.quanhe = quanhe;
    }

    public String getGhichu() {
        return Ghichu;
    }

    public void setGhichu(String Ghichu) {
        this.Ghichu = Ghichu;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public String getGioitinh() {
        return Gioitinh;
    }

    public void setGioitinh(String Gioitinh) {
        this.Gioitinh = Gioitinh;
    }

    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }
}
