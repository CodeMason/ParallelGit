package com.beijunyi.parallelgit.filesystem;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class GitPathCompareTest extends AbstractGitFileSystemTest {

  @Before
  public void setupFileSystem() throws IOException {
    initGitFileSystem();
  }

  @Test
  public void absolutePathCompareTest() {
    GitPath p1 = gfs.getPath("/a/b/1");
    GitPath p2 = gfs.getPath("/a/b/2");
    assertTrue(p1.compareTo(p2) < 0);
    assertTrue(p2.compareTo(p1) > 0);
  }

  @Test
  public void absolutePathCompareToSamePathTest() {
    GitPath p1 = gfs.getPath("/a/b/1");
    GitPath p2 = gfs.getPath("/a/b/1");
    assertTrue(p1.compareTo(p2) == 0);
  }

  @Test
  public void absolutePathCompareToPrefixPathTest() {
    GitPath p1 = gfs.getPath("/a/b/1");
    GitPath p2 = gfs.getPath("/a/b/11");
    assertTrue(p1.compareTo(p2) < 0);
    assertTrue(p2.compareTo(p1) > 0);
  }

  @Test
  public void relativePathCompareTest() {
    GitPath p1 = gfs.getPath("a/b/1");
    GitPath p2 = gfs.getPath("a/b/2");
    assertTrue(p1.compareTo(p2) < 0);
    assertTrue(p2.compareTo(p1) > 0);
  }

  @Test
  public void relativePathCompareToSamePathTest() {
    GitPath p1 = gfs.getPath("a/b/1");
    GitPath p2 = gfs.getPath("a/b/1");
    assertTrue(p1.compareTo(p2) == 0);
  }

  @Test
  public void relativePathCompareToPrefixPathTest() {
    GitPath p1 = gfs.getPath("a/b/1");
    GitPath p2 = gfs.getPath("a/b/11");
    assertTrue(p1.compareTo(p2) < 0);
    assertTrue(p2.compareTo(p1) > 0);
  }
}
