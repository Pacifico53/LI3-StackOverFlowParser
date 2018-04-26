#include "interface.h"
#include <time.h>
#include <stdio.h>

int main(int argc, char** argv){
    clock_t tpf;
    tpf = clock();

    TAD_community new = init();
    new = load(new, argv[1]);

    tpf = clock()- tpf;
    printf("Demorou %f segundos a ler\n", ((float)tpf)/CLOCKS_PER_SEC); 
    return 0;
}
