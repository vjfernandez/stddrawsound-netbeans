package org.daw1;
import java.applet.*;
import java.io.*;
import java.net.*;
import javax.sound.sampled.*;
import javax.sound.midi.*;

/**
 * Sonido para juegos simples en clase.<br>
 * Permite reproducir sonido en ficheros de onda o midi, de manera asincrona una sola vez o en loop. <br>
 * Los metodos play() y loop() inician la reproduccion de un sonido en formato .wav o au. stop() para la reproduccion<br>
 * Los metodos playMidi() y loopMidi() inician la reproduccion de un sonido en formato .mid.  stopMidi()
 * para la reproducción del fichero midi<br>
 * 
 * Tambien permite emitir el beep del sistema.
 * 
 * @author Victor
 * @version 1.1
 */

public class StdSound {

    static Sequencer sequencer;

    static javax.sound.sampled.Clip soundLoop=null;
   
    
    
private static InputStream load(String filename)
{
    InputStream r=null;
    
    try
    {
        r=StdSound.class.getResourceAsStream("/"+filename);
    }
    catch (Exception ex)
    {
        //nothing
    }
    try
    {
        if (r==null)
        {
            r=StdSound.class.getResourceAsStream(filename);
        }
    }
    catch (Exception ex)
    {
        //nothing
    }
    try
    {
        if (r==null)
        {
            r = new FileInputStream(filename);
        }
    }
    catch (Exception ex)
    {
        //nothing
    }
    
    return r;

}


    
    /**
     * Un beep simple
     */
    public static void beep()
    {
        java.awt.Toolkit.getDefaultToolkit().beep();
    }
    
    /**
     * Para el loop que esta sonando
     */

    public static void stop() {

            if (soundLoop!=null)
            {
                soundLoop.stop();
            }
    }
    

    /**
     * Reproduce un fichero de sonido (.wav o .au) asincronamente
     * @param filename El nombre del fichero (.wav o .au) compatible con Java.
     */
    public static void play(String filename) {
    javax.sound.sampled.Clip sonido;
    InputStream path=load(filename);
    
        try
        {
            sonido=AudioSystem.getClip();
            sonido.open(AudioSystem.getAudioInputStream(new BufferedInputStream(path)));
            sonido.start();

        }catch(Exception fallo)
        {
            
        }
    }
    /**
     * Reproduce sin parar un fichero de sonido (.wav o .au) asincronamente
     */
    public static void loop(String filename) {

    InputStream path=load(filename);

    
        try
        {
            stop();
            soundLoop=AudioSystem.getClip();
            soundLoop.open(AudioSystem.getAudioInputStream(new BufferedInputStream(path)));
            soundLoop.loop(javax.sound.sampled.Clip.LOOP_CONTINUOUSLY);

        }catch(Exception fallo)
        {
           

        }
        
    }
    /**
     * Reproduce en loop un fichero de sonido midi asincronamente
     */
    public static void loopMidi(String filename)
    {
        try
        {
            sequencer = MidiSystem.getSequencer();
            InputStream midiFile = load(filename);

            sequencer.setSequence(MidiSystem.getSequence(new BufferedInputStream(midiFile)));
            sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
            sequencer.open();
            sequencer.start();
        }
        catch (Exception ex)
        {
            
        }
    }
     /**
     * Reproduce un fichero de sonido midi asincronamente
     */
    public static void playMidi(String filename)
    {
        try
        {
            InputStream midiFile=load(filename);;
            sequencer = MidiSystem.getSequencer();
            sequencer.setSequence(MidiSystem.getSequence(new BufferedInputStream(midiFile)));
            sequencer.open();
            sequencer.start();
        }
        catch (Exception ex)
        {
           
        }
    }
    
    /**
     * Detiene la reproduccion del fichero midi en curso
     */
    public static void stopMidi()
    {
        if (sequencer!=null)
        {
            sequencer.stop();
        }
    }

    
}
