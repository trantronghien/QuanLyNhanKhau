package com.it.hientran.tracuunhankhau.object;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import java.util.Date;


/**
 * Created by admin on 5/23/2017.
 */

@Entity
public class HistorySeen {

    @Id
    private Long id;
    private long MaHd;
    private String idChuHo;
    private String clearName;
    private Date dateSeen;
    @Generated(hash = 52743851)
    public HistorySeen(Long id, long MaHd, String idChuHo, String clearName,
            Date dateSeen) {
        this.id = id;
        this.MaHd = MaHd;
        this.idChuHo = idChuHo;
        this.clearName = clearName;
        this.dateSeen = dateSeen;
    }
    @Generated(hash = 1831120438)
    public HistorySeen() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public long getMaHd() {
        return this.MaHd;
    }
    public void setMaHd(long MaHd) {
        this.MaHd = MaHd;
    }
    public String getIdChuHo() {
        return this.idChuHo;
    }
    public void setIdChuHo(String idChuHo) {
        this.idChuHo = idChuHo;
    }
    public String getClearName() {
        return this.clearName;
    }
    public void setClearName(String clearName) {
        this.clearName = clearName;
    }
    public Date getDateSeen() {
        return this.dateSeen;
    }
    public void setDateSeen(Date dateSeen) {
        this.dateSeen = dateSeen;
    }


}
