package timetracker;

public class LinuxDataCollector implements DataCollector{

	public static ApplicationInfo getFocusedWindow(){
		String activeWindowStr = "xprop -root _NET_ACTIVE_WINDOW".execute(null,new File("/")).text
		if(activeWindowStr){
			List activeWindowArr= activeWindowStr.split(" ") as List
			String activeWindowId=activeWindowArr.last()
			//println "Got active window id: $activeWindowId";
			if(activeWindowId){
				String activeWinInfoStr= "xprop -id ${activeWindowId}".execute(null,new File("/")).text
				if(activeWinInfoStr){
					//println activeWinInfoStr
					def titleRx = activeWinInfoStr =~ /(WM_NAME\((.+)\) = (.+))/
					//def roleRx = activeWinInfoStr =~ /(WM_WINDOW_ROLE\((.+)\) = (.+))/
					def classRx = activeWinInfoStr =~ /(WM_CLASS\((.+)\) = (.+))/
					//String role= (roleRx)?roleRx[0][1].split("=")[1].trim().replace("\"", ""):""
					String appName= (classRx)?classRx[0][1].split("=")[1].trim().replace("\"", "").split(",")[0]:""
					String title=(titleRx)?titleRx[0][1].split("=")[1].trim().replace("\"", ""):""
					
					ApplicationInfo awInfo=new ApplicationInfo(name:appName, title:title)
					return awInfo
					
				}else
					println "Nothing found for active window ID $activeWindowId"
			}else{
				println "No active window ID found"
			}
		}
	}

}
