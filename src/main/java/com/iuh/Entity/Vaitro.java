package com.iuh.Entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table
public class Vaitro implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ma;
    private String ten;

    @ManyToMany(mappedBy = "vaitros")
    private Set<Nguoidung> nguoidungs;

    public Vaitro(String ten) {
        this.ten = ten;
    }


    public Vaitro() {
    }

    public Set<Nguoidung> getNguoidungs() {
        return nguoidungs;
    }

    public void setNguoidungs(Set<Nguoidung> nguoidungs) {
        this.nguoidungs = nguoidungs;
    }

    public Long getMa() {
        return ma;
    }

    public void setMa(Long ma) {
        this.ma = ma;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    @Override
    public String toString() {
        return "Vaitro{" +
                "ma=" + ma +
                ", ten='" + ten + '\'' +
                '}';
    }
}
