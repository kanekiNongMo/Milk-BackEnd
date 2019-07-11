// 注册页面表单验证
$("#registerForm").validate({
    rules: {
        username: "required",
        password: {
            // required: true,
            checkInput: true,
            minlength: 4,
            maxlength: 12
        },
        rePassword: {
            equalTo: "#password"
        }
    }
    //,
    // 密码加密
    //submitHandler: function (form) {
    //    md5(form);
    //}
    // ,
    // messages: {
    //     password: {
    //         checkInput: "禁止输入特殊字符及空格"
    //     }
    // }
});

// 登录页面表单验证
$("#loginForm").validate({
    rules: {
        username: {
            required: true,
            checkInput: true
        },
        password: {
            required: true,
            checkInput: true
        }
    }
    //,
    //submitHandler: function (form) {
    //    md5(form);
    //}
});

// 刷新验证码
function changeImg() {
    var imgSrc = $("#imgObj");
    var src = imgSrc.attr("src");
    imgSrc.attr("src", changeUrl(src));
}

// md5加密
// function md5(form) {
//     var salt = 'kaneki';
//     var newPassword = $.md5($("#password").val() + salt);
//     $("#password").val(newPassword);
//     form.submit();
// }

// 为了使每次生成图片不一致，即不让浏览器读缓存，所以需要加上时间戳
function changeUrl(url) {
    var timestamp = (new Date()).valueOf();
    var index = url.indexOf("?", url);
    if (index > 0) {
        url = url.substring(index, url.indexOf(url, "?"));
    }
    if ((url.indexOf("&")) >= 0) {
        url = url + "xtamp" + timestamp;
    } else {
        url = url + "?timestamp=" + timestamp;
    }
    return url;
}

// 添加禁止输入非法字符和空格的自定义表单验证
jQuery.validator.addMethod("checkInput", function (value, element) {
    // var pattern = new RegExp("[.`~!@#$^&*=|{}':;',\\[\\]<>《》/?~！@#￥……&*|{}【】‘；：”“'。，、？' ']");
    // var reg = /^([0-9]+)$/;
    // if(pattern.test(value)) {
    //     return false;
    // } else
    if (value.indexOf(" ") != -1) {
        return false;
    } else {
        return true;
    }
}, "禁止输入空格");