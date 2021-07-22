package cn.redflagsoft.base.service;

import cn.redflagsoft.base.test.BaseTests;











public class MsgTemplateServiceTest extends  BaseTests {
	protected MsgTemplateService msgTemplateService;
	
	public void testGetTaskMsgTemplate() throws Exception{
		super.assertNotNull(msgTemplateService);
		String str=msgTemplateService.getTaskMsgTemplateContent("1:${Task_Clerk_Name};2:${Project_Sn};3:${Project_Abbr};4:${Task_Type_Name};5:${Risk_Days_Value};6:${Risk_Days_Remain};7:${Risk_Date_Start};8:${Risk_Day_ValueUnit};9:${Risk_Dutyer_Name};10:${System_Abbr};11:${Now}", 1238330109771L);
		System.out.println(str);
	}
	
	public void testGetRiskMsgTemplate() throws Exception{
		super.assertNotNull(msgTemplateService);
		String str=msgTemplateService.getRiskMsgTemplateContent("1:${Task_Clerk_Name};2:${Project_Sn};3:${Project_Abbr};4:${Task_Type_Name};5:${Risk_Days_Value};6:${Risk_Days_Remain};7:${Risk_Date_Start};8:${Risk_Day_ValueUnit};9:${Risk_Dutyer_Name};10:${System_Abbr};11:${Now}", 1238330109052L);
		System.out.println(str);
	}
}
