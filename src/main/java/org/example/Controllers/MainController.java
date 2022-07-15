package org.example.Controllers;

import org.example.Logg;
import org.example.Storage;
import org.example.repository.Currency;
import org.example.repository.CurrencyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Controller
public class MainController {

    @Autowired
    public CurrencyRepo currencyRepo;


    Logg logger;
    {
        try {
            logger = new Logg(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Storage storage = new Storage(this);
    {storage.start();
     logger.start();}

    @GetMapping("/")
    public String main(@RequestParam(name = "symbol",required = false)String symbol,
                       Model model){
        List<Currency> curList;
        if(symbol!=null){
            curList = new ArrayList<>();
            Currency cur = currencyRepo.findBySymbol(symbol);
            if(cur!=null) curList.add(cur);
        }else {
            curList = (List<Currency>) currencyRepo.findAll();
        }
        model.addAttribute("curList",curList);
        return "main";
    }

    @PostMapping("/reg")
    public String notify(@RequestParam(name = "username")String username,
                         @RequestParam(name = "code")String symbol,
                         Model model){
        Currency cur = currencyRepo.findBySymbol(symbol);
        if(cur!=null) {
            logger.registration(username, symbol, cur.getPrice());
        }
        List<Currency> curList;
        curList = (List<Currency>) currencyRepo.findAll();
        model.addAttribute("curList",curList);
        return "main";
    }
}
