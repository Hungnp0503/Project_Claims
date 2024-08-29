$(document).ready(function() {
    $('#user').click(function (){
        let userList =$('.user-list')[0]
        if(userList.style.display === 'block'){
            userList.style.display = 'none'
        }else {
            userList.style.display = 'block'
        }
    })


});