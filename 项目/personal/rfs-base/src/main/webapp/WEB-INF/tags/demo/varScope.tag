<%@ variable name-given="BV" scope="AT_BEGIN" %>
<%@ variable name-given="NV" scope="NESTED" %>
<%@ variable name-given="EV" scope="AT_END" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="tagPhase" value="TAG: at begin"/>
<%@ include file="varScopeRow.tagf" %>

<c:set var="GV" value="TAG A" scope="request"/>
<c:set var="LV" value="TAG A"/>
<c:set var="BV" value="TAG A"/>
<c:set var="NV" value="TAG A"/>
<c:set var="EV" value="TAG A"/>

<c:set var="tagPhase" value="TAG: before doBody"/>
<%@ include file="varScopeRow.tagf" %>

<jsp:doBody/>

<c:set var="tagPhase" value="TAG: after doBody"/>
<%@ include file="varScopeRow.tagf" %>

<c:set var="GV" value="TAG B" scope="request"/>
<c:set var="LV" value="TAG B"/>
<c:set var="BV" value="TAG B"/>
<c:set var="NV" value="TAG B"/>
<c:set var="EV" value="TAG B"/>

<c:set var="tagPhase" value="TAG: at end"/>
<%@ include file="varScopeRow.tagf" %>
