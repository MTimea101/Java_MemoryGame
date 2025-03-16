public class Card {
    private String value;
    private boolean isRevealed;
    private boolean isMatched;

    public Card(String value) {
        this.value = value;
        this.isRevealed = false;
        this.isMatched = false;
    }

    public String getValue() {
        return value;
    }

    public void reveal() {
        if (!isMatched) {
            isRevealed = true;
        }
    }

    public void hide() {
        if (!isMatched) {
            isRevealed = false;
        }
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public void setMatched() {
        isMatched = true;
    }

    public boolean isMatched() {
        return isMatched;
    }
}
