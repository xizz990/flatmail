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
        String url = ("https://www.gumtree.pl/s-mieszkania-i-domy-do-wynajecia/warszawa/v1c9008l3200008p1?pr=1400,2200&fr=ownr&nr=2");
        List<Ogloszenie> og = getOgloszeniaList(url);
        String url_3pok = ("https://www.gumtree.pl/s-mieszkania-i-domy-do-wynajecia/warszawa/v1c9008l3200008p1?pr=1400,2200&fr=ownr&nr=3");
        og.addAll(getOgloszeniaList(url_3pok));
        for (int i = 2; i < 3; i++) {
            String url_2 = ("https://www.gumtree.pl/s-mieszkania-i-domy-do-wynajecia/warszawa/page-"+i+"/v1c9008l3200008p"+i+"?pr=1400,2200&fr=ownr&nr=2");
            og.addAll(getOgloszeniaList(url_2));
        }
        for (int i = 2; i < 3; i++) {
            String url_2_3pok = ("https://www.gumtree.pl/s-mieszkania-i-domy-do-wynajecia/warszawa/page-"+i+"/v1c9008l3200008p"+i+"?pr=1400,2200&fr=ownr&nr=3");
            og.addAll(getOgloszeniaList(url_2_3pok));
        }

        List<Ogloszenie> ogOchota = filterByDzielnica(og, "Ochota");
        og.removeAll(ogOchota);
        List<Ogloszenie> ogSrodmiescie = filterByDzielnica(og, "Śródmieście");
        og.removeAll(ogSrodmiescie);
        List<Ogloszenie> ogWola = filterByDzielnica(og, "Wola");
        og.removeAll(ogWola);
        List<Ogloszenie> ogZoliborz = filterByDzielnica(og, "Żoliborz");
        og.removeAll(ogZoliborz);
        List<Ogloszenie> ogMokotow = filterByDzielnica(og, "Mokotów");
        og.removeAll(ogMokotow);
        List<Ogloszenie> ogWlochy = filterByDzielnica(og, "Włochy");
        og.removeAll(ogWlochy);
        List<Ogloszenie> ogUrsynow = filterByDzielnica(og, "Ursynów");
        og.removeAll(ogUrsynow);
        List<Ogloszenie> ogPragaP = filterByDzielnica(og, "Praga Południe");
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

        se.sendMail(allMessagesSb.toString(),"Gumtree 2 i 3 pokoje 1400 zł - 2200");

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
    private static List<Ogloszenie> filterByDzielnica(List<Ogloszenie> og, String dzielnica){
        return     og.stream()
                     .filter(o -> dzielnica.equals(o.getDzielnica()))
                     .collect(Collectors.toList());
    }
    public static String makeMessage(List<Ogloszenie> og){
        StringBuilder sb = new StringBuilder();
        int counter = 1;

        try {
            sb.append("========================="+ (og.get(0).getDzielnica().equals("")? "":og.get(0).getDzielnica())+"==============================\n\n\n");
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
