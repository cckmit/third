<%@ page import="javax.management.MBeanServer,
                 javax.management.MBeanServerFactory,
                 javax.management.ObjectName,
                 java.util.HashSet,
                 java.util.Iterator,
                 java.util.Set"%>
<html>
  <title>Coherence: JMX Cache Information</title>
  <body>
    <center>
<%
  // get the requested default JMX domain name
  String sDefaultDomainName = request.getParameter("domain");
  if (sDefaultDomainName == null)
    {
    sDefaultDomainName = "";
    }

  // get the requested Coherence Cache name
  String sCacheName = request.getParameter("view"); 

  // find (or create) an MBeanServer for the specified default JMX domain name
  MBeanServer server = null;
  for (Iterator iter = MBeanServerFactory.findMBeanServer(null).iterator();
       iter.hasNext();)
      {
      server = (MBeanServer) iter.next();
      if (sDefaultDomainName.length() == 0 || 
          server.getDefaultDomain().equals(sDefaultDomainName))
          {
          break;
          }
      server = null;
      }
  if (server == null)
      {
      server = MBeanServerFactory.createMBeanServer(sDefaultDomainName);
      }

  // get the set of all Coherence Member MBean ObjectNames
  ObjectName objNameMembers    = new ObjectName("Coherence:type=Node,*");
  Set        setObjNameMembers = server.queryNames(objNameMembers, null);

  // get the set of Coherence Cache names (obtained from the 'name' property on
  // a Cache MBean ObjectName)
  ObjectName objNameCaches    = new ObjectName("Coherence:type=Cache,*");
  Set        setObjNameCaches = server.queryNames(objNameCaches, null);
  Set        setCacheNames    = new HashSet();

  for (Iterator iter = setObjNameCaches.iterator(); iter.hasNext(); )
    {
    setCacheNames.add(((ObjectName) iter.next()).getKeyProperty("name"));
    }

  // initialize the selected Cache name, if necessary
  if (sCacheName == null && !setCacheNames.isEmpty())
    {
    sCacheName = (String) setCacheNames.iterator().next();
    }
%>
      <form action="<%= response.encodeURL("JmxCacheExplorer.jsp") %>" method="post">
        <table border=0>
          <tr valign="bottom">
            <td align="right" width="300"><b>Default Domain:</b></td>
            <td>
              <input type="text" name="domain" value="<%= sDefaultDomainName %>">
            </td>
            <td align="right"><b>View:</b></td>
            <td>
              <select name="view" onchange="this.form.submit();">
<%
  // display a list of all Coherence Caches
  for (Iterator iter = setCacheNames.iterator(); iter.hasNext(); )
    {
    String sName     = (String) iter.next();
    String sSelected = sName.equals(sCacheName) ? "selected=\"true\"" : "";

%>
                <option <%= sSelected %> value="<%= sName %>"><%= sName %></option>
<%
    }
%>
              </select>
            </td>
            <td>
              <input type="button" name="refresh" value="Refresh" onclick="this.form.submit();"/>
            </td>
            <td align="right" width="300"><img src="../images/PoweredByCoherence.gif"/></td>
          </tr>
        </table>
      </form>
      <hr>
      <table border=3>
        <tr>
          <td bgcolor="#C01020" colspan="3"><b><font color="white">Member:</font></b></td>
          <td bgcolor="#C01020" colspan="5"><b><font color="white">Front&nbsp;Tier:</font></b></td>
          <td bgcolor="#C01020" colspan="3"><b><font color="white">Back&nbsp;Tier:</font></b></td>
          <td bgcolor="#C01020" colspan="3"><b><font color="white">Write-Behind:</font></b></td>
        </tr>
        <tr>
          <td><b>Id</b></td>
          <td><b>Address</b></td>
          <td><b>Port</b></td>
          <td><b>Gets</b></td>
          <td><b>Puts</b></td>
          <td><b>Hits</b></td>
          <td><b>Misses</b></td>
          <td><b>Effectiveness</b></td>
          <td><b>Hits</b></td>
          <td><b>Misses</b></td>
          <td><b>Effectiveness</b></td>
          <td><b>Reads</b></td>
          <td><b>Writes</b></td>
          <td><b>Failures</b></td>
        </tr>
<%
  // display information on the requested Coherence Cache for each Cluster
  // Member; include information on the front and back tiers of each Cache
  // (including write-behind information for the back tiers)
  for (Iterator iter = setObjNameMembers.iterator(); iter.hasNext(); )
    {
    ObjectName objNameMember = (ObjectName) iter.next();
    String     sMemberId     = objNameMember.getKeyProperty("nodeId");

    String sFrontQuery = "Coherence:type=Cache,name=" + sCacheName + ",nodeId=" + sMemberId + ",tier=front,*";
    String sBackQuery  = "Coherence:type=Cache,name=" + sCacheName + ",nodeId=" + sMemberId + ",tier=back,*";

    Set setFront = server.queryNames(new ObjectName(sFrontQuery), null);
    Set setBack  = server.queryNames(new ObjectName(sBackQuery), null);

    Object id = "", address = "", port = "";
    Object frontGets = "", frontPuts = "", frontHits = "", frontMisses = "";
    Object backHits = "", backMisses = "";
    Object frontProb = "", backProb = "";
    Object storeReads = "", storeWrites = "", storeFails = "";

    if (!setFront.isEmpty() || !setBack.isEmpty())
      {
      id      = server.getAttribute(objNameMember, "Id");
      address = server.getAttribute(objNameMember, "UnicastAddress");
      port    = server.getAttribute(objNameMember, "UnicastPort");
      }

    if (!setFront.isEmpty())
      {
      // note: it is possible that the front query returned more than one 
      // ObjectName, such as in the case of two applications using the same cache
      ObjectName objNameFront = (ObjectName) setFront.iterator().next();

      frontGets   = server.getAttribute(objNameFront, "TotalGets");
      frontPuts   = server.getAttribute(objNameFront, "TotalPuts");
      frontHits   = server.getAttribute(objNameFront, "CacheHits");
      frontMisses = server.getAttribute(objNameFront, "CacheMisses");
      frontProb   = server.getAttribute(objNameFront, "HitProbability");
      }

    if (!setBack.isEmpty())
      {
      ObjectName objNameBack = (ObjectName) setBack.iterator().next();

      backHits   = server.getAttribute(objNameBack, "CacheHits");
      backMisses = server.getAttribute(objNameBack, "CacheMisses");
      backProb   = server.getAttribute(objNameBack, "HitProbability");

      storeReads  = server.getAttribute(objNameBack, "StoreReads");
      storeWrites = server.getAttribute(objNameBack, "StoreWrites");
      storeFails  = server.getAttribute(objNameBack, "StoreFailures");
      }
%>
        <tr>
          <td><%= id %></td>
          <td><%= address %></td>
          <td><%= port %></td>

          <td><%= frontGets %></td>
          <td><%= frontPuts %></td>
          <td><%= frontHits %></td>
          <td><%= frontMisses %></td>
          <td><%= frontProb %></td>

          <td><%= backHits %></td>
          <td><%= backMisses %></td>
          <td><%= backProb %></td>

          <td><%= storeReads %></td>
          <td><%= storeWrites %></td>
          <td><%= storeFails %></td>
        </tr>
<%
    }
%>
      </table>
      <hr>
      <font size="2" color="black">
        <p align="center">
          <br>
          &copy; Copyright (c) 2000, 2009, Oracle and/or its affiliates. All rights reserved.
          <br>
          <a href="http://www.tangosol.com/">Website</a>
          <a href="mailto:support@tangosol.com">Email Support.</a>
        </p>
      </font>
    </center>
  </body>
</html>