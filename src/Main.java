public class Main {
    public static void main(String[] args) {
        GameBoyEmulator emulator = new GameBoyEmulator();
        emulator.loadRomData();
        emulator.setVisible(true);
    }
}