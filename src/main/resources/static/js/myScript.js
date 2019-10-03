function check()
 {

     var pass1 = document.getElementById('mobile');


     var message = document.getElementById('message');

    var goodColor = "#0C6";
     var badColor = "#FF9B37";

     if(mobile.value.length!=10){

         mobile.style.borderColor= badColor;
         message.style.color = badColor;
         message.innerHTML = "Entrer 10 nombres"
     }else{
         mobile.style.borderColor= goodColor;
         document.getElementById('message').style.display= 'none';
     }
 }