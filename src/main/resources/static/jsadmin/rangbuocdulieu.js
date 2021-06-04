
function kiemtraloaisach()
{
    var ten = document.getElementById('tenloaisach').value;
    var check = true;

    if (ten == null || ten == "") {
        document.getElementById("loitenloaisach").innerHTML = "* Tên loại sách không được để trống";
        check = false;
    }else {
        document.getElementById("loitenloaisach").innerHTML = "";
    }
    return check;
}

function kiemtratensach()
{
    var ten = document.getElementById('tens').value;
    var check = true;

    if (ten == null || ten == "") {
        document.getElementById("loitensach").innerHTML = "* Tên sách không được để trống";
        check = false;
    }else {
        document.getElementById("loitensach").innerHTML = "";
    }
 
    return check;
}

function kiemtramotasach(){
    var mota = document.getElementById('mota').value;
    var check = true;
    if (mota == null || mota == "") {
        document.getElementById("loimota").innerHTML = "* Mô tả sách không được để trống";
        check = false;
    }else {
        document.getElementById("loimota").innerHTML = "";
    }
    return check;
}

function kiemtrasoluong(){
    var soluong = document.getElementById('soluong').value;
    var pattsl = /^[0-9]+$/;
    var check = true;
    if (soluong == null || soluong == "") {
        document.getElementById("loisoluong").innerHTML = "* Số lượng không được để trống";
        check = false;
    }else if(pattsl.test(soluong)== false){
         document.getElementById("loisoluong").innerHTML = "* Số lượng phải >=0";
        check = false;
    }
    else {
        document.getElementById("loisoluong").innerHTML = "";
    }
    return check;
}

function kiemtradongia(){
    var dongia = document.getElementById('dongia').value;
    var pattdg = /^[0-9]+$/;
    var check = true;
    if (dongia == null || dongia == "") {
        document.getElementById("loidongia").innerHTML = "* Đơn giá sách không được để trống";
        check = false;
    }else if(pattdg.test(dongia)== false){
        document.getElementById("loidongia").innerHTML = "* Đơn giá phải >=0";
       check = false;
   }
    else {
        document.getElementById("loidongia").innerHTML = "";
    }

    return check;
}

function themloaisach(){
    if(kiemtraloaisach() ==true){
        alert("thêm thành công");
        return true;
    }else {
        alert("Không thể thêm");
        return false;
    }
}




function themsach(){
    if(kiemtratensach() ==true && kiemtramotasach()== true && kiemtrasoluong()== true && kiemtradongia()== true){
        alert("thêm thành công");
        return true;
    }else {
        alert("Không thể thêm");
        return false;
    }
}





function kiemtraemail(){
 var email = document.getElementById('emailnguoiban').value;
    var check = true;
    var pattemail = /[a-z0-9]+(@)[a-z]+(.)[a-z]+/;

    if (pattemail.test(email) == false) {
        document.getElementById("emailerror").innerHTML = "* Email không đúng định đạng";
        check = false;
    }  else {
        document.getElementById("emailerror").innerHTML = "";
    }
    return check;
}


function kiemtrasdt(){
 var numberphone = document.getElementById('sdtnguoiban').value;
    var pattnumberphone = /(0)\d{9}$/;
    var check = true;

     if (pattnumberphone.test(numberphone) == false) {
        document.getElementById("sdterror").innerHTML = "* Số điện thoại phải 10 số và có số đầu là 0";
        check = false;
    } else {
        document.getElementById("sdterror").innerHTML = "";
    }
    return check;
}

function kiemtrapassword(){
 var password = document.getElementById('passwordnguoiban').value;
    var repassword = document.getElementById('repasswordnguoiban').value;
    var check = true;
    if(repassword !== password){
        document.getElementById("repassworderror").innerHTML = "* Nhập lại password không chính xác";
        check= false;
    }else{
        document.getElementById("repassworderror").innerHTML = "";
    }
    return check;
}

function kiemtrataikhoan(){
    var username = document.getElementById('usernamenguoiban').value;
        var check = true;
        var pattusername = /^[a-z\d]+$/;
        if (pattusername.test(username) == false) {
            document.getElementById("taikhoanerror").innerHTML = "Username không có ký tự đặc biệt";
            check = false;
        }  else {
            document.getElementById("taikhoanerror").innerHTML = "";
        }
        return check;
}

function kiemtranguoiban() {
    if( kiemtraemail()==true && kiemtrasdt()==true && kiemtrataikhoan()==true && kiemtrapassword()==true){
        return true;
    }    else {
        return false;
    }
}





function kiemtraemail1(){
 var email = document.getElementById('emailnguoimua').value;
    var check = true;
    var pattemail = /[a-z0-9]+(@)[a-z]+(.)[a-z]+/;

    if (pattemail.test(email) == false) {
        document.getElementById("emailerror1").innerHTML = "* Email không đúng định đạng";
        check = false;
    }  else {
        document.getElementById("emailerror1").innerHTML = "";
    }
    return check;
}


function kiemtrasdt1(){
 var numberphone = document.getElementById('sdtnguoimua').value;
    var pattnumberphone = /(0)\d{9}$/;
    var check = true;

     if (pattnumberphone.test(numberphone) == false) {
        document.getElementById("sdterror1").innerHTML = "* Số điện thoại phải 10 số và có số đầu là 0";
        check = false;
    } else {
        document.getElementById("sdterror1").innerHTML = "";
    }
    return check;
}


function kiemtrapassword1(){
 var password = document.getElementById('passwordnguoimua').value;
    var repassword = document.getElementById('repasswordnguoimua').value;
    var check = true;
    if(repassword !== password){
        document.getElementById("repassworderror1").innerHTML = "* Nhập lại password không chính xác";
        check= false;
    }else{
        document.getElementById("repassworderror1").innerHTML = "";
    }
    return check;
}

function kiemtrataikhoan1(){
    var username = document.getElementById('usernamenguoimua').value;
        var check = true;
        var pattusername = /^[a-z\d]+$/;
        if (pattusername.test(username) == false) {
            document.getElementById("taikhoanerror1").innerHTML = "Username không có ký tự đặc biệt";
            check = false;
        }  else {
            document.getElementById("taikhoanerror1").innerHTML = "";
        }
        return check;
}

function kiemtranguoimua() {
    if( kiemtraemail()==true && kiemtrasdt()==true && kiemtrataikhoan()==true && kiemtrapassword()==true){
        return true;
    }    else {
        return false;
    }
}


function kiemtrapasswordnew(){
    var password = document.getElementById('passwordnew').value;
    var repassword = document.getElementById('repasswordnew').value;
    var check = true;
    if(repassword !== password){
        document.getElementById("errorpasswordnew").innerHTML = "* Nhập lại password không chính xác";
        check= false;
    }else{
        document.getElementById("errorpasswordnew").innerHTML = "";
    }
    return check;
}

function kiemtradoimk(){
    if( kiemtrapasswordnew()==true){
        return true;
    }    else {
        return false;
    }
}



function kiemtrasdtupdate(){
 var numberphone = document.getElementById('sdtupdate').value;
    var pattnumberphone = /(0)\d{9}$/;
    var check = true;

     if (pattnumberphone.test(numberphone) == false) {
        document.getElementById("sdterrorupdate").innerHTML = "* Số điện thoại phải 10 số và có số đầu là 0";
        check = false;
    } else {
        document.getElementById("sdterrorupdate").innerHTML = "";
    }
    return check;
}

function kiemtrasdtupdatenguoiban(){
 var numberphone = document.getElementById('sdtupdatenguoiban').value;
    var pattnumberphone = /(0)\d{9}$/;
    var check = true;

     if (pattnumberphone.test(numberphone) == false) {
        document.getElementById("sdterrorupdatenguoiban").innerHTML = "* Số điện thoại phải 10 số và có số đầu là 0";
        check = false;
    } else {
        document.getElementById("sdterrorupdatenguoiban").innerHTML = "";
    }
    return check;
}
function kiemtraupdate(){
    if( kiemtrasdtupdate()==true){
        return true;
    }    else {
        return false;
    }
}

function kiemtraupdatenguoiban(){
    if( kiemtrasdtupdatenguoiban()==true){
        return true;
    }    else {
        return false;
    }
}
