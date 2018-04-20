#ifndef __USERS__
#define __USERS__
typedef struct TREE_users* USERS;

USERS create_tree_users(long id, char* name, char* aboutme, int score, int reputation);

long get_id(USERS u);

char* get_name(USERS u);

char* get_aboutme(USERS u);

int get_score(USERS u);

int get_reputation(USERS u);

void free_userstree(USERS u);

#endif