package Cyber_Assignments;

import java.util.*;
class sdes {
    public static int[] functionk(int ip8[], int[] ipk) {
        int EP[] = { 4, 1, 2, 3, 2, 3, 4, 1 };
        int s0[][] = { { 1, 0, 3, 2 }, { 3, 2, 1, 0 }, { 0, 2, 1, 3}, { 3, 1, 3, 2 } };
        int s1[][] = { { 0, 1, 2, 3 }, { 2, 0, 1, 3 }, { 3, 0, 1, 0}, { 2, 1, 0, 3 } };
        int p4[] = { 2, 4, 3, 1 };
        int rsp[] = new int[4];
        int leftnibble[] = new int[4];
        int rightnibble[] = new int[4];
        int rxor[] = new int[8];
        int rs[] = new int[4];
        int result[] = new int[8];

        // Seperating Left and Right Nibble
        for (int i = 0; i < 8; i++) {
            if (i < 4) {
                leftnibble[i] = ip8[i];
            } else {
                rightnibble[i - 4] = ip8[i];
            }
        }
        // Expansion Permutation
        int EPR[] = new int[8];
        for (int i = 0; i < 8; i++) {
            EPR[i] = rightnibble[EP[i] - 1];
        }
        for (int i = 0; i < 8; i++) {
            rxor[i] = EPR[i] ^ ipk[i];
        }
        int s0row = (2 * rxor[0]) + (rxor[3]);
        int s0col = (2 * rxor[1]) + (rxor[2]);
        int rs0 = s0[s0row][s0col];
        int s1row = (2 * rxor[4]) + (rxor[7]);
        int s1col = (2 * rxor[5]) + (rxor[6]);
        int rs1 = s1[s1row][s1col];
        for (int i = 1; i >= 0; i--) {
            rs[i] = rs0 % 2;
            rs0 = rs0 / 2;
        }
        for (int i = 3; i >= 2; i--) {
            rs[i] = rs1 % 2;
            rs1 = rs1 / 2;
        }
        // P4 permutation
        for (int i = 0; i < 4; i++) {
            rsp[i] = rs[p4[i] - 1];
        }
        for (int i = 0; i < 4; i++) {
            leftnibble[i] = leftnibble[i] ^ rsp[i];
        }
        for (int i = 0; i < 8; i++) {
            if (i < 4) {
                result[i] = leftnibble[i];
            } else {
                result[i] = rightnibble[i - 4];
            }
        }
        return result;
    }
    public static void main(String[] args) {
        int plaintext[] = { 1, 0, 1, 0, 0, 1, 0, 1 };
        int p10[] = { 3, 5, 2, 7, 4, 10, 1, 9, 8, 6 };
        int p8[] = { 6, 3, 7, 4, 8, 5, 10, 9 };
        int inputkey[] = { 0, 0, 1, 0, 0, 1, 0, 1, 1, 1 };
        int k1[] = new int[8];
        int k2[] = new int[8];
        int IP[] = { 2, 6, 3, 1, 4, 8, 5, 7 };
        int iIP[] = { 4, 1, 3, 5, 7, 2, 8, 6 };
        int resultIP[] = new int[8];
        int resultfunctionk1[] = new int[8];
        int resultfunctionk2[] = new int[8];
        int finalresult[] = new int[8];
        int Decryptedresult[] = new int[8];
        int r10[] = new int[10];
        Scanner sc = new Scanner(System.in);
        System.out.println("Plain Text is");
        System.out.println(Arrays.toString(plaintext));
        System.out.println("Input Key is");
        System.out.println(Arrays.toString(inputkey));
        for (int i = 0; i < 10; i++) {
            r10[i] = inputkey[p10[i] - 1];
        }
        for (int i = 0; i < 4; i++) {
            int temp = r10[i];
            r10[i] = r10[i + 1];
            r10[i + 1] = temp;
        }
        for (int i = 5; i < 9; i++) {
            int temp = r10[i];
            r10[i] = r10[i + 1];
            r10[i + 1] = temp;
        }
        for (int i = 0; i < 8; i++) {
            k1[i] = r10[p8[i] - 1];
        }
        System.out.println("The Key 1 is ");
        System.out.println(Arrays.toString(k1));
        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < 4; i++) {
                int temp = r10[i];
                r10[i] = r10[i + 1];
                r10[i + 1] = temp;
            }
            for (int i = 5; i < 9; i++) {
                int temp = r10[i];
                r10[i] = r10[i + 1];
                r10[i + 1] = temp;
            }
        }
        for (int i = 0; i < 8; i++) {
            k2[i] = r10[p8[i] - 1];
        }
        System.out.println("The Key 2 is ");
        System.out.println(Arrays.toString(k2));
        // Encryption
        // Initial Permutation
        for (int i = 0; i < 8; i++) {
            resultIP[i] = plaintext[IP[i] - 1];
        }
        resultfunctionk1 = functionk(resultIP, k1);
        // Swaping
        for (int i = 0; i < 4; i++) {
            int temp = resultfunctionk1[i];
            resultfunctionk1[i] = resultfunctionk1[i + 4];
            resultfunctionk1[i + 4] = temp;
        }
        // f2
        resultfunctionk2 = functionk(resultfunctionk1, k2);
        // IP-1
        for (int i = 0; i < 8; i++) {
            finalresult[i] = resultfunctionk2[iIP[i] - 1];
        }
        System.out.println("Encrypted data");
        System.out.println(Arrays.toString(finalresult));
        // Decyption
        for (int i = 0; i < 8; i++) {
            resultIP[i] = finalresult[IP[i] - 1];
        }
        resultfunctionk2 = functionk(resultIP, k2);
        // Swaping
        for (int i = 0; i < 4; i++) {
            int temp = resultfunctionk2[i];
            resultfunctionk2[i] = resultfunctionk2[i + 4];
            resultfunctionk2[i + 4] = temp;
        }
        resultfunctionk1 = functionk(resultfunctionk2, k1);
        // IP-1
        for (int i = 0; i < 8; i++) {
            Decryptedresult[i] = resultfunctionk1[iIP[i] - 1];
        }
        System.out.println("Decrypted Data");
        System.out.println(Arrays.toString(Decryptedresult));
    }
}
