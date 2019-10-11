package chip8.cpu

enum class CpuByteRegister(val registerNumber: Int) {
    V0(0x0),
    V1(0x1),
    V2(0x2),
    V3(0x3),
    V4(0x4),
    V5(0x5),
    V6(0x6),
    V7(0x7),
    V8(0x8),
    V9(0x9),
    VA(0xA),
    VB(0xB),
    VC(0xC),
    VD(0xD),
    VE(0xE),
    VF(0xF);

    companion object {
        val SYMBOL_ARRAY = CpuByteRegister.values().map { it }
    }
}