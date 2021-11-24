<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>webuploader</title>
</head>
<!--引入CSS-->
<link rel="stylesheet" type="text/css" href="/css/upload/webuploader.css">
<style>
    #upload-container, #upload-list{width: 500px; margin: 0 auto; }
    #upload-container{cursor: pointer; border-radius: 15px; background: #EEEFFF; height: 200px;}
    #upload-list{height: 800px; border: 1px solid #EEE; border-radius: 5px; margin-top: 10px; padding: 10px 20px;}
    #upload-container>span{
        widows: 2;
        text-align: center;
        color: gray;
        display: block;
        padding-top: 15%;
    }
    .upload-item{margin-top: 5px; padding-bottom: 5px; border-bottom: 1px dashed gray;}
    .percentage{height: 5px; background: green;}
    .btn-delete, .btn-retry{cursor: pointer; color: gray;}
    .btn-delete:hover{color: orange;}
    .btn-retry:hover{color: green;}
</style>
<!--引入JS-->
<body>
<div id="upload-container">
    <span>点击或将文件拖拽至此上传</span>
</div>
<div id="upload-list">
    <!-- <div class="upload-item">
        <span>文件名：123</span>
        <span data-file_id="" class="btn-delete">删除</span>
        <span data-file_id="" class="btn-retry">重试</span>
        <div class="percentage"></div>
    </div> -->
</div>
<button id="picker" style="display: none;">点击上传文件</button>
</body>
<script src="/js/jquery-3.1.1.js"></script>
<script src="/js/upload/webuploader.js"></script>
<script>
    $('#upload-container').click(function(event) {
        $("#picker").find('input').click();
    });
            var uploader = WebUploader.create({
        auto: true,// 选完文件后，是否自动上传。
        swf: 'https://cdn.bootcss.com/webuploader/0.1.1/Uploader.swf',// swf文件路径
        server: 'http://localhost:8080/upload/upload.action',// 文件接收服务端。
        dnd: '#upload-container',
        pick: '#picker',// 内部根据当前运行是创建，可能是input元素，也可能是flash. 这里是div的id
        multiple: true, // 选择多个
        chunked: true,// 开起分片上传。
        threads: 5, // 上传并发数。允许同时最大上传进程数。
        method: 'POST', // 文件上传方式，POST或者GET。
        fileSizeLimit: 1024*1024*100*100, //验证文件总大小是否超出限制, 超出则不允许加入队列。
        fileSingleSizeLimit: 1024*1024*100, //验证单个文件大小是否超出限制, 超出则不允许加入队列。
        fileVal:'files', // [默认值：'file'] 设置文件上传域的name。
    });

    uploader.on('fileQueued', function(file) {
        // 选中文件时要做的事情，比如在页面中显示选中的文件并添加到文件列表，获取文件的大小，文件类型等
        console.log(file.ext) // 获取文件的后缀
        console.log(file.size) // 获取文件的大小
        console.log(file.name);
        var html = '<div class="upload-item"><img src="file"/><span>文件名：'+file.name+'</span><span data-file_id="'+file.id+'" class="btn-delete">删除</span><span data-file_id="'+file.id+'" class="btn-retry">重试</span><div class="percentage '+file.id+'" style="width: 0%;"></div></div>';
        $('#upload-list').append(html);
    });

    uploader.on('uploadProgress', function(file, percentage) {
        console.log(percentage * 100 + '%');
        var width = $('.upload-item').width();
        $('.'+file.id).width(width*percentage);
    });

    uploader.on('uploadSuccess', function(file, response) {
        console.log(file.id+"传输成功");
    });

    uploader.on('uploadError', function(file) {
        console.log(file);
        console.log(file.id+'upload error')
    });

    $('#upload-list').on('click', '.upload-item .btn-delete', function() {
        // 从文件队列中删除某个文件id
        file_id = $(this).data('file_id');
        // uploader.removeFile(file_id); // 标记文件状态为已取消
        uploader.removeFile(file_id, true); // 从queue中删除
        console.log(uploader.getFiles());
    });

    $('#upload-list').on('click', '.btn-retry', function() {
        uploader.retry($(this).data('file_id'));
    });

    uploader.on('uploadComplete', function(file) {
        console.log(uploader.getFiles());
    });
</script>
</html>
