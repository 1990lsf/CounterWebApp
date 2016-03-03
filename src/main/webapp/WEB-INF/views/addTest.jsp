<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
<head>
<title>Spring MVC Tutorial by Crunchify - Hello World Spring MVC
	Example</title>
<style type="text/css">
body {
	background-image: url('http://crunchify.com/bg.png');
}
ul{list-style-type:none; margin:0;width:100%; }
ul li{ width:80px; float:left;}


.btn-select{position:absolute;display:inline-block;width:150px;height:25px;font:14px/20px "Microsoft YaHei";color:#4B4B4B;margin-left:0px;z-index:999;}
.btn-select .cur-select{position:absolute;display:block;width:150px;height:25px;line-height:25px;text-indent:10px;z-index:999;border:1px dashed #CCCCCC;border-radius:5px;background:url("img/sj.png") no-repeat 125px center;}
.btn-select select{position:absolute;top:0;left:0;width:150px;height:25px;opacity:0;filter:alpha(opacity:0;);font:14px/20px "Microsoft YaHei";color:#f80;}
.btn-select select option{text-indent:10px;}
#emotion{position:absolute;z-index:999;}
</style>

</head>
<body>${message }

	<div style="font-family: verdana; padding: 10px; border-radius: 10px; font-size: 12px; text-align:center;">
 
		Spring MCV Tutorial by <a href="http://crunchify.com">Crunchify</a>.
		
	</div>
	
	<textarea name="message" title="Ctrl+Enter快捷提交" placeholder="说点什么吧…" style="width:450px;height: 54px;"></textarea>


<div class="emoji">
<a class="btn-select" id="btn_select">
<span class="cur-select">添加快捷回复</span>
<select name="emotion" id="emotion">
<option value="" selected="selected">添加快捷回复：</option>
<option value="【哈哈哈】">【哈哈哈】</option>
<option value="【啪啪啪】">【啪啪啪】</option>
<option value="【顶顶顶】">【顶顶顶】</option>
<option value="【去去去】">【去去去】</option>
<option value="【啦啦啦】">【啦啦啦】</option>
<option value="【羊羊羊】">【羊羊羊】</option>
<option value="【哈哈哈】">【哈哈哈】</option>
<option value="【啪啪啪】">【啪啪啪】</option>
<option value="【顶顶顶】">【顶顶顶】</option>
<option value="【去去去】">【去去去】</option>
<option value="【啦啦啦】">【啦啦啦】</option>
<option value="【羊羊羊】">【羊羊羊】</option>
</select>
</a>
<div>
<ul>
<li >【哈哈哈】</li>
<li >【啪啪啪】</li>
<li >【顶顶顶】</li>
<li >【去去去】</li>
<li >【啦啦啦】</li>
<li >【羊羊羊】</li>
</ul>
</div>

</div>


<script>window.onload=function(){
var ds_textarea = document.getElementsByName("message")[0];
var faceList = ["【哈哈哈】", "【啪啪啪】", "【顶顶顶】", "【去去去】", "【啦啦啦】", "【羊羊羊】", "【哈哈哈】", "【啪啪啪】", "【顶顶顶】", "【去去去】", "【啦啦啦】", "【羊羊羊】"];
var optionsList = document.getElementById("emotion").options;

for (var i = 0; i < faceList.length; i++) {
    optionsList[1 + i] = new Option(faceList[i], faceList[i]);
}
document.getElementById("emotion").onchange = function (i) { 
    if (this.selectedIndex != 0) { 
        ds_textarea.value += this.value; 
        var l = ds_textarea.value.length; 
        ds_textarea.focus(); 
        ds_textarea.setSelectionRange(l, l); 
    } 
}
}
</script>
</body>
</html>
