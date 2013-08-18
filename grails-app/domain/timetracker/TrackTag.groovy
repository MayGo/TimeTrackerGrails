package timetracker

class TrackTag {
	String name
	Color color

	TrackTag() {
	}
	TrackTag(String name) {
		this.name=name
		this.color=new Color('').save()// TODO: If track tag is not saved color should not be saved
	}
	TrackTag(String name, Color color) {
		this.name=name
		this.color=color
	}

	Date dateCreated
	Date lastUpdated

	static constraints = {
	}
}
