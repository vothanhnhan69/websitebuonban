package com.iuh.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table
public class Thuonghieu implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mathuonghieu;
    private String tenthuonghieu;

    @JsonIgnore
    @OneToMany(mappedBy = "thuonghieu")
    private List<Sanpham> dssanpham;

    public Thuonghieu() {
    }

    public Thuonghieu(Long mathuonghieu) {
        this.mathuonghieu = mathuonghieu;
    }

    public Thuonghieu(String tenthuonghieu, List<Sanpham> dssanpham) {
        this.tenthuonghieu = tenthuonghieu;
        this.dssanpham = dssanpham;
    }

    public Long getMathuonghieu() {
        return mathuonghieu;
    }

    public void setMathuonghieu(Long mathuonghieu) {
        this.mathuonghieu = mathuonghieu;
    }

    public String getTenthuonghieu() {
        return tenthuonghieu;
    }

    public void setTenthuonghieu(String tenthuonghieu) {
        this.tenthuonghieu = tenthuonghieu;
    }

    public List<Sanpham> getDssanpham() {
        return dssanpham;
    }

    public void setDssanpham(List<Sanpham> dssanpham) {
        this.dssanpham = dssanpham;
    }


}

