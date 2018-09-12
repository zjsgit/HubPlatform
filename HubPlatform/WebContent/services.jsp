<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <title>Services</title>
    <link rel="stylesheet" href="bootstrap/css/bootstrap.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="assets/css/common.css"/>
    <link rel="stylesheet" href="assets/css/style.css">
    
    <script src="assets/js/jquery-3.3.1.js" type="text/javascript"></script>
    <script src="assets/js/ajaxfileupload.js" type="text/javascript"></script>
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
                            <li><a href="contact.jsp">Contact</a></li>

                        </ul>
                    </div>
                </div>

            </div>
        </div>
    </div>

    <div class="cyto-main">
        <div class="row" style="margin: 10px 0px;">
            <div class="col-md-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        Data Preprocessing
                    </div>
                    <div class="panel-body">
                    	<form role="form" enctype="multipart/form-data" >
	                        <div class="form-group">
	                            <label>Submit network </label>
	                            <input type="file" name="uploadFile" id="net" accept="text/plain" />
	                        </div>
	                        <div class="alert alert-info">
	                            Note: protein-protein interaction network.
	                        </div>
	                        <hr>
	                        <button type="button" id="btn" class="btn btn-info">process</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <aside>
        <div class="panel panel-default">
            <div class="panel-heading">
                Before
            </div>
            <div class="panel-body">
                <div class="form-group">
                    <label for="txt1">Number of nodes</label>
                    <input class="form-control" id="berforeNode" type="text" readonly />
                </div>
                <div class="form-group">
                    <label for="txt2">Number of edges</label>
                    <input class="form-control" id="berforeEdge" type="text" readonly />
                </div>
            </div>
        </div>
        <hr>
        <div class="panel panel-default">
            <div class="panel-heading">
                After
            </div>
            <div class="panel-body">
                <div class="form-group">
                    <label for="txt1">Number of nodes</label>
                    <input class="form-control" id="afterNode" type="text" readonly />
                </div>
                <div class="form-group">
                    <label for="txt2">Number of edges</label>
                    <input class="form-control" id="afterEdge" type="text" readonly />
                </div>
            </div>
            <div class="panel-footer">
                <a class="btn btn-info" href="downloadfile?fileName=net.txt" onclick="return false;" id="download" >Download</a>
            </div>
        </div>
    </aside>
    <!-- footer -->
    <footer>
        Copyright &copy; 2018 CSU-BIOINFORMATICS. All rights reserved.
    </footer>    
</div><!--.wrappper -->


<script type="text/javascript">
	
	$(function(){
		$('#btn').click(function(){
			if($("#net").val()=='') {
	              alert("please select net data");
	              return false;
	         }else{
	        	 
	        	 $.ajaxFileUpload({
                     url:'loadfile.action',//用于文件上传的请求地址
                     fileElementId :'net',//文件上传空间的id属性，通过这个id，相当于非异步中的myFile
                     dataType:'json',
                     success: function(data){
                         
                         $('#berforeNode').val(data['beforeNode']);
                         $('#berforeEdge').val(data['beforeEdge']);
                         $('#afterNode').val(data['afterNode']);
                         $('#afterEdge').val(data['afterEdge']);
                         $('#download').removeAttr('onclick');//去掉a标签中的onclick事件
                     },
                     error: function(data,status,e){
                         alert(e+"===上传文件失败");
                     }
                 });
	        	 return true;
	         }
			
		});
	});
	
</script>
</body>
</html>