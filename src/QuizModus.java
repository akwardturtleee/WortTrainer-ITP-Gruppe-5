package src;

public class QuizModus {

	private Fragenpool[] fragenpool;

	private int[] punkte;

	private int[] richtigeAntworten;

	private int[] falscheAntworten ;

	private Fragenpool fragenpool2;

	public QuizModus(Fragenpool fragenpool) {

	}

	public void starteQuiz() {

	}

	public void beendeQuiz() {
		System.exit(0);
	}

	public boolean pruefeAntwort(String eingabe, Frage frage) {
		return false;
	}

	public String berechneStatistik() {
		return null;
	}

}
