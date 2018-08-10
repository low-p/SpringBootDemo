var __links = document.querySelectorAll('a');function __linkClick(e) { parent.window.postMessage(this.href, '*');} ;for (var i = 0, l = __links.length; i < l; i++) {if ( __links[i].getAttribute('data-t') == '_blank' ) { __links[i].addEventListener('click', __linkClick, false);}}
$(document).ready(function(c) {
    $('.alert-close').on('click', function(c){
        $('.message').fadeOut('slow', function(c){
            $('.message').remove();
        });
    });
    $("#loginForm").submit(function (e) {
        console.log("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
    });
});
function loginFun() {
    console.log("++++++++++++++++++++++++++++++++++++");
    var username = $("#username").val();
    var password = $("#password").val();
    if (username == '' || username == "用户名") {
        $("#username").tooltip('show');
        setTimeout(function () {
            $("#username").tooltip('hide');
        }, 1500);
        return false;
    }
    if (password == '' || password == "Password") {
        $("#password").tooltip('show');
        setTimeout(function () {
            $("#password").tooltip('hide');
        }, 1500);
        return false;
    }
    var form = $("#loginForm");
    form.attr("action", "/demo/login");
    form.attr("method", "post");
    form.submit();
}