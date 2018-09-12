<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <title>Contact</title>
    <link rel="stylesheet" href="bootstrap/css/bootstrap.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="assets/css/common.css"/>
    <link rel="stylesheet" href="assets/css/style.css">
    <script src="assets/js/jquery-3.3.1.js" type="text/javascript"></script>
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
                            <li><a href="about.jsp">About</a></li>
                            <li><a href="contact.jsp" class="menu-top-active">Contact</a></li>
                        </ul>
                    </div>
                </div>

            </div>
        </div>
    </div>

    <div class="content-wrapper">
        <div class="container" style="width:940px;height: 400px;margin-top: 20px;">
            <div class="row contact_top">
                <div class="col-md-4 contact_details">
                    <h5>Mailing address:</h5>
                    <div class="contact_address"> No.932 South Lushan Road, Changsha, Hunan, 410083, P.R.China</div>
                </div>
                <div class="col-md-4 contact_details">
                    <h5>Call us:</h5>
                    <div class="contact_address"> +86 -731-8888 8888<br>
                    </div>
                </div>
                <div class="col-md-4 contact_details">
                    <h5>Email us:</h5>
                    <div class="contact_mail"> csbioinformatics@csu.edu.cn</div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12 col-sm-6">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            CONTACT US
                        </div>
                        <div class="panel-body">
                            <form role="form">
                                <div class="row">
                                    <div class="col-md-4 col-sm-4">
                                        <label>Enter Name <span style="color: red;">*</span></label>
                                        <input class="form-control" type="text" id="name" />
                                        <!--<p class="help-block">Help text here.</p>-->
                                    </div>
                                    <div class="col-md-4 col-sm-4">
                                        <label>Enter Email <span style="color: red;">*</span></label>
                                        <input class="form-control" type="text" id="email" />
                                        <!--<p class="help-block">Help text here.</p>-->
                                    </div>
                                    <div class="col-md-4 col-sm-4">
                                        <label>Subject</label>
                                        <input class="form-control" type="text" id="subject" />
                                        <!--<p class="help-block">Help text here.</p>-->
                                    </div>
                                </div>
                                <div class="form-group" style="margin-top: 10px;">
                                    <label>Text area</label>
                                    <textarea class="form-control" rows="3" id="content" ></textarea>
                                </div>

                                <button id="btn_send" class="btn btn-info">Send Message </button>

                            </form>
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

</div><!--.wrappper -->



<script type="text/javascript">
	$("#btn_send").click(function(){
		var name=$("#name").val();
		var email=$("#email").val();
		var subject=$("#subject").val();
		var content=$("#content").val();
		if(name==''){
			alert("please input your name");
			return false;
		}else if(email==''){
			alert("please input your email");
			return false;
		}else if(subject==''){
			alert("please input your subject");
			return false;
		}else if(content==''){
			alert("please input your content");
			return false;
		}else{
			$.ajax({ 
	            type : 'post',
	            url : 'sendMail',
	            data : {
	                username:name,
	                useremail:email,
	                usersubject:subject,
	                usercontent:content
	            },
	            async: false,
	            dataType:'json',
	    
	            success : function(data) {
	            	alert(data['ok']);
	            },
	
	            error : function() {
	                alert("emial sending occurs  error");
	            }
	        });
		}	
		return false;
	});
</script>

</body>
</html>