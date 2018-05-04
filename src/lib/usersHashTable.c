#include "usersHashTable.h"

/* struct HASHTABLE_users
 * Estrutura do que esta em cada value da hashtable de users.
 *
 */
struct HASHTABLE_users {
    long id;
    GString* name;
    GString* aboutme;
    int reputation;
    int numberOfPosts;      //inicializado a 0, e so alterado pela funçao increment_numberOfPosts
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

//Esta funçao é utilizada na query 2, em que se ordena os utilizadores pelo numero de posts
void increment_numberOfPosts(USERS u){
    u->numberOfPosts++;
}

void free_usersHashTable(USERS u) {
    g_string_free(u->name, TRUE);
    g_string_free(u->aboutme, TRUE);
    free(u);
}
