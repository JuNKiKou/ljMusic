<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html>
<body>
    <form id="form" method="post" action="download/downloadMusic">
        <input type="text" name="musicName" id="musicName">
        <input type="submit" value="下载">
    </form>


    <form action="upload/uploadMusic" enctype="multipart/form-data" method="post" accept-charset="UTF-8">
        选择文件:<input type="file" name="file">
        <br/>
        重命名:<input type="text" name="fileName">
        <input type="submit" value="提交">
    </form>


    <br/><br/><br/>
    <form action="upload/uploadMusics" enctype="multipart/form-data" method="post" accept-charset="UTF-8">
        选择文件:<input type="file" name="files">
        <br/>
        重命名:<input type="text" name="fileNames">
        <br/>
        选择文件:<input type="file" name="files">
        <br/>
        重命名:<input type="text" name="fileNames">
        <br/>
        选择文件:<input type="file" name="files">
        <br/>
        重命名:<input type="text" name="fileNames">
        <br/>
        <input type="submit" value="提交">
    </form>
</body>
</html>
