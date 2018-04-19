#ifndef __POSTS__
#define __POSTS__
typedef struct TREE_posts* POSTS;

POSTS create_tree_posts(long id, Date data, int score, long user_id, char* titulo, int comment_count, char* tags, int numeroRespostas, GTree* post_answers);

long get_id(POSTS p);

Date get_data(POSTS p);

int get_score(POSTS p);

long get_user_id(POSTS p);

char* get_titulo(POSTS p);

int get_comment_count(POSTS p);

char* get_tags(POSTS p);

int get_numeroRespostas(POSTS p);

GTree* get_post_answers(TREE_posts) 

void free_posttree(POSTS p);

#endif