#include "answersHashTable.h"

/* struct HASHTABLE_answers
 * Estrutura de cada value da hashtable de posts de resposta.
 *
 */
struct HASHTABLE_answers {
    long id_a;
    int score_a;
    long user_id_a;
    int comment_count_a;
    long parent_id;
};

ANSWERS create_hashtable_answers (long id_a, int score_a, long user_id_a, int comment_count_a, long parent_id){
    ANSWERS a = malloc(sizeof(struct HASHTABLE_answers));
    a->id_a = id_a;
    a->score_a = score_a;
    a->user_id_a = user_id_a;
    a->comment_count_a = comment_count_a;
    a->parent_id = parent_id;
    return a;
}

long get_id_a(ANSWERS a){
    return a->id_a;
}

int get_score_a(ANSWERS a){
    return a->score_a;
}

long get_user_id_a(ANSWERS a){
    return a->user_id_a;
}

int get_comment_count_a(ANSWERS a){
    return a->comment_count_a;
}

long get_parent_id(ANSWERS a){
    return a->parent_id;
}

void free_answersHashTable (ANSWERS a){
    free(a);
}
