#include <glib.h>
#include <string.h>
#include <stdlib.h>
#include "common.h"
#ifndef __USERS__
#define __USERS__
typedef struct HASHTABLE_users* USERS;

USERS create_hashtable_users(long id, GString* name, GString* aboutme, int reputation, int numberOfPosts);

long get_id(USERS u);

GString* get_name(USERS u);

GString* get_aboutme(USERS u);

int get_score(USERS u);

int get_reputation(USERS u);

int get_numberOfPosts(USERS u);

void increment_numberOfPosts(USERS u); 

void free_usersHashTable(USERS u);

#endif
