package com.iuh.Entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
public class Chitiethoadon implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long machitiethoadon;

    @ManyToOne
    @JoinColumn(name = "hoadon_id", nullable=false)
    private Hoadon hoadon;

    @ManyToOne
    @JoinColumn(name = "sanpham_id", nullable=false)
    private Sanpham sanpham;

    private int soluong;
    private double dongia;
    private String trangthai;

    public Chitiethoadon() {
    }


    public Chitiethoadon(Hoadon hoadon, Sanpham sanpham, int soluong, double dongia) {
        this.hoadon = hoadon;
        this.sanpham = sanpham;
        this.soluong = soluong;
        this.dongia = dongia;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }

    public Long getMachitiethoadon() {
        return machitiethoadon;
    }

    public void setMachitiethoadon(Long machitiethoadon) {
        this.machitiethoadon = machitiethoadon;
    }

    public Hoadon getHoadon() {
        return hoadon;
    }

    public void setHoadon(Hoadon hoadon) {
        this.hoadon = hoadon;
    }

    public Sanpham getSanpham() {
        return sanpham;
    }

    public void setSanpham(Sanpham sanpham) {
        this.sanpham = sanpham;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public double getDongia() {
        return dongia;
    }

    public void setDongia(double dongia) {
        this.dongia = dongia;
    }

    @Override
    public String toString() {
        return "Chitiethoadon{" +
                "machitiethoadon=" + machitiethoadon +
                ", hoadon=" + hoadon +
                ", sanpham=" + sanpham +
                ", soluong=" + soluong +
                ", dongia=" + dongia +
                '}';
    }
}











