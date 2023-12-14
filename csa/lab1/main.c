#include <stdio.h>
#include <malloc.h>
#include <string.h>

int main() {
    int s_size = 2500000;
    char* s = (char*) malloc(s_size + 1);
    for (int i = 0; i < s_size; i += 2) {
        s[i] = '1';
        s[i + 1] = ' ';
    }
    s[s_size] = '\0';

    char* s2 = s;
    for(int i = 0; i < s_size; i += 2) {
        int j;
        if (sscanf(s2, "%d", &j) == EOF) {
            printf("%s", "EOF");
            break;
        }
        s2 += 2;
    }

    free(s);

    return 0;
}
