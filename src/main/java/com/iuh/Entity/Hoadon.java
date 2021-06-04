package com.iuh.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table
public class Hoadon implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mahd;
    private LocalDate ngaylaphd;
    private String diachi;
    private String ghichu;

    private String phuongthucthanhtoan;

    @ManyToOne
    @JoinColumn(name = "khachhang_id", nullable=false)
    private Khachhang khachhang;

    @JsonIgnore
    @OneToMany(mappedBy = "hoadon")
    private List<Chitiethoadon> listcthd;


    public Hoadon(LocalDate ngaylaphd, String trangthai, String diachi, String ghichu, String phuongthucvanchuyen, Khachhang khachhang, List<Chitiethoadon> listcthd) {
        this.ngaylaphd = ngaylaphd;

        this.diachi = diachi;
        this.ghichu = ghichu;

        this.khachhang = khachhang;
        this.listcthd = listcthd;
    }

    public Hoadon() {
    }

    public String getGhichu() {
        return ghichu;
    }


    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public Long getMahd() {
        return mahd;
    }

    public void setMahd(Long mahd) {
        this.mahd = mahd;
    }

    public LocalDate getNgaylaphd() {
        return ngaylaphd;
    }

    public void setNgaylaphd(LocalDate ngaylaphd) {
        this.ngaylaphd = ngaylaphd;
    }

    public String getPhuongthucthanhtoan() {
        return phuongthucthanhtoan;
    }

    public void setPhuongthucthanhtoan(String phuongthucthanhtoan) {
        this.phuongthucthanhtoan = phuongthucthanhtoan;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public Khachhang getKhachhang() {
        return khachhang;
    }

    public void setKhachhang(Khachhang khachhang) {
        this.khachhang = khachhang;
    }

    public List<Chitiethoadon> getListcthd() {
        return listcthd;
    }

    public void setListcthd(List<Chitiethoadon> listcthd) {
        this.listcthd = listcthd;
    }

    @Override
    public String toString() {
        return "Hoadon{" +
                "diachi='" + diachi + '\'' +
                '}';
    }
}
