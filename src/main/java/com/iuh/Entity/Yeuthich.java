package com.iuh.Entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
public class Yeuthich implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mayeuthich;

    @ManyToOne
    @JoinColumn(name = "sampham_id", nullable=false)
    private Sanpham sanpham;

    @ManyToOne
    @JoinColumn(name = "khachhang_id", nullable=false)
    private Khachhang khachhang;


    public Yeuthich(Sanpham sanpham, Khachhang khachhang) {
        this.sanpham = sanpham;
        this.khachhang = khachhang;
    }

    public Yeuthich() {
    }

    public Long getMayeuthich() {
        return mayeuthich;
    }

    public void setMayeuthich(Long mayeuthich) {
        this.mayeuthich = mayeuthich;
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
}
