<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <title>Calculation</title>
    <link rel="stylesheet" href="bootstrap/css/bootstrap.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="assets/css/common.css" />
    <link rel="stylesheet" href="assets/css/style.css" />
    <link rel="stylesheet" href="bootstrap/css/bootstrap-table.min.css" />
    <link rel="stylesheet" href="bootstrap/css/bootstrap-spinner.min.css">
    <link rel="stylesheet" href="bootstrap/css/bootstrap-select.min.css">
    
	<script src="assets/js/jquery-1.12.0.min.js"></script>
	<script src="bootstrap/js/bootstrap.min.js"></script>
	<script src="bootstrap/js/jquery.spinner.min.js"></script>
	<script src="bootstrap/js/bootstrap-select.min.js"></script>
	
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
		<div class="row" style="margin:10px auto;">
            <div class="col-md-12">
                <div class="panel panel-default">
                	<div class="panel-heading">
		                <h3 class="text-center">the top nodes</h3>
		            </div>
                    <div class="panel-body">
						<table class="table table-hover" >
							<thead>
							  <tr>
							  	<th class="text-center">Rank</th>
							    <th class="text-center">Node</th>
								<th class="text-center">TS-Hub</th>
								<th class="text-center">T-Hub</th>
								<th class="text-center">S-Hub</th>
								<th class="text-center">DC</th>
								<th class="text-center">MNC</th>
								<th class="text-center">NC</th>
							  </tr>
							</thead>
							<tbody>
						  		<s:iterator value="topNodes" id="entry" status="status">
						  			<tr>
						  				<td class="text-center"><s:property value="#status.index+1"/> </td>
										<td class="text-center"><s:property value="#entry.proteinName"/> </td>
										<td class="text-center"><s:property value="#entry.TSDegree"/></td>
										<td class="text-center"><s:property value="#entry.TDegree"/></td>
										<td class="text-center"><s:property value="#entry.SDegree"/></td>
										<td class="text-center"><s:property value="#entry.DCValue"/></td>
										<td class="text-center"><s:property value="#entry.MNCValue"/></td>
										<td class="text-center"><s:property value="#entry.NCValue"/></td>
						  			</tr>
						 		 </s:iterator>
						  	</tbody>
						</table>
					</div>
                </div>
            </div>
        </div>
    </div>

    <aside>
        <div class="panel panel-default">
            <div class="panel-heading">
                Download all centrality
            </div>
            <div class="panel-body">
                <a class="pull-right btn btn-info btn-sm" href="downloadfile?fileName=multiCentrality.txt">
					<span class="glyphicon glyphicon-save"></span>Download
				</a>
            </div>
        </div>
        <div class="panel panel-default">
            <div class="panel-heading">
                Visualization of spatial network
            </div>
            <div class="panel-body">
                <a class="pull-right btn btn-info btn-sm" href="viewstatic" >
					<span class="glyphicon glyphicon-eye-open"></span>Visualization
				</a>
            </div>
        </div>
        <div class="panel panel-default">
            <div class="panel-heading">
                Display of top nodes
            </div>
            <div class="panel-body">
                <form class="form-group" method="post" action="updateNodes">
                    <label>Top Number</label>
                    <div class="row">
                        <div class="col-lg-8">
                        	<input type="text" class="form-control text-center" name="topNodes"  />
                        </div>
                        <div class="col-lg-4"  style="padding-left: 0px;">
                            <button class="btn btn-default">OK</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </aside>
	
    <!-- footer -->
    <footer>
        Copyright &copy; 2018 CSU-BIOINFORMATICS. All rights reserved.
    </footer>


</div><!--.wrappper -->

</body>
</html>