package com.it.hientran.tracuunhankhau.object;

import android.support.annotation.Nullable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by admin on 5/26/2017.
 * cache data had load , Page number records
 */
@Entity
public class LogSystem{
    @Id
    private Long idPage;
    private int numberPage;
    private long MaHd;
    @Generated(hash = 1632170826)
    public LogSystem(Long idPage, int numberPage, long MaHd) {
        this.idPage = idPage;
        this.numberPage = numberPage;
        this.MaHd = MaHd;
    }
    @Generated(hash = 2015434616)
    public LogSystem() {
    }
    public Long getIdPage() {
        return this.idPage;
    }
    public void setIdPage(Long idPage) {
        this.idPage = idPage;
    }
    public int getNumberPage() {
        return this.numberPage;
    }
    public void setNumberPage(int numberPage) {
        this.numberPage = numberPage;
    }
    public long getMaHd() {
        return this.MaHd;
    }
    public void setMaHd(long MaHd) {
        this.MaHd = MaHd;
    }

 
}
