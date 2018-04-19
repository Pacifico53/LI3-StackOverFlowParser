#include <glib.h>

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
