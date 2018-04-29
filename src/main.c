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
   
    printf("*************************TESTES DE QUERIES(DUMP UBUNTU)********************\n\n");
    printf("----------------------------------------------------------------\n");
    printf("\tTESTAR QUERY 1 COM ID = 801049 e ID = 796430\n\n");
    printf("-->ID = 801049:\n");
    info_from_post(new, 801049); // 15137  15136
    printf("\n-->ID = 187273:\n");
    info_from_post(new, 796430); // 15137  15136
    printf("\n----------------------------------------------------------------\n");
    printf("\tTESTAR QUERY 2 COM N = 10\n");
    top_most_active(new, 10);
    printf("\n\tTESTAR QUERY 2 COM N = 25\n");
    top_most_active(new, 25);
    printf("\n----------------------------------------------------------------\n");
    printf("\tTESTAR QUERY 3 COM DATA INICIAL = 2014-01-01 E DATA FINAL = 2014-12-31\n");
    Date begin = createDate(1, 1, 2014);
    Date end = createDate(31, 12, 2014);
    total_posts(new, begin, end);
    printf("\n----------------------------------------------------------------\n");
    printf("\tTESTAR QUERY 4 COM DATA INICIAL = 2013-03-01 E DATA FINAL = 2013-03-31 E TAG \"package-management\"\n");
    begin = createDate(1, 3, 2013);
    end = createDate(31, 3, 2013);
    char *tag = "package-management";
    questions_with_tag(new, tag, begin, end);
    printf("\n----------------------------------------------------------------\n");
    printf("\tTESTAR QUERY 5 COM ID = 15811\n");
    get_user_info(new, 15811); 
    printf("\n----------------------------------------------------------------\n");
    printf("\tTESTAR QUERY 6 COM N = \n");
    begin = createDate(1,1,2013);
    end = createDate(31,3,2013);
    //most_voted_answers(new,10, begin,end);
    return 0;
}
