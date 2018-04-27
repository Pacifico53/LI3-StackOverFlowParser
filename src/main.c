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
   
    printf("*************************TESTES DE QUERIES********************\n\n");
    printf("----------------------------------------------------------------\n");
    printf("\tTESTAR QUERY 1 COM ID = 15136 e ID = 187273\n\n");
    printf("-->ID = 15136:\n");
    info_from_post(new, 15136); // 15137  15136
    printf("\n-->ID = 187273:\n");
    info_from_post(new, 187273); // 15137  15136
    printf("\n----------------------------------------------------------------\n");
    printf("\tTESTAR QUERY 2 COM N = 5\n");
    top_most_active(new, 5);
    return 0;
}
