/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachinespringmvc.controller;

import com.sg.vendingmachinespringmvc.model.Item;
import com.sg.vendingmachinespringmvc.dto.Change;
import com.sg.vendingmachinespringmvc.service.VendingMachineInsufficientFundsException;
import com.sg.vendingmachinespringmvc.service.VendingMachineItemOutOfStockException;
import com.sg.vendingmachinespringmvc.service.VendingMachineServiceLayer;
import java.math.BigDecimal;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author softwareguild
 */
@Controller
public class VendingMachineController {

    VendingMachineServiceLayer service;

    @Inject
    public VendingMachineController(VendingMachineServiceLayer service) {
        this.service = service;
    }

    @RequestMapping(value = {"/displayVendingMachine", "/", "/index"}, method = RequestMethod.GET)
    public String displayVendingMachine(Model model) {

        List<Item> itemList = service.readAllItems();
        String message;
        int itemSelected;
        String blank = "";
        model.addAttribute("itemList", itemList);

        BigDecimal balance = service.findBalance().setScale(2);
        model.addAttribute("balance", balance);

        itemSelected = service.getItemSelection();
        model.addAttribute("itemSelected", itemSelected == 0 ? blank : itemSelected);

        message = service.getMessage();
        model.addAttribute("message", message == null ? blank : message);
        
        boolean changeNeeded = service.checkChangeNeeded();
        
        if(changeNeeded){
        BigDecimal lastBalance = service.getPastBalance();
        Change change = service.calculateChange(lastBalance);
        model.addAttribute("changeDue", change);
        service.clearItemSelection();
        }
        
        return "vendingMachine";
    }

    @RequestMapping(value = "/addFunds", method = RequestMethod.GET)
    public String addFunds(HttpServletRequest request, Model model) {

        String money = request.getParameter("money");
        BigDecimal moneyBD = new BigDecimal(money);
        service.insertFunds(moneyBD);
        BigDecimal balance = service.findBalance();
        model.addAttribute("balance", balance);
        service.verifyNotPurchaseAttempt();

        return "redirect:displayVendingMachine";
    }

    @RequestMapping(value = "/selectItem", method = RequestMethod.GET)
    public String selectItem(HttpServletRequest request, Model model) {

        String stringItemKey = request.getParameter("itemKey");
        int itemKey = Integer.parseInt(stringItemKey);
        service.selectItem(itemKey);
        service.verifyNotPurchaseAttempt();

        return "redirect:displayVendingMachine";
    }

    @RequestMapping(value = "/purchaseItem", method = RequestMethod.GET)
    public String purchaseItem(Model model) {

        try{
        BigDecimal remainingBalance = service.buyItem();
        service.savePastBalance(remainingBalance);
        service.alertChangeNeeded();
        }catch(VendingMachineItemOutOfStockException | VendingMachineInsufficientFundsException e){
            String errorMessage = e.getMessage();
            service.setMessage(errorMessage);
        }

        return "redirect:displayVendingMachine";
    }
    
    @RequestMapping(value = "/getChange", method = RequestMethod.GET)
    public String getChange(Model model) {

       service.verifyNotPurchaseAttempt();
       BigDecimal balance = service.findBalance();
       service.savePastBalance(balance);
       service.alertChangeNeeded();
        return "redirect:displayVendingMachine";
    }


}
