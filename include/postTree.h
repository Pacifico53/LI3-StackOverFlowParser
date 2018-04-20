#include <glib.h>
#ifndef __POSTS__
#define __POSTS__
typedef struct TREE_posts* POSTS;

POSTS create_tree_posts(long id_p, Date data_p, int score_p, long user_id, char* titulo, int comment_count, char* tags, int numeroRespostas);

long get_id_p(POSTS p);

Date get_data_p(POSTS p);

int get_score_p(POSTS p);

long get_user_id(POSTS p);

char* get_titulo(POSTS p);

int get_comment_count(POSTS p);

char* get_tags(POSTS p);

int get_numeroRespostas(POSTS p);

void free_posttree(POSTS p);

#endif
