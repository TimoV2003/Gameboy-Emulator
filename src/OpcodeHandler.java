public class OpcodeHandler {
    private final Flags flags = new Flags();

    //bytes
    private byte opcode;
    private byte d8 = 0;
    //ints
    private int pc = 0;
    private int operand1 = 0;
    private int operand2 = 0;
    private int a = 0;
    private int a16 = 0;
    private int b = 0;
    private int bc = 0;
    private int f = 0;
    private int hl = 0;
    private int sp = 0;

    public OpcodeHandler(){
    }

    public void fetchDecodeExecuteOpcode(byte[] memory) {
        // Fetch opcode from memory
        opcode = memory[pc];

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
                writeByteToMemory(bc, immediateValue, memory);
                System.out.println("yes " + opcode);
                break;
            case 0x02:
                // Opcode 0x02: Load contents of register A into memory address pointed by register BC
                operand1 = bc;
                operand2 = opcode;
                writeByteToMemory(operand1, operand2, memory);
                System.out.println("yes " + opcode);
                break;
            case 0x03:
                // Opcode 0x03: Increment register BC
                bc = (bc + 1) & 0xFFFF;
                System.out.println("yes " + opcode);
                break;
            case 0x04:
                b = (b + 1) & 0xFF;
                flags.setNFlag(1);
                System.out.println("yes " + opcode);
                break;
            case 0x05:
                b = (b - 1) & 0xFF;
                flags.setNFlag(0);
                System.out.println("yes " + opcode);
                break;
            case 0x06:
                operand1 = b;
                operand2 = d8;
                writeByteToMemory(operand1, operand2, memory);
                System.out.println("yes " + opcode);
                break;
            case 0x07:
                // Opcode 0x07: Rotate register A left through carry
                int carry = (a & 0x80) >> 7;
                a = ((a << 1) | carry) & 0xFF;
                f = carry << 4; // set carry flag to value of old bit 7
                flags.setCFlag(f);
                System.out.println("yes " + opcode);
                break;
            case 0x08:
                writeByteToMemory(a16, sp & 0xFF, memory); // Write the low byte of SP to memory at a16
                writeByteToMemory(a16 + 1, (sp >> 8) & 0xFF, memory); // Write the high byte of SP to memory at a16 + 1
                System.out.println("yes " + opcode);
                break;
            case 0x09:
                bc += hl & 0xF;
                flags.setNFlag(0);
                flags.setHFlag(1);
                System.out.println("yes " + opcode);
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
        System.out.println("Wrote to memory!");
    }
}
