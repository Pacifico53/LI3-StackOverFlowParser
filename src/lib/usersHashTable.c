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
    char* name;
    char* aboutme;
    int reputation;
};

USERS create_hashtable_users(long id, char* name, char* aboutme, int reputation) {
  USERS u = malloc(sizeof(struct HASHTABLE_users));
  u->id = id;
  u->name = mystrdup(name);
  u->aboutme = mystrdup(aboutme);
  u->reputation = reputation;
  return u;
}

long get_id(USERS u) {
    return u->id;
}

char* get_name(USERS u) {
    return u->name;
}

char* get_aboutme(USERS u) {
    return u->aboutme;
}


int get_reputation(USERS u) {
    return u->reputation;
}

void free_usersHashTable(USERS u) {
    free(u->name);
    free(u->aboutme);
    free(u);
}
