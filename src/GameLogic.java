import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameLogic {

    private List<Card> cards; // Kártyák listája
    private int gridSize; // Rácsméret (pl. 4x4)
    private String imageFolder; // Melyik mappából töltsük be a képeket?

    public GameLogic(int gridSize, Difficulty difficulty) {
        this.gridSize = gridSize;
        this.imageFolder = getImageFolder(difficulty); // Megadjuk a megfelelő mappa nevét
        generateCards();
    }

    // Kép mappa kiválasztása a nehézségi szint alapján
    private String getImageFolder(Difficulty difficulty) {
        switch (difficulty) {
            case MEDIUM:
                return "images_6x6"; // 6x6-os nehézségnél
            case HARD:
                return "images_8x8"; // 8x8-as nehézségnél
            case EASY:
            default:
                return "images"; // Alapértelmezett 4x4-es nehézségnél
        }
    }

    // Kártyák generálása és keverése
    private void generateCards() {
        cards = new ArrayList<>();
        int totalCards = gridSize * gridSize;

        // Kártyák értékeinek generálása
        for (int i = 1; i <= totalCards / 2; i++) {
            String imagePath = imageFolder + "/" + i + ".png"; // A megfelelő mappából töltjük be a képeket
            cards.add(new Card(imagePath)); // Első példány
            cards.add(new Card(imagePath)); // Második példány
        }

        Collections.shuffle(cards); // Keverés
    }

    // Kártya értékének lekérdezése adott index alapján
    public String getCardValue(int index) {
        return cards.get(index).getValue(); // A Card osztályból kérjük le az értéket
    }

    // Párok ellenőrzése
    public boolean checkMatch(int index1, int index2) {
        if (index1 == index2) return false; // Ugyanaz a kártya nem lehet pár
        boolean isMatch = cards.get(index1).getValue().equals(cards.get(index2).getValue());
        if (isMatch) {
            cards.get(index1).setMatched(); // Beállítjuk, hogy párosították
            cards.get(index2).setMatched();
        }
        return isMatch;
    }

    // Kártya felfedése
    public void revealCard(int index) {
        cards.get(index).reveal();
    }

    // Kártya elrejtése
    public void hideCard(int index) {
        cards.get(index).hide();
    }

    // Ellenőrzés, hogy véget ért-e a játék
    public boolean isGameOver() {
        for (Card card : cards) {
            if (!card.isMatched()) {
                return false;
            }
        }
        return true;
    }

    // A rácsméret lekérdezése
    public int getGridSize() {
        return gridSize;
    }
}
