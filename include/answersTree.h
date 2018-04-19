#ifndef __ANSWERS__
#define __ANSWERS__
typedef struct TREE_answers* ANSWERS;

ANSWERS create_tree_answers (long id, Date data, int score, long user_id, int comment_count);

long get_id(ANSWERS a);

Date get_data(ANSWERS a);

int get_score(ANSWERS a);

long get_user_id(ANSWERS a);

int get_comment_count(ANSWERS a);

void free_answerstree (ANSWERS a);