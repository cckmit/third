<%@ tag dynamic-attributes="attrMap" %>
<%@ attribute name="listTag" required="true" %>
<%@ attribute name="itemTag" required="true" %>
<%@ attribute name="separator" required="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<p> Dynamic Attributes: </p>
<jsp:element name="${listTag}">
    <c:forEach var="attr" items="${attrMap}">
        <jsp:element name="${itemTag}">
            ${attr.key} ${separator} ${attr.value}
        </jsp:element>
    </c:forEach>
</jsp:element>
