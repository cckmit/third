<%@ page 
contentType="text/html;charset=GBK" 
pageEncoding="GBK"
import="org.opoo.apps.security.*,
	org.opoo.apps.module.*,
	org.opoo.apps.lifecycle.*,
	org.apache.struts2.config.DefaultSettings,
	java.util.*,
	org.opoo.apps.bean.core.*,
	org.opoo.apps.dao.*,
	org.opoo.apps.*,
	org.opoo.cache.*,
	org.opoo.apps.database.*,
	org.opoo.apps.id.sequence.*,
	org.opoo.apps.license.*,
	java.text.DecimalFormat,
	com.tangosol.net.Cluster, 
	com.tangosol.net.Member"
%><%
String action = request.getParameter("action");
boolean setClusteringEnabled = request.getParameter("save") != null;
boolean clusteringEnabled = "true".equals(request.getParameter("clusteringEnabled"));
boolean doClusterCacheReset = request.getParameter("clusterCacheReset") != null;


AppsLicenseManager licenseManager = AppsLicenseManager.getInstance();


if("emptyCache".equals(action)){
	String cacheName = request.getParameter("cacheName");
	if(cacheName != null && !cacheName.trim().equals("")){
		org.opoo.cache.Cache[] caches = org.opoo.apps.cache.CacheFactory.getAllCaches();
		for(org.opoo.cache.Cache cache: caches){
			if(cacheName.equals(cache.getName())){
				System.out.println("清除缓存：" + cacheName);
				cache.clear();
				showMessage(out, "清除缓存：" + cacheName);
				return;
			}
		}
	}
}

if (setClusteringEnabled && licenseManager.getAppsLicense().getNumClusterMembers() > 1) {
	if (org.opoo.apps.cache.CacheFactory.isClusteringEnabled()	!= clusteringEnabled)
	{
		org.opoo.apps.cache.CacheFactory.setClusteringEnabled(clusteringEnabled);
		//if (!setStqcEnabled) {
			response.sendRedirect("system-cache.jsp");
			return;
		//}
	}
}

// clear out the cache values for all cluster members
if (doClusterCacheReset) {
	//clear local node
	org.opoo.apps.cache.CacheFactory.clearCaches();
	//clear clustering nodes
	org.opoo.apps.cache.CacheFactory.doClusterTask(new org.opoo.apps.cache.AbstractClusterTask(){
		public void execute() {
			org.opoo.apps.cache.CacheFactory.clearCaches();
		}
	});

	// Done so redirect
	response.sendRedirect("system-cache.jsp?clearSuccess=true");
	return;
}






%>
<%!
private void showMessage(javax.servlet.jsp.JspWriter out, String msg) throws java.io.IOException{
	StringBuffer sb = new StringBuffer();
	sb.append("<script language='JavaScript'>\n");
	sb.append("<!--\n");
	sb.append("alert('" + msg + "');\n");
	sb.append("location.href = '?';\n");
	sb.append("//-->\n");
	sb.append("</script>\n");
	out.println(sb.toString());
	System.out.println(msg);
}
%>












<a href="index.jsp">管理首页</a>

<!-- 缓存相关 -->
<style>
.local{
	background-color:'#ffc';
}
</style>
<%
// Determine if clustering is enabled:
clusteringEnabled = org.opoo.apps.cache.CacheFactory.isClusteringEnabled();
Set clusters = null;
if (clusteringEnabled) {
	clusters = com.tangosol.net.CacheFactory.ensureCluster().getMemberSet();
}

// decimal formatter for cache values
DecimalFormat mbFormat = new DecimalFormat("#0.00");
DecimalFormat percentFormat = new DecimalFormat("#0.0");


%>


<%  if (clusteringEnabled) { %>

	<p>
    <b>集群概况</b>
	</p>

    <p>
	以下是当前应用的集群概况，您共有 <%= clusters.size() %> 个集群节点正在运行，您的 License 允许在当前集群中运行 
	<%= licenseManager.getAppsLicense().getNumClusterMembers() %> 个节点。可选择节点或者点击<a href="system-clustering.jsp">这里</a>查看集群节点详情。
	<span style="background-color:#ffc;">黄色</span>的行表示是<b>本地</b>节点。
    </p>

    <table cellpadding="0" cellspacing="0" border="0" width="100%">
    <tbody>
        <tr valign="top">
            <td width="99%">

                <div class="nodes">
                <table cellpadding="3" cellspacing="0" border="1" width="100%">
                <thead>
                    <tr>
                        <th colspan="2">
                            节点
                        </th>
                        <th style="text-align:center;" nowrap>
                            成员 ID
                        </th>
                        <th>
                            加入时间
                        </th>
                        <th style="text-align:center;" nowrap>
                            Senior Member
                        </th>
                        <th  class="last">&nbsp;</th>
                    </tr>
                </thead>

                <%  // Build a list of the members in the cluster:
                    java.util.List memberList = new java.util.LinkedList();
                    for (Iterator iter=clusters.iterator(); iter.hasNext();) {
                        memberList.add(iter.next());
                    }

                    com.tangosol.net.Cluster cluster = com.tangosol.net.CacheFactory.ensureCluster();
                    String oldestUID = cluster.getLocalMember().getUid().toString();
                    long oldest = cluster.getLocalMember().getTimestamp();
                    for (int i=0; i<memberList.size(); i++) {
                        Member member = (Member) memberList.get(i);
                        if (member.getTimestamp() < oldest) {
                            oldest = member.getTimestamp();
                            oldestUID = member.getUid().toString();
                        }
                    }

                    for (int i=0; i<memberList.size(); i++) {
                        Member member = (Member)memberList.get(i);
                        boolean isLocalMember = false;
                        String thisUID = cluster.getLocalMember().getUid().toString();
                        isLocalMember = thisUID.equals(member.getUid().toString());
                        boolean isSeniorMember = member.getUid().toString().equals(oldestUID);
                %>
                    <tr valign="top" class="<%= (isLocalMember ? "local" : "") %>">
                        <td align="center" width="1%">
                            <a href="system-clustering.jsp?UID=<%= member.getUid().toString() %>"
                             title="Click for more details"
                             ><img src="images/server-network-24x24.gif" width="24" height="24" border="0" alt=""></a>
                        </td>
                        <td class="jive-description" nowrap width="1%">
                            <a href="system-clustering.jsp?UID=<%= member.getUid().toString() %>"
                            <%  if (isLocalMember) { %>
                                ><b><%= member.getAddress().getHostName() %></b></a>
                            <%  } else { %>
                                ><%= member.getAddress().getHostName() %></a>
                            <%  } %>
                        </td>
                        <td class="jive-description" align="center" nowrap width="1%">
                            <%= member.getId() %>
                        </td>
                        <td class="jive-description" nowrap width="1%">
                            <%= new Date(member.getTimestamp()) %>
                        </td>
                        <td class="jive-description" nowrap width="1%" style="padding-left:20px;">
                            <%  if (isSeniorMember) { %>
                                <img src="images/check-16x16.gif" width="16" height="16" border="0" alt="">
                            <%  } else { %>
                                &nbsp;
                            <%  } %>
                        </td>
                        <td >&nbsp;</td>
                    </tr>

                <%  } %>

                </table>
                </div>

            </td>
            <td width="1%">
                <div style="padding-left:5px;">
                <!-- <img src="images/tangosolbutton.gif" border="0" alt="Powered by Tangosol"> -->
                </div>
            </td>
        </tr>
    </tbody>
    </table>
    <br />

<%  } %>





<%
org.opoo.cache.Cache[] caches = org.opoo.apps.cache.CacheFactory.getAllCaches();
//for(org.opoo.cache.Cache cache: caches){
//	out.println(cache.getName() + " " + cache.getCacheSize() + "<br/>");
//}
%>





<%
boolean advancedMode = "advanced".equals(request.getParameter("mode"));


%>

<form action="system-cache.jsp" method="post" name="cacheForm">

<p>
<b>缓存性能概况</b>
</p>

<p>
以下是所有缓存的概况。
</p>

<div class="jive-button">
<table cellpadding="0" cellspacing="0" border="0">
<tr>
    <td>
&nbsp;

    </td>
    <td>
&nbsp;
    </td>
    <td>
        <table cellpadding="3" cellspacing="0" border="0">
        <tr>
            <td><img src="images/document-exchange-16x16.gif" width="16" height="16" alt="" border="0"></td>
            <td>
                <span class="jive-button-label">
                模式:
                <select size="1" onchange="location.href='system-cache.jsp?mode='+this.options[this.selectedIndex].value;">
                    <option value="simple" <%= (!advancedMode ? "selected" : "") %>>简单</option>
                    <option value="advanced" <%= (advancedMode ? "selected" : "") %>>高级</option>
                </select>
                </span>
            </td>
        </tr>
        </table>
    </td>
    <td>
        &nbsp;&nbsp;
    </td>
</tr>
</table>
</div><br />

<%  // cache variables
    double overallTotal = 0.0;
    double memUsed;
    double totalMem;
    double freeMem;
    double usedMem;
    String hitPercent;
    long hits;
    long misses;
%>

<div id="jive-caches">
<table cellpadding="0" cellspacing="0" border="1" width="100%">
<%  if (!advancedMode) { %>
    <tr class="header-row">
        <th width="30%" class="c1">
            缓存名称
        </th>
        <th width="10%" class="c2">
            最大容量
        </th>
        <th width="10%" class="c2b">
            当前容量
        </th>
        <th width="20%" class="c3">
            使用率
        </th>
        <th width="20%" class="c4">
            Effectiveness*
        </th>
        <th width="10%" class="c5">&nbsp;<!-- <input type="checkbox" name="" value="" onclick="handleCBClick(this);"> --></th>
    </tr>
<%  } else { %>
    <tr class="header-row">
        <th width="20%" class="c1">
            缓存名称
        </th>
        <th width="15%" class="c2">
            最大容量
        </th>
        <th width="10%" class="c2b">
            当前容量
        </th>
        <th width="10%" class="c3">
            使用率
        </th>
        <th width="10%" class="c4">
            Effectiveness*
        </th>
        <th width="10%" class="c6">
            对象
        </th>
        <th width="15%" class="c7">
            Hits / Misses
        </th>
        <th width="10%" class="c5">&nbsp;<!-- 
		<input id="clearallcb01" type="checkbox" name="" value="" onclick="handleCBClick(this);"> --></th>
    </tr>
<%  } %>

<%  // Loop through each cache, print out its info
    for (int i=0; i<caches.length; i++) {
        org.opoo.cache.Cache cache = caches[i];
        if (cache.getMaxCacheSize() != -1 && cache.getMaxCacheSize() != Integer.MAX_VALUE) {
            overallTotal += (double)cache.getMaxCacheSize();
        }
        memUsed = (double)cache.getCacheSize()/(1024*1024);
        totalMem = (double)cache.getMaxCacheSize()/(1024*1024);
        freeMem = 100 - (100*(memUsed/totalMem));
        usedMem = 100*(memUsed/totalMem);
        hits = cache.getCacheHits();
        misses = cache.getCacheMisses();
        boolean lowEffec = false;
        if (hits + misses == 0) {
            hitPercent = "N/A";
        }
        else {
            double hitValue = 100*(double)hits/(hits+misses);
            hitPercent = percentFormat.format(hitValue) + "%";
            lowEffec = (hits > 500 && hitValue < 85.0 && freeMem < 20.0);
        }
%>
    <tr class="<%= (lowEffec ? "error" : "") %>" >
        <td class="c1">
            <table cellpadding="0" cellspacing="0" border="0">
            <tr>
                <td class="icon"><img src="images/cache-16x16.gif" width="16" height="16" alt="" border="0"></td>
                <td><%= cache.getName() %></td>
            </tr>
            </table>
        </td>
        <td class="c2">
            <% if (cache.getMaxCacheSize() != -1 && cache.getMaxCacheSize() != Integer.MAX_VALUE) { %>
                <%= mbFormat.format(totalMem) %> MB
            <% } else { %>
                Unlimited
            <% } %>
        </td>
        <td class="c3">
            <%= mbFormat.format(memUsed)%> MB
        </td>
        <td class="c3">
            <%= percentFormat.format(usedMem)%>%
        </td>
        <td class="c4">
            <%= hitPercent%>
        </td>

        <%  if (advancedMode) { %>

            <td class="c6">
                <%= cache.size() %>
            </td>
            <td class="c7">
                <%= hits %>
                /
                <%= misses%>
            </td>

        <%  } %>

        <td width="10%" class="c5" align="center"><!-- <input type="checkbox" name="cacheID" value="<%= i %>" onclick="updateControls(this.form);toggleHighlight(this);"> -->
		<a href="?action=emptyCache&cacheName=<%=cache.getName()%>">清空</a>
		
		</td>


    </tr>

<%  } %>

<tr class="footer-row">
    <td class="c1">
        合计:
    </td>
    <td class="c2">
        <%= mbFormat.format(overallTotal/(1024.0*1024.0)) %> MB
    </td>
    <td colspan="<%= (advancedMode ? "6" : "4") %>">&nbsp;
        <!-- <input type="submit" name="clear" value="Clear Selected" disabled> -->
    </td>
</tr>
</table>
</div>

<p class="jive-description">
* Effectiveness measures how well your cache is working. If the effectiveness is low, that usually means that
the cache is too small. Caches for which this may be the case are specially flagged.
</p>



</form>


<style type="text/css">
.ibox TH, .ibox TD {
    padding : 3px !important;
}
</style>

<%  if (true) { %>

    <form action="system-cache.jsp" name="featureForm" method="post">

    <br />

    <p>
    <b>缓存特性</b>
    </p>

    <div id="features">
    <table cellpadding="0" cellspacing="0" border="1" width="100%">
    <tr>
        <th width="20%" class="c1">特点</th>
        <th width="25%" class="c2">状态</th>
        <th width="55%" class="c3">描述</th>
    </tr>


    <tr>
        <td class="c1">
            集群
        </td>
        <td class="c2">

            <%  if (licenseManager.getAppsLicense().getNumClusterMembers() <= 1) { %>

                <div class="ibox">
                N/A
                </div>

            <%  } else { %>

                <div class="ibox">
                <table cellpadding="0" cellspacing="0" border="0" width="100%">
                <tr>
                    <td width="1%">
                        <input type="radio" name="clusteringEnabled" value="false" id="clus01"
                         <%= (!clusteringEnabled ? "checked" : "") %>>
                    </td>
                    <td width="99%"><label for="clus01">禁用</label></td>
                </tr>
                <tr>
                    <td width="1%">
                        <input type="radio" name="clusteringEnabled" value="true" id="clus02"
                         <%= (clusteringEnabled ? "checked" : "") %>
                         >
                    </td>
                    <td width="99%"><label for="clus02">启用</label></td>
                </tr>
                </table>
                </div>

            <%  } %>

        </td>
        <td class="c3">
            <%  if (licenseManager.getAppsLicense().getNumClusterMembers() <= 1) { %>

                <img src="images/warn-16x16.gif" border="0" alt="" />
                注意: 您的 License 仅允许一个节点，所以您不能启用集群。
                <br /><br />

            <%  } %>

           您可以<i>启用</i>或<i>禁用</i>集群缓存。
			
            <b>注意: 启动集群可能需要最多 30 秒时间。</b>
        </td>
    </tr>

    <tr class="footer-row">
        <td colspan="3">
            <input type="submit" name="save" value="保存设置">
            <!-- <input type="submit" name="cancel" value="Cancel"> -->
        </td>
    </tr>
    </table>
    </div>

    </form>

<%  } %>

<br /><br />



<p>
<b>缓存工具</b>
</p>

<form action="system-cache.jsp">

<div id="features">
<table cellpadding="0" cellspacing="0" border="1" width="100%">
<tr>
    <th width="20%" class="c1">工具</th>
    <th width="25%" class="c2">&nbsp;</th>
    <th width="55%" class="c3">描述</th>
</tr>
<tr>
    <td class="c1">
        集群范围内缓存重置
    </td>
    <td class="c2">

        <input type="submit" name="clusterCacheReset" value="重置所有集群成员节点缓存">

    </td>
    <td class="c3">
		清理集群中所有成员节点的所有缓存。
    </td>
</tr>
<tr>
    <td class="c1">
       Java 内存查看器
    </td>
    <td colspan="2">

        <%    // The java runtime
            Runtime runtime = Runtime.getRuntime();

            double freeMemory = (double)runtime.freeMemory()/(1024*1024);
            double maxMemory = (double)runtime.maxMemory()/(1024*1024);
            double totalMemory = (double)runtime.totalMemory()/(1024*1024);
            double usedMemory = totalMemory - freeMemory;
            double percentFree = ((maxMemory - usedMemory)/maxMemory)*100.0;
            double percentUsed = 100 - percentFree;
            int percent = 100-(int)Math.round(percentFree);
        %>

        <table cellpadding="0" cellspacing="0" border="0" width="400">
        <tr valign="middle">
            <td width="99%" valign="middle">
                <table cellpadding="0" cellspacing="0" border="0" width="100%" style="border:1px #666 solid;">
                <tr>
                    <%  if (percent == 0) { %>

                        <td width="100%"><img src="images/percent-bar-left.gif" width="100%" height="8" border="0" alt=""></td>

                    <%  } else { %>

                        <%  if (percent >= 90) { %>

                            <td width="<%= percent %>%" background="images/percent-bar-used-high.gif"
                                ><img src="images/blank.gif" width="1" height="8" border="0" alt=""></td>

                        <%  } else { %>

                            <td width="<%= percent %>%" background="images/percent-bar-used-low.gif"
                                ><img src="images/blank.gif" width="1" height="8" border="0" alt=""></td>

                        <%  } %>
                        <td width="<%= (100-percent) %>%" background="images/percent-bar-left.gif"
                            ><img src="images/blank.gif" width="1" height="8" border="0" alt=""></td>
                    <%  } %>
                </tr>
                </table>
            </td>
            <td width="1%" nowrap="nowrap">
                <div style="padding-left:6px;">
                <%= mbFormat.format(usedMemory) %> MB / <%= mbFormat.format(maxMemory) %> MB (<%= percentFormat.format(percentUsed) %>%)
                </div>
            </td>
        </tr>
        </table>

    </td>
</tr>
</table>
</div>

</form>

<br /><br />
