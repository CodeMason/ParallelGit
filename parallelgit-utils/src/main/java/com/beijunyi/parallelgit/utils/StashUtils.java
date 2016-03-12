package com.beijunyi.parallelgit.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

import org.eclipse.jgit.lib.*;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

import static com.beijunyi.parallelgit.utils.CommitUtils.getCommit;
import static org.eclipse.jgit.lib.Constants.R_STASH;
import static org.eclipse.jgit.lib.ObjectId.zeroId;

public final class StashUtils {

  public static void createStash(@Nonnull RevCommit commit, @Nonnull Repository repo) throws IOException {
    RefUpdate update = repo.updateRef(R_STASH);
    update.setNewObjectId(commit);
    update.setRefLogIdent(commit.getCommitterIdent());
    update.setExpectedOldObjectId(commit.getParent(0) != null ? commit.getParent(0) : zeroId());
    update.forceUpdate();
  }

  @Nonnull
  public static RevCommit createStash(@Nonnull AnyObjectId commitId, @Nonnull Repository repo) throws IOException {
    RevCommit commit = getCommit(commitId, repo);
    createStash(commit, repo);
    return commit;
  }

  @Nonnull
  public static RevCommit createStash(@Nonnull String revision, @Nonnull Repository repo) throws IOException {
    AnyObjectId commitId = repo.resolve(revision);
    return createStash(commitId, repo);
  }

  @Nonnull
  public static List<RevCommit> listStashes(@Nonnull Repository repo) throws IOException {
    List<RevCommit> ret = new ArrayList<>();
    List<ReflogEntry> logs = RefUtils.getRefLogs(R_STASH, Integer.MAX_VALUE, repo);
    try(RevWalk rw = new RevWalk(repo)) {
      for(ReflogEntry log : logs)
        ret.add(rw.parseCommit(log.getNewId()));
    }
    return ret;
  }

}