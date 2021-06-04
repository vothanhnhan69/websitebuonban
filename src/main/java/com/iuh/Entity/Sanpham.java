package com.iuh.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table
public class Sanpham implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long masanpham;
    private String tensanpham;
    private String mota;
    private int soluong;
    private String hinhanh;
    private String kichthuoc;
    private String mausac;
    private double dongia;
    private int khuyenmai;
    private String trangthai;

    @ManyToOne
    @JoinColumn(name = "loaidanhmuc_id", nullable = false)
    private Loaidanhmuc loaidanhmuc;

    @ManyToOne
    @JoinColumn(name = "thuonghieu_id", nullable=false)
    private Thuonghieu thuonghieu;

    @ManyToOne
    @JoinColumn(name = "nguoiban_id", nullable=false)
    private Nguoiban nguoiban;

    @JsonIgnore
    @OneToMany(mappedBy = "sanpham")
    private List<Chitiethoadon> dschitiethoadon;

    @JsonIgnore
    @OneToMany(mappedBy = "sanpham")
    private List<Danhgia> dsdanhgia;

    @JsonIgnore
    @OneToMany(mappedBy = "sanpham")
    private List<Yeuthich> dsyeuthich;


    public Sanpham() {
    }



    public Loaidanhmuc getLoaidanhmuc() {
        return loaidanhmuc;
    }

    public void setLoaidanhmuc(Loaidanhmuc loaidanhmuc) {
        this.loaidanhmuc = loaidanhmuc;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }

    public Nguoiban getNguoiban() {
        return nguoiban;
    }

    public void setNguoiban(Nguoiban nguoiban) {
        this.nguoiban = nguoiban;
    }

    public String getKichthuoc() {
        return kichthuoc;
    }

    public void setKichthuoc(String kichthuoc) {
        this.kichthuoc = kichthuoc;
    }

    public String getMausac() {
        return mausac;
    }

    public void setMausac(String mausac) {
        this.mausac = mausac;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
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

    public Nguoiban getNhacungcap() {
        return nguoiban;
    }

    public void setNhacungcap(Nguoiban nguoiban) {
        this.nguoiban = nguoiban;
    }

    public Long getMasanpham() {
        return masanpham;
    }

    public void setMasanpham(Long masanpham) {
        this.masanpham = masanpham;
    }


    public String getTensanpham() {
        return tensanpham;
    }

    public void setTensanpham(String tensanpham) {
        this.tensanpham = tensanpham;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
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



    public Thuonghieu getThuonghieu() {
        return thuonghieu;
    }

    public void setThuonghieu(Thuonghieu thuonghieu) {
        this.thuonghieu = thuonghieu;
    }

    public List<Chitiethoadon> getDschitiethoadon() {
        return dschitiethoadon;
    }

    public void setDschitiethoadon(List<Chitiethoadon> dschitiethoadon) {
        this.dschitiethoadon = dschitiethoadon;
    }

    public int getKhuyenmai() {
        return khuyenmai;
    }

    public void setKhuyenmai(int khuyenmai) {
        this.khuyenmai = khuyenmai;
    }

    public Sanpham(String tensanpham, String mota, int soluong, String hinhanh, String kichthuoc, String mausac, double dongia, int khuyenmai, String trangthai, Loaidanhmuc loaidanhmuc, Thuonghieu thuonghieu, Nguoiban nguoiban, List<Chitiethoadon> dschitiethoadon, List<Danhgia> dsdanhgia, List<Yeuthich> dsyeuthich) {
        this.tensanpham = tensanpham;
        this.mota = mota;
        this.soluong = soluong;
        this.hinhanh = hinhanh;
        this.kichthuoc = kichthuoc;
        this.mausac = mausac;
        this.dongia = dongia;
        this.khuyenmai = khuyenmai;
        this.trangthai = trangthai;
        this.loaidanhmuc = loaidanhmuc;
        this.thuonghieu = thuonghieu;
        this.nguoiban = nguoiban;
        this.dschitiethoadon = dschitiethoadon;
        this.dsdanhgia = dsdanhgia;
        this.dsyeuthich = dsyeuthich;
    }

    @Override
    public String toString() {
        return "Sanpham{" +
                "tensanpham='" + tensanpham + '\'' +
                '}';
    }

}
