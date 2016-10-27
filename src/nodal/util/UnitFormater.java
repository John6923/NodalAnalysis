package nodal.util;

public abstract class UnitFormater {

	private UnitFormater() {
		throw new AssertionError(
				"Should not create instances of UnitFormater!");
	}

	private static String format(String prefix, double number) {
		if (number >= 100) {
			return String.format("%.0f %s", number, prefix);
		} else if (number >= 10) {
			return String.format("%.1f %s", number, prefix);
		} else {
			return String.format("%.2f %s", number, prefix);
		}
	}

	public static String prefixNumber(double number) {
		number = Math.abs(number);
		if (number >= 1000000) {
			return format("M", 0.000001 * number);
		} else if (number >= 1000) {
			return format("k", 0.001 * number);
		} else if (number >= 1) {
			return format("", 1 * number);
		} else if (number >= 0.001) {
			return format("m", 1000 * number);
		} else if (number >= 0.000001) {
			return format("u", 1000000 * number);
		} else {
			return "0 ";
		}
	}

	public static String formatAmp(double current) {
		return String.format("%sA", prefixNumber(current));
	}

	public static String formatVolt(double voltage) {
		return String.format("%sV", prefixNumber(voltage));
	}

	public static String formatOhm(double resistance) {
		return String.format("%sOhm", prefixNumber(resistance));
	}

}
