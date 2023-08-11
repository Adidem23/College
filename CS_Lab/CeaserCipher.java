import java.util.Scanner;

public class CeaserCipher {

    public static void main(String[] args) {

        //assigning numbers to letters
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the word");
        String word = scan.nextLine();
        System.out.println("Enter the shift");
        int shift = scan.nextInt();
        String eStr = "";
        int pos = 0;

        for (int i = 0; i < word.length(); i++) {
            pos = alphabet.indexOf(word.charAt(i));
            eStr += alphabet.charAt((pos + shift) % 26);
        }

        System.out.println(eStr);
    }

    }
