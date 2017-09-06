<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
	request.setAttribute("basePath", basePath);
%>
<meta http-equiv="Expires" CONTENT="0"> 
<meta http-equiv="Cache-Control" CONTENT="no-cache"> 
<meta http-equiv="Pragma" CONTENT="no-cache"> 
<link href="<%=basePath%>/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="<%=basePath%>/js/jquery.min.js"></script>
<script language="javascript" src="<%=basePath%>/js/bootstrap.min.js"></script>
<script language="javascript" src="<%=basePath%>/js/bootstrap-paginator.min.js"></script>
<script type="text/javascript">
 	$(document).ready(
	 	function(){
		var pinfo=$('#pagediv');
		if(pinfo!=undefined){
		var options = {  
            currentPage: pinfo.attr('pageNum'),  
            totalPages: pinfo.attr('pageCount'),
            bootstrapMajorVersion: 3,  
            size:"normal",  
            alignment:"center",  
            itemTexts: function (type, page, current) {  
                switch (type) {  
                    case "first":  
                        return "首页";  
                    case "prev":  
                        return "<";  
                    case "next":  
                        return ">";  
                    case "last":  
                        return "末页";  
                    case "page":  
                        return  page;  
                }                 
            },  
            onPageClicked: function (e, originalEvent, type, page) {  
                	$("#page").val(page);
                	$("#sform").submit();
            }  
        };  
        $('#pageul').bootstrapPaginator(options);  
		}
	 	}
    );
</script>
