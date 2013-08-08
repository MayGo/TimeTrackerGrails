package timetracker
import static Math.abs

class Color{
	String rgb

	Color() {
	}
	Color(String rgb) {
		this.rgb=(rgb)?:this.randomVividRgbCode()
	}
	static constraints = {
	}
	static mapping = { title type: 'text' }

	def floor(x) {
		x as int
	}

	def randomVividRgbCode() {
		def rnd = new Random()
		def h = rnd.nextFloat()
		def s = 0.65f + rnd.nextFloat() * 0.35f // Quite saturated
		def l = 0.5f
		rgbToColorCode(*hslToRgb(h, s, l))
	}

	def rgbToColorCode(r, g, b) {
		def rgb = floor(r * 255) << 16 |
				floor(g * 255) << 8  |
				floor(b * 255)
		'#' + Integer.toString(rgb, 16).padLeft(6, '0')
	}

	def hslToRgb(h, s, l) {
		def c = (1 - abs(2 * l - 1)) * s // Chroma.
		def h1 = h * 6
		def x = c * (1 - abs(h1 % 2 - 1))
		def rgb = h1 < 1 ? [c, x, 0]:
		h1 < 2 ? [x, c, 0]:
		h1 < 3 ? [0, c, x]:
		h1 < 4 ? [0, x, c]:
		h1 < 5 ? [x, 0, c]:
		[c, 0, x]
		def (r, g, b) = rgb
		def m = l - c * 0.5
		[r + m, g + m, b + m]
	}
}
