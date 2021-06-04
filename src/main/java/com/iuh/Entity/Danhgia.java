package com.iuh.Entity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table
public class Danhgia implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long madanhgia;

    @ManyToOne
    @JoinColumn(name = "sampham_id", nullable=false)
    private Sanpham sanpham;

    @ManyToOne
    @JoinColumn(name = "khachhang_id", nullable=false)
    private Khachhang khachhang;

    private String noidung;

    private LocalDate ngaydg;

    public Danhgia() {
    }

    public LocalDate getNgaydg() {
        return ngaydg;
    }

    public void setNgaydg(LocalDate ngaydg) {
        this.ngaydg = ngaydg;
    }

    public Danhgia(Sanpham sanpham, Khachhang khachhang, String noidung, LocalDate ngaydanhgia) {
        this.sanpham = sanpham;
        this.khachhang = khachhang;
        this.noidung = noidung;
        this.ngaydg = ngaydanhgia;
    }

    public LocalDate getThoigian() {
        return ngaydg;
    }

    public void setThoigian(LocalDate ngaydanhgia) {
        this.ngaydg = ngaydanhgia;
    }

    public Long getMadanhgia() {
        return madanhgia;
    }

    public void setMadanhgia(Long madanhgia) {
        this.madanhgia = madanhgia;
    }

    public Sanpham getSanpham() {
        return sanpham;
    }

    public void setSanpham(Sanpham sanpham) {
        this.sanpham = sanpham;
    }

    public Khachhang getKhachhang() {
        return khachhang;
    }

    public void setKhachhang(Khachhang khachhang) {
        this.khachhang = khachhang;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    @Override
    public String toString() {
        return "Danhgia{" +
                "madanhgia=" + madanhgia +
                ", sanpham=" + sanpham +
                ", khachhang=" + khachhang +
                ", noidung='" + noidung + '\'' +
                ", ngaydanhgia=" + ngaydg +
                '}';
    }
}
