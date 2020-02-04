package com.smuniov.restaurantServiceSystem;

public class Test {
    public static void main(String[] args) {
        Wind flute = new Wind();
        Music.tune(flute);
    }
}

enum Note{
    NOTE_ENOTE, NOTE_KOYOTE;
}

class Instrument{
    public void play(Note n){
        System.out.println("Instr play");
    }
}

class Wind extends Instrument{
    public void play(Note n){
        System.out.println("Instr play" + n);
    }
}

class Music{
    public static void tune(Instrument i){
        i.play(Note.NOTE_ENOTE);
    }
}
