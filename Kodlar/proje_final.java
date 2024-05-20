import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class proje_final {
    // Bu metod aynı değerlere sahip komşu hücrelere X yazdırır.
    public static void kontrolEt(String[][] tablo, int satir, int sutun, String secilenDeger) {
        // Hücrenin sınırlarını kontrol ettir, eğer sınırlar dışındaysa veya zaten X ise işlemi sonlandır.
        if (satir < 0 || sutun < 0 || satir >= 10 || sutun >= 10 || tablo[satir][sutun].equals("X") || !tablo[satir][sutun].equals(secilenDeger)) {
            return;
        } else {
            // Hücreye X işareti koy ve komşu hücreleri kontrol et.
            tablo[satir][sutun] = "X";
            kontrolEt(tablo, satir, sutun - 1, secilenDeger);//aşağı tarafın kontrolü
            kontrolEt(tablo, satir, sutun + 1, secilenDeger);//yukarı tarafın kontrolü
            kontrolEt(tablo, satir + 1, sutun, secilenDeger);//sağ tarafın kontrolü
            kontrolEt(tablo, satir - 1, sutun, secilenDeger);//sol tarafın kontrolü
        }
    }

    // Bu metod, verilen isimde yeni bir dosya oluşturur.
    public static void dosyaOlustur(String dosyaAdi) {
        File file = new File(dosyaAdi);
        try {
            // Dosya zaten varsa oluşturma işlemini geç.
            if (file.createNewFile()) {
                return;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Bu metod, verilen dosyanın içeriğini ekrana yazdırır.
    public static void dosyadanOku(String dosyaAdi) {
        try {
            File file = new File(dosyaAdi);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                // Dosyanın her satırını ekrana yazdırmak için.
                String text = scanner.nextLine();
                System.out.println(text);
            }
            scanner.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        // harita.txt isminde bir dosya oluşturulur.
        dosyaOlustur("harita.txt");
        // 10x10 boyutunda rastgele sayılarla doldurulmuş bir tablo oluşturuluyor.
        String[][] tablo = new String[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                // Her hücreye rastgele bir sayı atıyoruz.
                tablo[i][j] = String.valueOf((int) (Math.random() * 10));
            }
        }
        try {
            // Tablo değerleri harita.txt dosyasına yazılır.
            File file = new File("harita.txt");
            FileWriter writer = new FileWriter(file);
            for (String[] sutun : tablo) {
                // Her satırı dosyaya yaz.
                for (String value : sutun) {
                    writer.write(value + " ");
                }
                // Bir sonraki satıra geç.
                writer.write(System.lineSeparator());
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //harita.txt dosyasının içeriği okunup ekrana yazdırılır.
        dosyadanOku("harita.txt");
        Scanner kullaniciGirdisiScanner = new Scanner(System.in);
        while (true) {
            System.out.println("Lütfen koordinat giriniz (Örnek 4,3)");
            String kullaniciGirdisi = kullaniciGirdisiScanner.nextLine();
            String[] koordinatDizi = kullaniciGirdisi.split(",");
            int satir = Integer.parseInt(koordinatDizi[0]);
            int sutun = Integer.parseInt(koordinatDizi[1]);
            if (satir != 0 && sutun != 0) {
                satir--;
                sutun--;
                if (satir >= 0 && satir < 10 && sutun >= 0 && sutun < 10) {
                    String secilenDeger = tablo[satir][sutun];
                    kontrolEt(tablo, satir, sutun, secilenDeger);
                    try {
                        // Tabloyu değiştirdikten sonra dosyayı güncelliyor.
                        File file = new File("harita.txt");
                        FileWriter writer = new FileWriter(file);
                        for (String[] sutun2 : tablo) {
                            // Her satırı dosyaya yaz.
                            for (String value : sutun2) {
                                writer.write(value + " ");
                            }
                            // Bir sonraki satıra geç.
                            writer.write(System.lineSeparator());
                        }
                        writer.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    // harita.txt dosyasının güncellenmiş içeriği okunup ekrana yazdırılıyor.
                    dosyadanOku("harita.txt");
                } else {
                    System.out.println("Geçersiz koordinat girdiniz, lütfen tekrar koordinat girin1.");
                }
            } else {
                System.out.println("Oyun sonlandı,Güle Güle!!!");
                break;
            }
        }
    }
}