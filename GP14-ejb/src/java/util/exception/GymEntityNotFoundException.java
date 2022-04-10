/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author elgin
 */
public class GymEntityNotFoundException extends Exception {
    public GymEntityNotFoundException() {
    }

    public GymEntityNotFoundException(String msg) {
        super(msg);
    }
}
