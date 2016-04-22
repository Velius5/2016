/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.piotr;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import net.sourceforge.tess4j.*;
import org.apache.commons.lang3.StringUtils;

/**
 * Główna klasa modułu aplikacji odpowiedzialnego za rozpoznawanie tekstu.
 * Pozwala na użycie silnika Tesseract OCR.
 * @author Piotr
 */
public class TessOCR {
    private static ArrayList<String> shopHeaderList;
    private static ITesseract ocr;
    
    /**
     *  Funkcja inicjująca tablicę z nazwami sklepów oraz ustawiająca niektóre
     *  właściwości silnika OCR
     */
    public static void init(){
        ocr = new Tesseract();
        ocr.setLanguage("pol");
        shopHeaderList = new ArrayList<>();
        shopHeaderList.add("BIEDRONKA \"CODZIENNIE NISKIE CENY\"");
        shopHeaderList.add("LIDL POLSKA SKLEPY SPOZYWCZE");
        shopHeaderList.add("TESCO /POLSKA/ SP Z.O.O");
        shopHeaderList.add("SKLEP ZABKA");
    }
    

    /**
     * Funkcja służąca do rozpoznawania tekstu zawartego na zdjęciu paragonu.
     * Funkcja rozpoznaje z jakiego sklepu pochodzi paragon i zwraca obiekt
     * odpowiedniej klasy.
     * @param image zdjęcie paragonu w formacie JPG
     * @return obiekt klasy implementującej klase abstrakcyjną pl.piotr.Receipt
     */
    public static Receipt recognizeReceipt(byte[] image){
        int minEditLength = 100;
        Receipt receipt = null;
        try {
            InputStream in = new ByteArrayInputStream(image);
            BufferedImage img = ImageIO.read(in);
            ocr.setLanguage("pol");
            String text = ocr.doOCR(img).toUpperCase();
            System.out.println(text);
            Scanner scaner = new Scanner(text);
            String line = scaner.nextLine();
            
            int tmp=0;
            int LD;
            for(int i=0;i<shopHeaderList.size();i++){
                LD = StringUtils.getLevenshteinDistance(line, shopHeaderList.get(i));
                if(LD < minEditLength){
                    minEditLength = LD;
                    tmp = i;
                }
                //System.out.println(LD);
            }
            //System.out.println(tmp);
            switch(tmp){
                case 0:
                    receipt = new Biedronka();
                    break;
                case 1:
                    receipt = new Lidl();
                    break;
                case 2:
                    receipt = new Tesco();
                    break;
                case 3:
                    receipt = new Zabka();
                    break;
            }
            
            receipt.setDate(text);
            receipt.setProductList(text);
            receipt.setSum(text);
            
            
        } catch (TesseractException ex) {
            Logger.getLogger(TessOCR.class.getName()).log(Level.SEVERE, null, ex);
        }catch(IOException e){
            e.printStackTrace();
        }
        return receipt;
    }
    /*
    public static void main(String[] args){
        TessOCR.init();
        File img = new File("bieda0.tif");
        Receipt recognizeReceipt = TessOCR.recognizeReceipt(img);
    }
    */

}
