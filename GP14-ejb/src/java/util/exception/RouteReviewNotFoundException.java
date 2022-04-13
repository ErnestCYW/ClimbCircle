/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author ernestcyw
 */
public class RouteReviewNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>RouteNotFoundException</code> without
     * detail message.
     */
    public RouteReviewNotFoundException() {
    }

    /**
     * Constructs an instance of <code>RouteNotFoundException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public RouteReviewNotFoundException(String msg) {
        super(msg);
    }
}