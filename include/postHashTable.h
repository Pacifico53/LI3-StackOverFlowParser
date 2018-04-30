#include <glib.h>
#ifndef __POSTS__
#define __POSTS__
typedef struct HASHTABLE_posts* POSTS;

POSTS create_hashtable_posts(long id_p, int score_p, long user_id, GString* titulo, int comment_count, GString* tags, int numeroRespostas);

long get_id_p(POSTS p);

int get_score_p(POSTS p);

long get_user_id(POSTS p);

GString* get_titulo(POSTS p);

int get_comment_count(POSTS p);

GString* get_tags(POSTS p);

int get_numeroRespostas(POSTS p);

GList *get_listaTags(POSTS p);

void free_postHashTable(POSTS p);

#endif
