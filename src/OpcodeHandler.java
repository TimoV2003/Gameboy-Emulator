public class OpcodeHandler {
    private int pc;

    public OpcodeHandler(){
        pc = 0;
    }

    public void fetchDecodeExecuteOpcode(byte[] memory) {
        // Fetch opcode from memory
        int bc = 0;
        byte opcode = memory[pc];

        // Decode and execute opcode
        switch (opcode) {
            case 0x00:
                // Opcode 0x00: No operation
                // Do nothing
                break;
            case 0x01:
                // Opcode 0x01: Load 16-bit immediate value into register BC
                byte immediateLo = memory[pc + 1];
                byte immediateHi = memory[pc + 2];
                int immediateValue = ((immediateHi & 0xFF) << 8) | (immediateLo & 0xFF);
                bc = immediateValue;
                break;
            case 0x02:
                // Opcode 0x02: Load contents of register A into memory address pointed by register BC
                int operand1 = bc;
                int operand2 = opcode;
                writeByteToMemory(operand1, operand2, memory);
                break;
            case 0x03:
                // Opcode 0x03: Increment register BC
                bc = (bc + 1) & 0xFFFF;
                break;
            default:
                // Unsupported opcode
                System.out.println("Unsupported opcode: " + Integer.toHexString(opcode & 0xFF));
                break;
        }

        pc = (pc + getOpcodeSize(opcode)) & 0xFFFF;
    }

    private int getOpcodeSize(byte opcode) {
        // Get size of opcode in bytes
        // In this example, assuming all opcodes are 1 byte or 3 bytes in size
        if ((opcode & 0xFF) == 0xCB) {
            // Opcode 0xCB is a prefix opcode, which is followed by another opcode
            return 2;
        } else {
            return 1;
        }
    }

    private void writeByteToMemory(int address, int value, byte[] memory) {
        memory[address] = (byte) value;
    }
}
