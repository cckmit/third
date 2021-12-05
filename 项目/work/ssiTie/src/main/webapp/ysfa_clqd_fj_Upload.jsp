<%@ page language="java" import="java.util.List" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
	<head>
		<title>附件</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<script type="text/javascript" src="swfupload/jquery.form.js"></script>
		<script type="text/javascript" src="swfupload/jquery-1.7.2.min.js"></script>
		<script type="text/javascript" src="swfupload/swfupload.js"></script>
  		<script type="text/javascript" src="swfupload/handlers.js"></script>
  		<script type="text/javascript" src="H5Upload/H5Upload.js"></script>

		
	    <style type="text/css">
			td{
				font-size:16px;
			}
			th{
				font-size:16px;
			}
			#tpxs{
			    border:none;
			}
			#tpxs tr{
			    border:none;
			    font-size:16px;
			}
			#tpxs tr td{
			    border:none;
			    font-size:16px;
			}
		</style>
		<script type="text/javascript">
			var swfu;
			window.onload = function(){
				var uploadClass = SWFUpload;
				if (window.FormData) {
					uploadClass = H5Upload;
				}
				swfu = new uploadClass({
					upload_url: "../../FileUploadServlet?scwjId="+$("#scwjId").val()+"&sclb="+12,
					
					// File Upload Settings
					file_size_limit : "1024",	// 1000MB
					file_types : "*.jpg;*.png;*.jpeg",//设置可上传的类型
					file_types_description : "图片",
					file_upload_limit : "10",
	                
					file_queue_error_handler : fileQueueError,//选择文件后出错
					file_dialog_complete_handler : fileDialogComplete,//选择好文件后提交
					file_queued_handler : fileQueued,
					upload_progress_handler : uploadProgress,
					upload_error_handler : uploadError,
					upload_success_handler : uploadSuccess,
					upload_complete_handler : uploadComplete,
	
					// Button Settings
					button_image_url : "../../images/SmallSpyGlassWithTransperancy_17x18.png",
					button_placeholder_id : "spanButtonPlaceholder",
					button_width: 100,
					button_height: 18,
					button_text : '<span class="button">添加附件</span>',
					button_text_style : '.button { font-family: Helvetica, Arial, sans-serif; font-size: 12pt; } .buttonSmall { font-size: 10pt; }',
					button_text_top_padding: 0,
					button_text_left_padding: 18,
					button_window_mode: SWFUpload.WINDOW_MODE.TRANSPARENT,
					button_cursor: SWFUpload.CURSOR.HAND,
					
					// Flash Settings
					flash_url : "../../swfupload/swfupload.swf",
	
					custom_settings : {
						upload_target : "divFileProgressContainer"
					},
					// Debug Settings
					debug: false  //是否显示调试窗口
				});
				//window.
			};
			//删除附件
			function deleteYsxkClqdFj(obj){
				var id = $(obj).attr("id");
       	   		var sendData={"id":id}
    		   	$.ajax( {
   				    type : "POST",
   					url : "ysfaClqdFjDel.action",
   					dataType : "text",
   					data :sendData,
   					success : function(data) {
  						if($.trim(data)=="success"){
  	   					   	alert("删除成功！");
  	   					   	var url="ysfaFjPreUpload.action?clbh="+$("#clbh").val()+"&ysfaId="+$("#ysfaId").val();
 	   						window.location = url;
  						}else{
  							alert("删除失败！");
      				    }
   					}
    			});
			}
			//刷新按钮
			function sxFj(){
				var url="ysfaFjPreUpload.action?clbh="+$("#clbh").val()+"&ysfaId="+$("#ysfaId").val();
 	   			window.location = url;
			}
			//附件排序
			function fjPx(){
				//将排序的地方能显示input输入框输入数字
				var Containers = $(".fjpx");  //获取所有节点的dom数组   
                var len = Containers.length;               //数组长度   
                for(var index = 0; index < len; index++){   
                    var $container = $(Containers[index]); //循环遍历每一个dom节点
                    var value = $container.html();
                   	$container.empty();//清空节点内容
                   	$container.prev().css("margin-left","0px");//
                   	$container.html("<input style='width:30px;' type='text' class='cxpx' value='"+value+"' />");//添加节点内容
                }
                //修改刷新按钮上的value值为“确认排序”
                //删除原来刷新按钮上的事件，绑定一个新的onlick事件
                $('#sdpx').val("确认排序");
                setTimeout(function () {
                	$('#sdpx').attr('onclick', '').bind('click',function () { qrpx(); });
            		}, 1);
            	
			}
			//确认排序
			function qrpx() {
				//定义一个js数组用来保存（附件id--排序值）
				var a=new Array();
			
            	//检查排序编码是否正确
            	var Containers = $(".cxpx");  //获取所有节点的dom数组   
                var len = Containers.length;            //数组长度   
                for(var index = 0; index < len; index++){   
                    var $container = $(Containers[index]); //循环遍历每一个dom节点
                    var value = $.trim($container.val());//得到每个输入框的值
                    var idValue = $container.parent().next().val();//得到排序值对应的附件id
                    //"^\+?[1-9][0-9]*$"--非零正整数
                    var reg = /^\+?[1-9][0-9]*$/g;
                    if(reg.test(value)){//输入的排序值正确
                    	//遍历数组，判读这个排序值有没有
                    	for(var ele in a){
                    		if(a[ele][1] == value){//排序值重复
                    			alert("您好，您输入了多个排序值"+value+"。");
                        		return false;
                    		}else{
                    		    continue; //跳出当前次循环
                    		}
                    	}
                    	//循环遍历后没有，将附件id--排序值保存进数组
                    	a[index] = new Array();
                    	a[index][0]=idValue;
                    	a[index][1]=value;
                    }else{//输入的排序值不正确
                        alert("您好，你输入的排序值"+value+"错误，请输入正整数。");
                        return false;
                    }
                }
                //检查完赋值是否正确后，通过ajax到后台去排序
                var i,jsonstr;
                jsonstr="[";
                for(i=0;i<a.length;i++) { 
                	//jsonstr += "{\" " + a[i][0] + "\""+ ":" + "\"" + a[i][1] + "\}",";
                	jsonstr += "{\"ID\""+ ":" + "\"" + a[i][0] + "\",\"PX\""+ ":" + "\"" + a[i][1] + "\"},";
                }
                jsonstr = jsonstr.substring(0,jsonstr.lastIndexOf(',')); jsonstr += "]";
                
                var sendData={"jsonstr":jsonstr}
    		   	$.ajax( {
   				    type : "POST",
   					//url : "devUploadFjPx.action",
   					url : "ysfaClqdFjPx.action",
   					dataType : "text",
   					data :sendData,
   					success : function(data) {
  						if($.trim(data)=="success"){
  	   					   	alert("排序成功！");
  	   					   	var url="ysfaFjPreUpload.action?clbh="+$("#clbh").val()+"&ysfaId="+$("#ysfaId").val();
 	   						window.location = url;
  						}else{
  							alert("排序失败！");
      				    }
   					}
    			});
                
       		}
       		
       		//如果是第一次新增进来的时候刷新下页面
       		// $(function (){
       		// 	var scwjId = $("#scwjId").val();
		    // 	if(null == scwjId || scwjId==""){
		    // 		var url="ysfaFjPreUpload.action?clbh="+$("#clbh").val()+"&ysfaId="+$("#ysfaId").val();
			// 		window.location = url;
		    // 	}
			// });
		</script>
	</head>
	
	<body>
	<div id="content">
		<form>
			<input type="hidden" id="scwjId" value="${ysfaClqd.clqdId}" />
			<input type="hidden" id="ysfaId" value="${ysfaId}" />
			<input type="hidden" id="clbh" value="${clbh}" />
			<table class="grid2">
			<tr>
				<td colspan="2" style="text-align: center;">
					<h1>预（现）售方案附件上传</h1>
				</td>
			</tr>
			<tr>
				<th style="width:30%;">材料编号：</th>
				<td style="width:70%;">
					${ysfaClqd.clbh}
				</td>
			</tr>
			<tr>
				<th style="width:30%;">材料名称：</th>
				<td style="width:70%;">
					${ysfaClqd.clmc}
				</td>
			</tr>
			<tr style="height:60px;">
				<th>上传图片：</th>
			    <td colspan="2">
			    	<span id="spanButtonPlaceholder"></span>
		  			<div id="divFileProgressContainer" style="width:200;display:none;"></div>
					<div id="thumbnails">
						<table id="infoTable" border="0" width="50%" >
						</table>
					</div>
			    </td>
			</tr>
			<tr style="height:35px;">
				<td colspan="2" style="text-align: center;">
					<input type="button" style="margin-left: 100px;" id="sub" class="input-btn" value="刷 新" onclick="sxFj();" />
					<input type="button" style="margin-left: 100px;width:100px;background-color: #a3dae4;" id="sdpx" value="重新排序" onclick="fjPx();" />
					<input style="margin-left: 100px;" type="button" class="input-btn"
						onclick="window.close()" value="关 闭" />
				</td>
			</tr>
		   </table>
		</form>
	</div>
	<div>
		<table id="tpxs" class="grid2">
			<s:iterator value="ysfaClqdFjList" var="ysfaClqdFj">
				<tr>
					<td style="text-align:left;" valign="top">
						<span style="font-size:16px;margin-left:20px;">&nbsp;</span>
						<span class="fjpx" style="font-size:16px;display:inline-block;">${ysfaClqdFj.px}</span>：
						<input type="hidden" value="${ysfaClqdFj.id}"/>
					</td>
					<td style="width:80px;text-align:center;" valign="top">
						<span style="margin-left:10px;"></span>
						<input type="button" class="input-btn" id="${ysfaClqdFj.id}" onclick="deleteYsxkClqdFj(this);" value="删 除" />
						<span style="margin-right:30px;"></span>
					</td>
					<td style="width:auto;text-align:left;">
							<img width="1100" height="800" src="<%=basePath%>${ysfaClqdFj.bcswjlj}" id="${ysfaClqdFj.id}"/>
					</td>
				</tr>
			</s:iterator>
		</table>
	</div>
	</body>
</html>