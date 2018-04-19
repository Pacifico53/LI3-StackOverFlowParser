#include <glib.h>
#include <date.h>

/*struct TREE_posts
 * Estrutura de cada nodo da tree de posts.
 *
 */
struct TREE_posts {
    long id;
    Date data;
    int score;           //Upvotes - Downvotes
    long user_id;
    char* titulo;
    int comment_count;
    char** tags;
    int numeroRespostas; //Calculado por nos
    GTree* post_answers;
};
