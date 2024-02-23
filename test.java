
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class testExamble {
	private static final String[] VIETNAMESE_LETTERS = { "aw", "aa", "dd", "ee", "oo", "ow", "w" };

	public static void main(String[] args) {
		String input = Input();
		Map<String, Integer> letterCounts = countOccurrencesOfLetters(input);
		printLetterCounts(letterCounts);
	}

	private static String Input() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Nhập chuỗi của bạn: ");
		return scanner.nextLine();
	}

	private static Map<String, Integer> countOccurrencesOfLetters(String input) {
		Map<String, Integer> letterCounts = new HashMap<>();
		for (String letter : VIETNAMESE_LETTERS) {
			countOccurrences(input, letterCounts, letter);
		}
		return letterCounts;
	}

	private static void countOccurrences(String input, Map<String, Integer> letterCounts, String letter) {
		Pattern pattern = Pattern.compile(letter);
		Matcher matcher = pattern.matcher(input);
		int start = 0;
		while (matcher.find(start)) {
			if (isExcludedCase(input, letter, matcher)) {
				start = matcher.end();
				continue;
			}
			letterCounts.put(letter, letterCounts.getOrDefault(letter, 0) + 1);
			start = matcher.end();
		}
	}

	private static boolean isExcludedCase(String input, String letter, Matcher matcher) {
		return letter.equals("w") && isPrecededByCertainLetters(input, matcher);
	}

	private static boolean isPrecededByCertainLetters(String input, Matcher matcher) {
		int position = matcher.start();
		return position > 0 && (input.charAt(position - 1) == 'a' || input.charAt(position - 1) == 'o');
	}

	private static void printLetterCounts(Map<String, Integer> letterCounts) {
		StringBuilder detectedLetters = new StringBuilder();
		int total = 0;
		for (Map.Entry<String, Integer> entry : letterCounts.entrySet()) {
			appendLetterCount(detectedLetters, entry);
			total += entry.getValue();
		}
		removeLastCommaAndSpace(detectedLetters);
		printDetectedLetters(detectedLetters);
		printTotalCount(total);
	}

	private static void appendLetterCount(StringBuilder detectedLetters, Map.Entry<String, Integer> entry) {
		detectedLetters.append(entry.getValue()).append(" ").append(entry.getKey()).append(", ");
	}

	private static void removeLastCommaAndSpace(StringBuilder detectedLetters) {
		if (detectedLetters.length() > 0) {
			detectedLetters.setLength(detectedLetters.length() - 2); 
		}
	}

	private static void printDetectedLetters(StringBuilder detectedLetters) {
		System.out.println("Các chuỗi con tiếng Việt được phát hiện: (" + detectedLetters.toString() + ")");
	}

	private static void printTotalCount(int total) {
		System.out.println("Tổng số lượng các chuỗi con tiếng Việt: " + total);
	}
}
