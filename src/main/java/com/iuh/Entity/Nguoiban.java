package com.iuh.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table
public class Nguoiban implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long manguoiban;
    private String tennguoiban;
    private String sdt;



    //    @OneToMany(mappedBy = "nguoiban", cascade = CascadeType.ALL) // Quan hệ 1-n với đối tượng ở dưới (Person) (1 địa điểm có nhiều người ở)
    //    // MapopedBy trỏ tới tên biến Address ở trong Person.
    //    @EqualsAndHashCode.Exclude // không sử dụng trường này trong equals và hashcode
    //    @ToString.Exclude
    //    private List<Sanpham> dssanpham;


    @ManyToOne
    @JoinColumn(name = "nguoidung_id", nullable=false)
    private Nguoidung nguoidung;



    public Nguoiban() {
    }

//    public Nguoiban(String tennguoiban, String email, String sdt, String diachi, List<Sanpham> dssanpham, Nguoidung nguoidung) {
//        this.tennguoiban = tennguoiban;
//        this.email = email;
//        this.sdt = sdt;
//        this.diachi = diachi;
//        this.dssanpham = dssanpham;
//        this.nguoidung = nguoidung;
//    }


    public Long getManguoiban() {
        return manguoiban;
    }

    public void setManguoiban(Long manguoiban) {
        this.manguoiban = manguoiban;
    }

    public String getTennguoiban() {
        return tennguoiban;
    }

    public void setTennguoiban(String tennguoiban) {
        this.tennguoiban = tennguoiban;
    }

//    public List<Sanpham> getDssanpham() {
//        return dssanpham;
//    }
//
//    public void setDssanpham(List<Sanpham> dssanpham) {
//        this.dssanpham = dssanpham;
//    }

    public Nguoidung getNguoidung() {
        return nguoidung;
    }

    public void setNguoidung(Nguoidung nguoidung) {
        this.nguoidung = nguoidung;
    }



    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }



}
