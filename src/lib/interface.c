#include "date.h"
#include "pair.h"
#include "list.h"
#include "user.h"
#include "answersHashTable.h"
#include "postHashTable.h"
#include "usersHashTable.h"
#include "tad_community.h"
#include "anosArray.h"
#include "diaNodo.h"
#include "tags.h"
#include "parser.h"
#include <glib.h>

TAD_community init(){
    TAD_community com = init_TCD_community();
    return com;
}

// query 0
TAD_community load(TAD_community com, char* dump_path){
    parse(com, dump_path);
    return com;
}  //diretoria onde estarão os ficheiros do dump

// query 1
STR_pair info_from_post(TAD_community com, long id){
    STR_pair par = NULL;
    GHashTable* usershash = get_hash_userss(com);
    USERS user = NULL;      //sempre que possivel inicializamos uma estrutura com NULL ou 0, para nao haver dados nao inicializados

    GArray* anos = get_array_anos(com);     // isto da nos o 'calendario' que tem os posts (answers e questions) em cada dia, e assim 
                                            // podemos percorrer e encontrar o dia que se quer, e delimitar o calendario, caso seja preciso
    GArray* meses = NULL;
    GArray* dias = NULL;
    DIA dia = NULL;
    POSTS questions = NULL;
    ANSWERS respostas = NULL;
    GString* titulo = NULL;     //usamos em varios casos a estrutura glib GString, quando é preciso usar strings, porque achamos mais conveniente
    GString* nome = NULL;
    long id_p_u = 0;
    long id_parente = 0;
    int parar = 1;
    int i = 0;
    int j = 0;
    int d = 0;      //este proximo loop for é utilizado em muitas queries, é a maneira que nos usamos para percorrer o calendario
    for (i = 0; i < 10 && parar; i++) {     //o 'calendario' tem 10 anos, decidimos 10 anos dado as datas dos posts dos dumps
        meses = g_array_index(anos,GArray *,i);
        for (j = 0; j < 12 && parar; j++) {
            dias = g_array_index(meses, GArray *,j);
            for (d = 0; d < 31 && parar; d++) {
                dia = g_array_index(dias,DIA,d);
                GHashTable* respostashash = get_answers(dia);   //aqui obtemos a hashtable de respostas de um dia
                GHashTable* questionshash = get_questions(dia); // e o mesmo aqui para as questions

                if(g_hash_table_contains(respostashash,GINT_TO_POINTER(id))){   // aqui identificamos se na hashtable das respostas esta la o id que se quer
                    parar = 0;
                    respostas = g_hash_table_lookup(respostashash,GINT_TO_POINTER(id)); // caso esteja, vamos ver qual o valor do id da pergunta, para isso 'retiramos' a resposta da hashtable
                    id_parente = get_parent_id(respostas);                              // e vemos qual o id da pergunta, usando o get_parent_id
                    return info_from_post(com, id_parente);                             // e aqui retornamos o valor que esta funçao retorna, para esse id da pergunta
                }
                
                if(g_hash_table_contains(questionshash,GINT_TO_POINTER(id))){           // aqui vemos se o id esta na hashtable das perguntas do dia
                    parar = 0;                                                          // caso esteja, parar = 0 faz com que o loop de percorrer o calendario pare
                    questions = g_hash_table_lookup(questionshash,GINT_TO_POINTER(id)); // aqui 'retiramos' a questao da hashtable
                    titulo = get_titulo(questions);                                     // e aqui retiramos os valores do que se quer, o titulo da question
                    id_p_u = get_user_id(questions);                                    // e com o get_user_id sabemos o id do user que postou a pergunta
                    user = g_hash_table_lookup(usershash, GINT_TO_POINTER(id_p_u));     // vamos entao a hashtable dos users buscar o user com o id que se descobriu
                    nome = get_name(user);                                              // e fazemos get_name para ter o nome do user
                    par = create_str_pair(titulo->str, nome->str);                      // como ja temos tudo o que queriamos, damos os valores ao STR_pair que depois se vai retornar
                }
            }
        }
    }
    return par;
}

//funçao auxiliar que é utilizada no foreach da hashtable de answers, isto vai incrementar o numero de posts de cada utilizador
void incrementaNumbersA(gpointer key, gpointer value, gpointer userdata){
    long userID = 0;
    userID = get_user_id_a(value);

    increment_numberOfPosts(g_hash_table_lookup(userdata, GINT_TO_POINTER(userID)));
}

//funçao auxiliar que faz o mesmo que a anterior, mas esta é utilizada na hashtable de questions
void incrementaNumbersQ(gpointer key, gpointer value, gpointer userdata){
    long userID = 0;
    userID = get_user_id(value);

    increment_numberOfPosts(g_hash_table_lookup(userdata, GINT_TO_POINTER(userID)));
}

//Esta funçao auxiliar serve para ordenar a lista de users na query2 de maneira a termos os users com numero de posts maior no topo da lista
int comparaNumeroPosts(gconstpointer a, gconstpointer b){
    int result = 0;
    USERS ua = (gpointer)a;
    USERS ub = (gpointer)b;
    int numeroPostsa = get_numberOfPosts(ua);
    int numeroPostsb = get_numberOfPosts(ub);

    if (numeroPostsa > numeroPostsb) {result = -1;}
    else if (numeroPostsa < numeroPostsb) {result = 1;}
    
    return result;
}

// query 2
LONG_list top_most_active(TAD_community com, int N){
    LONG_list result = create_list(N);
    GHashTable *usersht = get_hash_userss(com);     //aqui estamos a tirar a hashtable de users da estrutura com
    USERS user = NULL;
    GArray *anos = get_array_anos(com);
    GArray *meses = NULL;
    GArray *dias = NULL;
    DIA dia = NULL;
    long userID = 0;
    int i = 0;
    int j = 0;
    int d = 0;
    int ii = 0;
    for (i = 0; i < 10; i++) {
        meses = g_array_index(anos,GArray *,i);
        for (j = 0; j < 12; j++) {
            dias = g_array_index(meses, GArray *,j);
            for (d = 0; d < 31; d++) {
                dia = g_array_index(dias,DIA,d);
                GHashTable* respostashash = get_answers(dia);
                GHashTable* questionshash = get_questions(dia);
                g_hash_table_foreach(respostashash, incrementaNumbersA, usersht); // o foreach aplica uma funçao para cada par de key/value da hashtable, usamos isso para incrementar o numero de posts de cada user,
                g_hash_table_foreach(questionshash, incrementaNumbersQ, usersht); // dando a hashtable de users à funçao 
            }
        }
    }

    GList * listaUsers = g_hash_table_get_values(usersht);      //aqui declara se uma GList, que é uma dupla lista ligada, com a funçao g_hash_table_get_values, que retorna todos os valores de uma hashtable
    listaUsers = g_list_sort(listaUsers, comparaNumeroPosts);   // e aqui ordenamos a lista usando g_list_sort e a funçao auxiliar de comparaçao, declarada anteriormente
    
    //Este for loop enche a LONG_list que vai ser retornada com os N primeiros users da lista anteriormente ordenada
    for (ii = 0; ii < N; ii++) {
        user = listaUsers->data;
        userID = get_id(user);
        set_list(result, ii, userID);
        listaUsers = listaUsers->next;
    }

    g_list_free(listaUsers); //aqui fazemos free da lista, para diminuir leaks de memoria
    return result;
}



// query 3
LONG_pair total_posts(TAD_community com, Date begin, Date end){
    long nQuestions = 0;
    long nAnswers = 0;

    int anoBegin = get_year(begin);
    int mesBegin = get_month(begin);
    int diaBegin = get_day(begin);

    int anoEnd = get_year(end);
    int mesEnd = get_month(end);
    int diaEnd = get_day(end);
    
    GArray *anos = get_array_anos(com);
    GArray *meses = NULL;
    GArray *dias = NULL;
    DIA dia = NULL;
    
    //Este for tem pequenas alteraçoes, para se conseguir percorrer o calendario apenas no limite de datas que é dada à query, usamos este loop quando uma query tem um limite de datas
    int i = 0;
    int j = 0;
    int d = 0;
    int k = 0;
    int l = 0;
    int p = 0;
    int o = 0;
    for (i = anoBegin - 2009; i <= anoEnd - 2009; i++) {
        meses = g_array_index(anos, GArray *, i);
        if(i == anoBegin -2009) k=mesBegin;
        if(i == anoEnd -2009) l=mesEnd;
        else{k=1;l=12;}
        for (j = k - 1; j <= l - 1; j++) {
            dias = g_array_index(meses, GArray *,j);
            if(i == anoBegin - 2009 && j == mesBegin) p = diaBegin;
            if(i == anoEnd - 2009 && j == mesEnd) o = diaEnd;
            else{p=1;o=31;}           
            for (d = p - 1; d <= o - 1 ; d++) {
                dia = g_array_index(dias,DIA,d);
                GHashTable* respostashash = get_answers(dia);
                GHashTable* questionshash = get_questions(dia);
                nAnswers += g_hash_table_size(respostashash);   // aqui usamos a funçao g_hash_table_size para aumentarmos o numero de answers total, no espaço de tempo dado
                nQuestions += g_hash_table_size(questionshash); // o mesmo aqui para o numero de questions
            }
        }
    }
    LONG_pair result = create_long_pair(nQuestions, nAnswers);
    return result;
}

// query 4
LONG_list questions_with_tag(TAD_community com, char* tag, Date begin, Date end){
    long questionID = 0;
    int size = 0;

    int anoBegin = get_year(begin);
    int mesBegin = get_month(begin);
    int diaBegin = get_day(begin);

    int anoEnd = get_year(end);
    int mesEnd = get_month(end);
    int diaEnd = get_day(end);
    
    GArray *anos = get_array_anos(com);
    GArray *meses = NULL;
    GArray *dias = NULL;
    DIA dia = NULL;
    GList *listaIDs = NULL;

    int i = 0;
    int j = 0;
    int d = 0;
    int ii = 0;
    int k = 0;
    int l = 0;
    int p = 0;
    int o = 0;
    for (i = anoBegin - 2009; i <= anoEnd - 2009; i++) {
        meses = g_array_index(anos, GArray *, i);
        if(i == anoBegin -2009) k=mesBegin;
        if(i == anoEnd -2009) l=mesEnd;
        else{k=1;l=12;}
        for (j = k - 1; j <= l - 1; j++) {
            dias = g_array_index(meses, GArray *,j);
            if(i == anoBegin - 2009 && j == mesBegin) p = diaBegin;
            if(i == anoEnd - 2009 && j == mesEnd) o = diaEnd;
            else{p=1;o=31;} 
            for (d = p - 1; d <= o - 1 ; d++) {
                dia = g_array_index(dias,DIA,d);
                GHashTable* questionshash = get_questions(dia);
                
                // Aqui usa se um metodo de percorrer uma hashtable, como aqui nao se podia usar um foreach (era preciso utilizar varios dados, e a funcao g_hash_table_foreach apenas aceita um parametro de dados)
                // utilizando um iterator, e a funçao g_hash_table_iter_init, isto està demonstrado na documentaçao glib
                GHashTableIter iter;
                gpointer key, value;
                g_hash_table_iter_init(&iter, questionshash);
                while (g_hash_table_iter_next (&iter, &key, &value)){
                    GString *tagsPost = get_tags(value);       //aqui retira se a GString que tem as tags de um post
                    if (strstr(tagsPost->str, tag) != NULL) {  //se nessa string existir a tag dada (verifica se isso usando strstr)
                        size++;                                // incrementa se o size da LONG_list final
                        questionID = get_id_p(value);
                        listaIDs = g_list_prepend(listaIDs, (gpointer)questionID);  // guarda se o id do post numa GList declarada previamente
                    }
                }
            }
        }
    }

    LONG_list result = create_list(size);   //cria se a LONG_list que se vai retornar, agora que ja se sabe o size

    // Neste loop enche se a LONG_list com os ids que estao na GList listaIDs
    for (ii = 0; ii < size; ii++) {
        questionID = (long)listaIDs->data;
        set_list(result, ii, questionID);
        listaIDs = listaIDs->next;
    }
    return result;
}

// query 5
USER get_user_info(TAD_community com, long id){
    long postsIDs[10];

    GHashTable *usershash = get_hash_userss(com);
    GArray *anos = get_array_anos(com);
    GArray *meses = NULL;
    GArray *dias = NULL;
    DIA dia = NULL;

    USERS userTarget = g_hash_table_lookup(usershash, (gpointer)id);    // aqui identificamos o user que queremos tirar informaçao, da hashtable users
    GString *shortbio = get_aboutme(userTarget);                        // e usamos o get_aboutme para tirar a GString, que depois se vai retornar
    
    long ownedUserID = 0;
    int i = 0;
    int j = 0;
    int d = 0;
    int ii = 0;
    
    //Este loop está ligeiramente alterado, de maneira a percorrer o calendario de forma inversa, e ter os posts mais recentes primeiro
    for (i = 9; i >= 0; i--) {
        meses = g_array_index(anos,GArray *,i);
        for (j = 11; j >= 0; j--) {
            dias = g_array_index(meses, GArray *,j);
            for (d = 30; d >= 0; d--) {
                dia = g_array_index(dias,DIA,d);
                GHashTable* respostashash = get_answers(dia);
                GHashTable* questionshash = get_questions(dia);
                
                //Aqui usa se o mesmo metodo para percorrer uma hashtable que na query anterior, dado que nao era possivel usar um foreach, e como temos duas hashtables para percorrer, usa se duas vezes
                GHashTableIter iter;
                gpointer key, value;
                g_hash_table_iter_init(&iter, questionshash);
                while (g_hash_table_iter_next (&iter, &key, &value) && ii < 10){
                    ownedUserID = get_user_id(value);
                    if (ownedUserID == id) {        // se se confirma que foi o user que postou a question, guarda se i id dessa question
                        postsIDs[ii] = get_id_p(value);
                        ii++;
                    }
                }

                GHashTableIter iter2;
                gpointer key2, value2;
                g_hash_table_iter_init(&iter2, respostashash);
                while (g_hash_table_iter_next (&iter2, &key2, &value2) && ii < 10){
                    ownedUserID = get_user_id_a(value2);
                    if (ownedUserID == id) {        //o mesmo aqui, mas para as answers
                        postsIDs[ii] = get_id_a(value2);
                        ii++;
                    }
                }
            }
        }
    }

    USER result = create_user(shortbio->str, postsIDs); // aqui cria se o USER resultante (USER este que é da estrutura declarada pelos professores, e nao a nossa estrutura de user)
    return result;
}

// Funçao auxiliar que vai ser utilizada para ordenar uma GList de answers pelo score destas
int comparaScore(gconstpointer a, gconstpointer b){
    int result = 0;
    ANSWERS aa = (gpointer)a;
    ANSWERS ab = (gpointer)b;
    int scorea = get_score_a(aa);
    int scoreb = get_score_a(ab);

    if (scorea > scoreb) {result = -1;}
    else if (scorea < scoreb) {result = 1;}
    
    return result;
}

// query 6
LONG_list most_voted_answers(TAD_community com, int N, Date begin, Date end){
    LONG_list result = create_list(N);

    int anoBegin = get_year(begin);
    int mesBegin = get_month(begin);
    int diaBegin = get_day(begin);

    int anoEnd = get_year(end);
    int mesEnd = get_month(end);
    int diaEnd = get_day(end);
    
    GArray *anos = get_array_anos(com);
    GArray *meses = NULL;
    GArray *dias = NULL;
    DIA dia = NULL;
    GList *listaAnswers = NULL;
    GList *answersdoDia = NULL;
    long respostaID = 0;

    int i = 0;
    int j = 0;
    int d = 0;
    int ii = 0;
    int k = 0;
    int l = 0;
    int p = 0;
    int o = 0;
    int jj = 0;
    int sizeDia = 0;
    for (i = anoBegin - 2009; i <= anoEnd - 2009; i++) {
        meses = g_array_index(anos, GArray *, i);
        if(i == anoBegin -2009) k=mesBegin;
        if(i == anoEnd -2009) l=mesEnd;
        else{k=1;l=12;}
        for (j = k - 1; j <= l - 1; j++) {
            dias = g_array_index(meses, GArray *,j);
            if(i == anoBegin - 2009 && j == mesBegin) p = diaBegin;
            if(i == anoEnd - 2009 && j == mesEnd) o = diaEnd;
            else{p=1;o=31;} 
            for (d = p - 1; d <= o - 1 ; d++) { 
                dia = g_array_index(dias,DIA,d);
                GHashTable* respostashash = get_answers(dia);
                answersdoDia = g_hash_table_get_values(respostashash); // aqui copiamos as answers de um dia para uma GList
                sizeDia = g_list_length(answersdoDia);
                // Este for enche a GList de answers (numero total) com as answers de cada dia
                for (jj = 0; jj < sizeDia; jj++) {
                    listaAnswers = g_list_prepend(listaAnswers, answersdoDia->data);
                    answersdoDia = answersdoDia->next;
                }
                g_list_free(answersdoDia); //free da lista das answers de cada dia para diminuir leaks de memoria
            }
        }
    }

    listaAnswers = g_list_sort(listaAnswers, comparaScore); //aqui ordena se a GList de answers pelo score, usando a funcao auxiliar de comparaçao declarada previamente
    
    // enche se a LONG_list com N ids que depois se vai retornar
    for (ii = 0; ii < N; ii++) {
        respostaID = get_id_a(listaAnswers->data);
        set_list(result, ii, respostaID);
        listaAnswers = listaAnswers->next;
    }

    //Frees de variaveis declaradas, para diminuir leaks de memoria
    g_list_free(listaAnswers);
    g_list_free(answersdoDia);
    return result;
}

//Funçao auxiliar de comparaçao para ordenar GList de posts pelo numero de respostas
int comparaNumeroAnswers(gconstpointer a, gconstpointer b){
    int result = 0;
    POSTS aa = (gpointer)a;
    POSTS ab = (gpointer)b;
    int nAnswersa = get_numeroRespostas(aa);
    int nAnswersb = get_numeroRespostas(ab);

    if (nAnswersa > nAnswersb) {result = -1;}
    else if (nAnswersa < nAnswersb) {result = 1;}
    
    return result;
}

// query 7
LONG_list most_answered_questions(TAD_community com, int N, Date begin, Date end){
    LONG_list result = create_list(N);

    int anoBegin = get_year(begin);
    int mesBegin = get_month(begin);
    int diaBegin = get_day(begin);

    int anoEnd = get_year(end);
    int mesEnd = get_month(end);
    int diaEnd = get_day(end);
    
    GArray *anos = get_array_anos(com);
    GArray *meses = NULL;
    GArray *dias = NULL;
    DIA dia = NULL;
    GList *listaQuestions = NULL;
    GList *questionsdoDia = NULL;
    long questionID = 0;

    //Aqui usou se a mesma estrategia que a query anterior, mas pegando apenas nas questions, e depois ordenando pelo numero de respostas

    int i = 0;
    int j = 0;
    int d = 0;
    int ii = 0;
    int k = 0;
    int l = 0;
    int p = 0;
    int o = 0;
    int jj = 0;
    int sizeDia = 0;
    for (i = anoBegin - 2009; i <= anoEnd - 2009; i++) {
        meses = g_array_index(anos, GArray *, i);
        if(i == anoBegin -2009) k=mesBegin;
        if(i == anoEnd -2009) l=mesEnd;
        else{k=1;l=12;}
        for (j = k - 1; j <= l - 1; j++) {
            dias = g_array_index(meses, GArray *,j);
            if(i == anoBegin - 2009 && j == mesBegin) p = diaBegin;
            if(i == anoEnd - 2009 && j == mesEnd) o = diaEnd;
            else{p=1;o=31;} 
            for (d = p - 1; d <= o - 1 ; d++) { 
                dia = g_array_index(dias,DIA,d);
                GHashTable* questionshash = get_questions(dia);
                questionsdoDia = g_hash_table_get_values(questionshash);
                sizeDia = g_list_length(questionsdoDia);
                for (jj = 0; jj < sizeDia; jj++) {
                    listaQuestions = g_list_prepend(listaQuestions, questionsdoDia->data);
                    questionsdoDia = questionsdoDia->next;
                }
                g_list_free(questionsdoDia);
                g_hash_table_remove_all(questionshash);
            }
        }
    }
    
    listaQuestions = g_list_sort(listaQuestions, comparaNumeroAnswers);
    
    for (ii = 0; ii < N; ii++) {
        questionID = get_id_a(listaQuestions->data);
        set_list(result, ii, questionID);
        listaQuestions = listaQuestions->next;
    }
    
    g_list_free(listaQuestions);
    g_list_free(questionsdoDia);
    return result;
}

// query 8
LONG_list contains_word(TAD_community com, char* word, int N){
    LONG_list result = create_list(N);
    
    GArray *anos = get_array_anos(com);
    GArray *meses = NULL;
    GArray *dias = NULL;
    DIA dia = NULL;
    
    int i = 0;
    int j = 0;
    int d = 0;
    int ii = 0;
    for (i = 9; i >= 0; i--) {
        meses = g_array_index(anos,GArray *,i);
        for (j = 11; j >= 0; j--) {
            dias = g_array_index(meses, GArray *,j);
            for (d = 30; d >= 0; d--) {
                dia = g_array_index(dias,DIA,d);
                GHashTable* questionshash = get_questions(dia);
                
                //Aqui percorre se a hashtable das questions para verificar se o titulo tem a word dada
                GHashTableIter iter;
                gpointer key, value;
                g_hash_table_iter_init(&iter, questionshash);
                while (g_hash_table_iter_next(&iter, &key, &value) && ii < N){
                    GString *titlePost = get_titulo(value);
                    if (strstr(titlePost->str, word) != NULL) {
                        set_list(result, ii, get_id_p(value));  //caso tenha a word, adiciona se o id de essa question à LONG_list que se vai retornar
                        ii++;                                   // e incrementa se o contador, para nao ultrapassar N id's
                    }
                }
            }
        }
    }
    
    // Aqui faz se este if porque no caso de nao se ter preenchido totalmente a LONG_list, retorna se uma LONG_list de menor tamanho com os ids que se encontraram
    int size = ii;
    if (size<N) {
        LONG_list smallerlist = create_list(size);
        for (ii = 0; ii < size; ii++) {
            set_list(smallerlist, ii, get_list(result, ii));
        }
        return smallerlist;
    }
    
    return result;
}

// query 9
LONG_list both_participated(TAD_community com, long id1, long id2, int N){
    LONG_list result = create_list(N);
    GArray *anos = get_array_anos(com);
    GArray *meses = NULL;
    GArray *dias = NULL;
    DIA dia = NULL;
    
    GList *listaAnswers1 = NULL;
    GList *listaAnswers2 = NULL; 
    GList *listaQuestions1 = NULL;
    GList *listaQuestions2 = NULL;
    long questionID = 0;
    
    int i = 0;
    int j = 0;
    int d = 0;
    int ii = 0;
    int jj = 0;
    for (i = 9; i >= 0; i--) {
        meses = g_array_index(anos,GArray *,i);
        for (j = 11; j >= 0; j--) {
            dias = g_array_index(meses, GArray *,j);
            for (d = 30; d >= 0; d--) {
                dia = g_array_index(dias,DIA,d);
                GHashTable* questionshash = get_questions(dia);
                GHashTable* answershash = get_answers(dia);
                
                //Aqui percorre se ambas as hashtables de questoes e respostas
                GHashTableIter iter;
                gpointer key, value;
                g_hash_table_iter_init(&iter, answershash);
                while (g_hash_table_iter_next(&iter, &key, &value)){
                    if(get_user_id_a(value) == id1){
                        listaAnswers1 = g_list_prepend(listaAnswers1, (gpointer)get_parent_id(value)); // caso se verifique que a resposta é de um dos users dados, guarda se o id da pergunta a qual a resposta esta associada
                    }
                    if(get_user_id_a(value) == id2){
                        listaAnswers2 = g_list_prepend(listaAnswers2, (gpointer)get_parent_id(value));
                    }
                }
                
                GHashTableIter iter2;
                gpointer key2, value2;
                g_hash_table_iter_init(&iter2, questionshash);
                while (g_hash_table_iter_next(&iter2, &key2, &value2)){
                    if(get_user_id(value2) == id1){
                        listaQuestions1 = g_list_prepend(listaQuestions1, (gpointer)get_id_p(value2)); // aqui guarda se o id da pergunta, caso seja feita por algum dos utilizadores
                    }
                    if(get_user_id(value2) == id2){
                        listaQuestions2 = g_list_prepend(listaQuestions2, (gpointer)get_id_p(value2));
                    }
                }
            }
        }
    }
    
    // Estes ints sao declarados para delimitar os for's seguintes, e percorrer se as GLists dos id's
    int lQ1size = g_list_length(listaQuestions1);
    int lQ2size = g_list_length(listaQuestions2);
    int lA1size = g_list_length(listaAnswers1);
    
    // Este int serve para dar check se um id ja esta guardado na LONG_list que se vai retornar
    int checkRepeat = 1;
    
    // Aqui verifica se se existem perguntas que o user1 fez as quais o user2 respondeu
    for (ii = 0; ii < lQ1size && jj < N; ii++) {
        questionID = (long)listaQuestions1->data;
        if (g_list_find(listaAnswers2, (gpointer)questionID)) {
            set_list(result, jj, questionID);
            jj++;
        }
        listaQuestions1 = listaQuestions1->next;
    }

    // Aqui verifica se se existem perguntas que o user2 fez as quais o user1 respondeu
    for (ii = 0; ii < lQ2size && jj < N; ii++) {
        questionID = (long)listaQuestions2->data;
        if (g_list_find(listaAnswers1, (gpointer)questionID)) {
            set_list(result, jj, questionID);
            jj++;
        }
        listaQuestions2 = listaQuestions2->next;
    }

    // Aqui verifica se se existem respostas que o user1 fez as quais o user2 tambem respondeu
    for (ii = 0; ii < lA1size && jj < N; ii++) {
        questionID = (long)listaAnswers1->data;
        if (g_list_find(listaAnswers2, (gpointer)questionID)) {
            for (i = 0; i < N && checkRepeat; i++) {
                if (get_list(result, i) == questionID) {checkRepeat = 0;} // e aqui aplica se um if para evitar aparecer id's repetidos
            }
            if (checkRepeat == 1) {
                set_list(result, jj, questionID);
                jj++;
            }
            checkRepeat = 1;
        }
        listaAnswers1 = listaAnswers1->next;
    }
   
    //Como na query anterior, caso seja preciso retorna se uma LONG_list menor
    if (jj<N) {
        LONG_list smallerlist = create_list(jj);
        for (ii = 0; ii < jj; ii++) {
            set_list(smallerlist, ii, get_list(result, ii));
        }
        return smallerlist;
    }
 
    g_list_free(listaAnswers1);
    g_list_free(listaAnswers2);
    g_list_free(listaQuestions1);
    g_list_free(listaQuestions2);
    
    return result;
}

//Funçao auxiliar que vai ordenar uma GList de respostas para ter as melhores respostas no topo, usando a formula (score * 0.65) + (reputaçao * 0.25) + (commentCount * 0.1)
int comparaBestQuestion(gconstpointer a, gconstpointer b, gpointer user_data){
    int result = 0;
    ANSWERS aa = (gpointer)a;
    ANSWERS ab = (gpointer)b;
    long user1ID = get_user_id_a(aa);
    long user2ID = get_user_id_a(ab);

    USERS u1 = g_hash_table_lookup(user_data, (gpointer)user1ID);
    USERS u2 = g_hash_table_lookup(user_data, (gpointer)user2ID);
    
    int userRep1 = get_reputation(u1);
    int userRep2 = get_reputation(u2);
    int score1 = get_score_a(aa);
    int score2 = get_score_a(ab);
    int commentCount1 = get_comment_count_a(aa);
    int commentCount2 = get_comment_count_a(ab);

    int scoreFinal1 = (score1 * 0.65) + (userRep1 * 0.25) + (commentCount1 * 0.1);
    int scoreFinal2 = (score2 * 0.65) + (userRep2 * 0.25) + (commentCount2 * 0.1);
    
    if (scoreFinal1 > scoreFinal2) {result = -1;}
    else if (scoreFinal1 < scoreFinal2) {result = 1;}

    return result;
}

// query 10
long better_answer(TAD_community com, long id){
    long result = 0;
    GHashTable *usersht = get_hash_userss(com);
    
    GArray *anos = get_array_anos(com);
    GArray *meses = NULL;
    GArray *dias = NULL;
    DIA dia = NULL;
    GList *listaAnswersdaQuestion = NULL;
    long parentID = 0;

    int i = 0;
    int j = 0;
    int d = 0;
    for (i = 0; i < 10; i++) {
        meses = g_array_index(anos,GArray *,i);
        for (j = 0; j < 12; j++) {
            dias = g_array_index(meses, GArray *,j);
            for (d = 0; d < 31; d++) {
                dia = g_array_index(dias,DIA,d);
                GHashTable* answershash = get_answers(dia);            
                
                //Aqui percorre se apenas a hashtable de respostas
                GHashTableIter iter;
                gpointer key, value;
                g_hash_table_iter_init(&iter, answershash);
                while (g_hash_table_iter_next(&iter, &key, &value)){
                    parentID = get_parent_id(value);
                    if (parentID == id) {
                        listaAnswersdaQuestion = g_list_prepend(listaAnswersdaQuestion, value); // caso seja uma resposta ao post do id dado à query, guarda se essa resposta numa GList
                    }
                }
            }
        }
    }
    
    //Existe este if no caso de nao se encontrar respostas à pergunta dada
    if(g_list_length(listaAnswersdaQuestion)>0){
        listaAnswersdaQuestion = g_list_sort_with_data(listaAnswersdaQuestion, comparaBestQuestion, usersht); // aqui usa se g_list_sort_with_data, que usa uma funçao auxiliar de comparaçao para ordenar uma lista
                                                                                                              // podendo tambem se dar dados extra, neste caso a hashtable de users, para se ver a reputaçao do user que respondeu
        result = get_id_a(listaAnswersdaQuestion->data);
    }

    g_list_free(listaAnswersdaQuestion);
    return result;
}

//Funçao auxiliar de comparaçao usada para ordenar uma GList pela reputaçao de cada user
int comparaReputation(gconstpointer a, gconstpointer b){
    int result = 0;
    USERS ua = (gpointer)a;
    USERS ub = (gpointer)b;
    int repA = get_reputation(ua);
    int repB = get_reputation(ub);

    if (repA > repB) {result = -1;}
    else if (repA < repB) {result = 1;}
    return result;
}

// funçao auxiliar que é chamada pela incrementaTags, para comparar uma tag com outra, e caso seja igual, incrementar a count dessa tag
void auxIncrementaTags(gpointer key, gpointer value, gpointer userdata){
    GString *gtag = g_string_new(userdata);
    GString *tag = g_string_erase(gtag, 0, 1); // isto é necessario porque as tags estao guardadas com um '<' a mais, que é retirado aqui
    if (!strcmp(get_tagName(value)->str, tag->str)) {
        increment_tagCount(value);
    }
    g_string_free(gtag, TRUE);
}

// funçao auxiliar para incrementar a count de uma tag
void incrementaTags(POSTS ppp, GHashTable *tagsht){
    GList *listaTags = get_listaTags(ppp);
    int listaTagsSize = g_list_length(listaTags);
    int i = 0;
    for (i = 0; i < listaTagsSize; i++) {
        g_hash_table_foreach(tagsht, auxIncrementaTags, listaTags->data);
        listaTags = listaTags->next;
    }
    g_list_free(listaTags);
}

//funçao auxiliar de comparaçao usada para ordenar uma GList de tags, pela count delas
int comparaTagCount(gconstpointer a, gconstpointer b){
    int result = 0;
    int countA = get_tagCount((gpointer)a);
    int countB = get_tagCount((gpointer)b);
    
    if (countA > countB) {result = -1;}
    else if (countA < countB) {result = 1;}
    
    return result;
}

// query 11
LONG_list most_used_best_rep(TAD_community com, int N, Date begin, Date end){
    LONG_list result = create_list(N);

    int anoBegin = get_year(begin);
    int mesBegin = get_month(begin);
    int diaBegin = get_day(begin);

    int anoEnd = get_year(end);
    int mesEnd = get_month(end);
    int diaEnd = get_day(end);
    
    GHashTable *tagsht = get_hash_tags(com); // aqui obtemos a hashtable das tags da estrutura com
    GHashTable *usersht = get_hash_userss(com);
    
    GArray *anos = get_array_anos(com);
    GArray *meses = NULL;
    GArray *dias = NULL;
    DIA dia = NULL;
    GList *questionsdoDia = NULL;
    GList *listaQuestions = NULL;
    
    int i = 0;
    int j = 0;
    int d = 0;
    int ii = 0;
    int k = 0;
    int l = 0;
    int p = 0;
    int o = 0;
    int jj = 0;
    int sizeDia = 0;
    for (i = anoBegin - 2009; i <= anoEnd - 2009; i++) {
        meses = g_array_index(anos, GArray *, i);
        if(i == anoBegin -2009) k=mesBegin;
        if(i == anoEnd -2009) l=mesEnd;
        else{k=1;l=12;}
        for (j = k - 1; j <= l - 1; j++) {
            dias = g_array_index(meses, GArray *,j);
            if(i == anoBegin - 2009 && j == mesBegin) p = diaBegin;
            if(i == anoEnd - 2009 && j == mesEnd) o = diaEnd;
            else{p=1;o=31;} 
            for (d = p - 1; d <= o - 1 ; d++) { 
                dia = g_array_index(dias,DIA,d);
                GHashTable* questionshash = get_questions(dia);
                questionsdoDia = g_hash_table_get_values(questionshash);
                sizeDia = g_list_length(questionsdoDia);
                for (jj = 0; jj < sizeDia; jj++) {
                    listaQuestions = g_list_prepend(listaQuestions, questionsdoDia->data);
                    questionsdoDia = questionsdoDia->next;
                }
                g_list_free(questionsdoDia);
            }
        }
    }
    
    long userID = 0;
    USERS uuu = NULL;
    GList *listaBestUsers = NULL;
    GList *listaNBestUsers = NULL;
    GList *nPosts = NULL;
    GList *listaQuestionsCopy = listaQuestions; // faz se esta copia porque vai ser preciso percorrer a GList de questoes mais do que uma vez

    int listaQuestionsSize = g_list_length(listaQuestions);

    // Este for serve para encher uma GList com todos os users que postaram algo no periodo de tempo dado
    for (ii = 0; ii < listaQuestionsSize; ii++) {
        userID = get_user_id(listaQuestions->data);
        uuu = g_hash_table_lookup(usersht, (gpointer)userID);
        listaBestUsers = g_list_prepend(listaBestUsers, uuu);
        listaQuestions = listaQuestions->next;
    }

    // aqui ordena se a lista de users pela reputaçao
    listaBestUsers = g_list_sort(listaBestUsers, comparaReputation);
    
    // aqui tiramos apenas os N users que vao ser necessarios
    for (ii = 0; ii < N; ii++) {
        listaNBestUsers = g_list_prepend(listaNBestUsers, listaBestUsers->data);
        listaBestUsers = listaBestUsers->next;
    }

    // aqui enche se uma GList com as questions postadas pelos N melhores users (comparados pela reputaçao)
    for (ii = 0; ii < listaQuestionsSize; ii++) {
        userID = get_user_id(listaQuestionsCopy->data);
        uuu = g_hash_table_lookup(usersht, (gpointer)userID);
        if (g_list_find(listaNBestUsers, uuu)) {
            nPosts = g_list_prepend(nPosts, listaQuestionsCopy->data);
        }
        listaQuestionsCopy = listaQuestionsCopy->next;
    }

    POSTS ppp = NULL;
    int nPostsSize = g_list_length(nPosts);
    
    // aqui vai se incrementar a count das tags dos posts feitos pelos N melhores users
    for (ii = 0; ii < nPostsSize; ii++) {
        ppp = nPosts->data;
        incrementaTags(ppp, tagsht); //damos a esta funçao a hashtable das tags para que se possa incrementar a count das tags
        nPosts = nPosts->next;
    }
    
    // aqui passa se os valores que estao na hashtable das tags, e depois ordena se pela count delas
    GList *allTags = g_hash_table_get_values(tagsht);
    allTags = g_list_sort(allTags, comparaTagCount);

    //Este for enche a LONG_list com as N melhores tags, que depois vai ser retornada
    long tagID = 0;
    for (ii = 0; ii < N; ii++) {
        tagID = get_id_tag(allTags->data);
        set_list(result, ii, tagID);
        allTags = allTags->next;
    }

    g_list_free(listaBestUsers);
    g_list_free(listaNBestUsers);
    g_list_free(nPosts);
    g_list_free(listaQuestionsCopy);
    g_list_free(questionsdoDia);
    return result;   
}

//Estas funçoes auxiliares servem para dar free aos valores das varias hashtables utilizadas
void auxFreeUsers(gpointer key, gpointer value, gpointer userdata){
   free_usersHashTable(value); 
}

void auxFreeTags(gpointer key, gpointer value, gpointer userdata){
   free_tagsHashTable(value); 
}

void auxFreeAnswers(gpointer key, gpointer value, gpointer userdata){
   free_answersHashTable(value); 
}

void auxFreeQuestions(gpointer key, gpointer value, gpointer userdata){
   free_postHashTable(value); 
}

TAD_community clean(TAD_community com){
    GHashTable *usersht = get_hash_userss(com);
    GHashTable *tagsht = get_hash_tags(com);
    GArray *anos = get_array_anos(com);
    GArray *meses = NULL;
    GArray *dias = NULL;
    DIA dia = NULL;
    int i = 0;
    int j = 0;
    int d = 0;
    for (i = 0; i < 10; i++) {
        meses = g_array_index(anos,GArray *,i);
        for (j = 0; j < 12; j++) {
            dias = g_array_index(meses, GArray *,j);
            for (d = 0; d < 31; d++) {
                // aqui estamos a limpar cada dia do calendario
                dia = g_array_index(dias,DIA,d);
                GHashTable* respostashash = get_answers(dia);
                GHashTable* questionshash = get_questions(dia);
                g_hash_table_foreach(respostashash, auxFreeAnswers, NULL);
                g_hash_table_foreach(questionshash, auxFreeQuestions, NULL);
                free_diaNodo(dia); 
            }
        }
    }

    // por fim limpamos as restantes hashtables que nao estao associadas ao calendario (users e tags) e usamos a funçao g_hash_table_destroy para minimizar leaks de memoria
    g_hash_table_foreach(usersht, auxFreeUsers, NULL);
    g_hash_table_destroy(usersht);
    g_hash_table_foreach(tagsht, auxFreeTags, NULL);
    g_hash_table_destroy(tagsht);
    return com;
}

