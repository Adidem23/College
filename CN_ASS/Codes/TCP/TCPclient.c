#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <arpa/inet.h>

#define MAX_BUFFER_SIZE 1024

int main() {
    int client_socket;
    struct sockaddr_in server_addr;

    // Create a socket
    client_socket = socket(AF_INET, SOCK_STREAM, 0);
    if (client_socket == -1) {
        perror("Error creating socket");
        exit(1);
    }

    // Set up the server address and port
    server_addr.sin_family = AF_INET;
    server_addr.sin_port = htons(12345); // Change to the same port as the server
    server_addr.sin_addr.s_addr = inet_addr("127.0.0.1"); // Change to the server's IP address

    // Connect to the server
    if (connect(client_socket, (struct sockaddr*)&server_addr, sizeof(server_addr)) == -1) {
        perror("Error connecting to the server");
        exit(1);
    }

    printf("Connected to the server\n");

    // Open a file for writing
    FILE *file = fopen("received.txt", "wb");
    if (file == NULL) {
        perror("Error opening file");
        exit(1);
    }

    char buffer[MAX_BUFFER_SIZE];
    int bytes_received;

    while ((bytes_received = recv(client_socket, buffer, sizeof(buffer), 0)) > 0) {
        fwrite(buffer, 1, bytes_received, file);
    }

    if (bytes_received == -1) {
        perror("Error receiving data");
    } else if (bytes_received == 0) {
        printf("Connection closed by the server\n");
    } else {
        printf("File received successfully\n");
    }

    // Close the socket and file
    fclose(file);
    close(client_socket);

    return 0;
}
