package com.beijunyi.parallelgit.filesystem;

import java.io.IOException;

import com.beijunyi.parallelgit.filesystem.utils.GitFileSystemBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GitPathHashCodeTest extends AbstractGitFileSystemTest {

  @Before
  public void setupFileSystem() throws IOException {
    initGitFileSystem();
  }

  @Test
  public void hashCodeOfAbsolutePathTest() {
    GitPath p1 = gfs.getPath("/a/b/c");
    GitPath p2 = gfs.getPath("/a/b/c");
    Assert.assertEquals(p1.hashCode(), p2.hashCode());
  }

  @Test
  public void hashCodeOfRelativePathTest() {
    GitPath p1 = gfs.getPath("a/b/c");
    GitPath p2 = gfs.getPath("a/b/c");
    Assert.assertEquals(p1.hashCode(), p2.hashCode());
  }

  @Test
  public void hashCodesFromDifferentPathsTest() {
    GitPath path = gfs.getPath("/a/b/c");
    int hashCode = path.hashCode();
    Assert.assertNotEquals(hashCode, gfs.getPath("a/b/c").hashCode());
    Assert.assertNotEquals(hashCode, gfs.getPath("/a/b").hashCode());
    Assert.assertNotEquals(hashCode, gfs.getPath("/a/b/c/d").hashCode());
    Assert.assertNotEquals(hashCode, gfs.getPath("abc").hashCode());
    Assert.assertNotEquals(hashCode, gfs.getPath("/").hashCode());
    Assert.assertNotEquals(hashCode, gfs.getPath("").hashCode());
  }

  @Test
  public void hashCodesFromDifferentFileSystemTest() throws IOException {
    GitFileSystem other = GitFileSystemBuilder.prepare()
                            .repository(repo)
                            .build();
    GitPath p1 = gfs.getPath("/a/b/c");
    GitPath p2 = other.getPath("/a/b/c");
    Assert.assertNotEquals(p1.hashCode(), p2.hashCode());
  }

}
