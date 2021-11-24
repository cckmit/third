<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!--引入CSS-->
    <link rel="stylesheet" type="text/css" href="/css/upload/webuploader.css">
    <!--引入JS-->
    <script src="/js/jquery-3.1.1.js"></script>
    <script src="/js/upload/webuploader.js"></script>
    <title>uploadTest</title>
</head>
<body>
<div id="post-container" class="container">
    <div class="page-container">
        <h1 id="demo">Demo</h1>
        <p>您可以尝试文件拖拽，使用QQ截屏工具，然后激活窗口后粘贴，或者点击添加图片按钮，来体验此demo.</p>
        <div id="uploader" class="wu-example">
            <div class="queueList">
                <div id="dndArea" class="placeholder">
                    <div id="filePicker"></div>
                    <p>或将照片拖到这里，单次最多可选300张</p>
                </div>
            </div>
            <div class="statusBar" style="display: none;">
                <div class="progress">
                    <span class="text">0%</span> <span class="percentage"></span>
                </div>
                <div class="info"></div>
                <div class="btns">
                    <div id="filePicker2"></div>
                    <div class="uploadBtn">开始上传</div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>