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
	    padding:4px 10px;
	}
	
	.no {width:15%}
	.subject {width:30%}
	.writer {width:20%}
	.reg {width:20%}
	.hit {width:15%}
	.title{background:lightsteelblue}
	.odd {background:silver}
</style>
<script type="text/javascript">
	function list_go(f) {
		f.action="${pageContext.request.contextPath}/MyController?cmd=list&cPage=${cPage}";
		f.submit();
	}
	
	function update_ok(f) {
		if("${bvo.pwd}" == f.pwd.value){
			alert("수정성공");
			f.action="${pageContext.request.contextPath}/MyController?cmd=update_ok&cPage=${cPage}";
			f.submit();
		} else{
			alert("비밀번호 틀림");
			f.pwd.value="";
			f.pwd.focus();
			return;
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
					<td><input type="text" name="subject" size="45" value="${bvo.subject}"></td>
				</tr>
				<tr>
					<th>이름:</th>
					<td><input type="text" name="writer" size="12" value="${bvo.writer}"></td>
				</tr>
				<tr>
					<th>내용:</th>
					<td><script src="//cdn.ckeditor.com/4.16.1/standard/ckeditor.js"></script>
					<textarea name="content" cols="50" rows="8">${bvo.content}</textarea>
					<script type="text/javascript">
						CKEDITOR.replace('content'); // content에 써지는게 아닌 ckeditor에 써지는 거라 
													 // required가 안채워짐. 해결하는게 숙제
					</script>
					</td>
				</tr>
				<tr>
					<th>첨부파일:</th>
					<td>
						<c:choose>
							<c:when test="${empty bvo.file_name}">
								<input type="file" name="file_name" />이전파일없음<br>
								<input type="hidden" name="old_file_name" value="" /> <!-- 이전 파일을 처리(없어도 될지도?) 차피 이전파일이 없는 경우이므로-->
							</c:when>
							<c:otherwise>
								<!-- 파일이 있는 경우 -->
								<input type="file" name="file_name" /> 이전파일(${bvo.file_name})
								<input type="hidden" name="old_file_name" value="${bvo.file_name}" /> 							
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr>
					<th>비밀번호:</th>
					<td><input type="password" name="pwd" size="12"></td>
				</tr>
				<tr>
					<td colspan="2" style="text-align: center">
						<input type="button" value="수정" onclick="update_ok(this.form)" />
						<input type="reset" value="다시" />
						<input type="button" value="목록" onclick="list_go(this.form)" />
					</td>
				</tr>
			</tbody>
		</table>
	</form>
	</div>
</body>
</html>

