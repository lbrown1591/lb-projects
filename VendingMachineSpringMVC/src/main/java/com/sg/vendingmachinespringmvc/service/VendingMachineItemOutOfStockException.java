/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachinespringmvc.service;

/**
 *
 * @author softwareguild
 */
public class VendingMachineItemOutOfStockException extends Exception{
    
    public VendingMachineItemOutOfStockException(String message) {
        super(message);
    }

    public VendingMachineItemOutOfStockException(String message, Throwable cause) {
        super(message, cause);
    }
}
