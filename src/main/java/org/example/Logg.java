package org.example;

import org.example.Controllers.MainController;
import org.example.repository.Currency;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Logg extends Thread{
    Logger logger = Logger.getLogger(Logg.class.getName());
    MainController controller;
    ArrayList<Log> logs = new ArrayList<>();

    public Logg(MainController controller) throws IOException {
        this.controller = controller;
        logger.setLevel(Level.WARNING);
    }

    public void registration (String username, String symbol,double price){
        Log log = new Log(username,symbol,price);
        boolean isNew = true;
        for (int i = 0;i<logs.size();i++) {
            if(logs.get(i).getUsername().equals(username)){
                logs.set(i,log);
                isNew=false;
            }
        }
        if(isNew){
            logs.add(log);
        }
    }

    @Override
    public void run() {
        do {
            try {
                sleep(30000);
                for(Log log:logs){
                    double dif;
                    Currency cur = controller.currencyRepo.findBySymbol(log.getSymbol());
                    if(cur!=null){
                        dif = Math.abs(cur.getPrice()-log.getPrice());
                        if(dif>log.getPrice()/100){
                            logger.warning(log.getUsername()+" "+log.getSymbol()+" "+(dif/log.getPrice()*100)+"%");
                        }
                    }else {
                        logger.warning("No such symbol in DB for "+log.getUsername());
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }while (true);
    }

}
