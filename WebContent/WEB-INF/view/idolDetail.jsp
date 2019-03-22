<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
 <%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>idol detail page</title>
</head>
<body>

<div>

<h1>${idol.name } </h1>
<div>

<img alt="${idol.name }" src="${idol.profile } " width="100" />
<strong>${idol.name } </strong>
</div>
<ul>
<li>출생 
<fmt:formatDate value="${idol.birthDate }" var="fmtDate" pattern="yyyy년 MM월 dd일 "/>
<span>${fmtDate } </span></li>
<li>그룹 <span>${idol.groupName } </span></li>
<li>소속사 <span>${idol.agent }</span></li>
<li>
<a href="/deleteIdol.html?no=${idol.no }">${idol.name } 삭제</a>
</li>
<li>

<a href="/updateIdol.html?no=${idol.no }">${idol.name } 수정 </a>
</li>

</ul>

<a href="/index.html"> 그룹 목록 </a>
</div>
</body>
</html>