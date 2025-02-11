package src;
import java.io.IOException;

public class Controller {

	private Fragenpool fragenpool;

	private QuizModus quizModus;

	private Nutzer nutzer;

	private View view;

	public Controller {
		this.fragenpool = new Fragenpool();
		this.quizModus = new QuizModus(fragenpool);
		this.view = new View();
	}

	public void starteQuiz() {

	}

	public void verarbeiteAntwort(String eingabe) {

	}

	public void nutzerRegisterien(String name, String email, String passwort) {

	}

	public void ladeFragenpool(String dateipfad) {

		try {
			System.out.println("Fragepool erfolgreich geladen.");
		} catch (IOException){
			System.err.println("Fehler beim Laden" + e.getMessage());
		}
	}

	public void speichereFragenpool(String dateipfad) {
		try {
			System.out.println("Fragepool erfolgreich gespeichert.");
		} catch (IOException e) {
			System.err.println("Fehler beim Speichern vom Fragepool:" + e.getMessage());
		}
	}
	public void start() {
		view.startMenü();
	}

	public static void main (String[]args) {
		Controller controller = new Controller();
		controller.start();
	}
}
