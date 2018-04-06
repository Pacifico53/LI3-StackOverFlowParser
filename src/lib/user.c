#include "user.h"

struct user {
  char* id;
  int upVotes;
  int downVotes;
  char* displayName;
  int nPosts;
  char* bio;
  int reputation;
  long posts[10];
};

USER create_user(char* id, int upVotes, int downVotes, char* displayName, int nPosts, char* bio, int reputation, long* post_history) {
  USER u = malloc(sizeof(struct user)); 
  u->id = mystrdup(id);
  u->upVotes = upVotes;
  u->downVotes = downVotes;
  u->displayName = mystrdup(displayName);
  u->nPosts = nPosts;
  u->bio = mystrdup(bio);
  u->reputation = reputation;
  memcpy(u->posts, post_history, sizeof(long) * 10);

  return u;
}

char* get_bio(USER u) {
  if(u)
    return u->bio;
  return NULL;
}

long* get_10_latest_posts(USER u) {
  long* r = malloc(sizeof(long)*10);
  memcpy(r, u->posts, sizeof(long)*10);
  return r;
}

void free_user(USER u) {
  if(u) {
    free(u->id);
    free(u->displayName);
    free(u->bio);
    free(u);
  }
}
