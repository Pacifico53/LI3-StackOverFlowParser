#include "date.h"
#ifndef __ANSWERS__
#define __ANSWERS__

typedef struct HASHTABLE_answers* ANSWERS;

ANSWERS create_hashtable_answers (long id_a, Date data_a, int score_a, long user_id_a, int comment_count_a, long get_parent_id);

long get_id_a(ANSWERS a);

Date get_data_a(ANSWERS a);

int get_score_a(ANSWERS a);

long get_user_id_a(ANSWERS a);

int get_comment_count_a(ANSWERS a);

long get_parent_id(ANSWERS a);

void free_answersHashTable (ANSWERS a);

#endif
