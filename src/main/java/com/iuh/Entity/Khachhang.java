package com.iuh.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table
public class Khachhang implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long makhachhang;
    private String tenkhachhang;
    private String sdt;

    @JsonIgnore
    @OneToMany(mappedBy = "khachhang")
    private List<Hoadon> dshoadon;



    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "nguoidung_id", nullable=false)
    private Nguoidung nguoidung;

    @JsonBackReference
    @OneToMany(mappedBy = "khachhang")
    private List<Danhgia> dsdanhgia;

    @JsonBackReference
    @OneToMany(mappedBy = "khachhang")
    private List<Yeuthich> dsyeuthich;

    public Khachhang() {
    }

    public Khachhang(String tenkhachhang, String sdt, List<Hoadon> dshoadon, Nguoidung nguoidung, List<Danhgia> dsdanhgia, List<Yeuthich> dsyeuthich) {
        this.tenkhachhang = tenkhachhang;
        this.sdt = sdt;
        this.dshoadon = dshoadon;
        this.nguoidung = nguoidung;
        this.dsdanhgia = dsdanhgia;
        this.dsyeuthich = dsyeuthich;
    }

    public List<Danhgia> getDsdanhgia() {
        return dsdanhgia;
    }

    public void setDsdanhgia(List<Danhgia> dsdanhgia) {
        this.dsdanhgia = dsdanhgia;
    }

    public List<Yeuthich> getDsyeuthich() {
        return dsyeuthich;
    }

    public void setDsyeuthich(List<Yeuthich> dsyeuthich) {
        this.dsyeuthich = dsyeuthich;
    }

    public Long getMakhachhang() {
        return makhachhang;
    }

    public void setMakhachhang(Long makhachhang) {
        this.makhachhang = makhachhang;
    }

    public String getTenkhachhang() {
        return tenkhachhang;
    }

    public void setTenkhachhang(String tenkhachhang) {
        this.tenkhachhang = tenkhachhang;
    }


    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }



    public List<Hoadon> getDshoadon() {
        return dshoadon;
    }

    public void setDshoadon(List<Hoadon> dshoadon) {
        this.dshoadon = dshoadon;
    }


    public Nguoidung getNguoidung() {
        return nguoidung;
    }

    public void setNguoidung(Nguoidung nguoidung) {
        this.nguoidung = nguoidung;
    }

    @Override
    public String toString() {
        return "Khachhang{" +
                "makhachhang=" + makhachhang +
                ", tenkhachhang='" + tenkhachhang + '\'' +
                ", sdt='" + sdt + '\'' +

                '}';
    }
}
