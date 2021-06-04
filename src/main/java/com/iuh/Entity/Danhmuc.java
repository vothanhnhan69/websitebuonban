package com.iuh.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table
public class Danhmuc implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long madanhmuc;
    private String tendanhmuc;


    @JsonBackReference
    @OneToMany(mappedBy = "danhmuc")
    private List<Loaidanhmuc> dsloaidanhmuc;



//    public Danhmuc(String tendanhmuc, List<Sanpham> dssanpham, List<Danhmucnho> dsdanhmucnho) {
//        this.tendanhmuc = tendanhmuc;
//        this.dssanpham = dssanpham;
//        this.dsdanhmucnho = dsdanhmucnho;
//    }

    public Danhmuc() {
    }



    public Long getMadanhmuc() {
        return madanhmuc;
    }

    public void setMadanhmuc(Long madanhmuc) {
        this.madanhmuc = madanhmuc;
    }

    public String getTendanhmuc() {
        return tendanhmuc;
    }

    public void setTendanhmuc(String tendanhmuc) {
        this.tendanhmuc = tendanhmuc;
    }

//    public List<Sanpham> getDanhsachsanpham() {
//        return dssanpham;
//    }
//
//    public void setDanhsachsanpham(List<Sanpham> dssanpham) {
//        this.dssanpham = dssanpham;
//    }

    public Danhmuc(String tendanhmuc, List<Loaidanhmuc> dsloaidanhmuc) {
        this.tendanhmuc = tendanhmuc;

        this.dsloaidanhmuc = dsloaidanhmuc;
    }

    public List<Loaidanhmuc> getDsloaidanhmuc() {
        return dsloaidanhmuc;
    }

    public void setDsloaidanhmuc(List<Loaidanhmuc> dsloaidanhmuc) {
        this.dsloaidanhmuc = dsloaidanhmuc;
    }

    @Override
    public String toString() {
        return "Danhmuc{" +
                "madanhmuc=" + madanhmuc +
                ", tendanhmuc='" + tendanhmuc + '\'' +
                ", dsloaidanhmuc=" + dsloaidanhmuc +
                '}';
    }
}
