package Opcodes;

public class OpcodeHandler {
    private final Flags flags = new Flags();
    private final Stop stop = new Stop();

    //bytes
    private byte opcode;
    //ints
    private int pc = 0;
    private int carry = 0;
    private int oldCarry = 0;
    private int A = 0;
    private int a16 = 0;
    private int B = 0;
    private int BC = 0;
    private int C = 0;
    private int D = 0;
    private int d8 = 0;
    private int d16 = 0;
    private int DE = 0;
    private int E = 0;
    private int F = 0;
    private int HL = 0;
    private int s8 = 0;
    private int SP = 0;

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
                writeByteToMemory(BC, immediateValue, memory);
                System.out.println("yes " + opcode);
                break;
            case 0x02:
                // Opcode 0x02: Load contents of register A into memory address pointed by register BC
                writeByteToMemory(BC, A, memory);
                System.out.println("yes " + opcode);
                break;
            case 0x03:
                // Opcode 0x03: Increment register BC
                BC = (BC + 1) & 0xFFFF;
                System.out.println("yes " + opcode);
                break;
            case 0x04:
                B = (B + 1) & 0xFF;
                flags.setNFlag(1);
                System.out.println("yes " + opcode);
                break;
            case 0x05:
                B = (B - 1) & 0xFF;
                flags.setNFlag(0);
                System.out.println("yes " + opcode);
                break;
            case 0x06:
                writeByteToMemory(B, d8, memory);
                System.out.println("yes " + opcode);
                break;
            case 0x07:
                // Opcode 0x07: Rotate register A left through carry
                carry = (A & 0x80) >> 7;
                A = ((A << 1) | carry) & 0xFF;
                F = carry << 4; // set carry flag to value of old bit 7
                flags.setCFlag(F);
                System.out.println("yes " + opcode);
                break;
            case 0x08:
                writeByteToMemory(a16, SP & 0xFF, memory); // Write the low byte of SP to memory at a16
                writeByteToMemory(a16 + 1, (SP >> 8) & 0xFF, memory); // Write the high byte of SP to memory at a16 + 1
                System.out.println("yes " + opcode);
                break;
            case 0x09:
                BC += HL & 0xF;
                flags.setNFlag(0);
                flags.setHFlag(1);
                System.out.println("yes " + opcode);
                break;
            case 0x0A:
                writeByteToMemory(A, BC, memory);
                System.out.println("yes " + opcode);
                break;
            case 0x0B:
                BC = (BC - 1) & 0xFFFF;
                System.out.println("yes " + opcode);
                break;
            case 0x0C:
                C = (C + 1) & 0xFF;
                flags.setNFlag(1);
                System.out.println("yes " + opcode);
                break;
            case 0x0D:
                C = (C - 1) & 0xFF;
                flags.setNFlag(0);
                System.out.println("yes " + opcode);
                break;
            case 0x0E:
                writeByteToMemory(C, d8, memory);
                System.out.println("yes " + opcode);
                break;
            case 0x0F:
            case 0x1F:
                carry = A & 0x01;
                A = (A >> 1) | (carry << 7);
                flags.setZFlag(0);
                flags.setNFlag(0);
                flags.setHFlag(0);
                flags.setCFlag(carry);
                System.out.println("yes " + opcode);
                break;
            case 0x10:
                stop.stop();
                System.out.println("Stopped" + opcode);
                break;
            case 0x11:
                writeByteToMemory(DE, d16, memory);
                System.out.println("yes " + opcode);
                break;
            case 0x12:
                writeByteToMemory(DE, A, memory);
                System.out.println("yes " + opcode);
                break;
            case 0x13:
                DE = (DE + 1) & 0xFFFF;
                System.out.println("yes " + opcode);
                break;
            case 0x14:
                D = (D + 1) & 0xFF;
                flags.setNFlag(1);
                System.out.println("yes " + opcode);
                break;
            case 0x15:
                D = (D - 1) & 0xFF;
                flags.setNFlag(0);
                System.out.println("yes " + opcode);
                break;
            case 0x16:
                writeByteToMemory(D, d8, memory);
                System.out.println("yes " + opcode);
                break;
            case 0x17:
                oldCarry = flags.getCFlag();
                carry = (A & 0x80);
                A = (A << 1) & 0xFF | (oldCarry);
                flags.setCFlag(carry);
                flags.setHFlag(0);
                flags.setZFlag(0);
                flags.setNFlag(0);
                System.out.println("yes " + opcode);
                break;
            case 0x18:
                s8 = readByteFromMemory(pc, memory);
                pc += s8;
                System.out.println("yes " + opcode);
                break;
            case 0x19:
                HL += DE & 0xF;
                flags.setNFlag(0);
                flags.setHFlag(1);
                System.out.println("yes " + opcode);
                break;
            case 0x1A:
                writeByteToMemory(A, DE, memory);
                System.out.println("yes " + opcode);
                break;
            case 0x1B:
                DE = (DE - 1) & 0xFFFF;
                System.out.println("yes " + opcode);
                break;
            case 0x1C:
                E = (E + 1) & 0xFF;
                flags.setNFlag(1);
                System.out.println("yes " + opcode);
                break;
            case 0x1D:
                E = (E - 1) & 0xFF;
                flags.setNFlag(0);
                System.out.println("yes " + opcode);
                break;
            case 0x1E:
                writeByteToMemory(E, d8, memory);
                System.out.println("yes " + opcode);
                break;
            case 0x20:

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

    private int readByteFromMemory(int pc, byte[] memory){
        System.out.println("Read from memory!");
        return memory[pc];
    }
}
