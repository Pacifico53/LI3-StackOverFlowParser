#include <glib.h>
#include <date.h>

/* struct TREE_answers
 * Estrutura de cada nodo da arvore de posts de resposta.
 *
 */
struct TREE_answers {
    long id;
    Date data;
    int score;
    long user_id;
    int comment_count;
};
