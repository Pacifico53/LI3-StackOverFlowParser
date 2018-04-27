#include <glib.h>
#include <string.h>
#include <stdlib.h>
#include "usersHashTable.h"
#include "common.h"

/* struct HASHTABLE_users
 * Estrutura da hashtable de users.
 *
 */
struct HASHTABLE_users {
    long id;
    GString* name;
    GString* aboutme;
    int reputation;
    int numberOfPosts;
};

USERS create_hashtable_users(long id, GString* name, GString* aboutme, int reputation, int numberPosts) {
  USERS u = malloc(sizeof(struct HASHTABLE_users));
  u->id = id;
  u->name = name;
  u->aboutme = aboutme;
  u->reputation = reputation;
  u->numberOfPosts = numberPosts;  
  return u;
}

long get_id(USERS u) {
    return u->id;
}

GString* get_name(USERS u) {
    return u->name;
}

GString* get_aboutme(USERS u) {
    return u->aboutme;
}

int get_reputation(USERS u) {
    return u->reputation;
}

int get_numberOfPosts(USERS u){
    return u->numberOfPosts;
}

void increment_numberOfPosts(USERS u){
    u->numberOfPosts++;
}

void free_usersHashTable(USERS u) {
    free(u->name);
    free(u->aboutme);
    free(u);
}
