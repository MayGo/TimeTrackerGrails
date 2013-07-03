package timetracker

import java.sql.Timestamp;

class ApplicationInfo {
	//Long id
	String name
	String title
	Date dateCreated
	Date lastUpdated
	int updatedCount = 0

	static constraints = {
	}

//	@Override
//	public String toString() {
//		return "ApplicationInfo [name=" + name + ", title=" + title
//		+ ", dateCreated=" + dateCreated + ", dateUpdated="
//		+ dateUpdated + ", updatedCount=" + updatedCount + "]";
//	}
}
