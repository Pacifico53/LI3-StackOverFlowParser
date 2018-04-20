#include <glib.h>
#include <string.h>
#include <stdlib.h>
#include "usersTree.h"
#include "common.h"

/* struct TREE_users
 * Estrutura de cada nodo da arvore de users.
 *
 */
struct TREE_users {
    long id;
    char* name;
    char* aboutme;
    int score;
    int reputation;
};

USERS create_tree_users(long id, char* name, char* aboutme, int score, int reputation) {
  USERS u = malloc(sizeof(struct TREE_users));
  u->id = id;
  u->name = mystrdup(name);
  u->aboutme = mystrdup(aboutme);
  u->score = score;
  u->reputation = reputation;
  return u;
}

long get_id(USERS u) {
  if(u)
    return u->id;
  return NULL;
}

char* get_name(USERS u) {
  if(u)
    return u->name;
  return NULL;
}

char* get_aboutme(USERS u) {
  if(u)
    return u->aboutme;
  return NULL;
}

int get_score(USERS u) {
  if(u)
    return u->score;
  return NULL;
}

int get_reputation(USERS u) {
  if(u)
    return u->reputation;
  return NULL;
}

void free_userstree(USERS u) {
  if(u) {
    free(u->name);
    free(u->aboutme);
    free(u);
  }
}
