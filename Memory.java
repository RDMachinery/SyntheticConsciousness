
public class Memory {

    byte[] mem;
    Neuron neuron;

    public Memory(int n) {
        mem = new byte[(int)Math.pow(2.0,(double)n)];
        neuron = new Neuron();
    }

    public void setMemory(int address, boolean bit) {
        mem[address] = bit == true ? (byte)1 : (byte)0;
    }

    public byte getMemory(int address) {
        return mem[address];
    }

    public int getMemorySize() {
        return mem.length;
    }

    public Neuron getNeuron() {
        return neuron;
    }

    public void dumpMemory() {
        for(int i=0; i < getMemorySize(); i++) {
            System.out.print(mem[i]);
        }
    }

    public static void main(String[] args) {
        Memory mem = new Memory(8);
        System.out.println("Memory size = " + mem.getMemorySize());
        mem.dumpMemory();
    }

class Neuron {
    public Neuron() {}
    public byte readMem(int address) {
        if( address < 0 || address > getMemorySize() )
            throw new IndexOutOfBoundsException("Address out of range.");

        return mem[address] == 1 ? 1 : 0;        
    }

    public void setMem(boolean bit, int address) {
        if( address < 0 || address > getMemorySize() )
            throw new IndexOutOfBoundsException("Address out of range.");

        mem[address] = (bit == true) ? (byte)1 : (byte)0;
    }
}
}
