package com.iuh.DTO;

public class UserDTO {

    private String ma;
    private String ten;
    private String email;
    private String sdt;
    private String username;
    private String password;
    private String maxacthuc;
    private boolean trangthai;

    public UserDTO(String ten, String email, String sdt, String username, String password, boolean trangthai) {
        this.ten = ten;
        this.email = email;
        this.sdt = sdt;

        this.username = username;
        this.password = password;
        this.trangthai = trangthai;
    }

    public UserDTO() {
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

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "ma='" + ma + '\'' +
                ", ten='" + ten + '\'' +
                ", email='" + email + '\'' +
                ", sdt='" + sdt + '\'' +

                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
