package com.socialvideo.constant;

import java.util.HashMap;
import java.util.Map;

public class WebConstants {

	
	
	//IDENTITY
	public static final String IDENTITY = "IDENTITY";
	
	
	//NAVBAR
	public static final String NAVBAR_SHOWLEFTSIDEBAR = "NAVBAR_SHOWLEFTSIDEBAR";
	public static final String NAVBAR_SHOWUPLOADBUTTON = "NAVBAR_SHOWUPLOADBUTTON";
	public static final String NAVBAR_SHOWSEARCHTXT = "NAVBAR_SHOWSEARCHTXT";
	public static final String NAVBAR_SHOWSIGNBUTTON = "NAVBAR_SHOWSIGNBUTTON";
	public static final String ISFOLLOWINGUSER = "ISFOLLOWINGUSER";
	
	
	

	public static final String DEVICE = "device";
	
	
	//SECURITY ROBOT CHECK
	public static final String SECDTO = "SecurityDTO";
	public static final String CAPTHASECFRM = "captchaFormDTO";

	
	
	//REQUEST GMAP RESULT
	public static final String GMAPRESULT = "GMAPRESULT";
	public static final String VIDEOGMAPRESULT = "VIDEOGMAPRESULT";
	public static final String GMAPSTATICURI = "GMAPSTATICURI";
	public static final String VIEWPORTMARKERS = "VIEWPORTMARKERS";

	
	//GENERAL REQUEST PARAMS
	public static final String VIDCATALOGQUERY = "VIDCATALOGQUERY";
	public static final String VIEW = "view";
	public static final String FILTER = "filter";
	public static final String SUBFILTER = "subfilter";
	public static final String GRID = "grid";
	public static final String USERDTO = "userDTO";
	public static final String VIDEODTO = "videoDTO";	
	public static final String RADIUSDTO = "radiusDTO";	
	public static final String VIDEOCLAIMDTO = "claimDTO";	
	public static final String EXCEPTION = "EXCEPTION";
	public static final String NOTIFICATIONGROUPEDLIST = "notificationGroupedListDTO";	
	public static final String NOTIFICATIONLIST = "notificationListDTO";	
	public static final String NOTIFICATIONUNREADEDCOUNT = "notificationUnreadedCount";
	public static final String NOTIFICATIONALLCOUNT = "notificationAllCount";
	
	
	
	
	
	
	
	public static final String SETTINGSVIEW_AVATAR = "avatar";
	public static final String SETTINGSVIEW_PROFILE = "profile";
	public static final String SETTINGSVIEW_SECURITY = "security";
	public static final String SETTINGSVIEW_PRIVACY = "privacy";
	public static final String SETTINGSVIEW_NOTIFICATIONS = "notifications";
	
	
	//SYS PROPS DTO
	public static final String SYSPROPSDTO = "syspropsDTO";
	
	//STATISTICS
	public static final String STATISTICSDTO = "stDTO";
	
	
	//FORMS
	public static final String VIDEOUPLOADFRM = "VIDEOUPLOADFRM";
	public static final String VIDEOSETTINGSFRM = "VIDEOSETTINGSFRM";
	public static final String VIDEOQUERYFRM = "VIDEOQUERYFRM";
	public static final String STATSQUERYFRM = "STATSQUERYFRM";
	public static final String VIDMANAGERFRM = "VIDMANAGERFRM";
	public static final String ADMINMANAGERFRM = "ADMINMANAGERFRM";
	public static final String ADMINQUERYFRM = "ADMINQUERYFRM";
	public static final String REGISTERFRM = "REGISTERFRM";
	public static final String LOGINFRM = "LOGINFRM";
	public static final String SETTINGSFRM = "SETTINGSFRM";
	public static final String VIDEOCLAIMFRM = "VIDEOCLAIMFRM";
	public static final String CONTACTUSFRM = "CONTACTUSFRM";	
	
	//PAGINATORS
	public static final String MAPPAGINATOR = "MAPPAGINATOR";
	public static final String MAPLISTPAGINATOR = "MAPLISTPAGINATOR";
	public static final String ADMINPAGINATOR = "ADMINPAGINATOR";
	public static final String CATALOGPAGINATOR = "CATALOGPAGINATOR";
	public static final String STATSPAGINATOR = "STATSPAGINATOR";	
	
	public static final String GRIDSTYLE_LIST = "LIST";
	public static final String GRIDSTYLE_DETAIL = "DETAIL";
	public static final String GRIDSTYLE_ANALYTIC = "ANALYTIC";
	public static final String GRIDSTYLE_DATATABLE = "DATATABLE";
	public static final String GRIDSTYLE_BOX = "BOX";
	public static final String GRIDSTYLE_BOXUSR = "BOXUSR";
	public static final String GRIDSTYLE_THUMB = "THUMB";
	public static final String GRIDSTYLE_FULLSCREENMAP = "FULLSCREENMAP";
	
	

	public static final String PERSONALIZATION = "PERS";
	public static final String VIDEOTYPECOLORSMAP = "VIDEOTYPECOLORSMAP";
	public static final String VIDEOTYPECOLORSLIST = "VIDEOTYPECOLORSLIST";
	public static final String PINSCLUSTERCOLORSLIST = "PINSCLUSTERCOLORSLIST";
	
	
	
	
	//CONTEXT PATH
	public static final String CTXPATH = "CTXPATH";
	
	
	
	public static final Map<String,Integer> GRIDSTYLE = new HashMap<>();

	//SECURITY
	public static final String SECURITY_LOCKVIDEO = "securityLockVideo";
	public static final String SECURITY_VIDEOCOUNTS = "securityVideoCounts";


	public static final String PERFOMANCETIME = "PERFORMANCETIME";
	





	
	static {
	    final Map<String,Integer> m = GRIDSTYLE; // Use short name!
	    m.put(GRIDSTYLE_DETAIL, 1); // Here referencing the local variable which is also faster!
	    m.put(GRIDSTYLE_BOX, 3); // Here referencing the local variable which is also faster!
	    m.put(GRIDSTYLE_THUMB, 4); // Here referencing the local variable which is also faster!
	}
	
	
	
	
	
		
}
