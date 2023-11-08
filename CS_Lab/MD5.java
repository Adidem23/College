package Cyber_Assignments;

import java.util.Scanner;

public class MD5 {
    private static final int[] S = new int[64];

    public static void main(String[] args) {
        System.out.println("MD5 Hashing Algorithm");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a message: ");
        String message = scanner.nextLine();
        md5(message);
        scanner.close();
    }

    public static void md5(String message) {
// MD5 initialization constants
        int A = 0x01234567, B = 0x89abcdef, C = 0xfedcba98, D = 0x76543210;
        int OVI_A = 0x01234567, OVI_B = 0x89abcdef, OVI_C = 0xfedcba98, OVI_D =
                0x76543210;
// Constants for MD5 transformations
        for (int i = 0; i < 64; i++) {
            if (i < 16)
                S[i] = (int) (7 + Math.abs(Math.sin(i + 1)) * Math.pow(2, 30));
            else if (i < 32)
                S[i] = (int) (5 + Math.abs(Math.sin(i + 1)) * Math.pow(2, 30));
            else if (i < 48)
                S[i] = (int) (4 + Math.abs(Math.sin(i + 1)) * Math.pow(2, 30));
            else
                S[i] = (int) (6 + Math.abs(Math.sin(i + 1)) * Math.pow(2, 30));
        }
// Initial message length in bits
        long n = (long) message.length() * 8;
// Pre-processing: padding with zeros and length in bits
        int len = message.length();
        int newLen = ((((len + 8) / 64) + 1) * 64) - 8;
        byte[] paddedMsg = new byte[newLen + 64];
        byte[] messageBytes = message.getBytes();
        System.arraycopy(messageBytes, 0, paddedMsg, 0, len);
        paddedMsg[len] = (byte) 0x80;
        for (int i = len + 1; i < newLen; i++) {
            paddedMsg[i] = 0;
        }
// Append the original length in bits
        for (int i = 0; i < 8; i++) {
            paddedMsg[newLen + i] = (byte) (n >> (i * 8));
        }
// Process the message in 512-bit blocks
        for (int i = 0; i < newLen + 8; i += 64) {
            int[] M = new int[16];
            for (int j = 0; j < 16; j++) {
                M[j] = (paddedMsg[i + j * 4 + 3] << 24) | (paddedMsg[i + j * 4 + 2] << 16) |
                        (paddedMsg[i + j * 4 + 1] << 8) | (paddedMsg[i + j * 4]);
            }
            int AA = A, BB = B, CC = C, DD = D;
            for (int j = 0; j < 64; j++) {
                int F, g;
                if (j < 16) {
                    F = (BB & CC) | ((~BB) & DD);
                    g = j;
                } else if (j < 32) {
                    F = (DD & BB) | ((~DD) & CC);
                    g = (5 * j + 1) % 16;
                } else if (j < 48) {
                    F = BB ^ CC ^ DD;
                    g = (3 * j + 5) % 16;
                } else {
                    F = CC ^ (BB | (~DD));
                    g = (7 * j) % 16;
                }
                int temp = DD;
                DD = CC;
                CC = BB;
                BB = BB + LEFTROTATE((AA + F + M[g] + S[j]), 7);
                AA = temp;
            }
            A += AA;
            B += BB;
            C += CC;
            D += DD;
        }
// Final hash
        int[] hash = new int[16];
        hash[0] = A;
        hash[1] = B;
        hash[2] = C;
        hash[3] = D;
// Convert the hash to hexadecimal format
        StringBuilder hashStr = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            String hex = Integer.toHexString(hash[i]);
            while (hex.length() < 8) {
                hex = "0" + hex;
            }
            hashStr.append(hex);
        }
        System.out.println("Input string: " + message);
        System.out.println("32 bit Hash of message: " + hashStr);
    }

    private static int LEFTROTATE(int x, int c) {
        return ((x << c) | (x >>> (32 - c)));
    }
}
