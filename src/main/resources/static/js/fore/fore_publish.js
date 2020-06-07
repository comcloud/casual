$(function () {
    $(".file").change(function () {
        var fileDome = $(this)[0];
        var url = "/user/uploadTaskImage";
        uploadMultiplePictures(fileDome, url);

    });

    /**
     * 多图片上传
     * */
    var formData = new FormData();
    function uploadMultiplePictures(fileDom, url) {
        var files = fileDom.files;
        var reg = /[0-9a-zA-Z].*.png|jpg/;
        for (var i = 0; i < files.length; i++) {
            if (!reg.test((files[i].name))) {
                alert("仅可以输入图片");
            } else {
                formData.append("file", files[i]);
            }
        }
        console.log(formData);
        $.ajax({
            type: "post",
            dataType: "json",
            data: formData,
            cache: false,
            url: url,
            processData: false,
            contentType: false,
            mimeType: "multipart/form-data",
            success: function (data) {
                //添加图片，返回图片二维数组
                if (data.success) {
                    var count = 1;
                    alert("成功回调");
                    for (var picture in data.pictures) {
                        var img = "<img alt='" + count + ".jpg' class='layui-upload-img' src='data:image/png;base64,'" + picture + "/>";
                        $("#demo2").prepend(img);
                    }

                }
            },
            beforeSend: function (data) {
            },
            error: function (data) {
            }
        })
    }


    //表单提交事件
    $('form').submit(function () {
        var task = {
            parent_sort: $("#parent_sort").val(),
            son_sort: $("#son_sort").val(),
            title: $("#title").val(),
            time: $("#time").val(),
            address: $("#address").val(),
            taskDescription: $("#taskDescription").val(),
            imageData: formData
        };
        $.ajax({
            type: "post",
            dataType: "json",
            data: parseJSON(task),
            cache: false,
            url: "/user/publishSuccess",
            processData: false,
            contentType: false,
            mimeType: "multipart/form-data",
            success: function (data) {
                //添加图片，返回图片二维数组
                if (data.success) {
                    alert("发布成功");
                }
            }
        })
    });


})