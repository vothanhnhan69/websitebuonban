package com.iuh.Entity;

import javax.persistence.*;

@Entity
@Table
public class Diachi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long madiachi;
    private String thanhpho;
    private String huyen;
    private String xa;
    private String ap;
    private String duong;

    @ManyToOne
    @JoinColumn(name = "nguoidung_id")
    private Nguoidung nguoidung;

    public Diachi(String thanhpho, String huyen, String xa, String ap, String duong, Nguoidung nguoidung) {
        this.thanhpho = thanhpho;
        this.huyen = huyen;
        this.xa = xa;
        this.ap = ap;
        this.duong = duong;
        this.nguoidung = nguoidung;
    }

    public Nguoidung getNguoidung() {
        return nguoidung;
    }

    public void setNguoidung(Nguoidung nguoidung) {
        this.nguoidung = nguoidung;
    }

    public Diachi() {
    }

    public Long getMadiachi() {
        return madiachi;
    }

    public void setMadiachi(Long madiachi) {
        this.madiachi = madiachi;
    }

    public String getThanhpho() {
        return thanhpho;
    }

    public void setThanhpho(String thanhpho) {
        this.thanhpho = thanhpho;
    }

    public String getHuyen() {
        return huyen;
    }

    public void setHuyen(String huyen) {
        this.huyen = huyen;
    }

    public String getXa() {
        return xa;
    }

    public void setXa(String xa) {
        this.xa = xa;
    }

    public String getAp() {
        return ap;
    }

    public void setAp(String ap) {
        this.ap = ap;
    }

    public String getDuong() {
        return duong;
    }

    public void setDuong(String duong) {
        this.duong = duong;
    }


}
