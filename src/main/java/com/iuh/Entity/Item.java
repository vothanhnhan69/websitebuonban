package com.iuh.Entity;

public class Item {
    private Sanpham sanpham;
    private int soluong;

    public Item(Sanpham sanpham, int soluong) {
        this.sanpham = sanpham;
        this.soluong = soluong;
    }

    public Item() {
    }

    public Sanpham getSanpham() {
        return sanpham;
    }

    public void setSanpham(Sanpham sanpham) {
        this.sanpham = sanpham;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }
}
