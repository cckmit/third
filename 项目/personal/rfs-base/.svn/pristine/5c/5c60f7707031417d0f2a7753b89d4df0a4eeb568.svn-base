<html>
<head>
  <title>My First Coherence Cache</title>
</head>
<body>
<%@ page language="java" 
         import="
                 com.tangosol.net.*,

                 com.tangosol.run.xml.XmlElement,

                 com.tangosol.util.*,

                 java.util.Iterator
                 "
%>


<% 
// ----- Process request parameters ----------------------------------------

String  sKey     = request.getParameter("key");
String  sValue   = request.getParameter("value");

boolean fGet     = request.getParameter("get"    ) != null;
boolean fPut     = request.getParameter("put"    ) != null;
boolean fRemove  = request.getParameter("remove" ) != null;
boolean fClear   = request.getParameter("clear"  ) != null;

String  sThisURI = request.getRequestURI();


// ----- Cache Operations --------------------------------------------------

NamedCache cache = CacheFactory.getCache("VirtualCache");

if (fPut && sKey   != null && sKey  .length() > 0
         && sValue != null && sValue.length() > 0)
    {
    // Put the value into the cache
    cache.put(sKey, sValue);
    }
else if (fRemove)
    {
    // Remove the value from the cache
    cache.remove(sKey);

    // clear the display
    sKey   = null;
    sValue = null;
    }
else if (fClear)
    {
    // Clear the cache
    cache.clear();

    // clear the display
    sKey   = null;
    sValue = null;
    }
else
    {
    // Get the value from the cache
    sValue = (String) cache.get(sKey);
    }

%>
<center>
<table>
  <tr>
    <td width=300><b>Edit Cache Entries</b> </td>
    <td width=100></td>
    <td><img src="../images/PoweredByCoherence.gif"/></td>
  </tr>
</table>
<hr>

<b>List of Keys in the cache:</b>
<p>
<%
// Display cache contents by showing the first 50 keys
int cnt = 0; 
for (Iterator iter = cache.keySet().iterator(); (iter.hasNext() && cnt < 50); cnt++)
    {
    String sTempKey  = (String) iter.next();
%>
<a href="<%= sThisURI %>?key=<%= sTempKey %>"><%= sTempKey %></a>,  
<%
    }
if (cnt >= 50)
    {
%>
<br>Only first 50 values displayed of the total <%= cache.size() %><br>
<%
    }
%>
<hr>
<form name='update'action='<%= sThisURI %>' method='post'>
<table>
  <tr>
    <td>Key:</td>
    <td><input type='text' name='key'   value='<%= sKey   == null ? "" : sKey %>' size='10'/></td>
  </tr>
  <tr>
    <td>Value:</td>
    <td><input type='text' name='value' value='<%= sValue == null ? "" : sValue %>' size='20'/></td>
  </tr>
</table>
<table>
  <tr>
    <td><input type='submit' name='get'    value='  Get  '/></td>
    <td><input type='submit' name='put'    value='  Put  '/></td>
    <td><input type='submit' name='remove' value='Remove '/></td>
    <td><input type='submit' name='clear'  value=' Clear '/></td>
  </tr>
</table>
</form>
</center>
<hr>
<%
// ------ Get and Display cache configuration information --------------------

DefaultConfigurableCacheFactory factory = 
    (DefaultConfigurableCacheFactory) CacheFactory.getConfigurableCacheFactory();

// get cache name from the cache instance
String sCacheName = cache.getCacheName();

// get cache info
DefaultConfigurableCacheFactory.CacheInfo cacheInfo =
  factory.findSchemeMapping(sCacheName);

// get caching scheme xml contents
XmlElement xmlConf = factory.resolveScheme(cacheInfo);

// Quick conversion to format xml properly for display
String sXmlConfig = xmlConf.toString();
sXmlConfig = Base.replace(Base.replace(sXmlConfig, ">","&gt;")  , "<","&lt;");
sXmlConfig = Base.replace(Base.replace(sXmlConfig, " ","&nbsp;"), "\n","<br>");
%>
<b><%= sCacheName %></b> cache is using a cache-scheme named <b><%= cacheInfo.getSchemeName() %></b>,<br>
defined as:
<blockquote>
<code><%= sXmlConfig %></code>
</blockquote>
<hr>
<b>The following member nodes are currently active:</b><br>
<blockquote>
<%
Cluster cluster    = CacheFactory.getCluster();
Member  memberThis = cluster.getLocalMember();

for (Iterator iter = cluster.getMemberSet().iterator(); iter.hasNext(); )
    {
    Member member = (Member) iter.next();
    
    out.println(member + 
        (member.equals(memberThis) ? " <-- this node<br>" : "<br>"));
    }
%>
</blockquote>
<hr>
</body>
</html>