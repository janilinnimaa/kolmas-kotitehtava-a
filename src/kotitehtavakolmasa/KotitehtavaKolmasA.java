package kotitehtavakolmasa;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Scanner;

// Kotitehtävä 3, HashMap-versio
public class KotitehtavaKolmasA {

    public static void main(String[] args) throws IOException {
        
        /*
        alkuperäiset arrayt
        String[] suomi = {"kissa", "koira", "aave", "rosvo"};
        String[] englanti = {"cat", "dog", "ghost", "thief"};
        */
        
        int i = 0;
        String syote;
        Scanner lukija = new Scanner(System.in);
        
        // Deserialisoidaan tallennettu HashMap
        HashMap<String, String> rakenne = null;
        
        try {
            FileInputStream fis = new FileInputStream("hashmap.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            rakenne = (HashMap) ois.readObject();
            ois.close();
            fis.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            System.out.println("Luokkaa ei löydy!");
            c.printStackTrace();
            return;
        }
        
        
        /* alkuperäisten listojen vieminen HashMappiin
        
        while (i < suomi.length) {
            rakenne.put(suomi[i], englanti[i]);
            i++;
        }
        */
        
        /* 
        Kysytään käyttäjältä uusia sanoja sanakirjaan.
        Huom: uuden sanan lisääminen vaatii sekä alkukielen sanan että käännöksen,
        jos jompikumpi puuttuu, sanaa ei lisätä.
         */
        while (true) {
            System.out.println("Sana alkukielellä? (tyhjä lopettaa)");

            syote = lukija.nextLine();

            if (syote.isEmpty()) {
                break;
            } else {
                String avain = syote;
                System.out.println("Sana käännettynä? (tyhjä lopettaa)");
                syote = lukija.nextLine();

                if (syote.isEmpty()) {
                    break;
                } else {
                    String arvo = syote;
                    rakenne.put(avain, arvo);
                }
            }
        }
        
        System.out.println("Ladataan sanakirjan sisältö...");

        Iterator<Entry<String, String>> it = rakenne.entrySet().iterator();

        System.out.print("Sanakirjan sisältö: {");

        while (it.hasNext()) {
            HashMap.Entry<String, String> alkio = (HashMap.Entry<String, String>) it.next();
            if (it.hasNext()) {
                System.out.print(alkio.getKey() + " = " + alkio.getValue() + ", ");
            } else {
                System.out.println(alkio.getKey() + " = " + alkio.getValue() + "}");
            }
        }

        while (true) {
            System.out.println("Minkä sanan käännöksen haluaet tietää? (tyhjä sana lopettaa)");

            syote = lukija.nextLine();
            if (syote.isEmpty()) {
                break;
            } else {
                if (rakenne.containsKey(syote)) {
                    System.out.println("Sanan \"" + syote + "\" käännös on \"" + rakenne.get(syote) + "\"");
                } else if (rakenne.containsValue(syote)) {
                    for (Entry<String, String> entry : rakenne.entrySet()) {
                        if (entry.getValue().equals(syote)) {
                            System.out.println("Sanan \"" + syote + "\" käännös on \"" + entry.getKey() + "\"");
                        }
                    }
                } else {
                    System.out.println("Kirjoittamaasi sanaa ei löytynyt sanakirjasta!");
                }
            }
        }
        
        // Tallennetaan HashMap tulevaa käyttöä varten
        try {
            FileOutputStream tallennus = new FileOutputStream("hashmap.ser");
            ObjectOutputStream oos = new ObjectOutputStream(tallennus);
            oos.writeObject(rakenne);
            oos.close();
            tallennus.close();
            System.out.println("Tallennetaan sanakirjan sisältö...");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
