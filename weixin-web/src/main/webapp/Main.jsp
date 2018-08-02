<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<img alt="二维码" src="${img_url}" width="200px" height="200">

	<h2>用户列表信息</h2>
	<table>
		<tr>
			<th>序号</th>
			<th>openId</th>
			<th>昵称</th>
			<th>图像</th>
			<th>性别</th>
			<th>地区</th>
			<th>关注时间</th>
			<th>操作</th>
		</tr>

		<c:forEach items="${userInfoList}" var="userInfo" varStatus="vs">
			<tr>
				<td><input type="checkbox" />${vs.index+1}</td>
				<td>${userInfo.openid}</td>
				<td>${userInfo.nickname}</td>
				<td><img title="图像" src="${userInfo.headimgurl}" width=40px
					height=40px /></td>
				<td><c:choose>
						<c:when test="${userInfo.sex==1}">男</c:when>
						<c:when test="${userInfo.sex==2}">女</c:when>
						<c:otherwise>未知</c:otherwise>
					</c:choose></td>
				<td>${userInfo.country}${userInfo.province}${userInfo.city}</td>
				<td>${userInfo.subscribe_time}</td>
				<td><button>发送消息</button>
					<button>详情</button></td>
			</tr>
		</c:forEach>


	</table>


</body>
</html>