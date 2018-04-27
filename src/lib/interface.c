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
    STR_pair par;
    GHashTable* usershash = get_hash_userss(com);
    USERS user;
    GArray* anos = get_array_anos(com);
    GArray* meses;
    GArray* dias;
    DIA dia;
    POSTS questions;
    ANSWERS respostas;
    GString* titulo;
    GString* nome;
    long id_p_u = 0;
    long id_parente = 0;
    int parar = 1;
    int i = 0;
    int j = 0;
    int d = 0;
    for (i = 0; i < 10 && parar; i++) {
        meses = g_array_index(anos,GArray *,i);
        for (j = 0; j < 12 && parar; j++) {
            dias = g_array_index(meses, GArray *,j);
            for (d = 0; d < 31 && parar; d++) {
                dia = g_array_index(dias,DIA,d);
                GHashTable* respostashash = get_answers(dia);
                GHashTable* questionshash = get_questions(dia);
                if(g_hash_table_contains(respostashash,GINT_TO_POINTER(id))){
                    printf("ENCONTREI A RESPOSTA EM ANO = %d , MES = %d , DIA = %d!\nVOU PROCURAR A PERGUNTA!\n", i, j, d);
                    parar = 0;
                    respostas = g_hash_table_lookup(respostashash,GINT_TO_POINTER(id));
                    id_parente = get_parent_id(respostas);
                    return info_from_post(com, id_parente);
                }
                if(g_hash_table_contains(questionshash,GINT_TO_POINTER(id))){
                    parar = 0;
                    printf("ENCONTREI EM ANO = %d , MES = %d , DIA = %d\n", i, j, d);
                    questions = g_hash_table_lookup(questionshash,GINT_TO_POINTER(id));
                    titulo = get_titulo(questions);
                    id_p_u = get_user_id(questions);
                    user = g_hash_table_lookup(usershash, GINT_TO_POINTER(id_p_u));
                    nome = get_name(user);
                    par = create_str_pair(titulo->str, nome->str);
                    printf("TITULO = \"%s\". USER NAME =\"%s\".\n", get_fst_str(par), get_snd_str(par));
                }
            }
        }
    }
    return par;
}

void incrementaNumbers(gpointer key, gpointer value, gpointer userdata){
    long userID = 0;
    userID = get_user_id_a(value);

    increment_numberOfPosts(g_hash_table_lookup(userdata, GINT_TO_POINTER(userID)));
}

int comparaNumeroPosts(USERS a, USERS b){
    int result = 0;
    int numeroPostsa = get_numberOfPosts(a);
    int numeroPostsb = get_numberOfPosts(b);

    if (numeroPostsa > numeroPostsb) {result = -1;}
    else if (numeroPostsa < numeroPostsb) {result = 1;}
    
    return result;
}

// query 2
LONG_list top_most_active(TAD_community com, int N){
    LONG_list result = create_list(N);
    GHashTable *usersht = get_hash_userss(com);
    USERS user;
    GArray *anos = get_array_anos(com);
    GArray *meses;
    GArray *dias;
    DIA dia;
    long userID;
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
                g_hash_table_foreach(respostashash, incrementaNumbers, usersht);
                g_hash_table_foreach(questionshash, incrementaNumbers, usersht);
            }
        }
    }
    GList * listaUsers = g_hash_table_get_values(usersht);
    listaUsers = g_list_sort(listaUsers, comparaNumeroPosts);
    for (ii = 0; ii < N; ii++) {
        user = listaUsers->data;
        userID = get_id(user);
        set_list(result, ii, userID);
        printf("%d = %lu\n", ii, get_list(result, ii));
        listaUsers = listaUsers->next;
    }
    return result;
}



// query 3
LONG_pair total_posts(TAD_community com, Date begin, Date end);

// query 4
LONG_list questions_with_tag(TAD_community com, char* tag, Date begin, Date end);

// query 5
USER get_user_info(TAD_community com, long id);

// query 6
LONG_list most_voted_answers(TAD_community com, int N, Date begin, Date end);

// query 7
LONG_list most_answered_questions(TAD_community com, int N, Date begin, Date end);

// query 8
LONG_list contains_word(TAD_community com, char* word, int N);

// query 9
LONG_list both_participated(TAD_community com, long id1, long id2, int N);

// query 10
long better_answer(TAD_community com, long id);

// query 11
LONG_list most_used_best_rep(TAD_community com, int N, Date begin, Date end);

TAD_community clean(TAD_community com);
