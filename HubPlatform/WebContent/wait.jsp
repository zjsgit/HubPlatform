<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Wait</title>
	<link href="assets/css/Icomoon/style.css" rel="stylesheet" type="text/css" />
	<link href="assets/css/main.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="assets/scripts/jquery-3.3.1.js"></script>
</head>
<body>
	<div id="loading">
        <div id="loading-center">
            <div id="loading-center-absolute">
                <div id="object"> <img src="assets/img/circle.gif" /></div>
                <p >Please wait a moment......</p>
            </div>
        </div>
    </div>
	<script type="text/javascript">
		setTimeout("location=location;",1000);//一秒后刷新本页面
	</script>
</body>
</html>