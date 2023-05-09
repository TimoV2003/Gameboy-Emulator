package Gameboy.CPU;

import Gameboy.MemoryMap;

public class Opcodes {
    private Registry registry = new Registry();
    private MemoryMap mmm;

    public Opcodes(MemoryMap memoryMap){
        this.mmm = memoryMap;
    }

    public void executeOpcode(int opcode) {
        switch (opcode) {
            case 0x00:
                break;
            case 0x01: // LD BC, nn
                int nn = mmm.rw(registry.pc());
                registry.pcpp(2);
                registry.bc(nn);
                break;
            case 0x02: // LD (BC), A
                mmm.wb(registry.bc(), registry.a());
                break;
            case 0x03: // INC BC
                registry.bc(registry.bc() + 1);
                break;
            case 0x04: // INC B
                registry.b(registry.b() + 1);
                registry.zero(registry.b() == 0);
                registry.subtract(false);
                registry.halfcarry((registry.b() & 0x0F) == 0x00);
                break;
            case 0x05: // DEC B
                registry.b(registry.b() - 1);
                registry.zero(registry.b() == 0);
                registry.subtract(true);
                registry.halfcarry((registry.b() & 0x0F) == 0x0F);
                break;
            case 0x06: // LD B, n
                registry.b(mmm.rb(registry.pc()));
                registry.pcpp(1);
                break;
            case 0x07: // RLCA
                registry.a((registry.a() << 1) | (registry.a() >> 7));
                registry.zero(false);
                registry.subtract(false);
                registry.halfcarry(false);
                registry.carry((registry.a() & 0x01) == 0x01);
                break;
            case 0x08: // LD (nn), SP
                int nn2 = mmm.rw(registry.pc());
                registry.pcpp(2);
                mmm.ww(nn2, registry.sp());
                break;
            case 0x09: // ADD HL, BC
                registry.hl(registry.hl() + registry.bc());
                break;
            case 0x0A: // LD A, (BC)
                registry.a(mmm.rb(registry.bc()));
                break;
            case 0x0B: // DEC BC
                registry.bc(registry.bc() - 1);
                break;

            default:
                throw new IllegalArgumentException("Unsupported opcode: " + opcode);
        }
    }
}
