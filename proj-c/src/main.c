#include "interface.h"
#include <time.h>
#include <stdio.h>

int main(int argc, char** argv){
    clock_t tpf, tpff;
    tpf = clock();
    tpff = tpf;

    TAD_community new = init();
    new = load(new, argv[1]);

    tpf = clock()- tpf;
    printf("Demorou %f segundos a ler\n", ((float)tpf)/CLOCKS_PER_SEC);
   
    printf("*************************TESTES DE QUERIES(DUMP UBUNTU)********************\n\n");
    printf("-------------------------------------------------------------------------\n");
    printf("======>TESTAR QUERY 1 COM ID = 796430\n\n");
    info_from_post(new, 796430); // 15137  15136
    printf("\n-----------------------------------------------------------------------\n");
    printf("======>TESTAR QUERY 2 COM N = 25\n");
    top_most_active(new, 25);
    printf("\n-----------------------------------------------------------------------\n");
    printf("======>TESTAR QUERY 3 COM DATA INICIAL = 2014-01-01 E DATA FINAL = 2014-12-31\n");
    Date begin = createDate(1, 1, 2014);
    Date end = createDate(31, 12, 2014);
    total_posts(new, begin, end);
    printf("\n-----------------------------------------------------------------------\n");
    printf("======>TESTAR QUERY 4 COM DATA INICIAL = 2013-03-01 E DATA FINAL = 2013-03-31 E TAG \"package-management\"\n");
    begin = createDate(1, 3, 2013);
    end = createDate(31, 3, 2013);
    char *tag = "package-management";
    questions_with_tag(new, tag, begin, end);
    printf("\n-----------------------------------------------------------------------\n");
    printf("======>TESTAR QUERY 5 COM ID = 15811\n");
    get_user_info(new, 15811); 
    printf("\n-----------------------------------------------------------------------\n");
    printf("======>TESTAR QUERY 6 COM DATA INICIAL = 2013-05-1 E DATA FINAL = 2013-05-06 E N = 50 \n");
    begin = createDate(1,5,2013);
    end = createDate(6,5,2013);
    most_voted_answers(new,50, begin,end);
    printf("\n-----------------------------------------------------------------------\n");
    printf("======>TESTAR QUERY 7 COM DATA INICIAL = 2014-08-1 E DATA FINAL = 2014-08-11 E N = 10 \n");
    begin = createDate(1,8,2014);
    end = createDate(11,8,2014);
    most_answered_questions(new,10, begin,end);
    printf("\n-----------------------------------------------------------------------\n");
    printf("======>TESTAR QUERY 8 COM WORD = \"kde\" E N = 10 \n");
    contains_word(new,"kde", 10);
    printf("\n-----------------------------------------------------------------------\n");
    printf("======>TESTAR QUERY 9 COM ID1 = 87 E ID2 = 5691 E N = 10\n");
    both_participated(new, 87, 5691, 10);
    printf("\n-----------------------------------------------------------------------\n");
    printf("======>TESTAR QUERY 10 COM ID = 30334\n");
    better_answer(new, 30334);
    printf("\n-----------------------------------------------------------------------\n");
    printf("======>TESTAR QUERY 11 COM DATA INICIAL = 2014-1-1 E DATA FINAL = 2014-12-31 E N = 10\n");
    begin = createDate(1,1,2014);
    end = createDate(31,12,2014);
    most_used_best_rep(new, 10,begin,end);
    printf("\n-----------------------------------------------------------------------\n");
    printf("======>A DAR CLEAN A ESTRUTURA TODA\n");
    clean(new);
    printf("All done.\n");
    tpff = clock()- tpff;
    printf("Demorou %f segundos completar tudo.\n", ((float)tpff)/CLOCKS_PER_SEC);
    
    return 0;
}
