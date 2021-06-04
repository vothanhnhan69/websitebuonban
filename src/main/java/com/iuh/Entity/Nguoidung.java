package com.iuh.Entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table
public class Nguoidung {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ma;
    private String taikhoan;
    private String email;
    private String matkhau;
    private String maxacthuc;
    private boolean trangthai;

    @JsonIgnore
    @OneToMany(mappedBy = "nguoidung")
    private List<Diachi> dsdiachi;

//    @OneToMany(mappedBy = "nguoidung",cascade=CascadeType.ALL)
//    private List<Khachhang> dskhachhang;

//    @OneToMany(mappedBy = "nguoidung")
//    private List<Nguoiban> dsnhacungcap;


    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name = "Nguoidung_Vaitro", joinColumns = {@JoinColumn(name="nguoidung_ma")}, inverseJoinColumns = {@JoinColumn(name="vaitro_ma")})
    private Set<Vaitro> vaitros;


    public Nguoidung(String taikhoan, String email, String matkhau, String maxacthuc, boolean trangthai, Set<Vaitro> vaitros) {
        this.taikhoan = taikhoan;
        this.email = email;
        this.matkhau = matkhau;
        this.maxacthuc = maxacthuc;
        this.trangthai = trangthai;
        this.vaitros = vaitros;
    }

    public Nguoidung() {
    }

    public List<Diachi> getDsdiachi() {
        return dsdiachi;
    }

    public void setDsdiachi(List<Diachi> dsdiachi) {
        this.dsdiachi = dsdiachi;
    }

    public String getMaxacthuc() {
        return maxacthuc;
    }

    public void setMaxacthuc(String maxacthuc) {
        this.maxacthuc = maxacthuc;
    }

    public boolean isTrangthai() {
        return trangthai;
    }

    public void setTrangthai(boolean trangthai) {
        this.trangthai = trangthai;
    }

    public Set<Vaitro> getVaitros() {
        return vaitros;
    }

    public void setVaitros(Set<Vaitro> vaitros) {
        this.vaitros = vaitros;
    }
//    public Nguoidung(String taikhoan, String matkhau, List<Khachhang> dskhachhang, List<Nguoiban> dsnhacungcap, Set<Vaitro> vaitros) {
//        this.taikhoan = taikhoan;
//        this.matkhau = matkhau;
//        this.dskhachhang = dskhachhang;
//        this.dsnhacungcap = dsnhacungcap;
//        this.vaitros = vaitros;
//    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTaikhoan() {
        return taikhoan;
    }

    public void setTaikhoan(String taikhoan) {
        this.taikhoan = taikhoan;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

//    public List<Khachhang> getDskhachhang() {
//        return dskhachhang;
//    }
//
//    public void setDskhachhang(List<Khachhang> dskhachhang) {
//        this.dskhachhang = dskhachhang;
//    }
//
//    public List<Nguoiban> getDsnhacungcap() {
//        return dsnhacungcap;
//    }
//
//    public void setDsnhacungcap(List<Nguoiban> dsnhacungcap) {
//        this.dsnhacungcap = dsnhacungcap;
//    }


    public Long getMa() {
        return ma;
    }

    public void setMa(Long ma) {
        this.ma = ma;
    }


    @Override
    public String toString() {
        return "Nguoidung{" +
                "ma=" + ma +
                ", taikhoan='" + taikhoan + '\'' +
                ", email='" + email + '\'' +
                ", matkhau='" + matkhau + '\'' +
                ", maxacthuc='" + maxacthuc + '\'' +
                ", trangthai=" + trangthai +
                ", vaitros=" + vaitros +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Nguoidung nguoidung = (Nguoidung) o;
        return ma.equals(nguoidung.ma);
    }


    @Override
    public int hashCode() {
        return Objects.hash(ma);
    }
}
