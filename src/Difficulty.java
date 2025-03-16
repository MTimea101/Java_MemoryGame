public enum Difficulty {
    EASY(4),
    MEDIUM(6),
    HARD(8);

    private final int gridSize;

    Difficulty(int gridSize) {
        this.gridSize = gridSize;
    }

    public int getGridSize() {
        return gridSize;
    }
}
