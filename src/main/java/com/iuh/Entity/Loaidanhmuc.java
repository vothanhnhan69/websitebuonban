package com.iuh.Entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table
public class Loaidanhmuc implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long maloaidanhmuc;
    private String tenloaidanhmuc;

    @ManyToOne
    @JoinColumn(name = "danhmuc_id", nullable=false)
    private Danhmuc danhmuc;

    @JsonIgnore
    @OneToMany(mappedBy = "loaidanhmuc")
    private List<Sanpham> dssanpham;

    public Loaidanhmuc() {
    }

    public List<Sanpham> getDssanpham() {
        return dssanpham;
    }

    public void setDssanpham(List<Sanpham> dssanpham) {
        this.dssanpham = dssanpham;
    }

    public Loaidanhmuc(String tenloaidanhmuc, Danhmuc danhmuc) {
        this.tenloaidanhmuc = tenloaidanhmuc;
        this.danhmuc = danhmuc;
    }

    public Long getMaloaidanhmuc() {
        return maloaidanhmuc;
    }

    public void setMaloaidanhmuc(Long maloaidanhmuc) {
        this.maloaidanhmuc = maloaidanhmuc;
    }

    public String getTenloaidanhmuc() {
        return tenloaidanhmuc;
    }

    public void setTenloaidanhmuc(String tenloaidanhmuc) {
        this.tenloaidanhmuc = tenloaidanhmuc;
    }

    public Danhmuc getDanhmuc() {
        return danhmuc;
    }

    public void setDanhmuc(Danhmuc danhmuc) {
        this.danhmuc = danhmuc;
    }

    @Override
    public String toString() {
        return "Loaidanhmuc{" +
                "tenloaidanhmuc='" + tenloaidanhmuc + '\'' +
                ", dssanpham=" + dssanpham +
                '}';
    }
}
