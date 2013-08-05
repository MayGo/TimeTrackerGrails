package timetracker


class LogTrackItem extends TrackItem{
	String desc

	static constraints = {
	}
	static mapping = { desc type: 'text' }
}
