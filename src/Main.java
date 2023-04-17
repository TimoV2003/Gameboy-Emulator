public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java GameBoyEmulator <ROM file path>");
            System.exit(1);
        }

        String romFilePath = args[0];
        GameBoyEmulator emulator = new GameBoyEmulator();
        emulator.loadRomData(romFilePath);
        emulator.setVisible(true);
    }
}