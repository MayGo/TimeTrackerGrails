package timetracker

import java.sql.Timestamp;

class ApplicationInfo {
	//Long id
	String name
	String title
	
	Date focusedFrom
	Date focusedUntil
	Date dateCreated
	Date lastUpdated
	int updatedCount = 0

	static constraints = {
	}
	static mapping = {
		title type: 'text'
	 }

//	@Override
//	public String toString() {
//		return "ApplicationInfo [name=" + name + ", title=" + title
//		+ ", dateCreated=" + dateCreated + ", dateUpdated="
//		+ dateUpdated + ", updatedCount=" + updatedCount + "]";
//	}
}
