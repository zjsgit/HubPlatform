<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <title>MainPage</title>
    <link rel="stylesheet" href="bootstrap/css/bootstrap.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="assets/css/common.css"/>
    <link rel="stylesheet" href="assets/css/style.css">

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
                            <li><a href="index.jsp" class="menu-top-active">HOME</a></li>
                            <li><a href="services.jsp">TOOL</a></li>
                            <li><a href="about.jsp">About</a></li>
                            <li><a href="contact.jsp">Contact</a></li>
                        </ul>
                    </div>
                </div>

            </div>
        </div>
    </div>

    <div class="content-wrapper">
        <div class="container" style="width:940px;margin-top: 20px;">
            <div class="panel panel-default">
                <div class="panel-heading">
                    Creating new job
                </div>
                <div class="panel-body">
                    <form role="form" action="fileload" enctype="multipart/form-data" method="post" >
                        <div class="row">
                            <div class="col-md-6 col-sm-6">
                                <label>Select Centrality</label>
                                <select class="form-control" name="centralitymethod">
                                    <option>DC</option>
                                    <option>NC</option>
                                    <option>MNC</option>
                                </select>
                            </div>
                        </div><!-- .row -->
                        <div class="row">
                            <div class="col-md-6 col-sm-6">
                                <label>Submit Datasets</label>
                                <div class="panel panel-default">
                                    <!--<div class="panel-heading">Datasets</div>-->
                                    <div class="panel-body">
                                        <ul class="nav nav-tabs">
                                            <li class="active"><a href="#net" data-toggle="tab">Network</a>
                                            </li>
                                            <li class=""><a href="#time" data-toggle="tab">Time</a>
                                            </li>
                                            <li class=""><a href="#space" data-toggle="tab">Space</a>
                                            </li>
                                        </ul>

                                        <div class="tab-content">
                                            <div class="tab-pane fade active in" id="net">
                                                <div class="dataset-note">
                                                    <p>protein-protein network.</p>
                                                </div>
                                                <div class="form-group">
                                                    <label>Submit your network </label>
                                                    <input type="file" name="upload" accept="text/plain" />
                                                </div>
                                            </div>
                                            <div class="tab-pane fade" id="time">
                                                <div class="dataset-note">
                                                    <p>gene expression data.</p>
                                                </div>
                                                <div class="form-group">
                                                    <label>Submit gene expression data </label>
                                                    <input type="file" name="upload" accept="text/plain" />
                                                </div>
                                            </div>
                                            <div class="tab-pane fade" id="space">
                                                <div class="dataset-note">
                                                    <p>subcellular localization information.</p>
                                                </div>
                                                <div class="form-group">
                                                    <label>Submit subcellular localization information </label>
                                                    <input type="file" name="upload" accept="text/plain"  />
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div><!-- .row -->
                        <div class="row">
                            <div class="col-md-6 col-sm-6">
                                <label>Validation Code</label>
                                <div class="input-group">
                                    <div class="input-group-addon nocode" id="code-div">TBD</div>
                                    <input type="text" class="form-control" id="code-input">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6 col-sm-6">
                                <button type="submit" class="btn btn-info" onclick="return check(this.form);">Submit Data </button>
                                <button type="reset" class="btn btn-success">Reset Fields</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>

        </div><!-- container -->
    </div><!-- .content-wrapper -->

    <!-- footer -->
    <footer>
        Copyright &copy; 2018 CSU-BIOINFORMATICS. All rights reserved.
    </footer>

</div><!--.wrappper -->

<script src="assets/js/jquery-1.12.0.min.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript">
	//对验证码进行检查
	var code = 0;
	$(function () {
	    createCode();
	    $("#code-input").blur(function () {
	        if(!validateCode(code)){
	        	alert("validation code error");
	        	checkCode=false;
	        }
	    });
	});
	function createCode() {
	    $("#code-input").val("");
	    var num1 = Math.floor(Math.random()*10+10);
	    var num2 = Math.floor(Math.random()*10+10);
	    code = num1 + num2;
	    $("#code-div").html(num1+ " + " + num2 + " = ? ");
	}
	function validateCode() {
	    var inputCode = $("#code-input").val();
	    if (inputCode != code){
	        $("#code-input").val("");
	        return false;
	    }else{
	    	return true;
	    }
	}
	
    //对表格中需要提交的文件进行检查
    function check(form) {
    	var checkNet=true,checkTime=true,checkSpace=true,checkCode=true;
        if($("#net").find("input").val()=='') {
              alert("please select net data");
              checkNet=false;
              return false;
         }
         if($("#time").find("input").val()==''){
              alert("please select time data");
              checkTime=false;
              return false;
          }
         if($("#space").find("input").val()==''){
             alert("please select space data");
             checkSpace=false;
             return false;
         }if($("#code-input").val()==''){
        	 alert("validation code empty");
             checkSpace=false;
             return false;
         }
         
         
         if(checkNet&&checkTime&&checkSpace&&checkCode){
        	 return true;
         }else{
        	 return false;
         }
         
      }
    
</script>
</body>
</html>