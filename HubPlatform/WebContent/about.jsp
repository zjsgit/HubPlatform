<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <title>About</title>
    <link rel="stylesheet" href="bootstrap/css/bootstrap.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="assets/css/common.css"/>
    <link rel="stylesheet" href="assets/css/style.css">
	<script src="assets/js/jquery-1.12.0.min.js"></script>
	<script src="bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
<div class="wrapper">
    <!-- header -->
    <div class="menu-section">
        <div class="menu-top">
            <div class="row">
                <div class="col-md-3">
                    <h1>TSHUB</h1>
                </div>

                <div class="col-md-9">
                    <div class="navbar-collapse collapse ">
                        <ul id="menu-top" class="nav navbar-nav navbar-right">
                            <li><a href="index.jsp">HOME</a></li>
                            <li><a href="services.jsp">TOOL</a></li>
                            <li><a href="about.jsp" class="menu-top-active">About</a></li>
                            <li><a href="contact.jsp">Contact</a></li>

                        </ul>
                    </div>
                </div>

            </div>
        </div>
    </div>

    <div class="content-wrapper">
        <div class="container" style="width:940px;height: 400px;margin-top: 20px;">
            <div class="row">
                <div class="col-md-12 col-sm-12">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            ABOUT TSHUB
                        </div>
                        <div class="panel-body">
                            <p>TSHUB.</p>
                        </div>
                        <div class="panel-footer">
                            Homepage: <a href="index.jsp">bioinformatics.csu.edu.cn/tshub/</a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12 col-sm-12">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            ABOUT CSU-BIOINFORMATICS
                        </div>
                        <div class="panel-body">
                            <p>CSU-BIOINFORMATICS.</p>
                        </div>
                        <div class="panel-footer">
                            Homepage: <a href="index.jsp">bioinformatics.csu.edu.cn</a>
                        </div>
                    </div>
                </div>
            </div>
        </div><!-- container -->
    </div><!-- .content-wrapper -->

    <!-- footer -->
    <footer>
        Copyright &copy; 2018 CSU-BIOINFORMATICS. All rights reserved.
    </footer>

</div><!-- wrappper  end -->

</body>
</html>