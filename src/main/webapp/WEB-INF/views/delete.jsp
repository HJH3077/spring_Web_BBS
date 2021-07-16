<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
	#bbs table {
	    width:800px;
	    margin:0 auto;
	    margin-top:20px;
	    border:1px solid black;
	    border-collapse:collapse;
	    font-size:14px;
	    
	}
	
	#bbs table caption {
	    font-size:20px;
	    font-weight:bold;
	    margin-bottom:10px;
	}
	
	#bbs table th {
	    text-align:center;
	    border:1px solid black;
	    padding:4px 10px;
	}
	
	#bbs table td {
	    text-align:left;
	    border:1px solid black;
	    padding:10px 10px;
	}
	
	.no {width:15%}
	.subject {width:30%}
	.writer {width:20%}
	.reg {width:20%}
	.hit {width:15%}
	.title{background:lightsteelblue}
	.odd {background:silver}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script type="text/javascript">
	$(function() {
		
	});
</script>
<script type="text/javascript">
	function list_go(f) {
		f.action="list.do?cPage=${cPage}";
		f.submit();
	}
	
	function delete_go(f) {
		var chk = false;
		$.ajax({
			url : "pwd_chk.do",
			method:"post",
			data : "pwd="+$("#pwd").val()+"&b_idx=${b_idx}",
			dataType :"json",
			async : false,
			success : function(data) {
				console.log(data);
				if(data=='0'){
					alert("비밀번호 틀림");
					$("#pwd").val("");
					$("#pwd").focus("");
				
				}else if(data=='1'){
					alert("비밀번호가 일치합니다.");
					chk = true;
				}
			},
			error: function() {
				alert("읽기실패");
			}
		});
		
		if(chk){
			f.action="delete_ok.do";
			f.submit();
		}
	}
</script>
</head>
<body>
	<div id="bbs">
	<form method="post">
		<table summary="게시판 글쓰기">
			<caption>게시판 글삭제</caption>
			<tbody>
				<tr>
					<th>비밀번호:</th>
					<td><input type="password" name="pwd" id="pwd"></td>
				</tr>
				<tr>
					<td colspan="2" style="text-align: center">
						<input type="button" value="삭제" onclick="delete_go(this.form)" />
						<input type="button" value="목록" onclick="list_go(this.form)" />
						<input type="hidden" name="b_idx" value="${b_idx}">
						<input type="hidden" name="cPage" value="${cPage}">
					</td>
				</tr>
			</tbody>
		</table>
	</form>
	</div>
</body>
</html>

