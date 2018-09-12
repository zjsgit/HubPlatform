<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <title>TemporalNetwork</title>
    <link rel="stylesheet" href="bootstrap/css/bootstrap.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="assets/css/common.css"/>
    <link rel="stylesheet" href="assets/css/style.css">
    <link rel="stylesheet" href="bootstrap/css/bootstrap-spinner.min.css">
    <link rel="stylesheet" href="bootstrap/css/bootstrap-select.min.css">
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
                            <li><a href="viewstatic">StaticNetwork</a></li>
                            <li><a class="menu-top-active" href="viewtemporal">TemporalNetwork</a></li>
                            <li><a href="viewspatial">SpatialNetwork</a></li>
                        </ul>
                    </div>
                </div>
  
            </div>
        </div>
    </div>

    <div class="cyto-main">
        <div id="cy" style="width: 100%;height: 618px;background-color: #FFF;"></div>
    </div>

    <aside>
        <div class="panel panel-default">
            <div class="panel-heading">
                Network layouts
            </div>
            <div class="panel-body">
                <div class="form-group">
                    <label>Select network layout</label>
                    <select class="form-control" id="select-layout">
                        <option value="random">RANDOM</option>
                        <option value="circle">CIRCLE</option>
                        <option value="concentric">CONCENTRIC</option>
                        <option value="cose">COSE</option>
                    </select>
                </div>
            </div>
        </div>
        <div class="panel panel-default">
            <div class="panel-heading">
                Visualization of top nodes
            </div>
            <div class="panel-body">
                <div class="form-group">
                    <label>Top Number</label>
                    <div class="row">
                        <div class="col-lg-8">
                            <div class="input-group spinner" data-trigger="spinner" id="customize-spinner">
                                <input type="text" class="form-control text-center" id="topNumber" value="10" data-max="1000" data-min="10" data-step="10" />
                                <div class="input-group-addon">
                                    <a href="javascript:;" class="spin-up" data-spin="up"><i class="glyphicon glyphicon-chevron-up"></i></a>
                                    <a href="javascript:;" class="spin-down" data-spin="down"><i class="glyphicon glyphicon-chevron-down"></i></a>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-4"  style="padding-left: 0px;">
                            <button class="btn btn-default" id="updataNumber">OK</button>
                        </div>
                    </div>

                </div>
            </div>
        </div>
        <div class="panel panel-default">
            <div class="panel-heading">
               Display temporal network
            </div>
            <div class="panel-body">
                <div class="form-group">
                    <label>Time</label>
                    <div class="row">
                        <div class="col-lg-8">
                            <select class="form-control" id="time">
                            	<option value="0">1</option>
                                <option value="1" selected="selected">2</option>
                                <option value="2">3</option>
                                <option value="3">4</option>
                                <option value="4">5</option>
                                <option value="5">6</option>
                                <option value="6">7</option>
                                <option value="7">8</option>
                                <option value="8">9</option>
                                <option value="9">10</option>
                                <option value="10">11</option>
                                <option value="11">12</option>
                            </select>
                        </div>

                        <div class="col-lg-4"  style="padding-left: 0px;">
                            <button class="btn btn-default" id="chageTime">OK</button>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </aside>

    <!-- footer -->
    <footer>
        Copyright &copy; 2018 CSU-BIOINFORMATICS. All rights reserved.
    </footer>

</div><!--.wrappper -->

<script src="assets/js/jquery-1.12.0.min.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>
<script src="bootstrap/js/jquery.spinner.min.js"></script>
<script src="bootstrap/js/bootstrap-select.min.js"></script>
<script src="assets/js/cytoscape.min.js"></script>
<script type="text/javascript">
    $(function () {
        var cy = null;
        var cystyle = [
             {
                selector: 'node',
                style: {
                    'content': 'data(id)',
                    'text-valign': 'top',
                    'text-halign': 'center',
                    'shape': 'circle',
                    'height': 20,
                    'width': 20,
                    'background-color': '#63B8FF',
                    'color': '#777'
                }
            },
            {
                selector: 'edge',
                style: {
                    'label' : 'data(label)',
                    //'color' : 'red',
                    'edge-text-rotation': 'autorotate',
                    'curve-style': 'haystack',
                    'haystack-radius': 0,
                    'width': 1,
                    'opacity': 1,
                    'line-color': '#000',
                }
            },
            {
                selector: ':selected',
                style: {
                    'background-color': '#FFFF00',
                    'line-color': '#FF0000'
                }
            }
        ];
        
        var netData = ${jsonResult};
        
        //显示图形
        cy = cytoscape({
            container: document.getElementById('cy'),

            userZoomingEnabled : true,
            userPanningEnabled : true,
            boxSelectionEnabled: true,
            autounselectify: false,
            hideLabelsOnViewport: true,
            wheelSensitivity : 0.1,
            layout : {
                name : 'cose'
            },
            style : cystyle,
            elements : netData
        });
        
        //改变图形布局
        $("#select-layout").change(function() {
            cy.layout({
                name : $(this).val()
            });
        });
        
        $("#updataNumber").click(function(){
        	var number=$("#topNumber").val();
        	var time=$("#time").val();
        	$.ajax({ 
                type : "post",
                url : "updatetemporal_updateNetwork",
                data : {
                    proteinNumber:number,
                    selectTime:time
                },
                dataType:"json",
        
                success : function(data) {
                	if(data.length==23){
                		alert("the top "+number+" nodes do not form subnets in the "+time+" time");
                    }
                	displayNetwork(data);
                },

                error : function() {
                    alert("可视化失败！！！");
                }
            });
        });
        $("#chageTime").click(function(){
        	var number=$("#topNumber").val();
        	var time=$("#time").val();
        	$.ajax({ 
                type : "post",
                url : "updatetemporal_updateNetwork",
                data : {
                    proteinNumber:number,
                    selectTime:time
                },
                dataType:"json",
        
                success : function(data) {
					if(data.length==23){
						alert("the top "+number+" nodes do not form subnets in the "+time+" time");
                    }
                	displayNetwork(data);
                },

                error : function() {
                    alert("可视化失败！！！");
                }
            });
        });
        
    });
    
  	function displayNetwork(data) {
  		var cy = null;
        var cystyle = [
            {
                selector: 'node',
                style: {
                    'content': 'data(id)',
                    'text-valign': 'top',
                    'text-halign': 'center',
                    'shape': 'circle',
                    'height': 20,
                    'width': 20,
                    'background-color': '#63B8FF',
                    'color': '#777'
                }
            },
            {
                selector: 'edge',
                style: {
                    'label' : 'data(label)',
                    //'color' : 'red',
                    'edge-text-rotation': 'autorotate',
                    'curve-style': 'haystack',
                    'haystack-radius': 0,
                    'width': 1,
                    'opacity': 1,
                    'line-color': '#000',
                }
            },
            {
                selector: ':selected',
                style: {
                    'background-color': '#FFFF00',
                    'line-color': '#FF0000'
                }
            }
        ];
        
        var netData = JSON.parse(data);//JSON.parse('${net}');
        //显示图形
        cy = cytoscape({
            container: document.getElementById('cy'),

            userZoomingEnabled : true,
            userPanningEnabled : true,
            boxSelectionEnabled: true,
            autounselectify: false,
            hideLabelsOnViewport: true,
            wheelSensitivity : 0.1,
            layout : {
                name : 'cose'
            },
            style : cystyle,
            elements : netData
        });
      	//改变图形布局
        $("#select-layout").change(function() {
            cy.layout({
                name : $(this).val()
            });
        });
    }
    
</script>
</body>
</html>