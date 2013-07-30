package timetracker;


import com.sun.jna.*
import com.sun.jna.platform.*;
import com.sun.jna.platform.win32.*;
import com.sun.jna.ptr.*
import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.ptr.IntByReference;

public class WindowsDataCollector implements DataCollector{
	def trackTagService

	public AppTrackItem getFocusedWindow(){
		HWND focusedWindow = User32.INSTANCE.GetForegroundWindow();
		//Get window title
		int titleLength = User32.INSTANCE.GetWindowTextLength(focusedWindow) + 1;
		char[] titleChar = new char[titleLength];
		User32.INSTANCE.GetWindowText(focusedWindow, titleChar, titleLength);
		String title = Native.toString(titleChar);
		// get window process id
		IntByReference id = new IntByReference()
		User32.INSTANCE.GetWindowThreadProcessId(focusedWindow, id)

		def pid = id.getValue();

		def cmd='TASKLIST /v /fi "PID eq '+pid+'" /FO CSV /NH'
		def appName=cmd.execute().text?.replaceAll('"', '')?.split(",")[0]

		AppTrackItem awInfo=new AppTrackItem(tag:TrackTagService.getOrCreateTrackTag(appName), title:title)
		return awInfo
	}
}
