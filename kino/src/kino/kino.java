package kino;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class kino {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

     // Opcje gatunkow filmowych
        String[] gatunki = {"Przygoodowe", "Komedie", "Historyczne"};
        String[][] filmy = {
            {"Uncharted", "Wladca pierscieni", "Harry Poter"},
            {"Tesciowie", "Shrek", "Listy do M"},
            {"Quo vadis", "Ogniem i mieczem", "Napoleon"}
        };

        // Nazwy plikow dla kazdego filmu
        String[][] plikiFilmow = {
            {"Uncharted.txt", "WladcaPierscieni.txt", "HaryPoter.txt"},
            {"Tesciowie.txt", "Shrek.txt", "Listy do M.txt"},
            {"QuoVadis.txt", "OgniemIMieczem.txt", "Napoleon.txt"}
        };

        boolean powrotDoGatunku = true;
        while (powrotDoGatunku) {
            // Wybierz gatunek
            System.out.println("Wybierz gatunek filmowy:");
            for (int i = 0; i < gatunki.length; i++) {
                System.out.println((i + 1) + ". " + gatunki[i]);
            }

            int wyborGatunku = scanner.nextInt() - 1;

            if (wyborGatunku < 0 || wyborGatunku >= gatunki.length) {
                System.out.println("Nieprawidlowy wybor gatunku.");
            } else {
                powrotDoGatunku = false;
                boolean powrotDoFilmu = true;

                while (powrotDoFilmu) {
                    System.out.println("Wybrales gatunek: " + gatunki[wyborGatunku]);
                    System.out.println("Oto dostepne filmy (wpisz 0 aby wrocic do wyboru gatunku):");

                    for (int j = 0; j < filmy[wyborGatunku].length; j++) {
                        System.out.println((j + 1) + ". " + filmy[wyborGatunku][j]);
                    }

                    int wyborFilmu = scanner.nextInt() - 1;

                    if (wyborFilmu == -1) {
                        powrotDoGatunku = true;
                        break; // Powrot do wyboru gatunku
                    } else if (wyborFilmu < 0 || wyborFilmu >= filmy[wyborGatunku].length) {
                        System.out.println("Nieprawidlowy wybor filmu.");
                    } else {
                        String nazwaPliku = plikiFilmow[wyborGatunku][wyborFilmu];
                        System.out.println("Wybrales film: " + filmy[wyborGatunku][wyborFilmu]);
                        powrotDoFilmu = false;

                        // Wczytaj tablice z pliku filmu
                        int[][] tablica = new int[4][10];
                        try {
                            File file = new File(nazwaPliku);
                            if (!file.exists()) {
                                // Tworzenie pliku z tablica zer, jesli plik nie istnieje
                                FileWriter writer = new FileWriter(file);
                                for (int i = 0; i < 4; i++) {
                                    for (int j = 0; j < 10; j++) {
                                        writer.write("0 ");
                                    }
                                    writer.write("\n");
                                }
                                writer.close();
                            }

                            // Odczytanie danych z pliku
                            Scanner fileScanner = new Scanner(file);
                            for (int i = 0; i < 4; i++) {
                                for (int j = 0; j < 10; j++) {
                                    if (fileScanner.hasNextInt()) {
                                        tablica[i][j] = fileScanner.nextInt();
                                    }
                                }
                            }
                            fileScanner.close();
                        } catch (IOException e) {
                            System.out.println("Blad podczas odczytu pliku.");
                            e.printStackTrace();
                        }

                        // Wyswietlenie wczytanej tablicy
                        for (int i = 0; i < tablica.length; i++) {
                            for (int j = 0; j < tablica[i].length; j++) {
                                System.out.print(tablica[i][j] + " ");
                            }
                            System.out.println();
                        }

                        // Uzytkownik wybiera numer wiersza i kolumny
                        boolean powrotDoWybieraniaMiejsca = true;
                        while (powrotDoWybieraniaMiejsca) {
                            System.out.print("Wybierz numer rzedu(0-3, lub -1 aby wrocic do wyboru filmu): ");
                            int wybranyWiersz = scanner.nextInt();
                            if (wybranyWiersz == -1) {
                                powrotDoFilmu = true;
                                break;
                            }

                            System.out.print("Wybierz numer miejsca(0-9, lub -1 aby wrocic do wyboru filmu): ");
                            int wybranaKolumna = scanner.nextInt();
                            if (wybranaKolumna == -1) {
                                powrotDoFilmu = true;
                                break;
                            }

                            // Sprawdzenie poprawnosci wyboru i zapis do tablicy
                            if (wybranyWiersz >= 0 && wybranyWiersz < 4 && wybranaKolumna >= 0 && wybranaKolumna < 10) {
                            	if (tablica[wybranyWiersz][wybranaKolumna] == 0) {
                                tablica[wybranyWiersz][wybranaKolumna] = 1; // Ustawienie wybranej komorki na 1
                                System.out.println("Zmieniono wartosc w tablicy.");
                                powrotDoWybieraniaMiejsca = false;
                            	}
                            	else if(tablica[wybranyWiersz][wybranaKolumna] == 1) {
                            		System.out.println("wybrane miejsce jest juz zajete");
                            		powrotDoFilmu = true;
                                    break;
                            	}

                                // Zapis zaktualizowanej tablicy do pliku
                                try {
                                    FileWriter writer = new FileWriter(nazwaPliku);
                                    for (int i = 0; i < tablica.length; i++) {
                                        for (int j = 0; j < tablica[i].length; j++) {
                                            writer.write(tablica[i][j] + " ");
                                        }
                                        writer.write("\n");
                                    }
                                    writer.close();
                                    System.out.println("Tablica zostala zapisana do pliku: " + nazwaPliku);
                                } catch (IOException e) {
                                    System.out.println("Blad podczas zapisu do pliku.");
                                    e.printStackTrace();
                                }
                            } else {
                                System.out.println("Nieprawidlowy wybor wiersza lub kolumny.");
                                powrotDoFilmu = true;
                                break;
                            }
                        }
                    }
                }
            }
        }

        scanner.close();
    }
}
