/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juang.utils;

/**
 *
 * @author juang
 */
public abstract class Performer{

    public abstract void method();

    public long getNanoseconds() {
        long startTime = System.nanoTime();
        method();
        long endTime = System.nanoTime();
        return (endTime - startTime);
    }
}
