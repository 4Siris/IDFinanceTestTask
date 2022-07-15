package org.example;

import org.example.Controllers.MainController;
import org.example.repository.Currency;
import org.example.repository.CurrencyRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class Storage extends Thread{

    MainController mainController;

    public Storage(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void run() {
        do {
            try {
                sleep(5000);
                download();
                sleep(55000);
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }while (true);
    }

    private void download() throws IOException {
        String url = "https://api.coinlore.net/api/ticker/?id=90,80,48543";
        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
        connection.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String stringLine;
        StringBuffer rez = new StringBuffer();
        while ((stringLine = in.readLine())!=null){
            rez.append(stringLine);
        }
        in.close();
        stringLine = rez.toString().replaceAll("[{}]","");
        stringLine = stringLine.substring(1,stringLine.length()-1);
        String[] rezAtr = stringLine.split(",");
        for(int i=0;i<3;i++){
            Currency newCur = new Currency();
            newCur.setCid(Integer.parseInt(rezAtr[i*16].split(":")[1].replace("\"","")));
            newCur.setSymbol(rezAtr[i*16+1].split(":")[1].replace("\"",""));
            newCur.setPrice(Double.parseDouble(rezAtr[i*16+5].split(":")[1].replace("\"","")));
            Currency oldCur = mainController.currencyRepo.findBySymbol(newCur.getSymbol());
            if(oldCur!=null){
                mainController.currencyRepo.deleteById(oldCur.getId());
                newCur.setId(oldCur.getId());
                mainController.currencyRepo.save(newCur);
            }else {
                mainController.currencyRepo.save(newCur);
            }
        }
    }
}
