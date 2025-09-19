<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url var="inquiryUrl" value="/inquiry" />
<c:url var="userUrl" value="jsp/Addtion.jsp" />
<c:url var="cssUrl" value="/style.css" />
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>お問い合わせ履歴</title>
<link rel="stylesheet" href="${cssUrl}"></head>
<body>
	<div class="container">
		<h1>お問い合わせ履歴</h1>
		<c:choose>
			<c:when test="${not empty inquiries1}">
				<c:forEach var="inquiry1" items="${inquiries1}" varStatus="status">
					<div class="inquiry-item">
						<h3>お問い合わせ #${status1.count}</h3>
						<p>
							<strong>名前:</strong>
							<c:out value="${inquiry1.name}" />
						</p>
						<p>
							<strong>メールアドレス:</strong>
							<c:out value="${inquiry1.email}" />
						</p>
						<p>
							<strong>内容:</strong>
						</p>
						<pre>
							<c:out value="${inquiry1.content}" />
						</pre>
						<p>
							<strong>添付ファイル:</strong>
							<c:choose>
    							<c:when test="${not empty inquiry1.attachmentFileName}">
        							<c:url var="fileUrl" value="/uploads/${inquiry.attachmentFileName}" />
        							<a href="${fileUrl}" target="_blank">
            							<c:out value="${inquiry1.attachmentFileName}" />
        							</a>
    							</c:when>
    							<c:otherwise>
        							なし
    							</c:otherwise>
							</c:choose>
						</p>
						<p>
							<strong>ステータス:</strong>
							<c:out value="${inquiry.status}" />
						</p>
						<form action="${inquiry1Url}" method="post" style="display: inline;">
							<input type="hidden" name="action" value="updateStatus">
							<input type="hidden" name="index" value="${status.index}">
							<label for="status-${status.index}">ステータス変更:</label> <select
								id="status-${status.index}" name="newStatus">
								<option value="新規"
									<c:if test="${inquiry.status == '新規'}">selected</c:if>>新規</option>
								<option value="対応中"
									<c:if test="${inquiry.status == '対応中'}">selected</c:if>>対応中</option>
								<option value="完了"
									<c:if test="${inquiry.status == '完了'}">selected</c:if>>完了</option>
							</select> <input type="submit" value="更新" class="button secondary">
						</form>
					</div>
					 <div class="button-group">
		           <a href="${abcUrl}" class="button secondary">ここまで</a>
		           </div>
				
					<hr>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<p>まだ回答を用意できていません</p>
				
			</c:otherwise>
		</c:choose>
		<div class="button-group">
			<a href="${inquiryUrl}" class="button secondary">お問い合わせフォームに戻る</a>
			<inquiries1>
		</div>
		
	</div>
</body>
</html>