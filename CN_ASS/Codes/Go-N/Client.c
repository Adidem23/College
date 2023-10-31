#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <netinet/in.h>
#include <sys/socket.h>
#include <unistd.h>
int main(){
    int maxpackets,n,i;
    printf("Enter the number of packets ");
    scanf("%d", &maxpackets);
    char packet [maxpackets];
    char data;
    i = 0;
    while(i<maxpackets){
        scanf("%d %c", &n,&data);
        if(n==i+1){
            printf("ack %d sent",n);
            packet[n-1]= data;
            i++;
        }
    }
    for (int i = 0;i<maxpackets;i++){
        printf("\nThe %d packet's Data is %c \n",i+1,packet[i]);
    }
}
