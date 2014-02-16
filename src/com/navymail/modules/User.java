package com.navymail.modules;

public class User {

	public int userID;
	public String name;
	public String jobName;
	public String signatureColor;
	public String rank;
	public int signImage ;
	public int photo ;
	public String dirName;
	public String backUpDir ;
	
	public User() {
	}

	public static User initializeUser (boolean isQa2d)
	{
		User user = new User();
		
		if (isQa2d)
		{
			user.userID = 1;
			user.name = "أسامة احمد الجندى" ;
			user.jobName = "قائد القوات البحرية" ;
			user.rank = "فريق" ;
			user.signatureColor = "#fe3a3a";
			user.photo = com.navymail.ui.R.drawable.photo_qa2ek;
			user.signImage = com.navymail.ui.R.drawable.sign_qa2ek;
			
			user.dirName = "navy_commander";
			user.backUpDir = "navy_arkan(commander_backup)" ;
 		}
		else
		{
			user.userID = 2;
			user.name = "محمد مجدى عبد السميع" ;
			user.jobName = "رئيس أركان القوات البحرية" ;
			user.rank = "لواء بحرى أ.ح " ;
			user.signatureColor = "#14694C";
			user.photo = com.navymail.ui.R.drawable.photo_arkan;
			user.signImage = com.navymail.ui.R.drawable.sign_arkan;
			
			user.dirName = "navy_arkan(commander_backup)";
			user.backUpDir = "navy_backup" ;
		}
		
		return user ;
	}
}
