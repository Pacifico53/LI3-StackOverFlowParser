#ifndef __USERS__
#define __USERS__
typedef struct HASHTABLE_users* USERS;

USERS create_hashtable_users(long id, GString* name, GString* aboutme, int reputation);

long get_id(USERS u);

GString* get_name(USERS u);

GString* get_aboutme(USERS u);

int get_score(USERS u);

int get_reputation(USERS u);

void free_usersHashTable(USERS u);

#endif
