import com.google.api.services.gmail.Gmail;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class main {

    public static void main(String[] args) throws MessagingException {

        ChromeDriverManager.getInstance().setup();
        String url;
        List<Ogloszenie> og;
        String url_3pok;

        url = ("https://www.gumtree.pl/s-mieszkania-i-domy-do-wynajecia/warszawa/v1c9008l3200008p1?pr=1400,2200&fr=ownr&nr=2");
        og = getOgloszeniaList(url);
        url_3pok = ("https://www.gumtree.pl/s-mieszkania-i-domy-do-wynajecia/warszawa/v1c9008l3200008p1?pr=1400,2200&fr=ownr&nr=3");
        og.addAll(getOgloszeniaList(url_3pok));
        for (int i = 2; i < 3; i++) {
            String url_2 = ("https://www.gumtree.pl/s-mieszkania-i-domy-do-wynajecia/warszawa/page-"+i+"/v1c9008l3200008p"+i+"?pr=1400,2200&fr=ownr&nr=2");
            og.addAll(getOgloszeniaList(url_2));
        }
        for (int i = 2; i < 3; i++) {
            String url_2_3pok = ("https://www.gumtree.pl/s-mieszkania-i-domy-do-wynajecia/warszawa/page-"+i+"/v1c9008l3200008p"+i+"?pr=1400,2200&fr=ownr&nr=3");
            og.addAll(getOgloszeniaList(url_2_3pok));
        }

        List<Ogloszenie> ogOchota;
        List<Ogloszenie> ogSrodmiescie;
        List<Ogloszenie> ogWola;
        List<Ogloszenie> ogZoliborz;
        List<Ogloszenie> ogMokotow;
        List<Ogloszenie> ogWlochy;
        List<Ogloszenie> ogUrsynow;
        List<Ogloszenie> ogPragaP;

        ogOchota = filterByDzielnica(og, "Ochota");
        og.removeAll(ogOchota);
        ogSrodmiescie = filterByDzielnica(og, "Śródmieście");
        og.removeAll(ogSrodmiescie);
        ogWola = filterByDzielnica(og, "Wola");
        og.removeAll(ogWola);
        ogZoliborz = filterByDzielnica(og, "Żoliborz");
        og.removeAll(ogZoliborz);
        ogMokotow = filterByDzielnica(og, "Mokotów");
        og.removeAll(ogMokotow);
        ogWlochy = filterByDzielnica(og, "Włochy");
        og.removeAll(ogWlochy);
        ogUrsynow = filterByDzielnica(og, "Ursynów");
        og.removeAll(ogUrsynow);
        ogPragaP = filterByDzielnica(og, "Praga Południe");
        og.removeAll(ogPragaP);

        SendEmail se = new SendEmail();

        StringBuilder allMessagesSb = new StringBuilder();
        allMessagesSb.append(makeMessage(ogOchota));
        allMessagesSb.append(makeMessage(ogSrodmiescie));
        allMessagesSb.append(makeMessage(ogWola));
        allMessagesSb.append(makeMessage(ogZoliborz));
        allMessagesSb.append(makeMessage(ogMokotow));
        allMessagesSb.append(makeMessage(ogWlochy));
        allMessagesSb.append(makeMessage(ogUrsynow));
        allMessagesSb.append(makeMessage(ogPragaP));

        se.sendMail(allMessagesSb.toString(),"Gumtree 2 i 3 pokoje 1400 zł - 2200", "wizz990@gmail.com,krysia.matyjasek@gmail.com");
//        se.sendMail(allMessagesSb.toString(),"Gumtree 2 i 3 pokoje 1400 zł - 2200", "wizz990@gmail.com");

        //-----------------------OLX-----------------------\\

        url = ("https://www.olx.pl/nieruchomosci/mieszkania/warszawa/?search%5Bfilter_float_price%3Afrom%5D=1400&search%5Bfilter_float_price%3Ato%5D=2200&search%5Bfilter_float_m%3Afrom%5D=34&search%5Bfilter_enum_rooms%5D%5B0%5D=two&search%5Bfilter_enum_rooms%5D%5B1%5D=three&search%5Bfilter_enum_rooms%5D%5B2%5D=four&search%5Bprivate_business%5D=private");
        og = getOgloszeniaListOLX(url);
        for (int i = 2; i < 4; i++) {
            String url_2 = ("https://www.olx.pl/nieruchomosci/mieszkania/warszawa/?search%5Bfilter_float_price%3Afrom%5D=1400&search%5Bfilter_float_price%3Ato%5D=2200&search%5Bfilter_float_m%3Afrom%5D=34&search%5Bfilter_enum_rooms%5D%5B0%5D=four&search%5Bfilter_enum_rooms%5D%5B1%5D=three&search%5Bfilter_enum_rooms%5D%5B2%5D=two&search%5Bprivate_business%5D=private&page="+i);
            og.addAll(getOgloszeniaListOLX(url_2));
        }

        ogOchota = filterByDzielnica(og, "Ochota");
        og.removeAll(ogOchota);
        ogSrodmiescie = filterByDzielnica(og, "Śródmieście");
        og.removeAll(ogSrodmiescie);
        ogWola = filterByDzielnica(og, "Wola");
        og.removeAll(ogWola);
        ogZoliborz = filterByDzielnica(og, "Żoliborz");
        og.removeAll(ogZoliborz);
        ogMokotow = filterByDzielnica(og, "Mokotów");
        og.removeAll(ogMokotow);
        ogWlochy = filterByDzielnica(og, "Włochy");
        og.removeAll(ogWlochy);
        ogUrsynow = filterByDzielnica(og, "Ursynów");
        og.removeAll(ogUrsynow);
        ogPragaP = filterByDzielnica(og, "Praga-Południe");
        og.removeAll(ogPragaP);

        allMessagesSb = new StringBuilder();
        allMessagesSb.append(makeMessage(ogOchota));
        allMessagesSb.append(makeMessage(ogSrodmiescie));
        allMessagesSb.append(makeMessage(ogWola));
        allMessagesSb.append(makeMessage(ogZoliborz));
        allMessagesSb.append(makeMessage(ogMokotow));
        allMessagesSb.append(makeMessage(ogWlochy));
        allMessagesSb.append(makeMessage(ogUrsynow));
        allMessagesSb.append(makeMessage(ogPragaP));

        se.sendMail(allMessagesSb.toString(),"OLX 2 i 3 pokoje 1400 zł - 2200", "wizz990@gmail.com,krysia.matyjasek@gmail.com");
//        se.sendMail(allMessagesSb.toString(),"OLX 2 i 3 pokoje 1400 zł - 2200", "wizz990@gmail.com");

    }

    private static List<Ogloszenie> getOgloszeniaList(String url){
        List<Ogloszenie> resLinks = new ArrayList<Ogloszenie>();

        WebDriver driver = new ChromeDriver();
        driver.get(url);

        for (int i = 1; i <=  20 ; i++) {

            String tytul = null;
            try {
                tytul = ( driver.findElement(By.xpath("//li["+i+"]/div[@class='result-link  ' and 1]/div[@class='container' and 2]/div[@class='title' and 1]/a[@class='href-link' and 1]")).getText());
            } catch (Exception e) {
            }
            String tresc = null;
            try {
                tresc = ( driver.findElement(By.xpath("//li["+i+"]/div[@class='result-link  ' and 1]/div[@class='container' and 2]/div[@class='description' and 2]/span[1]")).getText());
            } catch (Exception e) {
            }
            String link = null;
            try {
                link = ( driver.findElement(By.xpath("//li["+i+"]/div[@class='result-link  ' and 1]/div[@class='container' and 2]/div[@class='title' and 1]/a[@class='href-link' and 1]")).getAttribute("href"));
            } catch (Exception e) {
            }
            String dzielnica = null;
            try {
                dzielnica = ( driver.findElement(By.xpath("//li["+i+"]/div[@class='result-link  ' and 1]/div[@class='container' and 2]/div[@class='category-location' and 5]/span[1]")).getText());
                dzielnica = dzielnica.substring(dzielnica.lastIndexOf(",") + 2);
            } catch (Exception e) {
            }

            String cena = null;
            try {
                cena = ( driver.findElement(By.xpath("//li["+i+"]/div[@class='result-link  ' and 1]/div[@class='container' and 2]/div[@class='info' and 4]/div[@class='price' and 1]/span[@class='value' and 1]/span[@class='amount' and 1]")).getText());
            } catch (Exception e) {
            }

            String data = null;
            try {
                data = ( driver.findElement(By.xpath("//li["+i+"]/div[@class='result-link  ' and 1]/div[@class='container' and 2]/div[@class='info' and 4]/div[@class='creation-date' and 2]/span[2]")).getText());
            } catch (Exception e) {
            }

            Ogloszenie og = new Ogloszenie(tytul==null || tytul==""?"---":tytul,tresc==null || tytul==""?"---":tresc,link==null || tytul==""?"---":link,
                    dzielnica==null || tytul==""?"---":dzielnica,cena==null || tytul==""?"---":cena,data==null || tytul==""?"---":data);
            resLinks.add(og);
        }

        return resLinks;
    }
    private static List<Ogloszenie> getOgloszeniaListOLX(String url){
        List<Ogloszenie> resLinks = new ArrayList<Ogloszenie>();

        WebDriver driver = new ChromeDriver();
        driver.get(url);

        for (int i = 3; i <=  39 ; i++) {

            String tytul = null;
            String tresc = null;
            String link = null;
            String dzielnica = null;
            String cena = null;
            String data = null;
            try {
                tytul = ( driver.findElement(By.xpath("//div/table[@id='offers_table']/tbody/tr["+i+"]/td[@class='offer promoted ' or 'offer ']/div[@class='offer-wrapper']//*/a/strong[1]")).getText());
            } catch (Exception e) {
            }
            try {
//                tresc = ( driver.findElement(By.xpath("")).getText());
            } catch (Exception e) {
            }
            try {
                link = ( driver.findElement(By.xpath("//div/table[@id='offers_table']/tbody/tr["+i+"]/td[@class='offer promoted ' or 'offer ']/div[@class='offer-wrapper']//*/a")).getAttribute("href"));
            } catch (Exception e) {
            }
            try {
                dzielnica = ( driver.findElement(By.xpath("(//div/table[@id='offers_table']/tbody/tr["+i+"]/td[@class='offer promoted ' or 'offer ']/div[@class='offer-wrapper']//*/small[@class='breadcrumb x-normal']/span)[1]")).getText());
                dzielnica = dzielnica.substring(dzielnica.lastIndexOf(",") + 2);
            } catch (Exception e) {
            }

            try {
                data = ( driver.findElement(By.xpath("(//div/table[@id='offers_table']/tbody/tr["+i+"]/td[@class='offer promoted ' or 'offer ']/div[@class='offer-wrapper']//*/small[@class='breadcrumb x-normal']/span)[2]")).getText());
            } catch (Exception e) {
            }

            try {
                cena = ( driver.findElement(By.xpath("//div/table[@id='offers_table']/tbody/tr["+i+"]/td[@class='offer promoted ' or 'offer ']/div[@class='offer-wrapper']//*/p[@class='price']/strong[1]")).getText());
            } catch (Exception e) {
            }

            Ogloszenie og = new Ogloszenie(tytul==null || tytul==""?"---":tytul,tresc==null || tytul==""?"---":tresc,link==null || tytul==""?"---":link,
                    dzielnica==null || tytul==""?"---":dzielnica,cena==null || tytul==""?"---":cena,data==null || tytul==""?"---":data);
            resLinks.add(og);
        }

        return resLinks;
    }
    private static List<Ogloszenie> filterByDzielnica(List<Ogloszenie> og, String dzielnica){
        return     og.stream()
                     .filter(o -> dzielnica.equals(o.getDzielnica()))
                     .collect(Collectors.toList());
    }
    public static String makeMessage(List<Ogloszenie> og){
        StringBuilder sb = new StringBuilder();
        int counter = 1;

        try {
            sb.append("==="+ (og.get(0).getDzielnica().equals("")? "":og.get(0).getDzielnica())+"===\n\n\n");
            for (Ogloszenie ogloszenie : og) {
                sb.append(counter++ + ".\n");
                sb.append(ogloszenie.getLink()+"\n");
                sb.append(ogloszenie.getTytul()+"\n");
                sb.append(ogloszenie.getCena()+"\n");
                sb.append(ogloszenie.getData()+"\n");
                sb.append(ogloszenie.getTresc()+"\n\n\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }



}
