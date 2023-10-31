#include <stdio.h>
int main() {
    int data[10];
    int dataatrec[10], c, c1, c2, c3, i;
    
    printf("Enter 4 bits of data one by one \n");
    scanf("%d", &data[7]);
    scanf("%d", &data[6]);
    scanf("%d", &data[5]);
    scanf("%d", &data[3]);

    // Calculation of even parity
    data[4] = data[5] ^ data[6] ^ data[7];
    data[2] = data[3] ^ data[6] ^ data[7];
    data[1] = data[3] ^ data[5] ^ data[7];
    
    printf("\nEncoded data is\n");
    for (i = 1; i <= 7; i++)
        printf("%d", data[i]);
    
    printf("\n\nEnter received data bits one by one\n");
    for (i = 1; i <= 7; i++)
        scanf("%d", &dataatrec[i]);

    c1 = dataatrec[1] ^ dataatrec[3] ^ dataatrec[5] ^ dataatrec[7];
    c2 = dataatrec[2] ^ dataatrec[3] ^ dataatrec[6] ^ dataatrec[7];
    c3 = dataatrec[4] ^ dataatrec[5] ^ dataatrec[6] ^ dataatrec[7];
    c = c3 * 4 + c2 * 2 + c1;
    
    if (c == 0) {
        printf("\nCongratulations, there is no error.\n");
    } else {
        printf("\nError at position: %d\n", c);
        printf("Corrected message is: ");
        if (dataatrec[c] == 0)
            dataatrec[c] = 1;
        else
            dataatrec[c] = 0;
        for (i = 1; i <= 7; i++) {
            printf("%d", dataatrec[i]);
        }
    }

    return 0;
}
