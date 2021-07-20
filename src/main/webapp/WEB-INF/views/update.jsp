<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
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
	    padding:4px 10px;
	}
	
	input {padding: 5px;}
	
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
	function list_go(f) {
		f.action="list.do?cPage=${cPage}";
		f.submit();
	}
	/* vo을 이용한 비밀번호 처리 
	function update_ok(f) {
		if("${bvo.pwd}" == f.pwd.value){
			alert("수정하기");
			f.action="update_ok.do?cPage=${cPage}";
			f.submit();
		}else{
			alert("비밀번호틀림");
			f.pwd.value="";
			f.pwd.focus();
			return;
		}
	}
	*/
	// ajax를 이용해서 비밀번호 맞는지 체크하기 
	function update_ok(f) {
		// 비밀번호가 맞아야 chk가 true로 변경
		var chk = false;
		$.ajax({
			url: "pwd_ck.do",
			method : "post",
			data : "pwd="+$("#pwd").val()+"&b_idx=${b_idx}",
			dataType : "json",
			async : false,
			success : function(data) {
				if(data=='0'){
					alert("비밀번호 틀림");
					$("#pwd").val("");
					$("#pwd").focus();
				}else if(data=='1'){
					alert("비밀번호 맞음");
					chk = true;
				}
			},
			error :function(){
				alert("읽기실패");
			}
		});
		
		if(chk){
			f.action ="update_ok.do";
			f.submit();
		}
	}
</script>
</head>
<body>
	<div id="bbs">
	<form method="post" encType="multipart/form-data">
		<table summary="게시판 글수정">
			<caption>게시판 글수정</caption>
			<tbody>
				<tr>
					<th>제목:</th>
					<td><input type="text" name="subject" size="45" value="${bvo.subject }"></td>
				</tr>
				<tr>
					<th>이름:</th>
					<td><input type="text" name="writer" size="12" value="${bvo.writer }"></td>
				</tr>
				<tr>
					<th>내용:</th>
					<td><script src="//cdn.ckeditor.com/4.16.1/standard/ckeditor.js"></script>
						<textarea name="content" cols="50" rows="8">${bvo.content}</textarea>
						<script type="text/javascript">
							CKEDITOR.replace('content');
						</script>
					</td>
				</tr>
				<tr>
					<th>첨부파일:</th>
					<td>
						<c:choose>
							<c:when test="${empty bvo.file_name }">
								<input type="file" name="f_name">이전파일없음
								<input type="hidden" name="old_file_name" value="">
							</c:when>
							<c:otherwise>
								<input type="file" name="f_name"> 이전파일(${bvo.file_name})
								<input type="hidden" name="old_file_name" value="${bvo.file_name}">
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr>
					<th>비밀번호:</th>
					<td><input type="password" name="pwd" id="pwd"></td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="button" value="수정" onclick="update_ok(this.form)">
						<input type="button" value="목록" onclick="list_go(this.form)">
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