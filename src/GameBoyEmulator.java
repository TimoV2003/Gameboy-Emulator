import Gameboy.serial.Memory;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.IOException;

public class GameBoyEmulator extends JFrame {

    private static final int SCREEN_WIDTH = 160;
    private static final int SCREEN_HEIGHT = 144;
    private static final int SCALE_FACTOR = 3;

    private Memory memory;


    public GameBoyEmulator() {
        setTitle("Game Boy Emulator");
        setSize(SCREEN_WIDTH * SCALE_FACTOR, SCREEN_HEIGHT * SCALE_FACTOR);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setFocusable(true);

        InputHandler inputHandler = new InputHandler();
        addKeyListener(inputHandler);
        addMouseListener(inputHandler);
        setFocusable(true);
        requestFocus();

         memory = new Memory(65536); // 64KB of memory for Game Boy

        // Initialize the memory with appropriate values
        for (int i = 0; i < 256; i++) {
            memory.writeByte(i, (byte) i);
        }

        memory.setMemory();

        // Load the ROM data into memory
        loadRomData();

        // Start the emulation loop
        startEmulationLoop();
    }

    private void startEmulationLoop() {
        CreateGraphics createGraphics = new CreateGraphics();
        // Create a JPanel to represent the screen
        JPanel screenPanel = new JPanel();
        screenPanel.paintComponents(createGraphics.getGraphics());

        // Add the screen panel to the JFrame
        add(screenPanel);

        // Ensure the JFrame is visible
        setVisible(true);


        //opcodeHandler = new OpcodeHandler();

        while (true) {
            // Decode and execute the opcode


            // Update the screen by calling repaint() on the screen panel
            screenPanel.repaint();

            // Update input

            // Update timers

            // Sleep for appropriate time to achieve desired frame rate, in this case around 60 fps
            try {
                Thread.sleep(16);
            }catch (InterruptedException ex){
                ex.printStackTrace();
            }
        }
    }

    public void loadRomData() {
        try {
            FileInputStream fileInputStream = new FileInputStream("resources/Pokemon - Red Version (USA, Europe) (SGB Enhanced).gb");
            int bytesRead;
            int offset = 0;
            while ((bytesRead = fileInputStream.read(new byte[65536], offset, 65536 - offset)) > 0) {
                offset += bytesRead;
            }
            fileInputStream.close();
            System.out.println("ROM data loaded successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}