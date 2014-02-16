package com.navymail.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import com.navymail.modules.Topic;
import com.navymail.modules.User;
import android.graphics.Point;
import android.view.Display;

public class ApplicationController {

	private static ApplicationController instance = new ApplicationController();

	public User currentUser;
	public ArrayList<Topic> da5ly_topics = new ArrayList<Topic>();
	public ArrayList<Topic> khargy_topics = new ArrayList<Topic>();
	public Topic currentTopic;
	public String cachedOrders = "";
	public String selectedUnits = "";
	public HashMap<String, Boolean> signedHash = new HashMap<String, Boolean>();
	public boolean handFreeMode = true;
	public boolean isKhargy = true; // default , start with khargy mails
	public boolean isCommanderPreview = false; // check arkan suggest to preview
												// to qa2k

	public static ApplicationController getInstance() {
		return instance;
	}

	public String arabization(String original) {
		original = original.replaceAll("0", "٠");
		original = original.replaceAll("1", "١");
		original = original.replaceAll("2", "٢");
		original = original.replaceAll("3", "٣");
		original = original.replaceAll("4", "٤");
		original = original.replaceAll("5", "٥");
		original = original.replaceAll("6", "٦");
		original = original.replaceAll("7", "٧");
		original = original.replaceAll("8", "٨");
		original = original.replaceAll("9", "٩");
		return original;
	}

	public void copy(File src, File dst) throws IOException {
		File[] files = src.listFiles();
		for (int i = 0; i < files.length; i++) {
			File source = files[i];
			File destination = new File(dst, source.getName());
			InputStream in = new FileInputStream(source);
			OutputStream out = new FileOutputStream(destination);
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			in.close();
			out.close();
		}
	}

	public Point getXY(int x, int y, Display d, int numLines, int maxChars) {
		int screenW = d.getWidth();
		int screenH = d.getHeight() - 220;
		Point point = new Point(x, y);

		int expectedWidth = 9 * maxChars;
		if (expectedWidth < 250)
			expectedWidth = 250;

		if ((screenW - x) < expectedWidth)
			point.x = screenW - expectedWidth;

		int expectedHeight = 17 * numLines + 225;
		if ((screenH - y) < expectedHeight)
			point.y = screenH - expectedHeight;

		return point;
	}

	public String getFileName(int index) {
		if (index < 10)
			return "i0" + index + ".jpg";
		else
			return "i" + index + ".jpg";
	}

	private void copySingleFile(File source, File destination) {
		try {
			InputStream in = new FileInputStream(source);
			OutputStream out = new FileOutputStream(destination);
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			in.close();
			out.close();
		}

		catch (Exception e) {
			// TODO: handle exception
		}
	}

	public String getDirName() {
		return currentUser.dirName;
	}
}
