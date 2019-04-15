public class Letter {
    int freq;
    Character label;

    public Letter(int freq, Character label){
        this.freq = freq;
        this.label = label;
    }
    public void setFreq(int freq) {
        this.freq = freq;
    }

    public int getFreq() {
        return freq;
    }

    public Character getLabel() {
        return label;
    }

    public void setLabel(Character label) {
        this.label = label;
    }

    public void updateFreq() {
        this.freq += 1;
    }


}
