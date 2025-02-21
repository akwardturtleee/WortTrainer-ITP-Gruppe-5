package src;
import java.io.*;

public class Fragenpool {
	private Frage[] fragen;
	private int anzahlFragen;

	public Fragenpool() {
		this.fragen = new Frage[];
		this.anzahlFragen =0;
	}

	public void ladeFragenpool(String dateipfad) {
		try(BufferedReader reader = new BufferedReader(new FileReader(dateipfad))) {
			String line;
			anzahlFragen =0;
			while ((line = reader.readLine()) !=null && anzahlFragen < fragen.length) {
				String[]teile = line.split(";");
				if(teile.length == 2) {

				} else if (teile.length == 3) {

				}
			}
			System.out.println("Fragepool erfolgreich geladen. Anzahl der Fragen:" + this.anzahlFragen);
		} catch (IOException e){
			System.err.println("Fehler beim Laden" + e.getMessage());
		}
	}

	public void speichereFragenpool(String dateipfad) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(dateipfad))) {
			for (int i = 0; i < this.anzahlFragen; i++) {
				writer.write(fragen[i].getfrageID() + ";" + fragen[i].getAntwort());
				if (fragen[i].getBildURL() != null) {
					writer.write(";" + fragen[i].getBildURL());
				}
				writer.newLine();
			}
			System.out.println("Fragenpool erfolgreich gespeichert.");
		} catch (IOException e) {
			System.err.println("Fehler beim Speichern des Fragenpools: " + e.getMessage());
		}
	}


	public void fragehinzufügen(Frage frage) {

	}

	public void frageEntfernen(String frageID) {

	}

}
