/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachinespringmvc.dto;

/**
 *
 * @author softwareguild
 */
public class Change {
    private int numOfPennies;
    private int numOfNickels;
    private int numOfDimes;
    private int numOfQuarters;

    public Change(int numOfPennies){
       int quarters = 0;
       int dimes = 0;
       int nickels = 0;
       
       while(numOfPennies>=25){
           numOfPennies = numOfPennies - 25;
           quarters++;
       }
       while(numOfPennies>=10){
           numOfPennies = numOfPennies - 10;
           dimes++;
       }
       while(numOfPennies>=5){
           numOfPennies = numOfPennies - 5;
           nickels++;
       }
       
       this.numOfQuarters = quarters;
       this.numOfDimes = dimes;
       this.numOfNickels = nickels;
       this.numOfPennies = numOfPennies;
       
    }
    
    @Override
    public String toString() {
        String changeDue = "";
        int quarters = getNumOfQuarters();
        int dimes = getNumOfDimes();
        int nickels = getNumOfNickels();
        int pennies = getNumOfPennies();

        if (quarters != 0) {
            changeDue += quarters;

            if (quarters == 1) {
                changeDue += " Quarter ";
            } else {
                changeDue += " Quarters ";
            }

        }
        if (dimes != 0) {
            changeDue += dimes;
            if (dimes == 1) {
                changeDue += " Dime ";
            } else {
                changeDue += " Dimes ";
            }

        }
        if (nickels != 0) {
            changeDue += nickels;
            if (nickels == 1) {
                changeDue += " Nickel ";
            } else {
                changeDue += " Nickels ";
            }
        }
        if (pennies != 0) {
            changeDue += pennies;
            if (pennies == 1) {
                changeDue += " Penny";
            } else {
                changeDue += " Pennies";
            }
        }
        if (changeDue.equals("")) {
            changeDue = "No Change Due";
        }

        return changeDue;
    }
    
    

    public int getNumOfPennies() {
        return numOfPennies;
    }

    public void setNumOfPennies(int numOfPennies) {
        this.numOfPennies = numOfPennies;
    }

    public int getNumOfNickels() {
        return numOfNickels;
    }

    public void setNumOfNickels(int numOfNickels) {
        this.numOfNickels = numOfNickels;
    }

    public int getNumOfDimes() {
        return numOfDimes;
    }

    public void setNumOfDimes(int numOfDimes) {
        this.numOfDimes = numOfDimes;
    }

    public int getNumOfQuarters() {
        return numOfQuarters;
    }

    public void setNumOfQuarters(int numOfQuarters) {
        this.numOfQuarters = numOfQuarters;
    }
    
    
}
