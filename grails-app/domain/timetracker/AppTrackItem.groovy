package timetracker


class AppTrackItem extends TrackItem{
	String title

	static constraints = {
	}
	static mapping = { title type: 'text' }

}
