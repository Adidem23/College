#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <netinet/in.h>
#include <sys/socket.h>
#include <unistd.h>

int main(){
    int maxpackets,n;
    printf("Enter the number of packets ");
    scanf("%d", &maxpackets);
    int winsize;
    printf("Enter the number of packets in a window: ");
    scanf("%d", &winsize);
    char packet [maxpackets];
    char data;
    for (int i = 0;i<maxpackets;i++){
        printf("Enter the %d packet's Data: ",i+1);
        scanf(" %c", &data);
        packet[i] = data;
    }
    int ack = 0;
    int i = 0;
    while(ack<maxpackets){
        // printf("%d %d %d",i,maxpackets,ack);
        if(i-ack<winsize&& i<maxpackets){
            i++;
            printf("packet %d send  with data %c",i,packet[i-1]);
        }
        scanf("%d",&n);
        if (n!=-1){
            // printf("%d %d %d",i,n,ack);
            if(n==ack+1){
                ack++;
            }else{
                i = ack;
            }
            // printf("%d %d %d",i,n,ack);
        }
    }
}
